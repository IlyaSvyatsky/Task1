package com.example.task1.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinResponse {

    @SerializedName("data")
    @Expose
    private Coin[] coins;

    public Coin[] getCoins() {
        return coins;
    }

    public void setCoins(Coin[] coins) {
        this.coins = coins;
    }

}
