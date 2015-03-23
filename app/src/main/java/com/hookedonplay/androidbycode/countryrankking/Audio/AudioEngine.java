/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.Audio;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hookedonplay.androidbycode.countryrankking.R;

import java.util.HashMap;

/**
 * @author BRM20150317
 *         <p/>
 *         Main sound engine
 *         <p/>
 *         For gameplay we need to keep sounds loaded and read to go in a map Don't load
 *         huge soundtracks in here...just small audio, wav is ok or mp3 if size is an
 *         issue.
 *         <p/>
 *         For large audio files you should use the playMusic function
 * @see #playMusic(android.content.Context, int, boolean)
 * <p/>
 * For small game sounds that should be cached use the playSound function
 * @see #playSound(int, boolean)
 * <p/>
 * Supported audio containers are WAV and MP3 at a minimum.
 * Please use WAV for very small files (< 1 sec)
 */
@SuppressWarnings("WeakerAccess")
public class AudioEngine {
    private static final String TAG = "AudioEngine";

    // Sounds to be loaded
    public static final int SOUND_SWAP = 1;
    public static final int SOUND_CORRECT = 2;
    public static final int SOUND_INCORRECT = 3;
    public static final int SOUND_DISPLAY_1 = 4;
    public static final int SOUND_DISPLAY_2 = 5;
    public static final int SOUND_DISPLAY_3 = 6;
    public static final int SOUND_DISPLAY_4 = 7;
    public static final int SOUND_DISPLAY_5 = 8;
    public static final int SOUND_HIT_9 = 9;
    public static final int SOUND_MAX = SOUND_HIT_9;

    /**
     * Playback engine for larger audio files that we do not want to cache
     * at the creation of the audio factory
     */
    MediaPlayer mMediaPlayer;

    /**
     * Android AudioManager
     */
    AudioManager mAudioMgr;

    /**
     * Android SoundPool class allows us to load
     */
    private SoundPool mSoundPool;


    private boolean mSoundsLoaded = false;

    private Context mContext;
    /**
     * The sound pool map preloads all the small audio files to use
     * constantly in the app.
     * <p/>
     * It holds an ID we allocate and the and the sound ID returned when
     * the audio is loaded into the sound pool
     * <p/>
     * To add new audio you need to;
     * 1) ensure the audio file is small, I would not try anything > 20k
     * 2) add the audio file to the res directly
     * 3) Add a static SOUND_XXXXX static, as a handle to load it
     * 4) Add the load of the sound in the initSounds() fn
     *
     * @see #initSounds(android.content.Context)
     */
    private HashMap<Integer, Integer> mSoundPoolMap;

    /**
     * Holds the currently playing sound streams.  When a sound is played
     * it is retrieved from the SoundPoolMap played using the SoundPool
     * member
     *
     * @see #mSoundPoolMap
     * @see #mSoundPool
     */
    private HashMap<Integer, Integer> mStreamPoolMap;

