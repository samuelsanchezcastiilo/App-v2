package com.example.sistemas.appmolinotransporte;

import org.json.JSONStringer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by developer on 15/01/18.
 */

public interface ClienteServer {

    @FormUrlEncoded
    @POST("Ispection.php")
    Call<ResponseBody> Ispection(@Field("id_factura_cargue")String id_factura_cargue,
                                 @Field("aspecto_verificado")String aspecto_verificado,
                                 @Field("estado")String estado,
                                 @Field("observaciones")String observaciones);

    @FormUrlEncoded
    @POST("Inocuidad.php")
    Call<ResponseBody> Inocuidad(@Field("id_factura_cargue") String id_factura_cargue,
                                 @Field("aspecto_verificado_calidad_inocuidad") String tipo,
                                 @Field("estado_calidad_inocuidad") String estado);
}
