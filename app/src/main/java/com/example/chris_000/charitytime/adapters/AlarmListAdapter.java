package com.example.chris_000.charitytime.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.chris_000.charitytime.AlarmListActivity;
import com.example.chris_000.charitytime.R;
import com.example.chris_000.charitytime.helpers.AlarmDBHelper;
import com.example.chris_000.charitytime.model.AlarmModel;

import java.util.List;

/**
 * Created by chris_000 on 9/26/2015.
 */
public class AlarmListAdapter extends BaseAdapter {
    private Context mContext;
    private List<AlarmModel> alarmModelList;

    public AlarmListAdapter(Context context, List<AlarmModel> alarms) {
        mContext = context;
        alarmModelList = alarms;
    }

    public void setAlarms(List<AlarmModel> alarms) {
        alarmModelList = alarms;
    }

    @Override
    public int getCount() {
        if (alarmModelList != null) {
            Log.d("COUNT", Integer.toString(alarmModelList.size()));
            return alarmModelList.size();
        }
        Log.d("COUNT", Integer.toString(0));
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (alarmModelList == null) {
            return null;
        }
        return alarmModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (alarmModelList == null) {
            return 0;
        }
        return alarmModelList.get(position).alarmId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.alarm_list_item, parent, false);
        }

        AlarmModel model = (AlarmModel) getItem(position);

        TextView textTime = (TextView) view.findViewById(R.id.alarm_item_time);
        textTime.setText(String.format("%02d : %02d", model.timeHour, model.timeMinute));
        TextView textName = (TextView) view.findViewById(R.id.alarm_item_name);
        textName.setText(model.alarmName);

        updateTextColor((TextView) view.findViewById(R.id.alarm_item_sunday), model.getRepeatingDay(AlarmModel.SUNDAY));
        updateTextColor((TextView) view.findViewById(R.id.alarm_item_monday), model.getRepeatingDay(AlarmModel.MONDAY));
        updateTextColor((TextView) view.findViewById(R.id.alarm_item_tuesday), model.getRepeatingDay(AlarmModel.TUESDAY));
        updateTextColor((TextView) view.findViewById(R.id.alarm_item_wednesday), model.getRepeatingDay(AlarmModel.WEDNESDAY));
        updateTextColor((TextView) view.findViewById(R.id.alarm_item_thursday), model.getRepeatingDay(AlarmModel.THURSDAY));
        updateTextColor((TextView) view.findViewById(R.id.alarm_item_friday), model.getRepeatingDay(AlarmModel.FRIDAY));
        updateTextColor((TextView) view.findViewById(R.id.alarm_item_saturday), model.getRepeatingDay(AlarmModel.SATURDAY));

        Switch btnSwitch = (Switch) view.findViewById(R.id.alarm_item_toggle);
        btnSwitch.setChecked(model.isEnabled);
        btnSwitch.setTag(Long.valueOf(model.alarmId));
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((AlarmListActivity) mContext).setAlarmEnabled(((Long) buttonView.getTag()).longValue(), isChecked);
            }
        });

        view.setTag(Long.valueOf(model.alarmId));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AlarmListActivity)mContext).startAlarmDetailsActivity(((Long) view.getTag()).longValue());
            }
        });

        return view;
    }

    private void updateTextColor(TextView view, boolean isOn) {
        if (isOn) {
            view.setTextColor(Color.GREEN);
        } else {
            view.setTextColor(Color.BLACK);
        }
    }

}
