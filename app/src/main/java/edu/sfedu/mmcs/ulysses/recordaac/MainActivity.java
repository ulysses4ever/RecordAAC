package edu.sfedu.mmcs.ulysses.recordaac;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    MediaRecorder recorder = new MediaRecorder();

    boolean isStarted = false;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void initRecorder() {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setAudioEncodingBitRate(154000);
        recorder.setAudioSamplingRate(48000);
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC);
        recorder.setOutputFile(path.toString() + "/" +
                dateFormatter.format(new Date()) + ".aac");
        try {
            recorder.prepare();
        }

        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't prepare MediaRecorder:\n" + e.getStackTrace());
        }

    }

    public void onClickRec(View view) {
        Button b = (Button) view;

        if (isStarted) {
            recorder.stop();
            isStarted = false;
            b.setText("REC");
        }
        else {
            initRecorder();
            isStarted = true;
            b.setText("Stop");
            recorder.start();
        }
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
