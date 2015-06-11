package com.example.LokiHostSample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loki.sdk.host.LokiInstaller;

import java.io.InputStream;

public class SampleActivity extends Activity implements View.OnClickListener {

    private static final String LOKI_OTA_PACKAGE = "loki-ota.zip";

    private Button install;
    private boolean installSuccess;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        install = (Button) findViewById(R.id.install);
        install.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LokiInstaller installer = new LokiInstaller(SampleActivity.this);
                installSuccess = false;
                try {
                    InputStream stockPackage = getAssets().open("loki-ota-1.zip");
                    installSuccess = installer.install(stockPackage, 1, "atlas", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SampleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SampleActivity.this, "Install result is " + installSuccess, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }
}
