package org.jdom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class DescendantIterator
  implements Iterator
{
  private static final String CVS_ID = "@(#) $RCSfile: DescendantIterator.java,v $ $Revision: 1.6 $ $Date: 2007/11/10 05:28:58 $ $Name: jdom_1_1_1 $";
  private Iterator iterator;
  private Iterator nextIterator;
  private List stack = new ArrayList();

  DescendantIterator(Parent paramParent)
  {
    if (paramParent == null)
      throw new IllegalArgumentException("parent parameter was null");
    this.iterator = paramParent.getContent().iterator();
  }

  private Iterator pop()
  {
    int i = this.stack.size();
    if (i == 0)
      throw new NoSuchElementException("empty stack");
    return (Iterator)this.stack.remove(i - 1);
  }

  private void push(Iterator paramIterator)
  {
    this.stack.add(paramIterator);
  }

  private boolean stackHasAnyNext()
  {
    int i = this.stack.size();
    for (int j = 0; j < i; j++)
      if (((Iterator)this.stack.get(j)).hasNext())
        return true;
    return false;
  }

  public boolean hasNext()
  {
    if ((this.iterator != null) && (this.iterator.hasNext()));
    while (((this.nextIterator != null) && (this.nextIterator.hasNext())) || (stackHasAnyNext()))
      return true;
    return false;
  }

  public Object next()
  {
    if (!hasNext())
      throw new NoSuchElementException();
    if (this.nextIterator != null)
    {
      push(this.iterator);
      this.iterator = this.nextIterator;
      this.nextIterator = null;
    }
    while (!this.iterator.hasNext())
      if (this.stack.size() > 0)
        this.iterator = pop();
      else
        throw new NoSuchElementException("Somehow we lost our iterator");
    Content localContent = (Content)this.iterator.next();
    if ((localContent instanceof Element))
      this.nextIterator = ((Element)localContent).getContent().iterator();
    return localContent;
  }

  public void remove()
  {
    this.iterator.remove();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.DescendantIterator
 * JD-Core Version:    0.6.2
 */