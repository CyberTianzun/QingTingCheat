package fm.qingting.qtradio.model;

import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataCenterInfo
{
  private Map<String, List<String>> mapBestDataCenter = new HashMap();
  public Map<String, List<PingInfo>> mapServiceInfo;

  public List<String> getBestDataCenterByName(String paramString)
  {
    Object localObject;
    if (paramString == null)
      localObject = null;
    do
    {
      return localObject;
      if (!this.mapBestDataCenter.containsKey(paramString))
        break;
      localObject = (List)this.mapBestDataCenter.get(paramString);
    }
    while (localObject != null);
    if (this.mapServiceInfo != null)
    {
      List localList = (List)this.mapServiceInfo.get(paramString);
      if ((localList == null) || (localList.size() == 0))
        return null;
      Collections.sort(localList, new PingInfoComparator());
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      int i = 0;
      if (i < localList.size())
      {
        if (!((PingInfo)localList.get(i)).hasPinged())
          return null;
        String str2 = ((PingInfo)localList.get(i)).getBestMDomainIP();
        if ((str2 != null) && (!str2.equalsIgnoreCase("")))
        {
          if (!paramString.equalsIgnoreCase("download"))
            break label239;
          localArrayList1.add(0, str2);
          localArrayList2.add(0, Long.valueOf(((PingInfo)localList.get(i)).getBestMDomainTime() + 2L * ((PingInfo)localList.get(i)).getTotalBackUPSTime()));
        }
        while (true)
        {
          i++;
          break;
          label239: localArrayList1.add(str2);
          localArrayList2.add(Long.valueOf(((PingInfo)localList.get(i)).getBestMDomainTime() + 2L * ((PingInfo)localList.get(i)).getTotalBackUPSTime()));
        }
      }
      String str1 = QTLogger.getInstance().buildSpeedTest(paramString, localArrayList1, localArrayList2);
      if (str1 != null)
        LogModule.getInstance().send("SpeedTestResult", str1);
      this.mapBestDataCenter.put(paramString, localArrayList1);
      return localArrayList1;
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.DataCenterInfo
 * JD-Core Version:    0.6.2
 */