package fm.qingting.qtradio.view.playview;

import android.content.Context;
import android.graphics.Point;
import android.text.Layout.Alignment;
import android.view.View.MeasureSpec;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.utils.OnPlayProcessListener;
import fm.qingting.utils.PlayProcessSyncUtil;
import java.util.Calendar;
import java.util.Locale;

public class SeekBarView extends QtView
  implements OnPlayProcessListener, AbsSeekBarElement.OnSeekBarChangeListener
{
  private static final String HIDEACCURATESEEK = "hideaccurateseek";
  private static final String HOURFORMAT = "%02d:%02d:%02d";
  public static final String PROGRESSCHANGED = "progresschanged";
  private static final String SHOWACCURATESEEK = "showaccurateseek";
  private final ViewLayout leftTimeLayout = this.standardLayout.createChildLT(150, 45, 10, 60, ViewLayout.SCALE_FLAG_SLTCW);
  private TextViewElement mLeftTimeElement;
  private int mLeftTimeOffset = 0;
  private int mRightLength = 0;
  private TextViewElement mRightTimeElement;
  private SeekBarElement mSeekBarElement;
  private final ViewLayout rightTimeLayout = this.standardLayout.createChildLT(150, 45, 560, 60, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout seekLayout = this.standardLayout.createChildLT(720, 70, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 105, 720, 105, 0, 0, ViewLayout.FILL);

  public SeekBarView(Context paramContext)
  {
    super(paramContext);
    int i = hashCode();
    this.mSeekBarElement = new SeekBarElement(paramContext);
    addElement(this.mSeekBarElement, i);
    this.mLeftTimeElement = new TextViewElement(paramContext);
    this.mLeftTimeElement.setColor(SkinManager.getNewPlaySubColor());
    this.mLeftTimeElement.setMaxLineLimit(1);
    this.mLeftTimeElement.setText("00:00:00");
    addElement(this.mLeftTimeElement);
    this.mRightTimeElement = new TextViewElement(paramContext);
    this.mRightTimeElement.setColor(SkinManager.getNewPlaySubColor());
    this.mRightTimeElement.setMaxLineLimit(1);
    this.mRightTimeElement.setAlignment(Layout.Alignment.ALIGN_OPPOSITE);
    this.mRightTimeElement.setText("00:00:00");
    addElement(this.mRightTimeElement);
    PlayProcessSyncUtil.getInstance().addListener(this);
    this.mSeekBarElement.setSeekbarChangeListener(this);
    this.mSeekBarElement.setMax(PlayProcessSyncUtil.getInstance().getTotalLength());
    this.mSeekBarElement.initProgress(PlayProcessSyncUtil.getInstance().getCurrentPlayTime());
  }

  private String durationToStr(int paramInt)
  {
    int i = paramInt / 3600;
    int j = paramInt / 60 % 60;
    int k = paramInt % 60;
    if (i < 0)
      return "";
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(i);
    arrayOfObject[1] = Integer.valueOf(j);
    arrayOfObject[2] = Integer.valueOf(k);
    return String.format(localLocale, "%02d:%02d:%02d", arrayOfObject);
  }

  private String getCurrRelativeTime()
  {
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    int k = localCalendar.get(13);
    if (i < 0)
      return "";
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(i);
    arrayOfObject[1] = Integer.valueOf(j);
    arrayOfObject[2] = Integer.valueOf(k);
    return String.format(localLocale, "%02d:%02d:%02d", arrayOfObject);
  }

  private String getLeftTime(int paramInt)
  {
    return durationToStr(paramInt + this.mLeftTimeOffset);
  }

  private String getRightTime(int paramInt)
  {
    return durationToStr(paramInt);
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    PlayProcessSyncUtil.getInstance().removeListener(this);
    super.close(paramBoolean);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("progressPosition"))
    {
      Point localPoint = new Point();
      localPoint.x = ((int)(PlayProcessSyncUtil.getInstance().getCurrentPlayRatio() * getWidth()));
      localPoint.y = 0;
      return localPoint;
    }
    if (paramString.equalsIgnoreCase("leftTimeOffset"))
      return Integer.valueOf(this.mLeftTimeOffset);
    if (paramString.equalsIgnoreCase("rightTime"))
      return Integer.valueOf(this.mRightLength);
    if (paramString.equalsIgnoreCase("progress"))
      return Integer.valueOf(this.mSeekBarElement.getProgress());
    return super.getValue(paramString, paramObject);
  }

  public void onManualSeek()
  {
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.seekLayout.scaleToBounds(this.standardLayout);
    this.leftTimeLayout.scaleToBounds(this.standardLayout);
    this.rightTimeLayout.scaleToBounds(this.standardLayout);
    this.mSeekBarElement.measure(this.seekLayout);
    this.mLeftTimeElement.measure(this.leftTimeLayout);
    this.mRightTimeElement.measure(this.rightTimeLayout);
    this.mLeftTimeElement.setTextSize(SkinManager.getInstance().getTinyTextSize());
    this.mRightTimeElement.setTextSize(SkinManager.getInstance().getTinyTextSize());
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void onProcessChanged()
  {
    int i = PlayProcessSyncUtil.getInstance().getCurrentPlayTime();
    this.mSeekBarElement.setProgress(i, false);
  }

  public void onProcessMaxChanged()
  {
    this.mSeekBarElement.setMax(PlayProcessSyncUtil.getInstance().getTotalLength());
  }

  public void onProgressChanged(AbsSeekBarElement paramAbsSeekBarElement, int paramInt, boolean paramBoolean)
  {
    this.mLeftTimeElement.setText(getLeftTime(paramInt));
    dispatchActionEvent("progresschanged", Integer.valueOf(paramInt));
  }

  public void onProgressPause()
  {
  }

  public void onProgressResume()
  {
  }

  public void onSeek(AbsSeekBarElement paramAbsSeekBarElement, float paramFloat)
  {
    PlayProcessSyncUtil.getInstance().attempSeek(paramFloat);
  }

  public void onStartTrackingTouch(AbsSeekBarElement paramAbsSeekBarElement)
  {
    dispatchActionEvent("showaccurateseek", null);
  }

  public void onStopTrackingTouch(AbsSeekBarElement paramAbsSeekBarElement)
  {
    dispatchActionEvent("hideaccurateseek", null);
  }

  public void onThumbClick(AbsSeekBarElement paramAbsSeekBarElement)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    int i = 1;
    if ((!paramString.equalsIgnoreCase("setNode")) || (paramObject == null));
    ProgramNode localProgramNode;
    int j;
    label77: 
    do
    {
      Node localNode;
      do
      {
        return;
        localNode = (Node)paramObject;
      }
      while (!localNode.nodeName.equalsIgnoreCase("program"));
      localProgramNode = (ProgramNode)localNode;
      j = localProgramNode.getCurrPlayStatus();
      int k = localProgramNode.channelType;
      if (j == 3)
      {
        int m;
        if (k == i)
        {
          m = 0;
          if (i == 0)
            break label140;
          this.mLeftTimeOffset = m;
          if (i == 0)
            break label150;
        }
        for (int n = localProgramNode.getDuration(); ; n = localProgramNode.endTime())
        {
          int i1 = PlayProcessSyncUtil.getInstance().getCurrentPlayTime();
          this.mRightLength = n;
          this.mRightTimeElement.setText(getRightTime(n));
          this.mLeftTimeElement.setText(getLeftTime(i1));
          return;
          i = 0;
          break;
          m = localProgramNode.startTime();
          break label77;
        }
      }
    }
    while (j != i);
    label140: label150: this.mLeftTimeOffset = localProgramNode.startTime();
    this.mRightLength = localProgramNode.endTime();
    this.mRightTimeElement.setText(getRightTime(this.mRightLength));
    this.mLeftTimeElement.setText(getCurrRelativeTime());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.SeekBarView
 * JD-Core Version:    0.6.2
 */