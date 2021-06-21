package com.example.task1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.task1.adapter.CoinAdapter;
import com.example.task1.adapter.OnCoinClickListener;
import com.example.task1.network.ApiService;
import com.example.task1.network.Client;
import com.example.task1.network.Coin;
import com.example.task1.network.CoinPriceResponse;
import com.example.task1.network.CoinResponse;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnCoinClickListener {

    private  RecyclerView recyclerView;
    private ProgressBar mProgressBar;

    private CoinAdapter adapter = new CoinAdapter();

    private List<Coin> mCoinList;

    public static final String COIN_DETAILS_KEY = "com.example.task1.COIN_DETAILS_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
        adapter.setCoins(mCoinList);
        adapter.setOnCoinClickListener(this);
        //В созданном экземпляре RecyclerView мы должны установить LayoutManager - как отбражать данные.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getCoins();
    }

    private void getCoins() {

        //из интрфейса создаем реализацию с методами
        ApiService apiService = Client.getClient().create(ApiService.class);
        Call<CoinResponse> call = apiService.getCoins();
        //делаем асинхр запрос
        call.enqueue(new Callback<CoinResponse>() {

            @Override
            public void onResponse(Call<CoinResponse> call, Response<CoinResponse> response) {

                if (response.isSuccessful()) {

                    mProgressBar.setVisibility(View.GONE);
                    CoinResponse coinResponse = response.body();
                    mCoinList = new ArrayList<>(Arrays.asList(coinResponse.getCoins()));
                    adapter.setCoins(mCoinList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CoinResponse> call, Throwable t) {

                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Unable to proceed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Теперь мы может передать объект через Intent.
    // Создадим две активности. В первой активности напишем код для отправки объекта
    @Override
    public void onCoinClick(Coin coinProperty) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(COIN_DETAILS_KEY, coinProperty);
        startActivity(intent);
    }
}