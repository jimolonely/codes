第二次设计模式作业--理解callback---------------------------**2014112135--王棚--软件一班**  

# 回调函数(callback)
在计算机编程中,回调函数是作为参数传递给其他代码的一段可执行代码,这段代码会在合适的时间被调用.

根据被调用的时间,如果是立即调用的,称为**同步回调**;在以后的时间调用的称为**异步回调**.

实现回调函数的方式因语言而异,下面我将从几种语言来说明这种实现.

## 实验上下文
为了使程序更容易理解,我设定一个简单的实际例子:
```
在GUI中,我们会经常看到一个询问的弹出框,一般有2个按钮,yes或no.
因为弹出框一般是系统给我们实现好的,我们只是调用,所以弹出框显示我用一句输出代替,接着我们
要实现的就是当用户点击yes时我们的程序需要执行什么,我将以callback的方式实现.
``` 
## Java
首先定义对话框类,假设它长这样子:
```java
class ConfirmDialog {

    private OnCLickListener yesListener;
    // private OnCLickListener noListener;//同理

    public void show(OnCLickListener yesListener) {
        this.yesListener = yesListener;
        //显示会交给底层去实现
        System.out.println("询问框显示了...");
    }

    //一般这是系统的底层实现去响应
    private void onClickYes() {
        yesListener.click();
    }
}
```
然后是回调函数的载体,在java中不支持高阶函数,所以以接口的形式传递对象参数,间接传递方法:
```java
interface OnCLickListener {
    void click();
}
```
主程序,也就是我们写程序的地方:
```java
class Main {

    //我们的主程序,弹出对话框,在用户点击yes时做点什么
    public static void main(String[] args) {
        ConfirmDialog d = new ConfirmDialog();
        d.show(new OnCLickListener() { //这里用的匿名对象的形式

            @Override
            public void click() {
                System.out.println("用户点击了yes");
            }
        });
    }
}
```
以上就是模仿Android系统的点击事件的过程.
## Python
python是一门强大简单的语言,由于支持传递函数作为参数,所以实现更简单.

可以看到,上面java的实现是异步回调,我们必须等到用户点击时才会调用callback,下面python实现一个同步调用:
```python
def show_confirm_dialog(onclicklistener):
    '''
    传入函数,同步回调
    '''
    print("确认框显示了...")
    onclicklistener()

def on_click_yes():
    print("用户点击了yes")

def my_program():
    show_confirm_dialog(on_click_yes)
```
这样,在我们打开弹出框就点击yes,虽然不符合实际,不过意思到了.
## C sharp
C#类似与java,但和python一样使用同步回调:
```csharp
public class ConfirmDialog
{
    //传递Action作为参数,callback的载体
    public void Show(Action<string> callback)
    {
        Console.WriteLine("询问框显示了");
        callback("传点回调信息回去");
    }
}

public class Main 
{
    static void Main(string[] args)
    {
        ConfirmDialog c = new ConfirmDialog();
        c.Show(OnClickYes);
    }

    //我们的回调函数内容
    static void OnClickYes(string str)
    {
        Console.WriteLine("Callback: " + str);
    }
}
```
## Javascript
由于javascript和python非常相似,所以换一种context,以不同的运算为例:
```javascript
function someCalc(x, y, callback) {
    return callback(x, y);
}

function calcProduct(x, y) {
    return x * y;
}

function calcSum(x, y) {
    return x + y;
}
//调用方式
console.log(someCalc(5, 15, calcProduct));
console.log(someCalc(5, 15, calcSum));
```
<<<<<<< HEAD
其实更常用的Ajax请求使用的就是回调函数,这里以jquery库为例:
```javascript
$.ajax({
    url:"",
    type:"post",
    async: true,
    data:{},
    success:function(re){
    },
    error:function(re){
    }
})
console.log(2)
```
这里的success和error是用户自定定义,而把请求的过程交给框架去执行,如果把async设为false,则console.log(2)会在请求完成后才执行,否则为异步,不过这里的异步与回调函数的异步无关了,因为无论如何这里的回调都是异步的.
=======
in fact,more common is jquery:
```
$.ajax(
    url:"",
    type:"",
    success:function(){},
    error:funtion(){}
)
```
>>>>>>> 34ce382532488e633bd781713f2ffec794cfbe82
## C
c语言的实现和javascript类似,只不过涉及到传递函数的指针:
```c
#include <stdio.h>

void someCalc(int (*callback)(int,int),int x,int y) {
    int val= callback(x,y);
    printf("%d\n", val);
}

int calcProduct(int x,int y) {
    return x * y;
}

int calcSum(int x,int y) {
    return x + y;
}

int main(void) {
    someCalc(&calcProduct,5,15);
    someCalc(&calcSum,5,15);
    return 0;
}
```
# 总结
本次作业采用多种语言诠释了对callback机制的实现方式,理解了回调函数有时不只是一个简单的函数,这是一种重要的机制.

特别是在异步回调中,这种方式需要着重理解,同步回调的好处很明显,可以动态扩展代码,易于理解,且高度解耦合.
