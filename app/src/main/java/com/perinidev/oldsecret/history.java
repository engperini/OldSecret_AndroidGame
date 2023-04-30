package com.perinidev.oldsecret;

//code to do transition in background and show text each chapter of the game

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
import android.graphics.text.LineBreaker;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.widget.Toast;


public class history extends AppCompatActivity {

    private int currentChapter = 2;

    int current;

    private ImageView backgroundImage;
    private TextView textViewHistory;
    private TextView textViewTitle;

    private Button playButton;

    private Button buttonHelp;

    private ScrollView scrollView;

    private boolean isTextFullyDisplayed;

    public void intentToMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(history.this, MainActivity.class);
                startActivityForResult(mainIntent, 1);
            }
        }, 17000);
    }

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
        setContentView(R.layout.activity_history);

        backgroundImage = findViewById(R.id.background_image);
        textViewHistory = findViewById(R.id.textHistory);
        playButton = findViewById(R.id.play_button);
        textViewTitle = findViewById(R.id.textView2);
        buttonHelp = findViewById(R.id.help_button);
        playButton.setEnabled(false);



        displayTransition(R.drawable.cap1, R.string.textcap1_1, R.string.title_textcap1_1);


        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(history.this, InstructionsActivity.class);
                startActivityForResult(mainIntent, 1);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentChapter) {
                    case 1:
                        displayTransition(R.drawable.cap1, R.string.textcap1_1, R.string.title_textcap1_1);
                        break;


                    case 2:
                        displayTransition(R.drawable.cap2, R.string.textcap1_2, R.string.titlecap1_2);
                        //this intent should only execute when displayTransition above is done
                        intentToMain();
                        break;

                    case 3:
                        intentToMain();
                        displayTransition(R.drawable.cap2, R.string.textcap2, R.string.titlecap1_2);
                        break;

                    case 4:
                        //this intent should only execute when displayTransition above is done
                        //intentToMain();
                        displayTransition(R.drawable.cap3, R.string.textcap2_1, R.string.titlecap2);
                        break;

                    case 5:
                        displayTransition(R.drawable.cap3, R.string.textcap2_2, R.string.titlecap2);
                        //this intent should only execute when displayTransition above is done
                        intentToMain();
                        break;

                    case 6:
                        displayTransition(R.drawable.cap41, R.string.textcap3, R.string.titlecap3);
                        break;

                    case 7:
                        displayTransition(R.drawable.cap41, R.string.textcap3_2, R.string.titlecap3);
                        break;

                    case 8:
                        displayTransition(R.drawable.cap41, R.string.textcap3_3, R.string.titlecap3);
                        //this intent should only execute when displayTransition above is done
                        intentToMain();
                        break;

                    case 9:
                        displayTransition(R.drawable.cap42, R.string.textcap3_31, R.string.titlecap3_31);
                        break;

                    case 10:
                        displayTransition(R.drawable.cap42, R.string.textcap4_1, R.string.titlecap4);
                        break;

                    case 11:
                        displayTransition(R.drawable.cap42, R.string.textcap4_2, R.string.titlecap4);
                        intentToMain();
                        break;

                    case 12:
                        displayTransition(R.drawable.cap42, R.string.textcap4_3, R.string.titlecap4);

                        break;

                    case 13:
                        displayTransition(R.drawable.cap42, R.string.textcap4_4, R.string.titlecap4);
                        break;

                    case 14:
                        displayTransition(R.drawable.cap42, R.string.textcap4_5, R.string.titlecap4);
                        break;

                    case 15:
                        //this intent should only execute when displayTransition above is done
                        intentToMain();
                        displayTransition(R.drawable.cap5, R.string.textcap5, R.string.titlecap5);
                        break;

                    case 16:
                        displayTransition(R.drawable.cap5, R.string.textcap5_1, R.string.titlecap5);
                        break;

                    case 17:
                        //this intent should only execute when displayTransition above is done
                        intentToMain();
                        displayTransition(R.drawable.cap5, R.string.textcap5_2, R.string.titlecap5);
                        break;

                    case 18:
                        displayTransition(R.drawable.splash_back_forest, R.string.textEpilogo, R.string.titlecapEpilogo);
                        break;

                    case 19:
                        displayTransition(R.drawable.splash_back_forest, R.string.textEpilogo1, R.string.titlecapEpilogo);
                        //this intent should only execute when displayTransition above is done
                        intentToMain();
                        break;

                    case 20:
                        displayTransition(R.drawable.splash_back_forest, R.string.textEpilogo2, R.string.titlecapEpilogo);
                        break;

                    case 21:
                        displayTransition(R.drawable.splash_back_forest, R.string.textEpilogo3, R.string.titlecapEpilogo);
                        break;

                    case 22:
                        //this intent should only execute when displayTransition above is done
                        intentToMain();
                        displayTransition(R.drawable.prize, R.string.textEnd, R.string.app_name);
                        break;



                }
                currentChapter++;
                if (currentChapter > 22) {
                    currentChapter = 1;
                }
            }
        });
    }

    private void displayTransition(int backgroundId, int textId, int textTitleId) {
        // Transição de imagem de fundo
        TransitionDrawable transition = new TransitionDrawable(new Drawable[] {
                backgroundImage.getDrawable(),
                ContextCompat.getDrawable(this, backgroundId)
        });
        backgroundImage.setImageDrawable(transition);
        transition.startTransition(500);

        // Display Titulo
        textViewTitle.setText(textTitleId);


        // Transição de texto
        displayText(textId);
    }

    private void displayText(int textId) {
        playButton.setEnabled(false);
        // Código para exibir o texto palavra por palavra
        textViewHistory.setText("");
        scrollView = findViewById(R.id.scrollView);
        String text = (String) getText(textId);
        //String[] text = getResources().getString(textId).split(" ");

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            int index = 0;
            final CharSequence charSequence = text;
            final int length = charSequence.length();
            final int delay =40;

            // Definir variável para verificar se o texto foi totalmente exibido
            boolean isTextFullyDisplayed = false;

            // Código para exibir o texto palavra por palavra
            @Override
            public void run() {


                String builder = String.valueOf(textViewHistory.getText()) +
                        charSequence.charAt(index);
                textViewHistory.setText(builder);

                // Definir a variável como verdadeira quando o último caractere for adicionado
                if (index == length - 1) {
                    isTextFullyDisplayed = true;
                }

                //justify the text after it is displayed
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    textViewHistory.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                }

                if (index < length-1){
                    index++;
                    handler.postDelayed(this, delay);
                }
                //scroll to the bottom of the textview
                scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));

                // Verificar se o texto foi totalmente exibido e habilitar o botão
                if (isTextFullyDisplayed) {
                    playButton.setEnabled(true);

                }

            }

        };
        handler.postDelayed(runnable, 50);

    }


}

