package com.mikelau.shimmerxsample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mikelau.shimmerxsample.utils.BaseUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firstListDemoButton = findViewById<Button>(R.id.list_demo_button)
        val firstListHorizontalDemoButton = findViewById<Button>(R.id.list_horizontal_demo_button)
        val firstGridDemoButton = findViewById<Button>(R.id.grid_demo_button)
        createClickListener(firstListDemoButton, BaseUtils.TYPE_LIST)
        createClickListener(firstListHorizontalDemoButton, BaseUtils.TYPE_LIST_HORIZONTAL)
        createClickListener(firstGridDemoButton, BaseUtils.TYPE_GRID)
        val secondListDemoButton = findViewById<Button>(R.id.list_second_demo_button)
        val secondGridDemoButton = findViewById<Button>(R.id.grid_second_demo_button)
        createClickListener(secondListDemoButton, BaseUtils.TYPE_SECOND_LIST)
        createClickListener(secondGridDemoButton, BaseUtils.TYPE_SECOND_GRID)
    }

    private fun createClickListener(button: Button, demoType: Int) {
        button.setOnClickListener { startDemo(demoType) }
    }

    private fun startDemo(demoType: Int) {
        val intent = Intent(this, DemoActivity::class.java)
        intent.putExtra(DemoActivity.EXTRA_TYPE, demoType)
        startActivity(intent)
    }
    
}
