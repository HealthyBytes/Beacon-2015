package marand.beaconmarand.marand.beaconmarand.api;

import marand.beaconmarand.marand.beaconmarand.struct.Doctor;
import marand.beaconmarand.marand.beaconmarand.struct.FirstLogin;
import marand.beaconmarand.marand.beaconmarand.struct.WaitingQueue;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by virusss8 on 16.7.2015.
 */
public interface BeaconService {

    // example -> /doctor?uid=?minor=?major=
    @FormUrlEncoded
    @POST("/doctor")
    void getDoctor(
            @Field("uid") String uuid,
            @Field("minor") String minor,
            @Field("major") String major,
            Callback<Doctor> response
    );

    @FormUrlEncoded
    @POST("/firstlogin")
    void firstLoginToDoctor(
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("gender") String gender,
            @Field("doctor") int doctor_id,
            Callback<FirstLogin> response
    );

    @FormUrlEncoded
    @POST("/login")
    void loginToDoctor(
            @Field("doctor") int doctor_id,
            @Field("patient") int patient_id,
            Callback<WaitingQueue> response
    );

    @FormUrlEncoded
    @POST("/position")
    void getUsersPositionInLine(
            @Field("doctor") int doctor_id,
            @Field("patient") int patient_id,
            Callback<WaitingQueue> response
    );
}
