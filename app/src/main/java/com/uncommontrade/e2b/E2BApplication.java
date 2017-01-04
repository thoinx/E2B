package com.uncommontrade.e2b;

import android.app.Application;
import android.content.Context;

/**
 * Created by thoin_000 on 5/16/2016.
 */
public class E2BApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        E2BApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return E2BApplication.context;
    }

}
