public class VariableCompiler {
    public static void main(String[] args) {
        String input = "int x = 5 @ 10;";

        // Step 1: Tokenize
        Lexer lexer = new Lexer(input);

        // Step 2: Interpret multiple statements
        Interpreter interpreter = new Interpreter();
        Parser parser = new Parser(lexer);

        while (true) {
            try {
                // Parse a single statement
                AST node = parser.parse();

                // Stop if EOF is reached
                if (node == null) break;

                // Interpret the parsed statement
                interpreter.interpret(node);
            } catch (RuntimeException e) {
                System.err.println("Error: " + e.getMessage());
                break;
            }
        }

        // Step 3: Print Symbol Table
        interpreter.printSymbolTable();
    }
}
