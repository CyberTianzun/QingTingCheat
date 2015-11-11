package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HotDJRank extends Broadcaster
{
  public static final String PREFIX = "主播:";
  public static final String REQUIREMENT = "正在主持:";
  public String avatar;
  private String currentChannelID;
  public List<HotDJChannel> hotdjChannel = new ArrayList();

  public HotDJRank(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean, int paramInt, String paramString5, String paramString6)
  {
    super(paramString1, paramString2, paramString3, paramString4, paramBoolean, paramInt, paramString5);
    this.avatar = paramString6;
  }

  public void addElement(HotDJChannel paramHotDJChannel)
  {
    this.hotdjChannel.add(paramHotDJChannel);
  }

  public Broadcaster getBroadcaster()
  {
    return new Broadcaster(this.id, this.nick, this.vuid, this.vname, this.isVip, this.digcount, this.bgphoto);
  }

  public String getCurrentChannelID()
  {
    return this.currentChannelID;
  }

  public String[] getCurrentDJChannelName()
  {
    Iterator localIterator = this.hotdjChannel.iterator();
    String[] arrayOfString = new String[2];
    Object localObject = null;
    HotDJChannel localHotDJChannel;
    String str;
    int i;
    if (localIterator.hasNext())
    {
      localHotDJChannel = (HotDJChannel)localIterator.next();
      str = localHotDJChannel.getCurrentChannelName();
      this.currentChannelID = localHotDJChannel.id;
      if (str == null)
        i = 0;
    }
    while (true)
    {
      if (i == 0)
        arrayOfString[1] = ("主播:" + (String)localObject);
      return arrayOfString;
      if (str.indexOf("正在主持:") == -1)
      {
        if (localObject == null);
        while (true)
        {
          arrayOfString[0] = localHotDJChannel.getName();
          localObject = str;
          break;
          str = (String)localObject + " " + str;
        }
      }
      arrayOfString[0] = localHotDJChannel.getName();
      arrayOfString[1] = str;
      i = 1;
      continue;
      i = 0;
    }
  }

  public boolean isCurrent()
  {
    Iterator localIterator = this.hotdjChannel.iterator();
    while (localIterator.hasNext())
      if (((HotDJChannel)localIterator.next()).isCurrent())
        return true;
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.HotDJRank
 * JD-Core Version:    0.6.2
 */