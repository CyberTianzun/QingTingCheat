package master.flame.danmaku.danmaku.model;

public class SpecialDanmaku extends BaseDanmaku
{
  public long alphaDuration;
  public int beginAlpha;
  public float beginX;
  public float beginY;
  private float[] currStateValues = new float[4];
  public int deltaAlpha;
  public float deltaX;
  public float deltaY;
  public int endAlpha;
  public float endX;
  public float endY;
  public LinePath[] linePaths;
  public float pivotX;
  public float pivotY;
  public float rotateX;
  public float rotateZ;
  public long translationDuration;
  public long translationStartDelay;

  public float getBottom()
  {
    return this.currStateValues[3];
  }

  public float getLeft()
  {
    return this.currStateValues[0];
  }

  public float[] getRectAtTime(IDisplayer paramIDisplayer, long paramLong)
  {
    if (!isMeasured())
      return null;
    long l1 = paramLong - this.time;
    float f1;
    float f2;
    long l2;
    float f5;
    int j;
    float f3;
    float f4;
    label138: LinePath localLinePath;
    if ((this.alphaDuration > 0L) && (this.deltaAlpha != 0))
    {
      if (l1 >= this.alphaDuration)
        this.alpha = this.endAlpha;
    }
    else
    {
      f1 = this.beginX;
      f2 = this.beginY;
      l2 = l1 - this.translationStartDelay;
      if ((this.translationDuration <= 0L) || (l2 < 0L) || (l2 > this.translationDuration))
        break label447;
      f5 = (float)l2 / (float)this.translationDuration;
      if (this.linePaths == null)
        break label390;
      LinePath[] arrayOfLinePath = this.linePaths;
      int i = arrayOfLinePath.length;
      j = 0;
      f3 = f1;
      f4 = f2;
      if (j >= i)
        break label489;
      localLinePath = arrayOfLinePath[j];
      if ((l2 < localLinePath.beginTime) || (l2 >= localLinePath.endTime))
        break label356;
    }
    while (true)
    {
      if (localLinePath != null)
      {
        float f7 = localLinePath.delatX;
        float f8 = localLinePath.deltaY;
        float f9 = (float)(l1 - localLinePath.beginTime) / (float)localLinePath.duration;
        float f10 = localLinePath.pBegin.x;
        float f11 = localLinePath.pBegin.y;
        if (f7 != 0.0F)
          f3 = f10 + f7 * f9;
        if (f8 != 0.0F)
          f4 = f11 + f8 * f9;
      }
      while (true)
      {
        label265: this.currStateValues[0] = f3;
        this.currStateValues[1] = f4;
        this.currStateValues[2] = (f3 + this.paintWidth);
        this.currStateValues[3] = (f4 + this.paintHeight);
        if (!isOutside());
        for (boolean bool = true; ; bool = false)
        {
          setVisibility(bool);
          return this.currStateValues;
          this.alpha = ((int)((float)l1 / (float)this.alphaDuration * this.deltaAlpha) + this.beginAlpha);
          break;
          label356: float f12 = localLinePath.pEnd.x;
          float f13 = localLinePath.pEnd.y;
          j++;
          f4 = f13;
          f3 = f12;
          break label138;
          label390: if (this.deltaX != 0.0F)
            f1 = f5 * this.deltaX + this.beginX;
          if (this.deltaY == 0.0F)
            break label478;
          float f6 = f5 * this.deltaY + this.beginY;
          f3 = f1;
          f4 = f6;
          break label265;
          label447: if (l2 <= this.translationDuration)
            break label478;
          f3 = this.endX;
          f4 = this.endY;
          break label265;
        }
        label478: f3 = f1;
        f4 = f2;
      }
      label489: localLinePath = null;
    }
  }

  public float getRight()
  {
    return this.currStateValues[2];
  }

  public float getTop()
  {
    return this.currStateValues[1];
  }

  public int getType()
  {
    return 7;
  }

  public void layout(IDisplayer paramIDisplayer, float paramFloat1, float paramFloat2)
  {
    getRectAtTime(paramIDisplayer, this.mTimer.currMillisecond);
  }

