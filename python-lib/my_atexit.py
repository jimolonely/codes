import atexit

def exit1():
    print('exit1')

# 装饰器注册
@atexit.register
def exit2():
    print('exit2')

# 手动注册
atexit.register(exit1)

# 主程序
if __name__=='__main__':
    print('main')