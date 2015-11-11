package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.List;

public class RingToneInfoNode extends Node
{
  private String mCurrAvailableRingId;
  private List<BroadcasterNode> mLstBroadcaster;
  public List<Node> mLstRingToneNodes;
  private int mRingCatId;
  private int mRingChannelId;
  private int mRingChannelType;
  private int mRingParentId;
  private int mRingProgramId;

  public RingToneInfoNode()
  {
    this.nodeName = "ringtoneinfo";
  }

  public void addToRingTone(Node paramNode)
  {
    if ((paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("ringtone")))
      return;
    if (((RingToneNode)paramNode).ringToneId.equalsIgnoreCase("2"))
      ((RingToneNode)paramNode).ringDesc = "女仆驾到哦";
    for (int i = 0; ; i++)
    {
      if (i >= this.mLstRingToneNodes.size())
        break label189;
      if (((RingToneNode)this.mLstRingToneNodes.get(i)).ringToneId.equalsIgnoreCase(((RingToneNode)paramNode).ringToneId))
      {
        ((RingToneNode)this.mLstRingToneNodes.get(i)).updateRingTone((RingToneNode)paramNode);
        ((RingToneNode)this.mLstRingToneNodes.get(i)).downloadInfo = ((RingToneNode)paramNode).downloadInfo;
        return;
        if (((RingToneNode)paramNode).ringToneId.equalsIgnoreCase("1"))
        {
          ((RingToneNode)paramNode).ringDesc = "李季的起床派对";
          break;
        }
        if (!((RingToneNode)paramNode).ringToneId.equalsIgnoreCase("3"))
          break;
        ((RingToneNode)paramNode).ringDesc = "怪蜀黍叫你起床啦";
        break;
      }
    }
    label189: this.mLstRingToneNodes.add(paramNode);
  }

  public String getAvailableRingId()
  {
    return this.mCurrAvailableRingId;
  }

  public BroadcasterNode getBroadcasterByRoneId(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return null;
    for (int i = 0; i < this.mLstBroadcaster.size(); i++)
      if (paramString.equalsIgnoreCase(String.valueOf(((BroadcasterNode)this.mLstBroadcaster.get(i)).ringToneId)))
        return (BroadcasterNode)this.mLstBroadcaster.get(i);
    return null;
  }

  public int getRingCatId()
  {
    return this.mRingCatId;
  }

  public int getRingChannelId()
  {
    return this.mRingChannelId;
  }

  public int getRingChannelType()
  {
    return this.mRingChannelType;
  }

  public RingToneNode getRingNodeById(String paramString)
  {
    if (paramString == null)
      return null;
    for (int i = 0; i < this.mLstRingToneNodes.size(); i++)
      if (((RingToneNode)this.mLstRingToneNodes.get(i)).ringToneId.equalsIgnoreCase(paramString))
        return (RingToneNode)this.mLstRingToneNodes.get(i);
    return null;
  }

  public int getRingParentId()
  {
    return this.mRingParentId;
  }

  public int getRingProgramId()
  {
    return this.mRingProgramId;
  }

  public RingToneNode getRingToneByUserId(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return null;
    if (this.mLstRingToneNodes == null)
      return null;
    for (int i = 0; i < this.mLstRingToneNodes.size(); i++)
      if (((RingToneNode)this.mLstRingToneNodes.get(i)).belongToBroadcaster(paramString))
        return (RingToneNode)this.mLstRingToneNodes.get(i);
    return null;
  }

  public boolean hasRingTone(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    while (true)
    {
      return false;
      if (this.mLstRingToneNodes != null)
        for (int i = 0; i < this.mLstRingToneNodes.size(); i++)
          if (((RingToneNode)this.mLstRingToneNodes.get(i)).belongToBroadcaster(paramString))
            return true;
    }
  }

