## T系列— 仿锤子科技的ViewPager指示器TSIndicator
以还原为目的，可定制部分只有圆的直径。
LinearLayout封装而成，任意定制横竖布局。

![演示图](resource/TSIndicator.gif)
### 用例

  1. 绑定viewpager
  ```
  mIndicator.setDotCount(4).connect(viewPager).setDotDiameter(10f).build()
  
  // 设置圆的直径
  setDotDiameter()
  // or xml
  app:dot_diameter="100dp"
  ```
  
  2. 自定义滑动
  ```
  mIndicator.setDotCount(4).setDotDiameter(10f).build()
  mIndicator.startScroll(position,offset)
  ```


