package com.example.task1.network;

import retrofit2.Call;
import retrofit2.http.GET;

//Интерфейс содержит все методы, которые будут поддерживаться клиентом.
public interface ApiService {
    @GET("assets")
    Call<CoinResponse> getCoins();

    //GET/assets/{{id}}/history
    @GET("history?interval=d1")
    Call<CoinPriceResponse> getCoinPrice();


}
