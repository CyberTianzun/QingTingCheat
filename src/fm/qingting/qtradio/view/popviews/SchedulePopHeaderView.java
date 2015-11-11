package fm.qingting.qtradio.view.popviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import java.util.Calendar;

public class SchedulePopHeaderView extends ViewImpl
{
  private DrawFilter filter;
  private final ViewLayout labelLayout = this.standardLayout.createChildLT(120, 4, 30, 80, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.standardLayout.createChildLT(720, 1, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private boolean mInTouchMode = false;
  private float mLastMotionX = 0.0F;
  private float mLastMotionY = 0.0F;
  private boolean mNeedDayInfo = false;
  private Schedule mSchedule;
  private int mSelectedIndex = -1;
  private Paint selectTextPaint = new Paint();
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 104, 720, 104, 0, 0, ViewLayout.FILL);
  private Rect textBound = new Rect();
  private Paint textPaint = new Paint();
  private final ViewLayout titleLayout = this.standardLayout.createChildLT(720, 104, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private Paint unselectTextPaint = new Paint();

  public SchedulePopHeaderView(Context paramContext)
  {
    super(paramContext);
    this.textPaint.setColor(SkinManager.getTextColorNormal());
    this.textPaint.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.unselectTextPaint.setColor(SkinManager.getTextColorSubInfo());
    this.unselectTextPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.selectTextPaint.setColor(SkinManager.getTextColorHighlight());
    this.selectTextPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.filter = SkinManager.getInstance().getDrawFilter();
  }

  private void drawBg(Canvas paramCanvas)
  {
    paramCanvas.drawColor(SkinManager.getTagBackgroundColor());
  }

  private void drawDayInfo(Canvas paramCanvas, String paramString, float paramFloat, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      int i = paramCanvas.save();
      paramCanvas.clipRect(paramFloat, this.labelLayout.topMargin, paramFloat + this.labelLayout.width, this.labelLayout.getBottom());
      paramCanvas.drawColor(SkinManager.getTextColorHighlight());
      paramCanvas.restoreToCount(i);
    }
    this.selectTextPaint.getTextBounds(paramString, 0, paramString.length(), this.textBound);
    float f1 = paramFloat + (this.labelLayout.width - this.textBound.width()) / 2;
    float f2 = (this.standardLayout.height - this.textBound.top - this.textBound.bottom) / 2;
    if (paramBoolean);
    for (Paint localPaint = this.selectTextPaint; ; localPaint = this.unselectTextPaint)
    {
      paramCanvas.drawText(paramString, f1, f2, localPaint);
      return;
    }
  }

  private void drawLine(Canvas paramCanvas)
  {
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, 0, this.standardLayout.width, this.titleLayout.height - this.lineLayout.height, this.lineLayout.height);
  }

  private void drawTitle(Canvas paramCanvas)
  {
    boolean bool1 = true;
    this.textPaint.getTextBounds("节目单", 0, "节目单".length(), this.textBound);
    paramCanvas.drawText("节目单", this.titleLayout.getLeft(), (this.titleLayout.height - this.textBound.top - this.textBound.bottom) / 2, this.textPaint);
    boolean bool2;
    boolean bool3;
    label147: float f3;
    if (this.mNeedDayInfo)
    {
      float f1 = this.standardLayout.width - this.labelLayout.leftMargin - 3 * this.labelLayout.width;
      if (this.mSchedule != Schedule.YESTERDAY)
        break label192;
      bool2 = bool1;
      drawDayInfo(paramCanvas, "昨天", f1, bool2);
      float f2 = f1 + this.labelLayout.width;
      if (this.mSchedule != Schedule.TODAY)
        break label198;
      bool3 = bool1;
      drawDayInfo(paramCanvas, "今天", f2, bool3);
      f3 = f2 + this.labelLayout.width;
      if (this.mSchedule != Schedule.TOMORROW)
        break label204;
    }
    while (true)
    {
      drawDayInfo(paramCanvas, "明天", f3, bool1);
      return;
      label192: bool2 = false;
      break;
      label198: bool3 = false;
      break label147;
      label204: bool1 = false;
    }
  }

