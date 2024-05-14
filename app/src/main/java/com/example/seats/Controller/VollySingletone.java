package com.example.seats.Controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VollySingletone {
    private static VollySingletone mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    public VollySingletone(Context context) {
       mCtx=context;
        mRequestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue==null){
            mRequestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return mRequestQueue;
    }

    public static synchronized VollySingletone getInstance(Context context){
        if(mInstance==null){
            mInstance= new VollySingletone(context);

        }
        return mInstance;
    }
public <T>void AddToequestQueue(Request<T>req){
        getRequestQueue().add(req);
}
}
