# Asciidoc
本文将要说明如何快速应对新知识的摄取方式.比如如何将Asciidoc转化为pdf.

## 1.是什么
asciidoc是什么? google一下:[http://asciidoc.org/](http://asciidoc.org/)

就是类似于markdown的一种文本编辑语言,看到github上有用这种方式写书的,需要学习下.

## 2.怎么做
先安装[asciidoc-pdf](https://github.com/asciidoctor/asciidoctor-pdf):
```shell
# 1.检查ruby
[jimo@jimo-pc blockchain]$ ruby -v
ruby 2.4.2p198 (2017-09-14 revision 59899) [x86_64-linux]

# 2.检查gem
[jimo@jimo-pc blockchain]$ gem -v
2.6.13

# 3.安装
$ gem install asciidoctor-pdf --pre
WARNING:  You don't have /home/jimo/.gem/ruby/2.4.0/bin in your PATH,
	  gem executables will not run.
Successfully installed asciidoctor-pdf-1.5.0.alpha.16
invalid options: -ri
(invalid options are ignored)
Parsing documentation for asciidoctor-pdf-1.5.0.alpha.16
Done installing documentation for asciidoctor-pdf after 0 seconds
1 gem installed
```
可以看到有个警告,所以把路径/home/jimo/.gem/ruby/2.4.0/bin加到PATH就行了.

然后转换:
直接转换失败了,先转成html,再用浏览器打印成pdf即可.

进入源码目录:
```shell
[jimo@jimo-pc bitcoinbook-second_edition_print_1]$ asciidoctor book.asciidoc 
```
更多问题可参考:[https://github.com/bitcoinbook/bitcoinbook/issues/399](https://github.com/bitcoinbook/bitcoinbook/issues/399)
