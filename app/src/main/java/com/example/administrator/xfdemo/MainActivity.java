package com.example.administrator.xfdemo;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

    private Button add;
    ListView listView;
    private List<Map<String, String>> dataSource;
    private SimpleAdapter simpleAdapter;
    private SwipeRefreshLayout refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        ListenHelper.Init(this);

        listView = (ListView) findViewById(R.id.listview);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                ListenHelper.showTip(MainActivity.this, "刷新一下");
                refresh.setRefreshing(false);

//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        data.add(0, "刷新后新增的item");
//                        adapter.notifyDataSetChanged();
//                        swiperereshlayout.setRefreshing(false);
//                    }
//                }, 2000);
            }
        });








        add = (Button) findViewById(R.id.add);
        dataSource = new ArrayList<Map<String, String>>();

        simpleAdapter = new SimpleAdapter(this, dataSource, R.layout.item, new String[] {"text"}, new int[] {R.id.textview});
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                ListenHelper.playListener(dataSource.get(position).get("filePath"));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add.class);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && requestCode == 0 && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String filePath = (String) bundle.get("filePath");
                String transText = (String) bundle.get("transText");
                Map<String, String> temp = new HashMap<>();
                temp.put("text", transText);
                temp.put("filePath", filePath);
                dataSource.add(temp);
            }
        }
        simpleAdapter.notifyDataSetChanged();
    }
}








