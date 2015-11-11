package fm.qingting.qtradio.model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PickDataCenter extends Thread
{
  private int MAX_TIME_OUT = 10000;
  public Map<String, DataCenterInfo> mapDataCenter;
  public int pickCnt = 1;
  private String serviceKey = "hls";

  private long getRTTByUrl(String paramString1, String paramString2)
  {
    for (int i = 0; ; i++)
      while (true)
      {
        try
        {
          HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString1).openConnection();
          if (localHttpURLConnection == null)
            return 2147483647L;
          long l1 = System.currentTimeMillis();
          localHttpURLConnection.setConnectTimeout(this.MAX_TIME_OUT);
          localHttpURLConnection.setReadTimeout(this.MAX_TIME_OUT);
          localHttpURLConnection.setRequestMethod("GET");
          localHttpURLConnection.setDoInput(true);
          localHttpURLConnection.connect();
          if (localHttpURLConnection.getResponseCode() == 200)
          {
            int j = localHttpURLConnection.getContentLength();
            InputStream localInputStream = localHttpURLConnection.getInputStream();
            if (j >= 64)
              return 2147483647L;
            byte[] arrayOfByte1 = new byte[64];
            int k = 0;
            int m = localInputStream.read(arrayOfByte1);
            if (m == -1)
              break label219;
            k += m;
            if (k == j);
            try
            {
              byte[] arrayOfByte2 = paramString2.getBytes();
              if (i < arrayOfByte2.length)
              {
                if (arrayOfByte2[i] == arrayOfByte1[i])
                  break;
                return 2147483647L;
              }
              l2 = System.currentTimeMillis();
              localInputStream.close();
              localHttpURLConnection.disconnect();
              return l2 - l1;
              continue;
            }
            finally
            {
            }
          }
        }
        catch (Exception localException)
        {
          return 2147483647L;
        }
        return 2147483647L;
        label219: long l2 = 2147483647L;
      }
  }

  private void log(String paramString)
  {
  }

  private void pkDownLoad(List<PingInfo> paramList)
  {
    if (paramList == null)
      return;
    int i = 0;
    label7: String str1;
    if (i < paramList.size())
    {
      str1 = ((PingInfo)paramList.get(i)).getSDomainIP();
      if (str1 == null)
        break label443;
    }
    label443: for (long l1 = getRTTByUrl(((PingInfo)paramList.get(i)).getPKURL(str1), ((PingInfo)paramList.get(i)).res); ; l1 = 2147483647L)
    {
      ((PingInfo)paramList.get(i)).setSReachTime(l1);
      String str2 = ((PingInfo)paramList.get(i)).getMDomainIP();
      long l2;
      if (str2 != null)
      {
        l2 = getRTTByUrl(((PingInfo)paramList.get(i)).getPKURL(str2), ((PingInfo)paramList.get(i)).res);
        ((PingInfo)paramList.get(i)).setBestMDomainIP(str2, l2);
      }
      while (true)
      {
        ((PingInfo)paramList.get(i)).setMReachTime(l2);
        if (((PingInfo)paramList.get(i)).mLstBackupIPs != null)
          for (int k = 0; k < ((PingInfo)paramList.get(i)).mLstBackupIPs.size(); k++)
          {
            String str4 = (String)((PingInfo)paramList.get(i)).mLstBackupIPs.get(k);
            long l4 = getRTTByUrl(((PingInfo)paramList.get(i)).getPKURL(str4), ((PingInfo)paramList.get(i)).res);
            ((PingInfo)paramList.get(i)).setBestMDomainIP(str4, l4);
          }
        if (((PingInfo)paramList.get(i)).sLstBackupIPs != null)
          for (int j = 0; j < ((PingInfo)paramList.get(i)).sLstBackupIPs.size(); j++)
          {
            String str3 = (String)((PingInfo)paramList.get(i)).sLstBackupIPs.get(j);
            long l3 = getRTTByUrl(((PingInfo)paramList.get(i)).getPKURL(str3), ((PingInfo)paramList.get(i)).res);
            ((PingInfo)paramList.get(i)).setTotalBackUPSTime(l3);
          }
        ((PingInfo)paramList.get(i)).setPinged(true);
        i++;
        break label7;
        break;
        l2 = 2147483647L;
      }
    }
  }

  private void pkHLS(List<PingInfo> paramList)
  {
    if (paramList == null)
      return;
    log("pkHLS begin");
    int i = 0;
    long l5;
    if (i < paramList.size())
    {
      String str1 = ((PingInfo)paramList.get(i)).getSDomainIP();
      if (str1 == null)
        break label595;
      l5 = getRTTByUrl(((PingInfo)paramList.get(i)).getPKURL(str1), ((PingInfo)paramList.get(i)).res);
      log("sdomain,ttl = " + l5 + " " + str1);
    }
    label595: for (long l1 = l5; ; l1 = 2147483647L)
    {
      ((PingInfo)paramList.get(i)).setSReachTime(l1);
      String str2 = ((PingInfo)paramList.get(i)).getMDomainIP();
      long l2;
      if (str2 != null)
      {
        l2 = getRTTByUrl(((PingInfo)paramList.get(i)).getPKURL(str2), ((PingInfo)paramList.get(i)).res);
        ((PingInfo)paramList.get(i)).setBestMDomainIP(str2, l2);
        log("mdomain,ttl = " + l2 + " " + str2);
      }
      while (true)
      {
        ((PingInfo)paramList.get(i)).setMReachTime(l2);
        if (((PingInfo)paramList.get(i)).mLstBackupIPs != null)
          for (int k = 0; k < ((PingInfo)paramList.get(i)).mLstBackupIPs.size(); k++)
          {
            String str4 = (String)((PingInfo)paramList.get(i)).mLstBackupIPs.get(k);
            long l4 = getRTTByUrl(((PingInfo)paramList.get(i)).getPKURL(str4), ((PingInfo)paramList.get(i)).res);
            ((PingInfo)paramList.get(i)).setBestMDomainIP(str4, l4);
            log("mBackIP,ttl = " + l4 + " " + str4);
          }
        if (((PingInfo)paramList.get(i)).sLstBackupIPs != null)
          for (int j = 0; j < ((PingInfo)paramList.get(i)).sLstBackupIPs.size(); j++)
          {
            String str3 = (String)((PingInfo)paramList.get(i)).sLstBackupIPs.get(j);
            long l3 = getRTTByUrl(((PingInfo)paramList.get(i)).getPKURL(str3), ((PingInfo)paramList.get(i)).res);
            ((PingInfo)paramList.get(i)).setTotalBackUPSTime(l3);
            log("sdomainIP,ttl = " + l3 + " " + str3);
          }
        ((PingInfo)paramList.get(i)).setPinged(true);
        i++;
        break;
        log("pkHLS end");
        return;
        l2 = 2147483647L;
      }
    }
  }

  public void run()
  {
    try
    {
      if (this.mapDataCenter != null)
        for (int i = 0; i < this.pickCnt; i++)
        {
          Iterator localIterator = this.mapDataCenter.entrySet().iterator();
          while (localIterator.hasNext())
          {
            DataCenterInfo localDataCenterInfo = (DataCenterInfo)((Map.Entry)localIterator.next()).getValue();
            if ((localDataCenterInfo != null) && (localDataCenterInfo.mapServiceInfo != null))
            {
              List localList = (List)localDataCenterInfo.mapServiceInfo.get(this.serviceKey);
              if (localList != null)
                if (this.serviceKey.equalsIgnoreCase("hls"))
                  pkHLS(localList);
                else if (this.serviceKey.equalsIgnoreCase("download"))
                  pkDownLoad(localList);
            }
          }
        }
    }
    catch (Exception localException)
    {
    }
  }

  public void setDataCenterInfo(Map<String, DataCenterInfo> paramMap)
  {
    this.mapDataCenter = paramMap;
  }

  public void setServiceKey(String paramString)
  {
    this.serviceKey = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.PickDataCenter
 * JD-Core Version:    0.6.2
 */