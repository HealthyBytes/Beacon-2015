package marand.beaconmarand.marand.beaconmarand.struct;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by virusss8 on 13.7.2015.
 */

//@JsonObject
public class Tjest {

    //@JsonField(name = "msg")
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
