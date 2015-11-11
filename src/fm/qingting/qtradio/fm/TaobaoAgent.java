package fm.qingting.qtradio.fm;

import android.content.Context;
import android.media.MediaPlayer;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.view.audio.MunionAudio;
import com.taobao.newxp.view.audio.MunionAudio.OnAudioADClientCallBackListener;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.PlayMode;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.utils.QTMSGManage;
import java.lang.reflect.Field;

public class TaobaoAgent
{
  private static TaobaoAgent instance;
  private String AD_IDENTITY = "62831";
  private boolean hasLoadedAudio = false;
  private long hasPlayAdv = 0L;
  private MunionAudio mAudio;
  private Context mContext;
  private MediaPlayer mPlayer;

  private void _playAD(boolean paramBoolean)
  {
    if (this.mAudio != null)
    {
      if (!paramBoolean)
        InfoManager.getInstance().setPlayAdvertisementTime();
      if (paramBoolean)
        mute();
      this.mAudio.playAD();
      QTMSGManage.getInstance().sendStatistcsMessage("taobao_ad", "play");
    }
  }

  private void change()
  {
    AlimmContext.getAliContext().init(this.mContext);
    Class localClass = AlimmContext.getAliContext().getAppUtils().getClass();
    while (true)
    {
      int i;
      try
      {
        Field[] arrayOfField = localClass.getDeclaredFields();
        i = 0;
        if (i < arrayOfField.length)
          if (arrayOfField[i].getName().equalsIgnoreCase("l"))
          {
            Field localField2 = localClass.getDeclaredField(arrayOfField[i].getName());
            localField2.setAccessible(true);
            String str4 = String.valueOf(localField2.get(AlimmContext.getAliContext().getAppUtils()));
            int n = SharedCfg.getInstance().getTaoBaoChange();
            if (n == 0)
              return;
            int i1 = SharedCfg.getInstance().getBootstrapCnt() % n;
            if (str4.length() > i1)
            {
              char c1 = str4.charAt(0);
              char c2 = str4.charAt(i1);
              changeStr(str4, String.valueOf(c2) + String.valueOf(c1) + str4.substring(2));
            }
          }
          else if (arrayOfField[i].getName().equalsIgnoreCase("n"))
          {
            Field localField1 = localClass.getDeclaredField(arrayOfField[i].getName());
            localField1.setAccessible(true);
            String str1 = String.valueOf(localField1.get(AlimmContext.getAliContext().getAppUtils()));
            int j = SharedCfg.getInstance().getTaoBaoChange();
            if (j != 0)
            {
              int k = SharedCfg.getInstance().getBootstrapCnt() % j;
              if (str1.length() <= k)
                break label394;
              String[] arrayOfString = str1.split(":");
              if (arrayOfString != null)
              {
                String str2 = "";
                if (k >= arrayOfString.length)
                  break label400;
                str2 = arrayOfString[k];
                break label400;
                if (m < arrayOfString.length)
                {
                  if (m == k)
                    break label406;
                  String str3 = str2 + ":";
                  str2 = str3 + arrayOfString[m];
                  break label406;
                }
                if ((str2 == null) || (str2.equalsIgnoreCase("")))
                  break label394;
                changeStr(str1, str2);
              }
            }
          }
      }
      catch (Exception localException)
      {
      }
      return;
      label394: i++;
      continue;
      label400: int m = 0;
      continue;
      label406: m++;
    }
  }

