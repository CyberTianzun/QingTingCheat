package fm.qingting.qtradio.view.personalcenter.clock;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.AlarmInfo;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RingToneInfoNode;
import fm.qingting.qtradio.model.RingToneNode;
import fm.qingting.qtradio.model.RootNode;

public class AlarmSettingView extends ViewGroupViewImpl
  implements IEventHandler
{
  private TimePickView mHourPickView;
  private TimePickView mMinutePickView;
  private final ViewLayout ringtoneLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 240, 480, 800, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private AlarmDayRingtoneView ringtoneView;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private final ViewLayout timePickerLayout = ViewLayout.createViewLayoutWithBoundsLT(240, 331, 480, 800, 0, 20, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);

  public AlarmSettingView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.mHourPickView = new TimePickView(paramContext);
    this.mHourPickView.update("setTimeType", TimePickView.TimeType.Hour);
    addView(this.mHourPickView);
    this.mMinutePickView = new TimePickView(paramContext);
    this.mMinutePickView.update("setTimeType", TimePickView.TimeType.Minute);
    addView(this.mMinutePickView);
    this.ringtoneView = new AlarmDayRingtoneView(paramContext);
    this.ringtoneView.setEventHandler(this);
    addView(this.ringtoneView);
  }

  public void close(boolean paramBoolean)
  {
    this.mHourPickView.close(paramBoolean);
    this.ringtoneView.close(paramBoolean);
    super.close(paramBoolean);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    int i;
    if (paramString.equalsIgnoreCase("time"))
    {
      i = ((Integer)this.mHourPickView.getValue("selectedIndex", null)).intValue() % 24;
      if (i >= 0)
        break label129;
    }
    label129: for (int j = i + 24; ; j = i)
    {
      int k = ((Integer)this.mMinutePickView.getValue("selectedIndex", null)).intValue() % 60;
      if (k < 0)
        k += 60;
      return Integer.valueOf(j * 3600 + k * 60);
      if (paramString.equalsIgnoreCase("day"))
        return this.ringtoneView.getValue(paramString, paramObject);
      if (paramString.equalsIgnoreCase("repeat"))
        return this.ringtoneView.getValue(paramString, paramObject);
      return super.getValue(paramString, paramObject);
    }
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    dispatchActionEvent(paramString, paramObject2);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.ringtoneView.layout((this.standardLayout.width - this.ringtoneLayout.width) / 2, this.ringtoneLayout.topMargin, (this.standardLayout.width + this.ringtoneLayout.width) / 2, this.ringtoneLayout.topMargin + this.ringtoneLayout.height);
    this.mHourPickView.layout(0, this.standardLayout.height - this.timePickerLayout.height, this.timePickerLayout.width, this.standardLayout.height);
    this.mMinutePickView.layout(this.timePickerLayout.width, this.standardLayout.height - this.timePickerLayout.height, this.standardLayout.width, this.standardLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.timePickerLayout.scaleToBounds(this.standardLayout);
    this.timePickerLayout.measureView(this.mHourPickView);
    this.timePickerLayout.measureView(this.mMinutePickView);
    this.ringtoneLayout.scaleToBounds(this.standardLayout);
    this.ringtoneLayout.measureView(this.ringtoneView);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    String str1;
    String str2;
    if (paramString.equalsIgnoreCase("setData"))
    {
      AlarmInfo localAlarmInfo = (AlarmInfo)paramObject;
      long l = localAlarmInfo.alarmTime;
      int i = (int)(l / 3600L);
      int j = (int)(l / 60L % 60L);
      this.mHourPickView.update("setTime", Integer.valueOf(i));
      this.mMinutePickView.update("setTime", Integer.valueOf(j));
      str1 = localAlarmInfo.ringToneId;
      if ((str1 != null) && (str1.equalsIgnoreCase("0")))
      {
        str2 = "直接播放电台";
        this.ringtoneView.setParam(localAlarmInfo.repeat, localAlarmInfo.dayOfWeek, localAlarmInfo.channelName, str2);
      }
    }
    do
    {
      return;
      RingToneNode localRingToneNode2 = InfoManager.getInstance().root().mRingToneInfoNode.getRingNodeById(str1);
      str2 = null;
      if (localRingToneNode2 == null)
        break;
      str2 = localRingToneNode2.ringDesc;
      break;
      if (paramString.equalsIgnoreCase("day"))
      {
        this.ringtoneView.setDay(((Integer)paramObject).intValue());
        return;
      }
      if (paramString.equalsIgnoreCase("repeat"))
      {
        this.ringtoneView.setRepeat(((Boolean)paramObject).booleanValue());
        return;
      }
      if (paramString.equalsIgnoreCase("changeRing"))
      {
        this.ringtoneView.setChannel((String)paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("pickedRingtone"));
    if (paramObject == null)
    {
      this.ringtoneView.setRingtone("直接播放电台");
      return;
    }
    RingToneNode localRingToneNode1 = (RingToneNode)paramObject;
    this.ringtoneView.setRingtone(localRingToneNode1.ringDesc);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.clock.AlarmSettingView
 * JD-Core Version:    0.6.2
 */