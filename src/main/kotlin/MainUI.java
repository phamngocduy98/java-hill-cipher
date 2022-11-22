import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class MainUI {
    private JTextField txtFileSelect;
    private JPanel panelMain;
    private JButton btnBrowse;
    private JPasswordField txtPass;
    public JButton btnEncrypt;
    public JButton btnDecrypt;
    public JProgressBar progressBar;

    private java.io.File selectedFile = null;

    public MainUI() {
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileDialog = new JFileChooser();
                if (selectedFile != null) {
                    fileDialog.setCurrentDirectory(selectedFile);
                } else {
                    fileDialog.setCurrentDirectory(new File("D:\\"));
                }
                int returnVal = fileDialog.showOpenDialog(panelMain);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileDialog.getSelectedFile();
                    txtFileSelect.setText(selectedFile.getPath());
                }
            }
        });
        btnEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encrypt();
            }
        });
        btnDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decrypt();
            }
        });
    }

    public void encrypt() {
        try {
            String pass =  new String(txtPass.getPassword());
            if (pass.length() <= 4) {
                throw new RuntimeException("Password must have at least 4 characters");
            }
            if (selectedFile == null) {
                throw new RuntimeException("Please select a file");
            }
            EncryptFileWorker worker = new EncryptFileWorker(this, selectedFile, pass);
            worker.execute();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void decrypt() {
        try {
            String pass =  new String(txtPass.getPassword());
            if (pass.length() <= 4) {
                throw new RuntimeException("Password must have at least 4 characters");
            }
            if (selectedFile == null) {
                throw new RuntimeException("Please select a file");
            }
            DecryptFileWorker worker = new DecryptFileWorker(this, selectedFile, pass);
            worker.execute();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        MainUI ui = new MainUI();
        JFrame mainFrame = new JFrame("Hill Encryptor / Decryptor - by phamngocduy98");
        mainFrame.setContentPane(ui.panelMain);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

//        try {
//            EncryptFile enc = new EncryptFile();
//            enc.encrypt(new File("C:\\PERFORMANCE_DEV\\@encrypt\\aimer.ico"), "123456789");
//            enc.decrypt(new File("C:\\PERFORMANCE_DEV\\@encrypt\\aimer.ico.hill"), "123456789");
//            enc.encrypt("C:\\PERFORMANCE_DEV\\@encrypt\\rawdata.txt");
//            enc.decrypt("C:\\PERFORMANCE_DEV\\@encrypt\\rawdata.txt.hill");
//        } catch (IOException e) {
//            System.out.println("File not found");
//        }
    }
}
