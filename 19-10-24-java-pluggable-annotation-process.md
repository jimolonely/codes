Pluggable Annotation Processing的讲解与实践

如何自己实现一个lombok。

# 项目结构
先来个简单的测试：

```java
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── jimo
│   │   │           ├── Data.java
│   │   │           ├── Main.java
│   │   │           └── MyProcessor.java
```
代码

Data.java 
```java
package com.jimo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Data {
}
```
Main.java 
```java
@Data
public class Main {

    private int age;
    private String name;

    public static void main(String[] args) {
        new Main();
        System.out.println("yes");
    }
}
```
MyProcessor.java 
```java
package com.jimo;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes(value = {"com.jimo.Data"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("begin");
        for (TypeElement annotation : annotations) {
            if (annotation.getSimpleName().contentEquals("Data")) {
                Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(annotation);
                for (Element element : elementsAnnotatedWith) {
                    String pkg = element.getEnclosingElement().toString();
                    String className = element.getSimpleName().toString();

                    try {
                        rewriteClass(pkg, className);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("1:" + pkg);
                    System.out.println("2:" + className);
                }
            }
            System.out.println("anno:" + annotation);
        }
        System.out.println(roundEnv);
        return true;
    }

    private void rewriteClass(String pkg, String className) throws IOException {
        final JavaFileObject sourceFile = processingEnv.getFiler().createClassFile(pkg + ".NewClass");
//        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(pkg + ".Test");
//        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(pkg + "." + className);
        try (Writer writer = sourceFile.openWriter()) {
            writer.write("package " + pkg + ";");
            writer.write("class Main{");
            writer.write("private int age;");
            writer.write("}");
        }
    }
}
```

## 如何使用

### javac编译法

需要先编译处理器，再编译其他代码
```java
// 编译处理器
src\main\java>javac com\jimo\MyProcessor.java
// 带上处理器
javac -processor com.jimo.MyProcessor com\jimo\Main.java
```
然后会看到编译时输出：
```s
com.jimo.Data
[errorRaised=false, rootElements=[com.jimo.Main], processingOver=false]
[errorRaised=false, rootElements=[], processingOver=true]
警告: 注释处理不适用于隐式编译的文件。
  使用 -implicit 指定用于隐式编译的策略。
1 个警告
```

### maven插件编译法

在插件中声明处理器：
```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.5.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                    <annotationProcessors>
                        <annotationProcessor>
                            com.jimo.MyProcessor
                        </annotationProcessor>
                    </annotationProcessors>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

同样，我们也需要先编译处理器：因为默认，maven编译是放在`target/classes/com/jimo/xxx.class`，所以如下：
（编译命令参考：`javac -d ..\..\..\target\classes\  com\jimo\MyProcessor.java`）
```xml
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── jimo
│   │   │           ├── Data.java
│   │   │           ├── Main.java
│   │   │           └── MyProcessor.java
│   │   └── resources
│   └── test
│       └── java
└── target
    ├── classes
    │   └── com
    │       └── jimo
    │           └── MyProcessor.class
```

然后使用`maven compile`命令，或者在IDEA里调用`Lifecycle-->compile`按钮：

结果：
```java
[INFO] Compiling 3 source files to D:\workspace\test-demos\j-lombok-demo\target\classes
com.jimo.Data
[errorRaised=false, rootElements=[com.jimo.MyProcessor, com.jimo.Data, com.jimo.Main], processingOver=false]
[errorRaised=false, rootElements=[], processingOver=true]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### jar包引入

以上2种方式都是在一个仓库里使用，仅供开发测试，真正投入使用时，还是用jar包的方式好。


# 实践lombok

上面的方法只能生成新的类和class文件，而有时需要修改源代码和class，这就更复杂一些，需要懂得AST（抽象语法树）和编译原理。


安装tools.jar 到本地仓库：因为需要用到里面的AST代码
```shell
mvn install:install-file -Dfile=JDK目录\lib\tools.jar -DgroupId=com.sun -DartifactId=tools -Dversion=1.8 -Dpackaging=jar
```

写处理器代码：

