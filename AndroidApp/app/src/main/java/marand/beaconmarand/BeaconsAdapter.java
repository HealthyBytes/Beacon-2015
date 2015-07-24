package marand.beaconmarand;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import marand.beaconmarand.marand.beaconmarand.struct.Doctor;

/**
 * Created by virusss8 on 9.7.2015.
 */
public class BeaconsAdapter extends ArrayAdapter<Doctor> {

    private final Context ctx;
    private final List<Doctor> doctors;

    public BeaconsAdapter(Context ctx, List<Doctor> doctors) {
        super(ctx, -1, doctors);
        this.ctx = ctx;
        this.doctors = doctors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("BeaconsAdapter_getView", " " + position);

        LayoutInflater inflater = (LayoutInflater) /*this.getContext()*/ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.beacon_listview_item, parent, false);

        TextView tv_doctor = (TextView) rowView.findViewById(R.id.tv_doctor);
        tv_doctor.setTextColor(Color.BLUE);

        tv_doctor.setText("dr. " + doctors.get(position).getDoctor_name() + " " + doctors.get(position).getDoctor_surname());

        return rowView;
    }
}
