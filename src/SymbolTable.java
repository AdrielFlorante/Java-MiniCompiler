import java.util.HashMap;
import java.util.Map;

// Symbol Table
class SymbolTable {
    private final Map<String, Integer> variables = new HashMap<>();

    public void declare(String name, int value) {
        if (variables.containsKey(name)) {
            throw new RuntimeException("Variable already declared: " + name);
        }
        variables.put(name, value);
    }

    public int get(String name) {
        if (!variables.containsKey(name)) {
            throw new RuntimeException("Variable not found: " + name);
        }
        return variables.get(name);
    }

    public void printSymbolTable() {
        System.out.println("Symbol Table:");
        variables.forEach((key, value) -> System.out.println(key + " = " + value));
    }
}