```java
package com.jimo;

import com.google.auto.service.AutoService;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@SupportedAnnotationTypes(value = {"com.jimo.Data"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class CustomProcessor extends AbstractProcessor {
    /**
     * AST
     */
    private JavacTrees trees;
    /**
     * 操作修改AST
     */
    private TreeMaker treeMaker;
    /**
     * 符号封装类，处理名称
     */
    private Names names;
    /**
     * 打印信息
     */
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        trees = JavacTrees.instance(processingEnv);
        final Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        treeMaker = TreeMaker.instance(context);
        names = Names.instance(context);
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("begin");

        final Set<? extends Element> dataAnnotations = roundEnv.getElementsAnnotatedWith(Data.class);

        dataAnnotations.stream().map(element -> trees.getTree(element)).forEach(
                tree -> tree.accept(new TreeTranslator() {
                    @Override
                    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
                        // print method name
                        System.out.println("-------------2");
                        messager.printMessage(Diagnostic.Kind.NOTE, jcMethodDecl.toString());
                        super.visitMethodDef(jcMethodDecl);
                    }

                    @Override
                    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {

                        System.out.println("-------------1");
                        final Map<Name, JCTree.JCVariableDecl> treeMap =
                                jcClassDecl.defs.stream().filter(k -> k.getKind().equals(Tree.Kind.VARIABLE))
                                        .map(tree -> (JCTree.JCVariableDecl) tree)
                                        .collect(Collectors.toMap(JCTree.JCVariableDecl::getName, Function.identity()));

                        treeMap.forEach((k, var) -> {
                            messager.printMessage(Diagnostic.Kind.NOTE, "var:" + k);
                            System.out.println("-------------3");
                            try {
                                // add getter
                                jcClassDecl.defs = jcClassDecl.defs.prepend(getter(var));
                                // add setter
                                jcClassDecl.defs = jcClassDecl.defs.prepend(setter(var));
//                                jcClassDecl.defs.prepend(setter(var));
                            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                                e.printStackTrace();
                                messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                            }
                        });

                        super.visitClassDef(jcClassDecl);
                    }
                })
        );
        return true;
    }

    /**
     * 自定义setter
     */
    private JCTree setter(JCTree.JCVariableDecl var) throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        // 方法级别public
        final JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC);

        final Name varName = var.getName();
        Name methodName = methodName(varName, "set");

        // 方法体
        ListBuffer<JCTree.JCStatement> jcStatements = new ListBuffer<>();
        jcStatements.append(treeMaker.Exec(treeMaker.Assign(
                treeMaker.Select(treeMaker.Ident(names.fromString("this")), varName),
                treeMaker.Ident(varName)
        )));
        final JCTree.JCBlock block = treeMaker.Block(0, jcStatements.toList());

        // 返回值类型void
        JCTree.JCExpression returnType =
                treeMaker.Type((Type) (Class.forName("com.sun.tools.javac.code.Type$JCVoidType").newInstance()));

        List<JCTree.JCTypeParameter> typeParameters = List.nil();

        // 参数
        final JCTree.JCVariableDecl paramVars = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER,
                List.nil()), var.name, var.vartype, null);
        final List<JCTree.JCVariableDecl> params = List.of(paramVars);

        List<JCTree.JCExpression> throwClauses = List.nil();
        // 重新构造一个方法, 最后一个参数是方法注解的默认值，这里没有
        return treeMaker.MethodDef(modifiers, methodName, returnType, typeParameters, params, throwClauses, block,
                null);
    }

    /**
     * 构造驼峰命名
     */
    private Name methodName(Name varName, String prefix) {
        return names.fromString(prefix + varName.toString().substring(0, 1).toUpperCase()
                + varName.toString().substring(1));
    }

    /**
     * 构造getter
     */
    private JCTree getter(JCTree.JCVariableDecl var) {
        // 方法级别
        final JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC);

        // 方法名称
        final Name methodName = methodName(var.getName(), "get");

        // 方法内容
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        statements.append(treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), var.getName())));
        final JCTree.JCBlock block = treeMaker.Block(0, statements.toList());

        // 返回值类型
        final JCTree.JCExpression returnType = var.vartype;

        // 没有参数类型
        List<JCTree.JCTypeParameter> typeParameters = List.nil();

        // 没有参数变量
        List<JCTree.JCVariableDecl> params = List.nil();

        // 没有异常
        List<JCTree.JCExpression> throwClauses = List.nil();

        // 构造getter
        return treeMaker.MethodDef(modifiers, methodName, returnType, typeParameters, params, throwClauses, block,
                null);
    }
}
```

使用[google的auto service](https://github.com/google/auto/tree/master/service)进行配置处理器:

引入依赖：
```xml
        <dependency>
            <groupId>com.google.auto.service</groupId>
            <artifactId>auto-service</artifactId>
            <version>1.0-rc4</version>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.google.auto</groupId>
            <artifactId>auto-common</artifactId>
            <version>0.10</version>
            <optional>true</optional>
        </dependency>
```

注意到上面类中的`@AutoService`注解就是来自这。

这样编译完后，会生成META-INF信息：

```xml
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── jimo
│   │   │           ├── CustomProcessor.java
│   │   │           ├── Data.java
│   │   └── resources
│   └── test
│       └── java
└── target
    ├── classes
    │   ├── META-INF
    │   │   └── services
    │   │       └── javax.annotation.processing.Processor
    │   └── com
    │       └── jimo
    │           ├── CustomProcessor$1.class
    │           ├── CustomProcessor.class
    │           ├── Data.class
```

javax.annotation.processing.Processor这个文本文件里就一句话：
```java 
com.jimo.CustomProcessor
```

接着可以使用`mvn install`安装到本地，让其他项目引入试试。

目标类：

```java
@Data
public class User {
    private int age;
    private String name;
    public int height;
}
```

编译完后：

```java 
public class User {
    private int age;
    private String name;
    public int height;

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public User() {
    }
}
```

