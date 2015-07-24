package marand.beaconmarand.marand.beaconmarand.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by virusss8 on 8.7.2015.
 */
public class EHRUsers {
    private Meta meta;
    private String action;
    private List<Party> parties = new ArrayList<Party>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     * The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     *
     * @return
     * The action
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @param action
     * The action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     *
     * @return
     * The parties
     */
    public List<Party> getParties() {
        return parties;
    }

    /**
     *
     * @param parties
     * The parties
     */
    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}