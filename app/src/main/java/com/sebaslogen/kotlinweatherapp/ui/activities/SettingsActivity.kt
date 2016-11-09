package com.sebaslogen.kotlinweatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.sebaslogen.kotlinweatherapp.R
import com.sebaslogen.kotlinweatherapp.ui.utils.DelegatesExt
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        val ZIP_CODE = "zipCode"
        val DEFAULT_ZIP = 94043L
    }

    var zipCode: Long by DelegatesExt.preference(this, ZIP_CODE, DEFAULT_ZIP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cityCode.setText(zipCode.toString())
        
//        With RxJava it would use an Observable like:
//
//        Observable.create(Observable.OnSubscribe<String> { subscriber ->
//            cityCode.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) = Unit
//
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
//
//                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
//                        = subscriber.onNext(s.toString())
//            })
//        }).debounce(1000, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    text ->
//                    zipCode = text.toLong()
//                })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        zipCode = cityCode.text.toString().toLong()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> { onBackPressed(); true }
        else -> false
    }
}