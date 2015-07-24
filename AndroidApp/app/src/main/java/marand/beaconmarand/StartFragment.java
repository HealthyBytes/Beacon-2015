package marand.beaconmarand;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by virusss8 on 17.7.2015.
 */
public class StartFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button btnScan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_start, container, false);
        btnScan = (Button) view.findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(
                            getActivity().getBaseContext(),
                            "Vaša naprava ne podpira Bluetooth modula",
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Toast.makeText(
                                getActivity().getBaseContext(),
                                "Bluetooth je izklopljen. Prosim vklopite ga in poskusite znova.",
                                Toast.LENGTH_LONG)
                                .show();
                    } else if (mBluetoothAdapter.isEnabled()) {
                        ((MainActivity)getActivity()).replaceFragment(MainActivity.FragmentType.BEACON_SCAN, true);
                    }
                }
                break;
        }
    }
}
