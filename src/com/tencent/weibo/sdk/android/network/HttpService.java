package com.tencent.weibo.sdk.android.network;

import java.util.Iterator;
import java.util.List;

public class HttpService
{
  private static HttpService instance = null;
  private final int TAG_RUNNING = 1;
  private final int TAG_WAITTING = 0;
  private List<HttpReq> mRunningReqList = null;
  private int mThreadNum = 4;
  private List<HttpReq> mWaitReqList = null;

  public static HttpService getInstance()
  {
    if (instance == null)
      instance = new HttpService();
    return instance;
  }

  public void SetAsynchronousTaskNum(int paramInt)
  {
  }

  public void addImmediateReq(HttpReq paramHttpReq)
  {
    paramHttpReq.setServiceTag(1);
    this.mRunningReqList.add(paramHttpReq);
    paramHttpReq.execute(new Void[0]);
  }

  public void addNormalReq(HttpReq paramHttpReq)
  {
    if (this.mRunningReqList.size() < this.mThreadNum)
    {
      paramHttpReq.setServiceTag(1);
      this.mRunningReqList.add(paramHttpReq);
      paramHttpReq.execute(new Void[0]);
      return;
    }
    paramHttpReq.setServiceTag(0);
    this.mWaitReqList.add(paramHttpReq);
  }

  public void cancelAllReq()
  {
    Iterator localIterator = this.mRunningReqList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.mWaitReqList.clear();
        return;
      }
      ((HttpReq)localIterator.next()).cancel(true);
    }
  }

  public void cancelReq(HttpReq paramHttpReq)
  {
    Iterator localIterator2;
    if (paramHttpReq.getServiceTag() == 1)
    {
      localIterator2 = this.mRunningReqList.iterator();
      break label29;
      label19: if (localIterator2.hasNext());
    }
    while (true)
    {
      label29: return;
      if ((HttpReq)localIterator2.next() != paramHttpReq)
        break label19;
      paramHttpReq.cancel(true);
      this.mRunningReqList.remove(paramHttpReq);
      break label19;
      if (paramHttpReq.getServiceTag() != 0)
        break;
      Iterator localIterator1 = this.mWaitReqList.iterator();
      while (localIterator1.hasNext())
        if (paramHttpReq == localIterator1.next())
          this.mWaitReqList.remove(paramHttpReq);
    }
  }

  public void onReqFinish(HttpReq paramHttpReq)
  {
    Iterator localIterator1 = this.mRunningReqList.iterator();
    if (!localIterator1.hasNext());
    while (true)
    {
      if ((this.mWaitReqList.size() > 0) && (this.mRunningReqList.size() < this.mThreadNum))
      {
        Iterator localIterator2 = this.mWaitReqList.iterator();
        HttpReq localHttpReq = (HttpReq)localIterator2.next();
        localIterator2.remove();
        localHttpReq.setServiceTag(1);
        this.mRunningReqList.add(localHttpReq);
        localHttpReq.execute(new Void[0]);
      }
      return;
      if (paramHttpReq != (HttpReq)localIterator1.next())
        break;
      localIterator1.remove();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.network.HttpService
 * JD-Core Version:    0.6.2
 */