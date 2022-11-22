import kotlin.math.ceil
import kotlin.math.sqrt

open class Encoder {
    fun encodeBuffer(buff: ByteArray): Array<IntArray> {
        val arr = buff.map { it + 128 }.toIntArray()
        return Array(1) { arr }
    }

    fun decodeBuffer(data: Array<IntArray>): ByteArray {
        return data[0].map { it -> (it - 128).toByte() }.toByteArray()
    }

    fun encodePassword(pw: String): Array<IntArray> {
        val size = ceil(sqrt(pw.length.toDouble())).toInt();
        val mat = Array(size) { IntArray(size) { 0 } }
        for (i in 0 until size) {
            for (j in 0 until size) {
                val idx = i * size + j;
                mat[i][j] = pw[idx % pw.length].code
            }
        }
        return mat;
    }
}