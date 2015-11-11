package com.alibaba.fastjson.util;

public class IdentityHashMap<K, V>
{
  public static final int DEFAULT_TABLE_SIZE = 1024;
  private final Entry<K, V>[] buckets;
  private final int indexMask;

  public IdentityHashMap()
  {
    this(1024);
  }

  public IdentityHashMap(int paramInt)
  {
    this.indexMask = (paramInt - 1);
    this.buckets = new Entry[paramInt];
  }

  public final V get(K paramK)
  {
    int i = System.identityHashCode(paramK) & this.indexMask;
    for (Entry localEntry = this.buckets[i]; localEntry != null; localEntry = localEntry.next)
      if (paramK == localEntry.key)
        return localEntry.value;
    return null;
  }

  public boolean put(K paramK, V paramV)
  {
    int i = System.identityHashCode(paramK);
    int j = i & this.indexMask;
    for (Entry localEntry1 = this.buckets[j]; localEntry1 != null; localEntry1 = localEntry1.next)
      if (paramK == localEntry1.key)
      {
        localEntry1.value = paramV;
        return true;
      }
    Entry localEntry2 = new Entry(paramK, paramV, i, this.buckets[j]);
    this.buckets[j] = localEntry2;
    return false;
  }

  public int size()
  {
    int i = 0;
    for (int j = 0; j < this.buckets.length; j++)
      for (Entry localEntry = this.buckets[j]; localEntry != null; localEntry = localEntry.next)
        i++;
    return i;
  }

  protected static final class Entry<K, V>
  {
    public final int hashCode;
    public final K key;
    public final Entry<K, V> next;
    public V value;

    public Entry(K paramK, V paramV, int paramInt, Entry<K, V> paramEntry)
    {
      this.key = paramK;
      this.value = paramV;
      this.next = paramEntry;
      this.hashCode = paramInt;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.util.IdentityHashMap
 * JD-Core Version:    0.6.2
 */