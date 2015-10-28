package ru.dmitrybochkov.webinartesttask;

import android.app.Application;
import android.content.Context;
/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */

/**
 * Base Singleton class.
 */
public class WebinarTestTask extends Application {

    private static WebinarTestTask instance;

    public WebinarTestTask(){
        instance = this;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
    }


    public static Context provideContext(){
        return instance;
    }
}
