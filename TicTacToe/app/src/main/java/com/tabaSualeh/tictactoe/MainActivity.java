package com.tabaSualeh.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private String ad1 = "ca-app-pub-3940256099942544/1033173712";
    private String ad2 = "ca-app-pub-3940256099942544/1033173712";
    String PlayerOne;
    String PlayerTwo;
    boolean gameActive = true;
    // 0 - 0
    // 1 - x
    int activePlayer=0;
    //Game State
    //  0 - 0
    //  1 - x
    //  2 - Nulll
    int gamestate[]={2,2,2,2,2,2,2,2,2};
    int winningPositions[][]={  {0,1,2},{3,4,5},{6,7,8},
                            {0,3,6},{1,4,7},{2,5,8},
                            {0,4,8},{2,4,6}};


    public void playerTap(View view){
        ImageView img =(ImageView) view;
        int tappedImage=Integer.parseInt(img.getTag().toString());
        Button playAgnBTN = findViewById(R.id.play_again);
        Button exitBTN = findViewById(R.id.exit);
        TextView status2=findViewById(R.id.status2);
        if(gamestate[tappedImage]==2 && gameActive){
            gamestate[tappedImage]=activePlayer;
            img.setTranslationX(-1000f);
            if(activePlayer==0){
                img.setImageResource(R.drawable.on);
                activePlayer=1;
                TextView status=findViewById(R.id.status);
                status.setText(this.PlayerTwo+"'s Turn");
                status.setTextColor(Color.parseColor("#1205C5"));

            }
            else{
                img.setImageResource(R.drawable.xn);
                activePlayer=0;
                TextView status=findViewById(R.id.status);
                status.setText(this.PlayerOne+"'s Turn");
                status.setTextColor(Color.parseColor("#F62C2C"));
            }

            img.animate().translationXBy(1000f);
        }
        String winner=null ;
        for(int winPosition[]:winningPositions) {

//             int winningPositions[][]={  0{0,1,2},1{3,4,5},2{6,7,8},
//                                     3{0,3,6},4{1,4,7},5{2,5,8},
//                                     6{0,4,8},7{2,4,6}};
             if (gamestate[winPosition[0]] != 2 &&
                    gamestate[winPosition[0]] == gamestate[winPosition[1]] &&
                    gamestate[winPosition[1]] == gamestate[winPosition[2]]) {
                TextView status = findViewById(R.id.status);
                //Someday Won - Find Out Who
                if (gamestate[winPosition[0]] == 0) {
                    winner = this.PlayerOne+" has Won";
                    status.setTextColor(Color.parseColor("#F62C2C"));

                } else if (gamestate[winPosition[0]]==1) {
                    winner = this.PlayerTwo+" has Won ";
                    status.setTextColor(Color.parseColor("#1205C5"));
                }
                gameActive=false;
                playAgnBTN.setVisibility(View.VISIBLE);
                exitBTN.setVisibility(View.VISIBLE);
                status.setText(winner);
                status2.setText(null);
        }
    }
        boolean emptySpace = true;
        for (int box : gamestate) {
            if (box == 2) {
                emptySpace = true;
                break;
            }
            else{
                emptySpace=false;
            }
        }
        if (!emptySpace && gameActive) {
            winner = "Game Tied";
            playAgnBTN.setVisibility(View.VISIBLE);
            exitBTN.setVisibility(View.VISIBLE);
            TextView status = findViewById(R.id.status);
            status.setText(winner);

            status2.setText(null);
            gameActive = false;
        }
    }
    public void gameReset(View view){

        if (mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when fullscreen content is dismissed.
                    reset();
                    setAds(ad1);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when fullscreen content failed to show.
                    super.onAdFailedToShowFullScreenContent(adError);
                    setAds(ad1);
                    mInterstitialAd.show(MainActivity.this);
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
            reset();
        }





    }
    public void back(View view){

        if (mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when fullscreen content is dismissed.
                    goneback();
                    setAds(ad2);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when fullscreen content failed to show.
                    super.onAdFailedToShowFullScreenContent(adError);
                    setAds(ad2);
                    mInterstitialAd.show(MainActivity.this);
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
            goneback();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAds(ad1);
        setAds(ad2);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Intent menu =getIntent();
        this.PlayerOne=menu.getStringExtra(Menu.one1);
        this.PlayerTwo=menu.getStringExtra(Menu.two2);
        TextView status=findViewById(R.id.status);
        status.setText(this.PlayerOne+"'s Turn");
        status.setTextColor(Color.parseColor("#F62C2C"));
        TextView status2=findViewById(R.id.status2);
        status2.setText("Tap to Play");
        Button playAgnBTN = findViewById(R.id.play_again);
        Button exitBTN = findViewById(R.id.exit);
        playAgnBTN.setVisibility(View.GONE);
        exitBTN.setVisibility(View.GONE);


    }

    public void setAds(String adUnitId){

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(MainActivity.this,adUnitId, adRequest,
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
    public void reset(){

        gameActive=true;
        activePlayer=0;
        TextView status = findViewById(R.id.status);
        status.setText(this.PlayerOne+"'s Turn");
        status.setTextColor(Color.parseColor("#F62C2C"));
        TextView status2=findViewById(R.id.status2);
        status2.setText("Tap to Play");
        Button playAgnBTN = findViewById(R.id.play_again);
        Button exitBTN = findViewById(R.id.exit);
        playAgnBTN.setVisibility(View.GONE);
        exitBTN.setVisibility(View.GONE);
        for(int i=0;i< gamestate.length;i++){
            gamestate[i]=2;
        }
        ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);

    }
    public void goneback(){
        finish();
        Intent home=new Intent(MainActivity.this,Home.class);
        startActivity(home);
    }
}
