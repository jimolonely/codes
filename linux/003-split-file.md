分割与合并文件操作.

## 分割与合并文件
有时候大文件分割后更方便传输。
以一张图片为例：
分割：默认分割
```shell
[jimo@jimo-pc shell]$ ls -lh
总用量 1.5M
-rwxrwxrwx 1 jimo jimo 1.5M 9月  17 20:31 1.png
[jimo@jimo-pc shell]$ split 1.png 
[jimo@jimo-pc shell]$ ls -lh
总用量 2.9M
-rwxrwxrwx 1 jimo jimo 1.5M 9月  17 20:31 1.png
-rw-r--r-- 1 jimo jimo 254K 11月 12 18:31 xaa
-rw-r--r-- 1 jimo jimo 259K 11月 12 18:31 xab
-rw-r--r-- 1 jimo jimo 264K 11月 12 18:31 xac
-rw-r--r-- 1 jimo jimo 238K 11月 12 18:31 xad
-rw-r--r-- 1 jimo jimo 270K 11月 12 18:31 xae
-rw-r--r-- 1 jimo jimo 184K 11月 12 18:31 xaf
```
合并：
```shell
[jimo@jimo-pc shell]$ ls -lh
总用量 4.4M
-rwxrwxrwx 1 jimo jimo 1.5M 9月  17 20:31 1.png
-rw-r--r-- 1 jimo jimo 1.5M 11月 12 18:32 2.png
-rw-r--r-- 1 jimo jimo 254K 11月 12 18:31 xaa
-rw-r--r-- 1 jimo jimo 259K 11月 12 18:31 xab
-rw-r--r-- 1 jimo jimo 264K 11月 12 18:31 xac
-rw-r--r-- 1 jimo jimo 238K 11月 12 18:31 xad
-rw-r--r-- 1 jimo jimo 270K 11月 12 18:31 xae
-rw-r--r-- 1 jimo jimo 184K 11月 12 18:31 xaf
```
指定每个文件大小：
```shell
[jimo@jimo-pc shell]$ split 1.png -b 500KB 
[jimo@jimo-pc shell]$ ls -lh
总用量 4.4M
-rwxrwxrwx 1 jimo jimo 1.5M 9月  17 20:31 1.png
-rw-r--r-- 1 jimo jimo 1.5M 11月 12 18:32 2.png
-rw-r--r-- 1 jimo jimo 489K 11月 12 18:35 xaa
-rw-r--r-- 1 jimo jimo 489K 11月 12 18:35 xab
-rw-r--r-- 1 jimo jimo 489K 11月 12 18:35 xac
-rw-r--r-- 1 jimo jimo  972 11月 12 18:35 xad
```
类似的命令可以看csplit