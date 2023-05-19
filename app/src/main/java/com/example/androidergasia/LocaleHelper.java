package com.example.androidergasia;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class LocaleHelper {

    public static Context setLocale(Context context, String language) {
        {
            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLocale(new Locale(language));
            return context.createConfigurationContext(configuration);
        }
    }
}