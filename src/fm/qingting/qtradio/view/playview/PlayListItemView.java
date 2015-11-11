package fm.qingting.qtradio.view.playview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Toast;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.fmdriver.FMManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.ReserveInfoNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.PlayMode;
import java.util.Locale;

public class PlayListItemView extends QtListItemView
{
  private final ViewLayout arrowLayout = this.itemLayout.createChildLT(36, 36, 650, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout channelLayout = this.itemLayout.createChildLT(720, 45, 30, 20, ViewLayout.SCALE_FLAG_SLTCW);
  private DrawFilter filter;
  private int hash = -2;
  private Paint iconPaint = new Paint();
  private final ViewLayout infoLayout = this.itemLayout.createChildLT(720, 45, 30, 10, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 136, 720, 800, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(670, 1, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private Paint livePaint = new Paint();
  private final ViewLayout livingLayout = this.itemLayout.createChildLT(20, 26, 10, 29, ViewLayout.SCALE_FLAG_SLTCW);
  private Rect mArrowRect = new Rect();
  private Rect mLivingRect = new Rect();
  private Node mNode;
  private final TextPaint mNormalPaint = new TextPaint();
  private Rect textBound = new Rect();

  public PlayListItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.hash = paramInt;
    this.livePaint.setColor(SkinManager.getLiveColor());
    setOnClickListener(this);
    setItemSelectedEnable();
    this.filter = SkinManager.getInstance().getDrawFilter();
    this.mNormalPaint.setColor(-1);
  }

  private void drawArrow(Canvas paramCanvas)
  {
    paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getResources(), this.hash, 2130837694), null, this.mArrowRect, this.iconPaint);
  }

  private void drawBg(Canvas paramCanvas)
  {
    if (isItemPressed())
      paramCanvas.drawColor(SkinManager.getItemHighlightMaskColor());
  }

