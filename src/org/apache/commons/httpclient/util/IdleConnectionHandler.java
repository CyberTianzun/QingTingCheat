package org.apache.commons.httpclient.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IdleConnectionHandler
{
  private static final Log LOG;
  static Class class$org$apache$commons$httpclient$util$IdleConnectionHandler;
  private Map connectionToAdded = new HashMap();

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$util$IdleConnectionHandler == null)
    {
      localClass = class$("org.apache.commons.httpclient.util.IdleConnectionHandler");
      class$org$apache$commons$httpclient$util$IdleConnectionHandler = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$util$IdleConnectionHandler;
    }
  }

  static Class class$(String paramString)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }

  public void add(HttpConnection paramHttpConnection)
  {
    Long localLong = new Long(System.currentTimeMillis());
    if (LOG.isDebugEnabled())
      LOG.debug("Adding connection at: " + localLong);
    this.connectionToAdded.put(paramHttpConnection, localLong);
  }

  public void closeIdleConnections(long paramLong)
  {
    long l = System.currentTimeMillis() - paramLong;
    if (LOG.isDebugEnabled())
      LOG.debug("Checking for connections, idleTimeout: " + l);
    Iterator localIterator = this.connectionToAdded.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      HttpConnection localHttpConnection = (HttpConnection)localIterator.next();
      Long localLong = (Long)this.connectionToAdded.get(localHttpConnection);
      if (localLong.longValue() <= l)
      {
        if (LOG.isDebugEnabled())
          LOG.debug("Closing connection, connection time: " + localLong);
        localIterator.remove();
        localHttpConnection.close();
      }
    }
  }

  public void remove(HttpConnection paramHttpConnection)
  {
    this.connectionToAdded.remove(paramHttpConnection);
  }

  public void removeAll()
  {
    this.connectionToAdded.clear();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.util.IdleConnectionHandler
 * JD-Core Version:    0.6.2
 */