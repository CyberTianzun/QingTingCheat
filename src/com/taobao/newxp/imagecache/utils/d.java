package com.taobao.newxp.imagecache.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ImageView;
import com.taobao.newxp.Promoter;
import com.taobao.newxp.controller.ExchangeDataService;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

public abstract class d
{
  private static final String a = "ImageWorker";
  private static final int b = 200;
  private static final int k = 0;
  private static final int l = 1;
  private static final int m = 2;
  private static final int n = 3;
  protected boolean c = false;
  protected Resources d;
  private ImageCache e;
  private ImageCache.a f;
  private Bitmap g;
  private boolean h = true;
  private boolean i = false;
  private final Object j = new Object();

  protected d(Context paramContext)
  {
    this.d = paramContext.getResources();
  }

  public static void a(ImageView paramImageView)
  {
    b localb = c(paramImageView);
    if (localb != null)
      localb.a(true);
  }

  private void a(ImageView paramImageView, Drawable paramDrawable)
  {
    if (this.h)
    {
      Drawable[] arrayOfDrawable = new Drawable[2];
      arrayOfDrawable[0] = new ColorDrawable(17170445);
      arrayOfDrawable[1] = paramDrawable;
      TransitionDrawable localTransitionDrawable = new TransitionDrawable(arrayOfDrawable);
      paramImageView.setBackgroundDrawable(new BitmapDrawable(this.d, this.g));
      paramImageView.setImageDrawable(localTransitionDrawable);
      localTransitionDrawable.startTransition(200);
      return;
    }
    paramImageView.setImageDrawable(paramDrawable);
  }

  private static b c(ImageView paramImageView)
  {
    if (paramImageView != null)
    {
      Drawable localDrawable = paramImageView.getDrawable();
      if ((localDrawable instanceof a))
        return ((a)localDrawable).a();
    }
    return null;
  }

  public static boolean c(Object paramObject, ImageView paramImageView)
  {
    b localb = c(paramImageView);
    if (localb != null)
    {
      Object localObject = b.a(localb);
      if ((localObject == null) || (!localObject.equals(paramObject)))
        localb.a(true);
    }
    else
    {
      return true;
    }
    return false;
  }

  protected abstract Bitmap a(Object paramObject);

  protected void a()
  {
    if (this.e != null)
      this.e.a();
  }

  public void a(Bitmap paramBitmap)
  {
    this.g = paramBitmap;
  }

  public void a(FragmentActivity paramFragmentActivity, String paramString)
  {
    this.f = new ImageCache.a(paramFragmentActivity, paramString);
    this.e = ImageCache.a(paramFragmentActivity.getSupportFragmentManager(), this.f);
    c localc = new c();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(1);
    localc.c(arrayOfObject);
  }

  public void a(FragmentManager paramFragmentManager, ImageCache.a parama)
  {
    this.f = parama;
    this.e = ImageCache.a(paramFragmentManager, this.f);
    c localc = new c();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(1);
    localc.c(arrayOfObject);
  }

  public void a(ImageCache.a parama)
  {
    this.f = parama;
    this.e = ImageCache.a(parama);
    c localc = new c();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(1);
    localc.c(arrayOfObject);
  }

  public void a(Object paramObject, ImageView paramImageView)
  {
    a(paramObject, paramImageView, null);
  }

  public void a(Object paramObject, ImageView paramImageView, int paramInt1, int paramInt2, ExchangeDataService paramExchangeDataService, Promoter paramPromoter, boolean paramBoolean)
  {
    a(paramObject, paramImageView, null, paramInt1, paramInt2, paramExchangeDataService, paramPromoter, paramBoolean);
  }

