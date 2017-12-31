package com.firstproject.amit.bitcoinportfolio.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amit on 31-12-2017.
 */

public class InvestmentModel implements Parcelable {
    int Amount;
    int rate;
    int totalPrice;
    boolean isBuy;
    String timeStamp;

    public InvestmentModel() {

    }

    protected InvestmentModel(Parcel in) {
        Amount = in.readInt();
        rate = in.readInt();
        totalPrice = in.readInt();
        isBuy = in.readByte() != 0;
        timeStamp = in.readString();
    }

    public static final Creator<InvestmentModel> CREATOR = new Creator<InvestmentModel>() {
        @Override
        public InvestmentModel createFromParcel(Parcel in) {
            return new InvestmentModel(in);
        }

        @Override
        public InvestmentModel[] newArray(int size) {
            return new InvestmentModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Amount);
        parcel.writeInt(rate);
        parcel.writeInt(totalPrice);
        parcel.writeByte((byte) (isBuy ? 1 : 0));
        parcel.writeString(timeStamp);
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
