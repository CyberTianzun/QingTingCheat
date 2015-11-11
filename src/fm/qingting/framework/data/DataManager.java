package fm.qingting.framework.data;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataManager
  implements IDataRecvHandler
{
  private static final int DATACOMPLETE = 1;
  private static DataManager instance = null;
  private HashMap<IDataToken, ResultToken> _cbMap = new HashMap();
  private HashSet<WeakReference<IDataRecvHandler>> _dataListeners = new HashSet();
  private Handler _dispatchHandler = new DispatchHandler(Looper.getMainLooper());
  private HashMap<String, IDataSource> _dsMap = new HashMap();
  private HashMap<String, IDataSourceProxy> _proxyMap = new HashMap();
  private HashSet<WeakReference<IResultRecvHandler>> _resultListeners = new HashSet();
  private final int retryCount = 3;

  private void doCommand(IDataSource paramIDataSource, DataCommand paramDataCommand, ResultToken paramResultToken)
  {
    while (true)
    {
      try
      {
        if (paramIDataSource.isSynchronous(paramDataCommand.getType(), paramDataCommand.getParam()))
        {
          IDataToken localIDataToken2 = paramIDataSource.doCommand(paramDataCommand, null);
          if ((localIDataToken2 != null) || (paramDataCommand.getNextCount() >= 3))
          {
            if (localIDataToken2 != null)
            {
              localResult = (Result)localIDataToken2.getData();
              paramResultToken.setResult(localResult);
              sendResult(paramResultToken);
            }
          }
          else
          {
            paramDataCommand.getNextCommand();
            localIDataToken2 = paramIDataSource.doCommand(paramDataCommand, null);
            continue;
          }
          Result localResult = new Result(false, null, DataError.DATASOURCE_ERROR.getCode(), DataError.DATASOURCE_ERROR.getMessage());
          continue;
        }
        localIDataToken1 = paramIDataSource.doCommand(paramDataCommand, this);
        if ((localIDataToken1 != null) || (paramDataCommand.getNextCount() >= 3))
        {
          if (localIDataToken1 == null)
            break label184;
          this._cbMap.put(localIDataToken1, paramResultToken);
          continue;
        }
      }
      finally
      {
      }
      paramDataCommand.getNextCommand();
      IDataToken localIDataToken1 = paramIDataSource.doCommand(paramDataCommand, this);
      continue;
      label184: paramResultToken.setResult(new Result(false, null, DataError.DATASOURCE_ERROR.getCode(), DataError.DATASOURCE_ERROR.getMessage()));
      sendResult(paramResultToken);
    }
  }

  public static DataManager getInstance()
  {
    try
    {
      if (instance == null)
        instance = new DataManager();
      DataManager localDataManager = instance;
      return localDataManager;
    }
    finally
    {
    }
  }

  private void sendResult(ResultToken paramResultToken)
  {
    Message localMessage = new Message();
    localMessage.what = 1;
    localMessage.obj = paramResultToken;
    this._dispatchHandler.sendMessage(localMessage);
  }

  public void addDataListener(IDataRecvHandler paramIDataRecvHandler)
  {
    IDataRecvHandler localIDataRecvHandler;
    do
      synchronized (this._dataListeners)
      {
        Iterator localIterator = this._dataListeners.iterator();
        if (!localIterator.hasNext())
        {
          this._dataListeners.add(new WeakReference(paramIDataRecvHandler));
          return;
        }
        localIDataRecvHandler = (IDataRecvHandler)((WeakReference)localIterator.next()).get();
        if (localIDataRecvHandler == null)
          localIterator.remove();
      }
    while (paramIDataRecvHandler != localIDataRecvHandler);
  }

  public void addDataSource(IDataSource paramIDataSource)
  {
    try
    {
      this._dsMap.put(paramIDataSource.dataSourceName().toLowerCase(), paramIDataSource);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void addDataSourceProxy(IDataSourceProxy paramIDataSourceProxy)
  {
    try
    {
      this._proxyMap.put(paramIDataSourceProxy.dataSourceName().toLowerCase(), paramIDataSourceProxy);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void addResultListener(IResultRecvHandler paramIResultRecvHandler)
  {
    IResultRecvHandler localIResultRecvHandler;
    do
      synchronized (this._resultListeners)
      {
        Iterator localIterator = this._resultListeners.iterator();
        if (!localIterator.hasNext())
        {
          this._resultListeners.add(new WeakReference(paramIResultRecvHandler));
          return;
        }
        localIResultRecvHandler = (IResultRecvHandler)((WeakReference)localIterator.next()).get();
        if (localIResultRecvHandler == null)
          localIterator.remove();
      }
    while (paramIResultRecvHandler != localIResultRecvHandler);
  }

  protected void dispatchDataEvent(boolean paramBoolean, Object paramObject1, String paramString1, String paramString2, Object paramObject2, IDataToken paramIDataToken, Object paramObject3)
  {
    Iterator localIterator = new ArrayList(this._dataListeners).iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      IDataRecvHandler localIDataRecvHandler = (IDataRecvHandler)localWeakReference.get();
      if (localIDataRecvHandler == null)
        this._dataListeners.remove(localWeakReference);
      else if (paramBoolean)
        localIDataRecvHandler.onRecvData(paramObject1, paramObject2, paramIDataToken, paramObject3);
      else
        localIDataRecvHandler.onRecvError(paramString1, paramString2, paramObject2, paramIDataToken, paramObject3);
    }
  }

  protected void dispatchResultEvent(Result paramResult, Object paramObject1, IResultToken paramIResultToken, Object paramObject2)
  {
    Iterator localIterator = new ArrayList(this._resultListeners).iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      IResultRecvHandler localIResultRecvHandler = (IResultRecvHandler)localWeakReference.get();
      if (localIResultRecvHandler == null)
        this._resultListeners.remove(localWeakReference);
      else
        localIResultRecvHandler.onRecvResult(paramResult, paramObject1, paramIResultToken, paramObject2);
    }
  }

  public IResultToken getData(String paramString, IResultRecvHandler paramIResultRecvHandler, Map<String, Object> paramMap)
  {
    try
    {
      IResultToken localIResultToken = getData(paramString, paramIResultRecvHandler, paramMap, true);
      return localIResultToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  // ERROR //
  public IResultToken getData(String paramString, IResultRecvHandler paramIResultRecvHandler, Map<String, Object> paramMap, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new 101	fm/qingting/framework/data/ResultToken
    //   5: dup
    //   6: invokespecial 227	fm/qingting/framework/data/ResultToken:<init>	()V
    //   9: astore 5
    //   11: aload 5
    //   13: aload_2
    //   14: invokevirtual 230	fm/qingting/framework/data/ResultToken:setResultRecvHandler	(Lfm/qingting/framework/data/IResultRecvHandler;)V
    //   17: aload 5
    //   19: aload_1
    //   20: invokevirtual 234	fm/qingting/framework/data/ResultToken:setType	(Ljava/lang/String;)V
    //   23: aload 5
    //   25: aload_3
    //   26: invokevirtual 237	fm/qingting/framework/data/ResultToken:setParametets	(Ljava/lang/Object;)V
    //   29: invokestatic 242	fm/qingting/framework/data/ServerConfig:getInstance	()Lfm/qingting/framework/data/ServerConfig;
    //   32: aload_1
    //   33: invokevirtual 246	fm/qingting/framework/data/ServerConfig:getRequestTrait	(Ljava/lang/String;)Lfm/qingting/framework/data/RequestTrait;
    //   36: astore 7
    //   38: aload 7
    //   40: ifnonnull +40 -> 80
    //   43: aload 5
    //   45: new 99	fm/qingting/framework/data/Result
    //   48: dup
    //   49: iconst_0
    //   50: aconst_null
    //   51: getstatic 249	fm/qingting/framework/data/DataError:REQUEST_ERROR	Lfm/qingting/framework/data/DataError;
    //   54: invokevirtual 121	fm/qingting/framework/data/DataError:getCode	()Ljava/lang/String;
    //   57: getstatic 249	fm/qingting/framework/data/DataError:REQUEST_ERROR	Lfm/qingting/framework/data/DataError;
    //   60: invokevirtual 124	fm/qingting/framework/data/DataError:getMessage	()Ljava/lang/String;
    //   63: invokespecial 127	fm/qingting/framework/data/Result:<init>	(ZLjava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V
    //   66: invokevirtual 105	fm/qingting/framework/data/ResultToken:setResult	(Lfm/qingting/framework/data/Result;)V
    //   69: aload_0
    //   70: aload 5
    //   72: invokespecial 109	fm/qingting/framework/data/DataManager:sendResult	(Lfm/qingting/framework/data/ResultToken;)V
    //   75: aload_0
    //   76: monitorexit
    //   77: aload 5
    //   79: areturn
    //   80: aload 5
    //   82: aload 7
    //   84: invokevirtual 254	fm/qingting/framework/data/RequestTrait:getDataType	()Ljava/lang/String;
    //   87: invokevirtual 257	fm/qingting/framework/data/ResultToken:setDataType	(Ljava/lang/String;)V
    //   90: aload 7
    //   92: getfield 261	fm/qingting/framework/data/RequestTrait:dataSource	Ljava/lang/String;
    //   95: ifnonnull +45 -> 140
    //   98: aload 5
    //   100: new 99	fm/qingting/framework/data/Result
    //   103: dup
    //   104: iconst_0
    //   105: aconst_null
    //   106: getstatic 264	fm/qingting/framework/data/DataError:CONFIG_ERROR	Lfm/qingting/framework/data/DataError;
    //   109: invokevirtual 121	fm/qingting/framework/data/DataError:getCode	()Ljava/lang/String;
    //   112: getstatic 264	fm/qingting/framework/data/DataError:CONFIG_ERROR	Lfm/qingting/framework/data/DataError;
    //   115: invokevirtual 124	fm/qingting/framework/data/DataError:getMessage	()Ljava/lang/String;
    //   118: invokespecial 127	fm/qingting/framework/data/Result:<init>	(ZLjava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V
    //   121: invokevirtual 105	fm/qingting/framework/data/ResultToken:setResult	(Lfm/qingting/framework/data/Result;)V
    //   124: aload_0
    //   125: aload 5
    //   127: invokespecial 109	fm/qingting/framework/data/DataManager:sendResult	(Lfm/qingting/framework/data/ResultToken;)V
    //   130: goto -55 -> 75
    //   133: astore 6
    //   135: aload_0
    //   136: monitorexit
    //   137: aload 6
    //   139: athrow
    //   140: aload_0
    //   141: getfield 42	fm/qingting/framework/data/DataManager:_dsMap	Ljava/util/HashMap;
    //   144: aload 7
    //   146: getfield 261	fm/qingting/framework/data/RequestTrait:dataSource	Ljava/lang/String;
    //   149: invokevirtual 190	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   152: invokevirtual 267	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   155: checkcast 80	fm/qingting/framework/data/IDataSource
    //   158: astore 8
    //   160: aload 8
    //   162: ifnonnull +38 -> 200
    //   165: aload 5
    //   167: new 99	fm/qingting/framework/data/Result
    //   170: dup
    //   171: iconst_0
    //   172: aconst_null
    //   173: getstatic 118	fm/qingting/framework/data/DataError:DATASOURCE_ERROR	Lfm/qingting/framework/data/DataError;
    //   176: invokevirtual 121	fm/qingting/framework/data/DataError:getCode	()Ljava/lang/String;
    //   179: getstatic 118	fm/qingting/framework/data/DataError:DATASOURCE_ERROR	Lfm/qingting/framework/data/DataError;
    //   182: invokevirtual 124	fm/qingting/framework/data/DataError:getMessage	()Ljava/lang/String;
    //   185: invokespecial 127	fm/qingting/framework/data/Result:<init>	(ZLjava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V
    //   188: invokevirtual 105	fm/qingting/framework/data/ResultToken:setResult	(Lfm/qingting/framework/data/Result;)V
    //   191: aload_0
    //   192: aload 5
    //   194: invokespecial 109	fm/qingting/framework/data/DataManager:sendResult	(Lfm/qingting/framework/data/ResultToken;)V
    //   197: goto -122 -> 75
    //   200: new 70	fm/qingting/framework/data/DataCommand
    //   203: dup
    //   204: aload 7
    //   206: aload_3
    //   207: invokespecial 270	fm/qingting/framework/data/DataCommand:<init>	(Lfm/qingting/framework/data/RequestTrait;Ljava/util/Map;)V
    //   210: astore 9
    //   212: invokestatic 242	fm/qingting/framework/data/ServerConfig:getInstance	()Lfm/qingting/framework/data/ServerConfig;
    //   215: aload 8
    //   217: invokeinterface 185 1 0
    //   222: invokevirtual 274	fm/qingting/framework/data/ServerConfig:getDefaultProxy	(Ljava/lang/String;)Ljava/lang/String;
    //   225: astore 10
    //   227: aconst_null
    //   228: astore 11
    //   230: iload 4
    //   232: ifeq +67 -> 299
    //   235: aload 7
    //   237: getfield 277	fm/qingting/framework/data/RequestTrait:proxy	Ljava/lang/String;
    //   240: ifnull +149 -> 389
    //   243: aload 7
    //   245: getfield 277	fm/qingting/framework/data/RequestTrait:proxy	Ljava/lang/String;
    //   248: ldc_w 279
    //   251: invokevirtual 283	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   254: ifne +135 -> 389
    //   257: aload_0
    //   258: getfield 44	fm/qingting/framework/data/DataManager:_proxyMap	Ljava/util/HashMap;
    //   261: aload 7
    //   263: getfield 277	fm/qingting/framework/data/RequestTrait:proxy	Ljava/lang/String;
    //   266: invokevirtual 190	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   269: invokevirtual 267	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   272: checkcast 194	fm/qingting/framework/data/IDataSourceProxy
    //   275: astore 11
    //   277: aload 11
    //   279: ifnull +20 -> 299
    //   282: aload 11
    //   284: aload 9
    //   286: aload 8
    //   288: invokeinterface 287 3 0
    //   293: ifne +6 -> 299
    //   296: aconst_null
    //   297: astore 11
    //   299: aload 11
    //   301: ifnonnull +116 -> 417
    //   304: aload 8
    //   306: aload 9
    //   308: invokevirtual 74	fm/qingting/framework/data/DataCommand:getType	()Ljava/lang/String;
    //   311: aload_3
    //   312: invokeinterface 84 3 0
    //   317: istore 12
    //   319: goto +270 -> 589
    //   322: aload 13
    //   324: monitorenter
    //   325: iload 12
    //   327: ifeq +164 -> 491
    //   330: aload 11
    //   332: ifnonnull +116 -> 448
    //   335: aload 8
    //   337: aload 9
    //   339: aconst_null
    //   340: invokeinterface 87 3 0
    //   345: astore 18
    //   347: aload 18
    //   349: ifnull +116 -> 465
    //   352: aload 18
    //   354: invokeinterface 97 1 0
    //   359: checkcast 99	fm/qingting/framework/data/Result
    //   362: astore 19
    //   364: aload 5
    //   366: aload 19
    //   368: invokevirtual 105	fm/qingting/framework/data/ResultToken:setResult	(Lfm/qingting/framework/data/Result;)V
    //   371: aload_0
    //   372: aload 5
    //   374: invokespecial 109	fm/qingting/framework/data/DataManager:sendResult	(Lfm/qingting/framework/data/ResultToken;)V
    //   377: aload 13
    //   379: monitorexit
    //   380: goto -305 -> 75
    //   383: aload 13
    //   385: monitorexit
    //   386: aload 16
    //   388: athrow
    //   389: aconst_null
    //   390: astore 11
    //   392: aload 10
    //   394: ifnull -117 -> 277
    //   397: aload_0
    //   398: getfield 44	fm/qingting/framework/data/DataManager:_proxyMap	Ljava/util/HashMap;
    //   401: aload 10
    //   403: invokevirtual 190	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   406: invokevirtual 267	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   409: checkcast 194	fm/qingting/framework/data/IDataSourceProxy
    //   412: astore 11
    //   414: goto -137 -> 277
    //   417: aload 11
    //   419: aload 9
    //   421: invokevirtual 74	fm/qingting/framework/data/DataCommand:getType	()Ljava/lang/String;
    //   424: aload_3
    //   425: aload 8
    //   427: invokeinterface 290 4 0
    //   432: istore 20
    //   434: iload 20
    //   436: istore 12
    //   438: goto +151 -> 589
    //   441: aload 11
    //   443: astore 13
    //   445: goto -123 -> 322
    //   448: aload 11
    //   450: aload 9
    //   452: aconst_null
    //   453: aload 8
    //   455: invokeinterface 293 4 0
    //   460: astore 18
    //   462: goto -115 -> 347
    //   465: new 99	fm/qingting/framework/data/Result
    //   468: dup
    //   469: iconst_0
    //   470: aconst_null
    //   471: getstatic 118	fm/qingting/framework/data/DataError:DATASOURCE_ERROR	Lfm/qingting/framework/data/DataError;
    //   474: invokevirtual 121	fm/qingting/framework/data/DataError:getCode	()Ljava/lang/String;
    //   477: getstatic 118	fm/qingting/framework/data/DataError:DATASOURCE_ERROR	Lfm/qingting/framework/data/DataError;
    //   480: invokevirtual 124	fm/qingting/framework/data/DataError:getMessage	()Ljava/lang/String;
    //   483: invokespecial 127	fm/qingting/framework/data/Result:<init>	(ZLjava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V
    //   486: astore 19
    //   488: goto -124 -> 364
    //   491: aload 11
    //   493: ifnonnull +35 -> 528
    //   496: aload 8
    //   498: aload 9
    //   500: aload_0
    //   501: invokeinterface 87 3 0
    //   506: astore 14
    //   508: aload 14
    //   510: ifnull +35 -> 545
    //   513: aload_0
    //   514: getfield 40	fm/qingting/framework/data/DataManager:_cbMap	Ljava/util/HashMap;
    //   517: aload 14
    //   519: aload 5
    //   521: invokevirtual 131	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   524: pop
    //   525: goto -148 -> 377
    //   528: aload 11
    //   530: aload 9
    //   532: aload_0
    //   533: aload 8
    //   535: invokeinterface 293 4 0
    //   540: astore 14
    //   542: goto -34 -> 508
    //   545: new 99	fm/qingting/framework/data/Result
    //   548: dup
    //   549: iconst_0
    //   550: aconst_null
    //   551: getstatic 118	fm/qingting/framework/data/DataError:DATASOURCE_ERROR	Lfm/qingting/framework/data/DataError;
    //   554: invokevirtual 121	fm/qingting/framework/data/DataError:getCode	()Ljava/lang/String;
    //   557: getstatic 118	fm/qingting/framework/data/DataError:DATASOURCE_ERROR	Lfm/qingting/framework/data/DataError;
    //   560: invokevirtual 124	fm/qingting/framework/data/DataError:getMessage	()Ljava/lang/String;
    //   563: invokespecial 127	fm/qingting/framework/data/Result:<init>	(ZLjava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V
    //   566: astore 17
    //   568: aload 5
    //   570: aload 17
    //   572: invokevirtual 105	fm/qingting/framework/data/ResultToken:setResult	(Lfm/qingting/framework/data/Result;)V
    //   575: aload_0
    //   576: aload 5
    //   578: invokespecial 109	fm/qingting/framework/data/DataManager:sendResult	(Lfm/qingting/framework/data/ResultToken;)V
    //   581: goto -204 -> 377
    //   584: astore 16
    //   586: goto -203 -> 383
    //   589: aload 11
    //   591: ifnonnull -150 -> 441
    //   594: aload 8
    //   596: astore 13
    //   598: goto -276 -> 322
    //   601: astore 16
    //   603: goto -220 -> 383
    //
    // Exception table:
    //   from	to	target	type
    //   2	38	133	finally
    //   43	75	133	finally
    //   80	130	133	finally
    //   140	160	133	finally
    //   165	197	133	finally
    //   200	227	133	finally
    //   235	277	133	finally
    //   282	296	133	finally
    //   304	319	133	finally
    //   322	325	133	finally
    //   386	389	133	finally
    //   397	414	133	finally
    //   417	434	133	finally
    //   568	581	584	finally
    //   335	347	601	finally
    //   352	364	601	finally
    //   364	377	601	finally
    //   377	380	601	finally
    //   383	386	601	finally
    //   448	462	601	finally
    //   465	488	601	finally
    //   496	508	601	finally
    //   513	525	601	finally
    //   528	542	601	finally
    //   545	568	601	finally
  }

  public void onRecvData(Object paramObject1, Object paramObject2, IDataToken paramIDataToken, Object paramObject3)
  {
    try
    {
      dispatchDataEvent(true, paramObject1, null, null, paramObject2, paramIDataToken, paramObject3);
      ResultToken localResultToken = (ResultToken)this._cbMap.get(paramIDataToken);
      this._cbMap.remove(paramIDataToken);
      if (localResultToken == null);
      while (true)
      {
        return;
        localResultToken.setResult((Result)paramObject1);
        sendResult(localResultToken);
      }
    }
    finally
    {
    }
  }

  public void onRecvError(String paramString1, String paramString2, Object paramObject1, IDataToken paramIDataToken, Object paramObject2)
  {
    dispatchDataEvent(false, null, paramString1, paramString2, paramObject1, paramIDataToken, paramObject2);
    ResultToken localResultToken = (ResultToken)this._cbMap.get(paramIDataToken);
    this._cbMap.remove(paramIDataToken);
    if (((paramObject2 instanceof DataCommand)) && ((paramObject1 instanceof IDataSource)))
    {
      DataCommand localDataCommand = (DataCommand)paramObject2;
      IDataSource localIDataSource = (IDataSource)paramObject1;
      localDataCommand.getNextCommand();
      if (!localDataCommand.hasRetryAllCommands())
        doCommand(localIDataSource, localDataCommand, localResultToken);
    }
    while (localResultToken == null)
      return;
    localResultToken.setResult(new Result(false, null, paramString1, paramString2));
    sendResult(localResultToken);
  }

  public void removeDataListener(IDataRecvHandler paramIDataRecvHandler)
  {
    Iterator localIterator;
    IDataRecvHandler localIDataRecvHandler;
    do
      synchronized (this._dataListeners)
      {
        localIterator = this._dataListeners.iterator();
        if (!localIterator.hasNext())
          return;
        localIDataRecvHandler = (IDataRecvHandler)((WeakReference)localIterator.next()).get();
        if (localIDataRecvHandler == null)
          localIterator.remove();
      }
    while (paramIDataRecvHandler != localIDataRecvHandler);
    localIterator.remove();
  }

  public void removeResultListener(IResultRecvHandler paramIResultRecvHandler)
  {
    Iterator localIterator;
    IResultRecvHandler localIResultRecvHandler;
    do
      synchronized (this._resultListeners)
      {
        localIterator = this._resultListeners.iterator();
        if (!localIterator.hasNext())
          return;
        localIResultRecvHandler = (IResultRecvHandler)((WeakReference)localIterator.next()).get();
        if (localIResultRecvHandler == null)
          localIterator.remove();
      }
    while (paramIResultRecvHandler != localIResultRecvHandler);
    localIterator.remove();
  }

  private class AsynchronousDispatcher
    implements Runnable
  {
    private List<ResultToken> _tasks = new ArrayList();

    public AsynchronousDispatcher()
    {
      Thread localThread = new Thread(null, this);
      localThread.setPriority(1);
      localThread.start();
    }

    public void addTask(ResultToken paramResultToken)
    {
      try
      {
        synchronized (this._tasks)
        {
          this._tasks.add(paramResultToken);
          return;
        }
      }
      finally
      {
      }
    }

    public void run()
    {
      if (Thread.currentThread().isInterrupted())
        return;
      ArrayList localArrayList = new ArrayList();
      while (true)
      {
        Iterator localIterator;
        synchronized (this._tasks)
        {
          while (true)
          {
            localArrayList.addAll(this._tasks);
            this._tasks.clear();
            localIterator = localArrayList.iterator();
            if (!localIterator.hasNext())
              try
              {
                Thread.sleep(100L);
              }
              catch (InterruptedException localInterruptedException)
              {
                localInterruptedException.printStackTrace();
              }
          }
        }
        ResultToken localResultToken = (ResultToken)localIterator.next();
        Message localMessage = new Message();
        localMessage.what = 1;
        localMessage.obj = localResultToken;
        DataManager.this._dispatchHandler.sendMessage(localMessage);
      }
    }
  }

  private class DispatchHandler extends Handler
  {
    public DispatchHandler(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      ResultToken localResultToken = (ResultToken)paramMessage.obj;
      if (localResultToken == null)
        return;
      DataManager.this.dispatchResultEvent(localResultToken.getResult(), DataManager.this, localResultToken, localResultToken.getParametets());
      localResultToken.dispatchResultEvent(DataManager.this);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.data.DataManager
 * JD-Core Version:    0.6.2
 */