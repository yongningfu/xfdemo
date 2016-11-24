package com.example.administrator.xfdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    private List<Map<String, String>> dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListenHelper.Init(this);

        listView = (ListView) findViewById(R.id.listview);
        dataSource = new ArrayList<Map<String, String>>();
        //mock
        for (int i = 0; i < 10; i++) {
            Map temp = new HashMap<String, String>();
            temp.put("text", "hahahahhaha");
            dataSource.add(temp);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, dataSource, R.layout.item, new String[] {"text"}, new int[] {R.id.textview});
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListenHelper.listen(MainActivity.this);
            }
        });
    }
}








