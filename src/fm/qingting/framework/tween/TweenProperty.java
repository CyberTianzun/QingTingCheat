package fm.qingting.framework.tween;

public class TweenProperty
{
  public float begin = (0.0F / 0.0F);
  public boolean complete = false;
  public float duration = (0.0F / 0.0F);
  public IEase ease = null;
  public float[] easeParam = null;
  public float finish = (0.0F / 0.0F);
  public boolean fullMotion = true;
  public float minStep = (0.0F / 0.0F);
  public float time = 0.0F;
  public TRIM_TYPE trimType = TRIM_TYPE.NONE;
  public String type = null;

  public TweenProperty()
  {
  }

  public TweenProperty(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, IEase paramIEase)
  {
    this.type = paramString;
    this.begin = paramFloat1;
    this.finish = paramFloat2;
    this.duration = paramFloat3;
    this.ease = paramIEase;
  }

  public TweenProperty(String paramString, float paramFloat1, float paramFloat2, IEase paramIEase)
  {
    this.type = paramString;
    this.finish = paramFloat1;
    this.duration = paramFloat2;
    this.ease = paramIEase;
  }

  public boolean available()
  {
    if (Float.isNaN(this.begin));
    while ((Float.isNaN(this.finish)) || (Float.isNaN(this.duration)))
      return false;
    return true;
  }

  public TweenProperty clone()
  {
    TweenProperty localTweenProperty = new TweenProperty(this.type, this.begin, this.finish, this.duration, this.ease);
    localTweenProperty.time = this.time;
    localTweenProperty.minStep = this.minStep;
    localTweenProperty.easeParam = this.easeParam;
    localTweenProperty.complete = this.complete;
    localTweenProperty.fullMotion = this.fullMotion;
    localTweenProperty.trimType = this.trimType;
    return localTweenProperty;
  }

  public static enum TRIM_TYPE
  {
    static
    {
      FLOOR = new TRIM_TYPE("FLOOR", 2);
      CEIL = new TRIM_TYPE("CEIL", 3);
      TRIM_TYPE[] arrayOfTRIM_TYPE = new TRIM_TYPE[4];
      arrayOfTRIM_TYPE[0] = NONE;
      arrayOfTRIM_TYPE[1] = ROUND;
      arrayOfTRIM_TYPE[2] = FLOOR;
      arrayOfTRIM_TYPE[3] = CEIL;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.tween.TweenProperty
 * JD-Core Version:    0.6.2
 */