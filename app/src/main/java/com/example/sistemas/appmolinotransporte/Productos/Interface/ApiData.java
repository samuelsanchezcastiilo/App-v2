package com.example.sistemas.appmolinotransporte.Productos.Interface;

import com.example.sistemas.appmolinotransporte.Productos.Model.ProducModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sistemas on 15/02/18.
 */

public interface ApiData {

    @GET("GetProductos.php")
    Call <List<ProducModel>> getProductos(@Query("factura") String factura);
}
