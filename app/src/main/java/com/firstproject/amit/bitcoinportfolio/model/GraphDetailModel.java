package com.firstproject.amit.bitcoinportfolio.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by amit on 30-12-2017.
 */

public class GraphDetailModel implements Parcelable {
//    "status": "ok",
//            "name": "Market Price (USD)",
//            "unit": "USD",
//            "period": "day",
//            "description": "Average USD market price across major bitcoin exchanges.",
//            "values": [
//    {
//        "x": 1504224000,
//            "y": 4911.74
//    },

    String status;
    String name;
    String unit;
    String period;
    String description;
    ArrayList<ValuesModel> values;


    protected GraphDetailModel(Parcel in) {
        status = in.readString();
        name = in.readString();
        unit = in.readString();
        period = in.readString();
        description = in.readString();
        values = in.createTypedArrayList(ValuesModel.CREATOR);
    }

    public static final Creator<GraphDetailModel> CREATOR = new Creator<GraphDetailModel>() {
        @Override
        public GraphDetailModel createFromParcel(Parcel in) {
            return new GraphDetailModel(in);
        }

        @Override
        public GraphDetailModel[] newArray(int size) {
            return new GraphDetailModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(name);
        parcel.writeString(unit);
        parcel.writeString(period);
        parcel.writeString(description);
        parcel.writeTypedList(values);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<ValuesModel> getValues() {
        return values;
    }

    public void setValues(ArrayList<ValuesModel> values) {
        this.values = values;
    }
}
