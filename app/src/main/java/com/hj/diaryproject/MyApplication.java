package com.hj.diaryproject;

import android.app.Application;
import android.graphics.Typeface;

public class MyApplication extends Application {

    public void onCreate() {

        super.onCreate();


        TypefaceManager.mKopubBatangBoldTypeface = Typeface.createFromAsset(getAssets(), "KoPubBatangBold.ttf");
        TypefaceManager.mKopubBatangMediumTypeface = Typeface.createFromAsset(getAssets(), "KoPubBatangMedium.ttf");  //asset > fonts 폴더 내 폰트파일 적용
        TypefaceManager.mKopubBatangLightTypeface = Typeface.createFromAsset(getAssets(), "KoPubBatangLight.ttf");

        TypefaceManager.mKopubDotumBoldTypeface = Typeface.createFromAsset(getAssets(), "KoPubDotumBold.ttf");
        TypefaceManager.mKopubDotumMediumTypeface = Typeface.createFromAsset(getAssets(), "KoPubDotumMedium.ttf");
        TypefaceManager.mKopubDotumLightTypeface = Typeface.createFromAsset(getAssets(), "KoPubDotumLight.ttf");

    }
}
