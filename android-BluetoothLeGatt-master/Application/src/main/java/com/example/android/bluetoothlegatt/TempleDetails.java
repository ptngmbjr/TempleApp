package com.example.android.bluetoothlegatt;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.Enumerators.Temples;
import com.example.android.bluetoothlegatt.Fragments.LanguageFragment;

import static com.example.android.bluetoothlegatt.MonitoringActivity.ERROR;
import static com.example.android.bluetoothlegatt.MonitoringActivity.SUCCESS;

public class TempleDetails extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    TextView statueNum, statueNames, statuePlaces, statueDescs, toolbarTitle;
    int song = -1;
    String godName = "";

    public static String details = "";

    MediaPlayer mp;

    ImageView image;
    Temples globaltempleName;

    public static final String EXTRAS_TEMPLE_NAME = "TEMPLE_NAME";

    private int resumePosition;

    private int LANGUAGE_FRAGMENT = 1;

    private boolean LANGUAGE_CHANGED = false;

    ImageView musicStartStop, musicreplay;
    TextView audioName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_details);

        final Intent intent = getIntent();
        globaltempleName = getTempleDetailsThruEnum(intent.getStringExtra(EXTRAS_TEMPLE_NAME));

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        statueNum = (TextView) findViewById(R.id.statueno);
        statueNames = (TextView) findViewById(R.id.statuename);
        statuePlaces = (TextView) findViewById(R.id.statueplace);
        statueDescs = (TextView) findViewById(R.id.statueDescription);
        toolbarTitle = (TextView) findViewById(R.id.toolbartitle);
        image = (ImageView) findViewById(R.id.statueImage);
        audioName = (TextView) findViewById(R.id.audioname);

        musicStartStop = (ImageView) findViewById(R.id.iconstartstop);
        musicreplay = (ImageView) findViewById(R.id.iconreplay);

        musicStartStop.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                if (mp.isPlaying())
                    pauseMusic();
                else
                    playAudio(song);
            }
        });

        musicreplay.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                pauseMusic();
                resumePosition=0;
                playAudio(song);
            }
        });

        hardcodeText(globaltempleName);

        String[] items = new String[]{"Select your language", "English", "Tamil"};
        final Spinner spinner = (Spinner) findViewById(R.id.languagespinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                if (position != 0) {
                    final Intent intent = new Intent(getApplicationContext(), PlayerContainer.class);
                    intent.putExtra(PlayerContainer.HEADER_TITLE, globaltempleName.getGodName());
                    startActivity(intent);

                    spinner.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        setupToolbar();

        DataModel[] drawerItem = new DataModel[1];

        drawerItem[0] = new DataModel("Change Language");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter1 = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter1);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;
        Intent intent = null;
        int frag_val = -1;

        switch (position) {
            case 0:

                intent = new Intent(this, LanguageFragment.class);
                frag_val = LANGUAGE_FRAGMENT;

//                fragment = new LanguageFragment();
                break;
            case 1:
//                fragment = new FixturesFragment();
                break;
            case 2:
//                fragment = new TableFragment();
                break;

            default:
                break;
        }


//        if (fragment != null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
//            mDrawerList.setItemChecked(position, false);
//            mDrawerList.setSelection(position);
//            setTitle(mNavigationDrawerItemTitles[position]);
//            mDrawerLayout.closeDrawer(mDrawerList);
//
//        } else {
//            Log.e("MainActivity", "Error in creating fragment");
//        }

        mDrawerList.setItemChecked(position, false);
        mDrawerLayout.closeDrawer(mDrawerList);

        startActivityForResult(intent, frag_val);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        center();
    }

    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.empty, R.string.empty);
//        This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    public void center() {
        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        mTitleTextView.setSingleLine();
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        getSupportActionBar().setCustomView(mTitleTextView, layoutParams);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        mTitleTextView.setText("You're at");
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
    }

    public Temples getTempleDetailsThruEnum(String templeName) {
        for (int i = 0; i < Temples.values().length; i++) {
            if (templeName.equals(Temples.values()[i].getUrl()))
                return Temples.values()[i];
        }
        return null;
    }

    public void hardcodeText(Temples templeName) {
        String statueNo = "", place = "", description = "";
        int src = 0;

        for (int i = 0; i < Temples.values().length; i++) {
            if (templeName.equals(Temples.values()[i])) {
                src = Temples.values()[i].getSrc();
                statueNo = Temples.values()[i].getStatuNo();
                godName = Temples.values()[i].getGodName();
                place = Temples.values()[i].getPlace();
                description = Temples.values()[i].getDescription();
                song = Temples.values()[i].getSong();
                details = Temples.values()[i].getDetails();

            }
        }
//        switch (templeName) {
//            case TEMPLE1:
//                src = Temples.TEMPLE1.getSrc();
//                statueNo = Temples.TEMPLE1.getStatuNo();
//                godName = Temples.TEMPLE1.getGodName();
//                place = Temples.TEMPLE1.getPlace();
//                description = Temples.TEMPLE1.getDescription();
//                break;
//            case "TEMPLE-2":
//                src = R.drawable.pillayarpatti;
//                statueNo = "02\nTemple";
//                godName = "Karpaka Vinayakar";
//                place = "Tiruppathur";
//                description = "Pillaiyarpatti Pillaiyar Temple is an ancient rock-cut cave shrine dedicated to Lord Ganesha, located at Pillayarpatti in Tiruppathur Taluk, Sivaganga district in the state of Tamil Nadu, India. Pilliyarpatti is located 12 kilometers far, east to Tiruppathur, 3 kilometers far, west to Kundrakudi Murugan temple.";
//                break;
//            default:
//                break;
//        }
//        getSong(templeName);

        toolbarTitle.setText("You're at");
        image.setImageResource(src);
        statueNum.setText(statueNo);
        statueNames.setText(godName);
        statuePlaces.setText(place);
        statueDescs.setText(description);


    }

//    public void getSong(String templeName) {
//        String lang = MonitoringActivity.getDataFromSP(MonitoringActivity.LANGUAGE);
//
//        switch (templeName) {
//            case "TEMPLE-1":
//                song = lang.toLowerCase().contentEquals("english") ? R.raw.temple1_eng : R.raw.temple1_span;
//                break;
//            case "TEMPLE-2":
//                song = lang.toLowerCase().contentEquals("english") ? R.raw.temple2_eng : R.raw.temple2_span;
//                break;
//            case "TEMPLE-3":
//                song = lang.toLowerCase().contentEquals("english") ? R.raw.temple3_eng : R.raw.temple3_span;
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();

        pauseMusic();
    }

    public void pauseMusic() {

        if (mp != null) {
            musicStartStop.setImageResource(R.drawable.play);

            resumePosition = mp.getCurrentPosition();
            mp.pause();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (LANGUAGE_CHANGED == true) {
            mp = null;
            //getSong(globaltempleName);
            LANGUAGE_CHANGED = false;
        }

        playAudio(song);
    }

    private void onStartMusic() {

        audioName.setText(godName);
        musicStartStop.setImageResource(R.drawable.pause);

        mp.start();

    }

    public void playAudio(int name) {
        if (mp != null && !mp.isPlaying()) {
            mp.seekTo(resumePosition);
        } else {
            try {
                mp = MediaPlayer.create(this, name);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        onStartMusic();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LANGUAGE_FRAGMENT) {
            if (resultCode == SUCCESS) {
                LANGUAGE_CHANGED = true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        finish();
        super.onBackPressed();  // optional depending on your needs
    }

}
