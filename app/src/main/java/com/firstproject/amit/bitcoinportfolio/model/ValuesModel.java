package com.firstproject.amit.bitcoinportfolio.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amit on 30-12-2017.
 */

public class ValuesModel implements Parcelable {
    int x;
    float y;

    protected ValuesModel(Parcel in) {
        x = in.readInt();
        y = in.readFloat();
    }

    public static final Creator<ValuesModel> CREATOR = new Creator<ValuesModel>() {
        @Override
        public ValuesModel createFromParcel(Parcel in) {
            return new ValuesModel(in);
        }

        @Override
        public ValuesModel[] newArray(int size) {
            return new ValuesModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(x);
        parcel.writeFloat(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
