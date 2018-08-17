package com.example.android.bluetoothlegatt;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class TempleDetails extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    TextView statueNum, statueNames, statuePlaces, statueDescs, toolbarTitle;

    ImageView image;
    String globaltempleName;

    public static final String EXTRAS_TEMPLE_NAME = "TEMPLE_NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_details);

        final Intent intent = getIntent();
        globaltempleName = intent.getStringExtra(EXTRAS_TEMPLE_NAME);

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
                    intent.putExtra(PlayerContainer.HEADER_TITLE, globaltempleName);
                    startActivity(intent);

                    spinner.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        setupToolbar();

        DataModel[] drawerItem = new DataModel[2];

        drawerItem[0] = new DataModel("Settings");
        drawerItem[1] = new DataModel("About Us");
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

        switch (position) {
            case 0:
//                fragment = new ConnectFragment();
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

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
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

    public void hardcodeText(String templeName) {
        String statueNo = "", godName = "", place = "", description = "";
        int src = 0;
        switch (templeName) {
            case "TEMPLE-1":
                src = R.drawable.ranganayagiranganathar;
                statueNo = "01\nTemple";
                godName = "Ranganayagi Ranganathar";
                place = "Srirangam";
                description = "Ranganayaki is held in high reverence by the people of Srirangam and by Vaishnavites. Acharyas that sang the grace of Ranganatha venerate her. She is the feminine aspect of the universe and certain Vaishnavite traditions regard her co-equal to Ranganatha himself; she is both the means and the end of worship to them.";
                break;
            case "TEMPLE-2":
                src = R.drawable.pillayarpatti;
                statueNo = "02\nTemple";
                godName = "Karpaka Vinayakar";
                place = "Tiruppathur";
                description = "Pillaiyarpatti Pillaiyar Temple is an ancient rock-cut cave shrine dedicated to Lord Ganesha, located at Pillayarpatti in Tiruppathur Taluk, Sivaganga district in the state of Tamil Nadu, India. Pilliyarpatti is located 12 kilometers far, east to Tiruppathur, 3 kilometers far, west to Kundrakudi Murugan temple.";
                break;
            case "TEMPLE-3":
                src = R.drawable.ucchi;
                statueNo = "03\nTemple";
                godName = "Ucchi Pillayar";
                place = "Trichy";
                description = "Ucchi Pillayar koil, is a 7th century Hindu temple, one dedicated to Lord Ganesha located a top of Rockfort, Trichy, Tamil Nadu, India. Historically this rock is the place where Lord Ganesha ran from King Vibishana, after establishing the Ranganathaswamy deity in Srirangam.";
                break;
            default:
                break;
        }

        toolbarTitle.setText("You're at");
        image.setImageResource(src);
        statueNum.setText(statueNo);
        statueNames.setText(godName);
        statuePlaces.setText(place);
        statueDescs.setText(description);
    }
}
