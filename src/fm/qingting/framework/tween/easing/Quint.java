package fm.qingting.framework.tween.easing;

import fm.qingting.framework.tween.IEase;

public final class Quint
{
  public static class EaseIn
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      float f = paramFloat1 / paramFloat4;
      return paramFloat2 + f * (f * (f * (f * (paramFloat3 * f))));
    }
  }

  public static class EaseInOut
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      float f1 = paramFloat1 / (paramFloat4 / 2.0F);
      if (f1 < 1.0F)
        return paramFloat2 + f1 * (f1 * (f1 * (f1 * (f1 * (paramFloat3 / 2.0F)))));
      float f2 = paramFloat3 / 2.0F;
      float f3 = f1 - 2.0F;
      return paramFloat2 + f2 * (2.0F + f3 * (f3 * (f3 * (f3 * f3))));
    }
  }

  public static class EaseOut
    implements IEase
  {
    public static float inverseEase(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      float f = paramFloat1 / paramFloat3 - 1.0F;
      return paramFloat2 / (1.0F + f * (f * (f * (f * f))));
    }

    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      float f = paramFloat1 / paramFloat4 - 1.0F;
      return paramFloat2 + paramFloat3 * (1.0F + f * (f * (f * (f * f))));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.tween.easing.Quint
 * JD-Core Version:    0.6.2
 */