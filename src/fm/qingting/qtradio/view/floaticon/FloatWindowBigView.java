package fm.qingting.qtradio.view.floaticon;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.QTRadioActivity;
import fm.qingting.qtradio.QTRadioService;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.BillboardItemNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.DataLoadWrapper;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.playlist.PlayListManager;
import java.util.ArrayList;
import java.util.List;

public class FloatWindowBigView extends QtListItemView
{
  private final int HEAD = 200;
  private final int SETTING = 100;
  private final String TITLE = "蜻蜓FM随时听";
  private final ViewLayout bgLayout = this.standardLayout.createChildLT(660, 400, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final DrawFilter filter = new PaintFlagsDrawFilter(0, 67);
  private final ViewLayout headLayout = this.standardLayout.createChildLT(660, 98, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout iconLayout = this.itemLayout.createChildLT(110, 110, 0, 20, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout itemLayout = this.standardLayout.createChildLT(220, 200, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private IResultRecvHandler loadHandler = new IResultRecvHandler()
  {
    public void onRecvResult(Result paramAnonymousResult, Object paramAnonymousObject1, IResultToken paramAnonymousIResultToken, Object paramAnonymousObject2)
    {
      Node localNode;
      if ((paramAnonymousResult.getSuccess()) && (paramAnonymousIResultToken.getType().equalsIgnoreCase("GET_LIVE_CHANNEL_INFO")))
      {
        localNode = (Node)paramAnonymousResult.getData();
        if (localNode != null)
          break label36;
      }
      label36: 
      while (!localNode.nodeName.equalsIgnoreCase("channel"))
        return;
      if (((ChannelNode)localNode).channelId == FloatWindowBigView.this.newsChannelId)
        FloatWindowBigView.access$102(FloatWindowBigView.this, (ChannelNode)localNode);
      while (true)
      {
        FloatWindowBigView.this.play(localNode);
        return;
        if (((ChannelNode)localNode).channelId == FloatWindowBigView.this.musicChannelId)
          FloatWindowBigView.access$302(FloatWindowBigView.this, (ChannelNode)localNode);
      }
    }
  };
  private Rect mHeadRect = new Rect();
  private Rect mIconRect = new Rect();
  private boolean mInTouchMode = false;
  private float mLastMotionX = 0.0F;
  private float mLastMotionY = 0.0F;
  private List<CATEGORYTYPE> mLstCategoryNodes;
  private ChannelNode mMusicChannelNode;
  private final Paint mNameHighlightPaint = new Paint();
  private final Paint mNamePaint = new Paint();
  private ChannelNode mNewsChannelNode;
  private final Paint mPaint = new Paint();
  private int mSelectedIndex = -1;
  private Rect mSettingRect = new Rect();
  private final Paint mTitlePaint = new Paint();
  private ChannelNode mWhaterverChannelNode;
  private int musicChannelId = 4935;
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(220, 50, 0, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private int newsChannelId = 386;
  private final ViewLayout settingLayout = this.headLayout.createChildLT(52, 52, 30, 23, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private Rect textBound = new Rect();
  private final ViewLayout titleLayout = this.standardLayout.createChildLT(400, 45, 60, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public FloatWindowBigView(Context paramContext)
  {
    super(paramContext);
    this.mNamePaint.setColor(-14210768);
    this.mNameHighlightPaint.setColor(-707280);
    this.mTitlePaint.setColor(-1);
    setItemSelectedEnable();
    this.mLstCategoryNodes = new ArrayList();
    this.mLstCategoryNodes.add(CATEGORYTYPE.NEWS);
    this.mLstCategoryNodes.add(CATEGORYTYPE.MUSIC);
    this.mLstCategoryNodes.add(CATEGORYTYPE.WHATEVER);
    this.mLstCategoryNodes.add(CATEGORYTYPE.COLLECTION);
    this.mLstCategoryNodes.add(CATEGORYTYPE.DOWNLOAD);
    this.mLstCategoryNodes.add(CATEGORYTYPE.HISTORY);
  }

  private void cancel()
  {
    FloatViewManager.INSTANCE.removeBigWindow(getContext());
    FloatViewManager.INSTANCE.createSmallWindow(getContext());
  }

  private void drawBg(Canvas paramCanvas)
  {
    int i = paramCanvas.save();
    paramCanvas.clipRect(0, 0, this.standardLayout.width, this.standardLayout.height);
    paramCanvas.drawColor(-1442840576);
    paramCanvas.restoreToCount(i);
  }

  private void drawButton(Canvas paramCanvas, CATEGORYTYPE paramCATEGORYTYPE, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    String str = paramCATEGORYTYPE.getName();
    this.mIconRect.offset(paramInt1, paramInt2);
    paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, paramCATEGORYTYPE.getIconRes(paramBoolean)), null, this.mIconRect, this.mPaint);
    this.mNamePaint.getTextBounds(str, 0, str.length(), this.textBound);
    float f1 = paramInt1 + (this.itemLayout.width - this.textBound.left - this.textBound.right) / 2;
    float f2 = this.mIconRect.bottom + this.nameLayout.topMargin + (this.nameLayout.height - this.textBound.top - this.textBound.bottom) / 2;
    if (paramBoolean);
    for (Paint localPaint = this.mNameHighlightPaint; ; localPaint = this.mNamePaint)
    {
      paramCanvas.drawText(str, f1, f2, localPaint);
      this.mIconRect.offset(-paramInt1, -paramInt2);
      return;
    }
  }

  private void drawButtons(Canvas paramCanvas)
  {
    int i = paramCanvas.save();
    paramCanvas.clipRect(this.bgLayout.leftMargin, this.mHeadRect.bottom, this.bgLayout.getRight(), this.mHeadRect.bottom + this.bgLayout.height);
    paramCanvas.drawColor(-1);
    paramCanvas.restoreToCount(i);
    int j = 0;
    if (j < getItemSize())
    {
      int k = this.bgLayout.leftMargin + this.itemLayout.width * (j % 3);
      int m = this.mHeadRect.bottom + this.itemLayout.height * (j / 3);
      CATEGORYTYPE localCATEGORYTYPE = getCategoryType(j);
      if ((isItemPressed()) && (this.mSelectedIndex == j));
      for (boolean bool = true; ; bool = false)
      {
        drawButton(paramCanvas, localCATEGORYTYPE, k, m, bool);
        j++;
        break;
      }
    }
  }

  private void drawHead(Canvas paramCanvas)
  {
    paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, 2130837741), null, this.mHeadRect, this.mPaint);
    this.mNamePaint.getTextBounds("蜻蜓FM随时听", 0, "蜻蜓FM随时听".length(), this.textBound);
    paramCanvas.drawText("蜻蜓FM随时听", this.headLayout.leftMargin + this.titleLayout.leftMargin, this.mHeadRect.centerY() - (this.textBound.top + this.textBound.bottom) / 2, this.mTitlePaint);
    BitmapResourceCache localBitmapResourceCache = BitmapResourceCache.getInstance();
    Resources localResources = getResources();
    if ((isItemPressed()) && (this.mSelectedIndex == 100));
    for (int i = 2130837740; ; i = 2130837739)
    {
      paramCanvas.drawBitmap(localBitmapResourceCache.getResourceCache(localResources, this, i), null, this.mSettingRect, this.mPaint);
      return;
    }
  }

  private void generateRect()
  {
    this.mIconRect.set((this.itemLayout.width - this.iconLayout.width) / 2, this.iconLayout.topMargin, (this.itemLayout.width + this.iconLayout.width) / 2, this.iconLayout.topMargin + this.iconLayout.height);
    this.mHeadRect.set(this.headLayout.leftMargin, (this.standardLayout.height - this.headLayout.height - this.bgLayout.height) / 2 - this.headLayout.height, this.headLayout.getRight(), (this.standardLayout.height - this.headLayout.height - this.bgLayout.height) / 2);
    this.mSettingRect.set(this.mHeadRect.right - this.settingLayout.getRight(), this.mHeadRect.top + this.settingLayout.topMargin, this.mHeadRect.right - this.settingLayout.leftMargin, this.mHeadRect.top + this.settingLayout.getBottom());
  }

  private CATEGORYTYPE getCategoryType(int paramInt)
  {
    if (this.mLstCategoryNodes == null)
      return null;
    return (CATEGORYTYPE)this.mLstCategoryNodes.get(paramInt);
  }

  private int getItemSize()
  {
    if (this.mLstCategoryNodes == null)
      return 0;
    return this.mLstCategoryNodes.size();
  }

  private int getSelectedIndex()
  {
    int i = this.headLayout.leftMargin;
    int j = this.standardLayout.width - i;
    if ((this.mLastMotionX < i) || (this.mLastMotionX > j));
    int k;
    do
    {
      do
        return -1;
      while ((this.mLastMotionY < this.mHeadRect.top) || (this.mLastMotionY > this.mHeadRect.bottom + this.bgLayout.height));
      if (this.mSettingRect.contains((int)this.mLastMotionX, (int)this.mLastMotionY))
        return 100;
      if ((this.mLastMotionY > this.mHeadRect.top) && (this.mLastMotionY < this.mHeadRect.bottom))
        return 200;
      k = (int)((this.mLastMotionX - i) / this.itemLayout.width);
    }
    while (k > 2);
    return k + 3 * (int)((this.mLastMotionY - this.mHeadRect.bottom) / this.itemLayout.height);
  }

  private void handleEvent(CATEGORYTYPE paramCATEGORYTYPE)
  {
    Intent localIntent;
    Bundle localBundle;
    if ((paramCATEGORYTYPE == CATEGORYTYPE.COLLECTION) || (paramCATEGORYTYPE == CATEGORYTYPE.DOWNLOAD) || (paramCATEGORYTYPE == CATEGORYTYPE.HISTORY) || (paramCATEGORYTYPE == CATEGORYTYPE.NEWS) || (paramCATEGORYTYPE == CATEGORYTYPE.MUSIC) || (paramCATEGORYTYPE == CATEGORYTYPE.WHATEVER))
    {
      localIntent = new Intent(getContext(), QTRadioActivity.class);
      localIntent.addFlags(268435456);
      localBundle = new Bundle();
      switch (2.$SwitchMap$fm$qingting$qtradio$view$floaticon$FloatWindowBigView$CATEGORYTYPE[paramCATEGORYTYPE.ordinal()])
      {
      default:
      case 4:
      case 5:
      case 6:
      case 1:
      case 2:
      case 3:
      }
    }
    while (true)
    {
      localIntent.putExtra("fm.qingting.qtradio.float_jump", localBundle);
      FloatViewManager.INSTANCE.removeBigWindow(getContext());
      getContext().startActivity(localIntent);
      return;
      localBundle.putInt("floatjumptype", 16);
      continue;
      localBundle.putInt("floatjumptype", 32);
      continue;
      localBundle.putInt("floatjumptype", 48);
      continue;
      localBundle.putInt("floatjumptype", 80);
      continue;
      localBundle.putInt("floatjumptype", 96);
      continue;
      localBundle.putInt("floatjumptype", 112);
    }
  }

  private void listenMusic()
  {
    if (this.mMusicChannelNode == null)
    {
      DataLoadWrapper.loadLiveChannelNode(this.musicChannelId, this.loadHandler);
      return;
    }
    play(this.mMusicChannelNode);
  }

  private void listenNews()
  {
    if (this.mNewsChannelNode == null)
    {
      DataLoadWrapper.loadLiveChannelNode(this.newsChannelId, this.loadHandler);
      return;
    }
    play(this.mNewsChannelNode);
  }

  private void listenWhatever()
  {
    List localList = FloatViewManager.INSTANCE.getBillboardChannels();
    if (localList == null)
      listenNews();
    Node localNode2;
    do
    {
      Node localNode1;
      do
      {
        int i;
        do
        {
          return;
          i = localList.size();
        }
        while (i <= 0);
        localNode1 = (Node)localList.get((int)(10.0D * Math.random()) % i);
      }
      while ((localNode1 == null) || (!localNode1.nodeName.equalsIgnoreCase("billboarditem")));
      localNode2 = ((BillboardItemNode)localNode1).mNode;
    }
    while ((localNode2 == null) || (!localNode2.nodeName.equalsIgnoreCase("channel")));
    DataLoadWrapper.loadLiveChannelNode(((ChannelNode)localNode2).channelId, this.loadHandler);
  }

  private void play(Node paramNode)
  {
    if (paramNode == null);
    while (!paramNode.nodeName.equalsIgnoreCase("channel"))
      return;
    PlayListManager.getInstance().setPlayList(paramNode, ((ChannelNode)paramNode).channelId, 5, true);
    sendPlayIntent((ChannelNode)paramNode);
  }

  private void sendLog(int paramInt)
  {
    QTLogger localQTLogger = QTLogger.getInstance();
    if (FloatViewManager.INSTANCE.isV2());
    for (String str1 = "2"; ; str1 = "1")
    {
      String str2 = localQTLogger.buildCommonLog(str1, null, null);
      String str3 = str2 + "\"";
      String str4 = str3 + String.valueOf(paramInt);
      String str5 = str4 + "\"";
      String str6 = str5 + ",";
      LogModule.getInstance().send("FloatUsage", str6);
      return;
    }
  }

  private void sendPlayIntent(ChannelNode paramChannelNode)
  {
    if (paramChannelNode == null)
      return;
    Intent localIntent1 = new Intent("fm.qingting.qtradio.INSTANT_PLAY");
    localIntent1.putExtra("setplaychannelnode", paramChannelNode.channelId);
    localIntent1.putExtra("categoryid", paramChannelNode.categoryId);
    localIntent1.putExtra("channelid", paramChannelNode.channelId);
    localIntent1.putExtra("channelname", paramChannelNode.title);
    localIntent1.putExtra("notify_type", "push_live_channel");
    Intent localIntent2 = new Intent(getContext(), QTRadioService.class);
    localIntent2.setAction("fm.qingting.qtradio.START_SERVICE");
    localIntent2.putExtras(localIntent1);
    getContext().startService(localIntent2);
  }

  private void setting()
  {
    Intent localIntent = new Intent(getContext(), QTRadioActivity.class);
    localIntent.addFlags(268435456);
    Bundle localBundle = new Bundle();
    localBundle.putInt("floatjumptype", 64);
    localIntent.putExtra("fm.qingting.qtradio.float_jump", localBundle);
    FloatViewManager.INSTANCE.removeBigWindow(getContext());
    getContext().startActivity(localIntent);
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    if (((i == 4) || (i == 82)) && (paramKeyEvent.getAction() == 0))
    {
      FloatViewManager.INSTANCE.removeBigWindow(getContext());
      FloatViewManager.INSTANCE.createSmallWindow(getContext());
      return true;
    }
    return false;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(this.filter);
    paramCanvas.save();
    drawBg(paramCanvas);
    drawHead(paramCanvas);
    drawButtons(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.headLayout.scaleToBounds(this.standardLayout);
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.bgLayout.scaleToBounds(this.standardLayout);
    this.itemLayout.scaleToBounds(this.standardLayout);
    this.iconLayout.scaleToBounds(this.itemLayout);
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.settingLayout.scaleToBounds(this.headLayout);
    this.mNamePaint.setTextSize(0.6F * this.nameLayout.height);
    this.mNameHighlightPaint.setTextSize(0.6F * this.nameLayout.height);
    this.mTitlePaint.setTextSize(0.7F * this.titleLayout.height);
    generateRect();
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((!this.mInTouchMode) && (paramMotionEvent.getAction() != 0));
    do
    {
      do
      {
        int j;
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
            this.mInTouchMode = true;
            this.mSelectedIndex = getSelectedIndex();
            if (this.mSelectedIndex == 200)
            {
              this.mInTouchMode = false;
              return true;
            }
            if (this.mSelectedIndex > -1)
            {
              invalidate();
              return true;
            }
            this.mInTouchMode = false;
            cancel();
            return true;
          case 2:
            this.mLastMotionX = paramMotionEvent.getX();
            this.mLastMotionY = paramMotionEvent.getY();
            j = getSelectedIndex();
          case 3:
          case 1:
          }
        }
        while ((this.mSelectedIndex <= -1) || (j == this.mSelectedIndex));
        this.mInTouchMode = false;
        this.mSelectedIndex = -1;
        invalidate();
        return true;
        this.mInTouchMode = false;
        this.mSelectedIndex = -1;
      }
      while (!isItemPressed());
      invalidate();
      return true;
      if (this.mSelectedIndex == 100)
      {
        setting();
        sendLog(7);
        return true;
      }
    }
    while ((this.mSelectedIndex <= -1) || (this.mSelectedIndex >= getItemSize()));
    int i = this.mSelectedIndex;
    handleEvent((CATEGORYTYPE)this.mLstCategoryNodes.get(i));
    sendLog(i + 1);
    return true;
  }

  private static enum CATEGORYTYPE
  {
    static
    {
      MUSIC = new CATEGORYTYPE("MUSIC", 1);
      WHATEVER = new CATEGORYTYPE("WHATEVER", 2);
      COLLECTION = new CATEGORYTYPE("COLLECTION", 3);
      DOWNLOAD = new CATEGORYTYPE("DOWNLOAD", 4);
      HISTORY = new CATEGORYTYPE("HISTORY", 5);
      CATEGORYTYPE[] arrayOfCATEGORYTYPE = new CATEGORYTYPE[6];
      arrayOfCATEGORYTYPE[0] = NEWS;
      arrayOfCATEGORYTYPE[1] = MUSIC;
      arrayOfCATEGORYTYPE[2] = WHATEVER;
      arrayOfCATEGORYTYPE[3] = COLLECTION;
      arrayOfCATEGORYTYPE[4] = DOWNLOAD;
      arrayOfCATEGORYTYPE[5] = HISTORY;
    }

    public int getIconRes(boolean paramBoolean)
    {
      if (paramBoolean);
      for (int i = 2130837738; ; i = 2130837737)
        switch (FloatWindowBigView.2.$SwitchMap$fm$qingting$qtradio$view$floaticon$FloatWindowBigView$CATEGORYTYPE[ordinal()])
        {
        case 1:
        default:
          return i;
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        }
      if (paramBoolean)
        return 2130837736;
      return 2130837735;
      if (paramBoolean)
        return 2130837743;
      return 2130837742;
      if (paramBoolean)
        return 2130837730;
      return 2130837729;
      if (paramBoolean)
        return 2130837728;
      return 2130837727;
      if (paramBoolean)
        return 2130837734;
      return 2130837733;
    }

    public String getName()
    {
      switch (FloatWindowBigView.2.$SwitchMap$fm$qingting$qtradio$view$floaticon$FloatWindowBigView$CATEGORYTYPE[ordinal()])
      {
      default:
        return null;
      case 1:
        return "听新闻";
      case 2:
        return "听音乐";
      case 3:
        return "随便听";
      case 4:
        return "我的收藏";
      case 5:
        return "我的下载";
      case 6:
      }
      return "最近收听";
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.floaticon.FloatWindowBigView
 * JD-Core Version:    0.6.2
 */