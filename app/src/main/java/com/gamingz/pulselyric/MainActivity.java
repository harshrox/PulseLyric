package com.gamingz.pulselyric;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {


    TextView textView;
    CardView cardViewRem;

    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tagLine);

        String taglineText = "Elevating Care with Knowledge.";
        SpannableString spannableString = new SpannableString(taglineText);

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3366")),20,29, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);

        cardViewRem = findViewById(R.id.reminderCardview);
        bottomSheetDialog = new BottomSheetDialog(this);
        createDialog();
        cardViewRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });
        bottomSheetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void createDialog() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog,null,false);

        TextView dialog_rem = view.findViewById(R.id.set_rem_button);
        TextView dialog_del = view.findViewById(R.id.del_rem_button);
        dialog_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Toast.makeText(MainActivity.this,"Set",Toast.LENGTH_LONG).show();
            }
        });
        dialog_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Toast.makeText(MainActivity.this,"Delete",Toast.LENGTH_LONG).show();
            }
        });
        bottomSheetDialog.setContentView(view);
    }
}