package com.uraroji.android.nearbycurry;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class NearbyCurryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // ImageLoaderの初期化
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .displayer(new FadeInBitmapDisplayer(getApplicationContext().getResources().getInteger(R.integer.image_loader_fade_in_duration)))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
    }

}
