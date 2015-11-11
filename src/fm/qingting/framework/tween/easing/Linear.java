package fm.qingting.framework.tween.easing;

import fm.qingting.framework.tween.IEase;

public class Linear
{
  public static class EaseIn
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      return paramFloat2 + paramFloat3 * paramFloat1 / paramFloat4;
    }
  }

  public static class EaseInOut
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      return paramFloat2 + paramFloat3 * paramFloat1 / paramFloat4;
    }
  }

  public static class EaseOut
    implements IEase
  {
    public float ease(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
    {
      return paramFloat2 + paramFloat3 * paramFloat1 / paramFloat4;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.tween.easing.Linear
 * JD-Core Version:    0.6.2
 */