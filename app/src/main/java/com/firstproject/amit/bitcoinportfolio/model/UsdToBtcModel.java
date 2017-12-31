package com.firstproject.amit.bitcoinportfolio.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amit on 31-12-2017.
 */

public class UsdToBtcModel implements Parcelable {
    String id;
    String name;
    String symbol;
    String price_usd;
    String price_btc;
    String market_cap_usd;
    String available_supply;
    String total_supply;
    String max_supply;
    String percent_change_1h;
    String percent_change_24h;
    String percent_change_7d;
    String last_updated;

    public UsdToBtcModel() {

    }

    protected UsdToBtcModel(Parcel in) {

        id = in.readString();
        name = in.readString();
        symbol = in.readString();
        price_usd = in.readString();
        price_btc = in.readString();
        market_cap_usd = in.readString();
        available_supply = in.readString();
        total_supply = in.readString();
        max_supply = in.readString();
        percent_change_1h = in.readString();
        percent_change_24h = in.readString();
        percent_change_7d = in.readString();
        last_updated = in.readString();
    }


    public static final Creator<UsdToBtcModel> CREATOR = new Creator<UsdToBtcModel>() {
        @Override
        public UsdToBtcModel createFromParcel(Parcel in) {
            return new UsdToBtcModel(in);
        }

        @Override
        public UsdToBtcModel[] newArray(int size) {
            return new UsdToBtcModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(symbol);
        parcel.writeString(price_usd);
        parcel.writeString(price_btc);
        parcel.writeString(market_cap_usd);
        parcel.writeString(available_supply);
        parcel.writeString(total_supply);
        parcel.writeString(max_supply);
        parcel.writeString(percent_change_1h);
        parcel.writeString(percent_change_24h);
        parcel.writeString(percent_change_7d);
        parcel.writeString(last_updated);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(String price_usd) {
        this.price_usd = price_usd;
    }

    public String getPrice_btc() {
        return price_btc;
    }

    public void setPrice_btc(String price_btc) {
        this.price_btc = price_btc;
    }

    public String getMarket_cap_usd() {
        return market_cap_usd;
    }

    public void setMarket_cap_usd(String market_cap_usd) {
        this.market_cap_usd = market_cap_usd;
    }

    public String getAvailable_supply() {
        return available_supply;
    }

    public void setAvailable_supply(String available_supply) {
        this.available_supply = available_supply;
    }

    public String getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(String total_supply) {
        this.total_supply = total_supply;
    }

    public String getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(String max_supply) {
        this.max_supply = max_supply;
    }

    public String getPercent_change_1h() {
        return percent_change_1h;
    }

    public void setPercent_change_1h(String percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    public String getPercent_change_24h() {
        return percent_change_24h;
    }

    public void setPercent_change_24h(String percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
    }

    public String getPercent_change_7d() {
        return percent_change_7d;
    }

    public void setPercent_change_7d(String percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }
}
