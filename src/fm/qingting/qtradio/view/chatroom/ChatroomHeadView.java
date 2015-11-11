package fm.qingting.qtradio.view.chatroom;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.view.chatroom.broadcastor.ChatroomBroadcastorRowView;
import fm.qingting.utils.QTMSGManage;
import java.util.List;

public class ChatroomHeadView extends ViewGroupViewImpl
  implements IEventHandler
{
  private ViewLayout broadcastorLayout = this.standardLayout.createChildLT(500, 160, 80, 80, ViewLayout.SCALE_FLAG_SLTCW);
  private ViewLayout flowerLabelLayout = this.standardLayout.createChildLT(84, 45, 608, 200, ViewLayout.SCALE_FLAG_SLTCW);
  private ViewLayout flowerLayout = this.standardLayout.createChildLT(84, 84, 608, 112, ViewLayout.SCALE_FLAG_SLTCW);
  private ViewLayout labelLayout = this.standardLayout.createChildLT(80, 45, 0, 130, ViewLayout.SCALE_FLAG_SLTCW);
  private ChatroomBroadcastorRowView mBroadcastorRowView;
  private ImageView mFloatingFlowerView;
  private ImageView mFlower;
  private TextView mFlowerLabelView;
  private TextView mLabel;
  private WindowManager.LayoutParams mLp;
  private WindowManager mManager;
  private ChatroomNaviView mNaviView;
  private ProgramNode mNode;
  private FlowerNumberView mNumberView;
  private ViewLayout naviLayout = this.standardLayout.createChildLT(720, 114, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private ViewLayout numberLayout = this.standardLayout.createChildLT(54, 30, 660, 102, ViewLayout.SCALE_FLAG_SLTCW);
  private ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 256, 720, 256, 0, 0, ViewLayout.FILL);

  public ChatroomHeadView(Context paramContext)
  {
    super(paramContext);
    this.mBroadcastorRowView = new ChatroomBroadcastorRowView(paramContext);
    addView(this.mBroadcastorRowView);
    this.mBroadcastorRowView.setEventHandler(this);
    this.mBroadcastorRowView.setVisibility(8);
    this.mNaviView = new ChatroomNaviView(paramContext);
    addView(this.mNaviView);
    this.mNaviView.setEventHandler(this);
    this.mLabel = new TextView(paramContext);
    this.mLabel.setText("主播:");
    this.mLabel.setTextColor(SkinManager.getBackgroundColor());
    this.mLabel.setGravity(17);
    addView(this.mLabel);
    this.mLabel.setVisibility(8);
    this.mFlower = new ImageView(paramContext);
    this.mFlower.setImageResource(2130837591);
    addView(this.mFlower);
    this.mFlower.setVisibility(8);
    this.mFlower.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ChatroomHeadView.this.mBroadcastorRowView.update("changeFlowerState", null);
      }
    });
    this.mFlowerLabelView = new TextView(paramContext);
    this.mFlowerLabelView.setText("献花");
    this.mFlowerLabelView.setTextColor(-824195);
    this.mFlowerLabelView.setGravity(17);
    addView(this.mFlowerLabelView);
    this.mFlowerLabelView.setVisibility(8);
    this.mNumberView = new FlowerNumberView(paramContext);
    addView(this.mNumberView);
    this.mNumberView.setVisibility(8);
  }

  @TargetApi(11)
  private void doFlowerAnimation(final Point paramPoint)
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      ValueAnimator localValueAnimator = new ValueAnimator();
      localValueAnimator.setInterpolator(new LinearInterpolator());
      localValueAnimator.setFloatValues(new float[] { 1.0F, 0.0F });
      localValueAnimator.setDuration(800L);
      localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
          ChatroomHeadView.this.updateFlowerPosition(f, paramPoint);
        }
      });
      localValueAnimator.addListener(new Animator.AnimatorListener()
      {
        public void onAnimationCancel(Animator paramAnonymousAnimator)
        {
        }

        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          ChatroomHeadView.this.mManager.removeView(ChatroomHeadView.this.mFloatingFlowerView);
          ChatroomHeadView.access$202(ChatroomHeadView.this, null);
        }

        public void onAnimationRepeat(Animator paramAnonymousAnimator)
        {
        }

        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
        }
      });
      localValueAnimator.start();
    }
  }

  private boolean existsDj()
  {
    if (this.mNode == null);
    while ((this.mNode.lstBroadcaster == null) || (this.mNode.lstBroadcaster.size() <= 0))
      return false;
    return true;
  }

  private int getThisHeight()
  {
    if (existsDj())
      return this.standardLayout.height;
    return this.naviLayout.height;
  }

  private void initFloatingFlower()
  {
    if (this.mManager == null)
      this.mManager = ((WindowManager)getContext().getSystemService("window"));
    if (this.mLp == null)
    {
      this.mLp = new WindowManager.LayoutParams();
      this.mLp.gravity = 51;
      this.mLp.format = 1;
    }
    if (this.mFloatingFlowerView == null)
    {
      this.mFloatingFlowerView = new ImageView(getContext());
      this.mFloatingFlowerView.setImageResource(2130837591);
      this.mLp.x = this.mFlower.getLeft();
      this.mLp.y = this.mFlower.getTop();
      this.mLp.width = this.mFlower.getMeasuredWidth();
      this.mLp.height = this.mFlower.getMeasuredHeight();
      this.mLp.alpha = 1.0F;
      this.mManager.addView(this.mFloatingFlowerView, this.mLp);
      return;
    }
    this.mLp.x = this.mFlower.getLeft();
    this.mLp.y = this.mFlower.getTop();
    this.mLp.width = this.mFlower.getMeasuredWidth();
    this.mLp.height = this.mFlower.getMeasuredHeight();
    this.mLp.alpha = 1.0F;
    this.mManager.updateViewLayout(this.mFloatingFlowerView, this.mLp);
  }

  private void updateFlowerPosition(float paramFloat, Point paramPoint)
  {
    int i = (int)(paramPoint.x + paramFloat * (this.flowerLayout.leftMargin + this.flowerLayout.width / 2 - paramPoint.x)) - this.flowerLayout.width / 2;
    if (paramFloat > 0.5F);
    for (int j = this.flowerLayout.topMargin + this.flowerLayout.height / 2; ; j = paramPoint.y)
    {
      int k = (int)((j - this.flowerLayout.height / 2) * Math.pow(2.0F * paramFloat - 1.0F, 2.0D));
      this.mLp.x = i;
      this.mLp.y = k;
      this.mLp.alpha = ((float)Math.sin(3.141592653589793D * paramFloat / 2.0D));
      this.mManager.updateViewLayout(this.mFloatingFlowerView, this.mLp);
      return;
    }
  }

  public void close(boolean paramBoolean)
  {
    this.mBroadcastorRowView.close(paramBoolean);
    this.mNaviView.close(paramBoolean);
    super.close(paramBoolean);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("flowerToPoint"))
    {
      int i = FlowerInfo.decreaseFlowerCnt();
      if (i >= 0)
      {
        this.mNumberView.update("setNumber", Integer.valueOf(i));
        Point localPoint = (Point)paramObject2;
        localPoint.offset(this.broadcastorLayout.leftMargin, this.broadcastorLayout.topMargin);
        initFloatingFlower();
        doFlowerAnimation(localPoint);
      }
    }
    do
    {
      return;
      if (paramString.equalsIgnoreCase("openonlinemember"))
      {
        dispatchActionEvent(paramString, paramObject2);
        QTMSGManage.getInstance().sendStatistcsMessage("chatroom_onlinecnt");
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("clickback"));
    dispatchActionEvent(paramString, paramObject2);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.broadcastorLayout.layoutView(this.mBroadcastorRowView);
    this.naviLayout.layoutView(this.mNaviView);
    this.labelLayout.layoutView(this.mLabel);
    this.flowerLayout.layoutView(this.mFlower);
    this.flowerLabelLayout.layoutView(this.mFlowerLabelView);
    this.numberLayout.layoutView(this.mNumberView);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.broadcastorLayout.scaleToBounds(this.standardLayout);
    this.naviLayout.scaleToBounds(this.standardLayout);
    this.labelLayout.scaleToBounds(this.standardLayout);
    this.flowerLayout.scaleToBounds(this.standardLayout);
    this.flowerLabelLayout.scaleToBounds(this.standardLayout);
    this.numberLayout.scaleToBounds(this.standardLayout);
    this.naviLayout.measureView(this.mNaviView);
    this.broadcastorLayout.measureView(this.mBroadcastorRowView);
    this.labelLayout.measureView(this.mLabel);
    this.flowerLayout.measureView(this.mFlower);
    this.flowerLabelLayout.measureView(this.mFlowerLabelView);
    this.numberLayout.measureView(this.mNumberView);
    this.mLabel.setTextSize(0, SkinManager.getInstance().getSubTextSize());
    this.mFlowerLabelView.setTextSize(0, SkinManager.getInstance().getSubTextSize());
    setMeasuredDimension(this.standardLayout.width, getThisHeight());
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("resetState"))
      this.mBroadcastorRowView.update(paramString, paramObject);
    while (!paramString.equalsIgnoreCase("setHeadInfo"))
      return;
    this.mNaviView.update("setData", paramObject);
    this.mBroadcastorRowView.update("setHeadInfo", paramObject);
    this.mNumberView.update("setData", null);
    this.mNode = ((ProgramNode)paramObject);
    boolean bool = existsDj();
    int i;
    if (bool)
    {
      i = 0;
      this.mBroadcastorRowView.setVisibility(i);
      this.mLabel.setVisibility(i);
      this.mFlower.setVisibility(i);
      this.mFlowerLabelView.setVisibility(i);
      this.mNumberView.setVisibility(i);
      if (!bool)
        break label163;
    }
    while (true)
    {
      try
      {
        setBackgroundResource(2130837589);
        requestLayout();
        return;
        i = 8;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        setBackgroundColor(SkinManager.getNaviBgColor());
        continue;
      }
      label163: setBackgroundColor(SkinManager.getNaviBgColor());
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.ChatroomHeadView
 * JD-Core Version:    0.6.2
 */