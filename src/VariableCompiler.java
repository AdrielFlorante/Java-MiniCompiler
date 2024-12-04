import java.util.Scanner;

public class VariableCompiler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Interpreter interpreter = new Interpreter(); // Symbol table persists across runs

        System.out.println("MiniCompiler");
        System.out.println("Type 'exit' to quit.\n");

        while (true) {
            System.out.print("Input: ");
            String input = scanner.nextLine();

            // Exit condition
            if (input.trim().equalsIgnoreCase("exit")) {
                System.out.println("Exiting MiniCompiler. Goodbye!");
                break;
            }

            // Step 1: Tokenize
            Lexer lexer = new Lexer(input);
            Parser parser = new Parser(lexer);

            try {
                // Parse and interpret multiple statements
                while (true) {
                    AST node = parser.parse();

                    // Stop if EOF is reached
                    if (node == null) break;

                    // Interpret the parsed statement
                    interpreter.interpret(node);
                }
            } catch (RuntimeException e) {
                System.err.println("Error: " + e.getMessage());
            }

            // Step 2: Print Symbol Table after each input
            System.out.println("Current Symbol Table:");
            interpreter.printSymbolTable();
            System.out.println();
        }

        scanner.close(); // Close the scanner to free resources
    }
}