  public void a(Object paramObject, ImageView paramImageView, String paramString)
  {
    if (paramObject == null)
      return;
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      com.taobao.newxp.common.a.a.a locala = new com.taobao.newxp.common.a.a.a("");
      locala.a(paramString);
      if (paramObject != null)
        locala.b((String)paramObject);
      com.taobao.newxp.common.a.a.a().b(locala);
    }
    if (this.e != null);
    for (BitmapDrawable localBitmapDrawable = this.e.a(String.valueOf(paramObject)); ; localBitmapDrawable = null)
    {
      if (localBitmapDrawable != null)
      {
        paramImageView.setImageDrawable(localBitmapDrawable);
        return;
      }
      if (!c(paramObject, paramImageView))
        break;
      b localb = new b(paramImageView);
      paramImageView.setImageDrawable(new a(this.d, this.g, localb));
      localb.a(com.taobao.newxp.common.b.a.d, new Object[] { paramObject });
      return;
    }
  }

  public void a(Object paramObject, ImageView paramImageView, String paramString, int paramInt1, int paramInt2, ExchangeDataService paramExchangeDataService, Promoter paramPromoter, boolean paramBoolean)
  {
    if (paramObject == null)
      return;
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      com.taobao.newxp.common.a.a.a locala = new com.taobao.newxp.common.a.a.a("");
      locala.a(paramString);
      if (paramObject != null)
        locala.b((String)paramObject);
      com.taobao.newxp.common.a.a.a().b(locala);
    }
    if (this.e != null);
    for (BitmapDrawable localBitmapDrawable = this.e.a(String.valueOf(paramObject)); ; localBitmapDrawable = null)
    {
      if (localBitmapDrawable != null)
      {
        paramImageView.setImageDrawable(localBitmapDrawable);
        return;
      }
      if (!c(paramObject, paramImageView))
        break;
      b localb = new b(paramImageView, paramInt1, paramInt2, paramExchangeDataService, paramPromoter);
      paramImageView.setImageDrawable(new a(this.d, this.g, localb));
      Executor localExecutor = com.taobao.newxp.common.b.a.d;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramObject;
      arrayOfObject[1] = Boolean.valueOf(paramBoolean);
      localb.a(localExecutor, arrayOfObject);
      return;
    }
  }

  public void a(Object paramObject, ImageView paramImageView, String paramString, boolean paramBoolean)
  {
    if (paramObject == null)
      return;
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      com.taobao.newxp.common.a.a.a locala = new com.taobao.newxp.common.a.a.a("");
      locala.a(paramString);
      if (paramObject != null)
        locala.b((String)paramObject);
      com.taobao.newxp.common.a.a.a().b(locala);
    }
    if (this.e != null);
    for (BitmapDrawable localBitmapDrawable = this.e.a(String.valueOf(paramObject)); ; localBitmapDrawable = null)
    {
      if (localBitmapDrawable != null)
      {
        paramImageView.setImageDrawable(localBitmapDrawable);
        return;
      }
      if (!c(paramObject, paramImageView))
        break;
      b localb = new b(paramImageView);
      paramImageView.setImageDrawable(new a(this.d, this.g, localb));
      Executor localExecutor = com.taobao.newxp.common.b.a.d;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramObject;
      arrayOfObject[1] = Boolean.valueOf(paramBoolean);
      localb.a(localExecutor, arrayOfObject);
      return;
    }
  }

  public void a(boolean paramBoolean)
  {
    this.h = paramBoolean;
  }

  protected void b()
  {
    if (this.e != null)
      this.e.b();
  }

  public void b(int paramInt)
  {
    this.g = BitmapFactory.decodeResource(this.d, paramInt);
  }

  public void b(Object paramObject, ImageView paramImageView)
  {
    a(paramObject, paramImageView, null, true);
  }

  public void b(boolean paramBoolean)
  {
    this.i = paramBoolean;
    c(false);
  }

  protected void c()
  {
    if (this.e != null)
      this.e.c();
  }

  public void c(boolean paramBoolean)
  {
    synchronized (this.j)
    {
      this.c = paramBoolean;
      if (!this.c)
        this.j.notifyAll();
      return;
    }
  }

  protected void d()
  {
    if (this.e != null)
    {
      this.e.d();
      this.e = null;
    }
  }

  protected ImageCache f()
  {
    return this.e;
  }

  public void g()
  {
    c localc = new c();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(0);
    localc.c(arrayOfObject);
  }

  public void h()
  {
    c localc = new c();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(2);
    localc.c(arrayOfObject);
  }

  public void i()
  {
    c localc = new c();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(3);
    localc.c(arrayOfObject);
  }

  private static class a extends BitmapDrawable
  {
    private final WeakReference<d.b> a;

    public a(Resources paramResources, Bitmap paramBitmap, d.b paramb)
    {
      super(paramBitmap);
      this.a = new WeakReference(paramb);
    }

    public d.b a()
    {
      return (d.b)this.a.get();
    }
  }

  private class b extends com.taobao.newxp.common.b.a<Object, Void, BitmapDrawable>
  {
    private String f;
    private Object g;
    private int h;
    private int i;
    private ExchangeDataService j;
    private Promoter k;
    private final WeakReference<ImageView> l;

    public b(ImageView arg2)
    {
      Object localObject;
      this.l = new WeakReference(localObject);
    }

    public b(ImageView paramInt1, int paramInt2, int paramExchangeDataService, ExchangeDataService paramPromoter, Promoter arg6)
    {
      this(paramInt1);
      this.j = paramPromoter;
      Object localObject;
      this.k = localObject;
      this.h = paramInt2;
      this.i = paramExchangeDataService;
    }

    private ImageView h()
    {
      ImageView localImageView = (ImageView)this.l.get();
      if (this == d.b(localImageView))
        return localImageView;
      return null;
    }

    protected void a(BitmapDrawable paramBitmapDrawable)
    {
      if ((e()) || (d.c(d.this)))
        paramBitmapDrawable = null;
      ImageView localImageView = h();
      if ((paramBitmapDrawable != null) && (localImageView != null))
        d.a(d.this, localImageView, paramBitmapDrawable);
    }

    protected void b(BitmapDrawable paramBitmapDrawable)
    {
      super.b(paramBitmapDrawable);
      synchronized (d.a(d.this))
      {
        d.a(d.this).notifyAll();
        return;
      }
    }

    // ERROR //
    protected BitmapDrawable e(Object[] paramArrayOfObject)
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: iconst_0
      //   3: aaload
      //   4: putfield 50	com/taobao/newxp/imagecache/utils/d$b:g	Ljava/lang/Object;
      //   7: aload_0
      //   8: getfield 50	com/taobao/newxp/imagecache/utils/d$b:g	Ljava/lang/Object;
      //   11: invokestatic 106	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   14: astore_2
      //   15: aload_0
      //   16: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   19: invokestatic 89	com/taobao/newxp/imagecache/utils/d:a	(Lcom/taobao/newxp/imagecache/utils/d;)Ljava/lang/Object;
      //   22: astore_3
      //   23: aload_3
      //   24: monitorenter
      //   25: aload_0
      //   26: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   29: getfield 109	com/taobao/newxp/imagecache/utils/d:c	Z
      //   32: ifeq +32 -> 64
      //   35: aload_0
      //   36: invokevirtual 71	com/taobao/newxp/imagecache/utils/d$b:e	()Z
      //   39: istore 19
      //   41: iload 19
      //   43: ifne +21 -> 64
      //   46: aload_0
      //   47: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   50: invokestatic 89	com/taobao/newxp/imagecache/utils/d:a	(Lcom/taobao/newxp/imagecache/utils/d;)Ljava/lang/Object;
      //   53: invokevirtual 112	java/lang/Object:wait	()V
      //   56: goto -31 -> 25
      //   59: astore 20
      //   61: goto -36 -> 25
      //   64: aload_3
      //   65: monitorexit
      //   66: aload_0
      //   67: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   70: invokestatic 115	com/taobao/newxp/imagecache/utils/d:b	(Lcom/taobao/newxp/imagecache/utils/d;)Lcom/taobao/newxp/imagecache/utils/ImageCache;
      //   73: ifnull +267 -> 340
      //   76: aload_0
      //   77: invokevirtual 71	com/taobao/newxp/imagecache/utils/d$b:e	()Z
      //   80: ifne +260 -> 340
      //   83: aload_0
      //   84: invokespecial 77	com/taobao/newxp/imagecache/utils/d$b:h	()Landroid/widget/ImageView;
      //   87: ifnull +253 -> 340
      //   90: aload_0
      //   91: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   94: invokestatic 75	com/taobao/newxp/imagecache/utils/d:c	(Lcom/taobao/newxp/imagecache/utils/d;)Z
      //   97: ifne +243 -> 340
      //   100: aload_0
      //   101: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   104: invokestatic 115	com/taobao/newxp/imagecache/utils/d:b	(Lcom/taobao/newxp/imagecache/utils/d;)Lcom/taobao/newxp/imagecache/utils/ImageCache;
      //   107: aload_2
      //   108: invokevirtual 120	com/taobao/newxp/imagecache/utils/ImageCache:b	(Ljava/lang/String;)Landroid/graphics/Bitmap;
      //   111: astore 18
      //   113: aload 18
      //   115: astore 5
      //   117: aload 5
      //   119: ifnonnull +258 -> 377
      //   122: aload_0
      //   123: invokevirtual 71	com/taobao/newxp/imagecache/utils/d$b:e	()Z
      //   126: ifne +251 -> 377
      //   129: aload_0
      //   130: invokespecial 77	com/taobao/newxp/imagecache/utils/d$b:h	()Landroid/widget/ImageView;
      //   133: ifnull +244 -> 377
      //   136: aload_0
      //   137: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   140: invokestatic 75	com/taobao/newxp/imagecache/utils/d:c	(Lcom/taobao/newxp/imagecache/utils/d;)Z
      //   143: ifne +234 -> 377
      //   146: aload_0
      //   147: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   150: aload_1
      //   151: iconst_0
      //   152: aaload
      //   153: invokevirtual 123	com/taobao/newxp/imagecache/utils/d:a	(Ljava/lang/Object;)Landroid/graphics/Bitmap;
      //   156: astore 15
      //   158: aload 15
      //   160: astore 6
      //   162: aconst_null
      //   163: astore 7
      //   165: aload 6
      //   167: ifnull +132 -> 299
      //   170: iconst_1
      //   171: aload_1
      //   172: iconst_1
      //   173: aaload
      //   174: checkcast 125	java/lang/Boolean
      //   177: invokevirtual 128	java/lang/Boolean:booleanValue	()Z
      //   180: if_icmpne +234 -> 414
      //   183: aload 6
      //   185: invokestatic 133	com/taobao/newxp/common/b/d:a	(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;
      //   188: astore 12
      //   190: aload 12
      //   192: astore 9
      //   194: aload_0
      //   195: getfield 40	com/taobao/newxp/imagecache/utils/d$b:j	Lcom/taobao/newxp/controller/ExchangeDataService;
      //   198: ifnull +54 -> 252
      //   201: aload 9
      //   203: invokevirtual 139	android/graphics/Bitmap:getWidth	()I
      //   206: aload_0
      //   207: getfield 44	com/taobao/newxp/imagecache/utils/d$b:h	I
      //   210: if_icmpne +15 -> 225
      //   213: aload 9
      //   215: invokevirtual 142	android/graphics/Bitmap:getHeight	()I
      //   218: aload_0
      //   219: getfield 46	com/taobao/newxp/imagecache/utils/d$b:i	I
      //   222: if_icmpeq +30 -> 252
      //   225: aload_0
      //   226: getfield 40	com/taobao/newxp/imagecache/utils/d$b:j	Lcom/taobao/newxp/controller/ExchangeDataService;
      //   229: astore 10
      //   231: iconst_1
      //   232: anewarray 144	com/taobao/newxp/Promoter
      //   235: astore 11
      //   237: aload 11
      //   239: iconst_0
      //   240: aload_0
      //   241: getfield 42	com/taobao/newxp/imagecache/utils/d$b:k	Lcom/taobao/newxp/Promoter;
      //   244: aastore
      //   245: aload 10
      //   247: aload 11
      //   249: invokevirtual 150	com/taobao/newxp/controller/ExchangeDataService:reportNoMatchImage	([Lcom/taobao/newxp/Promoter;)V
      //   252: invokestatic 155	com/taobao/newxp/imagecache/utils/e:d	()Z
      //   255: ifeq +138 -> 393
      //   258: new 82	android/graphics/drawable/BitmapDrawable
      //   261: dup
      //   262: aload_0
      //   263: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   266: getfield 158	com/taobao/newxp/imagecache/utils/d:d	Landroid/content/res/Resources;
      //   269: aload 9
      //   271: invokespecial 161	android/graphics/drawable/BitmapDrawable:<init>	(Landroid/content/res/Resources;Landroid/graphics/Bitmap;)V
      //   274: astore 7
      //   276: aload_0
      //   277: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   280: invokestatic 115	com/taobao/newxp/imagecache/utils/d:b	(Lcom/taobao/newxp/imagecache/utils/d;)Lcom/taobao/newxp/imagecache/utils/ImageCache;
      //   283: ifnull +16 -> 299
      //   286: aload_0
      //   287: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   290: invokestatic 115	com/taobao/newxp/imagecache/utils/d:b	(Lcom/taobao/newxp/imagecache/utils/d;)Lcom/taobao/newxp/imagecache/utils/ImageCache;
      //   293: aload_2
      //   294: aload 7
      //   296: invokevirtual 164	com/taobao/newxp/imagecache/utils/ImageCache:a	(Ljava/lang/String;Landroid/graphics/drawable/BitmapDrawable;)V
      //   299: aload 7
      //   301: areturn
      //   302: astore 4
      //   304: aload_3
      //   305: monitorexit
      //   306: aload 4
      //   308: athrow
      //   309: astore 16
      //   311: ldc 166
      //   313: new 168	java/lang/StringBuilder
      //   316: dup
      //   317: invokespecial 169	java/lang/StringBuilder:<init>	()V
      //   320: ldc 171
      //   322: invokevirtual 175	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   325: aload 16
      //   327: invokevirtual 179	java/lang/Exception:toString	()Ljava/lang/String;
      //   330: invokevirtual 175	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   333: invokevirtual 180	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   336: invokestatic 185	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
      //   339: pop
      //   340: aconst_null
      //   341: astore 5
      //   343: goto -226 -> 117
      //   346: astore 13
      //   348: ldc 166
      //   350: new 168	java/lang/StringBuilder
      //   353: dup
      //   354: invokespecial 169	java/lang/StringBuilder:<init>	()V
      //   357: ldc 171
      //   359: invokevirtual 175	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   362: aload 13
      //   364: invokevirtual 179	java/lang/Exception:toString	()Ljava/lang/String;
      //   367: invokevirtual 175	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   370: invokevirtual 180	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   373: invokestatic 185	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
      //   376: pop
      //   377: aload 5
      //   379: astore 6
      //   381: goto -219 -> 162
      //   384: astore 8
      //   386: aload 6
      //   388: astore 9
      //   390: goto -196 -> 194
      //   393: new 187	com/taobao/newxp/view/widget/RecyclingBitmapDrawable
      //   396: dup
      //   397: aload_0
      //   398: getfield 25	com/taobao/newxp/imagecache/utils/d$b:e	Lcom/taobao/newxp/imagecache/utils/d;
      //   401: getfield 158	com/taobao/newxp/imagecache/utils/d:d	Landroid/content/res/Resources;
      //   404: aload 9
      //   406: invokespecial 188	com/taobao/newxp/view/widget/RecyclingBitmapDrawable:<init>	(Landroid/content/res/Resources;Landroid/graphics/Bitmap;)V
      //   409: astore 7
      //   411: goto -135 -> 276
      //   414: aload 6
      //   416: astore 9
      //   418: goto -224 -> 194
      //
      // Exception table:
      //   from	to	target	type
      //   46	56	59	java/lang/InterruptedException
      //   25	41	302	finally
      //   46	56	302	finally
      //   64	66	302	finally
      //   304	306	302	finally
      //   100	113	309	java/lang/Exception
      //   146	158	346	java/lang/Exception
      //   170	190	384	java/lang/Exception
    }
  }

  protected class c extends com.taobao.newxp.common.b.a<Object, Void, Void>
  {
    protected c()
    {
    }

    protected Void e(Object[] paramArrayOfObject)
    {
      try
      {
        switch (((Integer)paramArrayOfObject[0]).intValue())
        {
        case 0:
          d.this.b();
        case 1:
        case 2:
        case 3:
        }
      }
      catch (Exception localException)
      {
        Log.e("ImageWorker", "", localException);
      }
      d.this.a();
      break label90;
      d.this.c();
      break label90;
      d.this.d();
      label90: return null;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.imagecache.utils.d
 * JD-Core Version:    0.6.2
 */