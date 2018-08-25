package com.emmanuelkehinde.custom_swipe_to_delete.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emmanuelkehinde.custom_swipe_to_delete.R;
import com.emmanuelkehinde.custom_swipe_to_delete.model.Country;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesVH> {

    public ArrayList<Country> countries;
    private Context mContext;

    public CountriesAdapter(Context context, ArrayList<Country> countries) {
        this.mContext = context;
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountriesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false);
        return new CountriesVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesVH holder, int position) {
        Country country = countries.get(position);
        holder.bind(country);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    class CountriesVH extends RecyclerView.ViewHolder {

        private final TextView txtName;
        private final TextView txtCurrency;
        private final TextView txtLanguage;

        CountriesVH(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCurrency = itemView.findViewById(R.id.txtCurrency);
            txtLanguage = itemView.findViewById(R.id.txtLanguage);
        }

        void bind(Country country) {
            this.txtName.setText(country.getName());
            this.txtCurrency.setText(country.getCurrency());
            this.txtLanguage.setText(country.getLanguage());
        }
    }
}
