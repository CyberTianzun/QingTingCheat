package com.taobao.newxp.common.b;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import com.taobao.newxp.Promoter;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.Log;
import com.taobao.newxp.controller.ExchangeDataService;
import com.taobao.newxp.controller.XpListenersCenter.ExchangeDataRequestListener;
import com.taobao.newxp.controller.XpListenersCenter.STATUS;
import com.taobao.newxp.net.MMEntity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class e
{
  public static String a(String paramString)
  {
    if (paramString.contains("/"))
      paramString = paramString.replace("/", "^$^");
    return paramString;
  }

  public static <T extends Promoter> List<T> a(List<T> paramList1, List<T> paramList2)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = paramList2.iterator();
    while (localIterator1.hasNext())
    {
      Promoter localPromoter = (Promoter)localIterator1.next();
      if (!a(localPromoter.app_package_name, paramList1))
        localArrayList.add(localPromoter);
    }
    if (ExchangeConstants.DEBUG_MODE)
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      StringBuilder localStringBuilder2 = new StringBuilder();
      Iterator localIterator2 = localArrayList.iterator();
      while (localIterator2.hasNext())
      {
        localStringBuilder1.append(((Promoter)localIterator2.next()).title);
        localStringBuilder1.append(", ");
      }
      Iterator localIterator3 = paramList2.iterator();
      while (localIterator3.hasNext())
      {
        localStringBuilder2.append(((Promoter)localIterator3.next()).title);
        localStringBuilder2.append(", ");
      }
      Log.c(ExchangeConstants.LOG_TAG, "Showing next page data, before filtered: " + localStringBuilder2.toString());
      Log.c(ExchangeConstants.LOG_TAG, "Showing next page data, after filtered: " + localStringBuilder1.toString());
    }
    return localArrayList;
  }

  public static void a(Context paramContext)
  {
    com.taobao.munion.base.d.a(paramContext);
  }

  public static void a(final ImageView paramImageView1, final ImageView paramImageView2, final Context paramContext, ExchangeDataService paramExchangeDataService, a parama)
  {
    paramExchangeDataService.requestDataAsyn(paramContext, new XpListenersCenter.ExchangeDataRequestListener()
    {
      public void dataReceived(int paramAnonymousInt, final List<Promoter> paramAnonymousList)
      {
        if (paramAnonymousInt == 0)
          this.a.a(0, paramAnonymousList);
        do
        {
          do
            return;
          while (this.b.landing_image == null);
          d.a local1 = new d.a()
          {
            public void a(d.b paramAnonymous2b)
            {
            }

            public void a(XpListenersCenter.STATUS paramAnonymous2STATUS)
            {
              if (paramAnonymous2STATUS == XpListenersCenter.STATUS.SUCCESS)
              {
                if ((e.1.this.b.new_image != null) && (e.1.this.b.new_image.trim().length() > 0))
                {
                  e.1.this.a.a(2, paramAnonymousList);
                  return;
                }
                e.1.this.a.a(1, paramAnonymousList);
                return;
              }
              e.1.this.a.a(0, paramAnonymousList);
            }
          };
          d.a(paramContext, paramImageView1, this.b.landing_image, false, local1, null);
        }
        while ((this.b.new_image == null) || (this.b.new_image.trim().length() <= 0));
        d.a(paramContext, paramImageView2, this.b.new_image, false, null, null);
      }
    }
    , true);
  }

  public static boolean a(int paramInt)
  {
    boolean bool = false;
    if (43 == paramInt);
    try
    {
      Class.forName("com.taobao.newxp.view.largeimage.LargeGallery");
      bool = true;
      return bool;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      Log.e(ExchangeConstants.LOG_TAG, "the LargeImage model is no exist.");
    }
    return false;
  }

  private static boolean a(String paramString, List<? extends Promoter> paramList)
  {
    if ((paramString == null) || (paramList == null) || (paramList.size() < 1))
      return false;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Promoter localPromoter = (Promoter)localIterator.next();
      if ((!TextUtils.isEmpty(localPromoter.app_package_name)) && (paramString.equalsIgnoreCase(localPromoter.app_package_name)))
        return true;
    }
    return false;
  }

  public static boolean a(String paramString, boolean paramBoolean, String[] paramArrayOfString)
  {
    int i = 1;
    if ((paramArrayOfString == null) || (paramArrayOfString.length < i) || (paramString == null))
    {
      i = 0;
      return i;
    }
    int j = paramArrayOfString.length;
    for (int k = 0; ; k++)
    {
      if (k >= j)
        break label72;
      String str = paramArrayOfString[k];
      if (((paramBoolean) && (str.equalsIgnoreCase(paramString))) || ((!paramBoolean) && (str.equals(paramString))))
        break;
    }
    label72: return false;
  }

  public static boolean a(Collection<? extends Promoter> paramCollection, Field paramField, Object paramObject)
  {
    return a(paramCollection, new Field[] { paramField }, new Object[] { paramObject });
  }

  public static boolean a(Collection<? extends Promoter> paramCollection, Field[] paramArrayOfField, Object[] paramArrayOfObject)
  {
    Promoter localPromoter;
    if ((paramCollection != null) && (paramCollection.size() > 0))
    {
      Iterator localIterator = paramCollection.iterator();
      if (localIterator.hasNext())
        localPromoter = (Promoter)localIterator.next();
    }
    while (true)
    {
      int j;
      int m;
      try
      {
        int i = paramArrayOfField.length;
        j = 0;
        int k = 0;
        m = 0;
        if (j < i)
        {
          Field localField = paramArrayOfField[j];
          localField.setAccessible(true);
          if (localField.get(localPromoter) != paramArrayOfObject[m])
            break label114;
          k++;
          break label114;
        }
        int n = paramArrayOfField.length;
        if (k != n)
          break;
        return true;
        return false;
        return false;
      }
      catch (Exception localException)
      {
      }
      break;
      label114: m++;
      j++;
    }
  }

  public static String b(String paramString)
  {
    if (paramString.contains("^$^"))
      paramString = paramString.replace("^$^", "/");
    return paramString;
  }

  public static abstract interface a
  {
    public abstract void a(int paramInt, List<Promoter> paramList);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.b.e
 * JD-Core Version:    0.6.2
 */