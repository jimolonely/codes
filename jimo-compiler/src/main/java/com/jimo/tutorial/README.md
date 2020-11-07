
# 师出同门

```java
program: block
block: {decls stmts}
decls: decls decl | E;
decl: type id;
type: type [num] | basic
stmts: stmts stmt | E

stmt: loc=bool;
    | if (bool) stmt
    | if (bool) stmt else stmt
    | while(bool) stmt
    | do stmt while(bool);
    | break;
    | block
loc: loc [bool] | id
```