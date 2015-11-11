package com.handmark.pulltorefresh.library;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public abstract interface ILoadingLayout
{
  public abstract void setLastUpdatedLabel(CharSequence paramCharSequence);

  public abstract void setLoadingDrawable(Drawable paramDrawable);

  public abstract void setPullLabel(CharSequence paramCharSequence);

  public abstract void setRefreshingLabel(CharSequence paramCharSequence);

  public abstract void setReleaseLabel(CharSequence paramCharSequence);

  public abstract void setTextTypeface(Typeface paramTypeface);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.handmark.pulltorefresh.library.ILoadingLayout
 * JD-Core Version:    0.6.2
 */