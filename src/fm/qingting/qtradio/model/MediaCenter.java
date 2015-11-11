package fm.qingting.qtradio.model;

import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.utils.DeviceInfo;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaCenter
{
  public static final String LIVE_CHANNEL_DOWNLOAD = "radiodownload";
  public static final String LIVE_CHANNEL_PLAY = "radiohls";
  public static final String VIRUTAL_CHANNEL = "virutalchannel";
  public static MediaCenter _instance;
  private List<PingInfoV6> lstRes = new ArrayList();
  public String mDeviceId;
  public Map<String, List<PingInfoV6>> mapMediaCenters = new HashMap();
  public Map<String, List<PingInfoV6>> mapMediaCentersToDB;
  public String region = "CN";

  public static MediaCenter getInstance()
  {
    if (_instance == null)
      _instance = new MediaCenter();
    return _instance;
  }

  private List<PingInfoV6> getLstPingInfoAfterPing(String paramString1, String paramString2)
  {
    Object localObject;
    if ((paramString1 == null) || (paramString2 == null))
      localObject = null;
    int j;
    do
    {
      return localObject;
      localObject = (List)this.mapMediaCenters.get(paramString1);
      if ((localObject == null) || (((List)localObject).size() == 0))
        return null;
      if ((paramString1.equalsIgnoreCase("radiohls")) || (paramString1.equalsIgnoreCase("radiodownload")))
        paramString2 = this.mDeviceId;
      int i = 0;
      j = 0;
      while (i < ((List)localObject).size())
      {
        j += ((PingInfoV6)((List)localObject).get(i)).weight;
        i++;
      }
    }
    while (j == 0);
    long l = hashCode(paramString2) % j;
    int k = 0;
    int m = 0;
    int n = 0;
    if (k < ((List)localObject).size())
    {
      m += ((PingInfoV6)((List)localObject).get(k)).weight;
      if ((n > l) || (l > m));
    }
    for (int i1 = k; ; i1 = 0)
    {
      this.lstRes.clear();
      int i4;
      for (int i2 = 0; ; i2++)
      {
        int i3 = ((List)localObject).size();
        i4 = 0;
        if (i2 >= i3)
          break;
        PingInfoV6 localPingInfoV6 = new PingInfoV6();
        localPingInfoV6.update((PingInfoV6)((List)localObject).get(i2));
        this.lstRes.add(localPingInfoV6);
      }
      k++;
      n = m;
      break;
      while (i4 < this.lstRes.size())
      {
        if (i4 != i1)
          ((PingInfoV6)this.lstRes.get(i4)).setResult(((PingInfoV6)((List)localObject).get(i4)).getReachTime());
        i4++;
      }
      double d = ((PingInfoV6)this.lstRes.get(i1)).getReachTime() - ((PingInfoV6)this.lstRes.get(i1)).pcc;
      ((PingInfoV6)this.lstRes.get(i1)).setResult(d);
      Collections.sort(this.lstRes, new PingInfoV6Comparator());
      return this.lstRes;
    }
  }

  private long hashCode(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return 0L;
    try
    {
      long l = ByteBuffer.wrap(DeviceInfo.md5(paramString).getBytes()).getLong();
      return l;
    }
    catch (Exception localException)
    {
    }
    return 9223372036854775807L;
  }

  private void swapPingInfoV6(PingInfoV6 paramPingInfoV61, PingInfoV6 paramPingInfoV62)
  {
    if ((paramPingInfoV61 == null) || (paramPingInfoV62 == null))
      return;
    PingInfoV6 localPingInfoV6 = new PingInfoV6();
    localPingInfoV6.update(paramPingInfoV61);
    paramPingInfoV61.update(paramPingInfoV62);
    paramPingInfoV62.update(localPingInfoV6);
  }

  public List<PingInfoV6> getPingInfo(String paramString)
  {
    if (paramString == null)
      return null;
    return (List)this.mapMediaCenters.get(paramString);
  }

  public String getPlayUrls(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    Object localObject = null;
    if (paramString1 != null)
    {
      localObject = null;
      if (paramString2 != null)
      {
        boolean bool1 = paramString2.equalsIgnoreCase("");
        localObject = null;
        if (!bool1)
        {
          boolean bool2 = paramString2.equalsIgnoreCase("0");
          localObject = null;
          if (!bool2)
            break label49;
        }
      }
    }
    while (true)
    {
      return localObject;
      label49: List localList = getLstPingInfoAfterPing(paramString1, paramString2);
      localObject = null;
      if (localList != null)
      {
        int i = localList.size();
        localObject = null;
        if (i != 0)
        {
          localObject = "";
          if ((paramString1 == "radiohls") && (InfoManager.getInstance().isTestLiveChannel(Integer.valueOf(paramString2).intValue())))
          {
            String str7 = "rtmp://rtmplive.qingting.fm/qtrtmp/" + paramString2;
            String str8 = str7 + "?deviceid=" + InfoManager.getInstance().getDeviceId();
            String str9 = str8 + "&cid=" + paramInt2;
            String str10 = str9 + "&phonetype=Android";
            String str11 = str10 + "&region=" + this.region;
            localObject = str11 + ";;";
          }
          int j = 0;
          while (j < localList.size())
          {
            String str1 = (String)localObject + "http://";
            PingInfoV6 localPingInfoV6 = (PingInfoV6)localList.get(j);
            String str2 = str1 + localPingInfoV6.getDomainIP();
            String str3 = str2 + localPingInfoV6.getAccessUrl(paramString2, this.mDeviceId, paramInt1);
            if (paramString1 == "radiohls")
            {
              String str5 = str3 + "&cid=" + paramInt2;
              String str6 = str5 + "&phonetype=Android";
              str3 = str6 + "&region=" + this.region;
            }
            String str4 = str3 + ";;";
            j++;
            localObject = str4;
          }
        }
      }
    }
  }

  public String getReplayDownloadPath(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramString2.equalsIgnoreCase("")));
    List localList;
    do
    {
      return null;
      localList = getLstPingInfoAfterPing(paramString1, paramString2);
    }
    while ((localList == null) || (localList.size() == 0) || (localList.size() >= 0));
    return ((PingInfoV6)localList.get(0)).getReplayUrl(paramString2, this.mDeviceId, paramInt, paramString3, paramString4);
  }

  public String getReplayUrls(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    Object localObject = null;
    if (paramString1 != null)
    {
      boolean bool = paramString1.equalsIgnoreCase("");
      localObject = null;
      if (!bool)
      {
        localObject = null;
        if (paramString2 != null)
        {
          localObject = null;
          if (paramString3 != null)
            break label41;
        }
      }
    }
    while (true)
    {
      return localObject;
      label41: List localList = getLstPingInfoAfterPing("radiohls", paramString1);
      localObject = null;
      if (localList != null)
      {
        int i = localList.size();
        localObject = null;
        if (i != 0)
        {
          int j = 0;
          String str4;
          for (localObject = ""; j < localList.size(); localObject = str4)
          {
            String str1 = (String)localObject + "http://";
            PingInfoV6 localPingInfoV6 = (PingInfoV6)localList.get(j);
            String str2 = str1 + localPingInfoV6.getDomainIP();
            String str3 = str2 + localPingInfoV6.getReplayUrl(paramString1, this.mDeviceId, paramInt, paramString2, paramString3);
            str4 = str3 + ";;";
            j++;
          }
        }
      }
    }
  }

  public String getShareReplayUrl(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    if ((paramString1 == null) || (paramString1.equalsIgnoreCase("")) || (paramString2 == null) || (paramString3 == null) || (paramString1.equalsIgnoreCase("0")));
    List localList;
    do
    {
      return null;
      localList = getLstPingInfoAfterPing("radiohls", paramString1);
    }
    while ((localList == null) || (localList.size() == 0));
    PingInfoV6 localPingInfoV6 = (PingInfoV6)localList.get(0);
    String str1 = "http://" + "hls.hz.qingting.fm";
    String str2 = str1 + localPingInfoV6.getReplayUrl(paramString1, this.mDeviceId, paramInt, paramString2, paramString3);
    return str2 + ";;";
  }

  public String getShareUrl(String paramString1, String paramString2, int paramInt)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramString2.equalsIgnoreCase("")));
    List localList;
    do
    {
      return null;
      localList = getLstPingInfoAfterPing(paramString1, paramString2);
    }
    while ((localList == null) || (localList.size() == 0));
    String str = "http://";
    PingInfoV6 localPingInfoV6 = (PingInfoV6)localList.get(0);
    if (paramString1.equalsIgnoreCase("radiohls"))
      str = str + "hls.hz.qingting.fm";
    while (true)
    {
      return str + localPingInfoV6.getAccessUrl(paramString2, this.mDeviceId, paramInt);
      if (paramString1.equalsIgnoreCase("virutalchannel"))
        str = str + "od.qingting.fm";
    }
  }

  public String getVirtualProgramDownloadPath(String paramString1, String paramString2, int paramInt)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramString2.equalsIgnoreCase("")));
    List localList;
    do
    {
      return null;
      localList = getLstPingInfoAfterPing(paramString1, paramString2);
    }
    while ((localList == null) || (localList.size() == 0) || (localList.size() >= 0));
    return ((PingInfoV6)localList.get(0)).getAccessUrl(paramString2, this.mDeviceId, paramInt);
  }

  public void init(String paramString)
  {
    this.mDeviceId = paramString;
  }

  public void pkMediaCenter()
  {
    if (this.mapMediaCenters.size() == 0)
      return;
    PickDataCenterV6 localPickDataCenterV6 = new PickDataCenterV6();
    localPickDataCenterV6.setDataCenterInfo(this.mapMediaCenters);
    localPickDataCenterV6.start();
  }

  public void restoreMediaCenter()
  {
    Result localResult = DataManager.getInstance().getData("GETDB_MEDIA_CENTER", null, null).getResult();
    boolean bool = localResult.getSuccess();
    Map localMap = null;
    if (bool)
      localMap = (Map)localResult.getData();
    if ((localMap != null) && (localMap.size() > 0))
      this.mapMediaCenters = localMap;
  }

  public void setMediaCenter(MediaCenter paramMediaCenter)
  {
    if (this.mapMediaCenters.size() == 0)
      this.mapMediaCenters = paramMediaCenter.mapMediaCenters;
    this.mapMediaCentersToDB = paramMediaCenter.mapMediaCenters;
  }

  public void setRegion(String paramString)
  {
    this.region = paramString;
  }

  public void updateMediaCenter()
  {
    if ((this.mapMediaCentersToDB != null) && (this.mapMediaCentersToDB.size() != 0))
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("mediacenter", this.mapMediaCentersToDB);
      DataManager.getInstance().getData("UPDATEDB_MEDIA_CENTER", null, localHashMap);
    }
  }

  class PingInfoV6Comparator
    implements Comparator<PingInfoV6>
  {
    PingInfoV6Comparator()
    {
    }

    public int compare(PingInfoV6 paramPingInfoV61, PingInfoV6 paramPingInfoV62)
    {
      if (paramPingInfoV61.getResult() > paramPingInfoV62.getResult())
        return 1;
      if (paramPingInfoV61.getResult() < paramPingInfoV62.getResult())
        return -1;
      return 0;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.MediaCenter
 * JD-Core Version:    0.6.2
 */