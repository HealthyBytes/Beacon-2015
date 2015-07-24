package marand.beaconmarand.marand.beaconmarand.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by virusss8 on 9.7.2015.
 */
public class EHRUser {

    private Meta meta;
    private String action;
    private Party party;
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
     * The party
     */
    public Party getParty() {
        return party;
    }

    /**
     *
     * @param party
     * The party
     */
    public void setParty(Party party) {
        this.party = party;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
