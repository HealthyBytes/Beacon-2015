package marand.beaconmarand.marand.beaconmarand.struct;

/**
 * Created by virusss8 on 22.7.2015.
 */
public class WaitingQueue {

    private int wait_idx;
    private String status;
    private String doctor_name;

    public int getWait_idx() {
        return wait_idx;
    }

    public void setWait_idx(int wait_idx) {
        this.wait_idx = wait_idx;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }
}
