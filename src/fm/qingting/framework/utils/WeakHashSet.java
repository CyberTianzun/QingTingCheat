package fm.qingting.framework.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

public class WeakHashSet extends HashSet
{
  ReferenceQueue queue = new ReferenceQueue();

  private final Object getReferenceObject(WeakReference paramWeakReference)
  {
    if (paramWeakReference == null)
      return null;
    return paramWeakReference.get();
  }

  private final void processQueue()
  {
    while (true)
    {
      WeakElement localWeakElement = (WeakElement)this.queue.poll();
      if (localWeakElement == null)
        return;
      super.remove(localWeakElement);
    }
  }

  public boolean add(Object paramObject)
  {
    processQueue();
    return super.add(WeakElement.create(paramObject, this.queue));
  }

  public boolean contains(Object paramObject)
  {
    return super.contains(WeakElement.create(paramObject));
  }

  public Iterator iterator()
  {
    processQueue();
    return new Iterator()
    {
      public boolean hasNext()
      {
        return this.val$i.hasNext();
      }

      public Object next()
      {
        return WeakHashSet.this.getReferenceObject((WeakReference)this.val$i.next());
      }

      public void remove()
      {
        this.val$i.remove();
      }
    };
  }

  public boolean remove(Object paramObject)
  {
    boolean bool = super.remove(WeakElement.create(paramObject));
    processQueue();
    return bool;
  }

  private static class WeakElement extends WeakReference
  {
    private int hash;

    private WeakElement(Object paramObject)
    {
      super();
      this.hash = paramObject.hashCode();
    }

    private WeakElement(Object paramObject, ReferenceQueue paramReferenceQueue)
    {
      super(paramReferenceQueue);
      this.hash = paramObject.hashCode();
    }

    private static WeakElement create(Object paramObject)
    {
      if (paramObject == null)
        return null;
      return new WeakElement(paramObject);
    }

    private static WeakElement create(Object paramObject, ReferenceQueue paramReferenceQueue)
    {
      if (paramObject == null)
        return null;
      return new WeakElement(paramObject, paramReferenceQueue);
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      Object localObject1;
      Object localObject2;
      do
      {
        return true;
        if (!(paramObject instanceof WeakElement))
          return false;
        localObject1 = get();
        localObject2 = ((WeakElement)paramObject).get();
      }
      while (localObject1 == localObject2);
      if ((localObject1 == null) || (localObject2 == null))
        return false;
      return localObject1.equals(localObject2);
    }

    public int hashCode()
    {
      return this.hash;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.WeakHashSet
 * JD-Core Version:    0.6.2
 */