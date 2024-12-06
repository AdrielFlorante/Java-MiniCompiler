public class VariableCompiler {
    private Interpreter interpreter;
    private Parser parser;
    private Lexer lexer;

    // Initialize components
    public void initialize(String input) {
        lexer = new Lexer(input);
        parser = new Parser(lexer);
        interpreter = new Interpreter();
    }

    // Lexical Analysis Phase
    public void lexicalAnalysis() {
        try {
            while (true) {
                Token token = lexer.getNextToken();
                if (token.type == TokenType.EOF) break;
                System.out.println("Token: " + token);
            }
        } catch (RuntimeException e) {
            System.err.println("Lexical Error: " + e.getMessage());
        }
    }

    // Syntax Analysis Phase
    public void syntaxAnalysis() {
        try {
            while (true) {
                AST node = parser.parse();
                if (node == null) break;
                System.out.println("AST Node: " + node);
            }
        } catch (RuntimeException e) {
            System.err.println("Syntax Error: " + e.getMessage());
        }
    }

    // Semantic Analysis Phase
    public void semanticAnalysis() {
        try {
            while (true) {
                AST node = parser.parse();
                if (node == null) break;
                interpreter.interpret(node);
            }
            interpreter.printSymbolTable();
        } catch (RuntimeException e) {
            System.err.println("Semantic Error: " + e.getMessage());
        }
    }
}
