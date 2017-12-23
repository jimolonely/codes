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
## awk内置变量
有一些内置变量需要了解,可以加快编程.
### ARGC
代表参数个数
```shell
$ awk 'BEGIN {print "Arguments =", ARGC}' One Two Three Four
Arguments = 5
```
为什么有5个?看下面
### ARGV
存储每个参数
```shell
$ awk 'BEGIN {                                              
   for (i = 0; i < ARGC; ++i) { 
      printf "ARGV[%d] = %s\n", i, ARGV[i] 
   } 
}' one two three four
ARGV[0] = awk
ARGV[1] = one
ARGV[2] = two
ARGV[3] = three
ARGV[4] = four
```
### CONVFMT
代表数字的转换格式,默认是%.6g
```shell
]$ awk 'BEGIN {print "Conversion format = ",CONVFMT}'
Conversion format =  %.6g
```
### ENVIRON
一个环境变量的关联数组.
```shell
$ awk 'BEGIN {print ENVIRON["user"]}'
# 什么都没有
```
### FILENAME
当前文件名
```shell
$ awk 'END {print FILENAME}' marks.txt 
marks.txt
```
### FS
field separator,输入域的分隔符,默认是空格.可以使用-F修改.
```shell
[jimo@jimo-pc shell]$ awk 'BEGIN {print "FS=" FS}' | cat -vte
FS= $

[jimo@jimo-pc shell]$ awk -F "," 'BEGIN {print "FS=" FS}'
FS=,
```
### NF
number of field,只是某一行的域个数.
```shell
$ echo -e "One Two\nOne Two Three\nOne Two Three Four" | awk 'NF > 3'
One Two Three Four
```
### NR
number of record,当前记录的个数.
```shell
$ echo -e "One Two\nOne Two Three\nOne Two Three Four" | awk 'NR < 3'
One Two
One Two Three
```
### FNR
它与NR类似，但相对于当前文件。AWK在多个文件上运行时很有用。FNR的值与新文件重置。
### OFMT
输出数字格式,默认%.6g
```shell
$ awk 'BEGIN {print "OFMT = " OFMT}'
OFMT = %.6g
```
### OFS
output field separator,行内输出分隔符,默认也是空格.
```shell
$ awk 'BEGIN {print "OFS = " OFS}' | cat -vte
OFS =  $
```
### ORS
output record separator,输出记录(行)分隔符,或则行间分隔符,默认是\n
```shell
$ awk 'BEGIN {print "ORS = " ORS}' | cat -vte
ORS = $
$
```
### RS
input record separator,输入记录(行)分隔符,或则行间分隔符,默认是\n
```shell
$ awk 'BEGIN {print "RS = " RS}' | cat -vte
RS = $
$
```
### RLENGTH
代表match函数匹配的字符串长度.
```shell
$ awk 'BEGIN { if (match("One Two Three", "re")) { print RLENGTH } }'
2
```
### RSTART
由match匹配的字符串的第一个位置.
```shell
$ awk 'BEGIN { if (match("One Two Three", "Thre")) { print RSTART } }'
9
```
### SUBSEP
数组下标分隔符,默认是\034
```shell
$ awk 'BEGIN { print "SUBSEP = " SUBSEP }' | cat -vte
SUBSEP = ^\$
```
### $0
代表整个输入记录
```shell
$ awk '{print $0}' marks.txt
1)  Amit    Physics  80
2)  Rahul   Maths    90
3)  Shyam   Biology  87
4)  Kedar   English  85
5)  Hari    History  89
```
### $n
代表第n个被FS分割的单元
```shell
$ awk '{print $3 "\t" $4}' marks.txt
Physics	80
Maths	90
Biology	87
English	85
History	89
```
## GNU AWK的特定变量
### ERRNO
一个错误字符串
```shell
$ awk 'BEGIN { ret = getline < "junk.txt"; if (ret == -1) print "Error:", ERRNO }'
Error: 没有那个文件或目录
```
### IGNORECASE
忽略大小写
```shell
$ awk 'BEGIN{IGNORECASE = 1} /amit/' marks.txt
1)  Amit    Physics  80
```
### LINT
```shell
$ awk 'BEGIN {LINT = 1; a}'
awk: 命令行:1: 警告：引用未初始化的变量“a”
awk: 命令行:1: 警告：statement has no effect
```
### PROCINFO
打印与该进程相关的信息,如UID, PID等.
```shell
$ awk 'BEGIN { print PROCINFO["pid"] }'
14206
```
### TEXTDOMAIN
它代表了AWK程序的文本域,它用于查找程序字符串的本地化翻译。
```shell
$ awk 'BEGIN { print TEXTDOMAIN }'
messages
```
## awk运算符
和程序语言一样的运算符.

算术运算
+-*/%
```shell
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 50; b = 20; print "(a + b) = ", (a + b) }'
(a + b) =  70
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 50; b = 20; print "(a - b) = ", (a - b) }'
(a - b) =  30
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 50; b = 20; print "(a * b) = ", (a * b) }'
(a * b) =  1000
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 50; b = 20; print "(a / b) = ", (a / b) }'
(a / b) =  2.5
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 50; b = 20; print "(a % b) = ", (a % b) }'
(a % b) =  10
```
++,--
```shell
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 10; b = ++a; printf "a = %d, b = %d\n", a, b }'
a = 11, b = 11
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 10; b = a++; printf "a = %d, b = %d\n", a, b }'
a = 11, b = 10
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 10; b = --a; printf "a = %d, b = %d\n", a, b }'
a = 9, b = 9
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 10; b = a--; printf "a = %d, b = %d\n", a, b }'
a = 9, b = 10
```
简写:
```shell
[jimo@jimo-pc shell]$ awk 'BEGIN { cnt = 2; cnt **= 4; print "Counter =", cnt }'
Counter = 16
[jimo@jimo-pc shell]$ awk 'BEGIN { cnt = 2; cnt ^= 4; print "Counter =", cnt }'
Counter = 16
```
比较运算符:>,<,==,!=,>=,<=

逻辑运算符:&&,||,!

三元运算符:condition expression ? statement1 : statement2

一元运算符:
```shell
[jimo@jimo-pc shell]$ awk 'BEGIN { a = -10; a = +a; print "a =", a }'
a = -10
[jimo@jimo-pc shell]$ awk 'BEGIN { a = -10; a = -a; print "a =", a }'
a = 10
```
指数运算:
```shell
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 10; a = a ^ 2; print "a =", a }'
a = 100
[jimo@jimo-pc shell]$ awk 'BEGIN { a = 10; a = a**2; print "a =", a }'
a = 100
```
字符串连接符:有点区别,这里是空格
```shell
[jimo@jimo-pc shell]$ awk 'BEGIN { str1 = "Hello,"; str2 = "World"; str3 = str1 str2; print str3 }'
Hello,World
```
in运算符:
```shell
[jimo@jimo-pc shell]$ awk 'BEGIN { 
>    arr[0] = 1; arr[1] = 2; arr[2] = 3; for (i in arr) printf "arr[%d] = %d\n", i, arr[i]
> }'
arr[0] = 1
arr[1] = 2
arr[2] = 3
```
正则运算符:这里不是指正则运算的,而是匹配和不匹配:
```shell
# 含有9的行
[jimo@jimo-pc shell]$ awk '$0 ~ 9' marks.txt
2)  Rahul   Maths    90
5)  Hari    History  89
# 不含9的行
[jimo@jimo-pc shell]$ awk '$0 !~ 9' marks.txt
1)  Amit    Physics  80
3)  Shyam   Biology  87
4)  Kedar   English  85
```



