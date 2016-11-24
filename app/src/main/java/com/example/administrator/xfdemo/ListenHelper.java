package com.example.administrator.xfdemo;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.xfdemo.utils.JsonParser;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/11/24.
 */
public class ListenHelper {

    public static void Init(Context context) {
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=582b3f8f");
    }

    //这个函数为异步的
    public static void listen(final Context context) {
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");

        mDialog.setListener(new RecognizerDialogListener() {

            private String finalResults = "";
            @Override
            public void onResult(RecognizerResult results, boolean isLast) {

                //语音听写的结果 需要拼接
//                String result = parseResult(results);
                finalResults += parseResult(results);
                if (isLast) {
                    Toast.makeText(context, finalResults, Toast.LENGTH_LONG).show();
                    finalResults = "";
                }
            }

            @Override
            public void onError(SpeechError error) {
                showTip(context, error.getPlainDescription(true));
            }
        });

        mDialog.show();
    }









    //解析结果
    private static  String  parseResult(RecognizerResult results) {
        // 用HashMap存储听写结果
        HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {

            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        return resultBuffer.toString();
    }

    //信息输出
    private static void showTip(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
}
