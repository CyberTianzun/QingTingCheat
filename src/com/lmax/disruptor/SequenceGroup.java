package com.lmax.disruptor;

import com.lmax.disruptor.util.Util;
import java.util.concurrent.atomic.AtomicReference;

public final class SequenceGroup extends Sequence
{
  private final AtomicReference<Sequence[]> sequencesRef = new AtomicReference(new Sequence[0]);

  public SequenceGroup()
  {
    super(-1L);
  }

  public void add(Sequence paramSequence)
  {
    Sequence[] arrayOfSequence1;
    Sequence[] arrayOfSequence2;
    do
    {
      arrayOfSequence1 = (Sequence[])this.sequencesRef.get();
      int i = arrayOfSequence1.length;
      arrayOfSequence2 = new Sequence[i + 1];
      System.arraycopy(arrayOfSequence1, 0, arrayOfSequence2, 0, i);
      arrayOfSequence2[i] = paramSequence;
    }
    while (!this.sequencesRef.compareAndSet(arrayOfSequence1, arrayOfSequence2));
  }

  public long get()
  {
    if (((Sequence[])this.sequencesRef.get()).length != 0)
      return Util.getMinimumSequence((Sequence[])this.sequencesRef.get());
    return -1L;
  }

  public boolean remove(Sequence paramSequence)
  {
    boolean bool = false;
    Sequence[] arrayOfSequence1;
    Sequence[] arrayOfSequence2;
    do
    {
      arrayOfSequence1 = (Sequence[])this.sequencesRef.get();
      int i = arrayOfSequence1.length;
      arrayOfSequence2 = new Sequence[i - 1];
      int j = 0;
      int k = 0;
      if (j < i)
      {
        Sequence localSequence = arrayOfSequence1[j];
        int m;
        if ((paramSequence == localSequence) && (!bool))
        {
          bool = true;
          m = k;
        }
        while (true)
        {
          j++;
          k = m;
          break;
          m = k + 1;
          arrayOfSequence2[k] = localSequence;
        }
      }
      if (!bool)
        return bool;
    }
    while (!this.sequencesRef.compareAndSet(arrayOfSequence1, arrayOfSequence2));
    return bool;
  }

  public void set(long paramLong)
  {
    Sequence[] arrayOfSequence = (Sequence[])this.sequencesRef.get();
    int i = 0;
    int j = arrayOfSequence.length;
    while (i < j)
    {
      arrayOfSequence[i].set(paramLong);
      i++;
    }
  }

  public int size()
  {
    return ((Sequence[])this.sequencesRef.get()).length;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.SequenceGroup
 * JD-Core Version:    0.6.2
 */