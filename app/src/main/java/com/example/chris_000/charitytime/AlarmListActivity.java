package com.example.chris_000.charitytime;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.example.chris_000.charitytime.adapters.AlarmListAdapter;
import com.example.chris_000.charitytime.helpers.AlarmDBHelper;
import com.example.chris_000.charitytime.model.AlarmModel;


public class AlarmListActivity extends ListActivity {

    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
    private AlarmListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_alarm_list);

        mAdapter = new AlarmListAdapter(this, dbHelper.getAlarms());
        setListAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        Log.d("BLAH", "adf");
        getMenuInflater().inflate(R.menu.alarm_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == (R.id.action_add_new_alarm)) {
            startAlarmDetailsActivity(-1);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setAlarmEnabled(long id, boolean enabled) {
        AlarmModel model = dbHelper.getAlarm(id);
        model.isEnabled = enabled;
        dbHelper.updateAlarm(model);

        mAdapter.setAlarms(dbHelper.getAlarms());
        mAdapter.notifyDataSetChanged();
    }

    public void startAlarmDetailsActivity(long id) {
        Intent intent = new Intent(this, AlarmDetailsActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            mAdapter.setAlarms(dbHelper.getAlarms());
            mAdapter.notifyDataSetChanged();
        }
    }
}
