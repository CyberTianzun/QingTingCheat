package u.aly;

import android.content.Context;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class f
{
  private static final String a = ".imprint";
  private static final byte[] b = "pbl0".getBytes();
  private at c = null;
  private Context d;

  public f(Context paramContext)
  {
    this.d = paramContext;
  }

  private at a(at paramat1, at paramat2)
  {
    if (paramat2 == null)
      return paramat1;
    Map localMap = paramat1.d();
    Iterator localIterator = paramat2.d().entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (((au)localEntry.getValue()).e())
        localMap.put(localEntry.getKey(), localEntry.getValue());
      else
        localMap.remove(localEntry.getKey());
    }
    paramat1.a(paramat2.h());
    paramat1.a(a(paramat1));
    return paramat1;
  }

  private boolean c(at paramat)
  {
    if (!paramat.k().equals(a(paramat)))
      return false;
    Iterator localIterator = paramat.d().values().iterator();
    while (localIterator.hasNext())
    {
      au localau = (au)localIterator.next();
      byte[] arrayOfByte1 = c.b(localau.j());
      byte[] arrayOfByte2 = a(localau);
      for (int i = 0; i < 4; i++)
        if (arrayOfByte1[i] != arrayOfByte2[i])
          return false;
    }
    return true;
  }

  public String a(at paramat)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = new TreeMap(paramat.d()).entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localStringBuilder.append((String)localEntry.getKey());
      localStringBuilder.append(((au)localEntry.getValue()).c());
      localStringBuilder.append(((au)localEntry.getValue()).f());
      localStringBuilder.append(((au)localEntry.getValue()).j());
    }
    localStringBuilder.append(paramat.b);
    return bv.a(localStringBuilder.toString()).toLowerCase(Locale.US);
  }

  public at a()
  {
    try
    {
      at localat = this.c;
      return localat;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public byte[] a(au paramau)
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(8);
    localByteBuffer.order(null);
    localByteBuffer.putLong(paramau.f());
    byte[] arrayOfByte1 = localByteBuffer.array();
    byte[] arrayOfByte2 = b;
    byte[] arrayOfByte3 = new byte[4];
    for (int i = 0; i < 4; i++)
      arrayOfByte3[i] = ((byte)(arrayOfByte1[i] ^ arrayOfByte2[i]));
    return arrayOfByte3;
  }

  // ERROR //
  public void b()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 190	java/io/File
    //   5: dup
    //   6: aload_0
    //   7: getfield 34	u/aly/f:d	Landroid/content/Context;
    //   10: invokevirtual 196	android/content/Context:getFilesDir	()Ljava/io/File;
    //   13: ldc 8
    //   15: invokespecial 199	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   18: invokevirtual 202	java/io/File:exists	()Z
    //   21: ifne +4 -> 25
    //   24: return
    //   25: aload_0
    //   26: getfield 34	u/aly/f:d	Landroid/content/Context;
    //   29: ldc 8
    //   31: invokevirtual 206	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   34: astore 8
    //   36: aload 8
    //   38: astore 4
    //   40: aload 4
    //   42: invokestatic 209	u/aly/bv:b	(Ljava/io/InputStream;)[B
    //   45: astore 9
    //   47: aload 9
    //   49: astore 5
    //   51: aload 4
    //   53: invokestatic 212	u/aly/bv:c	(Ljava/io/InputStream;)V
    //   56: aload 5
    //   58: ifnull -34 -> 24
    //   61: new 37	u/aly/at
    //   64: dup
    //   65: invokespecial 213	u/aly/at:<init>	()V
    //   68: astore 6
    //   70: new 215	u/aly/cc
    //   73: dup
    //   74: invokespecial 216	u/aly/cc:<init>	()V
    //   77: aload 6
    //   79: aload 5
    //   81: invokevirtual 219	u/aly/cc:a	(Lu/aly/bz;[B)V
    //   84: aload_0
    //   85: aload 6
    //   87: putfield 32	u/aly/f:c	Lu/aly/at;
    //   90: return
    //   91: astore 7
    //   93: aload 7
    //   95: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   98: return
    //   99: astore_3
    //   100: aconst_null
    //   101: astore 4
    //   103: aload_3
    //   104: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   107: aload 4
    //   109: invokestatic 212	u/aly/bv:c	(Ljava/io/InputStream;)V
    //   112: aconst_null
    //   113: astore 5
    //   115: goto -59 -> 56
    //   118: astore_2
    //   119: aload_1
    //   120: invokestatic 212	u/aly/bv:c	(Ljava/io/InputStream;)V
    //   123: aload_2
    //   124: athrow
    //   125: astore_2
    //   126: aload 4
    //   128: astore_1
    //   129: goto -10 -> 119
    //   132: astore_3
    //   133: goto -30 -> 103
    //
    // Exception table:
    //   from	to	target	type
    //   61	90	91	java/lang/Exception
    //   25	36	99	java/lang/Exception
    //   25	36	118	finally
    //   40	47	125	finally
    //   103	107	125	finally
    //   40	47	132	java/lang/Exception
  }

  public void b(at paramat)
  {
    if (paramat == null);
    while (!c(paramat))
      return;
    while (true)
    {
      at localat1;
      try
      {
        localat1 = this.c;
        if (localat1 == null)
        {
          this.c = paramat;
          return;
        }
      }
      finally
      {
      }
      at localat2 = a(localat1, paramat);
      paramat = localat2;
    }
  }

  public void c()
  {
    if (this.c == null)
      return;
    try
    {
      byte[] arrayOfByte = new ci().a(this.c);
      bv.a(new File(this.d.getFilesDir(), ".imprint"), arrayOfByte);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public boolean d()
  {
    return new File(this.d.getFilesDir(), ".imprint").delete();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.f
 * JD-Core Version:    0.6.2
 */