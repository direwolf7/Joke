package com.example.joke.coding;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.joke.R;
import com.example.joke.model.Joke;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private JokeApi api;

    private TextView content;
    private CheckBox cb_any, cb_programing, cb_miscellaneous, cb_dark;
    private CheckBox cb_nsfw, cb_religious, cb_political, cb_racist, cb_sexist;
    private CheckBox cb_single, cb_twopart;
    private LinearLayout typeLayout;
    private Button btn_submit;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        jokeCategoryStates();

        jokeTypeState();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(JokeApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        api = retrofit.create(JokeApi.class);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                Map<String, String> parameters = new HashMap<>();
                Call<Joke> call = getJoke(parameters);
                call.enqueue(new Callback<Joke>() {
                    @Override
                    public void onResponse(Call<Joke> call, Response<Joke> response) {
                        Joke joke = response.body();

                        if (response.isSuccessful()) {
                            if (cb_twopart.isChecked() && !cb_single.isChecked() && joke.getJoke() == null) {
                                content.setText(joke.getSetup() + "\n\n");
                                content.append(joke.getDelivery());
                            } else if (cb_single.isChecked() && !cb_twopart.isChecked() && joke.getJoke() != null) {
                                content.setText(joke.getJoke());
                            } else if (cb_single.isChecked() && cb_twopart.isChecked()) {
                                if (joke.getJoke() == null) {
                                    content.setText(joke.getSetup() + "\n\n");
                                    content.append(joke.getDelivery());
                                } else if (joke.getJoke() != null) {
                                    content.setText(joke.getJoke());
                                }
                            }
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<Joke> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Internet connecting error", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void jokeTypeState() {
        cb_single.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b && !cb_twopart.isChecked()) {
                typeLayout.setBackgroundResource(R.drawable.error_bg);
                btn_submit.setEnabled(false);
            } else {
                typeLayout.setBackgroundResource(R.drawable.items_bg);
                btn_submit.setEnabled(true);
            }
        });
        cb_twopart.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b && !cb_single.isChecked()) {
                typeLayout.setBackgroundResource(R.drawable.error_bg);
                btn_submit.setEnabled(false);
            } else {
                typeLayout.setBackgroundResource(R.drawable.items_bg);
                btn_submit.setEnabled(true);
            }
        });
    }

    private void jokeCategoryStates() {
        cb_any.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                cb_dark.setEnabled(false);
                cb_programing.setEnabled(false);
                cb_miscellaneous.setEnabled(false);
            } else {
                cb_dark.setEnabled(true);
                cb_programing.setEnabled(true);
                cb_miscellaneous.setEnabled(true);
            }
        });
        cb_programing.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                cb_any.setEnabled(false);
            } else {
                if (!cb_miscellaneous.isChecked() && !cb_dark.isChecked())
                    cb_any.setEnabled(true);
            }
        });
        cb_miscellaneous.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                cb_any.setEnabled(false);
            } else {
                if (!cb_programing.isChecked() && !cb_dark.isChecked())
                    cb_any.setEnabled(true);
            }
        });
        cb_dark.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                cb_any.setEnabled(false);
            } else {
                if (!cb_programing.isChecked() && !cb_miscellaneous.isChecked())
                    cb_any.setEnabled(true);
            }
        });
    }

    private Call<Joke> getJoke(Map<String, String> parameters) {
        Call<Joke> call;
        if (cb_any.isChecked()) {
            call = api.getJoke();
        } else if (cb_programing.isChecked()) {
            FlagsState(parameters);
            call = api.getProgrammingJoke(parameters);
        } else if (cb_miscellaneous.isChecked()) {
            FlagsState(parameters);
            call = api.getMiscellaneousJoke(parameters);
        } else if (cb_dark.isChecked()) {
            FlagsState(parameters);
            call = api.getDarkJoke(parameters);
        } else if (cb_programing.isChecked() && cb_miscellaneous.isChecked()) {
            FlagsState(parameters);
            call = api.getProgrammingAndMiscellaneousJoke(parameters);
        } else if (cb_programing.isChecked() && cb_dark.isChecked()) {
            FlagsState(parameters);
            call = api.getProgrammingAndDarkJoke(parameters);
        } else if (cb_miscellaneous.isChecked() && cb_dark.isChecked()) {
            FlagsState(parameters);
            call = api.getMiscellaneousAndDarkJoke(parameters);
        } else if (cb_programing.isChecked() && cb_miscellaneous.isChecked() && cb_dark.isChecked()) {
            call = api.getJoke();
        } else call = api.getJoke();
        return call;
    }

    private void FlagsState(Map<String, String> parameters) {
        if (cb_nsfw.isChecked())
            parameters.put("nsfw", "true");
        if (cb_religious.isChecked())
            parameters.put("religious", "true");
        if (cb_political.isChecked())
            parameters.put("political", "true");
        if (cb_racist.isChecked())
            parameters.put("racist", "true");
        if (cb_sexist.isChecked())
            parameters.put("sexist", "true");
    }

    private void init() {
        cb_any = findViewById(R.id.cb_any);
        cb_programing = findViewById(R.id.cb_programming);
        cb_miscellaneous = findViewById(R.id.cb_miscellaneous);
        cb_dark = findViewById(R.id.cb_dark);
        cb_nsfw = findViewById(R.id.cb_nsfw);
        cb_religious = findViewById(R.id.cb_religious);
        cb_political = findViewById(R.id.cb_political);
        cb_racist = findViewById(R.id.cb_racist);
        cb_sexist = findViewById(R.id.cb_sexist);
        cb_single = findViewById(R.id.cb_single);
        cb_twopart = findViewById(R.id.cb_twopart);

        typeLayout = findViewById(R.id.layout_joke_type);

        btn_submit = findViewById(R.id.btn_submit);

        content = findViewById(R.id.content);

        progressBar = findViewById(R.id.progressbar);
    }
}
