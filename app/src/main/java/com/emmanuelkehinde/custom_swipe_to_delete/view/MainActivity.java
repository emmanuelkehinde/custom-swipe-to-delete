package com.emmanuelkehinde.custom_swipe_to_delete.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.emmanuelkehinde.custom_swipe_to_delete.R;
import com.emmanuelkehinde.custom_swipe_to_delete.adapter.CountriesAdapter;
import com.emmanuelkehinde.custom_swipe_to_delete.model.Country;
import com.emmanuelkehinde.custom_swipe_to_delete.view.swipe.SwipeController;
import com.emmanuelkehinde.custom_swipe_to_delete.view.swipe.SwipeControllerActions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    private MainPresenter mainPresenter;
    private RecyclerView rvCountries;
    private ProgressBar progressBar;
    private ArrayList<Country> countries = new ArrayList<>();
    private CountriesAdapter countriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_main_activity));
        }
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);

        rvCountries = findViewById(R.id.rvCountries);
        progressBar = findViewById(R.id.progressBar);

        setUpAdapter();
        mainPresenter.fetchData();
    }

    private void setUpAdapter() {
        countriesAdapter = new CountriesAdapter(this, countries);
        rvCountries.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCountries.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvCountries.setAdapter(countriesAdapter);

        final SwipeController swipeController = new SwipeController(this, new SwipeControllerActions() {
            @Override
            public void onDeleteClicked(int position) {
                try {
                    countriesAdapter.countries.remove(position);
                    countriesAdapter.notifyItemRemoved(position);
                    countriesAdapter.notifyItemRangeChanged(position, countriesAdapter.getItemCount());
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(rvCountries);

        rvCountries.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @Override
    public void showCountries(ArrayList<Country> countries) {
        countriesAdapter.setCountries(countries);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

}
