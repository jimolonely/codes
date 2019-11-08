spring boot如何集成mybatis plus并配备多数据源+mybatis

mybatis-plus: [https://mp.baomidou.com/guide/quick-start.html](https://mp.baomidou.com/guide/quick-start.html)

多数据源支持：[https://github.com/baomidou/dynamic-datasource-spring-boot-starter](https://github.com/baomidou/dynamic-datasource-spring-boot-starter)

# 代码结构
先看下项目结构
```java
.
├── config
│   ├── web-beta
│   │   ├── application.yml
│   │   └── log4j.properties
│   ├── web-dev
│   │   ├── application.yml
│   │   └── log4j.properties
│   └── web-online
│       ├── application.yml
│       └── log4j.properties
├── java
│   └── com
│       └── jimo
│                   └── admin
│                       ├── AdminApplication.java
│                       ├── mapper
│                       │   ├── admin
│                       │   │   └── AdminUserMapper.java
│                       │   └── web
│                       │       └── WebUserMapper.java
│                       ├── model
│                       │   └── db
│                       │       ├── admin
│                       │       │   └── User.java
│                       │       └── web
│                       │           └── User.java
│                       └── service
│                           └── AdminUserService.java
└── resources
    ├── mappers
    │   ├── admin
    │   │   └── AdminUserMapper.xml
    │   └── web
    │       └── WebUserMapper.xml
    └── mybatis-config.xml
```

# pom依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.jimo</groupId>
    <artifactId>admin</artifactId>
    <version>0.0.1</version>
    <name>admin</name>
    <description> admin system</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j</artifactId>
            <version>1.3.8.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.2.0</version>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
            <version>2.5.7</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-actuator</artifactId>
        </dependency>

        <!--        <dependency>-->
        <!--            <groupId>org.mybatis.spring.boot</groupId>-->
        <!--            <artifactId>mybatis-spring-boot-starter</artifactId>-->
        <!--            <version>2.1.0</version>-->
        <!--        </dependency>-->

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.20</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.10</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.60</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>/admin</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
        <resources>
            <resource>
                <!--根据profiles参数 导入测试或生产环境配置文件-->
                <directory>src/main/config/${profiles.activation}</directory>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
        </resources>
    </build>

    <!--配置环境的profile-->
    <profiles>
        <!--开发环境-->
        <profile>
            <id>web-dev</id>
            <properties>
                <profiles.activation>web-dev</profiles.activation>
            </properties>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
        </profile>

        <profile>
            <id>web-beta</id>
            <properties>
                <profiles.activation>web-beta</profiles.activation>
            </properties>
        </profile>

        <profile>
            <id>web-online</id>
            <properties>
                <profiles.activation>web-online</profiles.activation>
            </properties>
        </profile>
    </profiles>
</project>

```

# 配置

```yml
spring:
  datasource:
    dynamic:
      primary: druid-admin
      druid:
        #初始化大小,最小,最大
        initial-size: 5
        min-idle: 5
        max-active: 20
        #配置获取连接等待超时的时间
        max-wait: 60000
        #配置间隔多久才进行一次检测,检测需要关闭的空闲连接,单位是毫秒
        time-between-eviction-runs-millis: 60000
        #配置一个连接在池中最小生存的时间,单位是毫秒
        min-evictable-idle-time-millis: 300000
        validation-query: SELECT 1 FROM DUAL
        test-while-idle: true
        test-on-borrow: false
        test-on-return: false
        #打开PSCache,并且指定每个连接上PSCache的大小
        pool-prepared-statements: true
        max-pool-prepared-statement-per-connection-size: 20
        #配置监控统计拦截的filters,去掉后监控界面sql无法统计,'wall'用于防火墙
        filters: stat,wall # 注意这个值和druid原生不一致，默认启动了stat,wall
        filter:
          log4j:
            enabled: true
          wall:
            config:
              # 允许多个语句一起执行
              multi-statement-allow: true
            enabled: true
            db-type: mysql
          stat:
            enabled: true
            db-type: mysql
      datasource:
        druid-admin:
          url: jdbc:mysql://host:3306/db_admin?allowMultiQueries=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: test
          password: test
          driver-class-name: com.mysql.cj.jdbc.Driver
        druid-web:
          url: jdbc:mysql://host:3306/db_web?allowMultiQueries=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: test
          password: test
          driver-class-name: com.mysql.cj.jdbc.Driver

mybatis-plus:
  mapper-locations: classpath*:/mappers/**/*.xml
  config-location: classpath:mybatis-config.xml

```

# 代码

## 数据表对应的实体类

User.java (`@TableName("t_user")`声明表名，否则就是类名小写)
```java
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("t_user")
public class User {
    /**
     * 主键
     */
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String headIcon;
    private String state;
}
```

## 定义mapper

AdminUserMapper.java(`@Mapper`不能少)
```java
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jimo.admin.model.db.admin.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("druid-admin")
public interface AdminUserMapper extends BaseMapper<User> {
}
```



# 使用自定义的sql语句

我们依然可以自定义自己的xml文件，写自己的方法：

配置xml：
```yml
mybatis-plus:
  mapper-locations: classpath*:/mappers/**/*.xml
  config-location: classpath:mybatis-config.xml
```

# 主键递增问题

默认是一个随机的数，下面设置递增：
```java
    @TableId(type = IdType.AUTO)
    private Long id;
```

# 分页时total总为0的问题

```java
    /**
     * 分页插件,解决分页时total=0问题
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
```








