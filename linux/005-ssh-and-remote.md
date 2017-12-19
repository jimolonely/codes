讲ssh和远程桌面相关.
## 1.远程桌面
可以采用rdesktop命令，连linux，windows都没问题。

就windows而言，通常可以挂载本地文件夹：
```shell
# -r disk:Jimo=/home/jimo/xxx:挂载本地文件夹
# sound:local 写上比较好
$ rdesktop -u xxx ip:port -p xxxx -r sound:local -r disk:Jimo=/home/jimo/xxx
```
还需要注意的就是密码，当然可以不指定，等到出现界面时才输，在命令行上输密码需要转义，比如!,需要写成：\!,
否则会被当成shell命令执行

## 2.ssh
对于远程的云服务器,需要ssh链接.
```shell
$ ssh username@ip
```
通过ssh传文件到远程linux服务器:
```shell
$ scp localfile username@ip:/remote_path
# 例如
$ scp apache8.0.tar root@119.1.1.1:/root/webapp
```