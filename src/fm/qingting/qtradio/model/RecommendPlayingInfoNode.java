package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecommendPlayingInfoNode extends Node
{
  private int MAX_ITEMS_FRONTPAGE = 3;
  private List<RecommendPlayingItemNode> mLstPlayingItemsForAll = new ArrayList();
  private List<RecommendPlayingItemNode> mLstPlayingItemsForRest = new ArrayList();
  private List<RecommendPlayingItemNode> mLstRecommendPlaying;
  private List<RecommendPlayingItemNode> mLstRecommendPlayingForFrontPage = new ArrayList();
  private int minEndTimeForAll;
  private int minEndTimeForFrontPage;
  private int minPlayingProgramIndex = 0;

  public RecommendPlayingInfoNode()
  {
    this.nodeName = "recommendplayinginfo";
  }

  private List<RecommendPlayingItemNode> getCurrPlayingForAll()
  {
    int i = 0;
    if ((this.mLstRecommendPlaying == null) || (this.mLstRecommendPlaying.size() == 0))
      return null;
    long l = getRelativeTime(System.currentTimeMillis());
    this.mLstPlayingItemsForAll.clear();
    this.mLstPlayingItemsForRest.clear();
    int j = this.minPlayingProgramIndex;
    int k = 0;
    if (j < this.mLstRecommendPlaying.size())
    {
      RecommendPlayingItemNode localRecommendPlayingItemNode = (RecommendPlayingItemNode)this.mLstRecommendPlaying.get(j);
      if ((l >= localRecommendPlayingItemNode.startTime()) && (l < localRecommendPlayingItemNode.endTime()))
      {
        if (k == 0)
        {
          k = 1;
          this.minPlayingProgramIndex = j;
        }
        this.mLstPlayingItemsForAll.add(localRecommendPlayingItemNode);
      }
      while (true)
      {
        j++;
        break;
        if (l < localRecommendPlayingItemNode.startTime())
          this.mLstPlayingItemsForRest.add(localRecommendPlayingItemNode);
      }
    }
    this.minEndTimeForAll = 2147483647;
    if (this.mLstPlayingItemsForAll.size() > 0)
    {
      this.minEndTimeForAll = ((RecommendPlayingItemNode)this.mLstPlayingItemsForAll.get(0)).endTime();
      while (i < this.mLstPlayingItemsForAll.size())
      {
        if (((RecommendPlayingItemNode)this.mLstPlayingItemsForAll.get(i)).endTime() < this.minEndTimeForAll)
          this.minEndTimeForAll = ((RecommendPlayingItemNode)this.mLstPlayingItemsForAll.get(i)).endTime();
        i++;
      }
      return this.mLstPlayingItemsForAll;
    }
    return null;
  }

  private int getRelativeTime(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    return 60 * (i * 60) + j * 60;
  }

  public boolean checkRecommendPlayingList(long paramLong)
  {
    if ((this.mLstRecommendPlaying == null) || (this.mLstRecommendPlaying.size() == 0))
      return false;
    long l = getRelativeTime(1000L * paramLong);
    if (this.minEndTimeForAll < l)
      return true;
    if (this.minEndTimeForFrontPage < l)
      return true;
    for (int i = 0; ; i++)
      if ((i >= this.mLstPlayingItemsForRest.size()) || (((RecommendPlayingItemNode)this.mLstPlayingItemsForRest.get(i)).startTime() <= l))
      {
        if (i == this.mLstPlayingItemsForRest.size())
          break;
        getCurrPlayingForAll();
        getCurrPlayingFrontPageNodes();
        return true;
      }
  }

  public List<RecommendPlayingItemNode> getCurrPlayingForShow()
  {
    return getCurrPlayingForAll();
  }

  public List<RecommendPlayingItemNode> getCurrPlayingFrontPageNodes()
  {
    int i = 0;
    if ((this.mLstRecommendPlaying == null) || (this.mLstRecommendPlaying.size() == 0))
      return null;
    long l = getRelativeTime(System.currentTimeMillis());
    this.mLstRecommendPlayingForFrontPage.clear();
    int j = 0;
    int k = 0;
    while (true)
    {
      if (j < this.mLstRecommendPlaying.size())
      {
        RecommendPlayingItemNode localRecommendPlayingItemNode = (RecommendPlayingItemNode)this.mLstRecommendPlaying.get(j);
        if ((l > localRecommendPlayingItemNode.startTime()) && (l < localRecommendPlayingItemNode.endTime()) && (k < this.MAX_ITEMS_FRONTPAGE))
        {
          this.mLstRecommendPlayingForFrontPage.add(localRecommendPlayingItemNode);
          k++;
          if (k != this.MAX_ITEMS_FRONTPAGE);
        }
      }
      else
      {
        this.minEndTimeForFrontPage = 2147483647;
        if (this.mLstRecommendPlayingForFrontPage.size() <= 0)
          break label243;
        this.minEndTimeForFrontPage = ((RecommendPlayingItemNode)this.mLstRecommendPlayingForFrontPage.get(0)).endTime();
        while (i < this.mLstRecommendPlayingForFrontPage.size())
        {
          if (((RecommendPlayingItemNode)this.mLstRecommendPlayingForFrontPage.get(i)).endTime() < this.minEndTimeForFrontPage)
            this.minEndTimeForFrontPage = ((RecommendPlayingItemNode)this.mLstRecommendPlayingForFrontPage.get(i)).endTime();
          i++;
        }
      }
      j++;
    }
    return this.mLstRecommendPlayingForFrontPage;
    label243: return null;
  }

  public List<RecommendPlayingItemNode> getPlayingItemNodes(long paramLong)
  {
    if ((this.mLstRecommendPlaying == null) || (this.mLstRecommendPlaying.size() == 0))
      return null;
    ArrayList localArrayList = new ArrayList();
    long l = getRelativeTime(1000L * paramLong);
    for (int i = 0; i < this.mLstRecommendPlaying.size(); i++)
    {
      RecommendPlayingItemNode localRecommendPlayingItemNode = (RecommendPlayingItemNode)this.mLstRecommendPlaying.get(i);
      if ((l > localRecommendPlayingItemNode.startTime()) && (l < localRecommendPlayingItemNode.endTime()))
        localArrayList.add(localRecommendPlayingItemNode);
    }
    if (localArrayList.size() == 0)
      return null;
    return localArrayList;
  }

  public List<RecommendPlayingItemNode> getRecommendPlayingItemNodes(long paramLong)
  {
    if ((this.mLstRecommendPlaying == null) || (this.mLstRecommendPlaying.size() == 0))
      return null;
    ArrayList localArrayList = new ArrayList();
    long l = getRelativeTime(1000L * paramLong);
    for (int i = 0; i < this.mLstRecommendPlaying.size(); i++)
    {
      RecommendPlayingItemNode localRecommendPlayingItemNode = (RecommendPlayingItemNode)this.mLstRecommendPlaying.get(i);
      if ((l > localRecommendPlayingItemNode.startTime()) && (l < localRecommendPlayingItemNode.endTime()))
        localArrayList.add(localRecommendPlayingItemNode);
    }
    if (localArrayList.size() == 0)
      return null;
    return localArrayList;
  }

  public void setRecommendList(List<RecommendPlayingItemNode> paramList)
  {
    if (paramList.size() == 0)
      return;
    this.mLstPlayingItemsForAll.clear();
    this.mLstPlayingItemsForRest.clear();
    this.mLstRecommendPlayingForFrontPage.clear();
    this.minEndTimeForFrontPage = 0;
    this.minEndTimeForAll = 0;
    this.minPlayingProgramIndex = 0;
    this.mLstRecommendPlaying = paramList;
    try
    {
      Collections.sort(this.mLstRecommendPlaying, new RecommendPlayTimeComparator());
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void updateDB()
  {
  }

  class RecommendPlayTimeComparator
    implements Comparator<Node>
  {
    RecommendPlayTimeComparator()
    {
    }

    public int compare(Node paramNode1, Node paramNode2)
    {
      if (((RecommendPlayingItemNode)paramNode1).startTime() < ((RecommendPlayingItemNode)paramNode2).startTime())
        return -1;
      if (((RecommendPlayingItemNode)paramNode1).startTime() > ((RecommendPlayingItemNode)paramNode2).startTime())
        return 1;
      if (((RecommendPlayingItemNode)paramNode1).endTime() < ((RecommendPlayingItemNode)paramNode2).endTime())
        return -1;
      if (((RecommendPlayingItemNode)paramNode1).endTime() > ((RecommendPlayingItemNode)paramNode2).endTime())
        return 1;
      return 0;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.RecommendPlayingInfoNode
 * JD-Core Version:    0.6.2
 */