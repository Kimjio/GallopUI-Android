package com.kimjio.gallopui.widget;

import android.animation.AnimatorInflater;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;

import com.kimjio.gallopui.R;

import java.io.IOException;

public class GallopButton extends AppCompatButton {
    @RawRes
    private int clickAudioRes = R.raw.snd_sfx_ui_decide_m_01;

    public GallopButton(@NonNull Context context) {
        this(context, null);
    }

    public GallopButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GallopButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(new ContextThemeWrapper(context, R.style.GallopButton), attrs, defStyleAttr);
        setSoundEffectsEnabled(false);
        setHeight(110);
        setBackgroundTintList(null);
        setStateListAnimator(AnimatorInflater.loadStateListAnimator(context, R.animator.zoom_out_animator));
        setTypeface(ResourcesCompat.getFont(context, R.font.gyeonggi_title_medium));
        Type type = Type.BTN1;
        try (TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GallopButton, defStyleAttr, R.style.GallopButton)) {
            type = Type.values()[array.getInt(R.styleable.GallopButton_buttonType, Type.BTN1.ordinal())];
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        switch (type) {
            case BTN1:
                setBackgroundResource(R.drawable.btn1);
                setTextColor(Color.WHITE);
                clickAudioRes = R.raw.snd_sfx_ui_decide_m_01;
                break;
            case BTN2:
                setBackgroundResource(R.drawable.btn2);
                setTextColor(Color.rgb(121, 64, 22));
                clickAudioRes = R.raw.snd_sfx_ui_cancel_m_01;
                break;
        }
        if (!isEnabled()) {
            setTextColor(getContext().getColor(R.color.gallop_disabled_color));
            getBackground().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(Color.GRAY, BlendModeCompat.MODULATE));
        }
        setLetterSpacing(0);
        setPadding(0, 0, 0, 8);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            getBackground().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(Color.GRAY, BlendModeCompat.MODULATE));
        } else {
            getBackground().setColorFilter(null);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && !isEnabled()) {
            AudioManager audioManager = getContext().getSystemService(AudioManager.class);
            MediaPlayer player = MediaPlayer.create(getContext(), R.raw.snd_sfx_ui_cancel_m_02,
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .build(), audioManager.generateAudioSessionId());
            player.setOnCompletionListener(MediaPlayer::release);
            player.start();

            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        AudioManager audioManager = getContext().getSystemService(AudioManager.class);
        MediaPlayer player = MediaPlayer.create(getContext(), clickAudioRes,
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .build(), audioManager.generateAudioSessionId());
        player.setOnCompletionListener(MediaPlayer::release);
        player.start();
        return super.performClick();
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46, getResources().getDisplayMetrics()));
    }

    public enum Type {
        BTN1,
        BTN2
    }
}
