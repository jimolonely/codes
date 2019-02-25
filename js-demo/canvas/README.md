# canvas

1. 历史简介

2. 支持情况（2D和3D（WebGL））,实验(位于源码getContext的注释)

    contextId The identifier (ID) of the type of canvas to create. 
    Internet Explorer 9 and Internet Explorer 10 support only a 2-D context 
    using canvas.getContext("2d"); IE11 Preview also supports 3-D or WebGL 
    context using canvas.getContext("experimental-webgl");

3. canvas声明： 不支持体现

4. 初步使用，toDataURL, 跨域问题. basic.html

5. 2D绘图
    1. 画板(浏览器窗口)、画笔（context）、坐标介绍
    2. 2个操作： 填充+画线+style， fillStyle + strokeStyle
    3. 绘图： 矩形、路径、文本、转换、绘制图像（rect.html）、
    效果（effect.html, 阴影、渐变、pattern）
    4. 使用图像数据: imageData.html
    5. 合成：globalAlpha, globalCompositionOperation, composition.html

    转换
    1. translate(x,y)
    2. rotate(angle)
    3. transform: transform.html, http://www.w3school.com.cn/html5/canvas_transform.asp， 手动推导公式

6. canvas事件： event.html

3D绘图

1. 支持情况：https://developer.mozilla.org/zh-CN/docs/Web/API/WebGL_API
2. demo展示
3. 前置原理： 变量，缓冲区和数据类型：看书
4. 实践： 例子:https://developer.mozilla.org/zh-CN/docs/Web/API/WebGL_API/Tutorial/Getting_started_with_WebGL

github: https://github.com/mdn/webgl-examples/tree/gh-pages/tutorial

WebGL常量：https://developer.mozilla.org/zh-CN/docs/Web/API/WebGL_API/Constants

WebGL类型：https://developer.mozilla.org/zh-CN/docs/Web/API/WebGL_API/Types




