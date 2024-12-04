# Java MiniCompiler

All four phases work.

Instructions on how to use and use-cases:

We do not have UI or a robust method for inputting language yet.

Main method within `VariableCompiler.java`

Valid input: `"int x = 5; int y = 10;"`

Expected Output:

```
Symbol Table:
x = 5
y = 10
```

Scenario A:

Accepted in Lexical Only, but Fails Syntax

These inputs are tokenized correctly (valid lexical elements) but fail during syntax analysis due to structural issues.
Example Input:
```
int x 5;
```

Scenario B:

Pass Lexical and Syntax, but Fail Semantics

These inputs are valid syntactically, but the semantics (meaning or rules of usage) are incorrect or unsupported.
Example Input:
```
int x = y;
```

Scenario C:

Fail Immediately on Lexical

These inputs contain characters or sequences that the lexer cannot recognize.
Example Input:
```
int x = 5 @ 10;
```

For Improving Lexical, Syntax, and Semantic Analysis

Here are some suggestions to enhance each phase of the compiler

By yours truly, Uncle GPT:

    Lexical Analysis:
        Add error recovery for unknown tokens (e.g., skipping invalid characters instead of throwing an exception).
        Expand the token set to support additional keywords or symbols as needed.

    Syntax Analysis:
        Add support for more complex expressions (e.g., int x = 5 + 2;).
        Detect missing or misplaced tokens more robustly (e.g., unmatched parentheses or semicolons).

    Semantic Analysis:
        Implement a type-checking system.
        Ensure variables are declared before use and not redeclared in the same scope.
        Extend the symbol table to track variable types and scope.


"Proof of concept." - A
