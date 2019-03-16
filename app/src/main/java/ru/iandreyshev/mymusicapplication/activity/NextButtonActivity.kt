package ru.iandreyshev.mymusicapplication.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_next_button.*
import ru.iandreyshev.mymusicapplication.R

object Counter {
    private var mValues = hashSetOf<Int>()
    fun plus(id: Int) {
        mValues.add(id)
        Log.e("Count", mValues.size.toString())
    }
    fun minus(id: Int) {
        mValues.remove(id)
        Log.e("Count", mValues.size.toString())
    }
}

class NextButtonActivity : AppCompatActivity() {

    private var mModelHashCode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_button)

        mModelHashCode = intent?.extras?.getInt("model.hash.code") ?: mModelHashCode

        button.setOnClickListener {
            val intent = Intent(this, NextButtonActivity::class.java)
            intent.putExtras(Bundle().apply {
                putInt("model.hash.code", mModelHashCode + 1)
            })
            startActivity(intent)
        }

        printLog("onCreate")

        Counter.plus(mModelHashCode)
    }

    override fun onStart() {
        super.onStart()
        printLog("onStart")
    }

    override fun onResume() {
        super.onResume()
        printLog("onResume")
    }

    override fun onPause() {
        super.onPause()
        printLog("onPause")
    }

    override fun onStop() {
        super.onStop()
        printLog("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        printLog("onDestroy")

        if (isFinishing) {
            Counter.minus(mModelHashCode)
        }
    }

    private fun printLog(message: String) {
        Log.e("Lifecycle event", message)
    }

}
