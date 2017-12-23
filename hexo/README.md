飞快搭建hexo+github博客.

```shell
# 安装hexo
[jimo@jimo-pc workspace]$ hexo init myblog
[jimo@jimo-pc workspace]$ cd myblog
[jimo@jimo-pc workspace]$ npm install
```

配置基本信息:_config.yml
```shell
title: 在我的世界
subtitle: 你是谁,从哪里来,为什么你活着?
description: who,where,what
author: Jackpler
language: zh-Hans
...
# 配置git
deploy:
  type: git
  repo: git@github.com:jimolonely/jimolonely.github.io.git
  branch: master
  # message: {{ now('YYYY-MM-DD HH:mm:ss') }} 要上会报错
  name: jimolonely
  email: xxx@foxmail.com
```
配置标签:/scaffolds/post.md
```yml
---
title: {{ title }}
date: {{ date }}
tags:  # 定义了我们所有标签
- java
- hexo
- git
---
```
如何使用标签呢?在每篇文章内定义:
```yml
---
title: Hello World
tags: [hexo] # 多个用逗号隔开
---
```

运行起来看:
```shell
$ hexo server
```
写一篇新文章: 一篇文件名为deploy-hexo-on-github的位于_post/hexo/deploy-hexo-on-github.md的文章
```shell
# hexo new title -p dir
[jimo@jimo-pc myblog]$ hexo new deploy-hexo-on-github -p hexo/deploy-hexo-on-github
```

关键的:部署到github.

需要先安装插件:
```shell
[jimo@jimo-pc myblog]$ npm install hexo-deployer-git --save
```
前提:电脑有github,最好是用SSH链接的,github上也有一个项目.
```shell
$ hexo deploy
```
ok.