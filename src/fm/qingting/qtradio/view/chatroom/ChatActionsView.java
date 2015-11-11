package fm.qingting.qtradio.view.chatroom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import java.util.ArrayList;
import java.util.List;

public class ChatActionsView extends QtListItemView
  implements RootNode.IInfoUpdateEventListener
{
  private final ViewLayout iconLayout = this.itemLayout.createChildLT(120, 120, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = this.standardLayout.createChildLT(180, 220, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private Rect mIconRect = new Rect();
  private boolean mInTouchMode = false;
  private float mLastMotionX = 0.0F;
  private float mLastMotionY = 0.0F;
  private List<ChatActionType> mLstTypes = new ArrayList();
  private final Paint mNamePaint = new Paint();
  private final Paint mPaint = new Paint();
  private int mSelectIndex = -1;
  private Rect mTextBound = new Rect();
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(180, 45, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public ChatActionsView(Context paramContext)
  {
    super(paramContext);
    setItemSelectedEnable();
    this.mNamePaint.setColor(SkinManager.getTextColorNormal());
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 0);
    init();
  }

  private void drawBg(Canvas paramCanvas)
  {
    int i = paramCanvas.save();
    paramCanvas.clipRect(0, getTopMargin(), this.standardLayout.width, getTopMargin() + getThisHeight());
    paramCanvas.drawColor(-1);
    paramCanvas.restoreToCount(i);
  }

  private void drawHighlight(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    int i = paramCanvas.save();
    paramCanvas.clipRect(paramInt1, paramInt2, paramInt1 + this.itemLayout.width, paramInt2 + this.itemLayout.height);
    paramCanvas.drawColor(SkinManager.getItemHighlightMaskColor());
    paramCanvas.restoreToCount(i);
  }

  private void drawItem(Canvas paramCanvas, int paramInt1, int paramInt2, ChatActionType paramChatActionType)
  {
    this.mIconRect.offset(paramInt1, paramInt2);
    paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, paramChatActionType.getIconRes()), null, this.mIconRect, this.mPaint);
    String str = paramChatActionType.getName();
    this.mNamePaint.getTextBounds(str, 0, str.length(), this.mTextBound);
    paramCanvas.drawText(str, paramInt1 + (this.itemLayout.width - this.mTextBound.width()) / 2, this.mIconRect.bottom + (this.nameLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2, this.mNamePaint);
    this.mIconRect.offset(-paramInt1, -paramInt2);
  }

  private void drawItems(Canvas paramCanvas)
  {
    int i = 0;
    if (i < this.mLstTypes.size())
    {
      int j = this.itemLayout.width * (i % 4);
      int k = getTopMargin();
      int m = this.itemLayout.height;
      if (i < 4);
      for (int n = 0; ; n = 1)
      {
        int i1 = k + n * m;
        if ((isItemPressed()) && (this.mSelectIndex == i))
          drawHighlight(paramCanvas, j, i1);
        drawItem(paramCanvas, j, i1, (ChatActionType)this.mLstTypes.get(i));
        i++;
        break;
      }
    }
  }

  private int getSelectIndex()
  {
    if (this.mLastMotionY < getTopMargin());
    int k;
    do
    {
      int i;
      int j;
      do
      {
        return -1;
        i = (int)Math.floor(this.mLastMotionX / this.itemLayout.width);
        j = (int)Math.floor((this.mLastMotionY - getTopMargin()) / this.itemLayout.height);
      }
      while ((i < 0) || (j < 0));
      k = i + j * 4;
    }
    while (k >= this.mLstTypes.size());
    return k;
  }

  private int getThisHeight()
  {
    int i = this.mLstTypes.size();
    int j = i / 4;
    if (i % 4 == 0);
    for (int k = 0; ; k = 1)
      return (k + j) * this.itemLayout.height;
  }

  private int getTopMargin()
  {
    return 0;
  }

  private void init()
  {
    this.mLstTypes.add(ChatActionType.COLLECTION);
    this.mLstTypes.add(ChatActionType.SHARE);
    this.mLstTypes.add(ChatActionType.ASKNAME);
    this.mLstTypes.add(ChatActionType.ANSWERNAME);
  }

  public void close(boolean paramBoolean)
  {
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(0, this);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(SkinManager.getInstance().getDrawFilter());
    paramCanvas.save();
    drawBg(paramCanvas);
    drawItems(paramCanvas);
    paramCanvas.restore();
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 0)
      invalidate();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.itemLayout.scaleToBounds(this.standardLayout);
    this.iconLayout.scaleToBounds(this.itemLayout);
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.mNamePaint.setTextSize(0.6F * this.nameLayout.height);
    int i = (this.itemLayout.height - this.iconLayout.height - this.nameLayout.height) / 2;
    this.mIconRect.set((this.itemLayout.width - this.iconLayout.width) / 2, i, (this.itemLayout.width + this.iconLayout.width) / 2, i + this.iconLayout.height);
    setMeasuredDimension(this.standardLayout.width, getThisHeight());
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((!this.mInTouchMode) && (paramMotionEvent.getAction() != 0));
    do
    {
      do
      {
        int i;
        do
        {
          return true;
          switch (paramMotionEvent.getAction())
          {
          default:
            return true;
          case 0:
            this.mLastMotionX = paramMotionEvent.getX();
            this.mLastMotionY = paramMotionEvent.getY();
            this.mSelectIndex = getSelectIndex();
            if (this.mSelectIndex < 0)
            {
              this.mInTouchMode = false;
              dispatchActionEvent("cancelPop", null);
              return true;
            }
            this.mInTouchMode = true;
            invalidate();
            return true;
          case 2:
            this.mLastMotionX = paramMotionEvent.getX();
            this.mLastMotionY = paramMotionEvent.getY();
            i = getSelectIndex();
          case 3:
          case 1:
          }
        }
        while ((this.mSelectIndex <= -1) || (i == this.mSelectIndex));
        this.mInTouchMode = false;
        this.mSelectIndex = -1;
      }
      while (!isItemPressed());
      invalidate();
      return true;
      this.mInTouchMode = false;
      this.mSelectIndex = -1;
    }
    while (!isItemPressed());
    invalidate();
    return true;
    if ((this.mSelectIndex > -1) && (this.mSelectIndex < this.mLstTypes.size()))
      dispatchActionEvent("chatActionType", this.mLstTypes.get(this.mSelectIndex));
    this.mInTouchMode = false;
    invalidate();
    return true;
  }

  public static enum ChatActionType
  {
    static
    {
      ASKNAME = new ChatActionType("ASKNAME", 2);
      ANSWERNAME = new ChatActionType("ANSWERNAME", 3);
      ChatActionType[] arrayOfChatActionType = new ChatActionType[4];
      arrayOfChatActionType[0] = COLLECTION;
      arrayOfChatActionType[1] = SHARE;
      arrayOfChatActionType[2] = ASKNAME;
      arrayOfChatActionType[3] = ANSWERNAME;
    }

    private boolean isCurrentNodeFaved()
    {
      Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if (localNode != null)
      {
        if (localNode.nodeName.equalsIgnoreCase("channel"))
          return InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localNode);
        if (localNode.nodeName.equalsIgnoreCase("program"))
          return InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localNode.parent);
      }
      return false;
    }

    public int getIconRes()
    {
      switch (ChatActionsView.1.$SwitchMap$fm$qingting$qtradio$view$chatroom$ChatActionsView$ChatActionType[ordinal()])
      {
      default:
      case 1:
        do
          return 2130837561;
        while (!isCurrentNodeFaved());
        return 2130837564;
      case 2:
        return 2130837565;
      case 3:
        return 2130837563;
      case 4:
      }
      return 2130837562;
    }

    public String getName()
    {
      switch (ChatActionsView.1.$SwitchMap$fm$qingting$qtradio$view$chatroom$ChatActionsView$ChatActionType[ordinal()])
      {
      default:
        return "已收藏";
      case 1:
        return "收藏节目";
      case 2:
        return "分享";
      case 3:
        return "求歌名";
      case 4:
      }
      return "报歌名";
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.ChatActionsView
 * JD-Core Version:    0.6.2
 */