  public void init()
  {
    if (this.mLstRingToneNodes == null)
      this.mLstRingToneNodes = new ArrayList();
    if (this.mLstBroadcaster == null)
    {
      this.mLstBroadcaster = new ArrayList();
      BroadcasterNode localBroadcasterNode1 = new BroadcasterNode();
      localBroadcasterNode1.id = 3684;
      localBroadcasterNode1.avatar = "http://tp1.sinaimg.cn/1812508720/180/22841453705/1";
      localBroadcasterNode1.weiboName = "李季先生";
      localBroadcasterNode1.weiboId = "1812508720";
      localBroadcasterNode1.nick = "李季先生";
      localBroadcasterNode1.ringToneId = 1;
      this.mLstBroadcaster.add(localBroadcasterNode1);
      BroadcasterNode localBroadcasterNode2 = new BroadcasterNode();
      localBroadcasterNode2.id = 2031;
      localBroadcasterNode2.avatar = "http://tp1.sinaimg.cn/1743620792/180/40008201366/0";
      localBroadcasterNode2.weiboName = "1058小昕";
      localBroadcasterNode2.weiboId = "1743620792";
      localBroadcasterNode2.nick = "1058小昕";
      localBroadcasterNode2.ringToneId = 2;
      this.mLstBroadcaster.add(localBroadcasterNode2);
      BroadcasterNode localBroadcasterNode3 = new BroadcasterNode();
      localBroadcasterNode3.id = 2029;
      localBroadcasterNode3.avatar = "http://tp2.sinaimg.cn/1747314141/180/40023309355/1";
      localBroadcasterNode3.weiboName = "1058小K";
      localBroadcasterNode3.weiboId = "1747314141";
      localBroadcasterNode3.nick = "1058小K";
      localBroadcasterNode3.ringToneId = 3;
      this.mLstBroadcaster.add(localBroadcasterNode3);
      BroadcasterNode localBroadcasterNode4 = new BroadcasterNode();
      localBroadcasterNode4.id = 1;
      localBroadcasterNode4.avatar = "";
      localBroadcasterNode4.weiboName = "蜻蜓FM主播";
      localBroadcasterNode4.weiboId = "2673619603";
      localBroadcasterNode4.nick = "蜻蜓FM主播";
      localBroadcasterNode4.ringToneId = 0;
      this.mLstBroadcaster.add(localBroadcasterNode4);
    }
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    List localList = (List)paramObject;
    if ((localList == null) || (paramString == null) || (localList.size() == 0));
    while (!paramString.equalsIgnoreCase("ARTNL"))
      return;
    setRingToneList(localList);
  }

  public void setAvaliableRingId(String paramString)
  {
    this.mCurrAvailableRingId = paramString;
  }

  public void setRingCatId(int paramInt)
  {
    this.mRingCatId = paramInt;
  }

  public void setRingChannelId(int paramInt)
  {
    this.mRingChannelId = paramInt;
  }

  public void setRingChannelType(int paramInt)
  {
    this.mRingChannelType = paramInt;
  }

  public void setRingParentId(int paramInt)
  {
    this.mRingParentId = paramInt;
  }

  public void setRingProgramId(int paramInt)
  {
    this.mRingProgramId = paramInt;
  }

  public void setRingToneList(List<Node> paramList)
  {
    if ((this.mLstRingToneNodes == null) || (this.mLstRingToneNodes.size() == 0))
    {
      this.mLstRingToneNodes = paramList;
      return;
    }
    int i = this.mLstRingToneNodes.size();
    int j = 0;
    if (j < paramList.size())
    {
      RingToneNode localRingToneNode1 = (RingToneNode)paramList.get(j);
      for (int k = 0; ; k++)
        if (k < i)
        {
          RingToneNode localRingToneNode2 = (RingToneNode)this.mLstRingToneNodes.get(k);
          if (localRingToneNode2.ringToneId.equalsIgnoreCase(localRingToneNode1.ringToneId))
            localRingToneNode1.updateRingTone(localRingToneNode2);
        }
        else
        {
          j++;
          break;
        }
    }
    this.mLstRingToneNodes = paramList;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.RingToneInfoNode
 * JD-Core Version:    0.6.2
 */