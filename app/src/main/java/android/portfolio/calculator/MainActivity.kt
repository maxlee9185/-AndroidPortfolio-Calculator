package android.portfolio.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.portfolio.calculator.databinding.ActivityMainBinding
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var firstNumberText = StringBuilder("")
    private var secondNumberText = StringBuilder("")
    private var operatorText = StringBuilder("")
    private var decimalFormat = DecimalFormat("#,###")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun numberClicked(view: View){
        val numString = (view as? Button)?.text?.toString() ?: ""
        val numberText = if(operatorText.isEmpty()) firstNumberText else secondNumberText
        numberText.append(numString)
        updateEquationText()
    }
    fun clearClicked(view:View){
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()
        updateEquationText()
        binding.result.text = ""
    }
    fun operatorClicked(view: View){
        val operatorString = (view as? Button)?.text?.toString() ?: ""

        if(firstNumberText.isEmpty()){
            Toast.makeText(this,"숫자를 입력해주세요",Toast.LENGTH_SHORT).show()
            return
        }
        if(secondNumberText.isNotEmpty()){
            Toast.makeText(this,"한 개 연산만 가능합니다",Toast.LENGTH_SHORT).show()
            return
        }
        operatorText.append(operatorString)
        updateEquationText()
    }
    fun equalClicked(view: View){
        if(firstNumberText.isEmpty() || secondNumberText.isEmpty() || operatorText.isEmpty()) {
            Toast.makeText(this,"잘못된 수식입니다",Toast.LENGTH_SHORT).show()
            return
        }
        val first = firstNumberText.toString().toBigDecimal()
        val second = secondNumberText.toString().toBigDecimal()
        val oper = operatorText.toString()

        val res = when(oper) {
            "+" -> decimalFormat.format(first+second)
            "-" -> decimalFormat.format(first-second)
            else ->"잘못된 수식입니다."
        }
        binding.result.text = res
    }

    private fun updateEquationText(){
        val firstFormattedNum = if(firstNumberText.isNotEmpty()) decimalFormat.format(firstNumberText.toString().toBigDecimal()) else ""
        val secondFormattedNum = if(secondNumberText.isNotEmpty()) decimalFormat.format(secondNumberText.toString().toBigDecimal()) else ""
        binding.equation.text="$firstFormattedNum $operatorText $secondFormattedNum"
    }
}