# Maven手记

## 打包成可执行jar

官方文档：https://maven.apache.org/plugins/maven-jar-plugin/ 

在之前可能会遇到找不到主类的问题：
```shell
[jimo@jimo-pc target]$ java test-app-1.0-SNAPSHOT.jar
Error: Could not find or load main class test-app-1.0-SNAPSHOT.jar
[jimo@jimo-pc target]$ java -jar test-app-1.0-SNAPSHOT.jar
no main manifest attribute, in test-app-1.0-SNAPSHOT.jar
```
解决办法：需要在pom.xml里配置
```java
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.0.2</version>
        <configuration>
          <archive>
            <manifestFile>${project.build.outputDirectory}/META-INF/MANIFEST.MF</manifestFile>
          </archive>
        </configuration>
        ...
      </plugin>
    </plugins>
  </build>
  ...
</project>
```
当然这种方法需要自定义清单文件，更简单的是直接定义入口类：
```java
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.0.2</version>
            <configuration>
                <archive>
                    <manifest>
                        <mainClass>com.jimo.App</mainClass>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
    </plugins>
  </build>
```
其中com.jimo.App包含main方法，是整个程序的入口。

然后重新构建jar包再执行：
```shell
[jimo@jimo-pc test-app]$ mvn clean install
[jimo@jimo-pc target]$ java -jar test-app-1.0-SNAPSHOT.jar
Hello World!
```