import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


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

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public Editor() {
        editorArea.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        editorArea.setLineWrap(true);
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
                    BufferedReader r = new BufferedReader(reader);
                    editorArea.setText("");
                    String line = r.readLine();
                    while (line != null) {
                        editorArea.append(line + "\n");
                        line = r.readLine();
                    }
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
                }
                catch(IOException ex){
                    isOpen = false;
                    editorArea.setText(ex.getMessage());
                    saveButton.setEnabled(isOpen);
                    editorArea.setEditable(isOpen);
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Editor");
        frame.setSize(WIDTH, HEIGHT);
        frame.setContentPane(new Editor().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
