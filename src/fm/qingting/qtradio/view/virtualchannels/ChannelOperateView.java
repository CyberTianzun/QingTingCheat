package fm.qingting.qtradio.view.virtualchannels;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.Toast;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
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
import fm.qingting.utils.QTMSGManage;
import java.util.ArrayList;
import java.util.List;

public class ChannelOperateView extends QtListItemView
  implements RootNode.IInfoUpdateEventListener, InfoManager.ISubscribeEventListener
{
  private final DrawFilter filter = SkinManager.getInstance().getDrawFilter();
  private final ViewLayout iconLayout = this.standardLayout.createChildLT(50, 50, 0, 25, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.standardLayout.createChildLT(1, 60, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private List<VirtualAction> mActions = new ArrayList();
  private final Paint mBgPaint = new Paint();
  private Rect mBgRect = new Rect();
  private int mCount = 0;
  private boolean mDark = false;
  private final Paint mDarkPaint = new Paint();
  private boolean mFaved = true;
  private final Paint mHighlightBgPaint = new Paint();
  private Rect mIconRect = new Rect();
  private boolean mInTouchMode = false;
  private boolean mIsMusic = false;
  private final Paint mLabelBgPaint = new Paint();
  private final Paint mLabelPaint = new Paint();
  private final Rect mLabelTextBound = new Rect();
  private final RectF mLabelTipRectF = new RectF();
  private float mLastMotionX = 0.0F;
  private float mLastMotionY = 0.0F;
  private final Paint mLinePaint = new Paint();
  private Node mNode;
  private final Paint mPaint = new Paint();
  private int mSelectedIndex = -1;
  private final Paint mShadowLinePaint = new Paint();
  private Rect mShadowRect = new Rect();
  private String mWid = null;
  private final ViewLayout shadowLineLayout = this.standardLayout.createChildLT(720, 1, 0, 100, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 101, 720, 101, 0, 0, ViewLayout.FILL);

  public ChannelOperateView(Context paramContext)
  {
    super(paramContext);
    this.mHighlightBgPaint.setColor(SkinManager.getItemHighlightMaskColor());
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 0);
    init();
    setItemSelectedEnable();
    ColorMatrixColorFilter localColorMatrixColorFilter = new ColorMatrixColorFilter(new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 95.0F, 0.0F, 0.0F, 0.0F, 0.0F, 100.0F, 0.0F, 0.0F, 0.0F, 0.0F, 110.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F });
    this.mDarkPaint.setColorFilter(localColorMatrixColorFilter);
    Paint localPaint = this.mBgPaint;
    if (this.mDark);
    for (int i = SkinManager.getBackgroundColor(); ; i = -1)
    {
      localPaint.setColor(i);
      this.mShadowLinePaint.setColor(-2302237);
      this.mLabelBgPaint.setColor(SkinManager.getTextColorHighlight());
      this.mLabelBgPaint.setStyle(Paint.Style.FILL);
      this.mLabelPaint.setColor(-1);
      this.mLabelPaint.setTextSize(SkinManager.getInstance().getSmallLabelTextSize());
      return;
    }
  }

  private void drawBg(Canvas paramCanvas)
  {
    int i = paramCanvas.save();
    paramCanvas.clipRect(0, 0, this.standardLayout.width, this.standardLayout.height);
    paramCanvas.drawLine(0.0F, this.shadowLineLayout.getTop(), this.shadowLineLayout.getRight(), this.shadowLineLayout.getTop(), this.mShadowLinePaint);
    paramCanvas.restoreToCount(i);
  }

  private void drawItem(Canvas paramCanvas, VirtualAction paramVirtualAction, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBoolean)
      paramCanvas.drawRect(paramInt1, 0.0F, paramInt2, this.standardLayout.height, this.mHighlightBgPaint);
    BitmapResourceCache localBitmapResourceCache = BitmapResourceCache.getInstance();
    Resources localResources = getResources();
    int i;
    Bitmap localBitmap;
    Rect localRect;
    Paint localPaint;
    if (paramBoolean)
    {
      i = paramVirtualAction.getHighlightRes(this.mIsMusic);
      localBitmap = localBitmapResourceCache.getResourceCache(localResources, this, i);
      this.mIconRect.offset((paramInt1 + paramInt2) / 2, 0);
      localRect = this.mIconRect;
      if (!paramBoolean)
        break label152;
      localPaint = this.mPaint;
    }
    while (true)
    {
      paramCanvas.drawBitmap(localBitmap, null, localRect, localPaint);
      if (paramVirtualAction == VirtualAction.MEMBERS)
        drawTip(paramCanvas);
      this.mIconRect.offset(-(paramInt1 + paramInt2) / 2, 0);
      return;
      i = paramVirtualAction.getNormalRes(this.mFaved, this.mIsMusic);
      break;
      label152: if (this.mDark)
        localPaint = this.mDarkPaint;
      else
        localPaint = this.mPaint;
    }
  }

  private void drawItems(Canvas paramCanvas)
  {
    int i = this.mActions.size();
    int j = 0;
    if (j < i)
    {
      int k = j * (this.standardLayout.width / i);
      int m = this.standardLayout.width / i * (j + 1);
      int n;
      label55: int i1;
      VirtualAction localVirtualAction;
      if (j == i - 1)
      {
        n = 0;
        i1 = m - n;
        localVirtualAction = (VirtualAction)this.mActions.get(j);
        if ((!isItemPressed()) || (this.mSelectedIndex != j))
          break label126;
      }
      label126: for (boolean bool = true; ; bool = false)
      {
        drawItem(paramCanvas, localVirtualAction, k, i1, bool);
        j++;
        break;
        n = this.lineLayout.width;
        break label55;
      }
    }
  }

  private void drawTip(Canvas paramCanvas)
  {
    if (this.mCount == 0)
      return;
    int i = this.mCount;
    if (i >= 100);
    int k;
    for (String str = "99+"; ; str = String.valueOf(i))
    {
      this.mLabelPaint.getTextBounds(str, 0, str.length(), this.mLabelTextBound);
      int j = this.mLabelTextBound.right + this.mLabelTextBound.left;
      k = 2 * (this.mIconRect.bottom - this.mIconRect.top) / 3;
      if (j > k * 2 / 3)
        break;
      float f1 = this.mIconRect.right - k / 10;
      float f2 = this.mIconRect.top + k / 7;
      paramCanvas.drawCircle(f1, f2, k / 2, this.mLabelBgPaint);
      paramCanvas.drawText(str, f1 - (this.mLabelTextBound.right + this.mLabelTextBound.left) / 2, f2 - (this.mLabelTextBound.top + this.mLabelTextBound.bottom) / 2, this.mLabelPaint);
      return;
    }
    this.mLabelTipRectF.set(this.mIconRect.right - k / 2 - k / 10, this.mIconRect.top - k / 2, this.mIconRect.right + k / 2 - k / 10, this.mIconRect.top + k / 2);
    paramCanvas.drawRoundRect(this.mLabelTipRectF, k / 3, k / 3, this.mLabelBgPaint);
    paramCanvas.drawText(str, this.mLabelTipRectF.centerX() - (this.mLabelTextBound.right + this.mLabelTextBound.left) / 2, this.mLabelTipRectF.centerY() - (this.mLabelTextBound.top + this.mLabelTextBound.bottom) / 2, this.mLabelPaint);
  }

  private void generateRect()
  {
    this.mIconRect = this.iconLayout.getRect(-this.iconLayout.width / 2, 0);
    this.mBgRect.set(0, 0, this.standardLayout.width, this.standardLayout.height - this.shadowLineLayout.height);
    this.mShadowRect.set(0, this.shadowLineLayout.getTop(), this.shadowLineLayout.width, this.standardLayout.height);
  }

  private int getSelectedIndex()
  {
    if ((this.mLastMotionY < 0.0F) || (this.mLastMotionY > this.standardLayout.height))
      return -1;
    return (int)(this.mLastMotionX * this.mActions.size() / this.standardLayout.width);
  }

  private void init()
  {
    this.mActions.add(VirtualAction.COLLECTION);
    this.mActions.add(VirtualAction.SHARE);
    this.mActions.add(VirtualAction.DOWNLOAD);
  }

  public void close(boolean paramBoolean)
  {
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(0, this);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if ((this.mActions == null) || (this.mActions.size() == 0))
      return;
    paramCanvas.setDrawFilter(this.filter);
    paramCanvas.save();
    drawBg(paramCanvas);
    drawItems(paramCanvas);
    paramCanvas.restore();
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 0)
    {
      if ((this.mNode != null) && (this.mNode.nodeName.equalsIgnoreCase("channel")))
        this.mFaved = InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(this.mNode);
      invalidate();
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.iconLayout.scaleToBounds(this.standardLayout);
    this.lineLayout.scaleToBounds(this.standardLayout);
    this.shadowLineLayout.scaleToBounds(this.standardLayout);
    generateRect();
    this.mShadowLinePaint.setStrokeWidth(this.shadowLineLayout.height);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void onNotification(String paramString)
  {
    if (this.mWid != null)
    {
      this.mCount = InfoManager.getInstance().getWsqNew(this.mWid);
      invalidate();
    }
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 3:
    case 4:
    case 2:
    case 1:
    }
    int i;
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                int j;
                do
                {
                  do
                  {
                    do
                    {
                      return true;
                      this.mInTouchMode = true;
                      this.mLastMotionX = paramMotionEvent.getX();
                      this.mLastMotionY = paramMotionEvent.getY();
                      this.mSelectedIndex = getSelectedIndex();
                      invalidate();
                      return true;
                      this.mInTouchMode = false;
                    }
                    while (this.mSelectedIndex <= -1);
                    this.mSelectedIndex = -1;
                    invalidate();
                    return true;
                  }
                  while (!this.mInTouchMode);
                  this.mLastMotionX = paramMotionEvent.getX();
                  this.mLastMotionY = paramMotionEvent.getY();
                  j = getSelectedIndex();
                }
                while ((this.mSelectedIndex <= -1) || (this.mSelectedIndex == j));
                this.mSelectedIndex = -1;
                this.mInTouchMode = false;
              }
              while (!isItemPressed());
              invalidate();
              return true;
            }
            while ((!this.mInTouchMode) || (this.mSelectedIndex <= -1));
            i = this.mSelectedIndex;
          }
          while (i >= this.mActions.size());
          if (i != 0)
            break;
          QTMSGManage.getInstance().sendStatistcsMessage("scheduleassembleclick", "fav");
        }
        while (this.mNode == null);
        if (this.mFaved)
          InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.deleteFavNode(this.mNode);
        while (true)
        {
          invalidate();
          return true;
          InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.addFavNode(this.mNode);
          QTMSGManage.getInstance().sendStatistcsMessage("addFavEvent", "channeldetail");
        }
        if (i != 1)
          break;
        QTMSGManage.getInstance().sendStatistcsMessage("scheduleassembleclick", "share");
      }
      while (this.mNode == null);
      EventDispacthManager.getInstance().dispatchAction("shareChoose", this.mNode);
      return true;
      if (i == 2)
      {
        if (this.mIsMusic)
        {
          Toast.makeText(getContext(), "该专辑无法下载", 0).show();
          return true;
        }
        QTMSGManage.getInstance().sendStatistcsMessage("scheduleassembleclick", "download");
        QTMSGManage.getInstance().sendStatistcsMessage("openbatchdownload", "scheduleassemble");
        ControllerManager.getInstance().redirectToBatchDownloadView(this.mNode, false, true);
        return true;
      }
    }
    while (i != 3);
    ControllerManager.getInstance().redirectToLocalWebView("http://qtime.qingting.fm/wsq/" + this.mWid, "蜻蜓微社区", false);
    return true;
  }

  public void update(String paramString, Object paramObject)
  {
    if ((!paramString.equalsIgnoreCase("setData")) || (paramObject == null))
      return;
    this.mIsMusic = false;
    this.mNode = ((Node)paramObject);
    if (this.mNode.nodeName.equalsIgnoreCase("channel"))
    {
      String str = InfoManager.getInstance().getWsq(((ChannelNode)this.mNode).channelId);
      if (str != null)
      {
        this.mWid = str;
        this.mCount = InfoManager.getInstance().getWsqNew(this.mWid);
        InfoManager.getInstance().registerSubscribeEventListener(this, "WSQNEW");
        InfoManager.getInstance().loadWsqNew(str);
        if (this.mActions.size() < 4)
          this.mActions.add(VirtualAction.MEMBERS);
      }
      this.mFaved = InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(this.mNode);
      if ((((ChannelNode)this.mNode).isMusicChannel()) && (!InfoManager.getInstance().allowDownloadMusic(((ChannelNode)this.mNode).channelId)))
        this.mIsMusic = true;
    }
    invalidate();
  }

  private static enum VirtualAction
  {
    static
    {
      DOWNLOAD = new VirtualAction("DOWNLOAD", 2);
      MEMBERS = new VirtualAction("MEMBERS", 3);
      VirtualAction[] arrayOfVirtualAction = new VirtualAction[4];
      arrayOfVirtualAction[0] = COLLECTION;
      arrayOfVirtualAction[1] = SHARE;
      arrayOfVirtualAction[2] = DOWNLOAD;
      arrayOfVirtualAction[3] = MEMBERS;
    }

    public int getHighlightRes(boolean paramBoolean)
    {
      switch (ChannelOperateView.1.$SwitchMap$fm$qingting$qtradio$view$virtualchannels$ChannelOperateView$VirtualAction[ordinal()])
      {
      default:
        return 0;
      case 1:
        return 2130837703;
      case 2:
        return 2130837706;
      case 3:
        if (paramBoolean)
          return 2130837701;
        return 2130837702;
      case 4:
      }
      return 2130837708;
    }

    public String getName(boolean paramBoolean)
    {
      switch (ChannelOperateView.1.$SwitchMap$fm$qingting$qtradio$view$virtualchannels$ChannelOperateView$VirtualAction[ordinal()])
      {
      default:
        return "已收藏";
      case 1:
        if (paramBoolean)
          return "已收藏";
        return "收藏";
      case 2:
        return "分享";
      case 3:
        return "下载";
      case 4:
      }
      return "成员";
    }

    public int getNormalRes(boolean paramBoolean1, boolean paramBoolean2)
    {
      switch (ChannelOperateView.1.$SwitchMap$fm$qingting$qtradio$view$virtualchannels$ChannelOperateView$VirtualAction[ordinal()])
      {
      default:
        return 0;
      case 1:
        if (paramBoolean1)
          return 2130837704;
        return 2130837699;
      case 2:
        return 2130837705;
      case 3:
        if (paramBoolean2)
          return 2130837701;
        return 2130837700;
      case 4:
      }
      return 2130837707;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualchannels.ChannelOperateView
 * JD-Core Version:    0.6.2
 */