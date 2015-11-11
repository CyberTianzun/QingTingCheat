package com.google.gson.internal;

public final class Pair<FIRST, SECOND>
{
  public final FIRST first;
  public final SECOND second;

  public Pair(FIRST paramFIRST, SECOND paramSECOND)
  {
    this.first = paramFIRST;
    this.second = paramSECOND;
  }

  private static boolean equal(Object paramObject1, Object paramObject2)
  {
    return (paramObject1 == paramObject2) || ((paramObject1 != null) && (paramObject1.equals(paramObject2)));
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Pair));
    Pair localPair;
    do
    {
      return false;
      localPair = (Pair)paramObject;
    }
    while ((!equal(this.first, localPair.first)) || (!equal(this.second, localPair.second)));
    return true;
  }

  public int hashCode()
  {
    if (this.first != null);
    for (int i = this.first.hashCode(); ; i = 0)
    {
      int j = i * 17;
      Object localObject = this.second;
      int k = 0;
      if (localObject != null)
        k = this.second.hashCode();
      return j + k * 17;
    }
  }

  public String toString()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.first;
    arrayOfObject[1] = this.second;
    return String.format("{%s,%s}", arrayOfObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.Pair
 * JD-Core Version:    0.6.2
 */