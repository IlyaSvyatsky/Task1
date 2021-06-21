package com.example.task1.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.task1.R;
import com.example.task1.network.Coin;

import androidx.annotation.NonNull;

import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder>  {

    private OnCoinClickListener listener;
    private List<Coin> coinList;
    private Context context;

    //создает новый объект CoinViewHolder всякий раз, когда RecyclerView нуждается в этом.
    // Это тот момент, когда создаётся layout строки списка, передается объекту CoinViewHolder,
    // и каждый дочерний view-компонент может быть найден и сохранен.
    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        view = layoutInflater.inflate(R.layout.item_coin, viewGroup,false);

        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_coins,viewGroup,false);

        return new CoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder coinViewHolder, int i) {


        Coin coin = coinList.get(i);

        coinViewHolder.textViewId.setText(coin.getRank());
        coinViewHolder.textViewName.setText(coin.getName());
        coinViewHolder.textViewSymbol.setText(coin.getSymbol());
        coinViewHolder.textViewPrice.setText(context.getString(R.string.coin_price, Double.parseDouble(coin.getPriceUsd())));
        coinViewHolder.textViewPercentChange.setText(context.getString(R.string.coin_percent_change, Double.parseDouble(coin.getChangePercent24Hr())));

        if (Double.parseDouble(coin.getChangePercent24Hr()) >= 0) {
            coinViewHolder.textViewPercentChange.setTextColor(context.getResources().getColor(R.color.percentChangePositive));
        } else {
            coinViewHolder.textViewPercentChange.setTextColor(context.getResources().getColor(R.color.percentChangeNegative));
        }

        // обработка нажатия
        coinViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // вызываем метод слушателя, передавая ему данные
                listener.onCoinClick(coin);
            }
        });

    }

    @Override
    public int getItemCount() {

        return null != coinList ? coinList.size() : 0;
    }

    class CoinViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewId, textViewName, textViewSymbol, textViewPrice, textViewPercentChange;

        public CoinViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            textViewId = itemView.findViewById(R.id.coin_id);
            textViewName = itemView.findViewById(R.id.coin_name);
            textViewPrice = itemView.findViewById(R.id.coin_price);
            textViewSymbol = itemView.findViewById(R.id.coin_symbol);
            textViewPercentChange = itemView.findViewById(R.id.coin_percent_change);
        }
    }

    //из вне передаем наш список крипты
    //каждый раз когда будем вызывать сет, то будем обновлять список
    // и адаптер будет перерисовываь список
    public void setCoins(List<Coin> coinList) {
        this.coinList = coinList;
        notifyDataSetChanged();

    }

    public void setOnCoinClickListener(OnCoinClickListener listener) {
        this.listener = listener;
    }
}
