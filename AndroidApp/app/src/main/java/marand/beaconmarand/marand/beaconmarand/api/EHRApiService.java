package marand.beaconmarand.marand.beaconmarand.api;

import marand.beaconmarand.marand.beaconmarand.struct.EHRUser;
import marand.beaconmarand.marand.beaconmarand.struct.EHRUsers;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by virusss8 on 8.7.2015.
 */
public interface EHRApiService {

    //rest.ehrscape.com/rest/v1/demographics/party/query/?firstNames=Bruce*
    @GET("/rest/v1/demographics/party/query")
    void findUser(/*@Body EHRUsers users, */
                  @Query("firstNames") String firstNames,
                  Callback<EHRUsers> response);

    @GET("/rest/v1/demographics/party/query")
    void findUser(@Query("firstNames") String firstNames,
                  @Query("lastNames") String lastNames,
                  Callback<EHRUsers> response);

    @GET("/rest/v1/demographics/party/query")
    void findUser(@Query("firstNames") String firstNames,
                  @Query("lastNames") String lastNames,
                  @Query("gender") String gender,
                  Callback<EHRUsers> response);

    @GET("/rest/v1/demographics/ehr/{ehrId}/party")
    void getEhrID(@Path("ehrId") String ehrId,
                  Callback<EHRUser> response);
}
