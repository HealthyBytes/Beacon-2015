package marand.beaconmarand.marand.beaconmarand.struct;

/**
 * Created by virusss8 on 16.7.2015.
 */
public class Doctor {

    private int id;
    private String doctor_name;
    private String doctor_surname;

    public Doctor(int id, String doctor_name, String doctor_surname) {
        this.id = id;
        this.doctor_name = doctor_name;
        this.doctor_surname = doctor_surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_surname() {
        return doctor_surname;
    }

    public void setDoctor_surname(String doctor_surname) {
        this.doctor_surname = doctor_surname;
    }
}
