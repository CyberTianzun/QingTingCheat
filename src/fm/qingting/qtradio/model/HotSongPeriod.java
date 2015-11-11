package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.List;

public class HotSongPeriod
{
  public String id;
  public List<SongDetail> songList = new ArrayList();
  public String title;
  public String updateTime;

  public HotSongPeriod(String paramString1, String paramString2, String paramString3)
  {
    this.id = paramString1;
    this.title = paramString2;
    this.updateTime = paramString3;
  }

  public void addElement(SongDetail paramSongDetail)
  {
    this.songList.add(paramSongDetail);
  }

  public void setHotDJChannel(List<SongDetail> paramList)
  {
    if (this.songList != null)
    {
      this.songList.clear();
      this.songList = null;
    }
    this.songList = new ArrayList(paramList);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.HotSongPeriod
 * JD-Core Version:    0.6.2
 */