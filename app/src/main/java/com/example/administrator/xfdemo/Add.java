package com.example.administrator.xfdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Add extends AppCompatActivity {

    ImageView speack;
    EditText voiceText;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        speack = (ImageView) findViewById(R.id.speack);
        voiceText = (EditText) findViewById(R.id.voiceText);
        send = (Button) findViewById(R.id.send);

        //点击speack 开始录音
        speack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListenHelper.listen(Add.this);
            }
        });

    }
}
