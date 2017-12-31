package com.firstproject.amit.bitcoinportfolio.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amit on 31-12-2017.
 */

public class UsdToBtcModel implements Parcelable {
    //
//    [
//    {
//        "id": "bitcoin",
//            "name": "Bitcoin",
//            "symbol": "BTC",
//            "rank": "1",
//            "price_usd": "13773.3",
//            "price_btc": "1.0",
//            "24h_volume_usd": "15031300000.0",
//            "market_cap_usd": "230994245575",
//            "available_supply": "16771162.0",
//            "total_supply": "16771162.0",
//            "max_supply": "21000000.0",
//            "percent_change_1h": "-1.19",
//            "percent_change_24h": "-6.64",
//            "percent_change_7d": "-6.19",
//            "last_updated": "1514624961"
//    }
//]
    String id;
    String name;
    String symbol;
    float price_usd;
    float price_btc;
    float volume_usd_24h;
    float market_cap_usd;
    float available_supply;
    float total_supply;
    float max_supply;
    float percent_change_1h;
    float percent_change_24h;
    float percent_change_7d;
    String last_updated;

    protected UsdToBtcModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        symbol = in.readString();
        price_usd = in.readFloat();
        price_btc = in.readFloat();
        volume_usd_24h = in.readFloat();
        market_cap_usd = in.readFloat();
        available_supply = in.readFloat();
        total_supply = in.readFloat();
        max_supply = in.readFloat();
        percent_change_1h = in.readFloat();
        percent_change_24h = in.readFloat();
        percent_change_7d = in.readFloat();
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
        parcel.writeFloat(price_usd);
        parcel.writeFloat(price_btc);
        parcel.writeFloat(volume_usd_24h);
        parcel.writeFloat(market_cap_usd);
        parcel.writeFloat(available_supply);
        parcel.writeFloat(total_supply);
        parcel.writeFloat(max_supply);
        parcel.writeFloat(percent_change_1h);
        parcel.writeFloat(percent_change_24h);
        parcel.writeFloat(percent_change_7d);
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

    public float getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(float price_usd) {
        this.price_usd = price_usd;
    }

    public float getPrice_btc() {
        return price_btc;
    }

    public void setPrice_btc(float price_btc) {
        this.price_btc = price_btc;
    }

    public float getVolume_usd_24h() {
        return volume_usd_24h;
    }

    public void setVolume_usd_24h(float volume_usd_24h) {
        this.volume_usd_24h = volume_usd_24h;
    }

    public float getMarket_cap_usd() {
        return market_cap_usd;
    }

    public void setMarket_cap_usd(float market_cap_usd) {
        this.market_cap_usd = market_cap_usd;
    }

    public float getAvailable_supply() {
        return available_supply;
    }

    public void setAvailable_supply(float available_supply) {
        this.available_supply = available_supply;
    }

    public float getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(float total_supply) {
        this.total_supply = total_supply;
    }

    public float getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(float max_supply) {
        this.max_supply = max_supply;
    }

    public float getPercent_change_1h() {
        return percent_change_1h;
    }

    public void setPercent_change_1h(float percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    public float getPercent_change_24h() {
        return percent_change_24h;
    }

    public void setPercent_change_24h(float percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
    }

    public float getPercent_change_7d() {
        return percent_change_7d;
    }

    public void setPercent_change_7d(float percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }
}
