package com.example.theme.medmanager01.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theme.medmanager01.R;
import com.example.theme.medmanager01.helpers.InputValidation;
import com.example.theme.medmanager01.helpers.sql.DatabaseHelper;
import com.example.theme.medmanager01.helpers.model.Medication;
import com.example.theme.medmanager01.utils.ConversionOfDates;
import com.example.theme.medmanager01.utils.MedicationBroadCastReceiver;
import com.example.theme.medmanager01.utils.service.medManagerJobService;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.job.JobScheduler;
import android.app.job.JobInfo;
import android.content.ComponentName;

public class MedicationCreationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextInputEditText medicationTitleTextInputEditText;
    private TextInputEditText medicationDescriptionTextInputEditText;
    private TextInputEditText medicationIntervalTextInputEditText;
    private CalendarPickerView calendarView;
    private Button submitMedication;
    private InputValidation validation;
    private ImageView mic1;
    private ImageView mic2;
    private ImageView mic3;
    private TextInputLayout medicationTitleTextInputLayout;
    private TextInputLayout medicationDescriptionTextInputLayout;
    private TextInputLayout medicationIntervalTextInputLayout;
    private TextView textview;
    final static int RQS_1 = 1;

    int JOB_ID = 101;
    private static JobScheduler jobScheduler;
    protected JobInfo jobInfo;
    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    private Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_creation);


        medicationTitleTextInputEditText = findViewById(R.id.text_input_editText_medication_name);
        medicationDescriptionTextInputEditText = findViewById(R.id.text_input_editText_medication_description);
        textview=(TextView)findViewById(R.id.textview);
        calendarView = findViewById(R.id.calendar_view);
        submitMedication = findViewById(R.id.button_submit_medication);
        validation = new InputValidation(this);
        mic1=findViewById(R.id.imageView3);
        mic2=findViewById(R.id.imageView4);
        but=(Button)findViewById(R.id.button);
        medicationTitleTextInputLayout = findViewById(R.id.text_input_layout_medication_title);
        medicationDescriptionTextInputLayout = findViewById(R.id.text_input_layout_medication_description);
        medicationIntervalTextInputLayout = findViewById(R.id.text_input_layout_medication_interval);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker=new TimePicker();
                timepicker.show(getSupportFragmentManager(),"timepicker");
            }
        });
        Calendar maxDate = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.MONTH,-2);
        maxDate.add(Calendar.MONTH,3);
        calendarView.init(minDate.getTime(), maxDate.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(new Date());

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        submitMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validateTitle = validation.isInputEditTextFilled(medicationTitleTextInputEditText,medicationTitleTextInputLayout,"Please enter title here");
                boolean validatedescription = validation.isInputEditTextFilled(medicationDescriptionTextInputEditText,medicationDescriptionTextInputLayout,"Please enter description here");
                List <Date> dates = calendarView.getSelectedDates();
                Log.e("Display Date :: ", "onClick:  "+dates);
                startAlarm(ca);
                if (validatedescription && validateTitle && dates.size() > 0){
                    String startDate;
                    String endDate;
                    Intent intent = new Intent(getBaseContext(), MedicationBroadCastReceiver.class);
                    intent.putExtra("body",medicationTitleTextInputEditText.getText().toString().trim());
                    pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
                    alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                    DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                    Medication medication = new Medication();
                        startDate = ConversionOfDates.formatDate(dates.get(0));
                        endDate = ConversionOfDates.formatDate(dates.get(dates.size() - 1));

                    medication.setName(medicationTitleTextInputEditText.getText().toString().trim());
                    medication.setDescription(medicationDescriptionTextInputEditText.getText().toString().trim());
                    medication.setTime(textview.getText().toString().trim());
                    medication.setStart_date(startDate);
                    medication.setEnd_date(endDate);
                    medication.setPendingIntent(pendingIntent.toString());
                    int success = databaseHelper.addMedication(medication);

                    switch (success){
                        case -1:
                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
                            break;
                        default:
                            int sec=(hour*60*60)+(minute*60);
                            ScheduleJob(sec,100);
                            /*setAlarm(
                                    "It's Time to take "+medicationTitleTextInputEditText.getText().toString().trim(),Integer.parseInt(
                                    medicationIntervalTextInputEditText.getText().toString().trim()
                            ) * 60 * 60 * 1000);
*/
                            startAlarm(ca);
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                            Intent mainIntent = new Intent(MedicationCreationActivity.this,MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                            finish();
                    }
                } else {
                    Snackbar.make(v, "Medication was not added try again", Snackbar.LENGTH_LONG)
                            .setAction("Ok", null).show();
                }
                }
        });
    }
Calendar ca;
    int hour;
    int minute;
    public void setca(Calendar c,int hour,int min){
        ca=c;
        this.hour=hour;
        minute=min;
    }
    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int min) {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,min);
        c.set(Calendar.SECOND,0);
        textview.setText(hourOfDay+"\""+min);
        setca(c,hourOfDay,min);
    }
    public void startAlarm(Calendar c){
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }
    public void getSpeechInput(View view){
        Intent intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if(intent.resolveActivity(getPackageManager())!=null){
            if(mic1.isPressed())
            startActivityForResult(intent,10);
          }else {
            Toast.makeText(this, "your device doesnt support speech", Toast.LENGTH_SHORT).show();
        }
            if(mic2.isPressed())
                startActivityForResult(intent,11);
        else{
            Toast.makeText(this,"your device doesnt support speech", Toast.LENGTH_SHORT).show();
        }
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case 10:if(resultCode==RESULT_OK && data!=null){
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                medicationTitleTextInputEditText.setText(result.get(0));
            }
            break;
            case 11:if(resultCode==RESULT_OK && data!=null){
                ArrayList<String> result1=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                medicationDescriptionTextInputEditText.setText(result1.get(0));
            }
            break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    /**
     *  this is used to set the alarm
     * @param body
     * @param duration_interval
     */

    private void setAlarm(String body,int duration_interval){

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),duration_interval, pendingIntent);

    }

    /**
     * used to schedules jobs
     * @param seconds
     * @param Job_Id
     */

    private void ScheduleJob(int seconds,int Job_Id){
        ComponentName componentName = new ComponentName(this,medManagerJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(Job_Id,componentName);
        builder.setPeriodic(seconds);
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        jobInfo = builder.build();
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
        Toast.makeText(getApplicationContext(),"JOB scheduled",Toast.LENGTH_LONG).show();
    }

    /**
     *  used to terminate schedule job using job_id
     * @param Job_Id
     * @param context
     */
    public static void terminateScheduleJob(int Job_Id,Context  context){
        jobScheduler.cancel(Job_Id);
        Toast.makeText(context,"Job Terminated",Toast.LENGTH_LONG).show();
    }

    /**
     *  this method cancel the set alarm
     * @param pendingIntent
     * @param context
     */

    public static void terminateAlarm(PendingIntent pendingIntent,Context  context){
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context,"Alarm Terminated",Toast.LENGTH_LONG).show();
    }
}
