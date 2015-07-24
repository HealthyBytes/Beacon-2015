package marand.beaconmarand.marand.beaconmarand.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by virusss8 on 8.7.2015.
 */
public class Party {

    private String id;
    private Integer version;
    private String firstNames;
    private String lastNames;
    private String gender;
    private String dateOfBirth;
    private List<PartyAdditionalInfo> partyAdditionalInfo = new ArrayList<PartyAdditionalInfo>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     *
     * @param version
     * The version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     *
     * @return
     * The firstNames
     */
    public String getFirstNames() {
        return firstNames;
    }

    /**
     *
     * @param firstNames
     * The firstNames
     */
    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }

    /**
     *
     * @return
     * The lastNames
     */
    public String getLastNames() {
        return lastNames;
    }

    /**
     *
     * @param lastNames
     * The lastNames
     */
    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     *
     * @param dateOfBirth
     * The dateOfBirth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     *
     * @return
     * The partyAdditionalInfo
     */
    public List<PartyAdditionalInfo> getPartyAdditionalInfo() {
        return partyAdditionalInfo;
    }

    /**
     *
     * @param partyAdditionalInfo
     * The partyAdditionalInfo
     */
    public void setPartyAdditionalInfo(List<PartyAdditionalInfo> partyAdditionalInfo) {
        this.partyAdditionalInfo = partyAdditionalInfo;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
