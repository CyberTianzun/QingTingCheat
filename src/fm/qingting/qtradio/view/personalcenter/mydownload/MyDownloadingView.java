package fm.qingting.qtradio.view.personalcenter.mydownload;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.view.popviews.AlertParam;
import fm.qingting.qtradio.view.popviews.AlertParam.Builder;
import fm.qingting.qtradio.view.popviews.AlertParam.OnButtonClickListener;
import java.util.List;

public class MyDownloadingView extends ViewGroupViewImpl
  implements IEventHandler
{
  private MyDownloadListView mListView;
  private List<Node> mLstNodes;
  private MyDownloadTopView mManageView;
  private EmptyTipsView mTipsView;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private final ViewLayout topLayout = this.standardLayout.createChildLT(720, 110, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public MyDownloadingView(Context paramContext)
  {
    super(paramContext);
    this.mTipsView = new EmptyTipsView(paramContext, 3);
    addView(this.mTipsView);
    this.mListView = new MyDownloadListView(paramContext);
    this.mListView.setEventHandler(this);
    addView(this.mListView);
    this.mListView.setEmptyView(this.mTipsView);
    this.mManageView = new MyDownloadTopView(paramContext);
    this.mManageView.setEventHandler(this);
    addView(this.mManageView);
  }

  private void deleteSelected(List<Node> paramList)
  {
    if ((paramList != null) && (paramList.size() > 0))
      for (int i = -1 + paramList.size(); i >= 0; i--)
        InfoManager.getInstance().root().mDownLoadInfoNode.delDownLoading((Node)paramList.get(i), true);
    this.mLstNodes.clear();
    update("resetCheckList", null);
  }

  public void close(boolean paramBoolean)
  {
    this.mListView.close(false);
    this.mManageView.close(false);
    super.close(paramBoolean);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("hasCheckedIndexs"))
      return this.mListView.getValue(paramString, paramObject);
    if (paramString.equalsIgnoreCase("deletelist"))
      return this.mListView.getValue(paramString, paramObject);
    return null;
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("startAll"))
    {
      boolean bool = ((Boolean)paramObject2).booleanValue();
      List localList2 = (List)this.mListView.getValue("allData", null);
      if ((localList2 != null) && (localList2.size() > 0))
      {
        int k = 0;
        if (k < localList2.size())
        {
          if (bool)
            InfoManager.getInstance().root().mDownLoadInfoNode.pauseDownLoad((Node)localList2.get(k), true);
          while (true)
          {
            k++;
            break;
            InfoManager.getInstance().root().mDownLoadInfoNode.resumeDownLoad((Node)localList2.get(k));
          }
        }
      }
      this.mListView.update("changeProcessState", Boolean.valueOf(bool));
    }
    final List localList1;
    int i;
    do
    {
      return;
      if (!paramString.equalsIgnoreCase("deleteAll"))
        break;
      localList1 = (List)this.mListView.getValue("allData", null);
      i = 0;
      if (localList1 != null)
      {
        int j = localList1.size();
        i = 0;
        if (j > 0)
          i = 0 + localList1.size();
      }
    }
    while (i == 0);
    String str = "确认删除这" + i + "个节目吗？";
    AlertParam localAlertParam = new AlertParam.Builder().setMessage(str).addButton("取消").addButton("确定").setListener(new AlertParam.OnButtonClickListener()
    {
      public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
          SharedCfg.getInstance().setDeleteConfirm();
        switch (paramAnonymousInt)
        {
        default:
          return;
        case 0:
          EventDispacthManager.getInstance().dispatchAction("cancelPop", null);
          return;
        case 1:
        }
        EventDispacthManager.getInstance().dispatchAction("cancelPop", null);
        MyDownloadingView.this.deleteSelected(localList1);
      }
    }).create();
    EventDispacthManager.getInstance().dispatchAction("showAlert", localAlertParam);
    return;
    dispatchActionEvent(paramString, paramObject2);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.standardLayout.layoutView(this.mTipsView);
    this.topLayout.layoutView(this.mManageView);
    this.mListView.layout(0, this.topLayout.height, this.standardLayout.width, this.standardLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.topLayout.scaleToBounds(this.standardLayout);
    this.standardLayout.measureView(this.mTipsView);
    this.topLayout.measureView(this.mManageView);
    this.mListView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.topLayout.height, 1073741824));
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      if (paramObject == null)
        this.mManageView.setVisibility(4);
      while (true)
      {
        this.mListView.update(paramString, paramObject);
        return;
        this.mLstNodes = ((List)paramObject);
        if (this.mLstNodes.size() == 0)
          this.mManageView.setVisibility(4);
        else
          this.mManageView.setVisibility(0);
      }
    }
    if (paramString.equalsIgnoreCase("refreshList"))
    {
      this.mLstNodes = ((List)paramObject);
      if ((this.mLstNodes == null) || (this.mLstNodes.size() == 0))
        this.mManageView.setVisibility(4);
      while (true)
      {
        this.mListView.update("resetData", paramObject);
        return;
        this.mManageView.setVisibility(0);
      }
    }
    if (paramString.equalsIgnoreCase("resetCheckList"))
    {
      if ((this.mLstNodes == null) || (this.mLstNodes.size() == 0))
        this.mManageView.setVisibility(4);
      this.mListView.update(paramString, paramObject);
      return;
    }
    this.mListView.update(paramString, paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.mydownload.MyDownloadingView
 * JD-Core Version:    0.6.2
 */