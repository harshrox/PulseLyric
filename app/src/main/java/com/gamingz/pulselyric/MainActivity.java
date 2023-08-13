package com.gamingz.pulselyric;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    int flagSet;
    int flagDel ;
    String name0fMedicine;
    TextView textView;
    CardView cardViewRem;
    CardView cardViewSymptom;

    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog bottomSheetDialog_set;
    BottomSheetDialog bottomSheetDialog_del;
    Button timePicker;
    Button timePickerExit;
    WebView webView;

    EditText medName ;
    Button deleteMed;
    EditText medNameDel;
    ArrayList<String> medicineName ;
    ArrayList<Integer> reqCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineName = new ArrayList<>();
        reqCode = new ArrayList<>();

        textView = findViewById(R.id.tagLine);

        String taglineText = "Elevating Care with Knowledge.";
        SpannableString spannableString = new SpannableString(taglineText);

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3366")),20,29, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);

        cardViewRem = findViewById(R.id.reminderCardview);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog_set = new BottomSheetDialog(this);
        bottomSheetDialog_del = new BottomSheetDialog(this);
        createDialog();
        cardViewRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });
        bottomSheetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        cardViewSymptom = findViewById(R.id.symptomsCardview);
        cardViewSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SymptomWindow.class);
                startActivity(intent);



            }
        });

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


                timePicker = setView.findViewById(R.id.timePicker);
                timePickerExit = setView.findViewById(R.id.timePickerExit);

                flagSet=0;

                timePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment timePickerFrag = new TimePickerFragment();
                        timePickerFrag.show(getSupportFragmentManager(), "time picker");

                        medName = setView.findViewById(R.id.medName);
                        name0fMedicine = medName.getText().toString();
                        if (!name0fMedicine.equals("")) {
                            medicineName.add(name0fMedicine);
                            flagSet=1;
                        }
                        else{
                            timePickerFrag.dismiss();
                            Toast.makeText(MainActivity.this,"Please enter valid name",Toast.LENGTH_SHORT).show();

                        }

                    }
                });


                timePickerExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog_set.dismiss();
                        if(flagSet==1){
                            Toast.makeText(MainActivity.this,"Reminder set for "+name0fMedicine,Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                bottomSheetDialog_set.setContentView(setView);
            }
        });


        dialog_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();

                bottomSheetDialog_del.show();
                View setView2 = getLayoutInflater().inflate(R.layout.del_reminder_dialog, null, false);

                medNameDel = setView2.findViewById(R.id.medNameDelete);
                deleteMed = setView2.findViewById(R.id.reminderDeleteButton);

                bottomSheetDialog_del.setContentView(setView2);

                deleteMed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String deletedMedicine = medNameDel.getText().toString();
                        flagDel=0;
                        for(String name: medicineName){
                            if(deletedMedicine.equalsIgnoreCase(name)){
                                int indexMedicine = medicineName.indexOf(name);

                                int codeRequest = reqCode.get(indexMedicine);


                                Intent alarmIntent = new Intent(MainActivity.this, MyBroadcastReceiver.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        MainActivity.this,
                                        codeRequest,
                                        alarmIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                                );

                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                alarmManager.cancel(pendingIntent);

                                medicineName.remove(indexMedicine);
                                reqCode.remove(indexMedicine);


                                bottomSheetDialog_del.dismiss();


                                Toast.makeText(MainActivity.this, "Reminder deleted for " + deletedMedicine, Toast.LENGTH_SHORT).show();

                                flagDel=1;
                                break;


                            }

                        }
                        if(flagDel==0){
                            Toast.makeText(MainActivity.this,"No reminder exists for "+deletedMedicine,Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
        bottomSheetDialog.setContentView(view);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String code = Integer.toString(hourOfDay)+Integer.toString(minute);
        int codeOfReq = Integer.parseInt(code);
        reqCode.add(codeOfReq);

        Intent alarmIntent = new Intent(this, MyBroadcastReceiver.class);
        alarmIntent.putExtra("ALARM_MESSAGE", "Reminder for medicine "+medicineName.get(reqCode.indexOf(codeOfReq)));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(),
                codeOfReq,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        long triggerTimeMillis = calendar.getTimeInMillis();

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTimeMillis,
                pendingIntent
        );




    }

}