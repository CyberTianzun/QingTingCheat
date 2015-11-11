package fm.qingting.framework.tween.easing;

import fm.qingting.framework.tween.IEase;

public class Quart
{
  public static class EaseIn
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      float f = paramFloat1 / paramFloat4;
      return paramFloat2 + f * (f * (f * (paramFloat3 * f)));
    }
  }

  public static class EaseInOut
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      float f1 = paramFloat1 / (paramFloat4 / 2.0F);
      if (f1 < 1.0F)
        return paramFloat2 + f1 * (f1 * (f1 * (f1 * (paramFloat3 / 2.0F))));
      float f2 = -paramFloat3 / 2.0F;
      float f3 = f1 - 2.0F;
      return paramFloat2 + f2 * (f3 * (f3 * (f3 * f3)) - 2.0F);
    }
  }

  public static class EaseOut
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      float f1 = -paramFloat3;
      float f2 = paramFloat1 / paramFloat4 - 1.0F;
      return paramFloat2 + f1 * (f2 * (f2 * (f2 * f2)) - 1.0F);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.tween.easing.Quart
 * JD-Core Version:    0.6.2
 */