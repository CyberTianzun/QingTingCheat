package com.taobao.munion.view.webview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import com.taobao.munion.Munion;
import com.taobao.munion.base.ResourceManager;
import com.taobao.munion.base.ioc.x;
import com.taobao.munion.view.base.BaseWebViewDialog;
import com.taobao.munion.view.webview.windvane.WindVaneWebView;
import com.taobao.munion.view.webview.windvane.l;
import com.taobao.munion.view.webview.windvane.m;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MunionBrowser extends BaseWebViewDialog
{
  String a;
  ProgressBar b;
  boolean c = false;
  Map<String, String> d = new HashMap();
  Handler e;
  private final FrameLayout f;
  private final ActionBar g;
  private final List<ActionBar.a> h;
  private final ResourceManager i;

  public MunionBrowser(Context paramContext)
  {
    super(paramContext);
    this.d.put("Referer", "native null refer");
    this.e = new Handler();
    this.i = ((ResourceManager)Munion.init(paramContext.getApplicationContext()).d("resource"));
    int j = (int)dipToPixels(this.mContext, 48.0F);
    RelativeLayout localRelativeLayout = new RelativeLayout(this.mContext);
    localRelativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, j);
    localLayoutParams1.addRule(12);
    this.g = newActionLayout();
    this.g.setId(305419896);
    this.h = newDefaultActions();
    if ((this.h != null) && (this.h.size() > 0))
    {
      Iterator localIterator = this.h.iterator();
      while (localIterator.hasNext())
      {
        ActionBar.a locala = (ActionBar.a)localIterator.next();
        this.g.addAction(locala);
      }
      this.g.setVisibility(0);
    }
    localRelativeLayout.addView(this.g, localLayoutParams1);
    this.f = new FrameLayout(this.mContext);
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
    localLayoutParams2.addRule(2, this.g.getId());
    localRelativeLayout.addView(this.f, localLayoutParams2);
    setContentView(localRelativeLayout);
  }

  private void a()
  {
    if (this.h == null);
    while (true)
    {
      return;
      Iterator localIterator = this.h.iterator();
      while (localIterator.hasNext())
      {
        ActionBar.a locala = (ActionBar.a)localIterator.next();
        if ((locala instanceof a))
        {
          a locala1 = (a)locala;
          if ("back".equals(locala1.b()))
          {
            if (this.mWebView.canGoBack())
              locala1.a(true);
            else
              locala1.a(false);
          }
          else if ("forward".equals(locala1.b()))
            if (this.mWebView.canGoForward())
              locala1.a(true);
            else
              locala1.a(false);
        }
      }
    }
  }

  public static float dipToPixels(Context paramContext, float paramFloat)
  {
    return TypedValue.applyDimension(1, paramFloat, paramContext.getResources().getDisplayMetrics());
  }

  public void initContent()
  {
    this.mWebView = new WindVaneWebView(this.mContext);
    this.f.addView(this.mWebView, new ViewGroup.LayoutParams(-1, -1));
    this.g.setWebView(this.mWebView);
  }

  public void initWebview(WebView paramWebView)
  {
    super.initWebview(paramWebView);
    paramWebView.setWebChromeClient(new l((WindVaneWebView)paramWebView)
    {
      public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
      {
        if (MunionBrowser.this.b != null)
        {
          MunionBrowser.this.b.setProgress(paramAnonymousInt);
          if (paramAnonymousInt > 90)
            MunionBrowser.this.b.setVisibility(4);
        }
      }
    });
    paramWebView.setWebViewClient(new m(1)
    {
      public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
        MunionBrowser.a(MunionBrowser.this);
      }

      public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
      {
        try
        {
          Uri localUri = Uri.parse(paramAnonymousString2);
          String str = localUri.getScheme();
          if ((!str.equals("http")) && (!str.equals("https")))
          {
            Intent localIntent = new Intent();
            localIntent.setData(localUri);
            MunionBrowser.this.getContext().startActivity(localIntent);
            if (!MunionBrowser.this.mWebView.canGoBack())
              MunionBrowser.this.dismiss();
          }
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
    paramWebView.setDownloadListener(new DownloadListener()
    {
      public void onDownloadStart(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4, long paramAnonymousLong)
      {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramAnonymousString1));
        MunionBrowser.this.mContext.startActivity(localIntent);
      }
    });
  }

  public void loadAndShow(String paramString)
  {
    this.a = paramString;
    show();
  }

  public ActionBar newActionLayout()
  {
    ActionBar localActionBar = new ActionBar(getContext());
    localActionBar.setBackgroundColor(-1);
    localActionBar.setGravity(16);
    localActionBar.setOrientation(0);
    localActionBar.setVisibility(8);
    return localActionBar;
  }

  public List<ActionBar.a> newDefaultActions()
  {
    final int j = (int)dipToPixels(this.mContext, 40.0F);
    ArrayList localArrayList = new ArrayList();
    ImageView localImageView1 = new ImageView(this.mContext);
    FrameLayout.LayoutParams localLayoutParams1 = new FrameLayout.LayoutParams(j, j);
    localLayoutParams1.gravity = 17;
    localImageView1.setLayoutParams(localLayoutParams1);
    StateListDrawable localStateListDrawable1 = new StateListDrawable();
    localStateListDrawable1.addState(new int[] { 16842919 }, (Drawable)this.i.a("back_click.png"));
    localStateListDrawable1.addState(new int[] { 16842908 }, (Drawable)this.i.a("back_click.png"));
    localStateListDrawable1.addState(new int[0], (Drawable)this.i.a("back.png"));
    localImageView1.setImageDrawable(localStateListDrawable1);
    a local4 = new a(localImageView1)
    {
      public void a(View paramAnonymousView, WebView paramAnonymousWebView)
      {
        if (paramAnonymousWebView.canGoBack())
        {
          paramAnonymousWebView.goBack();
          return;
        }
        Toast.makeText(MunionBrowser.this.mContext, "已经是第一页了，亲～", 0).show();
      }

      public void a(boolean paramAnonymousBoolean)
      {
        super.a(paramAnonymousBoolean);
        if (Build.VERSION.SDK_INT > 11)
        {
          if (!paramAnonymousBoolean)
            a().setAlpha(0.3F);
        }
        else
          return;
        a().setAlpha(1.0F);
      }
    };
    local4.a(false);
    local4.a("back");
    localArrayList.add(local4);
    ImageView localImageView2 = new ImageView(this.mContext);
    FrameLayout.LayoutParams localLayoutParams2 = new FrameLayout.LayoutParams(j, j);
    localLayoutParams2.gravity = 17;
    localImageView2.setLayoutParams(localLayoutParams2);
    StateListDrawable localStateListDrawable2 = new StateListDrawable();
    localStateListDrawable2.addState(new int[] { 16842919 }, (Drawable)this.i.a("forward_click.png"));
    localStateListDrawable2.addState(new int[] { 16842908 }, (Drawable)this.i.a("forward_click.png"));
    localStateListDrawable2.addState(new int[0], (Drawable)this.i.a("forward.png"));
    localImageView2.setImageDrawable(localStateListDrawable2);
    a local5 = new a(localImageView2)
    {
      public void a(View paramAnonymousView, WebView paramAnonymousWebView)
      {
        if (paramAnonymousWebView.canGoForward())
        {
          paramAnonymousWebView.goForward();
          return;
        }
        Toast.makeText(MunionBrowser.this.mContext, "已经是最后一页了，亲～", 0).show();
      }

      public void a(boolean paramAnonymousBoolean)
      {
        super.a(paramAnonymousBoolean);
        if (Build.VERSION.SDK_INT > 11)
        {
          if (!paramAnonymousBoolean)
            a().setAlpha(0.3F);
        }
        else
          return;
        a().setAlpha(1.0F);
      }
    };
    local5.a(false);
    local5.a("forward");
    localArrayList.add(local5);
    localArrayList.add(new ActionBar.a()
    {
      public View a()
      {
        View localView = new View(MunionBrowser.this.mContext);
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(j, j);
        localLayoutParams.gravity = 17;
        localView.setLayoutParams(localLayoutParams);
        localView.setClickable(false);
        localView.setFocusable(false);
        return localView;
      }

      public void a(View paramAnonymousView, WebView paramAnonymousWebView)
      {
      }
    });
    localArrayList.add(new ActionBar.a()
    {
      public View a()
      {
        ImageView localImageView = new ImageView(MunionBrowser.this.mContext);
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(j, j);
        localLayoutParams.gravity = 17;
        localImageView.setLayoutParams(localLayoutParams);
        StateListDrawable localStateListDrawable = new StateListDrawable();
        localStateListDrawable.addState(new int[] { 16842919 }, (Drawable)MunionBrowser.b(MunionBrowser.this).a("reflush_click.png"));
        localStateListDrawable.addState(new int[] { 16842908 }, (Drawable)MunionBrowser.b(MunionBrowser.this).a("reflush_click.png"));
        localStateListDrawable.addState(new int[0], (Drawable)MunionBrowser.b(MunionBrowser.this).a("reflush.png"));
        localImageView.setImageDrawable(localStateListDrawable);
        return localImageView;
      }

      public void a(View paramAnonymousView, WebView paramAnonymousWebView)
      {
        paramAnonymousWebView.reload();
      }
    });
    localArrayList.add(new ActionBar.a()
    {
      public View a()
      {
        ImageView localImageView = new ImageView(MunionBrowser.this.mContext);
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(j, j);
        localLayoutParams.gravity = 17;
        localImageView.setLayoutParams(localLayoutParams);
        StateListDrawable localStateListDrawable = new StateListDrawable();
        localStateListDrawable.addState(new int[] { 16842919 }, (Drawable)MunionBrowser.b(MunionBrowser.this).a("close_click.png"));
        localStateListDrawable.addState(new int[] { 16842908 }, (Drawable)MunionBrowser.b(MunionBrowser.this).a("close_click.png"));
        localStateListDrawable.addState(new int[0], (Drawable)MunionBrowser.b(MunionBrowser.this).a("close.png"));
        localImageView.setImageDrawable(localStateListDrawable);
        return localImageView;
      }

      public void a(View paramAnonymousView, WebView paramAnonymousWebView)
      {
        MunionBrowser.this.dismiss();
      }
    });
    return localArrayList;
  }

  public void onLoadUrl()
  {
    if (this.b != null)
      this.b.setVisibility(0);
    if (!TextUtils.isEmpty(this.a))
    {
      if ((Build.VERSION.SDK_INT >= 8) && (this.d != null))
        this.mWebView.loadUrl(this.a, this.d);
    }
    else
      return;
    this.mWebView.loadUrl(this.a);
  }

  public MunionBrowser setExtraHeaders(Map<String, String> paramMap)
  {
    this.d.putAll(paramMap);
    return this;
  }

  public MunionBrowser setInterceptBack(boolean paramBoolean)
  {
    this.c = paramBoolean;
    return this;
  }

  static abstract class a
    implements ActionBar.a
  {
    private View a;
    private Object b;

    protected a(View paramView)
    {
      this.a = paramView;
    }

    public final View a()
    {
      return this.a;
    }

    public void a(Object paramObject)
    {
      this.b = paramObject;
    }

    public void a(boolean paramBoolean)
    {
      if (paramBoolean != this.a.isClickable())
      {
        this.a.setClickable(paramBoolean);
        this.a.setFocusable(paramBoolean);
      }
    }

    public Object b()
    {
      return this.b;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.MunionBrowser
 * JD-Core Version:    0.6.2
 */