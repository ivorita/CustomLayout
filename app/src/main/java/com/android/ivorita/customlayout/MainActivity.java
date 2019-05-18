package com.android.ivorita.customlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        practiceView practiceView = findViewById(R.id.TempCtrlView);

        practiceView.setAngleRate(3);
        practiceView.setTemp(16, 70, 69);
    }
}
