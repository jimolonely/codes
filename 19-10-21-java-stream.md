关闭流的问题

代码如下所示：

```java
    InputStreamReader reader = new InputStreamReader(new FileInputStream("xxx"));
    BufferedReader br = new BufferedReader(reader);
    String line;
    int row = 0;
    while (row++ < 10 && (line = br.readLine()) != null) {
        System.out.println(line);
    }
    br.close();
```
只关闭br，reader会关闭吗？

答案是肯定的，通过打断点可以看到，但更可信的是查看close源码：

```java
    public void close() throws IOException {
        synchronized (lock) {
            if (in == null)
                return;
            try {
                in.close();
            } finally {
                in = null;
                cb = null;
            }
        }
    }
```
这个in就是传入的reader，所以肯定是关闭了的，不用担心资源泄露。

