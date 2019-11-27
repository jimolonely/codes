

maven多模块项目，只想安装某一个子模块。

```s
mvn install -pl submodule-dir -am
```

* `-pl`: 代表执行项目project
* `-am`: 代表自动处理依赖