  private void drawLine(Canvas paramCanvas)
  {
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, this.lineLayout.leftMargin, this.lineLayout.getRight(), this.itemLayout.height - this.lineLayout.height, this.lineLayout.height);
  }

  private void drawLivingLabel(Canvas paramCanvas)
  {
    paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, 2130837920), null, this.mLivingRect, this.iconPaint);
  }

  private void drawSubInfo(Canvas paramCanvas)
  {
    int j;
    Object localObject2;
    Object localObject1;
    int i;
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
    {
      j = ((ProgramNode)this.mNode).getCurrPlayStatus();
      if (j == 1)
      {
        this.livePaint.setColor(SkinManager.getLiveColor());
        localObject2 = "直播";
        String str7 = ((ProgramNode)this.mNode).startTime;
        String str8 = str7 + "-";
        localObject1 = str8 + ((ProgramNode)this.mNode).endTime;
        i = 0;
      }
    }
    while (true)
    {
      TextPaint localTextPaint = SkinManager.getInstance().getSubTextPaint();
      if ((localObject2 != null) && (localObject1 != null))
      {
        this.livePaint.getTextBounds((String)localObject2, 0, ((String)localObject2).length(), this.textBound);
        float f5 = this.infoLayout.getLeft();
        float f6 = this.channelLayout.topMargin + this.channelLayout.height + this.infoLayout.topMargin + (this.infoLayout.height - this.textBound.top - this.textBound.bottom) / 2;
        paramCanvas.drawText((String)localObject2, f5, f6, this.livePaint);
        float f7 = f5 + this.textBound.width() + this.infoLayout.getLeft() / 2;
        localTextPaint.getTextBounds((String)localObject1, 0, ((String)localObject1).length(), this.textBound);
        paramCanvas.drawText((String)localObject1, f7, f6, localTextPaint);
      }
      float f3;
      do
      {
        return;
        if (j == 2)
        {
          if (InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.isExisted(this.mNode));
          for (String str3 = "己预约"; ; str3 = "预约")
          {
            this.livePaint.setColor(SkinManager.getTextColorHighlight());
            String str4 = ((ProgramNode)this.mNode).startTime;
            String str5 = str4 + "-";
            String str6 = str5 + ((ProgramNode)this.mNode).endTime;
            localObject2 = str3;
            localObject1 = str6;
            i = 0;
            break;
          }
        }
        if (((ProgramNode)this.mNode).channelType == 0)
        {
          String str1 = ((ProgramNode)this.mNode).startTime;
          String str2 = str1 + "-";
          localObject1 = str2 + ((ProgramNode)this.mNode).endTime;
          i = 1;
          localObject2 = null;
          break;
        }
        localObject1 = getDurationTime(((ProgramNode)this.mNode).getDuration());
        i = 0;
        localObject2 = null;
        break;
        if (i == 0)
          break label716;
        localTextPaint.getTextBounds("回听:", 0, "回听:".length(), this.textBound);
        f3 = this.infoLayout.getLeft();
        paramCanvas.drawText("回听:", f3, this.channelLayout.topMargin + this.channelLayout.height + this.infoLayout.topMargin + (this.infoLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
      }
      while ((localObject1 == null) || (((String)localObject1).equalsIgnoreCase("")));
      float f4 = f3 + this.textBound.width() + this.infoLayout.getLeft() / 2;
      localTextPaint.getTextBounds((String)localObject1, 0, ((String)localObject1).length(), this.textBound);
      paramCanvas.drawText((String)localObject1, f4, this.channelLayout.topMargin + this.channelLayout.height + this.infoLayout.topMargin + (this.infoLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
      return;
      label716: localTextPaint.getTextBounds("时长:", 0, "时长:".length(), this.textBound);
      float f1 = this.infoLayout.getLeft();
      paramCanvas.drawText("时长:", f1, this.channelLayout.topMargin + this.channelLayout.height + this.infoLayout.topMargin + (this.infoLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
      float f2 = f1 + this.textBound.width() + this.infoLayout.getLeft() / 2;
      localTextPaint.getTextBounds((String)localObject1, 0, ((String)localObject1).length(), this.textBound);
      paramCanvas.drawText((String)localObject1, f2, this.channelLayout.topMargin + this.channelLayout.height + this.infoLayout.topMargin + (this.infoLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
      return;
      i = 0;
      localObject1 = null;
      localObject2 = null;
    }
  }

  private void drawTitle(Canvas paramCanvas)
  {
    boolean bool = isPlaying();
    if (bool)
      drawLivingLabel(paramCanvas);
    String str1 = getTitle();
    TextPaint localTextPaint = this.mNormalPaint;
    int i = this.mArrowRect.left;
    int j;
    String str2;
    float f1;
    label118: float f2;
    if (bool)
    {
      j = this.mLivingRect.right + this.channelLayout.leftMargin;
      str2 = TextUtils.ellipsize(str1, localTextPaint, i - j, TextUtils.TruncateAt.END).toString();
      this.mNormalPaint.getTextBounds(str2, 0, str2.length(), this.textBound);
      if (!bool)
        break label197;
      f1 = this.mLivingRect.right + this.livingLayout.leftMargin;
      f2 = this.channelLayout.topMargin + (this.channelLayout.height - this.textBound.top - this.textBound.bottom) / 2;
      if ((!bool) && (!isItemPressed()))
        break label210;
      paramCanvas.drawText(str2, f1, f2, this.mNormalPaint);
    }
    while (true)
    {
      drawSubInfo(paramCanvas);
      return;
      j = this.channelLayout.leftMargin;
      break;
      label197: f1 = this.channelLayout.leftMargin;
      break label118;
      label210: paramCanvas.drawText(str2, f1, f2, this.mNormalPaint);
    }
  }

  private void generateRect()
  {
    int i = this.arrowLayout.leftMargin;
    int j = (this.itemLayout.height - this.arrowLayout.height) / 2;
    this.mArrowRect.set(i, j, i + this.arrowLayout.width, j + this.arrowLayout.height);
    this.mLivingRect.set(this.channelLayout.leftMargin, this.livingLayout.topMargin, this.channelLayout.leftMargin + this.livingLayout.width, this.livingLayout.getBottom());
  }

  private String getDurationTime(int paramInt)
  {
    int i = paramInt / 3600;
    int j = paramInt / 60 % 60;
    int k = paramInt % 60;
    if (i == 0)
    {
      if (k == 0)
      {
        Locale localLocale4 = Locale.CHINA;
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Integer.valueOf(j);
        return String.format(localLocale4, "%d分", arrayOfObject4);
      }
      if (j == 0)
      {
        Locale localLocale3 = Locale.CHINA;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(k);
        return String.format(localLocale3, "%d秒", arrayOfObject3);
      }
      Locale localLocale2 = Locale.CHINA;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(j);
      arrayOfObject2[1] = Integer.valueOf(k);
      return String.format(localLocale2, "%d分%d秒", arrayOfObject2);
    }
    Locale localLocale1 = Locale.CHINA;
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(i);
    arrayOfObject1[1] = Integer.valueOf(j);
    return String.format(localLocale1, "%d小时%d分", arrayOfObject1);
  }

  private String getTitle()
  {
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
      return ((ProgramNode)this.mNode).title;
    return "";
  }

  private boolean isPlaying()
  {
    if (this.mNode == null)
      return false;
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if (localNode == null)
      return false;
    if (!this.mNode.nodeName.equalsIgnoreCase(localNode.nodeName))
      return false;
    return (this.mNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)this.mNode).id == ((ProgramNode)localNode).id);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mNode == null)
      return;
    paramCanvas.save();
    paramCanvas.setDrawFilter(this.filter);
    drawBg(paramCanvas);
    drawLine(paramCanvas);
    drawTitle(paramCanvas);
    drawArrow(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.itemLayout.scaleToBounds(i, j);
    this.channelLayout.scaleToBounds(this.itemLayout);
    this.infoLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.arrowLayout.scaleToBounds(this.itemLayout);
    this.livingLayout.scaleToBounds(this.itemLayout);
    this.livePaint.setTextSize(0.5F * this.channelLayout.height);
    this.mNormalPaint.setTextSize(SkinManager.getInstance().getNormalTextSize());
    generateRect();
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  protected void onQtItemClick(View paramView)
  {
    if (this.mNode == null)
      return;
    if ((this.mNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)this.mNode).getCurrPlayStatus() == 2))
    {
      if (InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.isExisted(this.mNode))
        InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.cancelReserve(((ProgramNode)this.mNode).id);
      while (true)
      {
        invalidate();
        return;
        InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.addReserveNode((ProgramNode)this.mNode);
      }
    }
    int i;
    if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.FMPLAY)
    {
      Toast.makeText(getContext(), "亲，无法使用系统收音机回听节目，只能播放当前直播节目", 1).show();
      if (this.mNode.nodeName.equalsIgnoreCase("program"))
      {
        Node localNode = this.mNode.parent;
        i = 0;
        if (localNode != null)
        {
          boolean bool2 = this.mNode.parent.nodeName.equalsIgnoreCase("radiochannel");
          i = 0;
          if (bool2)
            i = Integer.valueOf(((RadioChannelNode)this.mNode.parent).freq).intValue();
        }
        if (i != 0)
          FMManager.getInstance().tune(i);
        PlayerAgent.getInstance().dispatchPlayStateInFM(4096);
        InfoManager.getInstance().root().tuneFM(true);
      }
    }
    while (true)
    {
      invalidate();
      return;
      boolean bool1 = this.mNode.nodeName.equalsIgnoreCase("radiochannel");
      i = 0;
      if (!bool1)
        break;
      i = Integer.valueOf(((RadioChannelNode)this.mNode).freq).intValue();
      break;
      dispatchActionEvent("refresh", this.mNode);
      PlayerAgent.getInstance().play(this.mNode);
    }
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
    {
      this.mNode = ((Node)paramObject);
      invalidate();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.PlayListItemView
 * JD-Core Version:    0.6.2
 */