  public void setAlphaData(int paramInt1, int paramInt2, long paramLong)
  {
    this.beginAlpha = paramInt1;
    this.endAlpha = paramInt2;
    this.deltaAlpha = (paramInt2 - paramInt1);
    this.alphaDuration = paramLong;
    if ((this.deltaAlpha != 0) && (paramInt1 != AlphaValue.MAX))
      this.alpha = paramInt1;
  }

  public void setLinePathData(float[][] paramArrayOfFloat)
  {
    if (paramArrayOfFloat != null)
    {
      int i = paramArrayOfFloat.length;
      this.beginX = paramArrayOfFloat[0][0];
      this.beginY = paramArrayOfFloat[0][1];
      this.endX = paramArrayOfFloat[(i - 1)][0];
      this.endY = paramArrayOfFloat[(i - 1)][1];
      if (paramArrayOfFloat.length > 1)
      {
        this.linePaths = new LinePath[-1 + paramArrayOfFloat.length];
        for (int j = 0; j < this.linePaths.length; j++)
        {
          this.linePaths[j] = new LinePath();
          this.linePaths[j].setPoints(new Point(paramArrayOfFloat[j][0], paramArrayOfFloat[j][1]), new Point(paramArrayOfFloat[(j + 1)][0], paramArrayOfFloat[(j + 1)][1]));
        }
        LinePath[] arrayOfLinePath1 = this.linePaths;
        int k = arrayOfLinePath1.length;
        float f1 = 0.0F;
        int m = 0;
        while (m < k)
        {
          float f2 = f1 + arrayOfLinePath1[m].getDistance();
          m++;
          f1 = f2;
        }
        Object localObject = null;
        LinePath[] arrayOfLinePath2 = this.linePaths;
        int n = arrayOfLinePath2.length;
        int i1 = 0;
        if (i1 < n)
        {
          LinePath localLinePath = arrayOfLinePath2[i1];
          localLinePath.duration = (()(localLinePath.getDistance() / f1 * (float)this.translationDuration));
          if (localObject == null);
          for (long l = 0L; ; l = localObject.endTime)
          {
            localLinePath.beginTime = l;
            localLinePath.endTime = (localLinePath.beginTime + localLinePath.duration);
            i1++;
            localObject = localLinePath;
            break;
          }
        }
      }
    }
  }

  public void setTranslationData(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, long paramLong1, long paramLong2)
  {
    this.beginX = paramFloat1;
    this.beginY = paramFloat2;
    this.endX = paramFloat3;
    this.endY = paramFloat4;
    this.deltaX = (paramFloat3 - paramFloat1);
    this.deltaY = (paramFloat4 - paramFloat2);
    this.translationDuration = paramLong1;
    this.translationStartDelay = paramLong2;
  }

  public void updateData(float paramFloat)
  {
  }

  public class LinePath
  {
    public long beginTime;
    float delatX;
    float deltaY;
    public long duration;
    public long endTime;
    SpecialDanmaku.Point pBegin;
    SpecialDanmaku.Point pEnd;

    public LinePath()
    {
    }

    public float[] getBeginPoint()
    {
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[0] = this.pBegin.x;
      arrayOfFloat[1] = this.pBegin.y;
      return arrayOfFloat;
    }

    public float getDistance()
    {
      return this.pEnd.getDistance(this.pBegin);
    }

    public float[] getEndPoint()
    {
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[0] = this.pEnd.x;
      arrayOfFloat[1] = this.pEnd.y;
      return arrayOfFloat;
    }

    public void setPoints(SpecialDanmaku.Point paramPoint1, SpecialDanmaku.Point paramPoint2)
    {
      this.pBegin = paramPoint1;
      this.pEnd = paramPoint2;
      this.delatX = (paramPoint2.x - paramPoint1.x);
      this.deltaY = (paramPoint2.y - paramPoint1.y);
    }
  }

  private class Point
  {
    float x;
    float y;

    public Point(float paramFloat1, float arg3)
    {
      this.x = paramFloat1;
      Object localObject;
      this.y = localObject;
    }

    public float getDistance(Point paramPoint)
    {
      float f1 = Math.abs(this.x - paramPoint.x);
      float f2 = Math.abs(this.y - paramPoint.y);
      return (float)Math.sqrt(f1 * f1 + f2 * f2);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.SpecialDanmaku
 * JD-Core Version:    0.6.2
 */