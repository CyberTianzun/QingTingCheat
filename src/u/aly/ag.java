package u.aly;

public enum ag
  implements cd
{
  private final int e;

  static
  {
    ag[] arrayOfag = new ag[4];
    arrayOfag[0] = a;
    arrayOfag[1] = b;
    arrayOfag[2] = c;
    arrayOfag[3] = d;
  }

  private ag(int paramInt)
  {
    this.e = paramInt;
  }

  public static ag a(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 0:
      return a;
    case 1:
      return b;
    case 2:
      return c;
    case 3:
    }
    return d;
  }

  public int a()
  {
    return this.e;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.ag
 * JD-Core Version:    0.6.2
 */