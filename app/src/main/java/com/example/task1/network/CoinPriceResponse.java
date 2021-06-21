package com.example.task1.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinPriceResponse {

    @SerializedName("data")
    @Expose
    private Price[] coinPrices;

    public Price[] getCoinPrices() {
        return coinPrices;
    }

    public void setCoinPrices(Price[] coinPrices) {
        this.coinPrices = coinPrices;
    }
}