  private Schedule getCurrPlayedSchedule()
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if (localNode == null)
      return Schedule.UNKNOWN;
    if (localNode.nodeName.equalsIgnoreCase("program"))
    {
      int i = ((ProgramNode)localNode).dayOfWeek;
      int j = Calendar.getInstance().get(7);
      if (i == j)
        return Schedule.TODAY;
      if (i == j - 1)
        return Schedule.YESTERDAY;
      if (i == j + 1)
        return Schedule.TOMORROW;
    }
    return Schedule.UNKNOWN;
  }

  private int getSelectedIndex(float paramFloat1, float paramFloat2)
  {
    int i = this.standardLayout.width - this.labelLayout.leftMargin;
    int j = this.labelLayout.width;
    if ((paramFloat1 > i - j * 3) && (paramFloat1 < i - j * 2))
      return 0;
    if ((paramFloat1 > i - j * 2) && (paramFloat1 < i - j))
      return 1;
    if ((paramFloat1 > i - j) && (paramFloat1 < i))
      return 2;
    return -1;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.save();
    paramCanvas.setDrawFilter(this.filter);
    drawBg(paramCanvas);
    drawTitle(paramCanvas);
    drawLine(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.labelLayout.scaleToBounds(this.standardLayout);
    setMeasuredDimension(this.titleLayout.width, this.titleLayout.height);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (!this.mNeedDayInfo);
    do
    {
      do
        return true;
      while ((!this.mInTouchMode) && (paramMotionEvent.getAction() != 0));
      switch (paramMotionEvent.getAction())
      {
      default:
        return true;
      case 0:
        this.mLastMotionX = paramMotionEvent.getX();
        this.mLastMotionY = paramMotionEvent.getY();
        this.mSelectedIndex = getSelectedIndex(this.mLastMotionX, this.mLastMotionY);
        if (this.mSelectedIndex == -1);
        for (this.mInTouchMode = false; ; this.mInTouchMode = true)
        {
          invalidate();
          return true;
        }
      case 2:
        this.mLastMotionX = paramMotionEvent.getX();
        this.mLastMotionY = paramMotionEvent.getY();
      case 1:
      case 3:
      }
    }
    while (getSelectedIndex(this.mLastMotionX, this.mLastMotionY) == this.mSelectedIndex);
    this.mSelectedIndex = -1;
    this.mInTouchMode = false;
    invalidate();
    return true;
    int i = Calendar.getInstance().get(7);
    if (i == 0);
    for (int j = 7; ; j = i)
    {
      int k = j - 1;
      if (k < 1);
      for (int m = k + 7; ; m = k)
      {
        int n = j + 1;
        if (n > 7)
          n -= 7;
        Schedule localSchedule = Schedule.UNKNOWN;
        switch (this.mSelectedIndex)
        {
        default:
          if (this.mSchedule != localSchedule)
          {
            this.mSchedule = localSchedule;
            if (this.mSchedule != Schedule.TODAY)
              break label323;
            dispatchActionEvent("selectSchedule", Integer.valueOf(j));
          }
          break;
        case 0:
        case 1:
        case 2:
        }
        while (true)
        {
          this.mSelectedIndex = -1;
          invalidate();
          return true;
          localSchedule = Schedule.YESTERDAY;
          break;
          localSchedule = Schedule.TODAY;
          break;
          localSchedule = Schedule.TOMORROW;
          break;
          label323: if (this.mSchedule == Schedule.YESTERDAY)
            dispatchActionEvent("selectSchedule", Integer.valueOf(m));
          else if (this.mSchedule == Schedule.TOMORROW)
            dispatchActionEvent("selectSchedule", Integer.valueOf(n));
        }
        this.mInTouchMode = false;
        this.mSelectedIndex = -1;
        invalidate();
        return true;
      }
    }
  }

  public void update(String paramString, Object paramObject)
  {
    int i;
    int j;
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mNeedDayInfo = false;
      i = ((Integer)paramObject).intValue();
      j = Calendar.getInstance().get(7);
      if (j != 0)
        break label159;
    }
    label159: for (int k = 7; ; k = j)
    {
      int m = k - 1;
      if (m < 1);
      for (int n = m + 7; ; n = m)
      {
        int i1 = k + 1;
        if (i1 > 7)
          i1 -= 7;
        if (i == k)
        {
          this.mNeedDayInfo = true;
          this.mSchedule = Schedule.TODAY;
        }
        while (true)
        {
          invalidate();
          return;
          if (i == n)
          {
            this.mNeedDayInfo = true;
            this.mSchedule = Schedule.YESTERDAY;
          }
          else if (i == i1)
          {
            this.mNeedDayInfo = true;
            this.mSchedule = Schedule.TOMORROW;
          }
          else
          {
            this.mSchedule = Schedule.UNKNOWN;
          }
        }
      }
    }
  }

  public static enum Schedule
  {
    static
    {
      TODAY = new Schedule("TODAY", 2);
      TOMORROW = new Schedule("TOMORROW", 3);
      Schedule[] arrayOfSchedule = new Schedule[4];
      arrayOfSchedule[0] = UNKNOWN;
      arrayOfSchedule[1] = YESTERDAY;
      arrayOfSchedule[2] = TODAY;
      arrayOfSchedule[3] = TOMORROW;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.SchedulePopHeaderView
 * JD-Core Version:    0.6.2
 */