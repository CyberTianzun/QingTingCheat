package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class as
  implements Serializable, Cloneable, bz<as, e>
{
  public static final Map<e, cl> d;
  private static final dd e = new dd("IdTracking");
  private static final ct f = new ct("snapshots", (byte)13, (short)1);
  private static final ct g = new ct("journals", (byte)15, (short)2);
  private static final ct h = new ct("checksum", (byte)11, (short)3);
  private static final Map<Class<? extends dg>, dh> i = new HashMap();
  public Map<String, ar> a;
  public List<aq> b;
  public String c;
  private e[] j;

  static
  {
    i.put(di.class, new b(null));
    i.put(dj.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new cl("snapshots", (byte)1, new co((byte)13, new cm((byte)11), new cq((byte)12, ar.class))));
    localEnumMap.put(e.b, new cl("journals", (byte)2, new cn((byte)15, new cq((byte)12, aq.class))));
    localEnumMap.put(e.c, new cl("checksum", (byte)2, new cm((byte)11)));
    d = Collections.unmodifiableMap(localEnumMap);
    cl.a(as.class, d);
  }

  public as()
  {
    e[] arrayOfe = new e[2];
    arrayOfe[0] = e.b;
    arrayOfe[1] = e.c;
    this.j = arrayOfe;
  }

  public as(Map<String, ar> paramMap)
  {
    this();
    this.a = paramMap;
  }

  public as(as paramas)
  {
    e[] arrayOfe = new e[2];
    arrayOfe[0] = e.b;
    arrayOfe[1] = e.c;
    this.j = arrayOfe;
    if (paramas.f())
    {
      HashMap localHashMap = new HashMap();
      Iterator localIterator1 = paramas.a.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator1.next();
        localHashMap.put((String)localEntry.getKey(), new ar((ar)localEntry.getValue()));
      }
      this.a = localHashMap;
    }
    if (paramas.l())
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator2 = paramas.b.iterator();
      while (localIterator2.hasNext())
        localArrayList.add(new aq((aq)localIterator2.next()));
      this.b = localArrayList;
    }
    if (paramas.o())
      this.c = paramas.c;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      a(new cs(new dk(paramObjectInputStream)));
      return;
    }
    catch (cf localcf)
    {
      throw new IOException(localcf.getMessage());
    }
  }

  private void a(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    try
    {
      b(new cs(new dk(paramObjectOutputStream)));
      return;
    }
    catch (cf localcf)
    {
      throw new IOException(localcf.getMessage());
    }
  }

  public e a(int paramInt)
  {
    return e.a(paramInt);
  }

  public as a()
  {
    return new as(this);
  }

  public as a(String paramString)
  {
    this.c = paramString;
    return this;
  }

  public as a(List<aq> paramList)
  {
    this.b = paramList;
    return this;
  }

  public as a(Map<String, ar> paramMap)
  {
    this.a = paramMap;
    return this;
  }

  public void a(String paramString, ar paramar)
  {
    if (this.a == null)
      this.a = new HashMap();
    this.a.put(paramString, paramar);
  }

  public void a(aq paramaq)
  {
    if (this.b == null)
      this.b = new ArrayList();
    this.b.add(paramaq);
  }

  public void a(cy paramcy)
    throws cf
  {
    ((dh)i.get(paramcy.D())).b().b(paramcy, this);
  }

  public void a(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.a = null;
  }

  public void b()
  {
    this.a = null;
    this.b = null;
    this.c = null;
  }

  public void b(cy paramcy)
    throws cf
  {
    ((dh)i.get(paramcy.D())).b().a(paramcy, this);
  }

  public void b(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.b = null;
  }

  public int c()
  {
    if (this.a == null)
      return 0;
    return this.a.size();
  }

  public void c(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.c = null;
  }

  public Map<String, ar> d()
  {
    return this.a;
  }

  public void e()
  {
    this.a = null;
  }

  public boolean f()
  {
    return this.a != null;
  }

  public int h()
  {
    if (this.b == null)
      return 0;
    return this.b.size();
  }

  public Iterator<aq> i()
  {
    if (this.b == null)
      return null;
    return this.b.iterator();
  }

  public List<aq> j()
  {
    return this.b;
  }

  public void k()
  {
    this.b = null;
  }

  public boolean l()
  {
    return this.b != null;
  }

  public String m()
  {
    return this.c;
  }

  public void n()
  {
    this.c = null;
  }

  public boolean o()
  {
    return this.c != null;
  }

  public void p()
    throws cf
  {
    if (this.a == null)
      throw new cz("Required field 'snapshots' was not present! Struct: " + toString());
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("IdTracking(");
    localStringBuilder.append("snapshots:");
    if (this.a == null)
    {
      localStringBuilder.append("null");
      if (l())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("journals:");
        if (this.b != null)
          break label135;
        localStringBuilder.append("null");
      }
      label72: if (o())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("checksum:");
        if (this.c != null)
          break label147;
        localStringBuilder.append("null");
      }
    }
    while (true)
    {
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
      break;
      label135: localStringBuilder.append(this.b);
      break label72;
      label147: localStringBuilder.append(this.c);
    }
  }

  private static class a extends di<as>
  {
    public void a(cy paramcy, as paramas)
      throws cf
    {
      paramcy.j();
      ct localct = paramcy.l();
      if (localct.b == 0)
      {
        paramcy.k();
        paramas.p();
        return;
      }
      switch (localct.c)
      {
      default:
        db.a(paramcy, localct.b);
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        paramcy.m();
        break;
        if (localct.b == 13)
        {
          cv localcv = paramcy.n();
          paramas.a = new HashMap(2 * localcv.c);
          for (int j = 0; j < localcv.c; j++)
          {
            String str = paramcy.z();
            ar localar = new ar();
            localar.a(paramcy);
            paramas.a.put(str, localar);
          }
          paramcy.o();
          paramas.a(true);
        }
        else
        {
          db.a(paramcy, localct.b);
          continue;
          if (localct.b == 15)
          {
            cu localcu = paramcy.p();
            paramas.b = new ArrayList(localcu.b);
            for (int i = 0; i < localcu.b; i++)
            {
              aq localaq = new aq();
              localaq.a(paramcy);
              paramas.b.add(localaq);
            }
            paramcy.q();
            paramas.b(true);
          }
          else
          {
            db.a(paramcy, localct.b);
            continue;
            if (localct.b == 11)
            {
              paramas.c = paramcy.z();
              paramas.c(true);
            }
            else
            {
              db.a(paramcy, localct.b);
            }
          }
        }
      }
    }

    public void b(cy paramcy, as paramas)
      throws cf
    {
      paramas.p();
      paramcy.a(as.q());
      if (paramas.a != null)
      {
        paramcy.a(as.r());
        paramcy.a(new cv((byte)11, (byte)12, paramas.a.size()));
        Iterator localIterator2 = paramas.a.entrySet().iterator();
        while (localIterator2.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator2.next();
          paramcy.a((String)localEntry.getKey());
          ((ar)localEntry.getValue()).b(paramcy);
        }
        paramcy.e();
        paramcy.c();
      }
      if ((paramas.b != null) && (paramas.l()))
      {
        paramcy.a(as.s());
        paramcy.a(new cu((byte)12, paramas.b.size()));
        Iterator localIterator1 = paramas.b.iterator();
        while (localIterator1.hasNext())
          ((aq)localIterator1.next()).b(paramcy);
        paramcy.f();
        paramcy.c();
      }
      if ((paramas.c != null) && (paramas.o()))
      {
        paramcy.a(as.t());
        paramcy.a(paramas.c);
        paramcy.c();
      }
      paramcy.d();
      paramcy.b();
    }
  }

  private static class b
    implements dh
  {
    public as.a a()
    {
      return new as.a(null);
    }
  }

  private static class c extends dj<as>
  {
    public void a(cy paramcy, as paramas)
      throws cf
    {
      de localde = (de)paramcy;
      localde.a(paramas.a.size());
      Iterator localIterator1 = paramas.a.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator1.next();
        localde.a((String)localEntry.getKey());
        ((ar)localEntry.getValue()).b(localde);
      }
      BitSet localBitSet = new BitSet();
      if (paramas.l())
        localBitSet.set(0);
      if (paramas.o())
        localBitSet.set(1);
      localde.a(localBitSet, 2);
      if (paramas.l())
      {
        localde.a(paramas.b.size());
        Iterator localIterator2 = paramas.b.iterator();
        while (localIterator2.hasNext())
          ((aq)localIterator2.next()).b(localde);
      }
      if (paramas.o())
        localde.a(paramas.c);
    }

    public void b(cy paramcy, as paramas)
      throws cf
    {
      int i = 0;
      de localde = (de)paramcy;
      cv localcv = new cv((byte)11, (byte)12, localde.w());
      paramas.a = new HashMap(2 * localcv.c);
      for (int j = 0; j < localcv.c; j++)
      {
        String str = localde.z();
        ar localar = new ar();
        localar.a(localde);
        paramas.a.put(str, localar);
      }
      paramas.a(true);
      BitSet localBitSet = localde.b(2);
      if (localBitSet.get(0))
      {
        cu localcu = new cu((byte)12, localde.w());
        paramas.b = new ArrayList(localcu.b);
        while (i < localcu.b)
        {
          aq localaq = new aq();
          localaq.a(localde);
          paramas.b.add(localaq);
          i++;
        }
        paramas.b(true);
      }
      if (localBitSet.get(1))
      {
        paramas.c = localde.z();
        paramas.c(true);
      }
    }
  }

  private static class d
    implements dh
  {
    public as.c a()
    {
      return new as.c(null);
    }
  }

  public static enum e
    implements cg
  {
    private static final Map<String, e> d;
    private final short e;
    private final String f;

    static
    {
      e[] arrayOfe = new e[3];
      arrayOfe[0] = a;
      arrayOfe[1] = b;
      arrayOfe[2] = c;
      g = arrayOfe;
      d = new HashMap();
      Iterator localIterator = EnumSet.allOf(e.class).iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        d.put(locale.b(), locale);
      }
    }

    private e(short paramShort, String paramString)
    {
      this.e = paramShort;
      this.f = paramString;
    }

    public static e a(int paramInt)
    {
      switch (paramInt)
      {
      default:
        return null;
      case 1:
        return a;
      case 2:
        return b;
      case 3:
      }
      return c;
    }

    public static e a(String paramString)
    {
      return (e)d.get(paramString);
    }

    public static e b(int paramInt)
    {
      e locale = a(paramInt);
      if (locale == null)
        throw new IllegalArgumentException("Field " + paramInt + " doesn't exist!");
      return locale;
    }

    public short a()
    {
      return this.e;
    }

    public String b()
    {
      return this.f;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.as
 * JD-Core Version:    0.6.2
 */