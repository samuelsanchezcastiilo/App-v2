package com.example.sistemas.appmolinotransporte.Home.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by sistemas on 19/02/18.
 */

public interface ApiServer {
    @FormUrlEncoded
    @POST("Despachando.php")
    Call<ResponseBody> updateCargueDespachando(@Field("consecutivo")String consecutivo);
}
