package com.example.chris_000.charitytime;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.chris_000.charitytime.helpers.AlarmDBHelper;
import com.example.chris_000.charitytime.model.AlarmModel;


public class AlarmDetailsActivity extends Activity {

    private AlarmModel alarmDetails;
    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
    private TimePicker timePicker;
    private EditText edtName;
    private Switch switchWeekly;
    private Switch switchSunday;
    private Switch switchMonday;
    private Switch switchTuesday;
    private Switch switchWednesday;
    private Switch switchThursday;
    private Switch switchFriday;
    private Switch switchSaturday;
    private TextView txtToneSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        getActionBar().setTitle("Create a New Alarm");
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        timePicker = (TimePicker) findViewById(R.id.alarm_details_time_picker);
        edtName = (EditText) findViewById(R.id.alarm_details_name);
        switchWeekly = (Switch) findViewById(R.id.alarm_details_repeat_weekly);
        switchSunday = (Switch) findViewById(R.id.alarm_details_repeat_sunday);
        switchMonday = (Switch) findViewById(R.id.alarm_details_repeat_monday);
        switchTuesday = (Switch) findViewById(R.id.alarm_details_repeat_tuesday);
        switchWednesday = (Switch) findViewById(R.id.alarm_details_repeat_wednesday);
        switchThursday = (Switch) findViewById(R.id.alarm_details_repeat_thursday);
        switchFriday = (Switch) findViewById(R.id.alarm_details_repeat_friday);
        switchSaturday = (Switch) findViewById(R.id.alarm_details_repeat_saturday);
        txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);
        
        final LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.alarm_ringtone_container);
        ringToneContainer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                startActivityForResult(intent , 1);
            }
        });

        long id = getIntent().getExtras().getLong("id");

        if (id == -1) {
            alarmDetails = new AlarmModel();
        } else {
            alarmDetails = dbHelper.getAlarm(id);

            timePicker.setCurrentMinute(alarmDetails.timeMinute);
            timePicker.setCurrentHour(alarmDetails.timeHour);
            edtName.setText(alarmDetails.alarmName);
            txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.alarm_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_save_alarm_details) {
            updateModelFromLayout();
            AlarmDBHelper alarmDBHelper = new AlarmDBHelper(getApplicationContext());
            if (alarmDetails.alarmId < 0) {
                alarmDBHelper.createAlarm(alarmDetails);
            } else {
                alarmDBHelper.updateAlarm(alarmDetails);
            }
            setResult(RESULT_OK);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            alarmDetails.alarmTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            TextView txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);
            txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
        }
    }

    private void updateModelFromLayout() {

        TimePicker timePicker = (TimePicker) findViewById(R.id.alarm_details_time_picker);
        alarmDetails.timeMinute = timePicker.getCurrentMinute().intValue();
        alarmDetails.timeHour = timePicker.getCurrentHour().intValue();

        EditText edtName = (EditText) findViewById(R.id.alarm_details_name);
        alarmDetails.alarmName = edtName.getText().toString();

        Switch switchWeekly = (Switch) findViewById(R.id.alarm_details_repeat_weekly);
        alarmDetails.repeatWeekly = switchWeekly.isChecked();

        Switch switchSunday = (Switch) findViewById(R.id.alarm_details_repeat_sunday);
        alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, switchSunday.isChecked());

        Switch switchMonday = (Switch) findViewById(R.id.alarm_details_repeat_monday);
        alarmDetails.setRepeatingDay(AlarmModel.MONDAY, switchMonday.isChecked());

        Switch switchTuesday = (Switch) findViewById(R.id.alarm_details_repeat_tuesday);
        alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, switchTuesday.isChecked());

        Switch switchWednesday = (Switch) findViewById(R.id.alarm_details_repeat_wednesday);
        alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY, switchWednesday.isChecked());

        Switch switchThursday = (Switch) findViewById(R.id.alarm_details_repeat_thursday);
        alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, switchThursday.isChecked());

        Switch switchFriday = (Switch) findViewById(R.id.alarm_details_repeat_friday);
        alarmDetails.setRepeatingDay(AlarmModel.FRIDAY, switchFriday.isChecked());

        Switch switchSaturday = (Switch) findViewById(R.id.alarm_details_repeat_saturday);
        alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, switchSaturday.isChecked());

        alarmDetails.isEnabled = true;
    }


}
