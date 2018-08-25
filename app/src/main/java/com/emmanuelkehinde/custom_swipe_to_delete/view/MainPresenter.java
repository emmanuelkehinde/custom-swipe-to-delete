package com.emmanuelkehinde.custom_swipe_to_delete.view;

import android.os.AsyncTask;

import com.emmanuelkehinde.custom_swipe_to_delete.model.Country;
import com.emmanuelkehinde.custom_swipe_to_delete.model.RequestResult;
import com.emmanuelkehinde.custom_swipe_to_delete.util.Constants;
import com.emmanuelkehinde.custom_swipe_to_delete.util.GeneralUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainPresenter implements MainContract.MainPresenter {

    private MainContract.MainView mainView;

    @Override
    public void attachView(MainContract.MainView view) {
        this.mainView = view;
    }

    @Override
    public void detachView() {
        mainView = null;
    }

    @Override
    public void fetchData() {
        FetchCountriesAsyncTask task = new FetchCountriesAsyncTask();
        task.execute(Constants.BASE_URL);
    }

    public class FetchCountriesAsyncTask extends AsyncTask<String, Void, RequestResult> {

        public FetchCountriesAsyncTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mainView.showProgress();
        }

        @Override
        protected RequestResult doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(30000);
                urlConnection.setConnectTimeout(30000);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod("GET");


                int statusCode = urlConnection.getResponseCode();

                if (statusCode ==  200) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            urlConnection.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    RequestResult result = new RequestResult();
                    result.setStatus(true);
                    result.setData(GeneralUtil.getJsonArray(sb.toString()));
                    return result;

                } else {
                     RequestResult result = new RequestResult();
                     result.setStatus(false);
                     return result;
                }
             

            } catch (Exception e) {
                 RequestResult result = new RequestResult();
                 result.setStatus(false);
                 return result;
            }
        }

        @Override
        protected void onPostExecute(RequestResult requestResult) {
            super.onPostExecute(requestResult);
            mainView.hideProgress();
            if (!requestResult.isStatus()) {
                mainView.showMessage("Error fetching data");
                return;
            }

            JSONArray jsonArray = requestResult.getData();
            ArrayList<Country> countries = new ArrayList<>();
            for (int i=0; i < jsonArray.length(); i++) {
                Country country = new Country();
                try {
                    country.setName(jsonArray.getJSONObject(i).getString("name"));
                    country.setCurrency(jsonArray.getJSONObject(i).getJSONArray("currencies").getJSONObject(0).getString("name"));
                    country.setLanguage(jsonArray.getJSONObject(i).getJSONArray("languages").getJSONObject(0).getString("name"));
                    countries.add(country);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mainView.showCountries(countries);
        }
    }

}
