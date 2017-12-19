# 1.TensorBoard
* 下载github上的源码
```shell
[jimo@jimo-pc mnist]$ pwd
/home/jimo/workspace/ml/tensorflow-1.3.1/tensorflow/examples/tutorials/mnist
[jimo@jimo-pc mnist]$ python mnist_with_summaries.py
```
* 然后进入tmp目录，找到输出：
```shell
[jimo@jimo-pc tmp]$ tree tensorflow/
tensorflow/
└── mnist
    ├── input_data
    │   ├── t10k-images-idx3-ubyte.gz
    │   ├── t10k-labels-idx1-ubyte.gz
    │   ├── train-images-idx3-ubyte.gz
    │   └── train-labels-idx1-ubyte.gz
    └── logs
        └── mnist_with_summaries
            ├── test
            │   └── events.out.tfevents.1507082401.jimo-pc
            └── train
                └── events.out.tfevents.1507082401.jimo-pc

6 directories, 6 files
```
* 打开tensorboard服务：
```shell
[jimo@jimo-pc mnist]$ tensorboard --logdir=logs/mnist_with_summaries/
TensorBoard 0.1.6 at http://jimo-pc:6006 (Press CTRL+C to quit)
```
# 2.tensorflow
## 2.1 设计理念
一般程序：
```python
In [1]: a = 1+1

In [2]: print(a)
2
```
tensorflow:先定义，放在session才可运行
```python
In [3]: import tensorflow as tf

In [4]: t = tf.add(1,1)

In [5]: print(t)
Tensor("Add:0", shape=(), dtype=int32)
```
## 2.2 编程模型
tensorflow用数据流图做计算，由节点和边组成的有向无环图。
### 2.2.1 边
代表数据依赖（实线边，数据的流动）和控制依赖（虚线边，用来确定先后关系）
```python
 tf.Graph.control_dependencies(control_inputs)
```
### 2.2.2 节点
又称为算子，代表一个操作，一般是数学运算，也可表示数据输入起点和输出终点。
