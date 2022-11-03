package com.kimjio.gallopui.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.kimjio.gallopui.R;

import java.io.IOException;

public class GallopDialog extends AlertDialog {

    public GallopDialog(@NonNull Context context) {
        this(context, R.style.ThemeOverlay_GallopDialog);
    }

    protected GallopDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(new ContextThemeWrapper(context, R.style.Theme_GallopDialog), themeResId);
        getWindow().getAttributes().windowAnimations = R.style.GallopDialogWindowAnimation;
        getWindow().setDimAmount(0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWindow().getAttributes().setBlurBehindRadius(20);
        }
    }

    protected GallopDialog(@NonNull Context context, boolean cancelable,
                           @Nullable OnCancelListener cancelListener) {
        this(context, R.style.ThemeOverlay_GallopDialog);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AudioManager audioManager = getContext().getSystemService(AudioManager.class);
        MediaPlayer player = MediaPlayer.create(getContext(), R.raw.snd_sfx_ui_window_01,
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .build(), audioManager.generateAudioSessionId());
        player.setOnCompletionListener(MediaPlayer::release);
        player.start();
    }

    @Override
    public void cancel() {
        super.cancel();
        AudioManager audioManager = getContext().getSystemService(AudioManager.class);
        MediaPlayer player = MediaPlayer.create(getContext(), R.raw.snd_sfx_ui_cancel_m_01,
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .build(), audioManager.generateAudioSessionId());
        player.setOnCompletionListener(MediaPlayer::release);
        player.start();
    }


}
