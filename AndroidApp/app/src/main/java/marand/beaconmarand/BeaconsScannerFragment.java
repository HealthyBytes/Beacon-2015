package marand.beaconmarand;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import marand.beaconmarand.marand.beaconmarand.api.RestClient;
import marand.beaconmarand.marand.beaconmarand.struct.Doctor;
import marand.beaconmarand.marand.beaconmarand.struct.Region;
import marand.beaconmarand.marand.beaconmarand.utils.Util;

/**
 * Created by virusss8 on 16.7.2015.
 */
public class BeaconsScannerFragment extends Fragment implements /*View.OnClickListener, */AdapterView.OnItemClickListener {

    public static BeaconsAdapter adapter;

    private View view;
    private ListView listOfBeacons;
    private List<Region> regions = new ArrayList<>();

    public BeaconsScannerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainActivity)getActivity()).startScanningForBeacons();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_beacons, container, false);
        listOfBeacons = (ListView) view.findViewById(R.id.lv_beacons);

        adapter = new BeaconsAdapter(getActivity().getApplicationContext(), new ArrayList<Doctor>());
        listOfBeacons.setAdapter(adapter);
        listOfBeacons.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((MainActivity)getActivity()).stopScanningForBeacons();
        Util.setDoctorID(((Doctor) parent.getAdapter().getItem(position)).getId());
        if (Util.getUser(getActivity().getBaseContext()) != -1) {
            Log.v("onDoctorClick", "pacient je stari znanec");
            ((MainActivity)this.getActivity()).setProgressDialog();
            RestClient restClient = new RestClient();
            restClient.loginToDoctor(this.getActivity());
            //((MainActivity)getActivity()).replaceFragment(MainActivity.FragmentType.QUEUE, true);
        } else {
            Log.v("onDoctorClick", "pacienta vidim prvič");
            ((MainActivity) getActivity()).replaceFragment(MainActivity.FragmentType.LOGIN, true);
        }
    }

    public void updateListOfBeacons(String uuid, String minor, String major) {
        Region r = new Region(uuid, minor, major);
        if (recordExists(r)) {
            regions.add(r);

            RestClient rc = new RestClient();

            rc.getDoctor(this.getActivity(), uuid, minor, major);
        }
    }

    private boolean recordExists(Region tmp) {
        boolean flag = true;
        for (Region r : regions) {
            if (r.getUuid().equalsIgnoreCase(tmp.getUuid())
                    && r.getMinor().equalsIgnoreCase(tmp.getMinor())
                    && r.getMajor().equalsIgnoreCase(tmp.getMajor())) {

                flag = false;
                break;
            }
        }
        return flag;
    }
}
