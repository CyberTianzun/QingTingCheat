package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchProgram extends SearchBasic
{
  private List<SearchBroadcastTime> broadcastTimeList = new ArrayList();
  private String broadcaster;
  private String categoryRegion;
  public int channelType = 0;
  public int contentType = 0;
  public String dimensionid;
  private String pid;
  private String programName;

  public SearchProgram()
  {
    this.searchType = "program";
  }

  public SearchProgram(SearchProgram paramSearchProgram)
  {
    super(paramSearchProgram);
    this.searchType = "program";
    this.pid = paramSearchProgram.pid;
    this.broadcaster = paramSearchProgram.broadcaster;
    this.categoryRegion = paramSearchProgram.categoryRegion;
    this.programName = paramSearchProgram.programName;
    this.broadcastTimeList.clear();
    this.broadcastTimeList.addAll(paramSearchProgram.broadcastTimeList);
  }

  public SearchProgram(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12)
  {
    super(paramString1, paramString2, paramString4, paramString5, paramString6, paramString7, paramString8, paramString3);
    this.searchType = "program";
    this.pid = paramString9;
    this.broadcaster = paramString10;
    this.categoryRegion = paramString11;
    this.programName = paramString12;
  }

  public String getBroadCaster()
  {
    return this.broadcaster;
  }

  public String getCategoryRegion()
  {
    return this.categoryRegion;
  }

  public String getNextPlayTime()
  {
    if ((this.broadcastTimeList == null) || (this.broadcastTimeList.size() == 0))
      return null;
    Iterator localIterator = this.broadcastTimeList.iterator();
    SearchBroadcastTime localSearchBroadcastTime2;
    do
    {
      if (!localIterator.hasNext())
        break;
      localSearchBroadcastTime2 = (SearchBroadcastTime)localIterator.next();
    }
    while (!localSearchBroadcastTime2.isNextProgram());
    for (SearchBroadcastTime localSearchBroadcastTime1 = new SearchBroadcastTime(localSearchBroadcastTime2); ; localSearchBroadcastTime1 = null)
    {
      if (localSearchBroadcastTime1 == null)
        localSearchBroadcastTime1 = new SearchBroadcastTime((SearchBroadcastTime)this.broadcastTimeList.get(0));
      return localSearchBroadcastTime1.getText();
    }
  }

  public String getPid()
  {
    return this.pid;
  }

  public String getProgramName()
  {
    return this.programName;
  }

  public void setBroadCaster(String paramString)
  {
    this.broadcaster = paramString;
  }

  public void setBroadcastTime(List<SearchBroadcastTime> paramList)
  {
    if (paramList == null)
      return;
    this.broadcastTimeList.clear();
    this.broadcastTimeList.addAll(paramList);
  }

  public void setCategoryRegion(String paramString)
  {
    this.categoryRegion = paramString;
  }

  public void setPid(String paramString)
  {
    this.pid = paramString;
  }

  public void setProgramName(String paramString)
  {
    this.programName = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.SearchProgram
 * JD-Core Version:    0.6.2
 */