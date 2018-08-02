# Iterator Pattern
用于迭代对象而不需要知道列表的具体内容。

![iterator_pattern_uml_diagram](./iterator_pattern_uml_diagram.jpg?raw=true)

## 1
```java
public interface Iterator {
    boolean hasNext();

    Object next();
}
```
```java
public interface Container {
    Iterator getIterator();
}
```
## 2
NameRepository就类似于List，可以自己写set方法设置数据。
```java
public class NameRepository implements Container {
    public String[] names = {"jimo", "hehe", "haha", "kk"};

    @Override
    public Iterator getIterator() {
        return new NameIterator();
    }

    private class NameIterator implements Iterator {
        int index;

        @Override
        public boolean hasNext() {
            return index < names.length;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                return names[index++];
            }
            return null;
        }
    }
}
```
## 3
```java
public class IteratorPatternDemo {
    public static void main(String[] args) {
        NameRepository nameRepository = new NameRepository();
        for (Iterator iter = nameRepository.getIterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            System.out.println("Name:" + name);
        }
    }
}
/*
Name:jimo
Name:hehe
Name:haha
Name:kk
 */
```