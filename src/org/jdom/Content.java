package org.jdom;

import java.io.Serializable;

public abstract class Content
  implements Cloneable, Serializable
{
  protected Parent parent = null;

  public Object clone()
  {
    try
    {
      Content localContent = (Content)super.clone();
      localContent.parent = null;
      return localContent;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
    }
    return null;
  }

  public Content detach()
  {
    if (this.parent != null)
      this.parent.removeContent(this);
    return this;
  }

  public final boolean equals(Object paramObject)
  {
    return paramObject == this;
  }

  public Document getDocument()
  {
    if (this.parent == null)
      return null;
    return this.parent.getDocument();
  }

  public Parent getParent()
  {
    return this.parent;
  }

  public Element getParentElement()
  {
    Parent localParent1 = getParent();
    if ((localParent1 instanceof Element));
    for (Parent localParent2 = localParent1; ; localParent2 = null)
      return (Element)localParent2;
  }

  public abstract String getValue();

  public final int hashCode()
  {
    return super.hashCode();
  }

  protected Content setParent(Parent paramParent)
  {
    this.parent = paramParent;
    return this;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.Content
 * JD-Core Version:    0.6.2
 */