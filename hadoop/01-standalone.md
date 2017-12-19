
一个单节点的例子：计数文件中的字符个数。

使用hadoop的例子。

```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ mkdir input
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ ls
bin  include  lib      LICENSE.txt  README.txt  share
etc  input    libexec  NOTICE.txt   sbin
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ cp etc/hadoop/*.xml input
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar wordcount input output
....
```
结果：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ cat output/* | head -n 5
"*"	19
"AS	8
"License");	8
"alice,bob	19
"clumping"	1
```

可能的错误：
1. 已存在output目录。
2. 连不上localhost