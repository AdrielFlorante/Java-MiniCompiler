import javax.swing.*;

public class Application extends JFrame {
    private JTextPane compilerInputTextPane;
    private JTextArea compilerOutputTextArea;
    private JButton openFileButton;
    private JButton lexicalAnalysisButton;
    private JButton syntaxAnalysisButton;
    private JButton semanticAnalysisButton;
    private JPanel mainPanel;
    private JButton clearButton;

    public Application() {
        setContentPane(mainPanel);
        setTitle("Java MiniCompiler!");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Application app = new Application();
    }
}
