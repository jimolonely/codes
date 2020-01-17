idea注释模板


## cc

class comment

```java
/**
 * @author jimo
 * @version 1.0.0
 * @date $date$ $time$
 */
```

## pc

property comment

```java
/**
*/
```

## mc

method comment

···groovy
groovyScript("def result=''; def params=\"${_1}\".replaceAll('[\\\\[|\\\\]|\\\\s]', '').split(',').toList(); for(i = 0; i < params.size(); i++) {result+=' * @param ' + params[i] + ((i < params.size() - 1) ? '\\n' : '')}; return result", methodParameters())
```

```java
/**
$param$
 * @return $returns$
 * @throws $exception$
 * @date:  $date$ $time$
 * @since 2.0.0 
 */
```

不过这个脚本必须要在方法内才能获取到属性，待解决。


# 文档

为了更方便地生成文档，使用javadoc生成。

目前markdown很常见，这里有几种将javadoc转为markdown的方法

## 生成javadoc

使用idea生成时，访问Tools-->Generate JavaDoc, 为了避免字符问题，在command line输入：

```java
-encoding UTF-8 -charset UTF-8 -windowtitle "接口文档" -link http://docs.Oracle.com/javase/8/docs/api
```

## 手动转换

访问：[https://delight-im.github.io/Javadoc-to-Markdown/](https://delight-im.github.io/Javadoc-to-Markdown/) 进行转换。

缺点就是不够自动化，不能满足继承等特性

## 自动转换

目前还没找到比较满意的方式


