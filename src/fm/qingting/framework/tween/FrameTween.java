package fm.qingting.framework.tween;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class FrameTween
  implements IFrameHandler
{
  public static final String TWEEN_COMPLETE = "tween_complete";
  public static final String TWEEN_PROGRESS = "progress";
  protected static Map<Object, FrameTween> tweens = new WeakHashMap();
  public ITweenDelegate delegate;
  public SyncType syncType = SyncType.SYNC;
  public Object target;
  private List<TweenProperty> targetProperties;

  public FrameTween(ITweenDelegate paramITweenDelegate, Object paramObject, SyncType paramSyncType)
  {
    this(paramITweenDelegate, paramObject, null, paramSyncType);
  }

  public FrameTween(ITweenDelegate paramITweenDelegate, Object paramObject, List<TweenProperty> paramList, SyncType paramSyncType)
  {
    this.delegate = paramITweenDelegate;
    this.target = paramObject;
    this.syncType = paramSyncType;
    setProperty(paramList);
  }

  private float calculateValue(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, IEase paramIEase, float[] paramArrayOfFloat, float paramFloat5)
  {
    Class[] arrayOfClass = new Class[5];
    arrayOfClass[0] = Float.TYPE;
    arrayOfClass[1] = Float.TYPE;
    arrayOfClass[2] = Float.TYPE;
    arrayOfClass[3] = Float.TYPE;
    arrayOfClass[4] = [F.class;
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = Float.valueOf(paramFloat1);
    arrayOfObject[1] = Float.valueOf(paramFloat2);
    arrayOfObject[2] = Float.valueOf(paramFloat3 - paramFloat2);
    arrayOfObject[3] = Float.valueOf(paramFloat4);
    if (paramArrayOfFloat == null)
      paramArrayOfFloat = new float[0];
    arrayOfObject[4] = paramArrayOfFloat;
    if (paramIEase == null);
    try
    {
      Method localMethod;
      for (Object localObject = FrameTween.class.getMethod("defaultEase", arrayOfClass); ; localObject = localMethod)
      {
        float f2 = ((Float)((Method)localObject).invoke(paramIEase, arrayOfObject)).floatValue();
        f1 = f2;
        if ((!Float.isNaN(paramFloat5)) && (paramFloat5 > Math.abs(f1)))
        {
          if (f1 >= 0.0F)
            break;
          f1 = -paramFloat5;
        }
        return f1;
        localMethod = paramIEase.getClass().getMethod("ease", arrayOfClass);
      }
    }
    catch (SecurityException localSecurityException)
    {
      while (true)
      {
        localSecurityException.printStackTrace();
        f1 = 0.0F;
      }
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
      {
        localNoSuchMethodException.printStackTrace();
        f1 = 0.0F;
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
      {
        localIllegalArgumentException.printStackTrace();
        f1 = 0.0F;
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
      {
        localIllegalAccessException.printStackTrace();
        f1 = 0.0F;
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
      {
        localInvocationTargetException.printStackTrace();
        float f1 = 0.0F;
      }
    }
    return paramFloat5;
  }

  public static FrameTween cancel(Object paramObject)
  {
    if (tweens.containsKey(paramObject))
    {
      FrameTween localFrameTween = (FrameTween)tweens.get(paramObject);
      localFrameTween.cancel();
      return localFrameTween;
    }
    return null;
  }

  public static int getFrameLength(SyncType paramSyncType)
  {
    if (paramSyncType == SyncType.SYNC)
      return SyncFrameTimer.getInstance().getPeriod();
    return AsyncFrameTimer.getInstance().getPeriod();
  }

  private void removeTween()
  {
    tweens.remove(this.target);
    SyncFrameTimer.getInstance().removeListener(this);
    AsyncFrameTimer.getInstance().removeListener(this);
  }

  private boolean targetValueComplete(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, IEase paramIEase, float[] paramArrayOfFloat, float paramFloat5, int paramInt)
  {
    boolean bool = true;
    int i = 0;
    if (i >= paramInt);
    while (paramFloat1 + i > paramFloat4)
      return bool;
    float f = calculateValue(paramFloat1 + i, paramFloat2, paramFloat3, paramFloat4, paramIEase, paramArrayOfFloat, paramFloat5);
    if (Float.isNaN(paramFloat5))
      if ((bool) && (paramFloat3 == f))
        bool = true;
    while (Math.abs(paramFloat3 - f) <= paramFloat5)
      while (true)
      {
        i++;
        break;
        bool = false;
      }
    return false;
  }

  public static FrameTween to(ITweenDelegate paramITweenDelegate, Object paramObject, List<TweenProperty> paramList)
  {
    return to(paramITweenDelegate, paramObject, paramList, SyncType.SYNC);
  }

  public static FrameTween to(ITweenDelegate paramITweenDelegate, Object paramObject, List<TweenProperty> paramList, SyncType paramSyncType)
  {
    FrameTween localFrameTween = new FrameTween(paramITweenDelegate, paramObject, paramList, paramSyncType);
    localFrameTween.doTween();
    return localFrameTween;
  }

  private float trimValue(float paramFloat, TweenProperty.TRIM_TYPE paramTRIM_TYPE)
  {
    switch ($SWITCH_TABLE$fm$qingting$framework$tween$TweenProperty$TRIM_TYPE()[paramTRIM_TYPE.ordinal()])
    {
    default:
      return paramFloat;
    case 2:
      return Math.round(paramFloat);
    case 3:
      return (float)Math.floor(paramFloat);
    case 4:
    }
    return (float)Math.ceil(paramFloat);
  }

  public void OnFrame()
  {
    this.delegate.onProgress(this.target);
    int i = 1;
    Iterator localIterator = this.targetProperties.iterator();
    if (!localIterator.hasNext())
    {
      if (i != 0)
      {
        removeTween();
        this.delegate.onComplete(this.target);
      }
      return;
    }
    TweenProperty localTweenProperty = (TweenProperty)localIterator.next();
    float f;
    if (!localTweenProperty.complete)
    {
      localTweenProperty.time = (1.0F + localTweenProperty.time);
      if ((localTweenProperty.time == 1.0F) && (!localTweenProperty.fullMotion))
        localTweenProperty.complete = targetValueComplete(localTweenProperty.time, localTweenProperty.begin, localTweenProperty.finish, localTweenProperty.duration, localTweenProperty.ease, localTweenProperty.easeParam, localTweenProperty.minStep, 5);
      if ((localTweenProperty.time != localTweenProperty.duration) && (!localTweenProperty.complete))
        break label197;
      f = localTweenProperty.finish;
      localTweenProperty.complete = true;
    }
    while (true)
    {
      this.delegate.setValue(this.target, localTweenProperty.type, f);
      if (localTweenProperty.complete)
        break;
      i = 0;
      break;
      label197: f = trimValue(calculateValue(localTweenProperty.time, localTweenProperty.begin, localTweenProperty.finish, localTweenProperty.duration, localTweenProperty.ease, localTweenProperty.easeParam, localTweenProperty.minStep), localTweenProperty.trimType);
    }
  }

  public void cancel()
  {
    removeTween();
    this.delegate.onCancel(this.target);
  }

  public float defaultEase(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
  {
    return paramFloat2 + paramFloat3 * paramFloat1 / paramFloat4;
  }

  public void doTween()
  {
    if (tweens.containsKey(this.target))
      ((FrameTween)tweens.get(this.target)).cancel();
    Iterator localIterator = this.targetProperties.iterator();
    if (!localIterator.hasNext())
      tweens.put(this.target, this);
    switch ($SWITCH_TABLE$fm$qingting$framework$tween$FrameTween$SyncType()[this.syncType.ordinal()])
    {
    default:
      SyncFrameTimer.getInstance().addListener(this);
      return;
      TweenProperty localTweenProperty = (TweenProperty)localIterator.next();
      this.delegate.onStart(this.target, localTweenProperty.type, localTweenProperty.begin, localTweenProperty.finish);
      if (Float.isNaN(localTweenProperty.begin))
        localTweenProperty.begin = this.delegate.getValue(this.target, localTweenProperty.type);
      while (true)
      {
        if (localTweenProperty.duration <= 0.0F)
          localTweenProperty.duration = 1.0F;
        if (Float.isNaN(localTweenProperty.time))
          localTweenProperty.time = 0.0F;
        localTweenProperty.complete = false;
        break;
        this.delegate.setValue(this.target, localTweenProperty.type, localTweenProperty.begin);
      }
    case 2:
    }
    AsyncFrameTimer.getInstance().addListener(this);
  }

  public void doTween(List<TweenProperty> paramList)
  {
    setProperty(paramList);
    doTween();
  }

  public void setProperty(List<TweenProperty> paramList)
  {
    if (this.targetProperties != null)
      this.targetProperties.clear();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator;
    if (paramList != null)
      localIterator = paramList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.targetProperties = localArrayList;
        return;
      }
      localArrayList.add(((TweenProperty)localIterator.next()).clone());
    }
  }

  public static enum SyncType
  {
    static
    {
      ASYNC = new SyncType("ASYNC", 1);
      SyncType[] arrayOfSyncType = new SyncType[2];
      arrayOfSyncType[0] = SYNC;
      arrayOfSyncType[1] = ASYNC;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.tween.FrameTween
 * JD-Core Version:    0.6.2
 */