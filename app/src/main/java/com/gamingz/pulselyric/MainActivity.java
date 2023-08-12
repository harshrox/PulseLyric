package com.gamingz.pulselyric;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.tagLine);

        String taglineText = "Elevating Care with Knowledge.";
        SpannableString spannableString = new SpannableString(taglineText);

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3366")),20,29, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);


    }
}