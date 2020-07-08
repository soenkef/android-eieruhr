package com.example.eieruhr;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar timerSeekBar;
    private TextView timerTextView;
    private boolean counterIsActive = false;
    private Button controllerButton;
    private CountDownTimer countDownTimer;

    public void updateTimer(int secondsLeft) {

        int minutes = (int) secondsLeft / 60;
        int seconds = (int) secondsLeft - minutes * 60;

        String secondsString = Integer.toString(seconds);
        String minutesString = Integer.toString(minutes);

        if (seconds < 10) {
            secondsString = String.format("%02d", seconds);
        }

        if (minutes < 10) {
            minutesString = String.format("%02d", minutes);
        }

        this.timerTextView.setText(minutesString + ":" + secondsString);

    }

    public void controlTimer(View view) {

        if (this.counterIsActive == false) {

            this.counterIsActive = true;
            this.timerSeekBar.setEnabled(false);
            this.controllerButton.setText("Stop");

            this.countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                    //Log.i("Sekunden übrig", String.valueOf(millisUntilFinished/1000));
                }

                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();
                    resetTimer();
                }

            }.start();

        } else {
            resetTimer();
        }

    }

    public void resetTimer(){
        this.counterIsActive = false;
        this.timerSeekBar.setProgress(30);
        this.countDownTimer.cancel();
        this.timerSeekBar.setEnabled(true);
        this.timerTextView.setText("00:30");
        this.controllerButton.setText("Start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        this.timerTextView = (TextView) findViewById(R.id.timerTextView);
        this.controllerButton = (Button) findViewById(R.id.controllerButton);

        this.timerSeekBar.setMax(600);
        this.timerSeekBar.setProgress(30);

        this.timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
