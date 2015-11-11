package fm.qingting.qtradio.view.personalcenter.clock;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View.MeasureSpec;
import fm.qingting.framework.tween.FrameTween;
import fm.qingting.framework.tween.FrameTween.SyncType;
import fm.qingting.framework.tween.IMotionHandler;
import fm.qingting.framework.tween.MotionController;
import fm.qingting.framework.tween.TweenProperty;
import fm.qingting.framework.tween.easing.Quad.EaseIn;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.SwitcherElement;
import fm.qingting.framework.view.SwitcherElement.OnSwitchChangeListener;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.AlarmInfo;
import fm.qingting.qtradio.model.AlarmInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.RingToneInfoNode;
import fm.qingting.qtradio.model.RingToneNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.QtTypeFace;
import fm.qingting.utils.QTMSGManage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlarmItemView extends QtView
  implements IMotionHandler
{
  private final ViewLayout checkStateLayout = this.checkbgLayout.createChildLT(30, 22, 2, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout checkbgLayout = this.standardLayout.createChildLT(48, 48, 30, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout deleteLayout = this.standardLayout.createChildLT(153, 72, 540, 7, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout lineLayout = this.standardLayout.createChildLT(720, 1, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private AlarmInfo mAlarmNode = null;
  private Object mAnimator;
  private ButtonViewElement mBg;
  private TextViewElement mChannelText;
  private Paint mCheckBgPaint = new Paint();
  private Rect mCheckRect = new Rect();
  private Paint mCheckStatePaint = new Paint();
  private int mHash = -66;
  private boolean mIsChecked = false;
  private int mOffset = 0;
  private Paint mPaint = new Paint();
  private TextViewElement mPeriodText;
  private TextViewElement mRingtoneText;
  private SwitcherElement mSwitcher;
  private TextViewElement mTimeText;
  private MotionController motionController;
  private final ViewLayout periodLayout = this.standardLayout.createChildLT(600, 45, 30, 9, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 285, 720, 800, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout switcherBgLayout = this.standardLayout.createChildLT(96, 58, 570, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout switcherIconLayout = this.switcherBgLayout.createChildLT(60, 58, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout timeLayout = this.standardLayout.createChildLT(450, 120, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public AlarmItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mBg = new ButtonViewElement(paramContext);
    this.mBg.setBackgroundColor(0, SkinManager.getCardColor());
    addElement(this.mBg, paramInt);
    this.mBg.setOnElementClickListener(new ViewElement.OnElementClickListener()
    {
      public void onElementClick(ViewElement paramAnonymousViewElement)
      {
        if (AlarmItemView.this.mOffset == 0)
        {
          AlarmItemView.this.dispatchActionEvent("select", null);
          return;
        }
        AlarmItemView.this.dispatchActionEvent("itemSelect", null);
      }
    });
    this.mPeriodText = new TextViewElement(paramContext);
    this.mPeriodText.setColor(SkinManager.getTextColorSubInfo());
    this.mPeriodText.setMaxLineLimit(1);
    addElement(this.mPeriodText);
    this.mTimeText = new TextViewElement(paramContext);
    this.mTimeText.setColor(SkinManager.getTextColorNormal());
    this.mTimeText.setMaxLineLimit(1);
    this.mTimeText.setTypeFace(QtTypeFace.getTypeFace(paramContext));
    addElement(this.mTimeText);
    this.mRingtoneText = new TextViewElement(paramContext);
    this.mRingtoneText.setColor(SkinManager.getTextColorSubInfo());
    this.mRingtoneText.setMaxLineLimit(1);
    addElement(this.mRingtoneText);
    this.mChannelText = new TextViewElement(paramContext);
    this.mChannelText.setColor(SkinManager.getTextColorSubInfo());
    this.mChannelText.setMaxLineLimit(1);
    addElement(this.mChannelText);
    this.mSwitcher = new SwitcherElement(paramContext);
    this.mSwitcher.setBgRes(2130837976, 2130837975);
    this.mSwitcher.setIconRes(2130837977);
    addElement(this.mSwitcher, paramInt);
    this.mSwitcher.setSwitchChangeListener(new SwitcherElement.OnSwitchChangeListener()
    {
      public void onChanged(boolean paramAnonymousBoolean)
      {
        AlarmItemView.this.mAlarmNode.isAvailable = paramAnonymousBoolean;
        if (!AlarmItemView.this.mAlarmNode.isAvailable)
          QTMSGManage.getInstance().sendStatistcsMessage("alarm_turnoff");
        InfoManager.getInstance().root().mPersonalCenterNode.alarmInfoNode.updateAlarm(AlarmItemView.this.mAlarmNode);
      }
    });
    this.mCheckBgPaint.setColor(SkinManager.getTextColorSubInfo());
    this.mCheckStatePaint.setColor(SkinManager.getTextColorHighlight());
    this.mCheckBgPaint.setStyle(Paint.Style.STROKE);
    this.mCheckStatePaint.setStyle(Paint.Style.FILL);
    init();
  }

  private void drawCheckState(Canvas paramCanvas)
  {
    if (this.mOffset > 0)
    {
      this.mCheckRect.offset(this.mOffset, 0);
      if (!this.mIsChecked)
        break label104;
      paramCanvas.drawCircle(this.mCheckRect.centerX(), this.mCheckRect.centerY(), this.checkbgLayout.width / 2, this.mCheckStatePaint);
      paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getResources(), this.mHash, 2130837748), null, this.mCheckRect, this.mPaint);
    }
    while (true)
    {
      this.mCheckRect.offset(-this.mOffset, 0);
      return;
      label104: paramCanvas.drawCircle(this.mCheckRect.centerX(), this.mCheckRect.centerY(), this.checkbgLayout.width / 2, this.mCheckBgPaint);
    }
  }

  private void drawLine(Canvas paramCanvas)
  {
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, this.lineLayout.leftMargin, this.standardLayout.width, getHeight() - this.lineLayout.height, this.lineLayout.height);
  }

  private String getChannel()
  {
    if (this.mAlarmNode.channelName == null)
      return "播放电台:";
    return "播放电台:" + this.mAlarmNode.channelName;
  }

  private String getDay(int paramInt)
  {
    int i = 1;
    Object localObject;
    if (!this.mAlarmNode.repeat)
    {
      localObject = " ";
      return localObject;
    }
    if (paramInt == 0)
      return "" + "工作日";
    String str1 = "" + "周";
    if ((paramInt & 0x4) > 0)
      str1 = str1 + "一 ";
    for (int j = i; ; j = 0)
    {
      if ((paramInt & 0x8) > 0)
      {
        str1 = str1 + "二 ";
        j++;
        i++;
      }
      if ((paramInt & 0x10) > 0)
      {
        str1 = str1 + "三 ";
        j++;
        i++;
      }
      if ((paramInt & 0x20) > 0)
      {
        str1 = str1 + "四 ";
        j++;
        i++;
      }
      if ((paramInt & 0x40) > 0)
      {
        str1 = str1 + "五 ";
        j++;
        i++;
      }
      if ((paramInt & 0x80) > 0)
      {
        str1 = str1 + "六 ";
        i++;
        j = 0;
      }
      int k;
      if ((paramInt & 0x2) > 0)
      {
        String str2 = str1 + "日";
        int m = i + 1;
        localObject = str2;
        j = 0;
        k = m;
      }
      while (true)
      {
        if (j == 5)
          localObject = "工作日";
        if (k == 7)
          localObject = "每天";
        if (!((String)localObject).equalsIgnoreCase("周"))
          break;
        return " ";
        k = i;
        localObject = str1;
      }
      i = 0;
    }
  }

  private int getMaxOffset()
  {
    return this.checkbgLayout.leftMargin + this.checkbgLayout.width;
  }

  private String getRingtone()
  {
    if (this.mAlarmNode.ringToneId == null);
    RingToneNode localRingToneNode;
    do
    {
      do
        return "闹铃声:不响铃,直接播放电台";
      while (this.mAlarmNode.ringToneId.equalsIgnoreCase("0"));
      localRingToneNode = InfoManager.getInstance().root().mRingToneInfoNode.getRingNodeById(this.mAlarmNode.ringToneId);
    }
    while (localRingToneNode == null);
    StringBuilder localStringBuilder = new StringBuilder().append("闹铃声:");
    if (localRingToneNode.ringDesc == null);
    for (String str = "不响铃,直接播放电台"; ; str = localRingToneNode.ringDesc)
      return str;
  }

  private String getTime(long paramLong)
  {
    int i = (int)(paramLong / 3600L);
    int j = (int)(paramLong / 60L % 60L);
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(i);
    arrayOfObject[1] = Integer.valueOf(j);
    return String.format(localLocale, "%02d:%02d", arrayOfObject);
  }

  @TargetApi(11)
  private void init()
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      this.mAnimator = new ValueAnimator();
      ((ValueAnimator)this.mAnimator).setDuration(200L);
      ((ValueAnimator)this.mAnimator).addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          AlarmItemView.this.setPosition(((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue());
        }
      });
      return;
    }
    this.motionController = new MotionController(this);
  }

  private void setPosition(float paramFloat)
  {
    this.mOffset = ((int)paramFloat);
    invalidate();
  }

  @TargetApi(11)
  private void startHideManageAnimation()
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      ValueAnimator localValueAnimator = (ValueAnimator)this.mAnimator;
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[0] = getMaxOffset();
      arrayOfFloat[1] = 0.0F;
      localValueAnimator.setFloatValues(arrayOfFloat);
      ((ValueAnimator)this.mAnimator).start();
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new TweenProperty("position", getMaxOffset(), 0.0F, 10.0F, new Quad.EaseIn()));
    FrameTween.to(this.motionController, this, localArrayList, FrameTween.SyncType.ASYNC);
  }

  @TargetApi(11)
  private void startShowManageAnimation(int paramInt)
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      ValueAnimator localValueAnimator = (ValueAnimator)this.mAnimator;
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[0] = 0.0F;
      arrayOfFloat[1] = paramInt;
      localValueAnimator.setFloatValues(arrayOfFloat);
      ((ValueAnimator)this.mAnimator).start();
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new TweenProperty("position", 0.0F, paramInt, 10.0F, new Quad.EaseIn()));
    FrameTween.to(this.motionController, this, localArrayList, FrameTween.SyncType.ASYNC);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    this.mPeriodText.setTranslationX(this.mOffset);
    this.mTimeText.setTranslationX(this.mOffset);
    this.mRingtoneText.setTranslationX(this.mOffset);
    this.mChannelText.setTranslationX(this.mOffset);
    int i;
    if (this.mOffset < 0)
    {
      i = 0;
      if ((i >= 0) && (i <= 255))
      {
        this.mSwitcher.setAlpha(255 - i);
        if (i != 255)
          break label139;
        this.mSwitcher.setVisible(4);
      }
    }
    while (true)
    {
      this.mSwitcher.setTranslationX(this.mOffset);
      super.onDraw(paramCanvas);
      drawCheckState(paramCanvas);
      drawLine(paramCanvas);
      return;
      i = (int)(255.0F * this.mOffset / getMaxOffset());
      break;
      label139: this.mSwitcher.setVisible(0);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.periodLayout.scaleToBounds(this.standardLayout);
    this.timeLayout.scaleToBounds(this.standardLayout);
    this.switcherBgLayout.scaleToBounds(this.standardLayout);
    this.switcherIconLayout.scaleToBounds(this.switcherBgLayout);
    this.deleteLayout.scaleToBounds(this.standardLayout);
    this.lineLayout.scaleToBounds(this.standardLayout);
    this.mPeriodText.measure(this.periodLayout);
    int i = this.periodLayout.topMargin + this.periodLayout.height;
    this.mTimeText.measure(this.timeLayout.leftMargin, i, this.timeLayout.leftMargin + this.timeLayout.width, i + this.timeLayout.height);
    int j = i + this.timeLayout.height;
    this.mRingtoneText.measure(this.periodLayout.leftMargin, j, this.periodLayout.leftMargin + this.periodLayout.width, j + this.periodLayout.height);
    int k = j + this.periodLayout.height;
    this.mChannelText.measure(this.periodLayout.leftMargin, k, this.periodLayout.leftMargin + this.periodLayout.width, k + this.periodLayout.height);
    int m = k + this.periodLayout.height;
    this.mSwitcher.measure(this.switcherBgLayout.leftMargin, (m - this.switcherBgLayout.height) / 2, this.switcherBgLayout.leftMargin + this.switcherBgLayout.width, (m + this.switcherBgLayout.height) / 2);
    this.mSwitcher.setIconSize(this.switcherBgLayout.leftMargin + this.switcherIconLayout.leftMargin, (m - this.switcherIconLayout.height) / 2, this.switcherBgLayout.leftMargin + this.switcherIconLayout.getRight(), (m + this.switcherIconLayout.height) / 2);
    this.mBg.measure(0, 0, this.standardLayout.width, m);
    float f = 0.65F * this.periodLayout.height;
    this.mPeriodText.setTextSize(f);
    this.mRingtoneText.setTextSize(f);
    this.mChannelText.setTextSize(f);
    this.mTimeText.setTextSize(0.85F * this.timeLayout.height);
    this.checkbgLayout.scaleToBounds(this.standardLayout);
    this.checkStateLayout.scaleToBounds(this.checkbgLayout);
    this.mCheckBgPaint.setStrokeWidth(this.checkStateLayout.leftMargin);
    this.mCheckRect.set((-this.checkbgLayout.width - this.checkStateLayout.width) / 2, (this.standardLayout.height - this.checkStateLayout.height) / 2, (-this.checkbgLayout.width + this.checkStateLayout.width) / 2, (this.standardLayout.height + this.checkStateLayout.height) / 2);
    setMeasuredDimension(this.standardLayout.width, m);
  }

  public void onMotionCancel(MotionController paramMotionController)
  {
  }

  public void onMotionComplete(MotionController paramMotionController)
  {
  }

  public void onMotionProgress(MotionController paramMotionController, float paramFloat1, float paramFloat2)
  {
    setPosition(paramFloat1);
  }

  public void onMotionStart(MotionController paramMotionController)
  {
  }

  public void onTargetChange(MotionController paramMotionController, float paramFloat)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
    {
      this.mAlarmNode = ((AlarmInfo)paramObject);
      this.mTimeText.setText(getTime(this.mAlarmNode.alarmTime), false);
      this.mPeriodText.setText(getDay(this.mAlarmNode.dayOfWeek), false);
      this.mChannelText.setText(getChannel(), false);
      this.mRingtoneText.setText(getRingtone(), false);
      if (this.mAlarmNode.isAvailable)
      {
        this.mSwitcher.switchOn(false);
        invalidate();
      }
    }
    label169: label205: 
    do
    {
      int i;
      do
      {
        do
        {
          return;
          this.mSwitcher.switchOff(false);
          break;
          if (paramString.equalsIgnoreCase("checkState"))
          {
            this.mIsChecked = ((Boolean)paramObject).booleanValue();
            invalidate();
            return;
          }
          if (!paramString.equalsIgnoreCase("showManage"))
            break label169;
        }
        while (this.mOffset > 0);
        startShowManageAnimation(((Integer)paramObject).intValue());
        return;
        if (!paramString.equalsIgnoreCase("showManageWithoutShift"))
          break label205;
        i = ((Integer)paramObject).intValue();
      }
      while (this.mOffset == i);
      this.mOffset = i;
      invalidate();
      return;
    }
    while ((!paramString.equalsIgnoreCase("hideManage")) || (this.mOffset == 0));
    startHideManageAnimation();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.clock.AlarmItemView
 * JD-Core Version:    0.6.2
 */