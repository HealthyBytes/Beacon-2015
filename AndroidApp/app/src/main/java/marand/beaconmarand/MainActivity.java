package marand.beaconmarand;

import android.app.ProgressDialog;
import android.os.RemoteException;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bluelinelabs.logansquare.LoganSquare;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import marand.beaconmarand.marand.beaconmarand.api.HttpClientM;
import marand.beaconmarand.marand.beaconmarand.api.RestClient;
import marand.beaconmarand.marand.beaconmarand.struct.Doctor;
import marand.beaconmarand.marand.beaconmarand.struct.Tjest;
import marand.beaconmarand.marand.beaconmarand.utils.Util;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    //public static Doctor doctor;
    private ProgressDialog progress;

    final private static String BEACON_TAG = "FRAGMENT_BEACONS";
    final private static String LOGIN_TAG = "FRAGMENT_LOGIN";
    final private static String QUEUE_TAG = "FRAGMENT_QUEUE";
    final private static String START_TAG = "FRAGMENT_START";

    BeaconsScannerFragment beaconsScannerFragment;
    LoginFragment loginFragment;
    QueueFragment queueFragment;
    StartFragment startFragment;

    public enum FragmentType {
        START,
        BEACON_SCAN,
        LOGIN,
        QUEUE,
    }

    private BeaconManager beaconManager;

    public BeaconManager getBeaconManager() {
        return beaconManager;
    }

    @Override
    public void onBeaconServiceConnect() {

        beaconManager.setMonitorNotifier(new MonitorNotifier() {

            @Override
            public void didEnterRegion(Region region) {
                Log.v("onBeaconServiceConnect", "didEnterRegion " + region);
                beaconManager.setRangeNotifier(new RangeNotifier() {
                    @Override
                    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                        if (!beacons.isEmpty()) {
                            int i = 0;
                            for (Beacon b : beacons) {
                                Log.v("_beaconData_" + i, b.toString());
                                final String beacUUID = b.getId1().toString();
                                final String beacMajor = b.getId2().toString();
                                final String beacMinor = b.getId3().toString();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Boljše uporabljati findFragmentByTAG byId za dinamične fragmente baje sploh ne deluje!!!
                                        BeaconsScannerFragment fragment = (BeaconsScannerFragment) getSupportFragmentManager().findFragmentByTag(BEACON_TAG);
                                        fragment.updateListOfBeacons(beacUUID, beacMinor, beacMajor);
                                    }
                                });

                                i++;
                            }
                        }
                        if (region != null) {
                            Log.v("_region_", region.toString());
                        }
                    }
                });

                try {
                    beaconManager.startRangingBeaconsInRegion(new Region("myID", null, null, null));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didExitRegion(Region region) {
                Log.v("onBeaconServiceConnect", "didExitRegion " + region);
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {
                Log.v("onBeaconServiceConnect", "didDetermineStateForRegion " + i + " " + region);
            }
        });
        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myID", null, null, null));
            //beaconManager.startMonitoringBeaconsInRegion(new Region("myID", "B9407F30-F5F8-466E-AFF9-25556B57FE6D", 63817, 61596));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Util.setUser(this, 4);

        /*Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    HttpClientM clientM = new HttpClientM();
                    //String response = clientM.run("http://172.30.192.11/beacons/android/hello.php");
                    //Log.v("okhttpresponse", response);
                    InputStream is = clientM.run("http://172.30.192.11/beacons/android/hello.php");
                    Tjest test =  LoganSquare.parse(is, Tjest.class);

                    Log.v("okhttpresponse", test.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();*/


        beaconsScannerFragment = new BeaconsScannerFragment();
        loginFragment = new LoginFragment();
        queueFragment = new QueueFragment();
        startFragment = new StartFragment();

        replaceFragment(FragmentType.START, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindBeaconManager();
    }

    private void unbindBeaconManager() {
        if (beaconManager != null) {
            beaconManager.unbind(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_clean_all:
                Util.setUser(this, -1);
                Util.setDoctorID(-1);
                replaceFragment(FragmentType.START, false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startScanningForBeacons() {
        Log.v("beaconManager", "startScanningForBeacons");
        this.setProgressDialog();
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        if (beaconManager != null) {
            beaconManager.bind(this);
        }
    }

    public void stopScanningForBeacons() {
        Log.v("beaconManager", "stopScanningForBeacons");
        if (beaconManager != null) {
            beaconManager.unbind(this);
        }
    }

    public void replaceFragment(FragmentType fType, boolean addToStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (fType) {
            case BEACON_SCAN:
                Log.v("replaceFragment", BEACON_TAG);
                ft.replace(R.id.view_container, beaconsScannerFragment, BEACON_TAG);
                if (addToStack) {
                    ft.addToBackStack(null);
                }
                ft.commit();
                break;

            case LOGIN:
                Log.v("replaceFragment", LOGIN_TAG);
                ft.replace(R.id.view_container, loginFragment, LOGIN_TAG);
                if (addToStack) {
                    ft.addToBackStack(null);
                }
                ft.commit();
                break;

            case QUEUE:
                Log.v("replaceFragment", QUEUE_TAG);
                ft.replace(R.id.view_container, queueFragment, QUEUE_TAG);
                if (addToStack) {
                    ft.addToBackStack(null);
                }
                ft.commit();
                break;

            case START:
                Log.v("replaceFragment", START_TAG);
                ft.replace(R.id.view_container, startFragment, START_TAG);
                if (addToStack) {
                    ft.addToBackStack(null);
                }
                ft.commit();
                break;

            default:
                break;
        }
    }

    public void setProgressDialog() {
        progress = ProgressDialog.show(this, "", "Prosimo počakajte...", true);
    }

    public void cancelProgressDialog() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }
}
