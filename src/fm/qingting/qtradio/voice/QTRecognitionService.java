package fm.qingting.qtradio.voice;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.speech.RecognitionService;
import android.speech.RecognitionService.Callback;
import fm.qingting.qtradio.model.InfoManager;
import java.util.ArrayList;

public class QTRecognitionService extends RecognitionService
{
  private RecognitionService.Callback mCallBack;
  private VoiceRecord.OnRecordListener mListener = new VoiceRecord.OnRecordListener()
  {
    public void onError()
    {
      if (QTRecognitionService.this.mCallBack != null);
      try
      {
        QTRecognitionService.this.mCallBack.error(0);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
      }
    }

    public void onPrepare()
    {
      if (QTRecognitionService.this.mCallBack != null);
      try
      {
        QTRecognitionService.this.mCallBack.readyForSpeech(null);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
      }
    }

    public void onStart()
    {
      if (QTRecognitionService.this.mCallBack != null);
      try
      {
        QTRecognitionService.this.mCallBack.beginningOfSpeech();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
      }
    }

    public void onStop(byte[] paramAnonymousArrayOfByte)
    {
      if (QTRecognitionService.this.mCallBack != null)
        try
        {
          QTRecognitionService.this.mCallBack.endOfSpeech();
          ArrayList localArrayList = VoiceRecognizer.recognize(paramAnonymousArrayOfByte);
          if ((localArrayList != null) && (!localArrayList.isEmpty()))
          {
            Bundle localBundle = new Bundle();
            localBundle.putStringArrayList("results_recognition", localArrayList);
            QTRecognitionService.this.mCallBack.results(localBundle);
            return;
          }
          QTRecognitionService.this.mCallBack.error(0);
          return;
        }
        catch (Exception localException)
        {
          try
          {
            QTRecognitionService.this.mCallBack.error(0);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
          }
        }
    }
  };
  private VoiceRecord mRecorder;

  protected void onCancel(RecognitionService.Callback paramCallback)
  {
    this.mRecorder.release();
  }

  protected void onStartListening(Intent paramIntent, RecognitionService.Callback paramCallback)
  {
    this.mCallBack = paramCallback;
    if (InfoManager.getInstance().hasConnectedNetwork())
    {
      this.mRecorder = VoiceRecord.getInstance();
      this.mRecorder.setListener(this.mListener);
      this.mRecorder.startRecord();
      return;
    }
    try
    {
      this.mCallBack.error(0);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  protected void onStopListening(RecognitionService.Callback paramCallback)
  {
    this.mRecorder.stopRecord();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.voice.QTRecognitionService
 * JD-Core Version:    0.6.2
 */