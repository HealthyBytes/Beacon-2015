package marand.beaconmarand.marand.beaconmarand.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import marand.beaconmarand.BeaconsScannerFragment;
import marand.beaconmarand.MainActivity;
import marand.beaconmarand.QueueFragment;
import marand.beaconmarand.marand.beaconmarand.struct.Doctor;
import marand.beaconmarand.marand.beaconmarand.struct.FirstLogin;
import marand.beaconmarand.marand.beaconmarand.struct.WaitingQueue;
import marand.beaconmarand.marand.beaconmarand.utils.Util;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by virusss8 on 8.7.2015.
 */
public class RestClient {

    private static final String BASE_URL = "http://172.30.192.11:3000/api";
    private BeaconService apiService;

    public RestClient()
    {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                //request.addHeader("Authorization", "Basic YWxlcy5zbW9rdmluYUBnbWFpbC5jb206ZWhyNGFsZXM=");
                //request.addHeader("Content-Type", "application/json");

                // dela s tem urlencodanjem in brez -> @anotacija v interfacu po novem
                //request.addHeader("Content-Type", "application/x-www-form-urlencoded");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(requestInterceptor)
                .build();

        apiService = restAdapter.create(BeaconService.class);
    }

    public void getWaitingPosition(final QueueFragment queueFragment) {
        int docTmp = Util.getDoctorID();
        int usrTmp = Util.getUser(queueFragment.getActivity().getBaseContext());
        if (docTmp == -1 || usrTmp == -1) {
            Toast.makeText(queueFragment.getActivity(), "Prišlo je do napake. Prosimo poskusite pozneje...", Toast.LENGTH_LONG).show();
        } else {
            apiService.getUsersPositionInLine(docTmp, usrTmp, new Callback<WaitingQueue>() {
                @Override
                public void success(WaitingQueue waitingQueue, Response response) {
                    ((MainActivity)queueFragment.getActivity()).cancelProgressDialog();
                    Log.v("apiSuccess", "w_idx: " + waitingQueue.getWait_idx() + " ");
                    Util.setWaitingOrder(waitingQueue);
                    queueFragment.invalidateView();
                }

                @Override
                public void failure(RetrofitError error) {
                    ((MainActivity)queueFragment.getActivity()).cancelProgressDialog();
                    Log.v("apiFailure", error.toString());
                    queueFragment.invalidateEndView();
                }
            });
        }
    }

    public void loginToDoctor(final Context ctx) {
        int docTmp = Util.getDoctorID();
        int usrTmp = Util.getUser(ctx);
        if (docTmp == -1 || usrTmp == -1) {
            Toast.makeText(ctx, "Prišlo je do napake. Prosimo poskusite pozneje...", Toast.LENGTH_LONG).show();
        } else {
            apiService.loginToDoctor(docTmp, usrTmp, new Callback<WaitingQueue>() {
                @Override
                public void success(WaitingQueue waitingQueue, Response response) {
                    ((MainActivity)ctx).cancelProgressDialog();
                    Log.v("apiSuccess", waitingQueue.getWait_idx() + " ");
                    Util.setWaitingOrder(waitingQueue);
                    ((MainActivity)ctx).replaceFragment(MainActivity.FragmentType.QUEUE, true);
                }

                @Override
                public void failure(RetrofitError error) {
                    ((MainActivity)ctx).cancelProgressDialog();
                    //Toast.makeText(ctx, "Ste že v vrsti pri drugem zdravniku. Izberite njega...", Toast.LENGTH_SHORT).show();
                    Log.v("apiFailure", error.toString());
                    ((MainActivity)ctx).replaceFragment(MainActivity.FragmentType.QUEUE, true);
                }
            });
        }
    }

    public void firstLoginToDoctor(final Context ctx, String name, String surname, String gender, int doctor_id) {
        apiService.firstLoginToDoctor(name, surname, gender, doctor_id, new Callback<FirstLogin>() {
            @Override
            public void success(FirstLogin firstLogin, Response response) {
                ((MainActivity)ctx).cancelProgressDialog();
                Log.v("apiSuccess", firstLogin.getMsg() + " " +
                        firstLogin.getMsg2() + " " +
                        firstLogin.getPatient_id() + " " +
                        firstLogin.getWait_idx());
                Util.setUser(ctx, firstLogin.getPatient_id());
                //Util.setWaiting_idx(firstLogin.getWait_idx());
                ((MainActivity)ctx).replaceFragment(MainActivity.FragmentType.QUEUE, true);
            }

            @Override
            public void failure(RetrofitError error) {
                ((MainActivity)ctx).cancelProgressDialog();
                Log.v("apiFailure", error.toString());
            }
        });
    }

    public void getDoctor(final Context ctx, String uuid, String minor, String major) {
        apiService.getDoctor(uuid, minor, major, new Callback<Doctor>() {
            @Override
            public void success(Doctor doctor, Response response) {
                ((MainActivity)ctx).cancelProgressDialog();
                Log.v("apiSuccess", "DOKTOR_ID: " +
                        doctor.getId() + " " +
                        doctor.getDoctor_name() + " " +
                        doctor.getDoctor_surname());

                //Util.setDoctorID(doctor.getId());
                BeaconsScannerFragment.adapter.add(doctor);
                BeaconsScannerFragment.adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                ((MainActivity)ctx).cancelProgressDialog();
                Log.v("apiFailure", error.toString());
            }
        });
    }
}
