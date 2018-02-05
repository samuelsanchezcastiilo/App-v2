package com.example.sistemas.appmolinotransporte.Ispectiones;


import com.example.sistemas.appmolinotransporte.ConductorModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by developer on 18/01/18.
 */

public interface ServiceData {

    @GET("GetConductorId.php")
    Call<List<ConductorModel>> getConductor(@Query("factura") String idFactura);

}
