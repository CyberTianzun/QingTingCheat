package com.lmax.disruptor.collections;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public final class Histogram
{
  private final long[] counts;
  private long maxValue = 0L;
  private long minValue = 9223372036854775807L;
  private final long[] upperBounds;

  public Histogram(long[] paramArrayOfLong)
  {
    validateBounds(paramArrayOfLong);
    this.upperBounds = Arrays.copyOf(paramArrayOfLong, paramArrayOfLong.length);
    this.counts = new long[paramArrayOfLong.length];
  }

  private void trackRange(long paramLong)
  {
    if (paramLong < this.minValue)
      this.minValue = paramLong;
    if (paramLong > this.maxValue)
      this.maxValue = paramLong;
  }

  private void validateBounds(long[] paramArrayOfLong)
  {
    long l1 = -1L;
    int i = paramArrayOfLong.length;
    for (int j = 0; j < i; j++)
    {
      long l2 = paramArrayOfLong[j];
      if (l2 <= 0L)
        throw new IllegalArgumentException("Bounds must be positive values");
      if (l2 <= l1)
        throw new IllegalArgumentException("bound " + l2 + " is not greater than " + l1);
      l1 = l2;
    }
  }

  public boolean addObservation(long paramLong)
  {
    int i = 0;
    int j = -1 + this.upperBounds.length;
    while (i < j)
    {
      int k = i + (j - i >> 1);
      if (this.upperBounds[k] < paramLong)
        i = k + 1;
      else
        j = k;
    }
    if (paramLong <= this.upperBounds[j])
    {
      long[] arrayOfLong = this.counts;
      arrayOfLong[j] = (1L + arrayOfLong[j]);
      trackRange(paramLong);
      return true;
    }
    return false;
  }

  public void addObservations(Histogram paramHistogram)
  {
    if (this.upperBounds.length != paramHistogram.upperBounds.length)
      throw new IllegalArgumentException("Histograms must have matching intervals");
    int i = 0;
    int j = this.upperBounds.length;
    while (i < j)
    {
      if (this.upperBounds[i] != paramHistogram.upperBounds[i])
        throw new IllegalArgumentException("Histograms must have matching intervals");
      i++;
    }
    int k = 0;
    int m = this.counts.length;
    while (k < m)
    {
      long[] arrayOfLong = this.counts;
      arrayOfLong[k] += paramHistogram.counts[k];
      k++;
    }
    trackRange(paramHistogram.minValue);
    trackRange(paramHistogram.maxValue);
  }

  public void clear()
  {
    this.maxValue = 0L;
    this.minValue = 9223372036854775807L;
    int i = 0;
    int j = this.counts.length;
    while (i < j)
    {
      this.counts[i] = 0L;
      i++;
    }
  }

  public long getCount()
  {
    long l = 0L;
    int i = 0;
    int j = this.counts.length;
    while (i < j)
    {
      l += this.counts[i];
      i++;
    }
    return l;
  }

  public long getCountAt(int paramInt)
  {
    return this.counts[paramInt];
  }

  public long getFourNinesUpperBound()
  {
    return getUpperBoundForFactor(0.9999D);
  }

  public long getMax()
  {
    return this.maxValue;
  }

  public BigDecimal getMean()
  {
    if (0L == getCount())
      return BigDecimal.ZERO;
    if (this.counts[0] > 0L);
    BigDecimal localBigDecimal;
    for (long l = this.minValue; ; l = 0L)
    {
      localBigDecimal = BigDecimal.ZERO;
      int i = 0;
      int j = this.upperBounds.length;
      while (i < j)
      {
        if (0L != this.counts[i])
          localBigDecimal = localBigDecimal.add(new BigDecimal(l + (Math.min(this.upperBounds[i], this.maxValue) - l) / 2L).multiply(new BigDecimal(this.counts[i])));
        l = Math.max(1L + this.upperBounds[i], this.minValue);
        i++;
      }
    }
    return localBigDecimal.divide(new BigDecimal(getCount()), 2, RoundingMode.HALF_UP);
  }

  public long getMin()
  {
    return this.minValue;
  }

  public int getSize()
  {
    return this.upperBounds.length;
  }

  public long getTwoNinesUpperBound()
  {
    return getUpperBoundForFactor(0.99D);
  }

  public long getUpperBoundAt(int paramInt)
  {
    return this.upperBounds[paramInt];
  }

  public long getUpperBoundForFactor(double paramDouble)
  {
    long l1 = 0L;
    if ((0.0D >= paramDouble) || (paramDouble >= 1.0D))
      throw new IllegalArgumentException("factor must be >= 0.0 and <= 1.0");
    long l2 = getCount();
    long l3 = l2 - Math.round(paramDouble * l2);
    long l4 = 0L;
    for (int i = -1 + this.counts.length; ; i--)
      if (i >= 0)
      {
        if (l1 != this.counts[i])
        {
          l4 += this.counts[i];
          if (l4 >= l3)
            l1 = this.upperBounds[i];
        }
      }
      else
        return l1;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Histogram{");
    localStringBuilder.append("min=").append(getMin()).append(", ");
    localStringBuilder.append("max=").append(getMax()).append(", ");
    localStringBuilder.append("mean=").append(getMean()).append(", ");
    localStringBuilder.append("99%=").append(getTwoNinesUpperBound()).append(", ");
    localStringBuilder.append("99.99%=").append(getFourNinesUpperBound()).append(", ");
    localStringBuilder.append('[');
    int i = 0;
    int j = this.counts.length;
    while (i < j)
    {
      localStringBuilder.append(this.upperBounds[i]).append('=').append(this.counts[i]).append(", ");
      i++;
    }
    if (this.counts.length > 0)
      localStringBuilder.setLength(-2 + localStringBuilder.length());
    localStringBuilder.append(']');
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.collections.Histogram
 * JD-Core Version:    0.6.2
 */