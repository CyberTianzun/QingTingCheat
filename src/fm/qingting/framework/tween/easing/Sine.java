package fm.qingting.framework.tween.easing;

import fm.qingting.framework.tween.IEase;

public class Sine
{
  private static final float _HALF_PI = 1.570796F;

  public static class EaseIn
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      return (float)(-paramFloat3 * Math.cos(1.570796F * (paramFloat1 / paramFloat4)) + paramFloat3 + paramFloat2);
    }
  }

  public static class EaseInOut
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      return (float)(-paramFloat3 / 2.0F * (Math.cos(3.141592653589793D * paramFloat1 / paramFloat4) - 1.0D) + paramFloat2);
    }
  }

  public static class EaseOut
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      return (float)(paramFloat3 * Math.sin(1.570796F * (paramFloat1 / paramFloat4)) + paramFloat2);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.tween.easing.Sine
 * JD-Core Version:    0.6.2
 */