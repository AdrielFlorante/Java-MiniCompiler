import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.util.List;

public class Application extends JFrame {
    private JTextArea compilerInputTextArea;
    private JTextArea compilerOutputTextArea;
    private JButton openFileButton;
    private JButton lexicalAnalysisButton;
    private JButton syntaxAnalysisButton;
    private JButton semanticAnalysisButton;
    private JPanel mainPanel;
    private JButton clearButton;
    private JButton runCodeButton;
    private JButton enterButton;
    private JFrame frame;

    public Application() {
        createUI();
    }

    private void createUI() {
        setContentPane(mainPanel);
        setTitle("Java MiniCompiler!");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //disable most buttons
        lexicalAnalysisButton.setEnabled(false);
        syntaxAnalysisButton.setEnabled(false);
        semanticAnalysisButton.setEnabled(false);
        runCodeButton.setEnabled(false);

        // Buttons
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileContents = openFileExplorer();
                contents = listToString(fileContents);

                if(!fileContents.isEmpty()){
                    compilerInputTextArea.setText(contents);
                    lexicalAnalysisButton.setEnabled(true);
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
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!compilerInputTextArea.getText().isEmpty()){
                    lexicalAnalysisButton.setEnabled(true);
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compilerInputTextArea.setText("");
                compilerOutputTextArea.setText("");
                lexicalAnalysisButton.setEnabled(false);
                syntaxAnalysisButton.setEnabled(false);
                semanticAnalysisButton.setEnabled(false);
                runCodeButton.setEnabled(false);
            }
        });
    }

    //check if there's an input

    private void handleLexical() {
        String input = compilerInputTextArea.getText();// Get text from JTextPane
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
            syntaxAnalysisButton.setEnabled(true);

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
            semanticAnalysisButton.setEnabled(true);
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

            // Step 3: Parse the input to get the AST list (Abstract Syntax Tree)
            List<AST> astList = parser.parse();

            // Step 4: Initialize the Interpreter (for semantic analysis)
            Interpreter interpreter = new Interpreter();

            // Step 5: Perform semantic analysis on each AST node
            for (AST node : astList) {
                interpreter.interpret(node); // This will throw errors if semantic issues are found
            }

            // Step 6: Output success message to the output area
            compilerOutputTextArea.append("Semantic Analysis Complete: No errors found.\n");

            // Step 7: Display the symbol table
            compilerOutputTextArea.append(interpreter.printSymbolTable());

            // Enable the "Run Code" button if all phases pass
            runCodeButton.setEnabled(true);

        } catch (RuntimeException ex) {
            compilerOutputTextArea.append("Semantic Error: " + ex.getMessage() + "\n");
        }
    }


    private void handleAllPhases() {
        String input = compilerInputTextArea.getText();
        compilerOutputTextArea.setText(""); // Clear the previous output

        try {
            // Step 1: Initialize Lexer with input
            Lexer lexer = new Lexer(input);

            // Step 2: Initialize Parser with Lexer (token stream)
            Parser parser = new Parser(lexer);

            // Step 3: Parse the input to get a list of AST nodes
            List<AST> astList = parser.parse();

            // Step 4: Initialize the Interpreter (for semantic analysis)
            Interpreter interpreter = new Interpreter();

            // Step 5: Perform semantic analysis on each AST node
            for (AST node : astList) {
                interpreter.interpret(node); // Process each node
            }

            // Step 6: Output success message to the output area
            compilerOutputTextArea.append("Lexical, Syntax, and Semantic Analysis Complete: No errors found.\n");

            // Step 7: Append the symbol table to the output
            String symbolTableString = interpreter.printSymbolTable();
            compilerOutputTextArea.append(symbolTableString);

        } catch (RuntimeException ex) {
            compilerOutputTextArea.append("Error: " + ex.getMessage() + "\n");
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
