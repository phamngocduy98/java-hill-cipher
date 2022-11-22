import java.lang.RuntimeException

class MatrixCal {
    companion object {
        private val mod256 = Mod(256)
        fun multiply(mat1: Array<IntArray>, mat2: Array<IntArray>): Array<IntArray> {
//            NOTE: mat1[i] => column[i] (not row[i])
            val m = mat1[0].size
            val n = mat1.size;
            val n2 = mat2[0].size
            val p = mat2.size

            if (n != n2) {
                throw RuntimeException("Invalid matrix size")
            }

            val resMat: Array<IntArray> = Array(p) { IntArray(m) { 0 } };
            for (i in 0 until m) {
                for (j in 0 until p) {
                    for (k in 0 until n) {
                        resMat[j][i] += mod256.modize(mat1[k][i] * mat2[j][k])
                    }
                    resMat[j][i] = mod256.modize(resMat[j][i])
                }
            }
            return resMat;
        }
        fun multiply(mat: Array<IntArray>, n: Int): Array<IntArray> {
//            NOTE: mat[i] => column[i] (not row[i])

            val resMat: Array<IntArray> = Array(mat.size) { IntArray(mat[0].size) { 0 } };
            for (i in 0 until mat.size) {
                for (j in 0 until mat[0].size) {
                    resMat[i][j] = mod256.modize(mat[i][j] * n);
                }
            }
            return resMat;
        }

        private fun subMatrix(mat: Array<IntArray>, rmI: Int, rmJ: Int): Array<IntArray> {
            val colFiltered = mat.filterIndexed { i, _ -> i != rmI };
            return colFiltered.map { col -> col.filterIndexed { j, _ -> j != rmJ }.toIntArray() }.toTypedArray()
        }

        fun determinant(mat: Array<IntArray>): Int {
            if (mat.size == 2 && mat[0].size == 2) {
                return mat[0][0] * mat[1][1] - mat[1][0] * mat[0][1];
            }
            var result = 0;
            for (j in 0 until mat[0].size) {
                result += mat[0][j] * (if (j % 2 == 0) 1 else -1) * determinant(subMatrix(mat, 0, j))
            }
            return mod256.modize(result);
        }

        private fun transpose(mat: Array<IntArray>): Array<IntArray> {
            val resMat: Array<IntArray> = Array(mat[0].size) { IntArray(mat.size) };
            for (i in 0 until mat.size) {
                for (j in 0 until mat[0].size) {
                    resMat[i][j] = mat[j][i];
                }
            }
            return resMat;
        }

        fun adjugateMatrix(mat: Array<IntArray>): Array<IntArray> {
            if (mat.size == 2 && mat[0].size == 2) {
                return arrayOf(intArrayOf(mat[1][1], -mat[1][0]), intArrayOf(-mat[0][1], mat[0][0]));
            }
            val resMat: Array<IntArray> = Array(mat.size) { IntArray(mat[0].size) };
            for (i in 0 until mat.size) {
                for (j in 0 until mat[0].size) {
                    resMat[i][j] = (if ((i + j) % 2 == 0) 1 else -1) * determinant(subMatrix(mat, i, j))
                }
            }
            return resMat
        }

        fun invert(mat: Array<IntArray>): Array<IntArray>? {
            val det = determinant(mat);
            if (det == 0) {
                return null;
            }
            val matT = transpose(mat);
            val matAStar = adjugateMatrix(matT)
            return multiply(matAStar, mod256.invert(det));
        }
    }


}