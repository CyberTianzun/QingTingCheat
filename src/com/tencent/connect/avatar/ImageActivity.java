package com.tencent.connect.avatar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.Util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageActivity extends Activity
{
  RelativeLayout a;
  private QQToken b;
  private String c;
  private Handler d;
  private c e;
  private Button f;
  private Button g;
  private b h;
  private TextView i;
  private ProgressBar j;
  private int k = 0;
  private boolean l = false;
  private long m = 0L;
  private int n = 0;
  private int o = 640;
  private int p = 640;
  private Rect q = new Rect();
  private String r;
  private Bitmap s;
  private View.OnClickListener t = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      ImageActivity.d(ImageActivity.this).setVisibility(0);
      ImageActivity.e(ImageActivity.this).setEnabled(false);
      ImageActivity.e(ImageActivity.this).setTextColor(Color.rgb(21, 21, 21));
      ImageActivity.f(ImageActivity.this).setEnabled(false);
      ImageActivity.f(ImageActivity.this).setTextColor(Color.rgb(36, 94, 134));
      new Thread(new Runnable()
      {
        public void run()
        {
          ImageActivity.g(ImageActivity.this);
        }
      }).start();
      if (ImageActivity.h(ImageActivity.this))
        ImageActivity.this.a("10657", 0L);
      do
      {
        return;
        long l = System.currentTimeMillis() - ImageActivity.i(ImageActivity.this);
        ImageActivity.this.a("10655", l);
      }
      while (!ImageActivity.c(ImageActivity.this).b);
      ImageActivity.this.a("10654", 0L);
    }
  };
  private View.OnClickListener u = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      long l = System.currentTimeMillis() - ImageActivity.i(ImageActivity.this);
      ImageActivity.this.a("10656", l);
      ImageActivity.this.setResult(0);
      ImageActivity.j(ImageActivity.this);
    }
  };
  private IUiListener v = new IUiListener()
  {
    public void onCancel()
    {
    }

    public void onComplete(Object paramAnonymousObject)
    {
      ImageActivity.e(ImageActivity.this).setEnabled(true);
      ImageActivity.e(ImageActivity.this).setTextColor(-1);
      ImageActivity.f(ImageActivity.this).setEnabled(true);
      ImageActivity.f(ImageActivity.this).setTextColor(-1);
      ImageActivity.d(ImageActivity.this).setVisibility(8);
      JSONObject localJSONObject = (JSONObject)paramAnonymousObject;
      try
      {
        int j = localJSONObject.getInt("ret");
        i = j;
        if (i == 0)
        {
          ImageActivity.b(ImageActivity.this, "设置成功", 0);
          ImageActivity.this.a("10658", 0L);
          ImageActivity localImageActivity = ImageActivity.this;
          if ((ImageActivity.k(ImageActivity.this) != null) && (!"".equals(ImageActivity.k(ImageActivity.this))))
          {
            Intent localIntent = new Intent();
            localIntent.setClassName(localImageActivity, ImageActivity.k(ImageActivity.this));
            if (localImageActivity.getPackageManager().resolveActivity(localIntent, 0) != null)
              localImageActivity.startActivity(localIntent);
          }
          ImageActivity.a(ImageActivity.this, 0, localJSONObject.toString(), null, null);
          ImageActivity.j(ImageActivity.this);
          return;
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
        {
          localJSONException.printStackTrace();
          int i = -1;
        }
        ImageActivity.b(ImageActivity.this, "设置出错了，请重新登录再尝试下呢：）", 1);
      }
    }

    public void onError(UiError paramAnonymousUiError)
    {
      ImageActivity.e(ImageActivity.this).setEnabled(true);
      ImageActivity.e(ImageActivity.this).setTextColor(-1);
      ImageActivity.f(ImageActivity.this).setEnabled(true);
      ImageActivity.f(ImageActivity.this).setTextColor(-1);
      ImageActivity.f(ImageActivity.this).setText("重试");
      ImageActivity.d(ImageActivity.this).setVisibility(8);
      ImageActivity.a(ImageActivity.this, true);
      ImageActivity.b(ImageActivity.this, paramAnonymousUiError.errorMessage, 1);
      ImageActivity.this.a("10660", 0L);
    }
  };
  private IUiListener w = new IUiListener()
  {
    private void a(int paramAnonymousInt)
    {
      if (ImageActivity.m(ImageActivity.this) < 2)
        ImageActivity.n(ImageActivity.this);
    }

    public void onCancel()
    {
    }

    public void onComplete(Object paramAnonymousObject)
    {
      JSONObject localJSONObject = (JSONObject)paramAnonymousObject;
      int i = -1;
      try
      {
        i = localJSONObject.getInt("ret");
        if (i == 0)
        {
          final String str = localJSONObject.getString("nickname");
          ImageActivity.l(ImageActivity.this).post(new Runnable()
          {
            public void run()
            {
              ImageActivity.b(ImageActivity.this, str);
            }
          });
          ImageActivity.this.a("10659", 0L);
        }
        while (true)
        {
          if (i != 0)
            a(i);
          return;
          ImageActivity.this.a("10661", 0L);
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
          localJSONException.printStackTrace();
      }
    }

    public void onError(UiError paramAnonymousUiError)
    {
      a(0);
    }
  };

  private Bitmap a(String paramString)
    throws IOException
  {
    int i1 = 1;
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = i1;
    Uri localUri = Uri.parse(paramString);
    InputStream localInputStream = getContentResolver().openInputStream(localUri);
    if (localInputStream == null)
      return null;
    BitmapFactory.decodeStream(localInputStream, null, localOptions);
    localInputStream.close();
    int i2 = localOptions.outWidth;
    int i3 = localOptions.outHeight;
    while (true)
    {
      if (i2 * i3 <= 4194304)
      {
        localOptions.inJustDecodeBounds = false;
        localOptions.inSampleSize = i1;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(localUri), null, localOptions);
      }
      i2 /= 2;
      i3 /= 2;
      i1 *= 2;
    }
  }

  private View a()
  {
    ViewGroup.LayoutParams localLayoutParams1 = new ViewGroup.LayoutParams(-1, -1);
    ViewGroup.LayoutParams localLayoutParams2 = new ViewGroup.LayoutParams(-1, -1);
    ViewGroup.LayoutParams localLayoutParams3 = new ViewGroup.LayoutParams(-2, -2);
    this.a = new RelativeLayout(this);
    this.a.setLayoutParams(localLayoutParams1);
    this.a.setBackgroundColor(-16777216);
    RelativeLayout localRelativeLayout1 = new RelativeLayout(this);
    localRelativeLayout1.setLayoutParams(localLayoutParams3);
    this.a.addView(localRelativeLayout1);
    this.e = new c(this);
    this.e.setLayoutParams(localLayoutParams2);
    this.e.setScaleType(ImageView.ScaleType.MATRIX);
    localRelativeLayout1.addView(this.e);
    this.h = new b(this);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(localLayoutParams2);
    localLayoutParams4.addRule(14, -1);
    localLayoutParams4.addRule(15, -1);
    this.h.setLayoutParams(localLayoutParams4);
    localRelativeLayout1.addView(this.h);
    LinearLayout localLinearLayout = new LinearLayout(this);
    RelativeLayout.LayoutParams localLayoutParams5 = new RelativeLayout.LayoutParams(-2, a.a(this, 80.0F));
    localLayoutParams5.addRule(14, -1);
    localLinearLayout.setLayoutParams(localLayoutParams5);
    localLinearLayout.setOrientation(0);
    localLinearLayout.setGravity(17);
    this.a.addView(localLinearLayout);
    ImageView localImageView = new ImageView(this);
    localImageView.setLayoutParams(new LinearLayout.LayoutParams(a.a(this, 24.0F), a.a(this, 24.0F)));
    localImageView.setImageDrawable(b("com.tencent.plus.logo.png"));
    localLinearLayout.addView(localImageView);
    this.i = new TextView(this);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(localLayoutParams3);
    localLayoutParams.leftMargin = a.a(this, 7.0F);
    this.i.setLayoutParams(localLayoutParams);
    this.i.setEllipsize(TextUtils.TruncateAt.END);
    this.i.setSingleLine();
    this.i.setTextColor(-1);
    this.i.setTextSize(24.0F);
    this.i.setVisibility(8);
    localLinearLayout.addView(this.i);
    RelativeLayout localRelativeLayout2 = new RelativeLayout(this);
    RelativeLayout.LayoutParams localLayoutParams6 = new RelativeLayout.LayoutParams(-1, a.a(this, 60.0F));
    localLayoutParams6.addRule(12, -1);
    localLayoutParams6.addRule(9, -1);
    localRelativeLayout2.setLayoutParams(localLayoutParams6);
    localRelativeLayout2.setBackgroundDrawable(b("com.tencent.plus.bar.png"));
    int i1 = a.a(this, 10.0F);
    localRelativeLayout2.setPadding(i1, i1, i1, 0);
    this.a.addView(localRelativeLayout2);
    a locala = new a(this);
    int i2 = a.a(this, 14.0F);
    int i3 = a.a(this, 7.0F);
    this.g = new Button(this);
    RelativeLayout.LayoutParams localLayoutParams7 = new RelativeLayout.LayoutParams(a.a(this, 78.0F), a.a(this, 45.0F));
    this.g.setLayoutParams(localLayoutParams7);
    this.g.setText("取消");
    this.g.setTextColor(-1);
    this.g.setTextSize(18.0F);
    this.g.setPadding(i2, i3, i2, i3);
    locala.b(this.g);
    localRelativeLayout2.addView(this.g);
    this.f = new Button(this);
    RelativeLayout.LayoutParams localLayoutParams8 = new RelativeLayout.LayoutParams(a.a(this, 78.0F), a.a(this, 45.0F));
    localLayoutParams8.addRule(11, -1);
    this.f.setLayoutParams(localLayoutParams8);
    this.f.setTextColor(-1);
    this.f.setTextSize(18.0F);
    this.f.setPadding(i2, i3, i2, i3);
    this.f.setText("选取");
    locala.a(this.f);
    localRelativeLayout2.addView(this.f);
    TextView localTextView = new TextView(this);
    RelativeLayout.LayoutParams localLayoutParams9 = new RelativeLayout.LayoutParams(localLayoutParams3);
    localLayoutParams9.addRule(13, -1);
    localTextView.setLayoutParams(localLayoutParams9);
    localTextView.setText("移动和缩放");
    localTextView.setPadding(0, a.a(this, 3.0F), 0, 0);
    localTextView.setTextSize(18.0F);
    localTextView.setTextColor(-1);
    localRelativeLayout2.addView(localTextView);
    this.j = new ProgressBar(this);
    RelativeLayout.LayoutParams localLayoutParams10 = new RelativeLayout.LayoutParams(localLayoutParams3);
    localLayoutParams10.addRule(14, -1);
    localLayoutParams10.addRule(15, -1);
    this.j.setLayoutParams(localLayoutParams10);
    this.j.setVisibility(8);
    this.a.addView(this.j);
    return this.a;
  }

  private void a(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("key_error_code", paramInt);
    localIntent.putExtra("key_error_msg", paramString2);
    localIntent.putExtra("key_error_detail", paramString3);
    localIntent.putExtra("key_response", paramString1);
    setResult(-1, localIntent);
  }

  private void a(Bitmap paramBitmap)
  {
    new QQAvatarImp(this, this.b).setAvator(paramBitmap, this.v);
  }

  private void a(final String paramString, final int paramInt)
  {
    this.d.post(new Runnable()
    {
      public void run()
      {
        ImageActivity.a(ImageActivity.this, paramString, paramInt);
      }
    });
  }

  // ERROR //
  private Drawable b(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 375	com/tencent/connect/avatar/ImageActivity:getAssets	()Landroid/content/res/AssetManager;
    //   4: astore_2
    //   5: aload_2
    //   6: aload_1
    //   7: invokevirtual 381	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   10: astore 6
    //   12: aload 6
    //   14: aload_1
    //   15: invokestatic 387	android/graphics/drawable/Drawable:createFromStream	(Ljava/io/InputStream;Ljava/lang/String;)Landroid/graphics/drawable/Drawable;
    //   18: astore 7
    //   20: aload 7
    //   22: astore 4
    //   24: aload 6
    //   26: invokevirtual 122	java/io/InputStream:close	()V
    //   29: aload 4
    //   31: areturn
    //   32: astore_3
    //   33: aconst_null
    //   34: astore 4
    //   36: aload_3
    //   37: astore 5
    //   39: aload 5
    //   41: invokevirtual 390	java/io/IOException:printStackTrace	()V
    //   44: aload 4
    //   46: areturn
    //   47: astore 5
    //   49: goto -10 -> 39
    //
    // Exception table:
    //   from	to	target	type
    //   5	20	32	java/io/IOException
    //   24	29	47	java/io/IOException
  }

  private void b()
  {
    try
    {
      this.s = a(this.r);
      if (this.s == null)
        throw new IOException("cannot read picture: '" + this.r + "'!");
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      b("图片读取失败，请检查该图片是否有效", 1);
      a(-5, null, "图片读取失败，请检查该图片是否有效", localIOException.getMessage());
      d();
    }
    while (true)
    {
      this.f.setOnClickListener(this.t);
      this.g.setOnClickListener(this.u);
      this.a.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
      {
        public void onGlobalLayout()
        {
          ImageActivity.this.a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
          ImageActivity.a(ImageActivity.this, ImageActivity.a(ImageActivity.this).a());
          ImageActivity.c(ImageActivity.this).a(ImageActivity.b(ImageActivity.this));
        }
      });
      return;
      this.e.setImageBitmap(this.s);
    }
  }

  private void b(String paramString, int paramInt)
  {
    Toast localToast = Toast.makeText(this, paramString, 1);
    LinearLayout localLinearLayout = (LinearLayout)localToast.getView();
    ((TextView)localLinearLayout.getChildAt(0)).setPadding(8, 0, 0, 0);
    ImageView localImageView = new ImageView(this);
    localImageView.setLayoutParams(new LinearLayout.LayoutParams(a.a(this, 16.0F), a.a(this, 16.0F)));
    if (paramInt == 0)
      localImageView.setImageDrawable(b("com.tencent.plus.ic_success.png"));
    while (true)
    {
      localLinearLayout.addView(localImageView, 0);
      localLinearLayout.setOrientation(0);
      localLinearLayout.setGravity(17);
      localToast.setView(localLinearLayout);
      localToast.setGravity(17, 0, 0);
      localToast.show();
      return;
      localImageView.setImageDrawable(b("com.tencent.plus.ic_error.png"));
    }
  }

  private void c()
  {
    float f1 = this.q.width();
    Matrix localMatrix1 = this.e.getImageMatrix();
    float[] arrayOfFloat = new float[9];
    localMatrix1.getValues(arrayOfFloat);
    float f2 = arrayOfFloat[2];
    float f3 = arrayOfFloat[5];
    float f4 = arrayOfFloat[0];
    float f5 = this.o / f1;
    int i1 = (int)((this.q.left - f2) / f4);
    int i2 = (int)((this.q.top - f3) / f4);
    Matrix localMatrix2 = new Matrix();
    localMatrix2.set(localMatrix1);
    localMatrix2.postScale(f5, f5);
    int i3 = (int)(650.0F / f4);
    int i4 = Math.min(this.s.getWidth() - i1, i3);
    int i5 = Math.min(this.s.getHeight() - i2, i3);
    Bitmap localBitmap1 = Bitmap.createBitmap(this.s, i1, i2, i4, i5, localMatrix2, true);
    Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, 0, this.o, this.p);
    localBitmap1.recycle();
    a(localBitmap2);
  }

  private void c(String paramString)
  {
    String str = d(paramString);
    if (!"".equals(str))
    {
      this.i.setText(str);
      this.i.setVisibility(0);
    }
  }

  private String d(String paramString)
  {
    return paramString.replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replaceAll("&#39;", "'").replaceAll("&amp;", "&");
  }

  private void d()
  {
    finish();
    if (this.n != 0)
      overridePendingTransition(0, this.n);
  }

  private void e()
  {
    this.k = (1 + this.k);
    new UserInfo(this, this.b).getUserInfo(this.w);
  }

  public void a(String paramString, long paramLong)
  {
    Util.reportBernoulli(this, paramString, paramLong, this.b.getAppId());
  }

  public void onBackPressed()
  {
    setResult(0);
    d();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setRequestedOrientation(1);
    setContentView(a());
    this.d = new Handler();
    Bundle localBundle = getIntent().getBundleExtra("key_params");
    this.r = localBundle.getString("picture");
    this.c = localBundle.getString("return_activity");
    String str1 = localBundle.getString("appid");
    String str2 = localBundle.getString("access_token");
    long l1 = localBundle.getLong("expires_in");
    String str3 = localBundle.getString("openid");
    this.n = localBundle.getInt("exitAnim");
    this.b = new QQToken(str1);
    this.b.setAccessToken(str2, (l1 - System.currentTimeMillis()) / 1000L + "");
    this.b.setOpenId(str3);
    b();
    e();
    this.m = System.currentTimeMillis();
    a("10653", 0L);
  }

  protected void onDestroy()
  {
    super.onDestroy();
    this.e.setImageBitmap(null);
    if ((this.s != null) && (!this.s.isRecycled()))
      this.s.recycle();
  }

  private class QQAvatarImp extends BaseApi
  {
    public QQAvatarImp(Context paramQQToken, QQToken arg3)
    {
      super(localQQToken);
    }

    public void setAvator(Bitmap paramBitmap, IUiListener paramIUiListener)
    {
      Bundle localBundle = composeCGIParams();
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 40, localByteArrayOutputStream);
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      paramBitmap.recycle();
      BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
      localBundle.putByteArray("picture", arrayOfByte);
      HttpUtils.requestAsync(this.mToken, this.mContext, "user/set_user_face", localBundle, "POST", localTempRequestListener);
    }
  }

  class a extends View
  {
    public a(Context arg2)
    {
      super();
    }

    public void a(Button paramButton)
    {
      StateListDrawable localStateListDrawable = new StateListDrawable();
      Drawable localDrawable1 = ImageActivity.a(ImageActivity.this, "com.tencent.plus.blue_normal.png");
      Drawable localDrawable2 = ImageActivity.a(ImageActivity.this, "com.tencent.plus.blue_down.png");
      Drawable localDrawable3 = ImageActivity.a(ImageActivity.this, "com.tencent.plus.blue_disable.png");
      localStateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, localDrawable2);
      localStateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, localDrawable1);
      localStateListDrawable.addState(View.ENABLED_STATE_SET, localDrawable1);
      localStateListDrawable.addState(View.FOCUSED_STATE_SET, localDrawable1);
      localStateListDrawable.addState(View.EMPTY_STATE_SET, localDrawable3);
      paramButton.setBackgroundDrawable(localStateListDrawable);
    }

    public void b(Button paramButton)
    {
      StateListDrawable localStateListDrawable = new StateListDrawable();
      Drawable localDrawable1 = ImageActivity.a(ImageActivity.this, "com.tencent.plus.gray_normal.png");
      Drawable localDrawable2 = ImageActivity.a(ImageActivity.this, "com.tencent.plus.gray_down.png");
      Drawable localDrawable3 = ImageActivity.a(ImageActivity.this, "com.tencent.plus.gray_disable.png");
      localStateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, localDrawable2);
      localStateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, localDrawable1);
      localStateListDrawable.addState(View.ENABLED_STATE_SET, localDrawable1);
      localStateListDrawable.addState(View.FOCUSED_STATE_SET, localDrawable1);
      localStateListDrawable.addState(View.EMPTY_STATE_SET, localDrawable3);
      paramButton.setBackgroundDrawable(localStateListDrawable);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.avatar.ImageActivity
 * JD-Core Version:    0.6.2
 */