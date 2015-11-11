package fm.qingting.qtradio.view.virtualchannels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.text.TextPaint;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.SpecialTopicNode;

public class RatingElement extends ViewElement
{
  public static final int ORIGIN_HEIGHT = 30;
  public static final int ORIGIN_ITEM_HEIGHT = 21;
  public static final int ORIGIN_ITEM_WIDTH = 21;
  public static final int ORIGIN_MARGIN = 8;
  public static final int ORIGIN_WIDTH = 137;
  private boolean mEnableShade = false;
  private final Paint mGradientPaint = new Paint();
  private int mItemHeight = 21;
  private int mItemMargin = 8;
  private int mItemWidth = 21;
  private Node mNode;
  private int mRating;
  private int mRealTotalHeight;
  private int mRealTotalWidth;
  private final Rect mTextBound = new Rect();
  private final TextPaint mTextPaint = new TextPaint();
  private float mTextSize = SkinManager.getInstance().getSubTextSize();
  private int mTotalHeight = 30;
  private int mTotalWidth = 137;

  public RatingElement(Context paramContext)
  {
    super(paramContext);
  }

  private void drawNoRating(Canvas paramCanvas)
  {
    this.mTextPaint.setColor(SkinManager.getTextColorSubInfo());
    this.mTextPaint.setTextSize(this.mTextSize);
    this.mTextPaint.getTextBounds("暂无评分", 0, "暂无评分".length(), this.mTextBound);
    paramCanvas.drawText("暂无评分", getLeftMargin(), getTopMargin() + (getHeight() - this.mTextBound.top - this.mTextBound.bottom) / 2 + getHeight() / 18, this.mTextPaint);
  }

