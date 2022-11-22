import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.swing.JOptionPane
import javax.swing.SwingWorker

class EncryptFileWorker(val ui: MainUI, val file: File, val pw: String): SwingWorker<Int, Int>() {

    override fun doInBackground(): Int {
        ui.btnEncrypt.isEnabled = false;
        ui.btnDecrypt.isEnabled = false;

        val hill = HillCrypto(pw)
        val readFile = ReadFile()
        readFile.openFile(file, hill.keySize)
        val fout = BufferedOutputStream(FileOutputStream("${file.path}.hill"))
        for (i in 0..readFile.maxPieceIdx) {
            var (buff, buffLen) = readFile.getNextBuffer(i)
            buff = hill.encrypt(buff);
            fout.write(buff, 0, buffLen)

            if (i % 10000 == 0) {
                publish(i  * 100 / readFile.maxPieceIdx);
            }
        }
        readFile.close()
        fout.close()
        return 0;
    }

    override fun process(chunks: MutableList<Int>?) {
        ui.progressBar.value = chunks?.lastOrNull() ?: 100
    }

    override fun done() {
        try {
            get();
            ui.progressBar.value = 100;

            JOptionPane.showMessageDialog(null, "Encrypt completed")
        } catch (ex: Exception) {
            JOptionPane.showMessageDialog(null, ex.message)
        } finally {
            ui.btnEncrypt.isEnabled = true;
            ui.btnDecrypt.isEnabled = true;
        }
    }
}