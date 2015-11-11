package fm.qingting.qtradio.mem.cache.data;

public class ConcurrentNode<E>
  implements DoubleLinkedNode<E>
{
  private ConcurrentNode<E> next;
  private ConcurrentNode<E> prev;
  private E value;

  public ConcurrentNode(E paramE)
  {
    this.value = paramE;
  }

  private boolean isRemoved()
  {
    if ((getNext() != null) && (getNext().getPrev() != this));
    while ((getPrev() != null) && (getPrev().getNext() != this))
      return true;
    return false;
  }

  private void setNext(ConcurrentNode<E> paramConcurrentNode)
  {
    try
    {
      this.next = paramConcurrentNode;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void setPrev(ConcurrentNode<E> paramConcurrentNode)
  {
    try
    {
      this.prev = paramConcurrentNode;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public DoubleLinkedNode<E> getNext()
  {
    return this.next;
  }

  public DoubleLinkedNode<E> getPrev()
  {
    return this.prev;
  }

  public E getValue()
  {
    return this.value;
  }

  public boolean insertAfter(E paramE)
  {
    if (isRemoved())
    {
      if (getNext() != null)
      {
        getNext().insertBefore(paramE);
        return true;
      }
      if (getPrev() != null)
      {
        getPrev().insertAfter(paramE);
        return true;
      }
      return false;
    }
    while (true)
    {
      ConcurrentNode localConcurrentNode1;
      try
      {
        localConcurrentNode1 = new ConcurrentNode(paramE);
        if (this.next == null)
        {
          localConcurrentNode1.setNext(null);
          localConcurrentNode1.setPrev(this);
          setNext(localConcurrentNode1);
          return true;
        }
      }
      finally
      {
      }
      synchronized (this.next)
      {
        this.next.setPrev(localConcurrentNode1);
        localConcurrentNode1.setNext(this.next);
        localConcurrentNode1.setPrev(this);
        setNext(localConcurrentNode1);
      }
    }
  }

  public boolean insertBefore(E paramE)
  {
    if (isRemoved())
    {
      if (getPrev() != null)
        return getPrev().insertAfter(paramE);
      if (getNext() != null)
        return getNext().insertBefore(paramE);
      return false;
    }
    try
    {
      ConcurrentNode localConcurrentNode1 = new ConcurrentNode(paramE);
      if (this.prev == null)
      {
        localConcurrentNode1.setNext(this);
        localConcurrentNode1.setPrev(null);
        setPrev(localConcurrentNode1);
      }
      while (true)
      {
        return true;
        synchronized (this.prev)
        {
          this.prev.setNext(localConcurrentNode1);
          localConcurrentNode1.setPrev(localConcurrentNode1);
          localConcurrentNode1.setNext(this);
          setPrev(localConcurrentNode1);
        }
      }
    }
    finally
    {
    }
  }

  public void removeNext()
  {
    if (this.next != null)
      this.next.removeSelf();
  }

  public void removePrev()
  {
    if (this.prev != null)
      this.prev.removeSelf();
  }

  // ERROR //
  public void removeSelf()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   4: ifnonnull +27 -> 31
    //   7: aload_0
    //   8: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   11: ifnonnull +20 -> 31
    //   14: aload_0
    //   15: monitorenter
    //   16: aload_0
    //   17: aconst_null
    //   18: putfield 21	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:value	Ljava/lang/Object;
    //   21: aload_0
    //   22: monitorexit
    //   23: return
    //   24: astore 13
    //   26: aload_0
    //   27: monitorexit
    //   28: aload 13
    //   30: athrow
    //   31: aload_0
    //   32: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   35: ifnonnull +66 -> 101
    //   38: aload_0
    //   39: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   42: ifnull +59 -> 101
    //   45: aload_0
    //   46: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   49: astore 10
    //   51: aload 10
    //   53: monitorenter
    //   54: aload_0
    //   55: monitorenter
    //   56: aload_0
    //   57: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   60: invokevirtual 27	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:getNext	()Lfm/qingting/qtradio/mem/cache/data/DoubleLinkedNode;
    //   63: aload_0
    //   64: if_acmpne +16 -> 80
    //   67: aload_0
    //   68: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   71: aconst_null
    //   72: invokespecial 54	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:setNext	(Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;)V
    //   75: aload_0
    //   76: aconst_null
    //   77: putfield 21	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:value	Ljava/lang/Object;
    //   80: aload_0
    //   81: monitorexit
    //   82: aload 10
    //   84: monitorexit
    //   85: return
    //   86: astore 11
    //   88: aload 10
    //   90: monitorexit
    //   91: aload 11
    //   93: athrow
    //   94: astore 12
    //   96: aload_0
    //   97: monitorexit
    //   98: aload 12
    //   100: athrow
    //   101: aload_0
    //   102: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   105: ifnull +66 -> 171
    //   108: aload_0
    //   109: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   112: ifnonnull +59 -> 171
    //   115: aload_0
    //   116: monitorenter
    //   117: aload_0
    //   118: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   121: astore 8
    //   123: aload 8
    //   125: monitorenter
    //   126: aload_0
    //   127: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   130: invokevirtual 31	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:getPrev	()Lfm/qingting/qtradio/mem/cache/data/DoubleLinkedNode;
    //   133: aload_0
    //   134: if_acmpne +16 -> 150
    //   137: aload_0
    //   138: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   141: aconst_null
    //   142: invokespecial 56	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:setPrev	(Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;)V
    //   145: aload_0
    //   146: aconst_null
    //   147: putfield 21	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:value	Ljava/lang/Object;
    //   150: aload 8
    //   152: monitorexit
    //   153: aload_0
    //   154: monitorexit
    //   155: return
    //   156: astore 7
    //   158: aload_0
    //   159: monitorexit
    //   160: aload 7
    //   162: athrow
    //   163: astore 9
    //   165: aload 8
    //   167: monitorexit
    //   168: aload 9
    //   170: athrow
    //   171: aload_0
    //   172: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   175: ifnull +105 -> 280
    //   178: aload_0
    //   179: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   182: ifnull +98 -> 280
    //   185: aload_0
    //   186: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   189: astore_2
    //   190: aload_2
    //   191: monitorenter
    //   192: aload_0
    //   193: monitorenter
    //   194: aload_0
    //   195: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   198: astore 5
    //   200: aload 5
    //   202: monitorenter
    //   203: aload_0
    //   204: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   207: invokevirtual 31	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:getPrev	()Lfm/qingting/qtradio/mem/cache/data/DoubleLinkedNode;
    //   210: aload_0
    //   211: if_acmpne +41 -> 252
    //   214: aload_0
    //   215: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   218: invokevirtual 27	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:getNext	()Lfm/qingting/qtradio/mem/cache/data/DoubleLinkedNode;
    //   221: aload_0
    //   222: if_acmpne +30 -> 252
    //   225: aload_0
    //   226: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   229: aload_0
    //   230: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   233: invokespecial 54	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:setNext	(Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;)V
    //   236: aload_0
    //   237: getfield 36	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:next	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   240: aload_0
    //   241: getfield 39	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:prev	Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;
    //   244: invokespecial 56	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:setPrev	(Lfm/qingting/qtradio/mem/cache/data/ConcurrentNode;)V
    //   247: aload_0
    //   248: aconst_null
    //   249: putfield 21	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:value	Ljava/lang/Object;
    //   252: aload 5
    //   254: monitorexit
    //   255: aload_0
    //   256: monitorexit
    //   257: aload_2
    //   258: monitorexit
    //   259: return
    //   260: astore_3
    //   261: aload_2
    //   262: monitorexit
    //   263: aload_3
    //   264: athrow
    //   265: astore 6
    //   267: aload 5
    //   269: monitorexit
    //   270: aload 6
    //   272: athrow
    //   273: astore 4
    //   275: aload_0
    //   276: monitorexit
    //   277: aload 4
    //   279: athrow
    //   280: aload_0
    //   281: monitorenter
    //   282: aload_0
    //   283: aconst_null
    //   284: putfield 21	fm/qingting/qtradio/mem/cache/data/ConcurrentNode:value	Ljava/lang/Object;
    //   287: aload_0
    //   288: monitorexit
    //   289: return
    //   290: astore_1
    //   291: aload_0
    //   292: monitorexit
    //   293: aload_1
    //   294: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   16	23	24	finally
    //   26	28	24	finally
    //   54	56	86	finally
    //   82	85	86	finally
    //   88	91	86	finally
    //   98	101	86	finally
    //   56	80	94	finally
    //   80	82	94	finally
    //   96	98	94	finally
    //   117	126	156	finally
    //   153	155	156	finally
    //   158	160	156	finally
    //   168	171	156	finally
    //   126	150	163	finally
    //   150	153	163	finally
    //   165	168	163	finally
    //   192	194	260	finally
    //   257	259	260	finally
    //   261	263	260	finally
    //   277	280	260	finally
    //   203	252	265	finally
    //   252	255	265	finally
    //   267	270	265	finally
    //   194	203	273	finally
    //   255	257	273	finally
    //   270	273	273	finally
    //   275	277	273	finally
    //   282	289	290	finally
    //   291	293	290	finally
  }

  public void setValue(E paramE)
  {
    try
    {
      this.value = paramE;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.mem.cache.data.ConcurrentNode
 * JD-Core Version:    0.6.2
 */