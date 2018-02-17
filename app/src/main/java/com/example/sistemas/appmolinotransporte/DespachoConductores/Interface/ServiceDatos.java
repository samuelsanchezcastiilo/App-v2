package com.example.sistemas.appmolinotransporte.DespachoConductores.Interface;

import com.example.sistemas.appmolinotransporte.DespachoConductores.Model.Conductor;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by developer on 29/12/17.
 */

public interface ServiceDatos {

    @GET("GetNotification.php")
    Call<List<Conductor>> GetNotification();


}
