package org.jdom;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.jdom.filter.Filter;

final class ContentList extends AbstractList
  implements Serializable
{
  private static final String CVS_ID = "@(#) $RCSfile: ContentList.java,v $ $Revision: 1.42 $ $Date: 2007/11/10 05:28:58 $ $Name: jdom_1_1_1 $";
  private static final int INITIAL_ARRAY_SIZE = 5;
  private static final long serialVersionUID = 1L;
  private Content[] elementData;
  private Parent parent;
  private int size;

  ContentList(Parent paramParent)
  {
    this.parent = paramParent;
  }

  private void documentCanContain(int paramInt, Content paramContent)
    throws IllegalAddException
  {
    if ((paramContent instanceof Element))
    {
      if (indexOfFirstElement() >= 0)
        throw new IllegalAddException("Cannot add a second root element, only one is allowed");
      if (indexOfDocType() > paramInt)
        throw new IllegalAddException("A root element cannot be added before the DocType");
    }
    if ((paramContent instanceof DocType))
    {
      if (indexOfDocType() >= 0)
        throw new IllegalAddException("Cannot add a second doctype, only one is allowed");
      int i = indexOfFirstElement();
      if ((i != -1) && (i < paramInt))
        throw new IllegalAddException("A DocType cannot be added after the root element");
    }
    if ((paramContent instanceof CDATA))
      throw new IllegalAddException("A CDATA is not allowed at the document root");
    if ((paramContent instanceof Text))
      throw new IllegalAddException("A Text is not allowed at the document root");
    if ((paramContent instanceof EntityRef))
      throw new IllegalAddException("An EntityRef is not allowed at the document root");
  }

  private static void elementCanContain(int paramInt, Content paramContent)
    throws IllegalAddException
  {
    if ((paramContent instanceof DocType))
      throw new IllegalAddException("A DocType is not allowed except at the document level");
  }

  private int getModCount()
  {
    return this.modCount;
  }

  private static void removeParent(Content paramContent)
  {
    paramContent.setParent(null);
  }

  public void add(int paramInt, Object paramObject)
  {
    if (paramObject == null)
      throw new IllegalAddException("Cannot add null object");
    if ((paramObject instanceof String))
      paramObject = new Text(paramObject.toString());
    if ((paramObject instanceof Content))
    {
      add(paramInt, (Content)paramObject);
      return;
    }
    throw new IllegalAddException("Class " + paramObject.getClass().getName() + " is of unrecognized type and cannot be added");
  }

  void add(int paramInt, Content paramContent)
  {
    if (paramContent == null)
      throw new IllegalAddException("Cannot add null object");
    if ((this.parent instanceof Document))
      documentCanContain(paramInt, paramContent);
    while (paramContent.getParent() != null)
    {
      Parent localParent = paramContent.getParent();
      if ((localParent instanceof Document))
      {
        throw new IllegalAddException((Element)paramContent, "The Content already has an existing parent document");
        elementCanContain(paramInt, paramContent);
      }
      else
      {
        throw new IllegalAddException("The Content already has an existing parent \"" + ((Element)localParent).getQualifiedName() + "\"");
      }
    }
    if (paramContent == this.parent)
      throw new IllegalAddException("The Element cannot be added to itself");
    if (((this.parent instanceof Element)) && ((paramContent instanceof Element)) && (((Element)paramContent).isAncestor((Element)this.parent)))
      throw new IllegalAddException("The Element cannot be added as a descendent of itself");
    if ((paramInt < 0) || (paramInt > this.size))
      throw new IndexOutOfBoundsException("Index: " + paramInt + " Size: " + size());
    paramContent.setParent(this.parent);
    ensureCapacity(1 + this.size);
    if (paramInt == this.size)
    {
      Content[] arrayOfContent = this.elementData;
      int i = this.size;
      this.size = (i + 1);
      arrayOfContent[i] = paramContent;
    }
    while (true)
    {
      this.modCount = (1 + this.modCount);
      return;
      System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + 1, this.size - paramInt);
      this.elementData[paramInt] = paramContent;
      this.size = (1 + this.size);
    }
  }

  public boolean addAll(int paramInt, Collection paramCollection)
  {
    if ((paramInt < 0) || (paramInt > this.size))
      throw new IndexOutOfBoundsException("Index: " + paramInt + " Size: " + size());
    if ((paramCollection == null) || (paramCollection.size() == 0))
      return false;
    ensureCapacity(size() + paramCollection.size());
    int i = 0;
    try
    {
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        add(paramInt + i, localObject);
        i++;
      }
    }
    catch (RuntimeException localRuntimeException)
    {
      for (int j = 0; j < i; j++)
        remove(paramInt);
      throw localRuntimeException;
    }
    return true;
  }

  public boolean addAll(Collection paramCollection)
  {
    return addAll(size(), paramCollection);
  }

  public void clear()
  {
    if (this.elementData != null)
    {
      for (int i = 0; i < this.size; i++)
        removeParent(this.elementData[i]);
      this.elementData = null;
      this.size = 0;
    }
    this.modCount = (1 + this.modCount);
  }

  void clearAndSet(Collection paramCollection)
  {
    Content[] arrayOfContent = this.elementData;
    int i = this.size;
    this.elementData = null;
    this.size = 0;
    if ((paramCollection != null) && (paramCollection.size() != 0))
      ensureCapacity(paramCollection.size());
    try
    {
      addAll(0, paramCollection);
      if (arrayOfContent != null)
        for (int j = 0; j < i; j++)
          removeParent(arrayOfContent[j]);
    }
    catch (RuntimeException localRuntimeException)
    {
      this.elementData = arrayOfContent;
      this.size = i;
      throw localRuntimeException;
    }
    this.modCount = (1 + this.modCount);
  }

  void ensureCapacity(int paramInt)
  {
    if (this.elementData == null)
      this.elementData = new Content[Math.max(paramInt, 5)];
    int i;
    do
    {
      return;
      i = this.elementData.length;
    }
    while (paramInt <= i);
    Content[] arrayOfContent = this.elementData;
    int j = 1 + i * 3 / 2;
    if (j < paramInt)
      j = paramInt;
    this.elementData = new Content[j];
    System.arraycopy(arrayOfContent, 0, this.elementData, 0, this.size);
  }

  public Object get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.size))
      throw new IndexOutOfBoundsException("Index: " + paramInt + " Size: " + size());
    return this.elementData[paramInt];
  }

  List getView(Filter paramFilter)
  {
    return new FilterList(paramFilter);
  }

  int indexOfDocType()
  {
    if (this.elementData != null)
      for (int i = 0; i < this.size; i++)
        if ((this.elementData[i] instanceof DocType))
          return i;
    return -1;
  }

  int indexOfFirstElement()
  {
    if (this.elementData != null)
      for (int i = 0; i < this.size; i++)
        if ((this.elementData[i] instanceof Element))
          return i;
    return -1;
  }

  public Object remove(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.size))
      throw new IndexOutOfBoundsException("Index: " + paramInt + " Size: " + size());
    Content localContent = this.elementData[paramInt];
    removeParent(localContent);
    int i = -1 + (this.size - paramInt);
    if (i > 0)
      System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
    Content[] arrayOfContent = this.elementData;
    int j = -1 + this.size;
    this.size = j;
    arrayOfContent[j] = null;
    this.modCount = (1 + this.modCount);
    return localContent;
  }

  public Object set(int paramInt, Object paramObject)
  {
    if ((paramInt < 0) || (paramInt >= this.size))
      throw new IndexOutOfBoundsException("Index: " + paramInt + " Size: " + size());
    if (((paramObject instanceof Element)) && ((this.parent instanceof Document)))
    {
      int j = indexOfFirstElement();
      if ((j >= 0) && (j != paramInt))
        throw new IllegalAddException("Cannot add a second root element, only one is allowed");
    }
    if (((paramObject instanceof DocType)) && ((this.parent instanceof Document)))
    {
      int i = indexOfDocType();
      if ((i >= 0) && (i != paramInt))
        throw new IllegalAddException("Cannot add a second doctype, only one is allowed");
    }
    Object localObject = remove(paramInt);
    try
    {
      add(paramInt, paramObject);
      return localObject;
    }
    catch (RuntimeException localRuntimeException)
    {
      add(paramInt, localObject);
      throw localRuntimeException;
    }
  }

  public int size()
  {
    return this.size;
  }

  public String toString()
  {
    return super.toString();
  }

  final void uncheckedAddContent(Content paramContent)
  {
    paramContent.parent = this.parent;
    ensureCapacity(1 + this.size);
    Content[] arrayOfContent = this.elementData;
    int i = this.size;
    this.size = (i + 1);
    arrayOfContent[i] = paramContent;
    this.modCount = (1 + this.modCount);
  }

  class FilterList extends AbstractList
    implements Serializable
  {
    int count = 0;
    int expected = -1;
    Filter filter;

    FilterList(Filter arg2)
    {
      Object localObject;
      this.filter = localObject;
    }

    private final int getAdjustedIndex(int paramInt)
    {
      int i = 0;
      for (int j = 0; j < ContentList.this.size; j++)
      {
        Content localContent = ContentList.this.elementData[j];
        if (this.filter.matches(localContent))
        {
          if (paramInt == i)
            return j;
          i++;
        }
      }
      if (paramInt == i)
        return ContentList.this.size;
      return 1 + ContentList.this.size;
    }

    public void add(int paramInt, Object paramObject)
    {
      if (this.filter.matches(paramObject))
      {
        int i = getAdjustedIndex(paramInt);
        ContentList.this.add(i, paramObject);
        this.expected = (1 + this.expected);
        this.count = (1 + this.count);
        return;
      }
      throw new IllegalAddException("Filter won't allow the " + paramObject.getClass().getName() + " '" + paramObject + "' to be added to the list");
    }

    public Object get(int paramInt)
    {
      int i = getAdjustedIndex(paramInt);
      return ContentList.this.get(i);
    }

    public Iterator iterator()
    {
      return new ContentList.FilterListIterator(ContentList.this, this.filter, 0);
    }

    public ListIterator listIterator()
    {
      return new ContentList.FilterListIterator(ContentList.this, this.filter, 0);
    }

    public ListIterator listIterator(int paramInt)
    {
      return new ContentList.FilterListIterator(ContentList.this, this.filter, paramInt);
    }

    public Object remove(int paramInt)
    {
      int i = getAdjustedIndex(paramInt);
      Object localObject1 = ContentList.this.get(i);
      if (this.filter.matches(localObject1))
      {
        Object localObject2 = ContentList.this.remove(i);
        this.expected = (1 + this.expected);
        this.count = (-1 + this.count);
        return localObject2;
      }
      throw new IllegalAddException("Filter won't allow the " + localObject1.getClass().getName() + " '" + localObject1 + "' (index " + paramInt + ") to be removed");
    }

    public Object set(int paramInt, Object paramObject)
    {
      if (this.filter.matches(paramObject))
      {
        int i = getAdjustedIndex(paramInt);
        Object localObject1 = ContentList.this.get(i);
        if (!this.filter.matches(localObject1))
          throw new IllegalAddException("Filter won't allow the " + localObject1.getClass().getName() + " '" + localObject1 + "' (index " + paramInt + ") to be removed");
        Object localObject2 = ContentList.this.set(i, paramObject);
        this.expected = (2 + this.expected);
        return localObject2;
      }
      throw new IllegalAddException("Filter won't allow index " + paramInt + " to be set to " + paramObject.getClass().getName());
    }

    public int size()
    {
      if (this.expected == ContentList.this.getModCount())
        return this.count;
      this.count = 0;
      for (int i = 0; i < ContentList.this.size(); i++)
      {
        Content localContent = ContentList.this.elementData[i];
        if (this.filter.matches(localContent))
          this.count = (1 + this.count);
      }
      this.expected = ContentList.this.getModCount();
      return this.count;
    }
  }

  class FilterListIterator
    implements ListIterator
  {
    private boolean canremove = false;
    private boolean canset = false;
    private int cursor = -1;
    private int expected = -1;
    Filter filter;
    private boolean forward = false;
    private int fsize = 0;
    private int index = -1;
    private int tmpcursor = -1;

    FilterListIterator(Filter paramInt, int arg3)
    {
      this.filter = paramInt;
      this.expected = ContentList.this.getModCount();
      this.forward = false;
      int i;
      if (i < 0)
        throw new IndexOutOfBoundsException("Index: " + i);
      this.fsize = 0;
      for (int j = 0; j < ContentList.this.size(); j++)
        if (paramInt.matches(ContentList.this.get(j)))
        {
          if (i == this.fsize)
          {
            this.cursor = j;
            this.index = this.fsize;
          }
          this.fsize = (1 + this.fsize);
        }
      if (i > this.fsize)
        throw new IndexOutOfBoundsException("Index: " + i + " Size: " + this.fsize);
      if (this.cursor == -1)
      {
        this.cursor = ContentList.this.size();
        this.index = this.fsize;
      }
    }

    private void checkConcurrentModification()
    {
      if (this.expected != ContentList.this.getModCount())
        throw new ConcurrentModificationException();
    }

    public void add(Object paramObject)
    {
      nextIndex();
      ContentList.this.add(this.tmpcursor, paramObject);
      this.forward = true;
      this.expected = ContentList.this.getModCount();
      this.canset = false;
      this.canremove = false;
      this.index = nextIndex();
      this.cursor = this.tmpcursor;
      this.fsize = (1 + this.fsize);
    }

    public boolean hasNext()
    {
      return nextIndex() < this.fsize;
    }

    public boolean hasPrevious()
    {
      return previousIndex() >= 0;
    }

    public Object next()
    {
      if (!hasNext())
        throw new NoSuchElementException("next() is beyond the end of the Iterator");
      this.index = nextIndex();
      this.cursor = this.tmpcursor;
      this.forward = true;
      this.canremove = true;
      this.canset = true;
      return ContentList.this.get(this.cursor);
    }

    public int nextIndex()
    {
      checkConcurrentModification();
      if (this.forward)
      {
        for (int i = 1 + this.cursor; i < ContentList.this.size(); i++)
          if (this.filter.matches(ContentList.this.get(i)))
          {
            this.tmpcursor = i;
            return 1 + this.index;
          }
        this.tmpcursor = ContentList.this.size();
        return 1 + this.index;
      }
      this.tmpcursor = this.cursor;
      return this.index;
    }

    public Object previous()
    {
      if (!hasPrevious())
        throw new NoSuchElementException("previous() is before the start of the Iterator");
      this.index = previousIndex();
      this.cursor = this.tmpcursor;
      this.forward = false;
      this.canremove = true;
      this.canset = true;
      return ContentList.this.get(this.cursor);
    }

    public int previousIndex()
    {
      checkConcurrentModification();
      if (!this.forward)
      {
        for (int i = -1 + this.cursor; i >= 0; i--)
          if (this.filter.matches(ContentList.this.get(i)))
          {
            this.tmpcursor = i;
            return -1 + this.index;
          }
        this.tmpcursor = -1;
        return -1 + this.index;
      }
      this.tmpcursor = this.cursor;
      return this.index;
    }

    public void remove()
    {
      if (!this.canremove)
        throw new IllegalStateException("Can not remove an element unless either next() or previous() has been called since the last remove()");
      nextIndex();
      ContentList.this.remove(this.cursor);
      this.cursor = (-1 + this.tmpcursor);
      this.expected = ContentList.this.getModCount();
      this.forward = false;
      this.canremove = false;
      this.canset = false;
      this.fsize = (-1 + this.fsize);
    }

    public void set(Object paramObject)
    {
      if (!this.canset)
        throw new IllegalStateException("Can not set an element unless either next() or previous() has been called since the last remove() or set()");
      checkConcurrentModification();
      if (!this.filter.matches(paramObject))
        throw new IllegalAddException("Filter won't allow index " + this.index + " to be set to " + paramObject.getClass().getName());
      ContentList.this.set(this.cursor, paramObject);
      this.expected = ContentList.this.getModCount();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.ContentList
 * JD-Core Version:    0.6.2
 */