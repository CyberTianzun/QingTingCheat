package com.taobao.munion.view.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ActionBar extends LinearLayout
  implements View.OnClickListener
{
  private WebView a;

  public ActionBar(Context paramContext)
  {
    super(paramContext);
  }

  public ActionBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private View a(a parama)
  {
    View localView1 = parama.a();
    View localView2 = null;
    if (localView1 != null)
    {
      localView2 = newActionItem();
      ((ViewGroup)((ViewGroup)localView2).getChildAt(0)).addView(localView1);
      localView1.setTag(parama);
      localView1.setOnClickListener(this);
    }
    return localView2;
  }

  public void addAction(a parama)
  {
    addAction(parama, getChildCount());
  }

  public void addAction(a parama, int paramInt)
  {
    addView(a(parama), paramInt);
  }

  public int getActionCount()
  {
    return getChildCount();
  }

  public WebView getWebView()
  {
    return this.a;
  }

  public View newActionItem()
  {
    Context localContext = getContext();
    LinearLayout localLinearLayout = new LinearLayout(localContext);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-2, -1);
    localLayoutParams1.weight = 1.0F;
    localLinearLayout.setLayoutParams(localLayoutParams1);
    FrameLayout localFrameLayout = new FrameLayout(localContext);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-1, -1);
    localLayoutParams2.gravity = 17;
    localFrameLayout.setLayoutParams(localLayoutParams2);
    localLinearLayout.addView(localFrameLayout);
    return localLinearLayout;
  }

  public void onClick(View paramView)
  {
    Object localObject = paramView.getTag();
    if ((localObject instanceof a))
      ((a)localObject).a(paramView, this.a);
  }

  public boolean removeAction(a parama)
  {
    int i = getChildCount();
    for (int j = 0; ; j++)
    {
      boolean bool = false;
      if (j < i)
      {
        View localView = getChildAt(j);
        if (localView != null)
        {
          Object localObject = localView.getTag();
          if (((localObject instanceof a)) && (localObject.equals(parama)))
          {
            removeView(localView);
            bool = true;
          }
        }
      }
      else
      {
        return bool;
      }
    }
  }

  public void removeActionAt(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < getChildCount()))
      removeViewAt(paramInt);
  }

  public void removeAllActions()
  {
    removeAllViews();
  }

  public void setWebView(WebView paramWebView)
  {
    this.a = paramWebView;
  }

  public static abstract interface a
  {
    public abstract View a();

    public abstract void a(View paramView, WebView paramWebView);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.ActionBar
 * JD-Core Version:    0.6.2
 */