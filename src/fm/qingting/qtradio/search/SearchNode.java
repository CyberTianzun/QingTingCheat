package fm.qingting.qtradio.search;

import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.SharedCfg;
import java.util.ArrayList;
import java.util.List;

public class SearchNode extends Node
{
  public static final int ALL = 0;
  public static final int CHANNEL = 3;
  public static final int EPISODE = 4;
  private static final int MAX_KEYWORDS_SIZE = 10;
  public static final int PODCASTOR = 2;
  private static final String SEPARATOR = "_";
  public static final int SHOW = 1;
  public static final String[] TABNAMES = { "全部", "专辑", "主播", "电台", "节目" };
  private String mLastKeyword;
  private List<SearchHotKeyword> mLstHotKeywords;
  private List<SearchItemNode> mLstSearchAll;
  private List<SearchItemNode> mLstSearchChannel;
  private List<SearchItemNode> mLstSearchPodcaster;
  private List<SearchItemNode> mLstSearchProgram;
  private List<SearchItemNode> mLstSearchVChannel;
  private List<String> mRecentKeywords;
  public int mSearchPageType = 1;
  private List<SearchItemNode> mSuggestions;
  private boolean mVoiceSearch;

  public SearchNode()
  {
    this.nodeName = "search";
  }

  private void addKeyword(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    for (int i = 0; i < this.mRecentKeywords.size(); i++)
      if (paramString.equalsIgnoreCase((String)this.mRecentKeywords.get(i)))
      {
        this.mRecentKeywords.remove(i);
        this.mRecentKeywords.add(0, paramString);
        updateToDB();
        return;
      }
    if (this.mRecentKeywords.size() >= 10)
      this.mRecentKeywords.remove(-1 + this.mRecentKeywords.size());
    this.mRecentKeywords.add(0, paramString);
    updateToDB();
  }

  private static int pickLargest(double[] paramArrayOfDouble)
  {
    int i = 0;
    if (paramArrayOfDouble == null)
      return 0;
    double d = -1.0D;
    int j = 0;
    while (i < paramArrayOfDouble.length)
    {
      if (d < paramArrayOfDouble[i])
      {
        d = paramArrayOfDouble[i];
        j = i;
      }
      i++;
    }
    return j;
  }

  private void restoreFromDB()
  {
    String str1 = SharedCfg.getInstance().getRecentKeyWords();
    if (str1 != null)
    {
      String[] arrayOfString = str1.split("_");
      if (arrayOfString != null)
        for (int i = 0; i < arrayOfString.length; i++)
        {
          String str2 = arrayOfString[i];
          if ((str2 != null) && (!str2.equalsIgnoreCase("")))
            this.mRecentKeywords.add(str2);
        }
    }
  }

  public void addRecentKeywords(String paramString)
  {
    if (paramString == null)
      return;
    addKeyword(paramString);
  }

  public void clearKeywords()
  {
    this.mRecentKeywords.clear();
    updateToDB();
  }

  public List<SearchHotKeyword> getHotKeywords()
  {
    return this.mLstHotKeywords;
  }

  public String getLastKeyword()
  {
    if ((this.mRecentKeywords != null) && (this.mRecentKeywords.size() > 0))
      return (String)this.mRecentKeywords.get(0);
    return null;
  }

  public List<String> getRecentKeywords()
  {
    return this.mRecentKeywords;
  }

