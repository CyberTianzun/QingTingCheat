package com.handmark.pulltorefresh.library.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;

@SuppressLint({"ViewConstructor"})
public abstract class LoadingLayout extends FrameLayout
  implements ILoadingLayout
{
  static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
  static final String LOG_TAG = "PullToRefresh-LoadingLayout";
  protected final ImageView mHeaderImage;
  protected final ProgressBar mHeaderProgress;
  private final TextView mHeaderText;
  private FrameLayout mInnerLayout;
  protected final PullToRefreshBase.Mode mMode;
  private CharSequence mPullLabel;
  private CharSequence mRefreshingLabel;
  private CharSequence mReleaseLabel;
  protected final PullToRefreshBase.Orientation mScrollDirection;
  private final TextView mSubHeaderText;
  private boolean mUseIntrinsicAnimation;

  public LoadingLayout(Context paramContext, PullToRefreshBase.Mode paramMode, PullToRefreshBase.Orientation paramOrientation, TypedArray paramTypedArray)
  {
    super(paramContext);
    this.mMode = paramMode;
    this.mScrollDirection = paramOrientation;
    1.$SwitchMap$com$handmark$pulltorefresh$library$PullToRefreshBase$Orientation[paramOrientation.ordinal()];
    LayoutInflater.from(paramContext).inflate(2130903059, this);
    this.mInnerLayout = ((FrameLayout)findViewById(2131230851));
    this.mHeaderText = ((TextView)this.mInnerLayout.findViewById(2131230854));
    this.mHeaderProgress = ((ProgressBar)this.mInnerLayout.findViewById(2131230853));
    this.mSubHeaderText = ((TextView)this.mInnerLayout.findViewById(2131230855));
    this.mHeaderImage = ((ImageView)this.mInnerLayout.findViewById(2131230852));
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.mInnerLayout.getLayoutParams();
    int j;
    Drawable localDrawable1;
    switch (1.$SwitchMap$com$handmark$pulltorefresh$library$PullToRefreshBase$Mode[paramMode.ordinal()])
    {
    default:
      if (paramOrientation == PullToRefreshBase.Orientation.VERTICAL)
      {
        j = 80;
        localLayoutParams.gravity = j;
        this.mPullLabel = paramContext.getString(2131492891);
        this.mRefreshingLabel = paramContext.getString(2131492893);
        this.mReleaseLabel = paramContext.getString(2131492892);
        if (paramTypedArray.hasValue(1))
        {
          Drawable localDrawable2 = paramTypedArray.getDrawable(1);
          if (localDrawable2 != null)
            ViewCompat.setBackground(this, localDrawable2);
        }
        if (paramTypedArray.hasValue(10))
        {
          TypedValue localTypedValue1 = new TypedValue();
          paramTypedArray.getValue(10, localTypedValue1);
          setTextAppearance(localTypedValue1.data);
        }
        if (paramTypedArray.hasValue(11))
        {
          TypedValue localTypedValue2 = new TypedValue();
          paramTypedArray.getValue(11, localTypedValue2);
          setSubTextAppearance(localTypedValue2.data);
        }
        if (paramTypedArray.hasValue(2))
        {
          ColorStateList localColorStateList2 = paramTypedArray.getColorStateList(2);
          if (localColorStateList2 != null)
            setTextColor(localColorStateList2);
        }
        if (paramTypedArray.hasValue(3))
        {
          ColorStateList localColorStateList1 = paramTypedArray.getColorStateList(3);
          if (localColorStateList1 != null)
            setSubTextColor(localColorStateList1);
        }
        boolean bool = paramTypedArray.hasValue(6);
        localDrawable1 = null;
        if (bool)
          localDrawable1 = paramTypedArray.getDrawable(6);
        switch (1.$SwitchMap$com$handmark$pulltorefresh$library$PullToRefreshBase$Mode[paramMode.ordinal()])
        {
        default:
          if (paramTypedArray.hasValue(7))
            localDrawable1 = paramTypedArray.getDrawable(7);
          break;
        case 1:
        }
      }
      break;
    case 1:
    }
    while (true)
    {
      if (localDrawable1 == null)
        localDrawable1 = paramContext.getResources().getDrawable(getDefaultDrawableResId());
      setLoadingDrawable(localDrawable1);
      reset();
      return;
      if (paramOrientation == PullToRefreshBase.Orientation.VERTICAL);
      for (int i = 48; ; i = 3)
      {
        localLayoutParams.gravity = i;
        this.mPullLabel = paramContext.getString(2131492891);
        this.mRefreshingLabel = paramContext.getString(2131492893);
        this.mReleaseLabel = paramContext.getString(2131492892);
        break;
      }
      j = 5;
      break;
      if (paramTypedArray.hasValue(17))
      {
        Utils.warnDeprecation("ptrDrawableTop", "ptrDrawableStart");
        localDrawable1 = paramTypedArray.getDrawable(17);
        continue;
        if (paramTypedArray.hasValue(8))
        {
          localDrawable1 = paramTypedArray.getDrawable(8);
        }
        else if (paramTypedArray.hasValue(18))
        {
          Utils.warnDeprecation("ptrDrawableBottom", "ptrDrawableEnd");
          localDrawable1 = paramTypedArray.getDrawable(18);
        }
      }
    }
  }

  private void setSubHeaderText(CharSequence paramCharSequence)
  {
    if (this.mSubHeaderText != null)
    {
      if (!TextUtils.isEmpty(paramCharSequence))
        break label24;
      this.mSubHeaderText.setVisibility(8);
    }
    label24: 
    do
    {
      return;
      this.mSubHeaderText.setText(paramCharSequence);
    }
    while (8 != this.mSubHeaderText.getVisibility());
    this.mSubHeaderText.setVisibility(0);
  }

  private void setSubTextAppearance(int paramInt)
  {
    if (this.mSubHeaderText != null)
      this.mSubHeaderText.setTextAppearance(getContext(), paramInt);
  }

  private void setSubTextColor(ColorStateList paramColorStateList)
  {
    if (this.mSubHeaderText != null)
      this.mSubHeaderText.setTextColor(paramColorStateList);
  }

  private void setTextAppearance(int paramInt)
  {
    if (this.mHeaderText != null)
      this.mHeaderText.setTextAppearance(getContext(), paramInt);
    if (this.mSubHeaderText != null)
      this.mSubHeaderText.setTextAppearance(getContext(), paramInt);
  }

  private void setTextColor(ColorStateList paramColorStateList)
  {
    if (this.mHeaderText != null)
      this.mHeaderText.setTextColor(paramColorStateList);
    if (this.mSubHeaderText != null)
      this.mSubHeaderText.setTextColor(paramColorStateList);
  }

  public final int getContentSize()
  {
    switch (1.$SwitchMap$com$handmark$pulltorefresh$library$PullToRefreshBase$Orientation[this.mScrollDirection.ordinal()])
    {
    default:
      return this.mInnerLayout.getHeight();
    case 2:
    }
    return this.mInnerLayout.getWidth();
  }

  protected abstract int getDefaultDrawableResId();

  public final void hideAllViews()
  {
    if (this.mHeaderText.getVisibility() == 0)
      this.mHeaderText.setVisibility(4);
    if (this.mHeaderProgress.getVisibility() == 0)
      this.mHeaderProgress.setVisibility(4);
    if (this.mHeaderImage.getVisibility() == 0)
      this.mHeaderImage.setVisibility(4);
    if (this.mSubHeaderText.getVisibility() == 0)
      this.mSubHeaderText.setVisibility(4);
  }

  protected abstract void onLoadingDrawableSet(Drawable paramDrawable);

  public final void onPull(float paramFloat)
  {
    if (!this.mUseIntrinsicAnimation)
      onPullImpl(paramFloat);
  }

  protected abstract void onPullImpl(float paramFloat);

  public final void pullToRefresh()
  {
    if (this.mHeaderText != null)
      this.mHeaderText.setText(this.mPullLabel);
    pullToRefreshImpl();
  }

  protected abstract void pullToRefreshImpl();

  public final void refreshing()
  {
    if (this.mHeaderText != null)
      this.mHeaderText.setText(this.mRefreshingLabel);
    if (this.mUseIntrinsicAnimation)
      ((AnimationDrawable)this.mHeaderImage.getDrawable()).start();
    while (true)
    {
      if (this.mSubHeaderText != null)
        this.mSubHeaderText.setVisibility(8);
      return;
      refreshingImpl();
    }
  }

  protected abstract void refreshingImpl();

  public final void releaseToRefresh()
  {
    if (this.mHeaderText != null)
      this.mHeaderText.setText(this.mReleaseLabel);
    releaseToRefreshImpl();
  }

  protected abstract void releaseToRefreshImpl();

  public final void reset()
  {
    if (this.mHeaderText != null)
      this.mHeaderText.setText(this.mPullLabel);
    this.mHeaderImage.setVisibility(0);
    if (this.mUseIntrinsicAnimation)
      ((AnimationDrawable)this.mHeaderImage.getDrawable()).stop();
    while (true)
    {
      if (this.mSubHeaderText != null)
      {
        if (!TextUtils.isEmpty(this.mSubHeaderText.getText()))
          break;
        this.mSubHeaderText.setVisibility(8);
      }
      return;
      resetImpl();
    }
    this.mSubHeaderText.setVisibility(0);
  }

  protected abstract void resetImpl();

  public final void setHeight(int paramInt)
  {
    getLayoutParams().height = paramInt;
    requestLayout();
  }

  public void setLastUpdatedLabel(CharSequence paramCharSequence)
  {
    setSubHeaderText(paramCharSequence);
  }

  public final void setLoadingDrawable(Drawable paramDrawable)
  {
    this.mHeaderImage.setImageDrawable(paramDrawable);
    this.mUseIntrinsicAnimation = (paramDrawable instanceof AnimationDrawable);
    onLoadingDrawableSet(paramDrawable);
  }

  public void setPullLabel(CharSequence paramCharSequence)
  {
    this.mPullLabel = paramCharSequence;
  }

  public void setRefreshingLabel(CharSequence paramCharSequence)
  {
    this.mRefreshingLabel = paramCharSequence;
  }

  public void setReleaseLabel(CharSequence paramCharSequence)
  {
    this.mReleaseLabel = paramCharSequence;
  }

  public void setTextTypeface(Typeface paramTypeface)
  {
    this.mHeaderText.setTypeface(paramTypeface);
  }

  public final void setWidth(int paramInt)
  {
    getLayoutParams().width = paramInt;
    requestLayout();
  }

  public final void showInvisibleViews()
  {
    if (4 == this.mHeaderText.getVisibility())
      this.mHeaderText.setVisibility(0);
    if (4 == this.mHeaderProgress.getVisibility())
      this.mHeaderProgress.setVisibility(0);
    if (4 == this.mHeaderImage.getVisibility())
      this.mHeaderImage.setVisibility(0);
    if (4 == this.mSubHeaderText.getVisibility())
      this.mSubHeaderText.setVisibility(0);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.handmark.pulltorefresh.library.internal.LoadingLayout
 * JD-Core Version:    0.6.2
 */