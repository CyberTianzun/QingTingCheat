package org.jdom;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class AttributeList extends AbstractList
  implements List, Serializable
{
  private static final String CVS_ID = "@(#) $RCSfile: AttributeList.java,v $ $Revision: 1.24 $ $Date: 2007/11/10 05:28:58 $ $Name: jdom_1_1_1 $";
  private static final int INITIAL_ARRAY_SIZE = 5;
  private Attribute[] elementData;
  private Element parent;
  private int size;

  private AttributeList()
  {
  }

  AttributeList(Element paramElement)
  {
    this.parent = paramElement;
  }

  private void ensureCapacity(int paramInt)
  {
    if (this.elementData == null)
      this.elementData = new Attribute[Math.max(paramInt, 5)];
    int i;
    do
    {
      return;
      i = this.elementData.length;
    }
    while (paramInt <= i);
    Attribute[] arrayOfAttribute = this.elementData;
    int j = 1 + i * 3 / 2;
    if (j < paramInt)
      j = paramInt;
    this.elementData = new Attribute[j];
    System.arraycopy(arrayOfAttribute, 0, this.elementData, 0, this.size);
  }

  private int indexOfDuplicate(Attribute paramAttribute)
  {
    return indexOf(paramAttribute.getName(), paramAttribute.getNamespace());
  }

  public void add(int paramInt, Object paramObject)
  {
    if ((paramObject instanceof Attribute))
    {
      Attribute localAttribute = (Attribute)paramObject;
      if (indexOfDuplicate(localAttribute) >= 0)
        throw new IllegalAddException("Cannot add duplicate attribute");
      add(paramInt, localAttribute);
      this.modCount = (1 + this.modCount);
      return;
    }
    if (paramObject == null)
      throw new IllegalAddException("Cannot add null attribute");
    throw new IllegalAddException("Class " + paramObject.getClass().getName() + " is not an attribute");
  }

  void add(int paramInt, Attribute paramAttribute)
  {
    if (paramAttribute.getParent() != null)
      throw new IllegalAddException("The attribute already has an existing parent \"" + paramAttribute.getParent().getQualifiedName() + "\"");
    String str = Verifier.checkNamespaceCollision(paramAttribute, this.parent);
    if (str != null)
      throw new IllegalAddException(this.parent, paramAttribute, str);
    if ((paramInt < 0) || (paramInt > this.size))
      throw new IndexOutOfBoundsException("Index: " + paramInt + " Size: " + size());
    paramAttribute.setParent(this.parent);
    ensureCapacity(1 + this.size);
    if (paramInt == this.size)
    {
      Attribute[] arrayOfAttribute = this.elementData;
      int i = this.size;
      this.size = (i + 1);
      arrayOfAttribute[i] = paramAttribute;
    }
    while (true)
    {
      this.modCount = (1 + this.modCount);
      return;
      System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + 1, this.size - paramInt);
      this.elementData[paramInt] = paramAttribute;
      this.size = (1 + this.size);
    }
  }

  public boolean add(Object paramObject)
  {
    if ((paramObject instanceof Attribute))
    {
      Attribute localAttribute = (Attribute)paramObject;
      int i = indexOfDuplicate(localAttribute);
      if (i < 0)
        add(size(), localAttribute);
      while (true)
      {
        return true;
        set(i, localAttribute);
      }
    }
    if (paramObject == null)
      throw new IllegalAddException("Cannot add null attribute");
    throw new IllegalAddException("Class " + paramObject.getClass().getName() + " is not an attribute");
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
        this.elementData[i].setParent(null);
      this.elementData = null;
      this.size = 0;
    }
    this.modCount = (1 + this.modCount);
  }

  void clearAndSet(Collection paramCollection)
  {
    Attribute[] arrayOfAttribute = this.elementData;
    int i = this.size;
    this.elementData = null;
    this.size = 0;
    if ((paramCollection != null) && (paramCollection.size() != 0))
      ensureCapacity(paramCollection.size());
    try
    {
      addAll(0, paramCollection);
      if (arrayOfAttribute != null)
        for (int j = 0; j < i; j++)
          arrayOfAttribute[j].setParent(null);
    }
    catch (RuntimeException localRuntimeException)
    {
      this.elementData = arrayOfAttribute;
      this.size = i;
      throw localRuntimeException;
    }
    this.modCount = (1 + this.modCount);
  }

  public Object get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.size))
      throw new IndexOutOfBoundsException("Index: " + paramInt + " Size: " + size());
    return this.elementData[paramInt];
  }

  Object get(String paramString, Namespace paramNamespace)
  {
    int i = indexOf(paramString, paramNamespace);
    if (i < 0)
      return null;
    return this.elementData[i];
  }

  int indexOf(String paramString, Namespace paramNamespace)
  {
    String str1 = paramNamespace.getURI();
    if (this.elementData != null)
      for (int i = 0; i < this.size; i++)
      {
        Attribute localAttribute = this.elementData[i];
        String str2 = localAttribute.getNamespaceURI();
        String str3 = localAttribute.getName();
        if ((str2.equals(str1)) && (str3.equals(paramString)))
          return i;
      }
    return -1;
  }

  public Object remove(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.size))
      throw new IndexOutOfBoundsException("Index: " + paramInt + " Size: " + size());
    Attribute localAttribute = this.elementData[paramInt];
    localAttribute.setParent(null);
    int i = -1 + (this.size - paramInt);
    if (i > 0)
      System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
    Attribute[] arrayOfAttribute = this.elementData;
    int j = -1 + this.size;
    this.size = j;
    arrayOfAttribute[j] = null;
    this.modCount = (1 + this.modCount);
    return localAttribute;
  }

  boolean remove(String paramString, Namespace paramNamespace)
  {
    int i = indexOf(paramString, paramNamespace);
    if (i < 0)
      return false;
    remove(i);
    return true;
  }

  public Object set(int paramInt, Object paramObject)
  {
    if ((paramObject instanceof Attribute))
    {
      Attribute localAttribute = (Attribute)paramObject;
      int i = indexOfDuplicate(localAttribute);
      if ((i >= 0) && (i != paramInt))
        throw new IllegalAddException("Cannot set duplicate attribute");
      return set(paramInt, localAttribute);
    }
    if (paramObject == null)
      throw new IllegalAddException("Cannot add null attribute");
    throw new IllegalAddException("Class " + paramObject.getClass().getName() + " is not an attribute");
  }

  Object set(int paramInt, Attribute paramAttribute)
  {
    if ((paramInt < 0) || (paramInt >= this.size))
      throw new IndexOutOfBoundsException("Index: " + paramInt + " Size: " + size());
    if (paramAttribute.getParent() != null)
      throw new IllegalAddException("The attribute already has an existing parent \"" + paramAttribute.getParent().getQualifiedName() + "\"");
    String str = Verifier.checkNamespaceCollision(paramAttribute, this.parent);
    if (str != null)
      throw new IllegalAddException(this.parent, paramAttribute, str);
    Attribute localAttribute = this.elementData[paramInt];
    localAttribute.setParent(null);
    this.elementData[paramInt] = paramAttribute;
    paramAttribute.setParent(this.parent);
    return localAttribute;
  }

  public int size()
  {
    return this.size;
  }

  public String toString()
  {
    return super.toString();
  }

  final void uncheckedAddAttribute(Attribute paramAttribute)
  {
    paramAttribute.parent = this.parent;
    ensureCapacity(1 + this.size);
    Attribute[] arrayOfAttribute = this.elementData;
    int i = this.size;
    this.size = (i + 1);
    arrayOfAttribute[i] = paramAttribute;
    this.modCount = (1 + this.modCount);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.AttributeList
 * JD-Core Version:    0.6.2
 */