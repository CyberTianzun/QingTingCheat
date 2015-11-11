package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class ResourceManager
{
  private static final String DRAWABLE = "drawable";
  private static final String DRAWABLE_HDPI = "drawable-hdpi";
  private static final String DRAWABLE_LDPI = "drawable-ldpi";
  private static final String DRAWABLE_MDPI = "drawable-mdpi";
  private static final String DRAWABLE_XHDPI = "drawable-xhdpi";
  private static final String DRAWABLE_XXHDPI = "drawable-xxhdpi";
  private static final String[] PRE_INSTALL_DRAWBLE_PATHS = { "drawable-xxhdpi", "drawable-xhdpi", "drawable-hdpi", "drawable-mdpi", "drawable-ldpi", "drawable" };
  private static final String TAG = ResourceManager.class.getName();

  public static ColorStateList createColorStateList(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = { paramInt2, paramInt2, paramInt2, paramInt1 };
    int[][] arrayOfInt1 = new int[4][];
    arrayOfInt1[0] = { 16842919 };
    arrayOfInt1[1] = { 16842913 };
    arrayOfInt1[2] = { 16842908 };
    arrayOfInt1[3] = StateSet.WILD_CARD;
    return new ColorStateList(arrayOfInt1, arrayOfInt);
  }

  public static StateListDrawable createStateListDrawable(Context paramContext, String paramString1, String paramString2)
  {
    Drawable localDrawable1;
    if (paramString1.indexOf(".9") > -1)
    {
      localDrawable1 = getNinePatchDrawable(paramContext, paramString1);
      if (paramString2.indexOf(".9") <= -1)
        break label108;
    }
    label108: for (Drawable localDrawable2 = getNinePatchDrawable(paramContext, paramString2); ; localDrawable2 = getDrawable(paramContext, paramString2))
    {
      StateListDrawable localStateListDrawable = new StateListDrawable();
      localStateListDrawable.addState(new int[] { 16842919 }, localDrawable2);
      localStateListDrawable.addState(new int[] { 16842913 }, localDrawable2);
      localStateListDrawable.addState(new int[] { 16842908 }, localDrawable2);
      localStateListDrawable.addState(StateSet.WILD_CARD, localDrawable1);
      return localStateListDrawable;
      localDrawable1 = getDrawable(paramContext, paramString1);
      break;
    }
  }

  public static int dp2px(Context paramContext, int paramInt)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    return (int)(0.5D + paramInt * localDisplayMetrics.density);
  }

  private static Drawable extractDrawable(Context paramContext, String paramString)
    throws Exception
  {
    InputStream localInputStream = paramContext.getAssets().open(paramString);
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    TypedValue localTypedValue = new TypedValue();
    localTypedValue.density = localDisplayMetrics.densityDpi;
    Drawable localDrawable = Drawable.createFromResourceStream(paramContext.getResources(), localTypedValue, localInputStream, paramString);
    localInputStream.close();
    return localDrawable;
  }

  private static View extractView(Context paramContext, String paramString, ViewGroup paramViewGroup)
    throws Exception
  {
    XmlResourceParser localXmlResourceParser = paramContext.getAssets().openXmlResourceParser(paramString);
    return ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(localXmlResourceParser, paramViewGroup);
  }

  private static String getAppropriatePathOfDrawable(Context paramContext, String paramString)
  {
    String str2;
    if (TextUtils.isEmpty(paramString))
    {
      LogUtil.e(TAG, "id is NOT correct!");
      str2 = null;
      return str2;
    }
    String str1 = getCurrentDpiFolder(paramContext);
    LogUtil.d(TAG, "find Appropriate path...");
    int i = -1;
    int j = -1;
    int k = -1;
    int m = 0;
    label45: label54: int n;
    if (m >= PRE_INSTALL_DRAWBLE_PATHS.length)
    {
      if ((i <= 0) || (k <= 0))
        break label190;
      if (Math.abs(j - k) > Math.abs(j - i))
        break label184;
      n = k;
    }
    while (true)
    {
      if (n >= 0)
        break label235;
      LogUtil.e(TAG, "Not find the appropriate path for drawable");
      return null;
      if (PRE_INSTALL_DRAWBLE_PATHS[m].equals(str1))
        j = m;
      str2 = PRE_INSTALL_DRAWBLE_PATHS[m] + "/" + paramString;
      if (isFileExisted(paramContext, str2))
      {
        if (j == m)
          break;
        if (j >= 0)
          break label177;
        i = m;
      }
      m++;
      break label45;
      label177: k = m;
      break label54;
      label184: n = i;
      continue;
      label190: if ((i > 0) && (k < 0))
      {
        n = i;
      }
      else if ((i < 0) && (k > 0))
      {
        n = k;
      }
      else
      {
        n = -1;
        LogUtil.e(TAG, "Not find the appropriate path for drawable");
      }
    }
    label235: return PRE_INSTALL_DRAWBLE_PATHS[n] + "/" + paramString;
  }

  private static String getCurrentDpiFolder(Context paramContext)
  {
    int i = paramContext.getResources().getDisplayMetrics().densityDpi;
    if (i <= 120)
      return "drawable-ldpi";
    if ((i > 120) && (i <= 160))
      return "drawable-mdpi";
    if ((i > 160) && (i <= 240))
      return "drawable-hdpi";
    if ((i > 240) && (i <= 320))
      return "drawable-xhdpi";
    return "drawable-xxhdpi";
  }

  public static Drawable getDrawable(Context paramContext, String paramString)
  {
    return getDrawableFromAssert(paramContext, getAppropriatePathOfDrawable(paramContext, paramString), false);
  }

  // ERROR //
  private static Drawable getDrawableFromAssert(Context paramContext, String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 111	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   4: astore_3
    //   5: aconst_null
    //   6: astore 4
    //   8: aload_3
    //   9: aload_1
    //   10: invokevirtual 117	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   13: astore 4
    //   15: aload 4
    //   17: ifnull +182 -> 199
    //   20: aload 4
    //   22: invokestatic 228	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   25: astore 9
    //   27: aload_0
    //   28: invokevirtual 90	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   31: invokevirtual 96	android/content/res/Resources:getDisplayMetrics	()Landroid/util/DisplayMetrics;
    //   34: astore 10
    //   36: iload_2
    //   37: ifeq +68 -> 105
    //   40: aload_0
    //   41: invokevirtual 90	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   44: invokevirtual 232	android/content/res/Resources:getConfiguration	()Landroid/content/res/Configuration;
    //   47: astore 11
    //   49: new 234	android/graphics/drawable/NinePatchDrawable
    //   52: dup
    //   53: new 92	android/content/res/Resources
    //   56: dup
    //   57: aload_0
    //   58: invokevirtual 111	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   61: aload 10
    //   63: aload 11
    //   65: invokespecial 237	android/content/res/Resources:<init>	(Landroid/content/res/AssetManager;Landroid/util/DisplayMetrics;Landroid/content/res/Configuration;)V
    //   68: aload 9
    //   70: aload 9
    //   72: invokevirtual 243	android/graphics/Bitmap:getNinePatchChunk	()[B
    //   75: new 245	android/graphics/Rect
    //   78: dup
    //   79: iconst_0
    //   80: iconst_0
    //   81: iconst_0
    //   82: iconst_0
    //   83: invokespecial 248	android/graphics/Rect:<init>	(IIII)V
    //   86: aconst_null
    //   87: invokespecial 251	android/graphics/drawable/NinePatchDrawable:<init>	(Landroid/content/res/Resources;Landroid/graphics/Bitmap;[BLandroid/graphics/Rect;Ljava/lang/String;)V
    //   90: astore 12
    //   92: aload 4
    //   94: ifnull +8 -> 102
    //   97: aload 4
    //   99: invokevirtual 137	java/io/InputStream:close	()V
    //   102: aload 12
    //   104: areturn
    //   105: aload 9
    //   107: aload 10
    //   109: getfield 124	android/util/DisplayMetrics:densityDpi	I
    //   112: invokevirtual 255	android/graphics/Bitmap:setDensity	(I)V
    //   115: new 257	android/graphics/drawable/BitmapDrawable
    //   118: dup
    //   119: aload_0
    //   120: invokevirtual 90	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   123: aload 9
    //   125: invokespecial 260	android/graphics/drawable/BitmapDrawable:<init>	(Landroid/content/res/Resources;Landroid/graphics/Bitmap;)V
    //   128: astore 12
    //   130: goto -38 -> 92
    //   133: astore 7
    //   135: aload 7
    //   137: invokevirtual 263	java/io/IOException:printStackTrace	()V
    //   140: aload 4
    //   142: ifnull +55 -> 197
    //   145: aload 4
    //   147: invokevirtual 137	java/io/InputStream:close	()V
    //   150: aconst_null
    //   151: areturn
    //   152: astore 8
    //   154: aload 8
    //   156: invokevirtual 263	java/io/IOException:printStackTrace	()V
    //   159: goto -9 -> 150
    //   162: astore 5
    //   164: aload 4
    //   166: ifnull +8 -> 174
    //   169: aload 4
    //   171: invokevirtual 137	java/io/InputStream:close	()V
    //   174: aload 5
    //   176: athrow
    //   177: astore 6
    //   179: aload 6
    //   181: invokevirtual 263	java/io/IOException:printStackTrace	()V
    //   184: goto -10 -> 174
    //   187: astore 13
    //   189: aload 13
    //   191: invokevirtual 263	java/io/IOException:printStackTrace	()V
    //   194: goto -92 -> 102
    //   197: aconst_null
    //   198: areturn
    //   199: aconst_null
    //   200: astore 12
    //   202: goto -110 -> 92
    //
    // Exception table:
    //   from	to	target	type
    //   8	15	133	java/io/IOException
    //   20	36	133	java/io/IOException
    //   40	92	133	java/io/IOException
    //   105	130	133	java/io/IOException
    //   145	150	152	java/io/IOException
    //   8	15	162	finally
    //   20	36	162	finally
    //   40	92	162	finally
    //   105	130	162	finally
    //   135	140	162	finally
    //   169	174	177	java/io/IOException
    //   97	102	187	java/io/IOException
  }

  public static Locale getLanguage()
  {
    Locale localLocale = Locale.getDefault();
    if ((Locale.SIMPLIFIED_CHINESE.equals(localLocale)) || (Locale.TRADITIONAL_CHINESE.equals(localLocale)))
      return localLocale;
    return Locale.ENGLISH;
  }

  public static Drawable getNinePatchDrawable(Context paramContext, String paramString)
  {
    return getDrawableFromAssert(paramContext, getAppropriatePathOfDrawable(paramContext, paramString), true);
  }

  public static String getString(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    Locale localLocale = getLanguage();
    if (Locale.SIMPLIFIED_CHINESE.equals(localLocale))
      return paramString2;
    if (Locale.TRADITIONAL_CHINESE.equals(localLocale))
      return paramString3;
    return paramString1;
  }

  private static boolean isFileExisted(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)));
    InputStream localInputStream;
    while (true)
    {
      return false;
      AssetManager localAssetManager = paramContext.getAssets();
      localInputStream = null;
      try
      {
        localInputStream = localAssetManager.open(paramString);
        LogUtil.d(TAG, "file [" + paramString + "] existed");
        if (localInputStream != null);
        try
        {
          localInputStream.close();
          return true;
        }
        catch (IOException localIOException4)
        {
          while (true)
            localIOException4.printStackTrace();
        }
      }
      catch (IOException localIOException2)
      {
        LogUtil.d(TAG, "file [" + paramString + "] NOT existed");
        if (localInputStream != null)
          try
          {
            localInputStream.close();
            return false;
          }
          catch (IOException localIOException3)
          {
            localIOException3.printStackTrace();
            return false;
          }
      }
      finally
      {
        if (localInputStream == null);
      }
    }
    try
    {
      localInputStream.close();
      throw localObject;
    }
    catch (IOException localIOException1)
    {
      while (true)
        localIOException1.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.utils.ResourceManager
 * JD-Core Version:    0.6.2
 */