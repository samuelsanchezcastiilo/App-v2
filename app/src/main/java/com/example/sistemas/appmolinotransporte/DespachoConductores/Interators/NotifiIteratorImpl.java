package com.example.sistemas.appmolinotransporte.DespachoConductores.Interators;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.NotifiIterator;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.NotifiPresenter;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.ServiceDatos;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Model.Conductor;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by developer on 16/12/17.
 */

public class NotifiIteratorImpl implements NotifiIterator {

    private NotifiPresenter presesenter;


    public NotifiIteratorImpl(NotifiPresenter presesenter){
        this.presesenter = presesenter;
    }

    @Override
    public void initRecycler() {
        final  String url = "http://192.168.119.30/Transporte/WebServicePHP/Notification/";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceDatos datos = retrofit.create(ServiceDatos.class);
        Call<List<Conductor>> response = datos.GetNotification();
        response.enqueue(new Callback<List<Conductor>>() {
            @Override
            public void onResponse(Call<List<Conductor>> call, Response<List<Conductor>> response) {

                if(response.isSuccessful()) {

                    if (response.body().size() == 0){
                        presesenter.errorRecycler("no hay cargues disponibles");
                    }else {
                        presesenter.initRecycler(response.body());
                    }

                }


            }

            @Override
            public void onFailure(Call<List<Conductor>> call, Throwable t) {
                presesenter.errorRecycler("error en la conexion compruebe que este conectado a la red");
            }
        });
    }

/*Call<Conductor> response = datos.GetNotification();
        response.enqueue(new Callback<Conductor>() {
            @Override
            public void onResponse(Call<Conductor> call, Response<Conductor> response) {


            }

            @Override
            public void onFailure(Call<Conductor> call, Throwable t) {
                System.out.println("error del metodo");
            }
        });*/

    /*System.out.println("antes del onResponse");
        response.enqueue(new Callback<List<Conductor>>() {
            @Override
            public void onResponse(Call<List<Conductor>> call, Response<List<Conductor>> response) {
                System.out.println("inicio del metodo +++++++++++++++++++++++++++++++00");

                for (Conductor user : response.body())
                {
                    Log.e("Respuesta:   ",user.getName());//mostamos en pantalla algunos de los datos del usuario
                    //conductor.add(new Conductor(user.getCedula(),user.getName(),user.getApellido()
                    // user.getPlaca(),user.getEstado(),user.getNumberBill(),user.getHourStart(),user.getHourWait()));
                }
            }

            @Override
            public void onFailure(Call<List<Conductor>> call, Throwable t) {
                System.out.println("error" +t +call);
            }
        });*/






           /*List<Conductor> conductors = new ArrayList<Conductor>();
        conductors.add(new Conductor("David ","Casadiegos","10873633","10:14 am","20 minutos","0012a","CUU-000","Verificado"));
        conductors.add(new Conductor("Samuel ","Sanchez","1093777046","10:20 am","30 minutos","0013a","AOF-09E","Despachado"));
        conductors.add(new Conductor("Diego ","Sandoval","1088383","10:30 am","40 minutos","0014a","CUU-001","Verificado"));
        conductors.add(new Conductor("Juan ","Perez","1083977356","11:00 am","50 minutos","0015a","CUU-002","Verificado"));
        conductors.add(new Conductor("David ","Casadiegos","10873633","10:14 am","20 minutos","0012a","CUU-000","Verificado"));
        conductors.add(new Conductor("Samuel ","Sanchez","1093777046","10:20 am","30 minutos","0013a","AOF-09E","Verificado"));
        conductors.add(new Conductor("Diego ","Sandoval","1088383","10:30 am","40 minutos","0014a","CUU-001","Verificado"));
        conductors.add(new Conductor("Juan ","Perez","1083977356","11:00 am","50 minutos","0015a","CUU-002","Verificado"));
        conductors.add(new Conductor("David ","Casadiegos","10873633","10:14 am","20 minutos","0012a","CUU-000","Despachado"));
        conductors.add(new Conductor("Samuel ","Sanchez","1093777046","10:20 am","30 minutos","0013a","AOF-09E","Despachado"));
        conductors.add(new Conductor("Diego ","Sandoval","1088383","10:30 am","40 minutos","0014a","CUU-001","Despachado"));
        conductors.add(new Conductor("Juan ","Perez","1083977356","11:00 am","50 minutos","0015a","CUU-002","Despachado"));
        conductors.add(new Conductor("David ","Casadiegos","10873633","10:14 am","20 minutos","0012a","CUU-000","Despachado"));
        conductors.add(new Conductor("Samuel ","Sanchez","1093777046","10:20 am","30 minutos","0013a","AOF-09E","Verificado"));
        conductors.add(new Conductor("Diego ","Sandoval","1088383","10:30 am","40 minutos","0014a","CUU-001","Verificado"));
        conductors.add(new Conductor("Juan ","Perez","1083977356","11:00 am","50 minutos","0015a","CUU-002","Despachado"));


        presesenter.initRecycler(conductors);*/


