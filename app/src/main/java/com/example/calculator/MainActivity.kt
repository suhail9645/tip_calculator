package com.example.calculator

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val Tag="MainActivity"
private const val initialPersentage=15
class MainActivity : AppCompatActivity() {
    private lateinit var baseAmout:EditText
    private  lateinit var seekBarTip:SeekBar
    private  lateinit var tipPercentage:TextView
    private  lateinit var tipAmount:TextView
    private  lateinit var total:TextView
    private lateinit var tipDescription:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("TIPPY")
        setContentView(R.layout.activity_main)
        baseAmout=findViewById(R.id.inputField)
        seekBarTip=findViewById(R.id.seekBar)
        tipPercentage=findViewById(R.id.percentageText)
        tipAmount=findViewById(R.id.textView5)
        total=findViewById(R.id.totalValue)
        tipDescription=findViewById(R.id.description)
        tipAmount.text="0"
        total.text="0"
         seekBarTip.progress= initialPersentage
        tipPercentage.text="$initialPersentage%"
        updateDescription(initialPersentage)
        seekBarTip.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
               Log.i(Tag,"onSeecBarChanged $p1")
                tipPercentage.text="$p1%"
                computeTipAndTotal()
                updateDescription(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        baseAmout.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
               Log.i("","After Text Changed $p0")
               computeTipAndTotal()
            }

        })


    }
   private fun computeTipAndTotal(){
       if(baseAmout.text.isEmpty()){
           tipAmount.text="0"
           total.text="0"
           return
       }
        val baseAmount=baseAmout.text.toString().toDouble()
        val percentage=seekBarTip.progress
        val  tipValue=baseAmount * percentage /100
        val totalAmount=tipValue+baseAmount
        tipAmount.text="%.2f".format(tipValue)
        total.text="%.2f".format(totalAmount)

    }
    private  fun  updateDescription(percentage:Int){
        val uDescription=when(percentage){
            in 0..9->"Poor"
            in 10..14->"Acceptable"
            in 15..19->"Good"
            in 20..24->"Great"
            else-> "Amazing"
        }

       tipDescription.text=uDescription
        val color=ArgbEvaluator().evaluate(percentage.toFloat()/seekBarTip.max,
         ContextCompat.getColor(this,R.color.tipWorst),
            ContextCompat.getColor(this,R.color.tipBest)
            )as Int
      tipDescription.setTextColor(color)
    }

}