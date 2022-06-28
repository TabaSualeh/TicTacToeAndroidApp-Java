package com.tabaSualeh.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;

public class Menu extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    public static final String one1 = "com.tabaSualeh.tictactoe.one";
    public static final String two2 = "com.tabaSualeh.tictactoe.two";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);


        //Admob Start
        setAds();


    }
    public void submit(View view){
        Intent play = new Intent(this,MainActivity.class);
        EditText one = findViewById(R.id.playerOne);
        EditText two = findViewById(R.id.playerTwo);
        String p1= one.getText().toString();
        String p2= two.getText().toString();
        if(p1.isEmpty()){
            Toast.makeText(this, "Please Enter Player One Name", Toast.LENGTH_SHORT).show();
        }
        else if(p2.isEmpty()){
            Toast.makeText(this, "Please Enter Player Two Name", Toast.LENGTH_SHORT).show();
        }
        else {
            play.putExtra(one1, p1);
            play.putExtra(two2, p2);
            one.setText(null);
            two.setText(null);
            //Ad starts here
            if (mInterstitialAd != null) {
                mInterstitialAd.show(Menu.this);

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        startActivity(play);
                        setAds();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        super.onAdFailedToShowFullScreenContent(adError);
                        setAds();
                        mInterstitialAd.show(Menu.this);
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        super.onAdShowedFullScreenContent();
                        mInterstitialAd = null;
                    }
                });

            } else {
                startActivity(play);
            }

        }
    }
    public void setAds(){

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(Menu.this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });

    }
}
