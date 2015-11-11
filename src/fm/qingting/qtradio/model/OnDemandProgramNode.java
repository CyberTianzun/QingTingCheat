package fm.qingting.qtradio.model;

import java.util.List;

public class OnDemandProgramNode extends Node
{
  public String albumId;
  public String broadcast_time;
  public String categoryId;
  public String desc;
  public transient Download downloadInfo;
  public int duration = 0;
  public List<String> lstOriginal;
  private int mAvailableUrlIndex = -1;
  public String mDownLoadPath;
  private String mDownLoadUrls;
  private String mOriginalUrls;
  private String mSourceUrls;
  public transient String mTranscode;
  private String mTranscodeUrls;
  public transient MediaInfoNode mediaInfoNode;
  public String pic;
  public String playUrl;
  public String programId;
  public String title;
  public String type = "";
  public String updatetime;

  public OnDemandProgramNode()
  {
    this.nodeName = "ondemandprogram";
  }

  private String getDownLoadUrl()
  {
    if (this.mDownLoadUrls != null)
      return this.mDownLoadUrls;
    if (this.mDownLoadPath == null)
      this.mDownLoadPath = this.mTranscode;
    if (this.mDownLoadPath != null);
    return this.mDownLoadUrls;
  }

  private String getOriginalUrls()
  {
    if ((this.mOriginalUrls == null) && (this.lstOriginal != null))
    {
      Object localObject = "";
      int i = 0;
      while (i < this.lstOriginal.size())
      {
        String str1 = (String)localObject + (String)this.lstOriginal.get(i);
        String str2 = str1 + "?deviceId=";
        String str3 = str2 + InfoManager.getInstance().getDeviceId();
        String str4 = str3 + ";;";
        i++;
        localObject = str4;
      }
      this.mOriginalUrls = ((String)localObject);
    }
    return this.mOriginalUrls;
  }

  private String getTranscodeUrls()
  {
    if ((this.mTranscodeUrls == null) && (this.mTranscode != null));
    return this.mTranscodeUrls;
  }

  public String getDownLoadPath()
  {
    if (this.mDownLoadPath == null)
      this.mDownLoadPath = this.mTranscode;
    return this.mDownLoadPath;
  }

  public int getDuration()
  {
    if (this.duration != 0)
      return this.duration;
    if (this.broadcast_time != null)
      try
      {
        this.duration = Integer.valueOf(this.broadcast_time).intValue();
        return this.duration;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        while (true)
          localNumberFormatException.printStackTrace();
      }
    return 0;
  }

  public String getNextDownLoadUrl()
  {
    if (this.downloadInfo == null)
      return null;
    int i = this.downloadInfo.type;
    String str;
    if (i == 0)
      str = "transcodeCenterList";
    while (true)
    {
      this.mAvailableUrlIndex = (1 + this.mAvailableUrlIndex);
      if (str != null);
      return "";
      str = null;
      if (i == 1)
        str = "storageCenterList";
    }
  }

  public String getSourceUrls()
  {
    if (this.mSourceUrls == null)
    {
      this.mSourceUrls = getTranscodeUrls();
      if (this.mSourceUrls == null)
        break label55;
    }
    label55: for (this.mSourceUrls += getOriginalUrls(); ; this.mSourceUrls = getOriginalUrls())
      return this.mSourceUrls;
  }

  public void setSourceUrls(String paramString)
  {
    if (paramString == null)
      return;
    this.mSourceUrls = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.OnDemandProgramNode
 * JD-Core Version:    0.6.2
 */