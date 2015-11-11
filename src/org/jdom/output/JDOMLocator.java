package org.jdom.output;

import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;

public class JDOMLocator extends LocatorImpl
{
  private static final String CVS_ID = "@(#) $RCSfile: JDOMLocator.java,v $ $Revision: 1.4 $ $Date: 2007/11/10 05:29:01 $ $Name: jdom_1_1_1 $";
  private Object node;

  JDOMLocator()
  {
  }

  JDOMLocator(Locator paramLocator)
  {
    super(paramLocator);
    if ((paramLocator instanceof JDOMLocator))
      setNode(((JDOMLocator)paramLocator).getNode());
  }

  public Object getNode()
  {
    return this.node;
  }

  void setNode(Object paramObject)
  {
    this.node = paramObject;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.output.JDOMLocator
 * JD-Core Version:    0.6.2
 */