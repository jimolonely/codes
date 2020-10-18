# BraceDemo01

括号demo

编辑BraceDemo.jj, 然后使用 javacc编译。

```java
options {
    STATIC = false;
}

PARSER_BEGIN(BraceDemo)

package com.jimo.parser.demo01;

public class BraceDemo {

    public static void main(String[] args) throws Exception{
        final BraceDemo b = new BraceDemo(System.in);
        b.input();
    }
}
PARSER_END(BraceDemo)

void input():
{}
{
    MatchedBraces() ("\n" | "\r" | "\n\r")* <EOF>
}

void MatchedBraces():
{}
{
    "{" [ MatchedBraces() ] "}"
}
```

# BraceDemo02

再改改，支持空格：

```java
options {
    STATIC = true;
}

PARSER_BEGIN(BraceDemo)

package com.jimo.parser.demo01;

public class BraceDemo {

    public static void main(String[] args) throws Exception{
        final BraceDemo b = new BraceDemo(System.in);
        b.input();
    }
}
PARSER_END(BraceDemo)

SKIP : {
    " "
    | "\t"
    | "\n"
    | "\r"
}

void input():
{}
{
    MatchedBraces() <EOF>
}

void MatchedBraces():
{}
{
    "{" [ MatchedBraces() ] "}"
}
```

# BraceDemo03

计算括号个数


