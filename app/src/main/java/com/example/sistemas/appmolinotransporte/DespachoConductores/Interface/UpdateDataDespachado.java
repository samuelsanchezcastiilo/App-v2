package com.example.sistemas.appmolinotransporte.DespachoConductores.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by sistemas on 16/02/18.
 */

public interface UpdateDataDespachado {

    @FormUrlEncoded
    @POST("Despachado.php")
    Call<ResponseBody> updateCargueDespachado(@Field("consecutivo")String consecutivo);


}
