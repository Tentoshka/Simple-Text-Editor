import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Editor {
    private JPanel panelMain;
    private JScrollPane scrollPane;
    private JToolBar menu;
    private JTextArea editorArea;
    private JButton openButton;
    private JButton saveButton;
    private JLabel linkLabel;
    private JTextField linkFile;
    private JPanel linkPane;
    private JButton OKButton;

    private boolean isOpen = false;
    private String link;

    public Editor() {

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkPane.setVisible(true);
            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                link = linkFile.getText();
                openFile(link);
                linkPane.setVisible(false);
            }
            private void openFile(String link) {
                try(FileReader reader = new FileReader(link))
                {
                    editorArea.setText(Files.lines(Paths.get(link)).reduce("", String::concat));
                    isOpen = true;
                }
                catch(IOException ex){
                    editorArea.setText(ex.getMessage());
                }
                saveButton.setEnabled(isOpen);
                editorArea.setEditable(isOpen);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try(FileWriter writer = new FileWriter(link, false))
                {
                    String text = editorArea.getText();
                    writer.write(text);
                    writer.flush();
                    isOpen = false;
                }
                catch(IOException ex){
                    editorArea.setText(ex.getMessage());
                    saveButton.setEnabled(isOpen);
                    editorArea.setEditable(isOpen);
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Editor");
        frame.setSize(800, 600);
        frame.setContentPane(new Editor().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
