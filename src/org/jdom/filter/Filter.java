package org.jdom.filter;

import java.io.Serializable;

public abstract interface Filter extends Serializable
{
  public abstract boolean matches(Object paramObject);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.filter.Filter
 * JD-Core Version:    0.6.2
 */