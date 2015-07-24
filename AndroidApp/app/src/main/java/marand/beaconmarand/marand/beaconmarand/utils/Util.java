package marand.beaconmarand.marand.beaconmarand.utils;

import android.content.Context;
import android.content.SharedPreferences;

import marand.beaconmarand.marand.beaconmarand.struct.WaitingQueue;

/**
 * Created by virusss8 on 21.7.2015.
 */
public class Util {

    private static String PREF_TAG = "myPrefs";
    private static int doctorID = -1;
    private static WaitingQueue waitingOrder;
    //private static int waiting_idx = -1;

    /*public static int getWaiting_idx() {
        return waiting_idx;
    }

    public static void setWaiting_idx(int waiting_idx) {
        Util.waiting_idx = waiting_idx;
    }*/

    public static WaitingQueue getWaitingOrder() {
        return waitingOrder;
    }

    public static void setWaitingOrder(WaitingQueue waitingOrder) {
        Util.waitingOrder = waitingOrder;
    }

    public static int getDoctorID() {
        return doctorID;
    }

    public static void setDoctorID(int doctorID) {
        Util.doctorID = doctorID;
    }

    public static boolean setUser(Context ctx, int id) {
        SharedPreferences sharedPrefs = ctx.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putInt("user_id", id);
        if (editor.commit()) {
            return true;
        } else {
            return false;
        }
    }

    public static int getUser(Context ctx) {
        SharedPreferences sharedPrefs = ctx.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE);
        return sharedPrefs.getInt("user_id", -1);
    }
}
