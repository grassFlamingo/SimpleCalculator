package com.example.aliy.simplecalculator

import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.example.aliy.simplecalculator.algorithm.AutoNumber
import com.example.aliy.simplecalculator.algorithm.OperatorTree


class MainActivity : Activity() {

    private lateinit var textResult: EditText
    private var needToClean = false
    private var computeResult = AutoNumber(0.0)
    private lateinit var fColor: ForegroundColorSpan
    private lateinit var fSize: RelativeSizeSpan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textResult = findViewById(R.id.display_text)
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        textResult.setOnClickListener {
            imm.hideSoftInputFromWindow(textResult.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
        }
        fColor = ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.textDisplaySecondary))
        fSize = RelativeSizeSpan(1.5f)
    }

    /**
     *
     * on / * - + button clicked
     */
    fun onDMSPButtonClicked(v: View) {
        val myText = (v as Button).text
        if (needToClean) {
            onClearButtonClicked(v)
            textResult.append("${computeResult.formattedValue}$myText")
        } else {
            val index = textResult.selectionStart
            if (index >= 1) {
                val cha = textResult.text[index - 1]
                if (cha == ')' || cha in '0'..'9') {
                    textResult.text.insert(index, myText)
                }
            }
        }
    }

    fun onDisplayableButtonClicked(v: View) {
        if (needToClean) {
            onClearButtonClicked(v)
        }
        val index = textResult.selectionStart
        textResult.text.insert(index, (v as Button).text)
    }

    fun onDeleteButtonClicked(v: View) {
        val index = textResult.selectionStart
        val editable = textResult.text
        if (!editable.isEmpty()) {
            editable.delete(index - 1, index)
        }
    }

    fun onLastValueButtonClicked(v: View) {
        val index = textResult.selectionStart
        if (index > 0 && textResult.text[index - 1] in charArrayOf('+', '-', '*', '/')) {
            textResult.append(computeResult.formattedValue)
        } else {
            textResult.setText(computeResult.formattedValue)
            textResult.setSelection(textResult.length())
            needToClean = false
        }
    }

    fun onBracketButtonClicked(v: View) {
        if (needToClean) {
            onClearButtonClicked(v)
        }
        val index = textResult.selectionStart
        val editable = textResult.text
        if (editable.isEmpty()) {
            return
        }
        val lastChar = editable[index - 1]
        if (lastChar in charArrayOf('+', '-', '*', '/')) {
            textResult.text.insert(index, "()")
            textResult.setSelection(index + 1)
        } else if (lastChar in '0'..'9') {
            textResult.text.insert(index, ")")
        }
    }

    fun onClearButtonClicked(v: View) {
        if (!textResult.text.isEmpty()) {
            textResult.text.clear()
        }
        needToClean = false
    }


    fun onEqualButtonClicked(v: View) {
        try {
            val textString = textResult.text.toString()
            val node = OperatorTree(textString).createTree().visit()
            computeResult = node.value
            val span = SpannableString("$textString\n${node.value.formattedValue}")
            span.setSpan(fColor, 0, textString.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            span.setSpan(fSize, textString.length, span.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            textResult.setText(span)
            textResult.setSelection(textResult.length())
            needToClean = true
        } catch (e: Exception) {
            Log.i("err", e.toString())
        }
    }
}
