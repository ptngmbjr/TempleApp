package com.example.android.bluetoothlegatt;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.example.android.bluetoothlegatt.Enumerators.Temples;

import io.fabric.sdk.android.Fabric;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dyoung
 * @author Matt Tyler
 */
public class MonitoringActivity extends Activity implements BeaconConsumer, BootstrapNotifier {

    protected static final String TAG = "MonitoringActivity";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    private BeaconManager beaconManager = null;//BeaconManager.getInstanceForApplication(this);
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;

    private static final int REQUEST_ENABLE_BT = 1;
    private int TEMPLE_ACTIVITY = 2;
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;

    private boolean haveDetectedBeaconsSinceBoot = false;
    private MonitoringActivity monitoringActivity = null;

    public static SharedPreferences pref;

    public static final String LANGUAGE = "LANGUAGE";

    LinearLayout langLL, landingscreen;
    public static String languages[] = {"English"};//, "Spanish"};

    private ListView lv;

    private String languageSelected = "";

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;
    Button continueBtn;

    public static int SUCCESS = 0;
    public static int ERROR = -1;
    private ArrayList<Beacon> mLeDevices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        landingscreen = (LinearLayout) findViewById(R.id.lllandingpage);

        pref = getApplicationContext().getSharedPreferences("MYTEMPLEAPP", 0); // 0 - for private mode

        beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);

        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));


        setContentView(R.layout.mainactivity);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+ Permission APIs
            marshMallow();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        if (!isLangSelectionDone()) {
            initLanguageSelection();
        }
    }

    private boolean isLangSelectionDone() {
        boolean val = false;

        Log.d(TAG, " Val is " + getDataFromSP(LANGUAGE));

        if (getDataFromSP(LANGUAGE) != null)
            val = true;

        return val;
    }

    public void bindBeaconToClass() {
        beaconManager.bind(this);
    }

    public void displayLandingPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                langLL.setVisibility(View.INVISIBLE);
//                landingscreen.setVisibility(View.VISIBLE);


                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    private void initLanguageSelection() {

        langLL = (LinearLayout) findViewById(R.id.langll);

        langLL.setVisibility(View.VISIBLE);

        lv = (ListView) findViewById(R.id.list_view);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        continueBtn = (Button) findViewById(R.id.continuebtn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                //Create the intent to start another activity

                setDataInSP(LANGUAGE, languageSelected);
                bindBeaconToClass();
                displayLandingPage();
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MonitoringActivity.this.adapter.getFilter().filter(cs);
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
        adapter = new ArrayAdapter<String>(this, R.layout.language_list_item, R.id.language_name, languages);

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

                //    Toast.makeText(MonitoringActivity.this, languageSelected, Toast.LENGTH_SHORT).show();
                RadioButton radio = (RadioButton) v.findViewById(R.id.radiobtnlang);
                radio.setChecked(true);


            }
        });

        lv.setAdapter(adapter);


    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {

        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void marshMallow() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("Show Location");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {

                // Need Rationale
                String message = "App need access to " + permissionsNeeded.get(0);

                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);

                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        } else if (requestCode == TEMPLE_ACTIVITY) {
            if (mLeDevices != null)
                mLeDevices.clear();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        if (isLangSelectionDone())
            bindBeaconToClass();
    }

    @Override
    public void onPause() {
        super.onPause();
        beaconManager.unbind(this);
    }

    public void logToDisplay(final String line) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {

        mLeDevices = new ArrayList<Beacon>();

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {

                    for (Beacon beacon : beacons) {

                        String url = UrlBeaconUrlCompressor.uncompress(beacon.getId1().toByteArray()).trim();
                        Log.d(TAG, "didRangeBeaconsInRegion called with beacon count:  " + beacons.size());
                        Beacon firstBeacon = beacons.iterator().next();
                        logToDisplay("The first beacon " + url + " is about " + firstBeacon.getDistance() + " meters away.");


                        if (!(mLeDevices.contains(beacon)) &&
                                (

                                        //       url.contains(Temples.TEMPLE1.getUrl()) || url.contains("http://temple-2.com") || url.contains("http://temple-3.com"))
                                        checkIfTempleNameMatches(url)

                                )) {
//                            openDetectedTemple(url.replace("http://", "").replace(".com", "").toUpperCase());
                            openDetectedTemple(url);
                            mLeDevices.add(beacon);
                            break;
                        }


                    }
                }
            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }
    }

    public boolean checkIfTempleNameMatches(String scannedTempleName) {
        boolean ret = false;
        for (int i = 0; i < Temples.values().length; i++) {
            if (scannedTempleName.contains(Temples.values()[i].getUrl())) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public void openDetectedTemple(String name) {
        final Intent intent = new Intent(this, TempleDetails.class);
        intent.putExtra(TempleDetails.EXTRAS_TEMPLE_NAME, name);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, TEMPLE_ACTIVITY);
//        beaconManager.unbind(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);


                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

                        ) {
                    // All Permissions Granted

                    // Permission Denied
                    Toast.makeText(this, "All Permission GRANTED !! Thank You :)", Toast.LENGTH_SHORT)
                            .show();


                } else {
                    // Permission Denied
                    Toast.makeText(this, "One or More Permissions are DENIED Exiting App :(", Toast.LENGTH_SHORT)
                            .show();

                    finish();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void didEnterRegion(Region arg0) {
        // In this example, this class sends a notification to the user whenever a Beacon
        // matching a Region (defined above) are first seen.
        Log.d(TAG, "did enter region.");
        if (!haveDetectedBeaconsSinceBoot) {
            haveDetectedBeaconsSinceBoot = true;
        } else {
            if (monitoringActivity != null) {
                // If the Monitoring Activity is visible, we log info about the beacons we have
                // seen on its display
                monitoringActivity.logToDisplay("I see a beacon again");
            } else {
                // If we have already seen beacons before, but the monitoring activity is not in
                // the foreground, we send a notification to the user on subsequent detections.
                Log.d(TAG, "Sending notification.");
//                sendNotification();
            }
        }


    }

    @Override
    public void didExitRegion(Region region) {
        if (monitoringActivity != null) {
            monitoringActivity.logToDisplay("I no longer see a beacon.");
        }
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        if (monitoringActivity != null) {
            monitoringActivity.logToDisplay("I have just switched from seeing/not seeing beacons: " + state);
        }
    }

    public static void setDataInSP(String key, String value) {
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value); // Storing string

        editor.commit(); // commit changes
    }

    public static String getDataFromSP(String key) {

        return pref.getString(key, null); // getting String

    }

}
