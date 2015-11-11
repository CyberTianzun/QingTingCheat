package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

public abstract class b extends Dialog
{
  protected a jsBridge;

  @SuppressLint({"NewApi"})
  protected final WebChromeClient mChromeClient = new WebChromeClient()
  {
    public void onConsoleMessage(String paramAnonymousString1, int paramAnonymousInt, String paramAnonymousString2)
    {
      Log.i("WebConsole", paramAnonymousString1 + " -- From 222 line " + paramAnonymousInt + " of " + paramAnonymousString2);
      if (Build.VERSION.SDK_INT == 7)
        b.this.onConsoleMessage(paramAnonymousString1);
    }

    public boolean onConsoleMessage(ConsoleMessage paramAnonymousConsoleMessage)
    {
      Log.i("WebConsole", paramAnonymousConsoleMessage.message() + " -- From  111 line " + paramAnonymousConsoleMessage.lineNumber() + " of " + paramAnonymousConsoleMessage.sourceId());
      b localb;
      if (Build.VERSION.SDK_INT > 7)
      {
        localb = b.this;
        if (paramAnonymousConsoleMessage != null)
          break label76;
      }
      label76: for (String str = ""; ; str = paramAnonymousConsoleMessage.message())
      {
        localb.onConsoleMessage(str);
        return true;
      }
    }
  };

  public b(Context paramContext)
  {
    super(paramContext);
  }

  public b(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }

  protected abstract void onConsoleMessage(String paramString);

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.jsBridge = new a();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.b
 * JD-Core Version:    0.6.2
 */