  private void changeStr(String paramString1, String paramString2)
  {
    Class localClass = paramString1.getClass();
    try
    {
      Field localField1 = localClass.getDeclaredField("value");
      Field localField2 = localClass.getDeclaredField("count");
      localField2.setAccessible(true);
      localField1.setAccessible(true);
      char[] arrayOfChar = paramString2.toCharArray();
      localField2.set(paramString1, Integer.valueOf(arrayOfChar.length));
      localField1.set(paramString1, arrayOfChar);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static TaobaoAgent getInstance()
  {
    try
    {
      if (instance == null)
        instance = new TaobaoAgent();
      TaobaoAgent localTaobaoAgent = instance;
      return localTaobaoAgent;
    }
    finally
    {
    }
  }

  private void getPlayer()
  {
    Class localClass = this.mAudio.getClass();
    try
    {
      Field[] arrayOfField = localClass.getDeclaredFields();
      for (int i = 0; ; i++)
        if (i < arrayOfField.length)
        {
          Field localField = localClass.getDeclaredField(arrayOfField[i].getName());
          localField.setAccessible(true);
          Object localObject = localField.get(this.mAudio);
          if ((localObject instanceof MediaPlayer))
            this.mPlayer = ((MediaPlayer)localObject);
        }
        else
        {
          return;
        }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void log(String paramString)
  {
  }

  private void mute()
  {
    try
    {
      getPlayer();
      if (this.mPlayer != null)
        this.mPlayer.setVolume(0.0F, 0.0F);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void onHandlePlayFinished()
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    InfoManager.getInstance().root().setPlayMode();
    PlayerAgent.getInstance().play(localNode, false);
    loadAd();
  }

  public boolean hasLoadedAdv()
  {
    return this.hasLoadedAudio;
  }

  public void init(Context paramContext, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mAudio = new MunionAudio(paramContext);
    if (paramBoolean)
      change();
    this.mAudio.setOnAudioADClientCallBackListener(new MunionAudio.OnAudioADClientCallBackListener()
    {
      public void onDidPause()
      {
        TaobaoAgent.this.log("暂停广告播放");
      }

      public void onDidStart()
      {
        TaobaoAgent.this.log("开始广告播放");
        InfoManager.getInstance().root().setPlayMode(RootNode.PlayMode.PLAY_FRONT_ADVERTISEMENT);
      }

      public void onDidStop()
      {
        TaobaoAgent.this.log("停止广告播放");
        TaobaoAgent.this.onHandlePlayFinished();
      }

      public void onPlayDidFinished()
      {
        TaobaoAgent.this.log("播放广告完成");
        TaobaoAgent.this.onHandlePlayFinished();
        QTMSGManage.getInstance().sendStatistcsMessage("taobao_ad", "playFinished");
      }

      public void onPlayFailed(String paramAnonymousString)
      {
        TaobaoAgent.this.log("播放广告失败");
        TaobaoAgent.this.onHandlePlayFinished();
        QTMSGManage.getInstance().sendStatistcsMessage("taobao_ad", "playFailed");
      }

      public void onRequestFailed(String paramAnonymousString)
      {
        TaobaoAgent.this.log("广告请求失败");
        QTMSGManage.getInstance().sendStatistcsMessage("taobao_ad", "requestADFailed");
      }

      public void onRequestFinished()
      {
        TaobaoAgent.this.log("广告请求成功");
        TaobaoAgent.access$102(TaobaoAgent.this, true);
        QTMSGManage.getInstance().sendStatistcsMessage("taobao_ad", "requestADSucc");
      }
    });
  }

  public void loadAd()
  {
    if (this.mAudio != null)
    {
      this.hasLoadedAudio = false;
      this.mAudio.requestAD(this.AD_IDENTITY);
      QTMSGManage.getInstance().sendStatistcsMessage("taobao_ad", "load");
    }
  }

  public boolean playAD(boolean paramBoolean)
  {
    if ((paramBoolean) && (System.currentTimeMillis() / 1000L - this.hasPlayAdv < 300L))
      return false;
    if (this.hasLoadedAudio)
    {
      if (!paramBoolean)
        PlayerAgent.getInstance().stop();
      _playAD(paramBoolean);
      this.hasPlayAdv = (System.currentTimeMillis() / 1000L);
      return true;
    }
    loadAd();
    return false;
  }

  public void setADId(String paramString)
  {
    this.AD_IDENTITY = paramString;
  }

  public void stopAD()
  {
    if (this.mAudio != null)
      this.mAudio.stopAD();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.fm.TaobaoAgent
 * JD-Core Version:    0.6.2
 */