package marand.beaconmarand.marand.beaconmarand.struct;

/**
 * Created by virusss8 on 9.7.2015.
 */
public class Region {

    private String uuid;
    private String minor;
    private String major;

    public Region(String uuid, String minor, String major) {
        this.uuid = uuid;
        this.minor = minor;
        this.major = major;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
