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


    SpeechRecognizer mIat;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //停止
            mIat.stopListening();

            //停止的话 讯飞里面可能有个不断发生请求的过程 不会立即onError 所以需要设置一下提示信息
            //前面关网络 中途开网络的话 它也是能识别的
            ListenHelper.showTip(Add.this,  "分析中");
            speack.setImageResource(R.drawable.microphone);
        }
    };

    // 开始 idle -> 加载录音-> record -> 成功-stop
    //                                -> 失败 idle
    enum STATE {IDLE, RECORD, STOP};
    private STATE state = STATE.IDLE;

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

                // 空闲就生成一个录音对象
                if (state == STATE.IDLE) {

                    handler.removeCallbacks(runnable);

                    state = STATE.RECORD;
                    speack.setImageResource(R.drawable.playing);
                    mIat =  ListenHelper.setOnResultListenerWithNoDialog(Add.this, new ListenHelper.OnResultListener() {
                        @Override
                        public void onResult(String fileId, String result) {

                            if (state == STATE.RECORD) {
                                state = STATE.STOP;
                                speack.setImageResource(R.drawable.microphone);
                                ListenHelper.showTip(Add.this, ListenHelper.getListenerPath(fileId));
                                voiceText.setText(result);
                                filePath = ListenHelper.getListenerPath(fileId);
                                transText = result;
                            }
                        }

                        @Override
                        public void onError(String errorMsg) {
                            state = STATE.IDLE;
                            speack.setImageResource(R.drawable.microphone);
                            ListenHelper.showTip(Add.this, errorMsg + "哈哈哈 出错啦");

                        }
                    });


                } else if (state == STATE.RECORD) {

                    handler.postDelayed(runnable, 0);

                } else if (state == STATE.STOP){
                    ListenHelper.showTip(Add.this, "已经录完了");
                }

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
