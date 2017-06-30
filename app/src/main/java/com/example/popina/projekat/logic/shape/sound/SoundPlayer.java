package com.example.popina.projekat.logic.shape.sound;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.popina.projekat.R;

/**
 * Created by popina on 27.06.2017..
 */

public class SoundPlayer implements SoundPlayerCallback
{
    private Activity activity;
    private SoundPool soundPool;
    private int sounds[];

    public SoundPlayer(Activity activity)
    {
        this.activity = activity;
        AudioAttributes attributes = new AudioAttributes.Builder()
                                                        .setUsage(AudioAttributes.USAGE_GAME)
                                                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                                        .build();
        soundPool = new SoundPool.Builder().setMaxStreams(SoundConst.MAX_STREAMS)
                                                     .setAudioAttributes(attributes)
                                                     .build();

        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        loadSounds();
    }

    private void loadSounds()
    {
        sounds = new int[SoundConst.SOUND_MAX];
        sounds[SoundConst.SOUND_ID_COLLISION] = soundPool.load(activity, R.raw.collision, SoundConst.SOUND_PRIORITY);
        sounds[SoundConst.SOUND_ID_MISS] = soundPool.load(activity, R.raw.miss, SoundConst.SOUND_PRIORITY);
        sounds[SoundConst.SOUND_ID_SUCCESS] = soundPool.load(activity, R.raw.success, SoundConst.SOUND_PRIORITY);
        sounds[SoundConst.SOUND_ID_VORTEX] = soundPool.load(activity, R.raw.water, SoundConst.SOUND_PRIORITY);
    }

    @Override
    public void playSound(int idSound)
    {
        soundPool.play(sounds[idSound],
                        SoundConst.SOUND_LEFT_VOLUME,
                        SoundConst.SOUND_RIGHT_VOLUME,
                        SoundConst.SOUND_PRIORITY_STREAM,
                        SoundConst.SOUND_NO_LOOP,
                        SoundConst.SOUND_RATE_PLAYBACK);
    }

    @Override
    public void destroy()
    {
        soundPool.release();
    }
}
