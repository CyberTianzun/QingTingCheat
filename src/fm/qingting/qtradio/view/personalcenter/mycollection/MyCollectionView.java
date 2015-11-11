package fm.qingting.qtradio.view.personalcenter.mycollection;

import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.tween.FrameTween;
import fm.qingting.framework.tween.FrameTween.SyncType;
import fm.qingting.framework.tween.IMotionHandler;
import fm.qingting.framework.tween.MotionController;
import fm.qingting.framework.tween.TweenProperty;
import fm.qingting.framework.tween.easing.Quad.EaseIn;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.view.MiniPlayerView;
import fm.qingting.qtradio.view.personalcenter.mydownload.EmptyTipsView;
import fm.qingting.qtradio.view.personalcenter.mydownload.GeneralManageView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyCollectionView extends ViewGroupViewImpl
  implements IEventHandler, RootNode.IInfoUpdateEventListener, InfoManager.ISubscribeEventListener, IMotionHandler
{
  private final ViewLayout checkbgLayout = this.standardLayout.createChildLT(48, 48, 30, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private int mButtonOffset = 0;
  private GeneralManageView mConfirmView;
  private EmptyTipsView mEmptyView;
  private MyCollectionListView mListView;
  private MiniPlayerView mPlayerView;
  private final ViewLayout miniLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 110, 720, 1200, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private MotionController motionController;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public MyCollectionView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.mEmptyView = new EmptyTipsView(paramContext, 0);
    addView(this.mEmptyView);
    this.mListView = new MyCollectionListView(paramContext, hashCode());
    this.mListView.setEventHandler(this);
    addView(this.mListView);
    this.mListView.setEmptyView(this.mEmptyView);
    this.mPlayerView = new MiniPlayerView(paramContext);
    addView(this.mPlayerView);
    this.mConfirmView = new GeneralManageView(paramContext);
    this.mConfirmView.setEventHandler(this);
    addView(this.mConfirmView);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 0);
    InfoManager.getInstance().registerViewTime(this);
    init();
  }

  private void deleteSelected(List<Object> paramList)
  {
    List localList1 = (List)this.mListView.getValue("allData", null);
    if ((localList1 != null) && (paramList != null) && (getListRealSize(localList1) == paramList.size()));
    for (int i = 1; ; i = 0)
    {
      if ((paramList != null) && (paramList.size() > 0))
        for (int j = -1 + paramList.size(); j >= 0; j--)
          InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.deleteFavNode((Node)paramList.get(j));
      List localList2 = InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes();
      this.mListView.update("resetCheckList", localList2);
      if (i != 0)
        dispatchActionEvent("emptynow", null);
      return;
    }
  }

  private int getListRealSize(List<Object> paramList)
  {
    int i = 0;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      if ((localIterator.next() instanceof ChannelNode))
        i++;
    return i;
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
    this.mConfirmView.layout(0, this.mButtonOffset + this.standardLayout.height, this.standardLayout.width, this.mButtonOffset + this.standardLayout.height + this.miniLayout.height);
    this.mPlayerView.layout(0, this.standardLayout.height - this.miniLayout.height - this.mButtonOffset, this.standardLayout.width, this.standardLayout.height - this.mButtonOffset);
  }

  @TargetApi(11)
  private void showDeleteButton()
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      GeneralManageView localGeneralManageView = this.mConfirmView;
      float[] arrayOfFloat = new float[1];
      arrayOfFloat[0] = (-this.miniLayout.height);
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
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(0, this);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    InfoManager.getInstance().unregisterViewTime(this);
    this.mPlayerView.destroy();
    this.mEmptyView.close(paramBoolean);
    super.close(paramBoolean);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("stateChanged"))
      if (((Boolean)paramObject2).booleanValue())
        this.mConfirmView.update(paramString, Boolean.valueOf(true));
    List localList;
    int i;
    do
    {
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
      }
      while (!paramString.equalsIgnoreCase("delete"));
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
    deleteSelected(localList);
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 0)
    {
      this.mListView.update("refreshList", null);
      if (InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes().size() == 0)
        dispatchActionEvent("emptynow", null);
    }
    else
    {
      return;
    }
    dispatchActionEvent("notEmpty", null);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mListView.layout(0, 0, this.standardLayout.width, this.standardLayout.height - this.miniLayout.height);
    this.mEmptyView.layout(0, 0, this.standardLayout.width, this.standardLayout.height - this.miniLayout.height);
    layoutMoveableViews();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.miniLayout.scaleToBounds(this.standardLayout);
    this.mListView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.miniLayout.height, 1073741824));
    this.mEmptyView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.miniLayout.height, 1073741824));
    this.miniLayout.measureView(this.mPlayerView);
    this.checkbgLayout.scaleToBounds(this.standardLayout);
    this.miniLayout.measureView(this.mConfirmView);
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
    this.mButtonOffset = ((int)(paramFloat1 * -this.miniLayout.height));
    layoutMoveableViews();
  }

  public void onMotionStart(MotionController paramMotionController)
  {
  }

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("viewTimeUpdated"))
      this.mListView.update("refreshList", null);
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void onTargetChange(MotionController paramMotionController, float paramFloat)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("showManage"))
    {
      this.mListView.update(paramString, Integer.valueOf(this.checkbgLayout.leftMargin + this.checkbgLayout.width));
      showDeleteButton();
    }
    do
    {
      return;
      if (paramString.equalsIgnoreCase("hideManage"))
      {
        this.mListView.update(paramString, paramObject);
        hideDeleteButton();
        return;
      }
      if (paramString.equalsIgnoreCase("setData"))
      {
        this.mListView.update(paramString, paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("resetCheckList"));
    this.mListView.update(paramString, paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.mycollection.MyCollectionView
 * JD-Core Version:    0.6.2
 */