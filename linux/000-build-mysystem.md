# 开始
当然是如何打造个性化系统,一般可以按以下步骤来.
## 常用文件夹
```shell
sudo mkdir ~/software/source -pv
sudo mkdir ~/software/bin -pv
mkdir ~/knowme/books -pv
mkdir ~/workspace/Git -pv
mkdir ~/backup
sudo apt-get update
```

## Install git
```shell
sudo apt-get install git -y

git config --global user.name "jimolonely"
git config --global user.email 15982406684@163.com
```
### 配置保存密码
#### 方法1
```shell
git config --global credential.helper store
```
#### 方法2
在~目录下创建.gitconfig文件，写入：
```shell
[credential]    
    helper = store
```

## 开发语言环境相关

### Install java
```shell
sudo apt-get install default-jdk -y
java -version
```

### python
(一般都自带了)
#### 更换pypi镜像地址
在~/.pip/pip.conf下配上阿里云的：
```shell
[global]
index-url = http://mirrors.aliyun.com/pypi/simple/
[install]
trusted-host=mirrors.aliyun.com
```

### maven
```shell
sudo apt-get install maven
mvn -v
```
然后修改为阿里云的镜像，在/etc/maven/settings.xml
```
<mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
</mirror>
```

## 数据库相关

### 1. dbeaver

### 2. mysql


## 开发工具

### 1.eclipse

### 2.intellij

### 3.vim

#### 3.1 clone vimrc
```shell
cd ~/workspace/Git/
git clone https://github.com/jimolonely/myshell.git
cp myshell/vim/.vimrc ~/.vimrc
cp -r myshell/vim/.vim/ ~
# 安装vbundle
git clone https://github.com/VundleVim/Vundle.vim.git ~/.vim/bundle/Vundle.vim
```
不过还没完，需要进入vim安装插件，在正常模式输入：
```shell
:BundleInstall
```
等安装完成后进入~/.vim/bundle/YouCompleteMe安装
```shell
cd ~/.vim/bundle/YouCompleteMe
./install.py --all
```


### 4.vscode


## 辅助工具

### 1.keepassx ,用于管理密码

### 2.baidupcs
一个百度云命令行工具，地址：https://github.com/GangZhuo/BaiduPCS

### 3.浏览器
#### 3.1chrome
因为chrome有同步，所以不用备份

#### 3.2firefox


## 备份
只需要备份工作空间即可，将workspace打包然后放到backup目录：
```shell
tar -cf workspace.tar ~/workspace/
mv workspace.tar ~/backup/ -u
```





