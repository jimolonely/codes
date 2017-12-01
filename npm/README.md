# npm
一些使用npm的记录。

## EACCES error
我是使用了sudo命令安装了一些全局包，然后普通用户就没有写的权限了。
根据官方做法：[https://docs.npmjs.com/getting-started/fixing-npm-permissions](https://docs.npmjs.com/getting-started/fixing-npm-permissions)

我没有采用修改文件夹所属者的方法，而采用了声明前缀的隔离法：
```shell
 mkdir ~/.npm-global
 npm config set prefix '~/.npm-global'
 export PATH=~/.npm-global/bin:$PATH
 source ~/.profile
# 结果
 [jimo@jimo-pc jshint]$ pwd
/home/jimo/.npm-global/lib/node_modules/jshint
```
这样，所有的全局包都会安装在这里了。

## 安装dva项目时出错
地址：[https://ant.design/docs/react/practical-projects-cn](https://ant.design/docs/react/practical-projects-cn)

安装dva-cli没问题，但无法安装antd，我的做法是：
```shell
# 1.删除node_modules
rm node_modules/ -r
# 2.清除缓存
npm cache clean --force
# 3.重装模板
npm install all module
npm install
# 4.再安装包
npm install antd --save
npm install babel-plugin-import --save
```
