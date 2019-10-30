# .whl is not a supported wheel on this platform

查看支持哪些
```s
import pip; print(pip.pep425tags.get_supported()) 

[('cp35', 'cp35m', 'win32'), ('cp35', 'none', 'win32'), ('py3', 'none', 'win32'), ('cp35', 'none', 'any'), ('py34', 'none', 'any'), ('py33', 'none', 'any'), ('py32', 'none', 'any'), ('py31', 'none', 'any'), ('py30', 'none', 'any')]
```

最好先使用旧一点的稳定版本，因为很多包没来得及更新。
