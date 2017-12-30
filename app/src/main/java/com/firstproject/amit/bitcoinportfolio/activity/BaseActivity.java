package com.firstproject.amit.bitcoinportfolio.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by amit_pc on 23-09-2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract void getId();
    public abstract void setData();
    public abstract void setListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        getId();
        setListener();
        setData();
    }
}
