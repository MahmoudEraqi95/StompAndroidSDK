package com.eraqi.chatsdk

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch




class LoadingButton(context: Context, attr: AttributeSet) :
    LinearLayout(context, attr) {

    private  var buttonState : MutableStateFlow<ButtonState>
    var progressBar: ProgressBar
    private var text: TextView
    private var button: LinearLayout

    init {
        inflate(context, R.layout.loading_button, this)
        button = findViewById(R.id.button)
        progressBar = findViewById(R.id.pb_loading)
        text = findViewById(R.id.text)
        buttonState = MutableStateFlow(ButtonState.Initial)
        CoroutineScope(Dispatchers.Main).launch {
            buttonState.collect {
                when (it) {
                    is LoadingButton.ButtonState.Loading -> {
                        button.isClickable = false
                        text.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    is LoadingButton.ButtonState.Success -> {
                        text.visibility = View.VISIBLE
                        text.text = "Disconnect"
                        progressBar.visibility = View.GONE
                        button.setBackgroundColor(Color.parseColor("#0F9D58"))
                    }
                    is LoadingButton.ButtonState.Failure -> {
                        text.text = "Connect"
                        progressBar.visibility = View.GONE
                        button.setBackgroundColor(Color.parseColor("#3700B3"))
                    }
                }
            }
        }
    }


    fun getButtonState():Flow<ButtonState>{
        return buttonState
    }
    fun setButtonState(state: ButtonState){
        buttonState.value = state
    }

    sealed class ButtonState{
        object Initial: ButtonState()
        object Loading : ButtonState()
        object Success : ButtonState()
        object Failure: ButtonState()
    }

}
