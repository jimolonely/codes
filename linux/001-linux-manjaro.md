#  manjaro
讲了manjaro如何配置.
## 配置鏡像

```shell
[jimo@jimo-pc ~]$ nano /etc/pacman-mirrors.conf 
OnlyCountry=China

[jimo@jimo-pc ~]$ sudo pacman-mirrors -g
.: Info Downloading mirrors from repo.manjaro.org
.: Info Using custom mirror file
.: Info Querying mirrors - This may take some time
   0.490 China          : https://mirrors.ustc.edu.cn/manjaro/
   0.291 China          : http://ftp.cuhk.edu.hk/pub/Linux/manjaro/
   1.109 China          : ftp://ftp.cuhk.edu.hk/pub/Linux/manjaro/
   0.131 China          : http://mirrors.tuna.tsinghua.edu.cn/manjaro/
   0.279 China          : https://mirrors.zju.edu.cn/manjaro/
.: Info Writing mirror list
   China           : http://mirrors.tuna.tsinghua.edu.cn/manjaro/stable
   China           : https://mirrors.zju.edu.cn/manjaro/stable
   China           : https://mirrors.ustc.edu.cn/manjaro/stable
   China           : http://ftp.cuhk.edu.hk/pub/Linux/manjaro/stable
.: Info Mirror list generated and saved to: /etc/pacman.d/mirrorlist
```
then
```shell
# /etc/pacman.conf 
[archlinuxcn] 
SigLevel = Optional TrustedOnly 
Server = https://mirrors.ustc.edu.cn/archlinuxcn/$arch 
```
then
```shell
sudo pacman -Syy && sudo pacman -S archlinuxcn-keyring
```
##  中文輸入法
有了上面的鏡像，可以配置下面的輸入法。
```shell
sudo pacman -S fcitx-sogoupinyin
sudo pacman -S fcitx-im # 全部安装
sudo pacman -S fcitx-configtool # 图形化配置工具
```
then
```shell
nano ~/.xprofile 
export GTK_IM_MODULE=fcitx
export QT_IM_MODULE=fcitx
export XMODIFIERS="@im=fcitx"
```

## VIM
```shell
[jimo@jimo-pc ~]$ sudo pacman -S vim
```
## Chrome
通過源安裝太慢了。

[jimo@jimo-pc ~]$ sudo pacman -Sy google-chrome

但可以使用yaourt安裝
```shell
#首先安裝yaourt
sudo pacman -S yaourt
# 再安裝chrome
yaourt google-chrome
```
不過可以使用Chromium
```shell
[jimo@jimo-pc ~]$ sudo pacman -Sy chromium
```

## 快捷鍵設置
在鍵盤設置裏进行快捷键设置

| 功能 | 快捷键 | 命令 |
| --- | --- | --- |
| 截图 | Ctrl+Alt+A | xfce4-screenshooter -rc |
| 查找程序 | super | xfce4-appfinder |
| 锁屏 | super+L | xflock4 |

下面是常用的默认快捷键:可以在设置编辑器里面看

| 功能 | 快捷键 |
| --- | --- |
| 最大化窗口 | Alt+F10    |
| 最小化窗口 | Alt+F9 |
| 切换窗口 | Alt+Tab |
| 显示桌面 | Ctrl+Alt+D |
| 关闭窗口 | Alt+F4 | 

## 安装mariadb
用mariadb替代mysql

[参考文档](https://downloads.mariadb.org/mariadb/repositories/#mirror=tuna&distro=Arch+Linux&distro_release=opensuse42-amd64--opensuse42)
[文档二](https://wiki.archlinux.org/index.php/MySQL)

### 安装
按下面的额顺序：

先安装：
```shell
sudo pacman -S mariadb
```
设置开机启动:
```shell
systemctl enable mysqld.service
```
开启服务：
```shell
sudo mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
```
启动数据库：
```shell
sudo systemctl start mysqld.service
```
自定义安装：
```shell
sudo mysql_secure_installation
```
停止服务：
```shell
sudo systemctl stop mysqld.service
```
### 配置
修改编码：/etc/mysql/my.cnf
```shell
[client]
default-character-set = utf8mb4

[mysqld]
collation_server = utf8mb4_unicode_ci
character_set_client = utf8mb4
character_set_server = utf8mb4
skip-character-set-client-handshake

[mysql]
default-character-set = utf8mb4
```
然后查看：
```shell
MariaDB [(none)]> show variables like 'character%';
+--------------------------+----------------------------+
| Variable_name            | Value                      |
+--------------------------+----------------------------+
| character_set_client     | utf8mb4                    |
| character_set_connection | utf8mb4                    |
| character_set_database   | utf8mb4                    |
| character_set_filesystem | binary                     |
| character_set_results    | utf8mb4                    |
| character_set_server     | utf8mb4                    |
| character_set_system     | utf8                       |
| character_sets_dir       | /usr/share/mysql/charsets/ |
+--------------------------+----------------------------+
```

## 其他
按照通用[系統構建](https://github.com/jimolonely/myshell/blob/master/build-mysystem.md)

### QQ
可一使用deepin的qq。
使用命令：
```shell
yaourt -Sy deepin.com.qq.im
```
假如安装不成功，可以在manjaro的包管理器中选择库-extra模块，搜索qq找到安装。

不过我的电脑上有些字体乱码。

### openjdk源码
直接在库里面找到下载即可

### 性能监控
包括网速，内存，CPU等，Xface自带有多面板功能，可以添加，具体步骤如下：

设置--面板--项目--+，同样可以自定义面板位置和大小，都在外观选项里。

### 自带单词查询
使用look命令查询单词：
```shell
[jimo@jimo-pc myshell]$ look car
look: /usr/share/dict/words: 没有那个文件或目录
```
这个字典文件怎么下载呢？很简单，在软件管理里搜索words，即可安装，安装后有很多语言的单词：
```shell
/usr/share/dict/american-english
/usr/share/dict/british
/usr/share/dict/british-english
/usr/share/dict/catala
/usr/share/dict/catalan
/usr/share/dict/finnish
/usr/share/dict/french
/usr/share/dict/german
/usr/share/dict/italian
/usr/share/dict/ngerman
/usr/share/dict/ogerman
/usr/share/dict/spanish
/usr/share/dict/usa
/usr/share/licenses/words/copyright-ca
/usr/share/licenses/words/copyright-de
/usr/share/licenses/words/copyright-en
/usr/share/licenses/words/copyright-es
/usr/share/licenses/words/copyright-fi
/usr/share/licenses/words/copyright-fr
/usr/share/licenses/words/copyright-it
```
现在我要查询zero开头的单词：
```shell
[jimo@jimo-pc myshell]$ look zer
zero
zeroed
zeroes
zeroing
zero's
zeros
zeroth
```
