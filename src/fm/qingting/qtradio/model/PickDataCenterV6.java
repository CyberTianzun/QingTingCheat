package fm.qingting.qtradio.model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PickDataCenterV6 extends Thread
{
  private int MAX_TIME_OUT = 10000;
  public Map<String, List<PingInfoV6>> mapMediaCenter;
  public int pickCnt = 1;

  private double getRTTByUrl(String paramString1, String paramString2)
  {
    for (int i = 0; ; i++)
      while (true)
      {
        try
        {
          HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString1).openConnection();
          if (localHttpURLConnection == null)
            return 2147483647.0D;
          double d1 = System.currentTimeMillis();
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
              return 2147483647.0D;
            byte[] arrayOfByte1 = new byte[64];
            int k = 0;
            int m = localInputStream.read(arrayOfByte1);
            if (m == -1)
              break label225;
            k += m;
            if (k == j);
            try
            {
              byte[] arrayOfByte2 = paramString2.getBytes();
              if (i < arrayOfByte2.length)
              {
                if (arrayOfByte2[i] == arrayOfByte1[i])
                  break;
                return 2147483647.0D;
              }
              d2 = System.currentTimeMillis();
              localInputStream.close();
              localHttpURLConnection.disconnect();
              return (d2 - d1) / 1000.0D;
              continue;
            }
            finally
            {
            }
          }
        }
        catch (Exception localException)
        {
          return 2147483647.0D;
        }
        return 2147483647.0D;
        label225: double d2 = 2147483647.0D;
      }
  }

  private void log(String paramString)
  {
  }

  private void pkMediaCenter(List<PingInfoV6> paramList)
  {
    if (paramList == null);
    while (true)
    {
      return;
      for (int i = 0; i < paramList.size(); i++)
      {
        String str = ((PingInfoV6)paramList.get(i)).getDomainIP();
        double d = 2147483647.0D;
        if (str != null)
          d = getRTTByUrl(((PingInfoV6)paramList.get(i)).getPKURL(str), ((PingInfoV6)paramList.get(i)).res);
        ((PingInfoV6)paramList.get(i)).setReachTime(d);
        ((PingInfoV6)paramList.get(i)).setPinged(true);
      }
    }
  }

  private void sort()
  {
    if ((this.mapMediaCenter != null) && (this.mapMediaCenter.size() > 0))
    {
      Iterator localIterator = this.mapMediaCenter.entrySet().iterator();
      while (localIterator.hasNext())
      {
        List localList = (List)((Map.Entry)localIterator.next()).getValue();
        if ((localList != null) && (localList.size() > 0))
          Collections.sort(localList, new PingInfoV6Comparator());
      }
    }
  }

  public void run()
  {
    while (true)
    {
      int i;
      try
      {
        if (this.mapMediaCenter != null)
        {
          i = 0;
          if (i < this.pickCnt)
          {
            Iterator localIterator = this.mapMediaCenter.entrySet().iterator();
            if (!localIterator.hasNext())
              break label86;
            List localList = (List)((Map.Entry)localIterator.next()).getValue();
            if ((localList == null) || (localList.size() <= 0))
              continue;
            pkMediaCenter(localList);
            continue;
          }
        }
      }
      catch (Exception localException)
      {
      }
      return;
      label86: i++;
    }
  }

  public void setDataCenterInfo(Map<String, List<PingInfoV6>> paramMap)
  {
    this.mapMediaCenter = paramMap;
  }

  class PingInfoV6Comparator
    implements Comparator<PingInfoV6>
  {
    PingInfoV6Comparator()
    {
    }

    public int compare(PingInfoV6 paramPingInfoV61, PingInfoV6 paramPingInfoV62)
    {
      if ((paramPingInfoV61 == null) || (paramPingInfoV62 == null));
      double d1;
      double d2;
      do
      {
        return -1;
        d1 = paramPingInfoV61.getReachTime();
        d2 = paramPingInfoV62.getReachTime();
      }
      while (d1 < d2);
      if (d1 > d2)
        return 1;
      return 0;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.PickDataCenterV6
 * JD-Core Version:    0.6.2
 */