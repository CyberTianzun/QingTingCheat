package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ak
  implements Serializable, Cloneable, bz<ak, e>
{
  private static final ct A;
  private static final ct B;
  private static final ct C;
  private static final ct D;
  private static final ct E;
  private static final ct F;
  private static final ct G;
  private static final ct H;
  private static final ct I;
  private static final ct J;
  private static final Map<Class<? extends dg>, dh> K;
  private static final int L = 0;
  private static final int M = 1;
  private static final int N = 2;
  public static final Map<e, cl> r;
  private static final dd s = new dd("DeviceInfo");
  private static final ct t = new ct("device_id", (byte)11, (short)1);
  private static final ct u = new ct("idmd5", (byte)11, (short)2);
  private static final ct v = new ct("mac_address", (byte)11, (short)3);
  private static final ct w = new ct("open_udid", (byte)11, (short)4);
  private static final ct x = new ct("model", (byte)11, (short)5);
  private static final ct y = new ct("cpu", (byte)11, (short)6);
  private static final ct z = new ct("os", (byte)11, (short)7);
  private byte O = 0;
  private e[] P;
  public String a;
  public String b;
  public String c;
  public String d;
  public String e;
  public String f;
  public String g;
  public String h;
  public ba i;
  public boolean j;
  public boolean k;
  public String l;
  public String m;
  public long n;
  public String o;
  public String p;
  public String q;

  static
  {
    A = new ct("os_version", (byte)11, (short)8);
    B = new ct("resolution", (byte)12, (short)9);
    C = new ct("is_jailbroken", (byte)2, (short)10);
    D = new ct("is_pirated", (byte)2, (short)11);
    E = new ct("device_board", (byte)11, (short)12);
    F = new ct("device_brand", (byte)11, (short)13);
    G = new ct("device_manutime", (byte)10, (short)14);
    H = new ct("device_manufacturer", (byte)11, (short)15);
    I = new ct("device_manuid", (byte)11, (short)16);
    J = new ct("device_name", (byte)11, (short)17);
    K = new HashMap();
    K.put(di.class, new b(null));
    K.put(dj.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new cl("device_id", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.b, new cl("idmd5", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.c, new cl("mac_address", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.d, new cl("open_udid", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.e, new cl("model", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.f, new cl("cpu", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.g, new cl("os", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.h, new cl("os_version", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.i, new cl("resolution", (byte)2, new cq((byte)12, ba.class)));
    localEnumMap.put(e.j, new cl("is_jailbroken", (byte)2, new cm((byte)2)));
    localEnumMap.put(e.k, new cl("is_pirated", (byte)2, new cm((byte)2)));
    localEnumMap.put(e.l, new cl("device_board", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.m, new cl("device_brand", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.n, new cl("device_manutime", (byte)2, new cm((byte)10)));
    localEnumMap.put(e.o, new cl("device_manufacturer", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.p, new cl("device_manuid", (byte)2, new cm((byte)11)));
    localEnumMap.put(e.q, new cl("device_name", (byte)2, new cm((byte)11)));
    r = Collections.unmodifiableMap(localEnumMap);
    cl.a(ak.class, r);
  }

  public ak()
  {
    e[] arrayOfe = new e[17];
    arrayOfe[0] = e.a;
    arrayOfe[1] = e.b;
    arrayOfe[2] = e.c;
    arrayOfe[3] = e.d;
    arrayOfe[4] = e.e;
    arrayOfe[5] = e.f;
    arrayOfe[6] = e.g;
    arrayOfe[7] = e.h;
    arrayOfe[8] = e.i;
    arrayOfe[9] = e.j;
    arrayOfe[10] = e.k;
    arrayOfe[11] = e.l;
    arrayOfe[12] = e.m;
    arrayOfe[13] = e.n;
    arrayOfe[14] = e.o;
    arrayOfe[15] = e.p;
    arrayOfe[16] = e.q;
    this.P = arrayOfe;
  }

  public ak(ak paramak)
  {
    e[] arrayOfe = new e[17];
    arrayOfe[0] = e.a;
    arrayOfe[1] = e.b;
    arrayOfe[2] = e.c;
    arrayOfe[3] = e.d;
    arrayOfe[4] = e.e;
    arrayOfe[5] = e.f;
    arrayOfe[6] = e.g;
    arrayOfe[7] = e.h;
    arrayOfe[8] = e.i;
    arrayOfe[9] = e.j;
    arrayOfe[10] = e.k;
    arrayOfe[11] = e.l;
    arrayOfe[12] = e.m;
    arrayOfe[13] = e.n;
    arrayOfe[14] = e.o;
    arrayOfe[15] = e.p;
    arrayOfe[16] = e.q;
    this.P = arrayOfe;
    this.O = paramak.O;
    if (paramak.e())
      this.a = paramak.a;
    if (paramak.i())
      this.b = paramak.b;
    if (paramak.l())
      this.c = paramak.c;
    if (paramak.o())
      this.d = paramak.d;
    if (paramak.r())
      this.e = paramak.e;
    if (paramak.u())
      this.f = paramak.f;
    if (paramak.x())
      this.g = paramak.g;
    if (paramak.A())
      this.h = paramak.h;
    if (paramak.D())
      this.i = new ba(paramak.i);
    this.j = paramak.j;
    this.k = paramak.k;
    if (paramak.M())
      this.l = paramak.l;
    if (paramak.P())
      this.m = paramak.m;
    this.n = paramak.n;
    if (paramak.V())
      this.o = paramak.o;
    if (paramak.Y())
      this.p = paramak.p;
    if (paramak.ab())
      this.q = paramak.q;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.O = 0;
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

  public boolean A()
  {
    return this.h != null;
  }

  public ba B()
  {
    return this.i;
  }

  public void C()
  {
    this.i = null;
  }

  public boolean D()
  {
    return this.i != null;
  }

  public boolean E()
  {
    return this.j;
  }

  public void F()
  {
    this.O = bw.b(this.O, 0);
  }

  public boolean G()
  {
    return bw.a(this.O, 0);
  }

  public boolean H()
  {
    return this.k;
  }

  public void I()
  {
    this.O = bw.b(this.O, 1);
  }

  public boolean J()
  {
    return bw.a(this.O, 1);
  }

  public String K()
  {
    return this.l;
  }

  public void L()
  {
    this.l = null;
  }

  public boolean M()
  {
    return this.l != null;
  }

  public String N()
  {
    return this.m;
  }

  public void O()
  {
    this.m = null;
  }

  public boolean P()
  {
    return this.m != null;
  }

  public long Q()
  {
    return this.n;
  }

  public void R()
  {
    this.O = bw.b(this.O, 2);
  }

  public boolean S()
  {
    return bw.a(this.O, 2);
  }

  public String T()
  {
    return this.o;
  }

  public void U()
  {
    this.o = null;
  }

  public boolean V()
  {
    return this.o != null;
  }

  public String W()
  {
    return this.p;
  }

  public void X()
  {
    this.p = null;
  }

  public boolean Y()
  {
    return this.p != null;
  }

  public String Z()
  {
    return this.q;
  }

  public e a(int paramInt)
  {
    return e.a(paramInt);
  }

  public ak a()
  {
    return new ak(this);
  }

  public ak a(long paramLong)
  {
    this.n = paramLong;
    p(true);
    return this;
  }

  public ak a(String paramString)
  {
    this.a = paramString;
    return this;
  }

  public ak a(ba paramba)
  {
    this.i = paramba;
    return this;
  }

  public void a(cy paramcy)
    throws cf
  {
    ((dh)K.get(paramcy.D())).b().b(paramcy, this);
  }

  public void a(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.a = null;
  }

  public void aa()
  {
    this.q = null;
  }

  public boolean ab()
  {
    return this.q != null;
  }

  public void ac()
    throws cf
  {
    if (this.i != null)
      this.i.j();
  }

  public ak b(String paramString)
  {
    this.b = paramString;
    return this;
  }

  public void b()
  {
    this.a = null;
    this.b = null;
    this.c = null;
    this.d = null;
    this.e = null;
    this.f = null;
    this.g = null;
    this.h = null;
    this.i = null;
    k(false);
    this.j = false;
    m(false);
    this.k = false;
    this.l = null;
    this.m = null;
    p(false);
    this.n = 0L;
    this.o = null;
    this.p = null;
    this.q = null;
  }

  public void b(cy paramcy)
    throws cf
  {
    ((dh)K.get(paramcy.D())).b().a(paramcy, this);
  }

  public void b(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.b = null;
  }

  public String c()
  {
    return this.a;
  }

  public ak c(String paramString)
  {
    this.c = paramString;
    return this;
  }

  public void c(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.c = null;
  }

  public ak d(String paramString)
  {
    this.d = paramString;
    return this;
  }

  public void d()
  {
    this.a = null;
  }

  public void d(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.d = null;
  }

  public ak e(String paramString)
  {
    this.e = paramString;
    return this;
  }

  public void e(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.e = null;
  }

  public boolean e()
  {
    return this.a != null;
  }

  public String f()
  {
    return this.b;
  }

  public ak f(String paramString)
  {
    this.f = paramString;
    return this;
  }

  public void f(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.f = null;
  }

  public ak g(String paramString)
  {
    this.g = paramString;
    return this;
  }

  public void g(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.g = null;
  }

  public ak h(String paramString)
  {
    this.h = paramString;
    return this;
  }

  public void h()
  {
    this.b = null;
  }

  public void h(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.h = null;
  }

  public ak i(String paramString)
  {
    this.l = paramString;
    return this;
  }

  public void i(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.i = null;
  }

  public boolean i()
  {
    return this.b != null;
  }

  public String j()
  {
    return this.c;
  }

  public ak j(String paramString)
  {
    this.m = paramString;
    return this;
  }

  public ak j(boolean paramBoolean)
  {
    this.j = paramBoolean;
    k(true);
    return this;
  }

  public ak k(String paramString)
  {
    this.o = paramString;
    return this;
  }

  public void k()
  {
    this.c = null;
  }

  public void k(boolean paramBoolean)
  {
    this.O = bw.a(this.O, 0, paramBoolean);
  }

  public ak l(String paramString)
  {
    this.p = paramString;
    return this;
  }

  public ak l(boolean paramBoolean)
  {
    this.k = paramBoolean;
    m(true);
    return this;
  }

  public boolean l()
  {
    return this.c != null;
  }

  public String m()
  {
    return this.d;
  }

  public ak m(String paramString)
  {
    this.q = paramString;
    return this;
  }

  public void m(boolean paramBoolean)
  {
    this.O = bw.a(this.O, 1, paramBoolean);
  }

  public void n()
  {
    this.d = null;
  }

  public void n(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.l = null;
  }

  public void o(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.m = null;
  }

  public boolean o()
  {
    return this.d != null;
  }

  public String p()
  {
    return this.e;
  }

  public void p(boolean paramBoolean)
  {
    this.O = bw.a(this.O, 2, paramBoolean);
  }

  public void q()
  {
    this.e = null;
  }

  public void q(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.o = null;
  }

  public void r(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.p = null;
  }

  public boolean r()
  {
    return this.e != null;
  }

  public String s()
  {
    return this.f;
  }

  public void s(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.q = null;
  }

  public void t()
  {
    this.f = null;
  }

  public String toString()
  {
    int i1 = 0;
    StringBuilder localStringBuilder = new StringBuilder("DeviceInfo(");
    int i2 = 1;
    if (e())
    {
      localStringBuilder.append("device_id:");
      if (this.a == null)
      {
        localStringBuilder.append("null");
        i2 = 0;
      }
    }
    else
    {
      if (i())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("idmd5:");
        if (this.b != null)
          break label754;
        localStringBuilder.append("null");
        label89: i2 = 0;
      }
      if (l())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("mac_address:");
        if (this.c != null)
          break label766;
        localStringBuilder.append("null");
        label133: i2 = 0;
      }
      if (o())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("open_udid:");
        if (this.d != null)
          break label778;
        localStringBuilder.append("null");
        label177: i2 = 0;
      }
      if (r())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("model:");
        if (this.e != null)
          break label790;
        localStringBuilder.append("null");
        label221: i2 = 0;
      }
      if (u())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("cpu:");
        if (this.f != null)
          break label802;
        localStringBuilder.append("null");
        label265: i2 = 0;
      }
      if (x())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("os:");
        if (this.g != null)
          break label814;
        localStringBuilder.append("null");
        label309: i2 = 0;
      }
      if (A())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("os_version:");
        if (this.h != null)
          break label826;
        localStringBuilder.append("null");
        label353: i2 = 0;
      }
      if (D())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("resolution:");
        if (this.i != null)
          break label838;
        localStringBuilder.append("null");
        label397: i2 = 0;
      }
      if (G())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("is_jailbroken:");
        localStringBuilder.append(this.j);
        i2 = 0;
      }
      if (J())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("is_pirated:");
        localStringBuilder.append(this.k);
        i2 = 0;
      }
      if (M())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("device_board:");
        if (this.l != null)
          break label850;
        localStringBuilder.append("null");
        label517: i2 = 0;
      }
      if (P())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("device_brand:");
        if (this.m != null)
          break label862;
        localStringBuilder.append("null");
        label561: i2 = 0;
      }
      if (S())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("device_manutime:");
        localStringBuilder.append(this.n);
        i2 = 0;
      }
      if (V())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("device_manufacturer:");
        if (this.o != null)
          break label874;
        localStringBuilder.append("null");
        label643: i2 = 0;
      }
      if (!Y())
        break label912;
      if (i2 == 0)
        localStringBuilder.append(", ");
      localStringBuilder.append("device_manuid:");
      if (this.p != null)
        break label886;
      localStringBuilder.append("null");
    }
    while (true)
    {
      label687: if (ab())
      {
        if (i1 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("device_name:");
        if (this.q != null)
          break label900;
        localStringBuilder.append("null");
      }
      while (true)
      {
        localStringBuilder.append(")");
        return localStringBuilder.toString();
        localStringBuilder.append(this.a);
        break;
        label754: localStringBuilder.append(this.b);
        break label89;
        label766: localStringBuilder.append(this.c);
        break label133;
        label778: localStringBuilder.append(this.d);
        break label177;
        label790: localStringBuilder.append(this.e);
        break label221;
        label802: localStringBuilder.append(this.f);
        break label265;
        label814: localStringBuilder.append(this.g);
        break label309;
        label826: localStringBuilder.append(this.h);
        break label353;
        label838: localStringBuilder.append(this.i);
        break label397;
        label850: localStringBuilder.append(this.l);
        break label517;
        label862: localStringBuilder.append(this.m);
        break label561;
        label874: localStringBuilder.append(this.o);
        break label643;
        label886: localStringBuilder.append(this.p);
        i1 = 0;
        break label687;
        label900: localStringBuilder.append(this.q);
      }
      label912: i1 = i2;
    }
  }

  public boolean u()
  {
    return this.f != null;
  }

  public String v()
  {
    return this.g;
  }

  public void w()
  {
    this.g = null;
  }

  public boolean x()
  {
    return this.g != null;
  }

  public String y()
  {
    return this.h;
  }

  public void z()
  {
    this.h = null;
  }

  private static class a extends di<ak>
  {
    public void a(cy paramcy, ak paramak)
      throws cf
    {
      paramcy.j();
      ct localct = paramcy.l();
      if (localct.b == 0)
      {
        paramcy.k();
        paramak.ac();
        return;
      }
      switch (localct.c)
      {
      default:
        db.a(paramcy, localct.b);
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
      }
      while (true)
      {
        paramcy.m();
        break;
        if (localct.b == 11)
        {
          paramak.a = paramcy.z();
          paramak.a(true);
        }
        else
        {
          db.a(paramcy, localct.b);
          continue;
          if (localct.b == 11)
          {
            paramak.b = paramcy.z();
            paramak.b(true);
          }
          else
          {
            db.a(paramcy, localct.b);
            continue;
            if (localct.b == 11)
            {
              paramak.c = paramcy.z();
              paramak.c(true);
            }
            else
            {
              db.a(paramcy, localct.b);
              continue;
              if (localct.b == 11)
              {
                paramak.d = paramcy.z();
                paramak.d(true);
              }
              else
              {
                db.a(paramcy, localct.b);
                continue;
                if (localct.b == 11)
                {
                  paramak.e = paramcy.z();
                  paramak.e(true);
                }
                else
                {
                  db.a(paramcy, localct.b);
                  continue;
                  if (localct.b == 11)
                  {
                    paramak.f = paramcy.z();
                    paramak.f(true);
                  }
                  else
                  {
                    db.a(paramcy, localct.b);
                    continue;
                    if (localct.b == 11)
                    {
                      paramak.g = paramcy.z();
                      paramak.g(true);
                    }
                    else
                    {
                      db.a(paramcy, localct.b);
                      continue;
                      if (localct.b == 11)
                      {
                        paramak.h = paramcy.z();
                        paramak.h(true);
                      }
                      else
                      {
                        db.a(paramcy, localct.b);
                        continue;
                        if (localct.b == 12)
                        {
                          paramak.i = new ba();
                          paramak.i.a(paramcy);
                          paramak.i(true);
                        }
                        else
                        {
                          db.a(paramcy, localct.b);
                          continue;
                          if (localct.b == 2)
                          {
                            paramak.j = paramcy.t();
                            paramak.k(true);
                          }
                          else
                          {
                            db.a(paramcy, localct.b);
                            continue;
                            if (localct.b == 2)
                            {
                              paramak.k = paramcy.t();
                              paramak.m(true);
                            }
                            else
                            {
                              db.a(paramcy, localct.b);
                              continue;
                              if (localct.b == 11)
                              {
                                paramak.l = paramcy.z();
                                paramak.n(true);
                              }
                              else
                              {
                                db.a(paramcy, localct.b);
                                continue;
                                if (localct.b == 11)
                                {
                                  paramak.m = paramcy.z();
                                  paramak.o(true);
                                }
                                else
                                {
                                  db.a(paramcy, localct.b);
                                  continue;
                                  if (localct.b == 10)
                                  {
                                    paramak.n = paramcy.x();
                                    paramak.p(true);
                                  }
                                  else
                                  {
                                    db.a(paramcy, localct.b);
                                    continue;
                                    if (localct.b == 11)
                                    {
                                      paramak.o = paramcy.z();
                                      paramak.q(true);
                                    }
                                    else
                                    {
                                      db.a(paramcy, localct.b);
                                      continue;
                                      if (localct.b == 11)
                                      {
                                        paramak.p = paramcy.z();
                                        paramak.r(true);
                                      }
                                      else
                                      {
                                        db.a(paramcy, localct.b);
                                        continue;
                                        if (localct.b == 11)
                                        {
                                          paramak.q = paramcy.z();
                                          paramak.s(true);
                                        }
                                        else
                                        {
                                          db.a(paramcy, localct.b);
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    public void b(cy paramcy, ak paramak)
      throws cf
    {
      paramak.ac();
      paramcy.a(ak.ad());
      if ((paramak.a != null) && (paramak.e()))
      {
        paramcy.a(ak.ae());
        paramcy.a(paramak.a);
        paramcy.c();
      }
      if ((paramak.b != null) && (paramak.i()))
      {
        paramcy.a(ak.af());
        paramcy.a(paramak.b);
        paramcy.c();
      }
      if ((paramak.c != null) && (paramak.l()))
      {
        paramcy.a(ak.ag());
        paramcy.a(paramak.c);
        paramcy.c();
      }
      if ((paramak.d != null) && (paramak.o()))
      {
        paramcy.a(ak.ah());
        paramcy.a(paramak.d);
        paramcy.c();
      }
      if ((paramak.e != null) && (paramak.r()))
      {
        paramcy.a(ak.ai());
        paramcy.a(paramak.e);
        paramcy.c();
      }
      if ((paramak.f != null) && (paramak.u()))
      {
        paramcy.a(ak.aj());
        paramcy.a(paramak.f);
        paramcy.c();
      }
      if ((paramak.g != null) && (paramak.x()))
      {
        paramcy.a(ak.ak());
        paramcy.a(paramak.g);
        paramcy.c();
      }
      if ((paramak.h != null) && (paramak.A()))
      {
        paramcy.a(ak.al());
        paramcy.a(paramak.h);
        paramcy.c();
      }
      if ((paramak.i != null) && (paramak.D()))
      {
        paramcy.a(ak.am());
        paramak.i.b(paramcy);
        paramcy.c();
      }
      if (paramak.G())
      {
        paramcy.a(ak.an());
        paramcy.a(paramak.j);
        paramcy.c();
      }
      if (paramak.J())
      {
        paramcy.a(ak.ao());
        paramcy.a(paramak.k);
        paramcy.c();
      }
      if ((paramak.l != null) && (paramak.M()))
      {
        paramcy.a(ak.ap());
        paramcy.a(paramak.l);
        paramcy.c();
      }
      if ((paramak.m != null) && (paramak.P()))
      {
        paramcy.a(ak.aq());
        paramcy.a(paramak.m);
        paramcy.c();
      }
      if (paramak.S())
      {
        paramcy.a(ak.ar());
        paramcy.a(paramak.n);
        paramcy.c();
      }
      if ((paramak.o != null) && (paramak.V()))
      {
        paramcy.a(ak.as());
        paramcy.a(paramak.o);
        paramcy.c();
      }
      if ((paramak.p != null) && (paramak.Y()))
      {
        paramcy.a(ak.at());
        paramcy.a(paramak.p);
        paramcy.c();
      }
      if ((paramak.q != null) && (paramak.ab()))
      {
        paramcy.a(ak.au());
        paramcy.a(paramak.q);
        paramcy.c();
      }
      paramcy.d();
      paramcy.b();
    }
  }

  private static class b
    implements dh
  {
    public ak.a a()
    {
      return new ak.a(null);
    }
  }

  private static class c extends dj<ak>
  {
    public void a(cy paramcy, ak paramak)
      throws cf
    {
      de localde = (de)paramcy;
      BitSet localBitSet = new BitSet();
      if (paramak.e())
        localBitSet.set(0);
      if (paramak.i())
        localBitSet.set(1);
      if (paramak.l())
        localBitSet.set(2);
      if (paramak.o())
        localBitSet.set(3);
      if (paramak.r())
        localBitSet.set(4);
      if (paramak.u())
        localBitSet.set(5);
      if (paramak.x())
        localBitSet.set(6);
      if (paramak.A())
        localBitSet.set(7);
      if (paramak.D())
        localBitSet.set(8);
      if (paramak.G())
        localBitSet.set(9);
      if (paramak.J())
        localBitSet.set(10);
      if (paramak.M())
        localBitSet.set(11);
      if (paramak.P())
        localBitSet.set(12);
      if (paramak.S())
        localBitSet.set(13);
      if (paramak.V())
        localBitSet.set(14);
      if (paramak.Y())
        localBitSet.set(15);
      if (paramak.ab())
        localBitSet.set(16);
      localde.a(localBitSet, 17);
      if (paramak.e())
        localde.a(paramak.a);
      if (paramak.i())
        localde.a(paramak.b);
      if (paramak.l())
        localde.a(paramak.c);
      if (paramak.o())
        localde.a(paramak.d);
      if (paramak.r())
        localde.a(paramak.e);
      if (paramak.u())
        localde.a(paramak.f);
      if (paramak.x())
        localde.a(paramak.g);
      if (paramak.A())
        localde.a(paramak.h);
      if (paramak.D())
        paramak.i.b(localde);
      if (paramak.G())
        localde.a(paramak.j);
      if (paramak.J())
        localde.a(paramak.k);
      if (paramak.M())
        localde.a(paramak.l);
      if (paramak.P())
        localde.a(paramak.m);
      if (paramak.S())
        localde.a(paramak.n);
      if (paramak.V())
        localde.a(paramak.o);
      if (paramak.Y())
        localde.a(paramak.p);
      if (paramak.ab())
        localde.a(paramak.q);
    }

    public void b(cy paramcy, ak paramak)
      throws cf
    {
      de localde = (de)paramcy;
      BitSet localBitSet = localde.b(17);
      if (localBitSet.get(0))
      {
        paramak.a = localde.z();
        paramak.a(true);
      }
      if (localBitSet.get(1))
      {
        paramak.b = localde.z();
        paramak.b(true);
      }
      if (localBitSet.get(2))
      {
        paramak.c = localde.z();
        paramak.c(true);
      }
      if (localBitSet.get(3))
      {
        paramak.d = localde.z();
        paramak.d(true);
      }
      if (localBitSet.get(4))
      {
        paramak.e = localde.z();
        paramak.e(true);
      }
      if (localBitSet.get(5))
      {
        paramak.f = localde.z();
        paramak.f(true);
      }
      if (localBitSet.get(6))
      {
        paramak.g = localde.z();
        paramak.g(true);
      }
      if (localBitSet.get(7))
      {
        paramak.h = localde.z();
        paramak.h(true);
      }
      if (localBitSet.get(8))
      {
        paramak.i = new ba();
        paramak.i.a(localde);
        paramak.i(true);
      }
      if (localBitSet.get(9))
      {
        paramak.j = localde.t();
        paramak.k(true);
      }
      if (localBitSet.get(10))
      {
        paramak.k = localde.t();
        paramak.m(true);
      }
      if (localBitSet.get(11))
      {
        paramak.l = localde.z();
        paramak.n(true);
      }
      if (localBitSet.get(12))
      {
        paramak.m = localde.z();
        paramak.o(true);
      }
      if (localBitSet.get(13))
      {
        paramak.n = localde.x();
        paramak.p(true);
      }
      if (localBitSet.get(14))
      {
        paramak.o = localde.z();
        paramak.q(true);
      }
      if (localBitSet.get(15))
      {
        paramak.p = localde.z();
        paramak.r(true);
      }
      if (localBitSet.get(16))
      {
        paramak.q = localde.z();
        paramak.s(true);
      }
    }
  }

  private static class d
    implements dh
  {
    public ak.c a()
    {
      return new ak.c(null);
    }
  }

  public static enum e
    implements cg
  {
    private static final Map<String, e> r;
    private final short s;
    private final String t;

    static
    {
      e[] arrayOfe = new e[17];
      arrayOfe[0] = a;
      arrayOfe[1] = b;
      arrayOfe[2] = c;
      arrayOfe[3] = d;
      arrayOfe[4] = e;
      arrayOfe[5] = f;
      arrayOfe[6] = g;
      arrayOfe[7] = h;
      arrayOfe[8] = i;
      arrayOfe[9] = j;
      arrayOfe[10] = k;
      arrayOfe[11] = l;
      arrayOfe[12] = m;
      arrayOfe[13] = n;
      arrayOfe[14] = o;
      arrayOfe[15] = p;
      arrayOfe[16] = q;
      u = arrayOfe;
      r = new HashMap();
      Iterator localIterator = EnumSet.allOf(e.class).iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        r.put(locale.b(), locale);
      }
    }

    private e(short paramShort, String paramString)
    {
      this.s = paramShort;
      this.t = paramString;
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
        return c;
      case 4:
        return d;
      case 5:
        return e;
      case 6:
        return f;
      case 7:
        return g;
      case 8:
        return h;
      case 9:
        return i;
      case 10:
        return j;
      case 11:
        return k;
      case 12:
        return l;
      case 13:
        return m;
      case 14:
        return n;
      case 15:
        return o;
      case 16:
        return p;
      case 17:
      }
      return q;
    }

    public static e a(String paramString)
    {
      return (e)r.get(paramString);
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
      return this.s;
    }

    public String b()
    {
      return this.t;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.ak
 * JD-Core Version:    0.6.2
 */