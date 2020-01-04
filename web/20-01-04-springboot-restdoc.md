
本文对spring的原生项目：[Spring REST Doc](https://spring.io/projects/spring-restdocs) 在springboot中的使用做一个总结。


其实官方已经给出了一个demo:[https://spring.io/guides/gs/testing-restdocs/](https://spring.io/guides/gs/testing-restdocs/)

但是，当我们的controller有其他依赖时，使用 `@WebMvcTest(HomeController.class)`注解并不能注入。

因此，下面是我成功的版本：

# 依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.restdocs</groupId>
            <artifactId>spring-restdocs-mockmvc</artifactId>
            <scope>test</scope>
        </dependency>
```

# 测试与生成文档类

```java
import com.alibaba.fastjson.JSON;
import com.jimo.model.param.UserParam;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Resource
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void login() throws Exception {
        final UserParam param = new UserParam();
        param.setPassword("xxx");
        param.setUsername("jimo");
        this.mockMvc.perform
                (
                        post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JSON.toJSONBytes(param))
                )
                .andDo(document("login"));
    }
}
```
运行完了会在 `target/generated-snippets`下生成一个login的文件夹包含 asciidoctor格式的文档：

```s
$ tree generated-snippets/
generated-snippets/
└── login
    ├── curl-request.adoc
    ├── http-request.adoc
    ├── http-response.adoc
    ├── httpie-request.adoc
    ├── request-body.adoc
    ├── request-fields.adoc
    ├── response-body.adoc
    └── response-fields.adoc

1 directory, 8 files
```

# 将asciidoc转为html

加入一个插件：

```xml
<plugin>
    <groupId>org.asciidoctor</groupId>
    <artifactId>asciidoctor-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>generate-docs</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>process-asciidoc</goal>
            </goals>
            <configuration>
                <sourceDocumentName>index.adoc</sourceDocumentName>
                <backend>html</backend>
                <attributes>
                    <snippets>${project.build.directory}/generated-snippets</snippets>
                </attributes>
            </configuration>
        </execution>
    </executions>
</plugin>
```

然后在项目中建立一个组合文档： `src/main/asciidoc/index.adoc`

```adoc
= Getting Started With Spring REST Docs

This is an example output for a service running at http://localhost:8080:

.request
include::{snippets}/login/http-request.adoc[]

.response
include::{snippets}/login/http-response.adoc[]

As you can see the format is very simple, and in fact you always get the same message.
```

再运行 `mvn package`就可以看到 `target/generated-docs`下的html文件：

```s
$ tree generated-docs/
generated-docs/
└── index.html

0 directories, 1 file
```

# spring restdoc文档

[https://docs.spring.io/spring-restdocs/docs/current/reference/html5/#introduction](https://docs.spring.io/spring-restdocs/docs/current/reference/html5/#introduction)






