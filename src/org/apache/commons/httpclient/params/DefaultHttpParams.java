package org.apache.commons.httpclient.params;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultHttpParams
  implements HttpParams, Serializable, Cloneable
{
  private static final Log LOG;
  static Class class$org$apache$commons$httpclient$params$DefaultHttpParams;
  private static HttpParamsFactory httpParamsFactory;
  private HttpParams defaults = null;
  private HashMap parameters = null;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$params$DefaultHttpParams == null)
    {
      localClass = class$("org.apache.commons.httpclient.params.DefaultHttpParams");
      class$org$apache$commons$httpclient$params$DefaultHttpParams = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      httpParamsFactory = new DefaultHttpParamsFactory();
      return;
      localClass = class$org$apache$commons$httpclient$params$DefaultHttpParams;
    }
  }

  public DefaultHttpParams()
  {
    this(getDefaultParams());
  }

  public DefaultHttpParams(HttpParams paramHttpParams)
  {
    this.defaults = paramHttpParams;
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

  public static HttpParams getDefaultParams()
  {
    return httpParamsFactory.getDefaultParams();
  }

  public static void setHttpParamsFactory(HttpParamsFactory paramHttpParamsFactory)
  {
    if (paramHttpParamsFactory == null)
      throw new IllegalArgumentException("httpParamsFactory may not be null");
    httpParamsFactory = paramHttpParamsFactory;
  }

  public void clear()
  {
    this.parameters = null;
  }

  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultHttpParams localDefaultHttpParams = (DefaultHttpParams)super.clone();
    if (this.parameters != null)
      localDefaultHttpParams.parameters = ((HashMap)this.parameters.clone());
    localDefaultHttpParams.setDefaults(this.defaults);
    return localDefaultHttpParams;
  }

  public boolean getBooleanParameter(String paramString, boolean paramBoolean)
  {
    Object localObject = getParameter(paramString);
    if (localObject == null)
      return paramBoolean;
    return ((Boolean)localObject).booleanValue();
  }

  public HttpParams getDefaults()
  {
    try
    {
      HttpParams localHttpParams = this.defaults;
      return localHttpParams;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public double getDoubleParameter(String paramString, double paramDouble)
  {
    Object localObject = getParameter(paramString);
    if (localObject == null)
      return paramDouble;
    return ((Double)localObject).doubleValue();
  }

  public int getIntParameter(String paramString, int paramInt)
  {
    Object localObject = getParameter(paramString);
    if (localObject == null)
      return paramInt;
    return ((Integer)localObject).intValue();
  }

  public long getLongParameter(String paramString, long paramLong)
  {
    Object localObject = getParameter(paramString);
    if (localObject == null)
      return paramLong;
    return ((Long)localObject).longValue();
  }

  public Object getParameter(String paramString)
  {
    try
    {
      HashMap localHashMap = this.parameters;
      Object localObject2 = null;
      if (localHashMap != null)
      {
        Object localObject3 = this.parameters.get(paramString);
        localObject2 = localObject3;
      }
      if (localObject2 != null);
      while (true)
      {
        return localObject2;
        if (this.defaults != null)
        {
          Object localObject4 = this.defaults.getParameter(paramString);
          localObject2 = localObject4;
        }
        else
        {
          localObject2 = null;
        }
      }
    }
    finally
    {
    }
  }

  public boolean isParameterFalse(String paramString)
  {
    boolean bool1 = getBooleanParameter(paramString, false);
    boolean bool2 = false;
    if (!bool1)
      bool2 = true;
    return bool2;
  }

  public boolean isParameterSet(String paramString)
  {
    return getParameter(paramString) != null;
  }

  public boolean isParameterSetLocally(String paramString)
  {
    return (this.parameters != null) && (this.parameters.get(paramString) != null);
  }

  public boolean isParameterTrue(String paramString)
  {
    return getBooleanParameter(paramString, false);
  }

  public void setBooleanParameter(String paramString, boolean paramBoolean)
  {
    setParameter(paramString, new Boolean(paramBoolean));
  }

  public void setDefaults(HttpParams paramHttpParams)
  {
    try
    {
      this.defaults = paramHttpParams;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setDoubleParameter(String paramString, double paramDouble)
  {
    setParameter(paramString, new Double(paramDouble));
  }

  public void setIntParameter(String paramString, int paramInt)
  {
    setParameter(paramString, new Integer(paramInt));
  }

  public void setLongParameter(String paramString, long paramLong)
  {
    setParameter(paramString, new Long(paramLong));
  }

  public void setParameter(String paramString, Object paramObject)
  {
    try
    {
      if (this.parameters == null)
        this.parameters = new HashMap();
      this.parameters.put(paramString, paramObject);
      if (LOG.isDebugEnabled())
        LOG.debug("Set parameter " + paramString + " = " + paramObject);
      return;
    }
    finally
    {
    }
  }

  public void setParameters(String[] paramArrayOfString, Object paramObject)
  {
    int i = 0;
    try
    {
      while (true)
      {
        int j = paramArrayOfString.length;
        if (i >= j)
          return;
        setParameter(paramArrayOfString[i], paramObject);
        i++;
      }
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.params.DefaultHttpParams
 * JD-Core Version:    0.6.2
 */