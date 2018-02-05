package com.example.sistemas.appmolinotransporte.Ispectiones.Images.View;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by developer on 12/01/18.
 */

public interface DataImgServer{

    @Multipart
    @POST("PostImage.php")
    Call<ResponseBody> posDateImage(@Part MultipartBody.Part uploadedfile,
                                    @Part("tiempo_evidencia_fotografica") RequestBody tiempo_evidencia_fotografica,
                                    @Part("id_factura_cargue") RequestBody id_factura_cargue );

}
