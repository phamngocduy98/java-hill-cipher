import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ReadFile {
    private var fis: FileInputStream? = null
    private var fileSize: Long = 0
    var bufferSize: Int = 0
    var maxPieceIdx = 0

    @Throws(IOException::class)
    fun openFile(file: File, bufferSize: Int) {
        fileSize = file.length()
        this.bufferSize = bufferSize;
        maxPieceIdx = ((fileSize - 1) / bufferSize).toInt()
        fis?.close()
        fis = FileInputStream(file.path)
    }

    @Throws(IOException::class)
    fun getNextBuffer(nextPieceId: Int): Pair<ByteArray,Int> {
        if (nextPieceId > maxPieceIdx) return Pair(ByteArray(0),0)
        val readBuffer = ByteArray(bufferSize)
        var bytesRead = 0
        val bytesToRead: Int = if (nextPieceId == maxPieceIdx) {
            (fileSize - maxPieceIdx * bufferSize).toInt()
        } else {
            bufferSize
        }
        while (bytesRead < bytesToRead) {
            bytesRead += fis!!.read(readBuffer, bytesRead, bytesToRead - bytesRead)
        }
        return Pair(readBuffer, bytesToRead)
    }

    fun close() {
        fis?.close()
    }
}