  private void drawRating(Canvas paramCanvas)
  {
    int i = 32;
    double d1 = 1.0D * this.mRealTotalWidth / this.mTotalWidth;
    double d2 = 1.0D * this.mRealTotalHeight / this.mTotalHeight;
    Bitmap localBitmap1;
    int j;
    int k;
    if (this.mRating > 0)
    {
      localBitmap1 = BitmapFactory.decodeResource(getContext().getResources(), 2130837808);
      j = (int)(d1 * this.mItemWidth);
      k = (int)(d2 * this.mItemHeight);
      if (j > i)
        j = i;
      if (k <= i)
        break label355;
    }
    while (true)
    {
      Bitmap localBitmap2 = Bitmap.createScaledBitmap(localBitmap1, j, i, false);
      int m = (int)(d1 * this.mItemMargin);
      if (m > 12)
        m = 12;
      int n = (this.mRealTotalHeight - i) / 2;
      if (this.mRating > 10)
        this.mRating = 10;
      int i1 = this.mRating / 2;
      int i2 = this.mRating % 2;
      int i3 = 0;
      int i4 = 0;
      int i8;
      for (int i5 = 0; i3 < i1; i5 = i8)
      {
        drawStar(paramCanvas, i5, n, localBitmap2);
        i8 = i5 + (j + m);
        int i9 = i4 + 1;
        i3++;
        i4 = i9;
      }
      if (i2 > 0)
      {
        drawStar(paramCanvas, i5, n, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), 2130837809), j, i, false));
        i5 += j + m;
        i4++;
      }
      if (i4 < 5)
      {
        Bitmap localBitmap3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), 2130837807), j, i, false);
        int i6 = i4;
        int i7 = i5;
        while (i6 < 5)
        {
          drawStar(paramCanvas, i7, n, localBitmap3);
          i7 += j + m;
          i6++;
          continue;
          if (this.mRating != 0)
            break label346;
          drawNoRating(paramCanvas);
        }
      }
      return;
      label346: drawTag(paramCanvas, d1, d2);
      return;
      label355: i = k;
    }
  }

  private void drawStar(Canvas paramCanvas, int paramInt1, int paramInt2, Bitmap paramBitmap)
  {
    paramCanvas.drawBitmap(paramBitmap, paramInt1 + getLeftMargin(), paramInt2 + getTopMargin(), null);
  }

  private void drawTag(Canvas paramCanvas, double paramDouble1, double paramDouble2)
  {
    RecommendItemNode localRecommendItemNode;
    Object localObject1;
    if (this.mNode != null)
      if ((this.mNode instanceof RecommendItemNode))
      {
        localRecommendItemNode = (RecommendItemNode)this.mNode;
        if ((localRecommendItemNode.mNode instanceof ActivityNode));
        for (localObject1 = "活动"; ; localObject1 = "专题")
        {
          if (localObject1 != null)
          {
            Bitmap localBitmap1 = BitmapFactory.decodeResource(getContext().getResources(), 2130837558);
            int i = (int)(78.0D * paramDouble1);
            int j = (int)(30.0D * paramDouble2);
            Bitmap localBitmap2 = Bitmap.createScaledBitmap(localBitmap1, i, j, false);
            int k = (this.mRealTotalHeight - j) / 2;
            paramCanvas.drawBitmap(localBitmap2, getLeftMargin(), k + getTopMargin(), null);
            this.mTextPaint.setColor(SkinManager.getTextColorWhite());
            this.mTextPaint.setTextSize(this.mTextSize);
            this.mTextPaint.getTextBounds((String)localObject1, 0, ((String)localObject1).length(), this.mTextBound);
            paramCanvas.drawText((String)localObject1, (i - this.mTextBound.right + this.mTextBound.left) / 2 + getLeftMargin(), getTopMargin() + (getHeight() - this.mTextBound.top - this.mTextBound.bottom) / 2 + getHeight() / 18, this.mTextPaint);
          }
          return;
          if (!(localRecommendItemNode.mNode instanceof SpecialTopicNode))
            break;
        }
        localObject1 = "直播";
        if ((localRecommendItemNode.mNode != null) && ((localRecommendItemNode.mNode instanceof ChannelNode)))
          if (!((ChannelNode)localRecommendItemNode.mNode).nodeName.equals("channel_ondemand"))
            break label397;
      }
    label397: for (Object localObject2 = null; ; localObject2 = localObject1)
    {
      localObject1 = localObject2;
      break;
      if ((localRecommendItemNode.mNode == null) || (!(localRecommendItemNode.mNode instanceof ProgramNode)) || (!((ProgramNode)localRecommendItemNode.mNode).nodeName.equals("program_ondemand")))
        break;
      localObject1 = null;
      break;
      if ((this.mNode instanceof ChannelNode))
      {
        if (this.mNode.nodeName.equals("channel_ondemand"))
        {
          localObject1 = null;
          break;
        }
        localObject1 = "直播";
        break;
      }
      localObject1 = null;
      break;
    }
  }

  public void enableShade(boolean paramBoolean)
  {
    this.mEnableShade = paramBoolean;
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    if (this.mEnableShade)
      paramCanvas.drawRect(getLeftMargin(), getTopMargin(), getRightMargin(), getBottomMargin(), this.mGradientPaint);
    drawRating(paramCanvas);
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.mEnableShade) && (this.mGradientPaint.getShader() == null))
    {
      LinearGradient localLinearGradient = new LinearGradient(getLeftMargin(), getTopMargin(), getLeftMargin(), getBottomMargin(), 0, -872415232, Shader.TileMode.CLAMP);
      this.mGradientPaint.setShader(localLinearGradient);
    }
    this.mRealTotalWidth = (paramInt3 - paramInt1);
    this.mRealTotalHeight = (paramInt4 - paramInt2);
  }

  public void setData(Node paramNode)
  {
    this.mNode = paramNode;
  }

  public void setRating(int paramInt)
  {
    this.mRating = paramInt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualchannels.RatingElement
 * JD-Core Version:    0.6.2
 */