package com.example.sistemas.appmolinotransporte.DriverAccept.View;

import com.example.sistemas.appmolinotransporte.DespachoConductores.Model.Conductor;

import java.util.List;

import javax.xml.transform.Result;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by developer on 3/01/18.
 */

public interface ServiceDatos{
    @Multipart
    @POST("PostImg.php")
    Call<ResponseBody> postImage(@Part MultipartBody.Part uploadedfile,
                                 @Part("id_factura_cargue")RequestBody id_factura_cargue,
                                 @Part("id_conductor_vehiculo")RequestBody id_conductor_vehiculo);
    @GET("VerificarFirma.php")
    Call<Veri> VerificarFirma(@Query("consecutivo") String consecutivo,
                                  @Query("idConductor") String idConductor);
}
