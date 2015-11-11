package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HotSongRank
{
  public List<BroadcastTime> broadcastTime = new ArrayList();
  public String channelid;
  public String channelname;
  public String pic;
  public String rid;
  public String songRankName;
  public String songRankid;
  public String timeStr;

  public HotSongRank(HotSongRank paramHotSongRank)
  {
    this.channelname = paramHotSongRank.channelname;
    this.channelid = paramHotSongRank.channelid;
    this.pic = paramHotSongRank.pic;
    this.songRankid = paramHotSongRank.songRankid;
    this.songRankName = paramHotSongRank.songRankName;
    this.rid = paramHotSongRank.rid;
    this.timeStr = paramHotSongRank.timeStr;
    if (this.broadcastTime != null)
    {
      this.broadcastTime.clear();
      this.broadcastTime = null;
    }
    this.broadcastTime = new ArrayList(paramHotSongRank.broadcastTime);
  }

  public HotSongRank(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.channelname = paramString1;
    this.channelid = paramString2;
    this.pic = paramString3;
    this.songRankid = paramString4;
    this.songRankName = paramString5;
    this.rid = paramString6;
    this.timeStr = paramString7;
  }

  public void addElement(BroadcastTime paramBroadcastTime)
  {
    this.broadcastTime.add(paramBroadcastTime);
  }

  public boolean isCurrent()
  {
    if ((this.broadcastTime == null) || (this.broadcastTime.size() == 0))
      return false;
    Iterator localIterator = this.broadcastTime.iterator();
    while (localIterator.hasNext())
      if (((BroadcastTime)localIterator.next()).isCurrentTime())
        return true;
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.HotSongRank
 * JD-Core Version:    0.6.2
 */