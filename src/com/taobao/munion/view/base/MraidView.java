package com.taobao.munion.view.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.taobao.munion.base.Log;
import com.taobao.munion.view.webview.windvane.mraid.MraidWebView;
import com.taobao.munion.view.webview.windvane.mraid.MraidWebView.ClickCallBackListener;
import com.taobao.munion.view.webview.windvane.mraid.MraidWebView.b;
import com.taobao.munion.view.webview.windvane.mraid.MraidWebView.d;
import com.taobao.munion.view.webview.windvane.mraid.MraidWebView.e;
import com.taobao.munion.view.webview.windvane.mraid.c;

public class MraidView extends RelativeLayout
{
  private MraidWebView a;

  public MraidView(Context paramContext)
  {
    super(paramContext);
    a(paramContext);
  }

  public MraidView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    a(paramContext);
  }

  public MraidView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    a(paramContext);
  }

  private void a(Context paramContext)
  {
    setVisibility(4);
    ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-1, -1);
    this.a = new MraidWebView(paramContext);
    addView(this.a, localLayoutParams);
  }

  public void closeAd(boolean paramBoolean)
  {
    if (this.a != null)
    {
      this.a.selfHidden = true;
      if (paramBoolean)
        this.a.closeAd();
    }
    else
    {
      return;
    }
    try
    {
      this.a.doHidden(null);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void creativeRequestInit(String paramString)
  {
    if (this.a != null)
      c.a().b(this.a, paramString);
  }

  public void creativeReuqest(String paramString)
  {
    if (this.a != null)
      c.a().a(this.a, paramString);
  }

  public void destroy()
  {
    if (this.a != null)
      this.a.destroy();
  }

  public boolean getFirstFinish()
  {
    if (this.a != null)
      return this.a.mFirstFinish;
    return false;
  }

  public boolean getIsShowAd()
  {
    if (this.a != null)
      return this.a.isShowAd;
    return false;
  }

  public void loadUrl(String paramString)
  {
    if (this.a != null)
      this.a.loadUrl(paramString);
  }

  public void locationControllerEnable(boolean paramBoolean)
  {
    if (this.a != null)
      this.a.locationControllerEnable(paramBoolean);
  }

  public boolean onBackPressed()
  {
    MraidWebView localMraidWebView = this.a;
    boolean bool = false;
    if (localMraidWebView != null)
    {
      MraidWebView.e locale1 = this.a.mViewState;
      MraidWebView.e locale2 = MraidWebView.e.c;
      bool = false;
      if (locale1 == locale2)
      {
        this.a.close(new String[0]);
        bool = true;
      }
    }
    return bool;
  }

  public void setClickCallBackListener(MraidWebView.ClickCallBackListener paramClickCallBackListener)
  {
    if (this.a != null)
      this.a.setClickCallBackListener(paramClickCallBackListener);
  }

  public void setFirstFinish(boolean paramBoolean)
  {
    if (this.a != null)
      this.a.mFirstFinish = paramBoolean;
  }

  public void setIsShowAd(boolean paramBoolean)
  {
    if (this.a != null)
    {
      this.a.isShowAd = paramBoolean;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(getIsShowAd());
      Log.i("set isshowad in creativerequest to %s", arrayOfObject);
    }
  }

  public void setPageFinishCallBack(a parama)
  {
    if (this.a != null)
      this.a.setPageFinishedCallBack(parama);
  }

  public void setPlacement(MraidWebView.b paramb)
  {
    if (this.a != null)
      this.a.setPlacement(paramb);
  }

  public void setScrollEnable(boolean paramBoolean)
  {
    if (this.a != null)
      this.a.setScrollEnable(paramBoolean);
  }

  public void setStateChangeCallBackListener(MraidWebView.d paramd)
  {
    if (this.a != null)
      this.a.setStateChangeCallBackListener(paramd);
  }

  public void setmSlotId(String paramString)
  {
    if (this.a != null)
      this.a.setmSlotId(paramString);
  }

  public void showAd()
  {
    if (this.a != null)
      this.a.showAd();
  }

  public static abstract interface a
  {
    public abstract void a();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.base.MraidView
 * JD-Core Version:    0.6.2
 */