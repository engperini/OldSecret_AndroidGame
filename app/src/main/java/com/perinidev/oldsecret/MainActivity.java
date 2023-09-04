package com.perinidev.oldsecret;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ImageView;

import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.os.Handler;

import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.os.CountDownTimer;

import android.media.MediaPlayer;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    int numeroAleatorio;
    static int numeroTentativas;

    TextView result;

    Button novo;

    TextView countdown_text;


    long startTime = 120000; // 30 seconds


    long timeSpent;

    CountDownTimer timer;

    MediaPlayer backgroundSound;
    MediaPlayer winSound;

    MediaPlayer overSound;

    private boolean backButtonPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (backButtonPressedOnce) {
            // Show exit message and exit the app
            //super.onBackPressed();
            finishAffinity();
        } else {
            backButtonPressedOnce = true;
            Toast.makeText(this, "Press the back button again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backButtonPressedOnce = false;
                }
            }, 2000); // Set a delay of 2 seconds to reset the flag
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //load ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        // sample ca-app-pub-3940256099942544/1033173712

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });



        numeroTentativas =0;
        numeroAleatorio = (int) (Math.random() * 100);
        result = (TextView) findViewById(R.id.result);
        result.setText("");
        novo = (Button) findViewById(R.id.new_game_button);

        ProgressBar progressBar = findViewById(R.id.progressBar);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);

        EditText palpiteEditText = findViewById(R.id.palpiteEditText);
        palpiteEditText.setText("");

        winSound = MediaPlayer.create(this,R.raw.win);
        overSound = MediaPlayer.create(this,R.raw.over);

        //plays background musica
        backgroundSound = MediaPlayer.create(this,R.raw.rain);
        backgroundSound.start();
        backgroundSound.setLooping(true);

        countdown_text = (TextView) findViewById(R.id.countdown_text);


        timer = new CountDownTimer(startTime, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown_text.setText(String.valueOf(millisUntilFinished / 1000));
                timeSpent = (startTime - millisUntilFinished) / 1000;
            }

            public void onFinish() {
                countdown_text.setText(R.string.loose);
                overSound.start();
                result.setText(R.string.new_game);

            }
        }.start();

        //done at keyboard, execute
        palpiteEditText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {


                    // To hide the keyboard
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(palpiteEditText.getWindowToken(), 0);


                    // To show the progress bar
                    progressBar.setVisibility(View.VISIBLE);
                    // To hide the progress bar after some time
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);

                            //get string to see if it empty
                            String palpite_string = palpiteEditText.getText().toString();

                            if (palpite_string.isEmpty()){

                                //mostrar para usuário
                                result.setText(R.string.palpite_errado);
                                imm.showSoftInput(palpiteEditText, InputMethodManager.SHOW_IMPLICIT);
                                return;
                            }

                            //not empty
                            int palpite = Integer.parseInt(palpite_string);

                            numeroTentativas++;


                            if (palpite < numeroAleatorio)   {
                                result.setText(R.string.palpite_abaixo);
                                palpiteEditText.setText("");
                                imm.showSoftInput(palpiteEditText, InputMethodManager.SHOW_IMPLICIT);
                            }
                            else if (palpite > numeroAleatorio) {
                                result.setText(R.string.palpite_acima);
                                palpiteEditText.setText("");
                                imm.showSoftInput(palpiteEditText, InputMethodManager.SHOW_IMPLICIT);
                            }
                            else {
                                //show result
                                winSound.start();
                                result.setText(getString(R.string.palpite_text, numeroAleatorio) + getString(R.string.palpite_text2, numeroTentativas) + " - " + timeSpent + " seg");
                                palpiteEditText.setText("");
                                timer.cancel();
                                imageView.setVisibility(View.VISIBLE);

                                //return history.java

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 3000);  // 1000 ms = 1 second




                            }

                        }
                    }, 1000);  // 1000 ms = 1 second

                    return true;
                }
                return false;
            }
        });


        novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

                imageView.setVisibility(View.INVISIBLE);
                numeroAleatorio = (int) (Math.random() * 100);
                result.setText("");
                palpiteEditText.setText("");
                timer.start();
                timeSpent = 0;
                numeroTentativas = 0;


            }
        });
    }




    @Override
    protected void onStop() {
        super.onStop();
        finish();
        if (backgroundSound != null) {
            backgroundSound.release();
            backgroundSound = null;
        }
    }





}

