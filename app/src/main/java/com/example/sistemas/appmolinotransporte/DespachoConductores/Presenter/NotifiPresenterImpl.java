package com.example.sistemas.appmolinotransporte.DespachoConductores.Presenter;

import com.example.sistemas.appmolinotransporte.DespachoConductores.Interators.NotifiIteratorImpl;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.NotifiFragmentView;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.NotifiIterator;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.NotifiPresenter;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Model.Conductor;

import java.util.List;



/**
 * Created by developer on 16/12/17.
 */

public class NotifiPresenterImpl implements NotifiPresenter {

    private NotifiFragmentView fragmentView;
    private NotifiIterator notifiIterator;

    public NotifiPresenterImpl(NotifiFragmentView fragmentView) {
        this.fragmentView = fragmentView;
        notifiIterator = new NotifiIteratorImpl(this);

    }

    @Override
    public void initRecycler(List<Conductor> conductors) {

        fragmentView.inRecycler(conductors);
        fragmentView.hideProgress();

    }
    @Override
    public void errorRecycler(String error) {
        fragmentView.errorRecycler(error);
        fragmentView.hideProgress();
    }

    @Override
    public void loadListConductor() {
        notifiIterator.initRecycler();
        fragmentView.showProgress();

    }

}
