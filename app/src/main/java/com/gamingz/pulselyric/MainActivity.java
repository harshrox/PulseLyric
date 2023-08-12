package com.gamingz.pulselyric;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{


    TextView textView;
    CardView cardViewRem;

    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog bottomSheetDialog_set;
    Button timePicker;
    TextView timeText;
    Button timePickerExit;
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
        bottomSheetDialog_set = new BottomSheetDialog(this);
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
        View view = getLayoutInflater().inflate(R.layout.reminder_dialog,null,false);

        TextView dialog_rem = view.findViewById(R.id.set_rem_button);
        TextView dialog_del = view.findViewById(R.id.del_rem_button);
        dialog_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();

                bottomSheetDialog_set.show();
                View setView = getLayoutInflater().inflate(R.layout.set_reminder_dialog, null, false);

                timeText =  setView.findViewById(R.id.timeText);
                timePicker = setView.findViewById(R.id.timePicker);
                timePickerExit = setView.findViewById(R.id.timePickerExit);

                timePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment timePickerFrag = new TimePickerFragment();
                        timePickerFrag.show(getSupportFragmentManager(), "time picker");
                    }
                });
                timePickerExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog_set.dismiss();
                        Toast.makeText(MainActivity.this, "Good", Toast.LENGTH_SHORT).show();
                    }
                });

                bottomSheetDialog_set.setContentView(setView);
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        timeText.setText("Hour: " + hourOfDay + " Minute: " + minute);
    }

}