  public List<SearchItemNode> getResult(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 0:
      return this.mLstSearchAll;
    case 1:
      return this.mLstSearchVChannel;
    case 2:
      return this.mLstSearchPodcaster;
    case 3:
      return this.mLstSearchChannel;
    case 4:
    }
    return this.mLstSearchProgram;
  }

  public int getSearchIndex(SearchItemNode paramSearchItemNode)
  {
    if (paramSearchItemNode == null);
    do
    {
      return -1;
      if (this.mSearchPageType == 1)
        return this.mLstSearchAll.indexOf(paramSearchItemNode);
      if (this.mSearchPageType == 2)
        return this.mLstSearchChannel.indexOf(paramSearchItemNode);
      if (this.mSearchPageType == 3)
        return this.mLstSearchVChannel.indexOf(paramSearchItemNode);
      if (this.mSearchPageType == 4)
        return this.mLstSearchProgram.indexOf(paramSearchItemNode);
    }
    while (this.mSearchPageType != 5);
    return this.mLstSearchPodcaster.indexOf(paramSearchItemNode);
  }

  public ArrayList<Integer> getSortedTypesByScore()
  {
    double[] arrayOfDouble = { 0.0D, 0.0D, 0.0D, 0.0D };
    if ((this.mLstSearchVChannel != null) && (this.mLstSearchVChannel.size() > 0))
      arrayOfDouble[0] = ((SearchItemNode)this.mLstSearchVChannel.get(0)).totalScore;
    if ((this.mLstSearchPodcaster != null) && (this.mLstSearchPodcaster.size() > 0))
      arrayOfDouble[1] = ((SearchItemNode)this.mLstSearchPodcaster.get(0)).totalScore;
    if ((this.mLstSearchChannel != null) && (this.mLstSearchChannel.size() > 0))
      arrayOfDouble[2] = ((SearchItemNode)this.mLstSearchChannel.get(0)).totalScore;
    if ((this.mLstSearchProgram != null) && (this.mLstSearchProgram.size() > 0))
      arrayOfDouble[3] = ((SearchItemNode)this.mLstSearchProgram.get(0)).totalScore;
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfDouble.length; i++)
    {
      int j = pickLargest(arrayOfDouble);
      arrayOfDouble[j] = -1.0D;
      localArrayList.add(Integer.valueOf(j + 1));
    }
    return localArrayList;
  }

  public List<SearchItemNode> getSuggestions()
  {
    return this.mSuggestions;
  }

  public boolean getVoiceSearch()
  {
    return this.mVoiceSearch;
  }

  public boolean hasResult()
  {
    return (this.mLstSearchAll != null) && (this.mLstSearchAll.size() > 0);
  }

  public void init()
  {
    this.mRecentKeywords = new ArrayList();
    restoreFromDB();
  }

  public void reset()
  {
    if (this.mLstSearchAll != null)
      this.mLstSearchAll.clear();
    if (this.mLstSearchChannel != null)
      this.mLstSearchChannel.clear();
    if (this.mLstSearchProgram != null)
      this.mLstSearchProgram.clear();
    if (this.mLstSearchVChannel != null)
      this.mLstSearchVChannel.clear();
    if (this.mLstSearchPodcaster != null)
      this.mLstSearchPodcaster.clear();
  }

  public void setHotKeywords(List<SearchHotKeyword> paramList)
  {
    this.mLstHotKeywords = paramList;
  }

  public void setLastKeyword(String paramString)
  {
    this.mLastKeyword = paramString;
  }

  public void setResult(int paramInt, List<SearchItemNode> paramList)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
      this.mLstSearchAll = paramList;
      return;
    case 1:
      this.mLstSearchVChannel = paramList;
      return;
    case 2:
      this.mLstSearchPodcaster = paramList;
      return;
    case 3:
      this.mLstSearchChannel = paramList;
      return;
    case 4:
    }
    this.mLstSearchProgram = paramList;
  }

  public void setSearchPageType(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
      this.mSearchPageType = 1;
      return;
    case 1:
      this.mSearchPageType = 3;
      return;
    case 2:
      this.mSearchPageType = 5;
      return;
    case 3:
      this.mSearchPageType = 2;
      return;
    case 4:
    }
    this.mSearchPageType = 4;
  }

  public void setSearchResult(SearchNode paramSearchNode)
  {
    if (paramSearchNode == null);
    String str;
    do
    {
      return;
      this.mLstSearchAll = paramSearchNode.mLstSearchAll;
      this.mLstSearchChannel = paramSearchNode.mLstSearchChannel;
      this.mLstSearchProgram = paramSearchNode.mLstSearchProgram;
      this.mLstSearchVChannel = paramSearchNode.mLstSearchVChannel;
      this.mLstSearchPodcaster = paramSearchNode.mLstSearchPodcaster;
      str = QTLogger.getInstance().buildSearchKeywordLogString(this.mLastKeyword, this.mLstSearchAll);
    }
    while (str == null);
    LogModule.getInstance().send("search_v6", str);
  }

  public void setSuggestions(List<SearchItemNode> paramList)
  {
    this.mSuggestions = paramList;
  }

  public void setVoiceSearch(boolean paramBoolean)
  {
    this.mVoiceSearch = false;
  }

  public void updateToDB()
  {
    if (this.mRecentKeywords == null)
      return;
    String str = "";
    for (int i = 0; i < this.mRecentKeywords.size(); i++)
    {
      str = str + (String)this.mRecentKeywords.get(i);
      if (-1 + this.mRecentKeywords.size() != i)
        str = str + "_";
    }
    SharedCfg.getInstance().setRecentKeyWords(str);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.search.SearchNode
 * JD-Core Version:    0.6.2
 */