package com.example.showjoke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowJokeActivity extends AppCompatActivity {

    public static final String JOKE_INTENT_KEY = "joke_intent_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_joke);

        TextView jokeTextView = findViewById(R.id.tv_joke);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(JOKE_INTENT_KEY)){
            String joke = intent.getStringExtra(JOKE_INTENT_KEY);
            jokeTextView.setText(joke);
        }
    }
}