    /**
     * Flag if sound engine is on or off.
     * <p/>
     * TODO: At the moment this applies to both audio and sound clips
     * Later it might be worthwhile being able to turn game sounds and
     * background music on or off independently
     *
     * @see #setSoundOn(boolean)
     * @see #isSoundOn()
     */
    private boolean mSoundEngineOn = true;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewApiSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        mSoundPool = new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(6).build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldApiSoundPool() {
        mSoundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 100);
    }

    /**
     * Load all the audio we want ready to go. Do this early on when loading the
     * app or before starting the game, don't wait until you need it!
     *
     * @param context To load the resources from
     */
    public void initSounds(Context context) {
        Log.v(TAG, "Initilizing sound engine...");
        try {
            mAudioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                createNewApiSoundPool();
            } else {
                createOldApiSoundPool();
            }
            mContext = context;
            new loadSoundsAsync().execute("");
        } catch (Exception e) {
            Log.e(TAG, "Exception raised loading sounds: " + e.getMessage());
        }

    }

    public void playSound(int sound) {
        playSound(sound, true);
    }

    /**
     * Play a predefined small sound
     *
     * @param sound     Sound to play see SOUND_* values
     * @param playSoft Play the sound at half users set volume
     * @throws IllegalArgumentException if sound is not an acceptable value
     */
    public void playSound(final int sound, final boolean playSoft) throws IllegalArgumentException {

        if (!mSoundsLoaded) {
            Log.e(TAG, "Sounds are not loaded yet...unable to play");
            return;
        }

        if (sound > SOUND_MAX || sound <= 0) {
            throw new IllegalArgumentException("Sound '" + sound + "' is not in the acceptable range 1.." + SOUND_MAX);
        }

        try {
            if (!mSoundEngineOn) {
                return;
            }

            final float streamVolumeCurrent = mAudioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
            final float streamVolumeMax = mAudioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = streamVolumeCurrent / streamVolumeMax;

            if (playSoft) {
                volume = volume * (float) 0.1;
            }

            /* Play the sound with the correct volume */
            final int StreamID = mSoundPool.play(mSoundPoolMap.get(sound), volume, volume, 1, 0, 1f);
            mStreamPoolMap.put(sound, StreamID);
        } catch (Exception e) {
            Log.e(TAG, "Exception raised playing sound- " + sound);
            e.printStackTrace();
        }
    }

    /**
     * Play a large audio file from the application resources
     * <p/>
     * At this stage only one audio file can be played at once.  This
     * does not effect the playSound audio which can have many sounds
     * play simultaneously
     *
     * @param context Context to get resources to load the audio from
     * @param sound   resource id of the audio file
     * @param loop    loop the audio playing
     */
    @SuppressWarnings("unused")
    public void playMusic(@NonNull Context context, final int sound, final boolean loop) {
        try {

            if (!mSoundEngineOn) {
                return;
            }

            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer = null;
            }

            Log.v(TAG, "Playing audio..." + sound);
            mMediaPlayer = MediaPlayer.create(context, sound);
            mMediaPlayer.setLooping(loop);
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception raised playing music- " + sound);
        }
    }

    /**
     * Stop playing the given sound
     *
     * @param sound ID of the sound to stop playing
     * @throws IllegalArgumentException if sound is not an acceptable value
     * @see #playSound(int, boolean)
     */
    @SuppressWarnings("unused")
    public void stopSound(final int sound) {
        if (sound > SOUND_MAX || sound <= 0) {
            throw new IllegalArgumentException("Sound '" + sound + "' is not in the acceptable range 1.." + SOUND_MAX);
        }
        if (!mSoundEngineOn) {
            return;
        }
        try {
            final int streamID = mStreamPoolMap.get(sound);
            mSoundPool.stop(streamID);
        } catch (Exception e) {
            Log.e(TAG, "Exception raised stopping sound- " + sound);
        }

    }

    /**
     * Stop playing the given music audio track
     *
     * @see #playMusic(android.content.Context, int, boolean)
     */
    @SuppressWarnings("unused")
    public void stopMusic() {
        try {
            Log.v(TAG, "Stopping music...");
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSoundOn() {
        return mSoundEngineOn;
    }

    public void setSoundOn(boolean on) {
        mSoundEngineOn = on;
    }

    private class loadSoundsAsync extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... str) {
            mSoundPoolMap = new HashMap<>();
            mSoundPoolMap.put(SOUND_SWAP, mSoundPool.load(mContext, R.raw.sound_swap, 1));
            mSoundPoolMap.put(SOUND_CORRECT, mSoundPool.load(mContext, R.raw.sound_correct, 1));
            mSoundPoolMap.put(SOUND_INCORRECT, mSoundPool.load(mContext, R.raw.sound_incorrect, 1));
            mSoundPoolMap.put(SOUND_DISPLAY_1, mSoundPool.load(mContext, R.raw.sound_tone1, 1));
            mSoundPoolMap.put(SOUND_DISPLAY_2, mSoundPool.load(mContext, R.raw.sound_tone3, 1));
            mSoundPoolMap.put(SOUND_DISPLAY_3, mSoundPool.load(mContext, R.raw.sound_tone6, 1));
            mSoundPoolMap.put(SOUND_DISPLAY_4, mSoundPool.load(mContext, R.raw.sound_tone9, 1));
            mSoundPoolMap.put(SOUND_DISPLAY_5, mSoundPool.load(mContext, R.raw.sound_tone12, 1));
            mSoundPoolMap.put(SOUND_HIT_9, mSoundPool.load(mContext, R.raw.sound_hit_9, 1));

            Log.v(TAG, "Loaded " + mSoundPoolMap.size() + " sounds into engine...");

            mStreamPoolMap = new HashMap<>();
            return 0L;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            mSoundsLoaded = true;
        }
    }
}
