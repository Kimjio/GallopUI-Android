package com.kimjio.gallopui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import com.kimjio.gallopui.app.GallopDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.ok_button).setOnClickListener(v -> openDialog());
    }

    void openDialog() {
        GallopDialog dialog = new GallopDialog(this);
        dialog.setTitle("네트워크 오류");
        dialog.setMessage("네트워크 연결에 오류가 발생했습니다");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "타이틀 화면으로", (d, witch) -> {});
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "재시도", (d, witch) -> {

            new Handler().postDelayed(this::openDialog, 200);
        });
        dialog.show();
    }
}