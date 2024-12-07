import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.util.List;

public class Application extends JFrame {
    private VariableCompiler compiler;
    private JTextArea compilerInputTextArea;
    private JTextArea compilerOutputTextArea;
    private JButton openFileButton;
    private JButton lexicalAnalysisButton;
    private JButton syntaxAnalysisButton;
    private JButton semanticAnalysisButton;
    private JPanel mainPanel;
    private JButton clearButton;
    private JButton runCodeButton;
    private JFrame frame;

    public Application() {
        compiler = new VariableCompiler();
        createUI();
    }

    private void createUI() {
        setContentPane(mainPanel);
        setTitle("Java MiniCompiler!");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // Input Area
        JTextPane compilerInputTextPane = new JTextPane();
        compilerInputTextPane.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane inputScrollPane = new JScrollPane(compilerInputTextPane);
        inputScrollPane.setBorder(BorderFactory.createTitledBorder("Input"));


        // Buttons
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileContents = openFileExplorer();
                contents = listToString(fileContents);

                if(!fileContents.isEmpty()){
                    compilerInputTextArea.setText(contents);
                }

            }
        });
        lexicalAnalysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLexical();
            }
        });
        syntaxAnalysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSyntax();
            }
        });
        semanticAnalysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSemantic();
            }
        });
        runCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAllPhases();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compilerInputTextArea.setText("");
                compilerOutputTextArea.setText("");
            }
        });
    }

    private void handleLexical() {
        String input = compilerInputTextArea.getText(); // Get text from JTextPane
        compilerOutputTextArea.setText(""); // Clear previous output
        compilerOutputTextArea.append("Lexical Analysis:\n");

        try {
            // Step 1: Initialize the lexer with the input text
            Lexer lexer = new Lexer(input);

            // Step 2: Tokenize the input
            Token token;
            while ((token = lexer.getNextToken()).type != TokenType.EOF) {
                compilerOutputTextArea.append(token.toString() + "\n");
            }

            // Step 3: Lexical analysis completed
            compilerOutputTextArea.append("Lexical Analysis Complete\n");
        } catch (RuntimeException ex) {
            // If lexical error occurs, display error message
            compilerOutputTextArea.append("Lexical Error: " + ex.getMessage() + "\n");
        }
    }

    private void handleSyntax() {
        String input = compilerInputTextArea.getText(); // Get text from JTextPane
        compilerOutputTextArea.setText(""); // Clear previous output
        compilerOutputTextArea.append("Syntax Analysis:\n");

        try {
            // Step 1: Initialize lexer and parser with input text
            Lexer lexer = new Lexer(input);
            Parser parser = new Parser(lexer);

            // Step 2: Parse the input
            parser.parse(); // Will throw an error if there's a syntax issue

            // Step 3: Syntax analysis completed
            compilerOutputTextArea.append("Syntax Analysis Complete\n");
        } catch (RuntimeException ex) {
            // If syntax error occurs, display error message
            compilerOutputTextArea.append("Syntax Error: " + ex.getMessage() + "\n");
        }
    }

    private void handleSemantic() {
        String input = compilerInputTextArea.getText(); // Get text from JTextPane
        compilerOutputTextArea.setText(""); // Clear previous output
        compilerOutputTextArea.append("Semantic Analysis:\n");

        try {
            // Step 1: Initialize Lexer with input
            Lexer lexer = new Lexer(input);
            // Step 2: Initialize Parser with Lexer (token stream)
            Parser parser = new Parser(lexer);
            // Step 3: Parse the input to get the AST (Abstract Syntax Tree)
            AST ast = parser.parse();

            // Step 4: Initialize the Interpreter (for semantic analysis)
            Interpreter interpreter = new Interpreter();

            // Step 5: Perform semantic analysis (type checking, variable declarations, etc.)
            interpreter.interpret(ast); // This will throw errors if semantic issues are found

            // Step 6: Output success message to the output area
            compilerOutputTextArea.append("Semantic Analysis Complete: No errors found.\n");

        } catch (RuntimeException ex) {
            // If any semantic error occurs, show the error message
            compilerOutputTextArea.append("Semantic Error: " + ex.getMessage() + "\n");
        }
    }

    private void handleAllPhases() {
        String input = compilerInputTextArea.getText(); // Get text from JTextPane
        boolean allPhasesPassed = true;

        // Step 1: Run Lexical Analysis
        handleLexical();

        // Step 2: Run Syntax Analysis if Lexical passed
        if (!compilerOutputTextArea.getText().contains("Lexical Error")) {
            handleSyntax();
        } else {
            allPhasesPassed = false;
        }

        // Step 3: Run Semantic Analysis if Syntax passed
        if (!compilerOutputTextArea.getText().contains("Syntax Error") && allPhasesPassed) {
            handleSemantic();
        } else {
            allPhasesPassed = false;
        }

        // Step 4: If all phases pass, append the symbol table
        if (allPhasesPassed) {
            compilerOutputTextArea.append("All phases passed. Symbol table:\n");
            Interpreter interpreter = new Interpreter();
            interpreter.printSymbolTable(); // Print the symbol table if all phases pass
        } else {
            compilerOutputTextArea.append("Compilation failed due to previous errors.\n");
        }
    }

    //File exploere
    List<String> fileContents = new ArrayList<String>();
    String contents = "none";
    public List<String> openFileExplorer()
    {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Java and Text Files", "java", "txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            fileContents = displayFileContents(selectedFile);
        }
        return fileContents;

    }

    private String listToString(List<String> list)
    {
        // convert list to string xdd
        return String.join("\n", list);
    }

    private List<String> displayFileContents(File file)
    {
        try
        {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(file);

            // Wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read the file line by line and display the contents.
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                fileContents.add(line);
            }

            contents = fileContents.toString();

            // Close the BufferedReader.
            bufferedReader.close();


        }
        catch (IOException e)
        {
            e.printStackTrace();

        }
        return fileContents;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }

}
