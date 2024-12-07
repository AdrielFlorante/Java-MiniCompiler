import java.util.HashMap;
import java.util.Map;

class SymbolTable {
    private final Map<String, String> variables = new HashMap<>();

    // Declare a new variable in the symbol table
    public void declare(String name, String value) {
        if (variables.containsKey(name)) {
            throw new RuntimeException("Variable already declared: " + name);
        }
        variables.put(name, value);
    }

    // Get the value of a variable
    public String get(String name) {
        if (!variables.containsKey(name)) {
            throw new RuntimeException("Variable not found: " + name);
        }
        return variables.get(name);
    }

    // Update the value of an existing variable
    public void update(String name, String value) {
        if (!variables.containsKey(name)) {
            throw new RuntimeException("Variable not found: " + name);
        }
        variables.put(name, value);
    }

    // Print all declared variables
    public void printSymbolTable() {
        System.out.println("Symbol Table:");
        variables.forEach((key, value) -> System.out.println(key + " = " + value));
    }
}
