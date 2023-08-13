package com.gamingz.pulselyric;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SymptomWindow extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.symptom_window);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript if required
        webView.loadUrl("https://symptomate.com/interview/0"); // Replace with the desired website URL
    }
}
