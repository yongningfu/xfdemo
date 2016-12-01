package com.example.administrator.xfdemo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.speech.SpeechComponent;

public class Add extends AppCompatActivity {

    private ImageView speack;
    private EditText voiceText;
    private Button send;
    private String filePath = null;
    private String transText = null;
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

//                ListenHelper.setOnResultListener(Add.this, new ListenHelper.OnResultListener() {
//                    @Override
//                    public void onResult(String fileId, String result) {
//                        ListenHelper.showTip(Add.this, ListenHelper.getListenerPath(fileId));
//                        voiceText.setText(result);
//                        filePath = ListenHelper.getListenerPath(fileId);
//                        transText = result;
//                    }
//                });

                speack.setImageResource(R.drawable.playing);
               final SpeechRecognizer mIat =  ListenHelper.setOnResultListenerWithNoDialog(Add.this, new ListenHelper.OnResultListener() {
                    @Override
                    public void onResult(String fileId, String result) {
                        ListenHelper.showTip(Add.this, ListenHelper.getListenerPath(fileId));
                        voiceText.setText(result);
                        filePath = ListenHelper.getListenerPath(fileId);
                        transText = result;
                    }

                   @Override
                   public void onError(String errorMsg) {
                       ListenHelper.showTip(Add.this, errorMsg + "哈哈哈 出错啦");
                       speack.setImageResource(R.drawable.microphone);
                   }
               });


                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        //停止
                        mIat.stopListening();
                        ListenHelper.showTip(Add.this,  "stop 这里");
                        speack.setImageResource(R.drawable.microphone);
                    }
                };

                handler.postDelayed(runnable, 70000);






            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (filePath != null && transText != null) {

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("filePath", filePath);
                    bundle.putString("transText", transText);
                    intent.putExtras(bundle);
                    //向前面的activity传递会增加的数据
                    Add.this.setResult(0, intent);
                    finish();

                } else {
                    ListenHelper.showTip(Add.this, "还没录音？");
                }

            }
        });
    }
}
