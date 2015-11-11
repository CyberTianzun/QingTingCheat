package fm.qingting.qtradio.model;

import java.util.Comparator;

class PingInfoComparator
  implements Comparator<PingInfo>
{
  public int compare(PingInfo paramPingInfo1, PingInfo paramPingInfo2)
  {
    if ((paramPingInfo1 == null) || (paramPingInfo2 == null));
    long l1;
    long l2;
    do
    {
      return -1;
      l1 = paramPingInfo1.getBestMDomainTime() + 2L * paramPingInfo1.getTotalBackUPSTime();
      l2 = paramPingInfo2.getBestMDomainTime() + 2L * paramPingInfo2.getTotalBackUPSTime();
    }
    while (l1 < l2);
    if (l1 > l2)
      return 1;
    return 0;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.PingInfoComparator
 * JD-Core Version:    0.6.2
 */