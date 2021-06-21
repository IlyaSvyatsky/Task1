
package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task1.network.ApiService;
import com.example.task1.network.Coin;

import com.example.task1.network.CoinPriceResponse;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Вторая активность должна принять данные от первой активности:
public class DetailActivity extends AppCompatActivity {

    private TextView mTextName, mTextSymbol, mTextPriceUSD, mTextAveragePrice, mTextPercentChanged,
            mTextAvailableSupply, mTextMaximumSupply, mTextMarketCapUSD, mTextValumeUSD24Hr;

    private Coin mCoinProperty;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coin_detail);
        init();

        //Вторая активность принимает данные от первой активности
        mCoinProperty = getIntent().getParcelableExtra(MainActivity.COIN_DETAILS_KEY);


        if (mCoinProperty != null) {
            getData();
        }

        getCoinPrice();
    }

    private void getCoinPrice() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coincap.io/v2/assets/" + mCoinProperty.getId() + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //из интрфейса создаем реализацию с методами
        ApiService apiService = retrofit.create(ApiService.class);
        Call<CoinPriceResponse> call = apiService.getCoinPrice();
        //делаем асинхр запрос
        call.enqueue(new Callback<CoinPriceResponse>() {

            @Override
            public void onResponse(Call<CoinPriceResponse> call, Response<CoinPriceResponse> response) {

                GraphView graphView = (GraphView) findViewById((R.id.graph));
                LineGraphSeries<DataPoint> series =
                        new LineGraphSeries<>();

                for (int i = 0; i < response.body().getCoinPrices().length; i++) {

                    double y = Double.parseDouble(response.body().getCoinPrices()[i].getPriceUsd());

                    series.appendData(new DataPoint(i, y), true, response.body().getCoinPrices().length);
                }

                graphView.addSeries(series);

                series.setColor(Color.rgb(147, 112, 219));
                series.setTitle("Price Curve 1");

                series.setThickness(5);

                graphView.setTitle("Price Graph");
                graphView.setTitleTextSize(50);
                graphView.setTitleColor(Color.rgb(255, 255, 255));

                graphView.getLegendRenderer().setVisible(true);
                graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graphView.getLegendRenderer().setTextColor(Color.rgb(255, 255, 255));

                GridLabelRenderer gridLabelRenderer = graphView.getGridLabelRenderer();
                gridLabelRenderer.setHorizontalAxisTitle("X Axis Title");
                gridLabelRenderer.setHorizontalAxisTitleTextSize(30);
                gridLabelRenderer.setVerticalAxisTitle("Y Axis Title");
                gridLabelRenderer.setVerticalAxisTitleTextSize(30);
                gridLabelRenderer.setGridColor(Color.rgb(255, 255, 255));
                gridLabelRenderer.setHorizontalAxisTitleColor(Color.rgb(147, 112, 219));
                gridLabelRenderer.setVerticalAxisTitleColor(Color.rgb(147, 112, 219));
                gridLabelRenderer.setVerticalLabelsColor(Color.rgb(255, 255, 255));
                gridLabelRenderer.setHorizontalLabelsColor(Color.rgb(255, 255, 255));
            }

            @Override
            public void onFailure(Call<CoinPriceResponse> call, Throwable t) {

                Toast.makeText(DetailActivity.this, "Unable to proceed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {

        mTextName = findViewById(R.id.text_name);
        mTextSymbol = findViewById(R.id.text_symbol);
        mTextPriceUSD  = findViewById(R.id.coin_price);
        mTextAveragePrice = findViewById(R.id.text_average_price);
        mTextPercentChanged = findViewById(R.id.coin_percent_change);
        mTextAvailableSupply = findViewById(R.id.text_available_supply);;
        mTextMaximumSupply = findViewById(R.id.text_max_supply);
        mTextMarketCapUSD = findViewById(R.id.text_market_cap);
        mTextValumeUSD24Hr = findViewById(R.id.text_volume);
    }
    private void getData() {

        mTextName.setText(mCoinProperty.getId());
        mTextSymbol.setText(mCoinProperty.getSymbol());
        mTextAveragePrice.setText(getString(R.string.text_average_price, Double.parseDouble(mCoinProperty.getVwap24Hr())));
        mTextAvailableSupply.setText(getString(R.string.text_available_supply, Double.parseDouble(mCoinProperty.getSupply())));
        mTextMaximumSupply.setText(getString(R.string.text_average_price, Double.parseDouble(mCoinProperty.getMaxSupply())));
        mTextMarketCapUSD.setText(getString(R.string.text_market_cap, Double.parseDouble(mCoinProperty.getMarketCapUsd())));
        mTextValumeUSD24Hr.setText(getString(R.string.text_volume, Double.parseDouble(mCoinProperty.getVolumeUsd24Hr())));
        mTextPriceUSD.setText(getString(R.string.coin_price, Double.parseDouble(mCoinProperty.getPriceUsd())));
        mTextPercentChanged.setText(getString(R.string.coin_percent_change, Double.parseDouble(mCoinProperty.getChangePercent24Hr())));

        if (Double.parseDouble(mCoinProperty.getChangePercent24Hr()) >= 0) {
            mTextPercentChanged.setTextColor(getResources().getColor(R.color.percentChangePositive));
        } else {
            mTextPercentChanged.setTextColor(getResources().getColor(R.color.percentChangeNegative));
        }
    }
}
