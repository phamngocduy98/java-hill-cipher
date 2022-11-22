import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.swing.JOptionPane
import javax.swing.SwingUtilities
import javax.swing.SwingWorker

class DecryptFileWorker(private val ui: MainUI, private val file: File, private val pw: String) :
    SwingWorker<Int, Int>() {
    override fun doInBackground(): Int {
        ui.btnEncrypt.isEnabled = false;
        ui.btnDecrypt.isEnabled = false;

        val hill = HillCrypto(pw)
        val readFile = ReadFile()
        readFile.openFile(file, hill.keySize)
        val fout = BufferedOutputStream(FileOutputStream("${file.parent}\\hill.${file.nameWithoutExtension}"))
        for (i in 0..readFile.maxPieceIdx) {
            var (buff, buffLen) = readFile.getNextBuffer(i)
            buff = hill.decrypt(buff);
            fout.write(buff, 0, buffLen)

            if (i % 10000 == 0) {
                publish(i * 100 / readFile.maxPieceIdx);
            }
        }
        readFile.close()
        fout.close()
        return 0
    }

    override fun process(chunks: MutableList<Int>?) {
        ui.progressBar.value = chunks?.lastOrNull() ?: 100
    }

    override fun done() {
        try {
            get();
            ui.progressBar.value = 100;

            JOptionPane.showMessageDialog(null, "Decrypt completed")
        } catch (ex: Exception) {
            JOptionPane.showMessageDialog(null, ex.message)
        } finally {
            ui.btnEncrypt.isEnabled = true;
            ui.btnDecrypt.isEnabled = true;
        }
    }
}