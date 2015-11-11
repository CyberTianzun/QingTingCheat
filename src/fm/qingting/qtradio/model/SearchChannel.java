package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.List;

public class SearchChannel extends SearchBasic
{
  public String catName = "";
  private String categoryRegion;
  public int channelType = 0;
  public int contentType = 0;
  public String dimensionName = "";
  public String dimensionid;
  private List<String> freqs = new ArrayList();

  public SearchChannel()
  {
    this.searchType = "channel";
  }

  public SearchChannel(SearchChannel paramSearchChannel)
  {
    super(paramSearchChannel);
    this.searchType = "channel";
    this.categoryRegion = paramSearchChannel.categoryRegion;
    this.freqs.clear();
    this.freqs.addAll(paramSearchChannel.freqs);
  }

  public SearchChannel(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    super(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8);
    this.searchType = "channel";
    this.categoryRegion = paramString9;
  }

  public String getCategoryRegion()
  {
    return this.categoryRegion;
  }

  public List<String> getFreqs()
  {
    return this.freqs;
  }

  public void setCategoryRegion(String paramString)
  {
    this.categoryRegion = paramString;
  }

  public void setFreqs(List<String> paramList)
  {
    this.freqs.clear();
    this.freqs.addAll(paramList);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.SearchChannel
 * JD-Core Version:    0.6.2
 */