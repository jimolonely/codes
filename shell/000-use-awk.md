# awk
准确的说,awk是一种编程语言,所以值得拿出来看看.

我希望通过写这篇日志让自己明白这个命令的用法,如果不幸帮助到别人,我会倍感失落.

此教程来自[tutorialspoint-awk](https://www.tutorialspoint.com/awk/index.htm)
## 理解awk执行流程
盗图:

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


