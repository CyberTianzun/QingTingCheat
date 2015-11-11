package u.aly;

public enum bc
  implements cd
{
  private final int e;

  static
  {
    bc[] arrayOfbc = new bc[4];
    arrayOfbc[0] = a;
    arrayOfbc[1] = b;
    arrayOfbc[2] = c;
    arrayOfbc[3] = d;
  }

  private bc(int paramInt)
  {
    this.e = paramInt;
  }

  public static bc a(int paramInt)
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
 * Qualified Name:     u.aly.bc
 * JD-Core Version:    0.6.2
 */