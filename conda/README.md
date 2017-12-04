# conda
[conda](https://conda.io/docs/),一个环境管理工具。

# 迷你安装
[https://conda.io/miniconda.html](https://conda.io/miniconda.html)

# 基本使用
```shell
# 安装包：
jimo@jimo-slave:~$ conda create -n magenta python=2.7 jupyter
# 进入该环境
jimo@jimo-slave:~$ source activate magenta
(magenta) jimo@jimo-slave:~$ 
# 离开环境
(magenta) jimo@jimo-slave:~$ source deactivate
jimo@jimo-slave:~$ ls
```

