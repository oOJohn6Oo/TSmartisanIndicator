package com.github.john.tsomething

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.item_si.view.*

class TSIndicator(context: Context, attrs: AttributeSet?, defStyle: Int) :
    LinearLayout(context, attrs, defStyle) {
    private val density: Float = dp2Px(1f)
    private var dotCount: Int = 0
    private var dotRadius: Int = 20
    private var currentIndex: Int = 0
    private var viewPager: ViewPager? = null
    private var onPageChangeListener: ViewPager.OnPageChangeListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    init {
        orientation = HORIZONTAL
    }

    private fun startScroll(position: Int, positionOffset: Float) {
        if (position + 1 >= dotCount) return
        val currentItem = (getChildAt(position) as FrameLayout).inner_si
        val nextItem = (getChildAt(position + 1) as FrameLayout).inner_si

        currentItem.layoutParams = (currentItem.layoutParams as FrameLayout.LayoutParams).also {
            it.marginEnd = 0
            it.marginStart = (2*dotRadius * density * positionOffset).toInt()
        }
        nextItem.layoutParams = (nextItem.layoutParams as FrameLayout.LayoutParams).also {
            it.marginEnd = (2*dotRadius * density * (1 - positionOffset)).toInt()
            it.marginStart = 0
        }
    }

    fun setDotRadius(radiusInDp:Int):TSIndicator{
        dotRadius = radiusInDp
        return this
    }

    fun connect(viewPager: ViewPager):TSIndicator {
        this.viewPager = viewPager
        return this
    }

    fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener): TSIndicator {
        this.onPageChangeListener = onPageChangeListener
        return this
    }

    fun setDotCount(count: Int): TSIndicator {
        dotCount = count
        return this
    }

    fun build(){
        //初始化 小圆点
        for (i in 0 until dotCount) {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_si, this, false)
            itemView.layoutParams = with(itemView.layoutParams as LayoutParams){
                width = (2*dotRadius * density).toInt()
                this
            }
            itemView.outer_si.layoutParams = (itemView.outer_si.layoutParams as FrameLayout.LayoutParams).also {
                it.width = (dotRadius * density).toInt()
                it.height = (dotRadius * density).toInt()
            }
            itemView.inner_si.layoutParams = (itemView.inner_si.layoutParams as FrameLayout.LayoutParams).apply {
                width = (dotRadius * density).toInt()
                height = (dotRadius * density).toInt()
                marginEnd = (2 * dotRadius * density).toInt()
            }
            itemView.clipToOutline = true
            itemView.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline?) {
                    outline?.setOval(
                        (density * dotRadius/2).toInt(),
                        0,
                        (density * dotRadius *1.5f).toInt(),
                        (density * dotRadius).toInt()
                    )
                }
            }
            addView(itemView)
        }

        // 监听事件
        viewPager?.apply{
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    onPageChangeListener?.onPageScrollStateChanged(state)
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {

                        currentIndex = currentItem


                        this@TSIndicator.getChildAt(currentIndex).inner_si.apply {
                            scaleX = 1f
                            scaleY = 1f
                        }


                    }
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        currentIndex = currentItem


                        this@TSIndicator.getChildAt(currentIndex).inner_si.apply {
                            scaleX = 1.2f
                            scaleY = 1.2f
                        }
                    }
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    onPageChangeListener?.onPageScrolled(
                        position,
                        positionOffset,
                        positionOffsetPixels
                    )
                    if (position !in 0 until dotCount) return
                    else startScroll(position, positionOffset)
                }

                override fun onPageSelected(position: Int) {
                    onPageChangeListener?.onPageSelected(position)
                }

            })
        }
    }

    private fun dp2Px(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }
}