package com.example.sistemas.appmolinotransporte.DespachoConductores.Interface;

import com.example.sistemas.appmolinotransporte.DespachoConductores.Model.Conductor;

import java.util.List;



/**
 * Created by developer on 16/12/17.
 */

public interface NotifiPresenter {
    void initRecycler(List<Conductor> conductors);
    void errorRecycler(String error);
    void loadListConductor();


}
