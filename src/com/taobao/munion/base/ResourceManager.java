package com.taobao.munion.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager
{
  private static final String[] b = { "back.png", "back_click.png", "close.png", "close_click.png", "forward.png", "forward_click.png", "reflush.png", "reflush_click.png", "closebox.png", "closebox_click.png" };
  Map<String, Object> a = new HashMap();
  private h c = new h();
  private Context d;

  private Drawable b(String paramString)
  {
    try
    {
      Drawable localDrawable = Drawable.createFromStream(this.c.b(this.d, paramString), paramString);
      return localDrawable;
    }
    catch (IOException localIOException)
    {
      Log.e(localIOException, "", new Object[0]);
    }
    return null;
  }

  public Object a(String paramString)
  {
    Object localObject = this.a.get(paramString);
    if (localObject == null)
      localObject = b(paramString);
    return localObject;
  }

  public void init(Context paramContext)
  {
    this.d = paramContext;
    for (String str : b)
    {
      Drawable localDrawable = b(str);
      if (localDrawable != null)
        this.a.put(str, localDrawable);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.ResourceManager
 * JD-Core Version:    0.6.2
 */