package marand.beaconmarand;

import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import marand.beaconmarand.marand.beaconmarand.api.RestClient;
import marand.beaconmarand.marand.beaconmarand.utils.Util;

/**
 * Created by virusss8 on 15.7.2015.
 */
public class QueueFragment extends Fragment {

    private enum WaitingType {
        UNORDERED,
        ORDERED,
        ACCEPTED,
        ENDED
    }

    private View view;
    private TextView tvTitleQueue, tvTitleTime, tvTitleStatus, tvQueue, tvTime, tvStatus, tvDoctorName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("QueueFragment", "onCreateView");
        view = inflater.inflate(R.layout.fragment_queue, container, false);
        tvDoctorName = (TextView) view.findViewById(R.id.tv_doctor_name);
        tvTitleQueue = (TextView) view.findViewById(R.id.tv_title_queue);
        tvTitleStatus = (TextView) view.findViewById(R.id.tv_title_status);
        tvTitleTime = (TextView) view.findViewById(R.id.tv_title_time);
        tvQueue = (TextView) view.findViewById(R.id.tv_queue);
        tvStatus = (TextView) view.findViewById(R.id.tv_status);
        tvTime = (TextView) view.findViewById(R.id.tv_time);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getWaitingQueue();
        //invalidateView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getWaitingQueue() {
        RestClient restClient = new RestClient();
        restClient.getWaitingPosition(this);
    }

    public void setWaitingTime() {
        int tmp = (Util.getWaitingOrder().getWait_idx() - 1) * 15;
        if (tmp <= 60) {
            tvTime.setText((Util.getWaitingOrder().getWait_idx() - 1) * 15 + " min");
        } else if (tmp > 60) {
            tvTime.setText("> 60 min");
        } else {
            tvTime.setText("čas neznan!");
        }

    }

    public void setWaitingTimeColor() {
        if (Util.getWaitingOrder().getStatus().equalsIgnoreCase(WaitingType.ACCEPTED.toString())) {
            tvQueue.setBackgroundColor(Color.parseColor("#ff9aee8d"));
            tvStatus.setText(WaitingType.ACCEPTED.toString());
        } else if (Util.getWaitingOrder().getStatus().equalsIgnoreCase(WaitingType.ORDERED.toString())) {
            tvQueue.setBackgroundColor(Color.parseColor("#ffdbfdf4"));
            tvStatus.setText(WaitingType.ORDERED.toString());
        } else if (Util.getWaitingOrder().getStatus().equalsIgnoreCase(WaitingType.UNORDERED.toString())) {
            tvQueue.setBackgroundColor(Color.parseColor("#fff9efb9"));
            tvStatus.setText(WaitingType.UNORDERED.toString());
        }
    }

    public void setWaitingQueue() {
        tvQueue.setText(Util.getWaitingOrder().getWait_idx() + ".");
    }

    public void invalidateView() {
        if (Util.getWaitingOrder() != null && Util.getWaitingOrder().getWait_idx() > -1) {
            tvDoctorName.setText("dr." + Util.getWaitingOrder().getDoctor_name());
            setWaitingQueue();
            setWaitingTime();
            setWaitingTimeColor();
        } else {
            getWaitingQueue();
        }
    }

    public void invalidateEndView() {
        tvTitleQueue.setVisibility(View.INVISIBLE);
        tvTitleTime.setVisibility(View.INVISIBLE);
        tvQueue.setVisibility(View.INVISIBLE);
        tvTime.setVisibility(View.INVISIBLE);
        tvStatus.setText(WaitingType.ENDED.toString());
    }
}
