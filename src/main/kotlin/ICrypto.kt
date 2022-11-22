interface ICrypto {
    fun encrypt(array: ByteArray): ByteArray;
    fun decrypt(cipher: ByteArray): ByteArray;
}