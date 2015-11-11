package fm.qingting.framework.view;

import android.app.Activity;
import android.view.Display;
import android.view.WindowManager;

public class ScreenInfo
{
  private static ScreenInfo instance = null;
  private int baseHeight;
  private int baseWidth;
  public int heightSampleSize = 1;
  public float heightScale = 1.0F;
  private int screenHeight;
  private int screenWidth;
  private SizeType sizeType;
  public int widthSampleSize = 1;
  public float widthScale = 1.0F;

  public static ScreenInfo getInstance()
  {
    try
    {
      if (instance == null)
        instance = new ScreenInfo();
      ScreenInfo localScreenInfo = instance;
      return localScreenInfo;
    }
    finally
    {
    }
  }

  public int getSizeFromHeight(int paramInt)
  {
    return paramInt * this.screenHeight / this.baseHeight;
  }

  public int getSizeFromHeight(int paramInt1, int paramInt2)
  {
    switch ($SWITCH_TABLE$fm$qingting$framework$view$ScreenInfo$SizeType()[this.sizeType.ordinal()])
    {
    default:
      return paramInt1 * this.screenHeight / this.baseHeight;
    case 3:
    }
    return paramInt2 * this.screenHeight / this.baseHeight;
  }

  public int getSizeFromHeight(int paramInt1, int paramInt2, int paramInt3)
  {
    switch ($SWITCH_TABLE$fm$qingting$framework$view$ScreenInfo$SizeType()[this.sizeType.ordinal()])
    {
    default:
      return paramInt1 * this.screenHeight / this.baseHeight;
    case 3:
      return paramInt3 * this.screenHeight / this.baseHeight;
    case 2:
    }
    return paramInt2 * this.screenHeight / this.baseHeight;
  }

  public int getSizeFromWidth(int paramInt)
  {
    return paramInt * this.screenWidth / this.baseWidth;
  }

  public int getSizeFromWidth(int paramInt1, int paramInt2)
  {
    switch ($SWITCH_TABLE$fm$qingting$framework$view$ScreenInfo$SizeType()[this.sizeType.ordinal()])
    {
    default:
      return paramInt1 * this.screenWidth / this.baseWidth;
    case 3:
    }
    return paramInt2 * this.screenWidth / this.baseWidth;
  }

  public int getSizeFromWidth(int paramInt1, int paramInt2, int paramInt3)
  {
    switch ($SWITCH_TABLE$fm$qingting$framework$view$ScreenInfo$SizeType()[this.sizeType.ordinal()])
    {
    default:
      return paramInt1 * this.screenWidth / this.baseWidth;
    case 3:
      return paramInt3 * this.screenWidth / this.baseWidth;
    case 2:
    }
    return paramInt2 * this.screenWidth / this.baseWidth;
  }

  public SizeType getSizeType()
  {
    return this.sizeType;
  }

  public void setActivity(Activity paramActivity)
  {
    Display localDisplay = paramActivity.getWindowManager().getDefaultDisplay();
    this.screenWidth = localDisplay.getWidth();
    this.screenHeight = localDisplay.getHeight();
  }

  public void setBaseSize(Activity paramActivity, int paramInt1, int paramInt2)
  {
    setActivity(paramActivity);
    this.baseWidth = paramInt1;
    this.baseHeight = paramInt2;
    this.widthScale = (this.screenWidth / this.baseWidth);
    this.heightScale = (this.screenHeight / this.baseHeight);
    this.widthSampleSize = ((int)Math.ceil(this.baseWidth / this.screenWidth));
    this.heightSampleSize = ((int)Math.ceil(this.baseHeight / this.screenHeight));
    if (this.screenWidth / 3 == this.screenHeight / 5)
    {
      this.sizeType = SizeType.S3_5;
      return;
    }
    if (this.screenWidth / 3 == this.screenHeight / 4)
    {
      this.sizeType = SizeType.S3_4;
      return;
    }
    if (this.screenWidth / 2 == this.screenHeight / 3)
    {
      this.sizeType = SizeType.S2_3;
      return;
    }
    this.sizeType = SizeType.OTHER;
  }

  public static enum SizeType
  {
    static
    {
      S3_4 = new SizeType("S3_4", 1);
      S2_3 = new SizeType("S2_3", 2);
      OTHER = new SizeType("OTHER", 3);
      SizeType[] arrayOfSizeType = new SizeType[4];
      arrayOfSizeType[0] = S3_5;
      arrayOfSizeType[1] = S3_4;
      arrayOfSizeType[2] = S2_3;
      arrayOfSizeType[3] = OTHER;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.ScreenInfo
 * JD-Core Version:    0.6.2
 */