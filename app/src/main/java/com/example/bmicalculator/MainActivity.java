package com.example.bmicalculator;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.buttonCal);
        button.setOnClickListener(calcBMI);
    }

    private View.OnClickListener calcBMI = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DecimalFormat nf = new DecimalFormat("0.00");
            EditText fieldheight = (EditText)findViewById(R.id.inputHeight);
            EditText fieldweight = (EditText)findViewById(R.id.inputWeight);
            //身高
            double height = Double.parseDouble(fieldheight.getText().toString())/100;
            //體重
            double weight = Double.parseDouble(fieldweight.getText().toString());
            //計算出BMI值
            double BMI = weight / (height*height);

            //結果
            TextView result = (TextView)findViewById(R.id.result);
            result.setText(getText(R.string.result) + " " + nf.format(BMI));

            //建議
            TextView fieldsuggest = (TextView)findViewById(R.id.suggest);
            if(BMI > 24) //太重了
                fieldsuggest.setText(R.string.advice_heavy);
            else if(BMI < 18.5) //太輕了
                fieldsuggest.setText(R.string.advice_light);
            else //剛剛好
                fieldsuggest.setText(R.string.advice_average);
        }
    };

    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() ==  MotionEvent.ACTION_DOWN ) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    //判定目前是否需隱藏鍵盤
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
            //return !(ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    //隱藏軟鍵盤
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}