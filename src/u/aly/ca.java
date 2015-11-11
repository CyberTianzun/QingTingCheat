package u.aly;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public final class ca
{
  private static final Comparator a = new a(null);

  public static int a(byte paramByte1, byte paramByte2)
  {
    if (paramByte1 < paramByte2)
      return -1;
    if (paramByte2 < paramByte1)
      return 1;
    return 0;
  }

  public static int a(double paramDouble1, double paramDouble2)
  {
    if (paramDouble1 < paramDouble2)
      return -1;
    if (paramDouble2 < paramDouble1)
      return 1;
    return 0;
  }

  public static int a(int paramInt1, int paramInt2)
  {
    if (paramInt1 < paramInt2)
      return -1;
    if (paramInt2 < paramInt1)
      return 1;
    return 0;
  }

  public static int a(long paramLong1, long paramLong2)
  {
    if (paramLong1 < paramLong2)
      return -1;
    if (paramLong2 < paramLong1)
      return 1;
    return 0;
  }

  public static int a(Comparable paramComparable1, Comparable paramComparable2)
  {
    return paramComparable1.compareTo(paramComparable2);
  }

  public static int a(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 instanceof Comparable))
      return a((Comparable)paramObject1, (Comparable)paramObject2);
    if ((paramObject1 instanceof List))
      return a((List)paramObject1, (List)paramObject2);
    if ((paramObject1 instanceof Set))
      return a((Set)paramObject1, (Set)paramObject2);
    if ((paramObject1 instanceof Map))
      return a((Map)paramObject1, (Map)paramObject2);
    if ((paramObject1 instanceof byte[]))
      return a((byte[])paramObject1, (byte[])paramObject2);
    throw new IllegalArgumentException("Cannot compare objects of type " + paramObject1.getClass());
  }

  public static int a(String paramString1, String paramString2)
  {
    return paramString1.compareTo(paramString2);
  }

  public static int a(ByteBuffer paramByteBuffer, byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramByteBuffer.remaining();
    System.arraycopy(paramByteBuffer.array(), paramByteBuffer.arrayOffset() + paramByteBuffer.position(), paramArrayOfByte, paramInt, i);
    return i;
  }

  public static int a(List paramList1, List paramList2)
  {
    int i = a(paramList1.size(), paramList2.size());
    int m;
    if (i != 0)
    {
      m = i;
      return m;
    }
    for (int j = 0; ; j++)
    {
      int k = paramList1.size();
      m = 0;
      if (j >= k)
        break;
      int n = a.compare(paramList1.get(j), paramList2.get(j));
      if (n != 0)
        return n;
    }
  }

  public static int a(Map paramMap1, Map paramMap2)
  {
    int i = a(paramMap1.size(), paramMap2.size());
    if (i != 0)
      return i;
    TreeMap localTreeMap1 = new TreeMap(a);
    localTreeMap1.putAll(paramMap1);
    Iterator localIterator1 = localTreeMap1.entrySet().iterator();
    TreeMap localTreeMap2 = new TreeMap(a);
    localTreeMap2.putAll(paramMap2);
    Iterator localIterator2 = localTreeMap2.entrySet().iterator();
    while ((localIterator1.hasNext()) && (localIterator2.hasNext()))
    {
      Map.Entry localEntry1 = (Map.Entry)localIterator1.next();
      Map.Entry localEntry2 = (Map.Entry)localIterator2.next();
      int j = a.compare(localEntry1.getKey(), localEntry2.getKey());
      if (j != 0)
        return j;
      int k = a.compare(localEntry1.getValue(), localEntry2.getValue());
      if (k != 0)
        return k;
    }
    return 0;
  }

  public static int a(Set paramSet1, Set paramSet2)
  {
    int i = a(paramSet1.size(), paramSet2.size());
    if (i != 0)
      return i;
    TreeSet localTreeSet1 = new TreeSet(a);
    localTreeSet1.addAll(paramSet1);
    TreeSet localTreeSet2 = new TreeSet(a);
    localTreeSet2.addAll(paramSet2);
    Iterator localIterator1 = localTreeSet1.iterator();
    Iterator localIterator2 = localTreeSet2.iterator();
    while ((localIterator1.hasNext()) && (localIterator2.hasNext()))
    {
      int j = a.compare(localIterator1.next(), localIterator2.next());
      if (j != 0)
        return j;
    }
    return 0;
  }

  public static int a(short paramShort1, short paramShort2)
  {
    if (paramShort1 < paramShort2)
      return -1;
    if (paramShort2 < paramShort1)
      return 1;
    return 0;
  }

  public static int a(boolean paramBoolean1, boolean paramBoolean2)
  {
    return Boolean.valueOf(paramBoolean1).compareTo(Boolean.valueOf(paramBoolean2));
  }

  public static int a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int i = a(paramArrayOfByte1.length, paramArrayOfByte2.length);
    int m;
    if (i != 0)
    {
      m = i;
      return m;
    }
    for (int j = 0; ; j++)
    {
      int k = paramArrayOfByte1.length;
      m = 0;
      if (j >= k)
        break;
      int n = a(paramArrayOfByte1[j], paramArrayOfByte2[j]);
      if (n != 0)
        return n;
    }
  }

  public static String a(byte paramByte)
  {
    return Integer.toHexString(0x1FF & (paramByte | 0x100)).toUpperCase().substring(1);
  }

  public static void a(ByteBuffer paramByteBuffer, StringBuilder paramStringBuilder)
  {
    byte[] arrayOfByte = paramByteBuffer.array();
    int i = paramByteBuffer.arrayOffset();
    int j = i + paramByteBuffer.position();
    int k = i + paramByteBuffer.limit();
    if (k - j > 128);
    for (int m = j + 128; ; m = k)
      for (int n = j; n < m; n++)
      {
        if (n > j)
          paramStringBuilder.append(" ");
        paramStringBuilder.append(a(arrayOfByte[n]));
      }
    if (k != m)
      paramStringBuilder.append("...");
  }

  public static byte[] a(ByteBuffer paramByteBuffer)
  {
    if (b(paramByteBuffer))
      return paramByteBuffer.array();
    byte[] arrayOfByte = new byte[paramByteBuffer.remaining()];
    a(paramByteBuffer, arrayOfByte, 0);
    return arrayOfByte;
  }

  public static byte[] a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
    return arrayOfByte;
  }

  public static boolean b(ByteBuffer paramByteBuffer)
  {
    return (paramByteBuffer.hasArray()) && (paramByteBuffer.position() == 0) && (paramByteBuffer.arrayOffset() == 0) && (paramByteBuffer.remaining() == paramByteBuffer.capacity());
  }

  public static ByteBuffer c(ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer == null)
      paramByteBuffer = null;
    while (b(paramByteBuffer))
      return paramByteBuffer;
    return ByteBuffer.wrap(a(paramByteBuffer));
  }

  public static ByteBuffer d(ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer == null)
      return null;
    ByteBuffer localByteBuffer = ByteBuffer.wrap(new byte[paramByteBuffer.remaining()]);
    if (paramByteBuffer.hasArray())
    {
      System.arraycopy(paramByteBuffer.array(), paramByteBuffer.arrayOffset() + paramByteBuffer.position(), localByteBuffer.array(), 0, paramByteBuffer.remaining());
      return localByteBuffer;
    }
    paramByteBuffer.slice().get(localByteBuffer.array());
    return localByteBuffer;
  }

  private static class a
    implements Comparator
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      if ((paramObject1 == null) && (paramObject2 == null))
        return 0;
      if (paramObject1 == null)
        return -1;
      if (paramObject2 == null)
        return 1;
      if ((paramObject1 instanceof List))
        return ca.a((List)paramObject1, (List)paramObject2);
      if ((paramObject1 instanceof Set))
        return ca.a((Set)paramObject1, (Set)paramObject2);
      if ((paramObject1 instanceof Map))
        return ca.a((Map)paramObject1, (Map)paramObject2);
      if ((paramObject1 instanceof byte[]))
        return ca.a((byte[])paramObject1, (byte[])paramObject2);
      return ca.a((Comparable)paramObject1, (Comparable)paramObject2);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.ca
 * JD-Core Version:    0.6.2
 */