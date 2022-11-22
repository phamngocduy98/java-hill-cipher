import java.lang.RuntimeException

class HillCrypto(pw: String) : Encoder(), ICrypto {
    private val key: Array<IntArray> = encodePassword(pw);
    private val keyInverted: Array<IntArray>;
    val keySize: Int = key.size;
    init {
//        check key
        val det = MatrixCal.determinant(key)
        if (det == 0) {
            throw RuntimeException("Invalid key (det = 0)")
        }
        val primeFactorDet = Prime.primeFactor(det);
//        println(primeFactorDet)
        val primeFactor256 = Prime.primeFactor(256);
//        println(primeFactor256)
        if (!primeFactorDet.all { pf -> !primeFactor256.contains(pf) }) {
            throw RuntimeException("Invalid key (common prime factor of 256)")
        }
        keyInverted = MatrixCal.invert(key)!!;
    }
    override fun encrypt(bytes: ByteArray): ByteArray {
//        println(bytes.contentToString())
        val encoded = encodeBuffer(bytes)
//        println(encoded[0].contentToString())
        val cipher = MatrixCal.multiply(key, encoded)
        val decoded = decodeBuffer(cipher)
//        println(decoded.contentToString())
        return decoded;
    }

    override fun decrypt(cipher: ByteArray): ByteArray {
//        println(cipher.contentToString())
        val encoded = encodeBuffer(cipher)
//        println(encoded[0].contentToString())
        val decrypted = MatrixCal.multiply(keyInverted, encoded)
        val decoded = decodeBuffer(decrypted)
//        println(decoded.contentToString())
        return decoded;
    }

}