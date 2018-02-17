package com.example.sistemas.appmolinotransporte.DespachoConductores.View;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sistemas.appmolinotransporte.DespachoConductores.Adapter.NotifiConductorAdapter;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.NotifiFragmentView;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.NotifiPresenter;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.OnRecyclerViewItemClickListener;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.UpdateDataDespachado;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Model.Conductor;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Presenter.NotifiPresenterImpl;
import com.example.sistemas.appmolinotransporte.Home.View.HomeActivity;
import com.example.sistemas.appmolinotransporte.R;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifiFragment extends Fragment  implements NotifiFragmentView {

RecyclerView recyclerView;
private NotifiConductorAdapter notifiConductorAdapter;
private NotifiPresenter notifiPresenter;
private HomeActivity homeActivity;

//CondutorViewFragmnet condutorViewFragmnet;
 ProgressBar progressBar;

    public NotifiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifi, container, false);
        notifiPresenter = new NotifiPresenterImpl(this);
        homeActivity = new HomeActivity();
        //condutorViewFragmnet =  new CondutorViewFragmnet();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycleNotificationes);
        notifiConductorAdapter =  new NotifiConductorAdapter(R.layout.notification);
        progressBar = (ProgressBar)view.findViewById(R.id.progreLoadData);
        recyclerView.setAdapter(notifiConductorAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
      

        notifiConductorAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<Conductor>() {
            @Override
            public void onItemClick(View view, Conductor conductor) {
                final String factura = conductor.getConsecutivo();
                final AlertDialog.Builder builder =  new AlertDialog.Builder(getContext());
                builder.setMessage("Iniciar nuevo cargue de consecutivo: "+factura)
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateStatusCargue(factura);
                    }
                }).setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                 builder.create();
                 builder.show();
            }
        });

      loadData();
        return view;
    }
    @Override
    public void inRecycler(List<Conductor> conductor) {
     notifiConductorAdapter.setListConductor(conductor);


    }

    @Override
    public void errorRecycler(String error) {
        Toast.makeText(getActivity(),  error,Toast.LENGTH_LONG).show();

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
     progressBar.setVisibility(View.INVISIBLE);
    }

    public  void loadData()
    {
        notifiPresenter.loadListConductor();
    }
    public void updateStatusCargue(final String consecutivo)
    {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Espere un momento");
        progressDialog.show();
        OkHttpClient okHttpClient =  new OkHttpClient();
        Retrofit.Builder reBuilder = new Retrofit.Builder()
                .baseUrl("http://192.168.119.30/Transporte/WebServicePHP/Cargues/")
                .client(okHttpClient);
        Retrofit retrofit = reBuilder.build();
        UpdateDataDespachado updateDataDespachado = retrofit.create(UpdateDataDespachado.class);

        updateDataDespachado.updateCargueDespachado(consecutivo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent =  new Intent(getContext(),HomeActivity.class);
                    intent.putExtra("factura",consecutivo);
                    getActivity().finish();
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),"Ocurrio un error revise su conexión",Toast.LENGTH_SHORT).show();

            }
        });


    }



}
