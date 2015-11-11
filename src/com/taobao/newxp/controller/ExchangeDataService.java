package com.taobao.newxp.controller;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.taobao.munion.base.a;
import com.taobao.newxp.Promoter;
import com.taobao.newxp.UBroadcastReceiver;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.Log;
import com.taobao.newxp.net.MMEntity;
import com.taobao.newxp.net.s;
import com.taobao.newxp.net.s.a;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class ExchangeDataService
{
  public static int DEBUG_NEW_TIPS = 0;
  protected static final int c = 0;
  protected static final int d = 1;
  private static final com.taobao.newxp.common.c.d h = new com.taobao.newxp.common.c.e();
  private static final String p = "EXCHANGE_PRELOAD_ADS";
  com.taobao.newxp.net.b<MMEntity> a = new com.taobao.newxp.net.b();
  MMEntity b = new MMEntity();
  public boolean broadcast = false;
  public boolean cacheLiveData = false;
  protected String e;
  protected String f = "";
  private final String g = ExchangeDataService.class.getName();
  private int i = -1;
  public XpListenersCenter.InitializeListener initializeListener;
  public boolean isCopyBottom = false;
  private final String j = "PROMOTERS_FIRST_PAGE_";
  private final String k = "PROMOTERS_NEXT_PAGE_";
  private String l = "";
  private String m = "";
  public Context mContext;
  public XpListenersCenter.ExchangeDataRequestListener mDataReceiverListener = null;
  public Class<? extends Promoter> mSpecificPromoterClz;
  private String n = "";
  private e o;
  public boolean pagination = false;
  private String q;
  public boolean show_progress_wheel = true;

  public ExchangeDataService()
  {
    this("");
  }

  public ExchangeDataService(String paramString)
  {
    this.b.slotId = paramString;
  }

  private void a(Context paramContext)
  {
    this.e = ("EXCHANGE_PRELOAD_ADS_" + this.b.keywords + "_" + this.b.autofill);
    String str2;
    if ((TextUtils.isEmpty(this.f)) || (TextUtils.isEmpty(this.l)) || (TextUtils.isEmpty(this.n)) || (TextUtils.isEmpty(this.m)))
    {
      if (!TextUtils.isEmpty(this.b.slotId))
        break label215;
      str2 = this.b.appkey;
      if (TextUtils.isEmpty(str2))
        Log.b(this.g, "No found Slot_id or Appkey!!!!!");
    }
    else
    {
      return;
    }
    this.f = ("PRELOAD_KEY_" + str2);
    this.l = ("PROMOTERS_FIRST_PAGE_" + str2);
    this.n = ("PROMOTERS_NEXT_PAGE_" + str2);
    this.m = ("PRELOAD_UPDATE_DATE_" + str2);
    return;
    label215: String str1 = this.b.slotId;
    this.f = ("PRELOAD_KEY_" + str1);
    this.l = ("PROMOTERS_FIRST_PAGE_" + str1);
    this.n = ("PROMOTERS_NEXT_PAGE_" + str1);
    this.m = ("PRELOAD_UPDATE_DATE_" + str1);
  }

  private void a(XpListenersCenter.ExchangeDataRequestListener paramExchangeDataRequestListener, int paramInt)
  {
    if (paramInt == 1)
    {
      b(paramExchangeDataRequestListener, paramInt);
      return;
    }
    new c(this, paramExchangeDataRequestListener, 0, this.cacheLiveData).a(this.isCopyBottom);
  }

  private void b(final XpListenersCenter.ExchangeDataRequestListener paramExchangeDataRequestListener, int paramInt)
  {
    if (TextUtils.isEmpty(this.b.sid));
    for (final boolean bool = true; ; bool = false)
    {
      new c(this, new XpListenersCenter.ExchangeDataRequestListener()
      {
        private void a(boolean paramAnonymousBoolean)
        {
          try
          {
            MMEntity localMMEntity = (MMEntity)ExchangeDataService.this.b.clone();
            ExchangeDataService localExchangeDataService = new ExchangeDataService();
            localExchangeDataService.mContext = ExchangeDataService.this.mContext;
            localExchangeDataService.setEntity(localMMEntity);
            if (paramAnonymousBoolean);
            synchronized (ExchangeDataService.this.mContext.getSharedPreferences(ExchangeDataService.this.e, 0))
            {
              SharedPreferences.Editor localEditor = ???.edit();
              localEditor.remove(ExchangeDataService.c(ExchangeDataService.this));
              localEditor.commit();
              localMMEntity.sid = "";
              new c(localExchangeDataService, null, 0, true).a();
              return;
            }
          }
          catch (CloneNotSupportedException localCloneNotSupportedException)
          {
            localCloneNotSupportedException.printStackTrace();
          }
        }

        public void dataReceived(int paramAnonymousInt, List<Promoter> paramAnonymousList)
        {
          if (paramAnonymousInt == 1)
          {
            paramExchangeDataRequestListener.dataReceived(paramAnonymousInt, paramAnonymousList);
            a(bool);
            return;
          }
          Log.c(ExchangeDataService.a(ExchangeDataService.this), "Get from cache failed...");
          XpListenersCenter.ExchangeDataRequestListener local1 = new XpListenersCenter.ExchangeDataRequestListener()
          {
            public void dataReceived(int paramAnonymous2Int, List<Promoter> paramAnonymous2List)
            {
              ExchangeDataService.3.this.a.dataReceived(paramAnonymous2Int, paramAnonymous2List);
              if (paramAnonymous2Int == 1)
                ExchangeDataService.3.a(ExchangeDataService.3.this, ExchangeDataService.3.this.b);
            }
          };
          new c(ExchangeDataService.this, local1, 0, false).a();
        }
      }
      , 1, false).a();
      return;
    }
  }

  public static com.taobao.newxp.common.c.d getVerInfo()
  {
    return h;
  }

  protected int a(List<Promoter> paramList)
  {
    if (paramList == null)
    {
      i2 = 0;
      return i2;
    }
    ArrayList localArrayList = new ArrayList();
    int i1 = -1 + paramList.size();
    int i2 = 0;
    label30: boolean bool;
    if (i1 >= 0)
    {
      Promoter localPromoter1 = (Promoter)paramList.get(i1);
      if (this.i > 0)
        if (this.i == 0)
        {
          bool = false;
          label63: if ((localPromoter1 == null) || (!localPromoter1.filterInstalledApp) || (!bool) || (!AlimmContext.getAliContext().getAppUtils().c(localPromoter1.app_package_name)))
            break label263;
          Log.a(ExchangeConstants.LOG_TAG, "Installed: " + ((Promoter)paramList.get(i1)).title + ". Remove from the list.");
          Promoter localPromoter2 = (Promoter)paramList.remove(i1);
          localArrayList.add(localPromoter2);
          if (localPromoter2.new_tip != 1)
            break label263;
        }
    }
    label263: for (int i3 = i2 + 1; ; i3 = i2)
    {
      i1--;
      i2 = i3;
      break label30;
      bool = true;
      break label63;
      bool = this.b.filterInstalledApp;
      break label63;
      if (localArrayList.size() <= 0)
        break;
      new s.a(this.b).a(-1).b(-1).c(-1).a((Promoter[])localArrayList.toArray(new Promoter[0])).a().a();
      return i2;
    }
  }

  protected void a()
  {
  }

  protected void a(final Context paramContext, final XpListenersCenter.ExchangeDataRequestListener paramExchangeDataRequestListener)
  {
    this.mContext = paramContext;
    XpListenersCenter.ExchangeDataRequestListener local2 = new XpListenersCenter.ExchangeDataRequestListener()
    {
      public void dataReceived(int paramAnonymousInt, List<Promoter> paramAnonymousList)
      {
        e locale;
        label109: String str;
        StringBuilder localStringBuilder;
        if ((1 == paramAnonymousInt) && (paramAnonymousList != null) && (paramAnonymousList.size() > 0))
        {
          if (!this.a)
            break label215;
          if (ExchangeDataService.b(ExchangeDataService.this) != null)
            break label200;
          locale = new e(paramContext, ExchangeDataService.this, null);
          locale.a(paramAnonymousList, 1);
          ExchangeDataService.a(ExchangeDataService.this, locale);
          Log.a(ExchangeDataService.a(ExchangeDataService.this), "create new preload data object.[" + paramAnonymousList.size() + "]");
          str = ExchangeDataService.a(ExchangeDataService.this);
          localStringBuilder = new StringBuilder().append("received data ").append(paramAnonymousList.size()).append("   preload is avaliable=");
          if (ExchangeDataService.b(ExchangeDataService.this) == null)
            break label278;
        }
        label278: for (boolean bool = ExchangeDataService.b(ExchangeDataService.this).b(); ; bool = false)
        {
          Log.a(str, bool);
          if (paramExchangeDataRequestListener != null)
            paramExchangeDataRequestListener.dataReceived(paramAnonymousInt, paramAnonymousList);
          return;
          label200: locale = ExchangeDataService.b(ExchangeDataService.this).d();
          break;
          label215: if (ExchangeDataService.b(ExchangeDataService.this) == null)
            break label109;
          ExchangeDataService.b(ExchangeDataService.this).a(paramAnonymousList);
          Log.a(ExchangeDataService.a(ExchangeDataService.this), "add extend promoter data..[" + paramAnonymousList.size() + "]");
          break label109;
        }
      }
    };
    a(paramContext);
    if ((ExchangeConstants.ONLY_CHINESE) && (!AlimmContext.getAliContext().getAppUtils().e()))
    {
      Log.b(ExchangeConstants.LOG_TAG, "English os can not show ads");
      local2.dataReceived(0, null);
      if (this.mDataReceiverListener != null)
        this.mDataReceiverListener.dataReceived(0, null);
      return;
    }
    a(local2, paramContext.getSharedPreferences(this.e, 0).getInt(this.f, 0));
  }

  protected boolean a(boolean paramBoolean, JSONObject paramJSONObject)
  {
    SharedPreferences localSharedPreferences = this.mContext.getSharedPreferences(this.e, 0);
    if (paramJSONObject != null)
    {
      Log.a(this.g, "save json to cache....");
      try
      {
        SharedPreferences.Editor localEditor = localSharedPreferences.edit();
        localEditor.putLong(this.m, System.currentTimeMillis());
        if (paramBoolean)
          localEditor.putString(this.l, paramJSONObject.toString());
        while (true)
        {
          localEditor.commit();
          return true;
          localEditor.putString(this.n, paramJSONObject.toString());
        }
      }
      finally
      {
      }
    }
    return false;
  }

  public void clickOnPromoter(Activity paramActivity, Promoter paramPromoter)
  {
    b.a(paramPromoter, paramActivity, this, false);
  }

  public void clickOnPromoter(Activity paramActivity, Promoter paramPromoter, boolean paramBoolean)
  {
    b.a(paramPromoter, paramActivity, this, paramBoolean);
  }

  public String getBroadCastAction(Context paramContext)
  {
    if (TextUtils.isEmpty(this.q))
      if (!TextUtils.isEmpty(this.b.slotId))
        break label109;
    label109: for (String str1 = this.b.appkey; ; str1 = this.b.slotId)
    {
      String str2 = com.taobao.newxp.common.b.b.a(str1);
      this.q = (AlimmContext.getAliContext().getAppUtils().g() + "." + str2);
      Log.c(this.g, "Get BroadcastAction " + this.q);
      return this.q;
    }
  }

  public MMEntity getEntity()
  {
    return this.b;
  }

  public e getPreloadData()
  {
    return this.o;
  }

  public com.taobao.newxp.net.b<MMEntity> getProvider()
  {
    return this.a;
  }

  public List<String> onUpload()
  {
    return null;
  }

  public void preloadData(Context paramContext, XpListenersCenter.NTipsChangedListener paramNTipsChangedListener, int paramInt)
  {
    preloadData(paramContext, null, paramNTipsChangedListener, paramInt);
  }

  public void preloadData(Context paramContext, List<Promoter> paramList, XpListenersCenter.NTipsChangedListener paramNTipsChangedListener, int paramInt)
  {
    preloadData(paramContext, paramList, paramNTipsChangedListener, paramInt, null);
  }

  public void preloadData(Context paramContext, List<Promoter> paramList, XpListenersCenter.NTipsChangedListener paramNTipsChangedListener, int paramInt, Class<? extends Promoter> paramClass)
  {
    if ((this.b.layoutType > -1) && (this.b.layoutType != paramInt))
    {
      Log.e(this.g, "sorry  type is no match ");
      return;
    }
    this.b.layoutType = paramInt;
    if (this.o == null)
      this.o = new e(paramContext, this, paramNTipsChangedListener);
    while ((paramList != null) && (paramList.size() > 0))
    {
      this.o.a(paramList);
      return;
      this.o.a(paramNTipsChangedListener);
    }
    this.o.a(paramClass);
  }

  public void registerBroadcast(Context paramContext, UBroadcastReceiver paramUBroadcastReceiver)
  {
    this.broadcast = true;
    IntentFilter localIntentFilter = new IntentFilter(getBroadCastAction(paramContext));
    localIntentFilter.setPriority(1000);
    paramContext.registerReceiver(paramUBroadcastReceiver, localIntentFilter);
  }

  public void removeCache()
  {
    Log.a(this.g, "remove cache....[" + this.e + "]");
    synchronized (this.mContext.getSharedPreferences(this.e, 0))
    {
      SharedPreferences.Editor localEditor = ???.edit();
      localEditor.remove(this.l);
      localEditor.remove(this.n);
      localEditor.remove(this.m);
      localEditor.commit();
      return;
    }
  }

  public void reportImpression(Promoter[] paramArrayOfPromoter)
  {
    if ((paramArrayOfPromoter == null) || (paramArrayOfPromoter.length == 0))
    {
      String str = this.g;
      StringBuilder localStringBuilder = new StringBuilder().append("unable send impression report.[promoters=");
      int i1 = 0;
      if (paramArrayOfPromoter == null);
      while (true)
      {
        Log.e(str, i1 + "]");
        return;
        i1 = paramArrayOfPromoter.length;
      }
    }
    new s.a(this.b).a(0).b(0).c(3).a(paramArrayOfPromoter).a().a();
  }

  public void reportNoMatchImage(Promoter[] paramArrayOfPromoter)
  {
    if ((paramArrayOfPromoter == null) || (paramArrayOfPromoter.length == 0))
    {
      String str = this.g;
      StringBuilder localStringBuilder = new StringBuilder().append("unable send impression report.[promoters=");
      if (paramArrayOfPromoter == null);
      for (int i1 = 0; ; i1 = paramArrayOfPromoter.length)
      {
        Log.e(str, i1 + "]");
        return;
      }
    }
    Log.a(this.g, "report nomatch");
    new s.a(this.b).a(-4).a(paramArrayOfPromoter).a().a();
  }

  public List<Promoter> requestCache(boolean paramBoolean1, boolean paramBoolean2)
  {
    try
    {
      SharedPreferences localSharedPreferences = this.mContext.getSharedPreferences(this.e, 0);
      String str1;
      if (paramBoolean1)
      {
        Log.c(this.g, "Request data from first-cache..");
        Long localLong = Long.valueOf(localSharedPreferences.getLong(this.m, 0L));
        boolean bool = (System.currentTimeMillis() - localLong.longValue()) / 1000L < -3600 + 86400 * this.b.sid_expired;
        int i1 = 0;
        if (bool)
          i1 = 1;
        if (i1 != 0)
        {
          removeCache();
          Log.e(this.g, "Cache data is inactivation...");
          return null;
        }
        str1 = this.l;
      }
      while (true)
      {
        String str2 = localSharedPreferences.getString(str1, null);
        if (str2 != null)
        {
          if (paramBoolean2);
          try
          {
            SharedPreferences.Editor localEditor = localSharedPreferences.edit();
            localEditor.remove(str1);
            localEditor.commit();
            Log.a(this.g, "destroy the used cache data.");
            JSONObject localJSONObject = new JSONObject(str2);
            ArrayList localArrayList = new ArrayList();
            this.a.a(this.b, localArrayList, this.mSpecificPromoterClz, localJSONObject);
            if ((localArrayList != null) && (localArrayList.size() > 0))
            {
              return localArrayList;
              Log.c(this.g, "Request data from second-cache..");
              str1 = this.n;
            }
          }
          finally
          {
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public void requestDataAsyn(Context paramContext, XpListenersCenter.ExchangeDataRequestListener paramExchangeDataRequestListener)
  {
    AlimmContext.getAliContext().init(paramContext);
    this.mContext = paramContext;
    if (TextUtils.isEmpty(this.b.slotId));
    for (String str1 = this.b.appkey; ; str1 = this.b.slotId)
    {
      TabsDiskCache.a(this.mContext, str1).b();
      if (h != null)
        h.a(paramContext);
      a();
      com.taobao.newxp.common.b.e.a(paramContext);
      if ((this.o == null) || (!this.o.b()) || (!TextUtils.isEmpty(this.b.sid)))
        break;
      List localList = this.o.a();
      Log.a(this.g, "get data from preloadData." + localList.size());
      if ((localList == null) || (paramExchangeDataRequestListener == null))
        break;
      paramExchangeDataRequestListener.dataReceived(1, localList);
      return;
    }
    String str2 = this.g;
    StringBuilder localStringBuilder = new StringBuilder().append("get data from requestData. mem-preload:");
    if (this.o == null);
    for (String str3 = "null"; ; str3 = " isAvailable=" + this.o.b())
    {
      Log.a(str2, str3);
      a(this.mContext, paramExchangeDataRequestListener);
      return;
    }
  }

  public void requestDataAsyn(Context paramContext, XpListenersCenter.ExchangeDataRequestListener paramExchangeDataRequestListener, boolean paramBoolean)
  {
    if (paramBoolean)
      this.b.clear();
    requestDataAsyn(paramContext, paramExchangeDataRequestListener);
  }

  public void requestRichImageDataAsyn(Context paramContext, int paramInt, final XpListenersCenter.ExchangeDataRequestListener paramExchangeDataRequestListener, boolean paramBoolean)
  {
    this.b.layoutType = paramInt;
    requestDataAsyn(paramContext, new XpListenersCenter.ExchangeDataRequestListener()
    {
      public void dataReceived(final int paramAnonymousInt, final List<Promoter> paramAnonymousList)
      {
        if (paramAnonymousList == null)
        {
          if (paramExchangeDataRequestListener != null)
            paramExchangeDataRequestListener.dataReceived(paramAnonymousInt, paramAnonymousList);
          return;
        }
        new AsyncTask()
        {
          protected List<Promoter> a(Void[] paramAnonymous2ArrayOfVoid)
          {
            ArrayList localArrayList = new ArrayList();
            Iterator localIterator = paramAnonymousList.iterator();
            while (localIterator.hasNext())
            {
              Promoter localPromoter = (Promoter)localIterator.next();
              if (!TextUtils.isEmpty(localPromoter.img))
              {
                String str;
                try
                {
                  File localFile3 = com.taobao.newxp.common.b.d.b(ExchangeDataService.this.mContext, localPromoter.img);
                  localFile1 = localFile3;
                  if ((localFile1 != null) && (localFile1.exists()))
                    localArrayList.add(localPromoter);
                }
                catch (IOException localIOException)
                {
                  while (true)
                  {
                    Log.b(ExchangeDataService.a(ExchangeDataService.this), "", localIOException);
                    File localFile1 = null;
                  }
                  str = com.taobao.newxp.common.b.d.a(ExchangeDataService.this.mContext, localPromoter.img);
                }
                if (!TextUtils.isEmpty(str))
                {
                  File localFile2 = new File(str);
                  if ((localFile2 != null) && (localFile2.exists()))
                    localArrayList.add(localPromoter);
                }
              }
            }
            return localArrayList;
          }

          protected void a(List<Promoter> paramAnonymous2List)
          {
            if (ExchangeDataService.1.this.a != null)
              ExchangeDataService.1.this.a.dataReceived(paramAnonymousInt, paramAnonymous2List);
          }
        }
        .execute(new Void[0]);
      }
    }
    , paramBoolean);
  }

  public void setCopyBottom(boolean paramBoolean)
  {
    this.isCopyBottom = paramBoolean;
  }

  public void setEntity(MMEntity paramMMEntity)
  {
    this.b = paramMMEntity;
    a(AlimmContext.getAliContext().getAppContext());
  }

  public void setFilterInstalledApp(boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i1 = 1; ; i1 = 0)
    {
      this.i = i1;
      return;
    }
  }

  public void setPreloadData(e parame)
  {
    this.o = parame;
    if (this.o != null)
      this.o.a = this;
  }

  public void setProvider(com.taobao.newxp.net.b<MMEntity> paramb)
  {
    this.a = paramb;
  }

  public void setSpecificPromoterClz(Class<? extends Promoter> paramClass)
  {
    this.mSpecificPromoterClz = paramClass;
  }

  public void setTag(String paramString)
  {
    this.b.tag = paramString;
  }

  public void setTemplateAttrs(String paramString)
  {
    this.b.templateAttrs = paramString;
  }

  public void unregisterBroadcast(Context paramContext, UBroadcastReceiver paramUBroadcastReceiver)
  {
    paramContext.unregisterReceiver(paramUBroadcastReceiver);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.controller.ExchangeDataService
 * JD-Core Version:    0.6.2
 */