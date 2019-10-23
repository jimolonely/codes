# 使用IDEA部署jar包到maven仓库

1. 在项目的pom里声明要部署的仓库
  ```xml
  <distributionManagement>
      <repository>
      <id>libs-releases-local</id>
      <name>Release Repository</name>
      <url>http://xxxx/libs-releases-local</url>
   </repository>
   <snapshotRepository>
      <id>libs-snapshots-local</id>
      <name>Snapshot Repository</name>
      <url>http://xxxx/libs-snapshots-local</url>
      </snapshotRepository>
   </distributionManagement>
  ```

2. maven的settings.xml中配置servers：
    1. 这里的用户名密码是 maven仓库的
    2. 这里的id要和上面的id相同
    ```xml
      <servers>
          <server>    
            <id>libs-releases-local</id>    
            <username>用户名</username>
            <password>密码</password>  
          </server>  
          <server>    
            <id>libs-snapshots-local</id>    
            <username>用户名</username>
            <password>密码</password>  
          </server>    
        </servers>
    ```
3. 在IDEA的maven小窗口：Lifecycle--deploy


  
  
