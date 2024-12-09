# Java MiniCompiler

All four phases work.

Instructions on how to use and use-cases:

Main method within `Application.java`

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
int x = 0;
int x = 5;
```

We cannot redeclare variables. However, this still passes Lexical and Syntax analysis because it only violates Semantics rules.

Scenario C:

Fail Immediately on Lexical

These inputs contain characters or sequences that the lexer cannot recognize.
Example Input:
```
int x = 5 @ 10;
```
