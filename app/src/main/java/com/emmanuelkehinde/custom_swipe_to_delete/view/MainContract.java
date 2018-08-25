package com.emmanuelkehinde.custom_swipe_to_delete.view;

import com.emmanuelkehinde.custom_swipe_to_delete.model.Country;

import java.util.ArrayList;

public interface MainContract {

    interface MainPresenter {
        void attachView(MainView view);
        void detachView();
        void fetchData();
    }

    interface MainView {
        void showCountries(ArrayList<Country> countries);
        void showProgress();
        void hideProgress();
        void showMessage(String message);
    }

}