   /* public static class Peticion extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            //Url del servicio,sin el endpoint
            final  String url = "http://192.168.100.74/WebServicePHP/Notification/";
            //Creamos el objeto Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)//Indicamos la url del servicio
                    .addConverterFactory(GsonConverterFactory.create())//Agregue la fábrica del convertidor para la serialización y la deserialización de objetos.
                    .build();//Cree la instancia de Retrofit utilizando los valores configurados.
            //https://square.github.io/retrofit/2.x/retrofit/retrofit2/Retrofit.Builder.html

            ServiceDatos datos = retrofit.create(ServiceDatos.class);

            //Recuerda que debemos colocar el modo en como obtenemos esa respuesta,en este caso es una lista de objetos
            //pero puede ser simplemente un objeto.
            Call<List<Conductor>> response = datos.GetNotification();//indicamos el metodo que deseamos ejecutar
            try {
                //Realizamos la peticion sincrona
                //Recuerda que en el body se encuentra la respuesta,que en este caso es una lista de objetos
                //List<Conductor> conductors = new ArrayList<Conductor>();
                for (Conductor user : response.execute().body())
                {
                    Log.e("Respuesta:   ",user.getApellido());//mostamos en pantalla algunos de los datos del usuario
                    //Conductor = new Conductor(user);
                    //conductors.add(new Conductor(user.getCedula(),user.getName(),user.getApellido(),
                      //     user.getPlaca(),user.getEstado(),user.getNumberBill(),user.getHourStart(),user.getHourWait()));
                    //realizamos un foreach para recorrer la lista
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/
}

/*
* final  String url = "http://192.168.100.74/WebServicePHP/Notification/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//Indicamos la url del servicio
                .addConverterFactory(GsonConverterFactory.create())//Agregue la fábrica del convertidor para la serialización y la deserialización de objetos.
                .build();
        ServiceDatos datos = retrofit.create(ServiceDatos.class);

        Call<List<Conductor>> response = datos.GetNotification();
        response.enqueue(new Callback<List<Conductor>>() {
            @Override
            public void onResponse(Call<List<Conductor>> call, Response<List<Conductor>> response) {
                for (Conductor user : response.body())
                {
                    Log.e("Respuesta:   ",user.getName());//mostamos en pantalla algunos de los datos del usuario
                    //Conductor = new Conductor(user);
                    conductors.add(new Conductor(user.getCedula(),user.getName(),user.getApellido(),
                       user.getPlaca(),user.getEstado(),user.getNumberBill(),user.getHourStart(),user.getHourWait()));
                    //realizamos un foreach para recorrer la lista
                }
            }

            @Override
            public void onFailure(Call<List<Conductor>> call, Throwable t) {

            }
        });
*/