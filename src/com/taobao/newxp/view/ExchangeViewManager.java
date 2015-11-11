package com.taobao.newxp.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager.BadTokenException;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.taobao.newxp.Promoter;
import com.taobao.newxp.Promoter.a;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.Category;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.a.a.f.a;
import com.taobao.newxp.controller.ExchangeDataService;
import com.taobao.newxp.controller.TabsDiskCache;
import com.taobao.newxp.controller.XpListenersCenter.AdapterListener;
import com.taobao.newxp.controller.XpListenersCenter.EntryOnClickListener;
import com.taobao.newxp.controller.XpListenersCenter.ExchangeDataRequestListener;
import com.taobao.newxp.controller.XpListenersCenter.FloatDialogListener;
import com.taobao.newxp.controller.XpListenersCenter.NTipsChangedListener;
import com.taobao.newxp.controller.XpListenersCenter.WelcomeAdsListener;
import com.taobao.newxp.controller.XpListenersCenter.onHandleVisListener;
import com.taobao.newxp.net.MMEntity;
import com.taobao.newxp.net.s;
import com.taobao.newxp.net.s.a;
import com.taobao.newxp.net.s.b;
import com.taobao.newxp.view.common.UMBrowser;
import com.taobao.newxp.view.container.GridTemplate;
import com.taobao.newxp.view.container.GridTemplateConfig;
import com.taobao.newxp.view.handler.UMHandleRelativeLayout;
import com.taobao.newxp.view.handler.umwall.AlimamaWall;
import com.taobao.newxp.view.largeimage.LargeGallery;
import com.taobao.newxp.view.largeimage.LargeGallery.a;
import com.taobao.newxp.view.largeimage.LargeGalleryConfig;
import com.taobao.newxp.view.welcome.UMWelcomeDialog;
import com.taobao.newxp.view.welcome.UMWelcomePromoter;
import com.taobao.newxp.view.welcome.WelcomeView;
import com.taobao.newxp.view.widget.SwipeViewPointer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeViewManager
{
  Context a;
  int b;
  XpListenersCenter.EntryOnClickListener c;
  private final String d = ExchangeViewManager.class.getName();
  private ExHeader e = null;
  private final Map<String, d> f = new HashMap();
  private ExchangeDataService g;
  private UMBrowser h;
  private ImageView i;
  private View j;
  private View k;
  private TextView l;
  private TextView m;
  private ImageView n;
  private volatile boolean o = false;

  public ExchangeViewManager(Context paramContext)
  {
    this(paramContext, new ExchangeDataService());
  }

  public ExchangeViewManager(Context paramContext, ExchangeDataService paramExchangeDataService)
  {
    this.a = paramContext;
    if (paramExchangeDataService == null);
    for (this.g = new ExchangeDataService(); ; this.g = paramExchangeDataService)
    {
      this.g.getEntity().layoutType = 7;
      return;
    }
  }

  private <T extends d> T getFeatureConfig(Class<T> paramClass)
  {
    String str = paramClass.getSimpleName();
    if (this.f.containsKey(str))
    {
      com.taobao.newxp.common.Log.a(this.d, "has exist config " + str);
      return (d)this.f.get(str);
    }
    return null;
  }

  private void matchHandlerList(final XpListenersCenter.NTipsChangedListener paramNTipsChangedListener, final XpListenersCenter.onHandleVisListener paramonHandleVisListener, Drawable paramDrawable)
  {
    // Byte code:
    //   0: aload_3
    //   1: ifnonnull +60 -> 61
    //   4: aload_0
    //   5: getfield 99	com/taobao/newxp/view/ExchangeViewManager:k	Landroid/view/View;
    //   8: iconst_4
    //   9: invokevirtual 155	android/view/View:setVisibility	(I)V
    //   12: aload_0
    //   13: getfield 99	com/taobao/newxp/view/ExchangeViewManager:k	Landroid/view/View;
    //   16: iconst_0
    //   17: invokevirtual 159	android/view/View:setClickable	(Z)V
    //   20: aload_0
    //   21: getfield 64	com/taobao/newxp/view/ExchangeViewManager:g	Lcom/taobao/newxp/controller/ExchangeDataService;
    //   24: invokevirtual 68	com/taobao/newxp/controller/ExchangeDataService:getEntity	()Lcom/taobao/newxp/net/MMEntity;
    //   27: astore 5
    //   29: aload_0
    //   30: getfield 138	com/taobao/newxp/view/ExchangeViewManager:i	Landroid/widget/ImageView;
    //   33: aload_0
    //   34: getfield 93	com/taobao/newxp/view/ExchangeViewManager:n	Landroid/widget/ImageView;
    //   37: aload_0
    //   38: getfield 62	com/taobao/newxp/view/ExchangeViewManager:a	Landroid/content/Context;
    //   41: aload_0
    //   42: getfield 64	com/taobao/newxp/view/ExchangeViewManager:g	Lcom/taobao/newxp/controller/ExchangeDataService;
    //   45: new 161	com/taobao/newxp/view/ExchangeViewManager$6
    //   48: dup
    //   49: aload_0
    //   50: aload 5
    //   52: aload_2
    //   53: aload_1
    //   54: invokespecial 164	com/taobao/newxp/view/ExchangeViewManager$6:<init>	(Lcom/taobao/newxp/view/ExchangeViewManager;Lcom/taobao/newxp/net/MMEntity;Lcom/taobao/newxp/controller/XpListenersCenter$onHandleVisListener;Lcom/taobao/newxp/controller/XpListenersCenter$NTipsChangedListener;)V
    //   57: invokestatic 169	com/taobao/newxp/common/b/e:a	(Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/content/Context;Lcom/taobao/newxp/controller/ExchangeDataService;Lcom/taobao/newxp/common/b/e$a;)V
    //   60: return
    //   61: aload_0
    //   62: getfield 138	com/taobao/newxp/view/ExchangeViewManager:i	Landroid/widget/ImageView;
    //   65: aload_3
    //   66: invokevirtual 175	android/widget/ImageView:setImageDrawable	(Landroid/graphics/drawable/Drawable;)V
    //   69: new 177	com/taobao/newxp/view/ExchangeViewManager$7
    //   72: dup
    //   73: aload_0
    //   74: invokespecial 179	com/taobao/newxp/view/ExchangeViewManager$7:<init>	(Lcom/taobao/newxp/view/ExchangeViewManager;)V
    //   77: astore 4
    //   79: aload_0
    //   80: getfield 99	com/taobao/newxp/view/ExchangeViewManager:k	Landroid/view/View;
    //   83: instanceof 181
    //   86: ifeq +16 -> 102
    //   89: aload_0
    //   90: getfield 99	com/taobao/newxp/view/ExchangeViewManager:k	Landroid/view/View;
    //   93: checkcast 181	com/taobao/newxp/view/handler/UMHandleRelativeLayout
    //   96: aload 4
    //   98: invokevirtual 185	com/taobao/newxp/view/handler/UMHandleRelativeLayout:setClickRunnable	(Ljava/lang/Runnable;)V
    //   101: return
    //   102: aload_0
    //   103: getfield 99	com/taobao/newxp/view/ExchangeViewManager:k	Landroid/view/View;
    //   106: new 187	com/taobao/newxp/view/ExchangeViewManager$8
    //   109: dup
    //   110: aload_0
    //   111: aload 4
    //   113: invokespecial 190	com/taobao/newxp/view/ExchangeViewManager$8:<init>	(Lcom/taobao/newxp/view/ExchangeViewManager;Ljava/lang/Runnable;)V
    //   116: invokevirtual 194	android/view/View:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   119: return
  }

  private void pushMsgDialog(final Promoter paramPromoter, final XpListenersCenter.FloatDialogListener paramFloatDialogListener)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.a);
    localBuilder.setMessage(paramPromoter.title);
    localBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        String str = Uri.parse(paramPromoter.url).getScheme();
        paramAnonymousDialogInterface.dismiss();
        if ((paramFloatDialogListener != null) && (str.equalsIgnoreCase(Promoter.a.b.toString())))
        {
          paramFloatDialogListener.onConfirmClickWithCallBackUrl(paramPromoter.url);
          s.a locala = new s.a(ExchangeViewManager.f(ExchangeViewManager.this).getEntity()).a(2).b(0).c(3);
          Promoter[] arrayOfPromoter = new Promoter[1];
          arrayOfPromoter[0] = paramPromoter;
          locala.a(arrayOfPromoter).a().a();
          return;
        }
        com.taobao.newxp.controller.b.a(paramPromoter, ExchangeViewManager.this.a, ExchangeViewManager.f(ExchangeViewManager.this), false);
      }
    });
    localBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    });
    try
    {
      localBuilder.create().show();
      new s.a(this.g.getEntity()).a(0).b(0).c(0).a(new Promoter[] { paramPromoter }).a().a();
      return;
    }
    catch (WindowManager.BadTokenException localBadTokenException)
    {
      com.taobao.newxp.common.Log.e(this.d, "Can`t show dialog ", localBadTokenException);
    }
  }

  private void reportAclick()
  {
    if (7 == this.g.getEntity().layoutType)
      new s.b(this.g.getEntity()).a(15).b(0).c(3).a().a();
  }

  private void reportApv()
  {
    new s.b(this.g.getEntity()).a(14).b(0).c(3).a().a();
  }

  private void showFirst(UMWelcomePromoter paramUMWelcomePromoter, final XpListenersCenter.WelcomeAdsListener paramWelcomeAdsListener)
  {
    final UMWelcomeDialog localUMWelcomeDialog = new UMWelcomeDialog(this.a);
    WelcomeView local4 = new WelcomeView(this.a)
    {
      public void onCountdown(int paramAnonymousInt, View paramAnonymousView1, View paramAnonymousView2)
      {
        super.onCountdown(paramAnonymousInt, paramAnonymousView1, paramAnonymousView2);
        if (paramWelcomeAdsListener != null)
          paramWelcomeAdsListener.onCountdown(paramAnonymousInt);
      }

      public void onError(String paramAnonymousString)
      {
        super.onError(paramAnonymousString);
        try
        {
          if (localUMWelcomeDialog.isShowing())
            localUMWelcomeDialog.dismiss();
          if (paramWelcomeAdsListener != null)
          {
            paramWelcomeAdsListener.onError(paramAnonymousString);
            paramWelcomeAdsListener.onFinish();
          }
          return;
        }
        catch (WindowManager.BadTokenException localBadTokenException)
        {
          while (true)
            android.util.Log.e(ExchangeViewManager.d(ExchangeViewManager.this), "can`t open welcome ads,the parent activity has finished.", localBadTokenException);
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          while (true)
            android.util.Log.e(ExchangeViewManager.d(ExchangeViewManager.this), "can`t open welcome ads,the parent activity has finished.", localIllegalArgumentException);
        }
      }

      public void onFinish(WelcomeView paramAnonymousWelcomeView)
      {
        try
        {
          if (localUMWelcomeDialog.isShowing())
            localUMWelcomeDialog.dismiss();
          super.onFinish(paramAnonymousWelcomeView);
          if (paramWelcomeAdsListener != null)
            paramWelcomeAdsListener.onFinish();
          return;
        }
        catch (WindowManager.BadTokenException localBadTokenException)
        {
          while (true)
            android.util.Log.e(ExchangeViewManager.d(ExchangeViewManager.this), "can`t open welcome ads,the parent activity has finished.", localBadTokenException);
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          while (true)
            android.util.Log.e(ExchangeViewManager.d(ExchangeViewManager.this), "can`t open welcome ads,the parent activity has finished.", localIllegalArgumentException);
        }
      }
    };
    local4.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    if (local4.loaded(paramUMWelcomePromoter))
    {
      localUMWelcomeDialog.setContentView(local4);
      try
      {
        localUMWelcomeDialog.show();
        this.g.reportImpression(new Promoter[] { paramUMWelcomePromoter });
        if (paramWelcomeAdsListener != null)
          paramWelcomeAdsListener.onShow(local4);
        return;
      }
      catch (WindowManager.BadTokenException localBadTokenException)
      {
        while (true)
          android.util.Log.e(this.d, "can`t open welcome ads,the parent activity has finished.", localBadTokenException);
      }
    }
    paramWelcomeAdsListener.onError("the promoter is failed");
    paramWelcomeAdsListener.onFinish();
  }

  private void welcomeDataReceived(final XpListenersCenter.WelcomeAdsListener paramWelcomeAdsListener, long paramLong1, long paramLong2, long paramLong3, boolean paramBoolean, List<Promoter> paramList)
  {
    Handler localHandler = new Handler();
    this.o = true;
    long l1 = System.currentTimeMillis() - paramLong3;
    if ((paramList == null) || (paramList.size() < 1))
      if (paramWelcomeAdsListener != null)
      {
        if (l1 < paramLong1)
          localHandler.postDelayed(new Runnable()
          {
            public void run()
            {
              paramWelcomeAdsListener.onDataReviced(null);
              paramWelcomeAdsListener.onFinish();
            }
          }
          , paramLong1 - l1);
      }
      else
        android.util.Log.w(ExchangeConstants.LOG_TAG, "unshow welcome dialog,there is no promoter data.");
    do
    {
      return;
      paramWelcomeAdsListener.onDataReviced(null);
      paramWelcomeAdsListener.onFinish();
      break;
      final UMWelcomePromoter localUMWelcomePromoter = (UMWelcomePromoter)paramList.get(0);
      if (paramWelcomeAdsListener != null)
        paramWelcomeAdsListener.onDataReviced(localUMWelcomePromoter);
      if (paramBoolean)
      {
        showFirst(localUMWelcomePromoter, paramWelcomeAdsListener);
        return;
      }
      if (l1 < paramLong1)
      {
        localHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            ExchangeViewManager.a(ExchangeViewManager.this, localUMWelcomePromoter, paramWelcomeAdsListener);
          }
        }
        , paramLong1 - l1);
        return;
      }
      if (l1 < paramLong2)
      {
        showFirst(localUMWelcomePromoter, paramWelcomeAdsListener);
        return;
      }
      android.util.Log.w(ExchangeConstants.LOG_TAG, "Load outdated..");
    }
    while (paramWelcomeAdsListener == null);
    paramWelcomeAdsListener.onError("Load outdated..");
    paramWelcomeAdsListener.onFinish();
  }

  public void addView(int paramInt, View paramView, Object[] paramArrayOfObject)
  {
    Object localObject1 = null;
    if ((7 == paramInt) && (!AlimmContext.getAliContext().getAppUtils().a(AlimamaWall.class)))
    {
      Toast.makeText(AlimmContext.getAliContext().getAppContext(), "请确认是否在Manifest文件中已添加 ‘com.taobao.newxp.view.handler.umwall.UMWall’。", 1).show();
      return;
    }
    while (true)
    {
      int i2;
      try
      {
        this.g.getEntity().layoutType = paramInt;
        int i1 = paramArrayOfObject.length;
        i2 = 0;
        localObject2 = null;
        if (i2 < i1)
        {
          Object localObject3 = paramArrayOfObject[i2];
          if ((localObject3 instanceof Drawable))
          {
            Drawable localDrawable = (Drawable)localObject3;
            Object localObject6 = localObject1;
            localObject5 = localDrawable;
            localObject4 = localObject6;
            break label494;
          }
          if (!(localObject3 instanceof XpListenersCenter.onHandleVisListener))
            break label486;
          localObject4 = (XpListenersCenter.onHandleVisListener)localObject3;
          localObject5 = localObject2;
          break label494;
        }
        if (paramView != null)
          break label273;
        com.taobao.munion.base.a locala = AlimmContext.getAliContext().getAppUtils();
        if ((locala.e("android.permission.ACCESS_NETWORK_STATE")) && (!locala.c()))
        {
          Toast.makeText(this.a, this.a.getResources().getString(com.taobao.newxp.a.e.a(this.a)), 1).show();
          return;
        }
      }
      catch (Exception localException)
      {
        android.util.Log.e(ExchangeConstants.LOG_TAG, "添加推广样式失败！", localException);
        return;
      }
      switch (paramInt)
      {
      default:
        return;
      case 7:
      }
      this.g.getEntity().template = com.taobao.newxp.b.a;
      MMEntity localMMEntity = this.g.getEntity();
      com.taobao.newxp.controller.e locale = this.g.getPreloadData();
      com.taobao.newxp.view.handler.b.a(this.a, localMMEntity, locale, null);
      return;
      label273: if ((paramView instanceof ImageView))
      {
        this.i = ((ImageView)paramView);
        this.k = paramView;
      }
      while (true)
      {
        XpListenersCenter.NTipsChangedListener local5 = new XpListenersCenter.NTipsChangedListener()
        {
          public void onChanged(int paramAnonymousInt)
          {
            if (paramAnonymousInt == 0);
            try
            {
              ExchangeViewManager.a(ExchangeViewManager.this).setVisibility(0);
              ExchangeViewManager.b(ExchangeViewManager.this).setImageResource(com.taobao.newxp.a.b.c(ExchangeViewManager.this.a));
              ExchangeViewManager.c(ExchangeViewManager.this).setText("");
              ExchangeViewManager.c(ExchangeViewManager.this).setBackgroundDrawable(null);
              return;
              if (paramAnonymousInt > 0)
              {
                ExchangeViewManager.b(ExchangeViewManager.this).setImageDrawable(null);
                ExchangeViewManager.c(ExchangeViewManager.this).setBackgroundResource(com.taobao.newxp.a.b.d(ExchangeViewManager.this.a));
                ExchangeViewManager.c(ExchangeViewManager.this).setText("" + paramAnonymousInt);
                ExchangeViewManager.a(ExchangeViewManager.this).setVisibility(0);
                return;
              }
            }
            catch (Exception localException)
            {
              com.taobao.newxp.common.Log.e(ExchangeViewManager.d(ExchangeViewManager.this), "", localException);
              return;
            }
            ExchangeViewManager.a(ExchangeViewManager.this).setVisibility(4);
          }
        };
        this.b = paramInt;
        switch (paramInt)
        {
        case 7:
          matchHandlerList(local5, (XpListenersCenter.onHandleVisListener)localObject1, (Drawable)localObject2);
          return;
          if ((paramView instanceof RelativeLayout))
          {
            this.k = paramView;
            this.i = ((ImageView)this.k.findViewById(com.taobao.newxp.common.b.c.a(this.a).b("imageview")));
            this.j = this.k.findViewById(com.taobao.newxp.common.b.c.a(this.a).b("newtip_area"));
            this.l = ((TextView)this.k.findViewById(com.taobao.newxp.common.b.c.a(this.a).b("newtip_tv")));
            this.n = ((ImageView)this.k.findViewById(com.taobao.newxp.common.b.c.a(this.a).b("newtip_iv")));
            this.m = ((TextView)this.k.findViewById(com.taobao.newxp.common.b.c.a(this.a).b("textview")));
          }
          break;
        }
      }
      label486: Object localObject4 = localObject1;
      Object localObject5 = localObject2;
      label494: i2++;
      Object localObject2 = localObject5;
      localObject1 = localObject4;
    }
  }

  public void addView(ViewGroup paramViewGroup, int paramInt, String[] paramArrayOfString)
  {
    this.g.getEntity().layoutType = paramInt;
    this.b = paramInt;
    if (paramArrayOfString != null);
    try
    {
      if ((paramArrayOfString.length > 0) && (paramArrayOfString.length >= 1))
        ExchangeConstants.CHANNEL = paramArrayOfString[0];
      if ((ExchangeConstants.ONLY_CHINESE) && (!AlimmContext.getAliContext().getAppUtils().e()))
      {
        com.taobao.newxp.common.Log.b(ExchangeConstants.LOG_TAG, "Only chinese language os can show ads");
        return;
      }
      if (this.b == 8)
      {
        GridTemplateConfig localGridTemplateConfig = (GridTemplateConfig)getFeatureConfig(GridTemplateConfig.class);
        paramViewGroup.addView(new GridTemplate(null, this.g, this.a, localGridTemplateConfig).contentView);
        return;
      }
    }
    catch (Exception localException)
    {
      com.taobao.newxp.common.Log.b(ExchangeConstants.LOG_TAG, "add view error " + localException.getMessage(), localException);
      return;
    }
    LargeGalleryConfig localLargeGalleryConfig1;
    switch (this.b)
    {
    default:
      String str = ExchangeConstants.LOG_TAG;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.b);
      com.taobao.newxp.common.Log.b(str, String.format("Paramter type  %1$s cannot be handled. It may be deprecated.", arrayOfObject));
      return;
    case 43:
      localLargeGalleryConfig1 = (LargeGalleryConfig)getFeatureConfig(LargeGalleryConfig.class);
      if (localLargeGalleryConfig1 != null)
        break;
    case 13:
    }
    for (LargeGalleryConfig localLargeGalleryConfig2 = new LargeGalleryConfig(); ; localLargeGalleryConfig2 = localLargeGalleryConfig1)
    {
      LayoutInflater localLayoutInflater = (LayoutInflater)this.a.getSystemService("layout_inflater");
      com.taobao.newxp.common.b.c localc = com.taobao.newxp.common.b.c.a(this.a);
      ViewGroup localViewGroup = (ViewGroup)localLayoutInflater.inflate(com.taobao.newxp.a.d.e(this.a), null);
      localLargeGalleryConfig2.setParent(localViewGroup);
      LargeGallery localLargeGallery = (LargeGallery)localViewGroup.findViewById(localc.b("taobao_xp_gallery"));
      SwipeViewPointer localSwipeViewPointer = (SwipeViewPointer)localViewGroup.findViewById(localc.b("taobao_xp_gallery_pointer"));
      localLargeGallery.setLoadListener(new LargeGallery.a()
      {
        public void a()
        {
          this.a.setVisibility(4);
          this.b.setVisibility(0);
          this.c.setVisibility(8);
        }

        public void b()
        {
          this.a.setVisibility(4);
          this.b.setVisibility(4);
          this.c.setVisibility(0);
        }

        public void c()
        {
          this.a.setVisibility(0);
          this.b.setVisibility(8);
          this.c.setVisibility(8);
        }
      });
      localLargeGallery.work(this.g, localLargeGalleryConfig2);
      localLargeGallery.setForefathers(paramViewGroup);
      paramViewGroup.addView(localViewGroup, -1, -1);
      localLargeGallery.setPageControl(localSwipeViewPointer);
      return;
      new com.taobao.newxp.view.text.b(this.a, paramViewGroup, 0, this.g);
      return;
    }
  }

  public void addView(ViewGroup paramViewGroup, ListView paramListView)
  {
    addView(paramViewGroup, paramListView, null);
  }

  public void addView(ViewGroup paramViewGroup, ListView paramListView, XpListenersCenter.AdapterListener paramAdapterListener)
  {
    this.g.getEntity().layoutType = 8;
    GridTemplateConfig localGridTemplateConfig = (GridTemplateConfig)getFeatureConfig(GridTemplateConfig.class);
    new com.taobao.newxp.view.container.a(this.a, paramViewGroup, paramListView, this.g, paramAdapterListener, null, localGridTemplateConfig, this.e);
  }

  public void addWelcomeAds(String paramString, final XpListenersCenter.WelcomeAdsListener paramWelcomeAdsListener, final long paramLong1, long paramLong2)
  {
    final long l1 = System.currentTimeMillis();
    this.o = false;
    this.g = new ExchangeDataService(paramString);
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        List localList;
        String str;
        StringBuilder localStringBuilder;
        int i;
        if (!ExchangeViewManager.l(ExchangeViewManager.this))
        {
          ExchangeViewManager.a(ExchangeViewManager.this, true);
          localList = ExchangeViewManager.f(ExchangeViewManager.this).requestCache(true, false);
          str = ExchangeViewManager.d(ExchangeViewManager.this);
          localStringBuilder = new StringBuilder().append("timeout,request data from cache.");
          i = 0;
          if (localList != null)
            break label101;
        }
        while (true)
        {
          com.taobao.newxp.common.Log.a(str, i);
          ExchangeViewManager.a(ExchangeViewManager.this, paramWelcomeAdsListener, paramLong1, l1, this.d, true, localList);
          return;
          label101: i = localList.size();
        }
      }
    }
    , paramLong2);
    this.g.cacheLiveData = true;
    this.g.setSpecificPromoterClz(UMWelcomePromoter.class);
    this.g.requestRichImageDataAsyn(this.a, 9, new XpListenersCenter.ExchangeDataRequestListener()
    {
      public void dataReceived(int paramAnonymousInt, List<Promoter> paramAnonymousList)
      {
        if (ExchangeViewManager.l(ExchangeViewManager.this));
        while (1 != paramAnonymousInt)
          return;
        String str = ExchangeViewManager.d(ExchangeViewManager.this);
        StringBuilder localStringBuilder = new StringBuilder().append("request data from network.");
        if (paramAnonymousList == null);
        for (int i = 0; ; i = paramAnonymousList.size())
        {
          com.taobao.newxp.common.Log.a(str, i);
          ExchangeViewManager.a(ExchangeViewManager.this, paramWelcomeAdsListener, paramLong1, l1, this.d, false, paramAnonymousList);
          return;
        }
      }
    }
    , true);
  }

  public void destroy()
  {
    if ((this.h != null) && (this.h.isShowing()))
    {
      this.h.dismiss();
      this.h = null;
    }
  }

  public ExchangeViewManager setEntryOnClickListener(XpListenersCenter.EntryOnClickListener paramEntryOnClickListener)
  {
    if ((this.c != null) && (this.c != paramEntryOnClickListener))
    {
      com.taobao.newxp.common.Log.e(this.d, "EntryOnClickListener is exist, and make old listener invalid...");
      this.c = paramEntryOnClickListener;
    }
    while (this.c != null)
      return this;
    com.taobao.newxp.common.Log.a(this.d, "EntryOnClickListener set up...");
    this.c = paramEntryOnClickListener;
    return this;
  }

  public void setFeatureConfig(d paramd)
  {
    String str = paramd.getClass().getSimpleName();
    if (this.f.containsKey(str))
      com.taobao.newxp.common.Log.e(this.d, "replace exchange feature config [" + str + "]");
    this.f.put(str, paramd);
  }

  public void setListHeader(int paramInt, ExchangeDataService paramExchangeDataService)
  {
    if (paramExchangeDataService.getEntity().layoutType == -1)
      paramExchangeDataService.getEntity().layoutType = 43;
    this.e = new ExHeader(paramExchangeDataService, paramInt);
  }

  public void setListHeader(ExHeader paramExHeader)
  {
    this.e = paramExHeader;
  }

  public void setLoopInterval(int paramInt)
  {
    if (paramInt > 3000)
    {
      ExchangeConstants.REFRESH_INTERVAL = paramInt;
      ExchangeConstants.IGNORE_SERVER_INTERVAL = true;
    }
  }

  class a
    implements Runnable
  {
    a()
    {
    }

    public void run()
    {
      ExchangeViewManager.k(ExchangeViewManager.this);
      if (ExchangeViewManager.this.c != null)
        ExchangeViewManager.this.c.onClick(ExchangeViewManager.h(ExchangeViewManager.this));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.ExchangeViewManager
 * JD-Core Version:    0.6.2
 */