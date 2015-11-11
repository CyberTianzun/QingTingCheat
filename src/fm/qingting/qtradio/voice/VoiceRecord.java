package fm.qingting.qtradio.voice;

import android.media.AudioRecord;
import android.os.Handler;
import android.os.Looper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class VoiceRecord
{
  public static final int CHANNEL_NUMBER = 1;
  private static final int DEFAULT_MAX_DURATION = 5000;
  public static final int SAMPLE_RATE = 8000;
  private static final int STATE_ERROR = 4;
  private static final int STATE_IDLE = 0;
  private static final int STATE_PREPARED = 1;
  private static final int STATE_STARTED = 2;
  private static final int STATE_STOPED = 3;
  private static VoiceRecord mInstance;
  private ByteArrayOutputStream mByteBuffer;
  private Handler mHandler = new Handler(Looper.getMainLooper());
  private ArrayList<OnRecordListener> mListeners;
  private int mMaxDuration;
  private byte[] mMinBuffer;
  private int mMinBufferSize;
  private AudioRecord mRecorder;
  private Runnable mRunnable = new Runnable()
  {
    public void run()
    {
      VoiceRecord.this.stopRecord();
    }
  };
  private int mState;

  private VoiceRecord()
  {
    setState(0);
    checkInitialize();
  }

  private void checkInitialize()
  {
    if (this.mState == 0)
    {
      this.mMinBufferSize = AudioRecord.getMinBufferSize(8000, 1, 2);
      this.mRecorder = new AudioRecord(1, 8000, 2, 2, this.mMinBufferSize);
      this.mMinBuffer = new byte[this.mMinBufferSize];
      this.mByteBuffer = new ByteArrayOutputStream();
      this.mMaxDuration = 5000;
      setState(1);
      dispatchListener();
    }
  }

  private void dispatchListener()
  {
    if (this.mListeners == null);
    while (true)
    {
      return;
      int i = this.mListeners.size();
      switch (this.mState)
      {
      default:
        return;
      case 1:
        for (int n = 0; n < i; n++)
          ((OnRecordListener)this.mListeners.get(n)).onPrepare();
      case 2:
        for (int m = 0; m < i; m++)
          ((OnRecordListener)this.mListeners.get(m)).onStart();
      case 3:
        for (int k = 0; k < i; k++)
          ((OnRecordListener)this.mListeners.get(k)).onStop(this.mByteBuffer.toByteArray());
      case 4:
      }
      for (int j = 0; j < i; j++)
        ((OnRecordListener)this.mListeners.get(j)).onError();
    }
  }

  public static VoiceRecord getInstance()
  {
    if (mInstance == null)
      mInstance = new VoiceRecord();
    return mInstance;
  }

  private void setState(int paramInt)
  {
    if (this.mState != paramInt)
      try
      {
        this.mState = paramInt;
        return;
      }
      finally
      {
      }
  }

  private void startRunnalbe()
  {
    this.mHandler.removeCallbacks(this.mRunnable);
    this.mHandler.postDelayed(this.mRunnable, this.mMaxDuration);
  }

  private void stopRunnable()
  {
    this.mHandler.removeCallbacks(this.mRunnable);
  }

  public void release()
  {
    if (this.mState != 0)
      setState(0);
    try
    {
      this.mRecorder.stop();
      this.mRecorder.release();
      this.mByteBuffer.close();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public void setListener(OnRecordListener paramOnRecordListener)
  {
    if (paramOnRecordListener == null)
      return;
    if (this.mListeners == null)
      this.mListeners = new ArrayList();
    this.mListeners.add(paramOnRecordListener);
  }

  public void setMaxDuration(int paramInt)
  {
    this.mMaxDuration = paramInt;
  }

  public void startRecord()
  {
    checkInitialize();
    if (this.mState != 2)
    {
      setState(2);
      dispatchListener();
      this.mRecorder.startRecording();
      startRunnalbe();
      ThreadHelper.getInstance().execute(new Runnable()
      {
        public void run()
        {
          try
          {
            while (VoiceRecord.this.mState == 2)
            {
              int i = VoiceRecord.this.mRecorder.read(VoiceRecord.this.mMinBuffer, 0, VoiceRecord.this.mMinBufferSize);
              VoiceRecord.this.mByteBuffer.write(VoiceRecord.this.mMinBuffer, 0, i);
            }
            if (VoiceRecord.this.mState == 3)
            {
              VoiceRecord.this.dispatchListener();
              VoiceRecord.this.mByteBuffer.reset();
              VoiceRecord.this.stopRunnable();
              return;
            }
          }
          catch (Exception localException)
          {
          }
        }
      });
    }
  }

  public void stopRecord()
  {
    checkInitialize();
    if ((this.mState != 3) && (this.mState == 2))
    {
      setState(3);
      this.mRecorder.stop();
    }
  }

  public static abstract interface OnRecordListener
  {
    public abstract void onError();

    public abstract void onPrepare();

    public abstract void onStart();

    public abstract void onStop(byte[] paramArrayOfByte);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.voice.VoiceRecord
 * JD-Core Version:    0.6.2
 */