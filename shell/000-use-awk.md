# awk
准确的说,awk是一种编程语言,所以值得拿出来看看.

我希望通过写这篇日志让自己明白这个命令的用法,如果不幸帮助到别人,我会倍感失落.

此教程来自[tutorialspoint-awk](https://www.tutorialspoint.com/awk/index.htm)
## 理解awk执行流程
![awk-process](https://www.tutorialspoint.com/awk/images/awk_workflow.jpg)

例如:

有文本内容marks.txt:
```
1)  Amit    Physics  80
2)  Rahul   Maths    90
3)  Shyam   Biology  87
4)  Kedar   English  85
5)  Hari    History  89
```
我们要加上标题栏可以使用BEGIN关键字:
```shell
$ awk 'BEGIN{printf "Sr No\tName\tSub\tMarks\n"} {print} END{printf "end line\n"}' marks.txt 
# 输出
Sr No	Name	Sub	Marks
1)  Amit    Physics  80
2)  Rahul   Maths    90
3)  Shyam   Biology  87
4)  Kedar   English  85
5)  Hari    History  89
end line
```
## 基本使用方式
1. 命令行
```shell
awk [options] file
```
2. 输入脚本文件
```shell
awk [options] -f awk-script-file file ....
```
例如:command.awk内容:
```shell
{print}
```
使用:
```shell
$ awk -f command.awk marks.txt
```
## options选项
### -v
v代表variable,指在程序执行前赋予变量值.
```shell
$ awk -v name=Jerry 'BEGIN{printf "Name = %s\n", name}'
Name = Jerry
```
### --dump-variables[=file]
输出全局变量到文件,默认为awkvars.out.
```shell
[jimo@jimo-pc shell]$ awk --dump-variables ''

[jimo@jimo-pc shell]$ cat awkvars.out 
ARGC: 1
ARGIND: 0
ARGV: array, 1 elements
BINMODE: 0
CONVFMT: "%.6g"
ENVIRON: array, 60 elements
ERRNO: ""
FIELDWIDTHS: ""
FILENAME: ""
FNR: 0
FPAT: "[^[:space:]]+"
FS: " "
FUNCTAB: array, 41 elements
IGNORECASE: 0
LINT: 0
NF: 0
NR: 0
OFMT: "%.6g"
OFS: " "
ORS: "\n"
PREC: 53
PROCINFO: array, 28 elements
RLENGTH: 0
ROUNDMODE: "N"
RS: "\n"
RSTART: 0
RT: ""
SUBSEP: "\034"
SYMTAB: array, 28 elements
TEXTDOMAIN: "messages"
```
### --lint
严格检查语法,将警告当做错误输出
```shell
[jimo@jimo-pc shell]$ awk '' /bin/ls
[jimo@jimo-pc shell]$ awk --lint '' /bin/ls
awk: 命令行:1: 警告：命令行中程序体为空
awk: 命令行:1: 警告：源文件不以换行符结束
awk: 警告：完全没有程序正文！
```
### --profile[=file]
这个选项在文件中生成一个相当漂亮的程序版本,默认文件是awkprof.out。
```shell
[jimo@jimo-pc shell]$ awk --profile 'BEGIN{printf"---|Header|--\n"} {print} END{printf"---|Footer|---\n"}' marks.txt > /dev/null

[jimo@jimo-pc shell]$ cat awkprof.out 
	# gawk 配置, 创建 Thu Dec 21 20:45:30 2017

	# BEGIN 规则

	BEGIN {
     1  	printf "---|Header|--\n"
	}

	# 规则

     5  {
     5  	print $0
	}

	# END 规则

	END {
     1  	printf "---|Footer|---\n"
	}
```
## 基本使用实例
使用文件:
```shell
[jimo@jimo-pc shell]$ cat marks.txt 
1)  Amit    Physics  80
2)  Rahul   Maths    90
3)  Shyam   Biology  87
4)  Kedar   English  85
5)  Hari    History  89
```
### 打印列
```shell
$ awk '{print $3 "\t" $4}' marks.txt 
Physics	80
Maths	90
Biology	87
English	85
History	89
```
### 打印所有行
默认打印所有行,下面使用了正则匹配,必须含有a才打印
```shell
$ awk '/a/ {print $0}' marks.txt 
2)  Rahul   Maths    90
3)  Shyam   Biology  87
4)  Kedar   English  85
5)  Hari    History  89
# 等价于
$ awk '/a/' marks.txt
```
### 计数
```shell
$ awk '/a/{++cnt} END {print "Count=",cnt}' marks.txt 
Count= 4
```
### 打印行字符数
```shell
$ awk '{print length($0)}' marks.txt 
23
23
23
23
23
```




