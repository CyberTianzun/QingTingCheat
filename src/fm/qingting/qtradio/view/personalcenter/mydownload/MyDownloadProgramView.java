package fm.qingting.qtradio.view.personalcenter.mydownload;

import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.tween.FrameTween;
import fm.qingting.framework.tween.FrameTween.SyncType;
import fm.qingting.framework.tween.IMotionHandler;
import fm.qingting.framework.tween.MotionController;
import fm.qingting.framework.tween.TweenProperty;
import fm.qingting.framework.tween.easing.Quad.EaseIn;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.DownLoadInfoNode.IDownloadInfoEventListener;
import fm.qingting.qtradio.model.Download;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.MiniPlayerView;
import fm.qingting.qtradio.view.popviews.AlertParam;
import fm.qingting.qtradio.view.popviews.AlertParam.Builder;
import fm.qingting.qtradio.view.popviews.AlertParam.OnButtonClickListener;
import fm.qingting.qtradio.view.scheduleview.SizeInfo;
import fm.qingting.utils.FuncUtils;
import fm.qingting.utils.QTMSGManage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MyDownloadProgramView extends ViewGroupViewImpl
  implements IEventHandler, IMotionHandler, DownLoadInfoNode.IDownloadInfoEventListener
{
  private final ViewLayout bottomLayout = this.standardLayout.createChildLT(720, 110, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout checkbgLayout = this.standardLayout.createChildLT(48, 48, 30, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout downloadMoreLayout = this.standardLayout.createChildLT(720, 100, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private DownloadMoreView downloadMoreView;
  private int mButtonOffset = 0;
  private GeneralManageView mConfirmView;
  private List<Node> mList;
  private MyDownloadListView mListView;
  private MiniPlayerView mPlayerView;
  private SortTagView mTagView;
  private MotionController motionController;
  private final ViewLayout orderLayout = this.standardLayout.createChildLT(720, 80, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private StorageInfoView storageView;

  public MyDownloadProgramView(Context paramContext)
  {
    super(paramContext);
    try
    {
      setBackgroundColor(SkinManager.getBackgroundColor());
      this.mListView = new MyDownloadListView(paramContext);
      this.mListView.setEventHandler(this);
      addView(this.mListView);
      this.downloadMoreView = new DownloadMoreView(paramContext);
      addView(this.downloadMoreView);
      this.storageView = new StorageInfoView(paramContext);
      addView(this.storageView);
      this.mPlayerView = new MiniPlayerView(paramContext);
      addView(this.mPlayerView);
      this.mConfirmView = new GeneralManageView(paramContext);
      this.mConfirmView.setEventHandler(this);
      addView(this.mConfirmView);
      this.mTagView = new SortTagView(paramContext);
      addView(this.mTagView);
      this.mTagView.setEventHandler(this);
      InfoManager.getInstance().root().mDownLoadInfoNode.registerListener(this);
      init();
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
        localOutOfMemoryError.printStackTrace();
    }
  }

  private void caculateStorage(List<Node> paramList)
  {
    if (paramList == null)
      return;
    int i;
    int j;
    ProgramNode localProgramNode;
    if (paramList != null)
    {
      Iterator localIterator = paramList.iterator();
      i = 0;
      j = 0;
      if (!localIterator.hasNext())
        break label76;
      localProgramNode = (ProgramNode)localIterator.next();
      if (localProgramNode.downloadInfo == null)
        break label92;
    }
    label76: label92: for (int k = i + localProgramNode.downloadInfo.fileSize; ; k = i)
    {
      j++;
      i = k;
      break;
      i = 0;
      j = 0;
      this.storageView.update("setUsageInfo", SizeInfo.getStorageInfo(j, i));
      return;
    }
  }

  private void deleteSelected(List<Object> paramList)
  {
    if ((paramList != null) && (paramList.size() > 0))
      for (int i = -1 + paramList.size(); i >= 0; i--)
        InfoManager.getInstance().root().mDownLoadInfoNode.delDownLoad((ProgramNode)paramList.get(i), true);
    this.mListView.update("resetCheckList", null);
  }

  @TargetApi(11)
  private void hideDeleteButton()
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(this.mConfirmView, "translationY", new float[] { 0.0F });
      ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(this.mPlayerView, "alpha", new float[] { 1.0F });
      AnimatorSet localAnimatorSet = new AnimatorSet();
      localAnimatorSet.play(localObjectAnimator1).with(localObjectAnimator2);
      localAnimatorSet.setDuration(200L);
      localAnimatorSet.start();
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new TweenProperty("position", 1.0F, 0.0F, 10.0F, new Quad.EaseIn()));
    FrameTween.to(this.motionController, this, localArrayList, FrameTween.SyncType.ASYNC);
  }

  @TargetApi(11)
  private void init()
  {
    if (!QtApiLevelManager.isApiLevelSupported(11))
      this.motionController = new MotionController(this);
  }

  private void layoutMoveableViews()
  {
    this.mConfirmView.layout(0, this.mButtonOffset + this.standardLayout.height, this.standardLayout.width, this.mButtonOffset + this.standardLayout.height + this.bottomLayout.height);
    this.storageView.layout(0, this.standardLayout.height - this.bottomLayout.height - this.storageView.getMeasuredHeight(), this.standardLayout.width, this.standardLayout.height - this.bottomLayout.height);
    this.mPlayerView.layout(0, this.standardLayout.height - this.bottomLayout.height - this.mButtonOffset, this.standardLayout.width, this.standardLayout.height - this.mButtonOffset);
  }

  @TargetApi(11)
  private void showDeleteButton()
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      GeneralManageView localGeneralManageView = this.mConfirmView;
      float[] arrayOfFloat = new float[1];
      arrayOfFloat[0] = (-this.bottomLayout.height);
      ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(localGeneralManageView, "translationY", arrayOfFloat);
      ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(this.mPlayerView, "alpha", new float[] { 0.0F });
      AnimatorSet localAnimatorSet = new AnimatorSet();
      localAnimatorSet.play(localObjectAnimator1).with(localObjectAnimator2);
      localAnimatorSet.setDuration(200L);
      localAnimatorSet.start();
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new TweenProperty("position", 0.0F, 1.0F, 10.0F, new Quad.EaseIn()));
    FrameTween.to(this.motionController, this, localArrayList, FrameTween.SyncType.ASYNC);
  }

  public void close(boolean paramBoolean)
  {
    this.mListView.close(paramBoolean);
    this.storageView.close(paramBoolean);
    this.mPlayerView.destroy();
    InfoManager.getInstance().root().mDownLoadInfoNode.unregisterListener(this);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public void onDownLoadInfoUpdated(int paramInt, Node paramNode)
  {
    if (paramInt == 1)
      this.mListView.update("refreshList", null);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("stateChanged"))
      if (((Boolean)paramObject2).booleanValue())
        this.mConfirmView.update(paramString, Boolean.valueOf(true));
    do
    {
      final List localList;
      int i;
      do
      {
        do
          return;
        while (((Boolean)this.mListView.getValue("hasCheckedIndexs", null)).booleanValue());
        this.mConfirmView.update(paramString, Boolean.valueOf(false));
        return;
        if (paramString.equalsIgnoreCase("selectAll"))
        {
          this.mListView.update(paramString, paramObject2);
          return;
        }
        if (!paramString.equalsIgnoreCase("delete"))
          break;
        localList = (List)this.mListView.getValue("deletelist", null);
        i = 0;
        if (localList != null)
        {
          int j = localList.size();
          i = 0;
          if (j > 0)
            i = 0 + localList.size();
        }
      }
      while (i == 0);
      String str = "确认删除这" + i + "个节目吗？";
      AlertParam localAlertParam = new AlertParam.Builder().setMessage(str).addButton("取消").addButton("确定").setListener(new AlertParam.OnButtonClickListener()
      {
        public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
        {
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
          MyDownloadProgramView.this.deleteSelected(localList);
        }
      }).create();
      EventDispacthManager.getInstance().dispatchAction("showAlert", localAlertParam);
      return;
    }
    while (!paramString.equalsIgnoreCase("converseOrder"));
    FuncUtils.revertNodesList(this.mList);
    this.mListView.update("resetData", this.mList);
    QTMSGManage.getInstance().sendStatistcsMessage("revertDownload");
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.downloadMoreView.layout(0, this.orderLayout.height, this.standardLayout.width, this.orderLayout.height + this.downloadMoreLayout.height);
    this.mListView.layout(0, this.orderLayout.height + this.downloadMoreLayout.height, this.standardLayout.width, this.standardLayout.height - this.storageView.getMeasuredHeight() - this.bottomLayout.height);
    this.orderLayout.layoutView(this.mTagView);
    layoutMoveableViews();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.bottomLayout.scaleToBounds(this.standardLayout);
    this.checkbgLayout.scaleToBounds(this.standardLayout);
    this.orderLayout.scaleToBounds(this.standardLayout);
    this.downloadMoreLayout.scaleToBounds(this.standardLayout);
    this.bottomLayout.measureView(this.mConfirmView);
    this.bottomLayout.measureView(this.storageView);
    this.orderLayout.measureView(this.mTagView);
    this.bottomLayout.measureView(this.mPlayerView);
    this.downloadMoreLayout.measureView(this.downloadMoreView);
    this.mListView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.storageView.getMeasuredHeight() - this.orderLayout.height - this.bottomLayout.height, 1073741824));
    setMeasuredDimension(i, j);
  }

  public void onMotionCancel(MotionController paramMotionController)
  {
  }

  public void onMotionComplete(MotionController paramMotionController)
  {
  }

  public void onMotionProgress(MotionController paramMotionController, float paramFloat1, float paramFloat2)
  {
    this.mButtonOffset = ((int)(paramFloat1 * -this.bottomLayout.height));
    layoutMoveableViews();
  }

  public void onMotionStart(MotionController paramMotionController)
  {
  }

  public void onTargetChange(MotionController paramMotionController, float paramFloat)
  {
  }

  public void setChannel(ChannelNode paramChannelNode)
  {
    this.downloadMoreView.setChannel(paramChannelNode);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("showManage"))
    {
      this.mListView.update(paramString, Integer.valueOf(this.checkbgLayout.leftMargin + this.checkbgLayout.width));
      showDeleteButton();
      this.mTagView.update("setEnable", Boolean.valueOf(false));
      return;
    }
    if (paramString.equalsIgnoreCase("hideManage"))
    {
      this.mListView.update(paramString, paramObject);
      hideDeleteButton();
      this.mTagView.update("setEnable", Boolean.valueOf(true));
      this.mListView.update("resetCheckList", null);
      return;
    }
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mList = ((List)paramObject);
      final ArrayList localArrayList = new ArrayList();
      if ((this.mList != null) && (this.mList.size() > 0))
        try
        {
          ProgramNode localProgramNode1 = (ProgramNode)this.mList.get(0);
          List localList = ChannelHelper.getInstance().getChannel(localProgramNode1.channelId, localProgramNode1.channelType).getAllLstProgramNode();
          for (int j = 0; j < localList.size(); j++)
          {
            ProgramNode localProgramNode2 = (ProgramNode)localList.get(j);
            if (!localArrayList.contains(Integer.valueOf(localProgramNode2.id)))
              localArrayList.add(Integer.valueOf(localProgramNode2.id));
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      Collections.sort(this.mList, new Comparator()
      {
        public int compare(Node paramAnonymousNode1, Node paramAnonymousNode2)
        {
          ProgramNode localProgramNode1 = (ProgramNode)paramAnonymousNode1;
          ProgramNode localProgramNode2 = (ProgramNode)paramAnonymousNode2;
          int i = localArrayList.indexOf(Integer.valueOf(localProgramNode1.id));
          int j = localArrayList.indexOf(Integer.valueOf(localProgramNode2.id));
          if ((i >= 0) && (j >= 0))
            if (i >= j);
          do
          {
            do
            {
              return -1;
              if (i == j)
                return 0;
              return 1;
            }
            while (i >= 0);
            if (j >= 0)
              return 1;
          }
          while (localProgramNode1.sequence < localProgramNode2.sequence);
          if (localProgramNode1.sequence == localProgramNode2.sequence)
            return 0;
          return 1;
        }
      });
      this.mListView.update(paramString, this.mList);
      if (this.mList == null)
        break label368;
    }
    label368: for (int i = this.mList.size(); ; i = 0)
    {
      this.mTagView.update("setNumber", Integer.valueOf(i));
      caculateStorage(this.mList);
      return;
      if (!paramString.equalsIgnoreCase("resetCheckList"))
        break;
      caculateStorage((List)this.mListView.getValue("allData", null));
      this.mListView.update(paramString, paramObject);
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.mydownload.MyDownloadProgramView
 * JD-Core Version:    0.6.2
 */