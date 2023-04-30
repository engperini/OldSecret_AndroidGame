package com.perinidev.oldsecret;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.text.LineBreaker;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class InstructionsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_MAIN_ACTIVITY = 1;
    private AdView mAdView;

    private Handler handler;
    private String text;
    private TextView textView;
    private ScrollView scrollView;


    Button skipButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        skipButton = (Button) findViewById(R.id.button_skip);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //adds
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(InstructionsActivity.this, history.class);
                if (isTaskRoot()) {
                    // Start the history activity if it's not already running
                    startActivityForResult(mainIntent, REQUEST_CODE_MAIN_ACTIVITY);
                }
                // when user click this button after the first time, just close the activity and return to history activity
                else {
                    finish();
                }

            }
        });

        //scroll automatically textview and show text letter by letter

        textView = findViewById(R.id.textView4);
        textView.setText("");
        scrollView = findViewById(R.id.scrollView);
        text = (String) getText(R.string.instruction2);

        //scroll automatically textview and show text letter by letter
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            int index = 0;
            final CharSequence charSequence = text;
            final int length = charSequence.length();
            final int delay =40;

            @Override
            public void run() {
                String builder = String.valueOf(textView.getText()) +
                        charSequence.charAt(index);
                textView.setText(builder);

                //scroll to the bottom of the textview
                scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));

                //justify the text after it is displayed
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                }

                if (index < length-1){
                    index++;
                    handler.postDelayed(this, delay);
                }


            }
        },2000);

    }

}
