package com.example.sistemas.appmolinotransporte.DespachoConductores.View;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.sistemas.appmolinotransporte.DespachoConductores.Model.Conductor;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Presenter.NotifiPresenterImpl;
import com.example.sistemas.appmolinotransporte.HomeActivity;
import com.example.sistemas.appmolinotransporte.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;

import java.util.ArrayList;
import java.util.List;



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
                final String factura = conductor.getNumberBill();
                final AlertDialog.Builder builder =  new AlertDialog.Builder(getContext());
                builder.setMessage("Iniciar nuevo cargue con factura: "+factura)
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =  new Intent(getContext(),HomeActivity.class);
                        intent.putExtra("factura",factura);
                        getActivity().finish();
                        startActivity(intent);
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
        Toast.makeText(getActivity(), "error en la " +error,Toast.LENGTH_LONG).show();

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




}
