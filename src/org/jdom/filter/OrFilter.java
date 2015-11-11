package org.jdom.filter;

final class OrFilter extends AbstractFilter
{
  private static final String CVS_ID = "@(#) $RCSfile: OrFilter.java,v $ $Revision: 1.5 $ $Date: 2007/11/10 05:29:00 $";
  private Filter left;
  private Filter right;

  public OrFilter(Filter paramFilter1, Filter paramFilter2)
  {
    if ((paramFilter1 == null) || (paramFilter2 == null))
      throw new IllegalArgumentException("null filter not allowed");
    this.left = paramFilter1;
    this.right = paramFilter2;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    OrFilter localOrFilter;
    do
    {
      return true;
      if (!(paramObject instanceof OrFilter))
        break;
      localOrFilter = (OrFilter)paramObject;
    }
    while (((this.left.equals(localOrFilter.left)) && (this.right.equals(localOrFilter.right))) || ((this.left.equals(localOrFilter.right)) && (this.right.equals(localOrFilter.left))));
    return false;
  }

  public int hashCode()
  {
    return 31 * this.left.hashCode() + this.right.hashCode();
  }

  public boolean matches(Object paramObject)
  {
    return (this.left.matches(paramObject)) || (this.right.matches(paramObject));
  }

  public String toString()
  {
    return 64 + "[OrFilter: " + this.left.toString() + ",\n" + "           " + this.right.toString() + "]";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.filter.OrFilter
 * JD-Core Version:    0.6.2
 */