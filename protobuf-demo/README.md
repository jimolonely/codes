本文将使用protobuf来进行序列化和反序列化，和json对比，看看各方面性能差别。

# protobuf简介

# protobuf使用

## 编写proto文件



## 根据proto文件生成代码

1. 下载proto的客户端，编译proto文件（这里不说）
2. 通过maven直接生成

插件：
[https://www.xolstice.org/protobuf-maven-plugin/usage.html](https://www.xolstice.org/protobuf-maven-plugin/usage.html)

```xml
<dependencies>
    <dependency>
        <groupId>com.google.protobuf</groupId>
        <artifactId>protobuf-java</artifactId>
        <version>${protobuf.version}</version>
    </dependency>
</dependencies>

<build>
    <extensions>
        <extension>
            <groupId>kr.motd.maven</groupId>
            <artifactId>os-maven-plugin</artifactId>
            <version>1.6.2</version>
        </extension>
    </extensions>
    <plugins>
        <plugin>
            <groupId>org.xolstice.maven.plugins</groupId>
            <artifactId>protobuf-maven-plugin</artifactId>
            <version>0.6.1</version>
            <configuration>
                <protocArtifact>com.google.protobuf:protoc:3.6.1:exe:${os.detected.classifier}</protocArtifact>
                <pluginId>protoc-java</pluginId>
                <!--读取proto文件路径-->
                <protoSourceRoot>${project.basedir}/src/main/resources</protoSourceRoot>
                <!--生产的java文件路径-->
                <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>compile</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
然后在plugin里运行proto compile即可

## 使用proto序列化


