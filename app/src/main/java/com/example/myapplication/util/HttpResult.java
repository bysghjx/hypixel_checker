package com.example.myapplication.util;

import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.Acceptable;
import com.example.myapplication.MainActivity7;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 如果说连接成功responseCode 将会是http状态码
 * 此时无论是否请求成功都可以用read读取网页上的内容，read调用后可get content获取信息
 * 否则responseCode会是-1且content会是错误信息
 */
public class HttpResult {
    public final int responseCode;
    private final BufferedReader reader;

    private String content;

    public HttpResult(int responseCode, BufferedReader reader) {
        this.responseCode = responseCode;
        this.reader = reader;
    }

    public void read() {
        if (!isSuccess()) return;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            content = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            MainActivity7 mainActivity = new MainActivity7();
            mainActivity.runOnUiThread(()->{
                WaitDialog.Companion.WaitDialogDismiss();
                String s = String.valueOf(e);
                AlertDialog dialog = new AlertDialog.Builder(mainActivity)
                        .setTitle("出现异常！")
                        .setMessage("遇到此问题时可以尝试重试，一般为网络原因引起"+"\n"+s)
                        .setPositiveButton("确定", (dialog1, which) -> {
                            mainActivity.setButtonEnabled();
                        })
                        .create();
                dialog.setOnCancelListener(dialog12 -> {
                    Toast.makeText(mainActivity, "如多次遇到此问题可在github上提交issues反馈", Toast.LENGTH_LONG).show();
                });
                dialog.show();
            });
        } finally {
            close();
        }
    }

    public boolean isSuccess() {
        return responseCode != -1;
    }

    public void close() {
        IOUtils.closeQuietly(reader);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
