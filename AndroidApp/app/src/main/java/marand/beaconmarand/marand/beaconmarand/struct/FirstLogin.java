package marand.beaconmarand.marand.beaconmarand.struct;

/**
 * Created by virusss8 on 21.7.2015.
 */
public class FirstLogin {

    private String Msg;
    private String Msg2;
    private int patient_id;
    private int wait_idx;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getMsg2() {
        return Msg2;
    }

    public void setMsg2(String msg2) {
        Msg2 = msg2;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getWait_idx() {
        return wait_idx;
    }

    public void setWait_idx(int wait_idx) {
        this.wait_idx = wait_idx;
    }
}
