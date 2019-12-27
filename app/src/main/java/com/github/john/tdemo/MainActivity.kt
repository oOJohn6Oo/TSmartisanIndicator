package com.github.john.tdemo

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val images = object : ArrayList<ImageView>() {}
        val imageView = object : ImageView(this) {}
        imageView.setBackgroundColor(Color.GREEN)
        val imageView2 = object : ImageView(this) {}
        imageView2.setBackgroundColor(Color.WHITE)
        val imageView3 = object : ImageView(this) {}
        imageView3.setBackgroundColor(Color.RED)
        val imageView4 = object : ImageView(this) {}
        imageView4.setBackgroundColor(Color.YELLOW)
        images.add(imageView)
        images.add(imageView2)
        images.add(imageView3)
        images.add(imageView4)
        smartisan_indicator.setDotCount(4).connect(viewPager).setDotRadius(10).build()
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return images.size
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                super.destroyItem(container, position, `object`)
                container.removeViewAt(position)
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                container.addView(images[position])
                return container.getChildAt(position)
            }
        }
    }
}
