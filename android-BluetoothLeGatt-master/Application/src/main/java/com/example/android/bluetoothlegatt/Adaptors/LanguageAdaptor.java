package com.example.android.bluetoothlegatt.Adaptors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.MonitoringActivity;
import com.example.android.bluetoothlegatt.R;

import java.util.ArrayList;

public class LanguageAdaptor extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public LanguageAdaptor(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.language_list_item, parent, false);
        TextView txtVwLangName = (TextView) rowView.findViewById(R.id.language_name);
        RadioButton rbtnLang = (RadioButton) rowView.findViewById(R.id.radiobtnlang);
        txtVwLangName.setText(values[position]);
        // change the icon for Windows and iPhone
        String s = values[position];

        Log.d("Lang val : ", "" + s.toString() + " vs " + MonitoringActivity.getDataFromSP(MonitoringActivity.LANGUAGE).toString());

        if (s.contentEquals(MonitoringActivity.getDataFromSP(MonitoringActivity.LANGUAGE))) {
            rbtnLang.setChecked(true);
        } else {
            rbtnLang.setChecked(false);
        }

        return rowView;
    }

}
