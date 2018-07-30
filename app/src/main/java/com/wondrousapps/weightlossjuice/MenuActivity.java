package com.wondrousapps.weightlossjuice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.design.internal.BottomNavigationItemView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.lang.reflect.Field;


public class MenuActivity extends AppCompatActivity implements RewardedVideoAdListener{

    private WebView mWebMessage;
    private View title;
    View titleBar;
    private AdView mBannerAd;
    private InterstitialAd mInterstitialAd;

      private RewardedVideoAd mRewardedVideoAd;
    private BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mWebMessage.loadUrl("file:///android_asset/main/tab1.html");
                    navigation.setBackgroundColor(Color.parseColor("#2196F3"));
                    commonmetod();
                    return true;

                case R.id.navigation_dashboard:
                    mWebMessage.loadUrl("file:///android_asset/main/tab2.html");
                    navigation.setBackgroundColor(Color.parseColor("#673AB7"));
                    commonmetod();
                    return true;

                case R.id.navigation_notifications:
                    mWebMessage.loadUrl("file:///android_asset/main/tab3.html");
                    navigation.setBackgroundColor(Color.parseColor("#00BCD4"));
                    commonmetod();
                    return true;

                case R.id.navigation_app_info:
                    mWebMessage.loadUrl("file:///android_asset/main/tab4.html");
                    navigation.setBackgroundColor(Color.parseColor("#009688"));
                    WebSettings webSettings = mWebMessage.getSettings();
                    webSettings.setJavaScriptEnabled(false);

                    mWebMessage.setWebViewClient(new WebViewClient(){

                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            if (url != null && (url.startsWith("https://play.google.com/store/apps/details?id=com.wondrousapps.weightlossjuice") ||
                                    url.startsWith("https://play.google.com/store/apps/details?id=com.wondrousapps.weightlossjuice"))) {
                                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                                String shareDetail = getString(R.string.app_name) + " : \n" + "https://play.google.com/store/apps/details?id=" + getPackageName();
                                shareIntent.putExtra(Intent.EXTRA_TEXT, shareDetail);
                                startActivity(Intent.createChooser(shareIntent, "Share via :"));
                                return true;
                            }if (url!=null && (url.startsWith("https://play.google.com/store/search?q=wondrousapps"))){
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://play.google.com/store/search?q=wondrousapps"));
                                startActivity(intent);
                                return true;
                            }
                            else {
                                return false;
                            }
                        }
                    });
        //            commonmetod();
                    return true;
                    }
            return false;
        }
    };

    private void commonmetod() {
        mWebMessage.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        WebSettings webSettings = mWebMessage.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebMessage.canGoBack();

        mWebMessage.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && mWebMessage.canGoBack()) {
                    mWebMessage.goBack();
//                    if (mInterstitialAd.isLoaded()) {
//                        mInterstitialAd.show();
//                    }
                if (mRewardedVideoAd.isLoaded()) {
                   mRewardedVideoAd.show();
                }
                loadRewardedVideoAd();
                return true;
                }
                return false;
            }
        });
//        mWebMessage.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (Uri.parse(url).getScheme().equals("market")) {
//                    try {
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setData(Uri.parse(url));
//                        Activity host = (Activity) view.getContext();
//                        host.startActivity(intent);
//                        return true;
//                    } catch (ActivityNotFoundException e) {
//                        // Google Play app is not installed, you may want to open the app store link
//                        Uri uri = Uri.parse(url);
//                        view.loadUrl("http://play.google.com/store/apps/" + uri.getHost() + "?" + uri.getQuery());
//                        return false;
//                    }
//
//                }
//                return false;
//            }
//        });
        mWebMessage.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mWebMessage =  findViewById(R.id.message);
        mWebMessage.loadUrl("file:///android_asset/main/tab1.html");
        commonmetod();
        title = getWindow().findViewById(android.R.id.title);
//        titleBar = (View) title.getParent();
        MobileAds.initialize(this, String.valueOf(R.string.ad_id_banner));
        mBannerAd = (AdView) findViewById(R.id.adView);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
        showBannerAd();
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //mInterstitialAd.setAdUnitId(String.valueOf(R.string.ad_id_interstitial));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when the ad is displayed.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when when the interstitial ad is closed.
//            }
//        });
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                // Load the next interstitial.
//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//            }
//
//        });

        navigation = findViewById(R.id.navigation);
        navigation.setBackgroundColor(Color.parseColor("#2196F3"));
        navigation.setItemIconTintList(null);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            iconView.setLayoutParams(layoutParams);
            }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void loadRewardedVideoAd() {
    mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder()
                       // .addTestDevice("DB07DDA7FB3B42C3FD6DC85ACBD34693")
                        .build());
    }
    @Override
    public void onRewarded(RewardItem reward) {
//        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
//        reward.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
    }
    @Override
    public void onRewardedVideoAdLeftApplication() {
//        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
//        Toast.LENGTH_SHORT).show();
    }
       @Override
    public void onRewardedVideoAdClosed() {
        //Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        //Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRewardedVideoAdLoaded() {
        //Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRewardedVideoAdOpened() {
        //Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRewardedVideoStarted() {
//        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRewardedVideoCompleted() {
  //      Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
    }
    private void showBannerAd() {
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("DB07DDA7FB3B42C3FD6DC85ACBD34693")
                .build();
        mBannerAd.loadAd(adRequest);
    }

//    @Override
//    public void onResume() {
//        mRewardedVideoAd.resume(this);
//        super.onResume();
//    }
//    @Override
//    public void onPause() {
//        mRewardedVideoAd.pause(this);
//        super.onPause();
//    }
//    @Override
//    public void onDestroy() {
//        mRewardedVideoAd.destroy(this);
//        super.onDestroy();
//    }
    public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            //noinspection RestrictedApi
                 item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                 item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }
}