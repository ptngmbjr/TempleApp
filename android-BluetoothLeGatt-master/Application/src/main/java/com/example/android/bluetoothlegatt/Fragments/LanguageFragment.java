package com.example.android.bluetoothlegatt.Fragments;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.Adaptors.LanguageAdaptor;
import com.example.android.bluetoothlegatt.MonitoringActivity;
import com.example.android.bluetoothlegatt.R;

import static com.example.android.bluetoothlegatt.MonitoringActivity.ERROR;
import static com.example.android.bluetoothlegatt.MonitoringActivity.LANGUAGE;
import static com.example.android.bluetoothlegatt.MonitoringActivity.SUCCESS;

public class LanguageFragment extends Activity {

    private ListView lv;
    EditText inputSearch;
    Button continueBtn;
    private String languageSelected = MonitoringActivity.getDataFromSP(LANGUAGE);
    LanguageAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        adapter = new LanguageAdaptor(this, MonitoringActivity.languages);

        lv = (ListView) findViewById(R.id.list_view);

        lv.setAdapter(adapter);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        continueBtn = (Button) findViewById(R.id.continuebtn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Create the intent to start another activity

                int res = ERROR;

                if (!MonitoringActivity.getDataFromSP(LANGUAGE).contentEquals(languageSelected)) {
                    MonitoringActivity.setDataInSP(LANGUAGE, languageSelected);
                    res = SUCCESS;
                }

                setResult(res);
                finish();
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                LanguageFragment.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        // Adding items to listview
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent1, View v, int position, long id) {


                ViewGroup parent = ((ViewGroup) v.getParent()); // linear layout

                for (int x = 0; x < parent.getChildCount(); x++) {

                    View cv = parent.getChildAt(x);
                    ((RadioButton) cv.findViewById(R.id.radiobtnlang)).setChecked(false); // your checkbox/radiobtt id
                }

                languageSelected = lv.getItemAtPosition(position).toString();

//                Toast.makeText(LanguageFragment.this, languageSelected, Toast.LENGTH_SHORT).show();
                RadioButton radio = (RadioButton) v.findViewById(R.id.radiobtnlang);
                radio.setChecked(true);


            }
        });

        lv.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        setResult(ERROR);
        finish();
        super.onBackPressed();  // optional depending on your needs
    }
}
