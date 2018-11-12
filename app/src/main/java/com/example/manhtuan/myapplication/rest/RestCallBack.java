package com.example.manhtuan.myapplication.rest;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RestCallBack<T1,T2> implements Callback {

    public interface ResponseListener<T2>{
        void onSuccess(T2 objT2);
    }
    private ResponseListener mResponseListener;

    public RestCallBack(ResponseListener mResponseListener){
        this.mResponseListener = mResponseListener;
    }

    public abstract T2 getData(T1 objT1);
    @Override
    public void onResponse(Call call, Response response) {
        if (response == null) {
            return;
        }
        if (!response.isSuccessful()) {
            // xu ly
            return;
        }
        if (response.body() == null) {
            //response.message();
            return;
        }
        T2 objT2= getData((T1) response.body());
        mResponseListener.onSuccess(objT2);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.e("asdf","onFailure" + t.getMessage());
    }
}
