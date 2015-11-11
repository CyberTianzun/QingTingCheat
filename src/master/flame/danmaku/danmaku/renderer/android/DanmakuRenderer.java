package master.flame.danmaku.danmaku.renderer.android;

import master.flame.danmaku.controller.DanmakuFilters;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.renderer.IRenderer.RenderingState;
import master.flame.danmaku.danmaku.renderer.Renderer;

public class DanmakuRenderer extends Renderer
{
  private final IRenderer.RenderingState mRenderingState = new IRenderer.RenderingState();
  private final DanmakuTimer mStartTimer = new DanmakuTimer();

  public void clear()
  {
    DanmakusRetainer.clear();
    DanmakuFilters.getDefault().clear();
  }

  public IRenderer.RenderingState draw(IDisplayer paramIDisplayer, IDanmakus paramIDanmakus, long paramLong)
  {
    int i = this.mRenderingState.totalDanmakuCount;
    this.mRenderingState.reset();
    IDanmakuIterator localIDanmakuIterator = paramIDanmakus.iterator();
    int j = 0;
    this.mStartTimer.update(System.currentTimeMillis());
    int k = paramIDanmakus.size();
    BaseDanmaku localBaseDanmaku = null;
    boolean bool;
    label95: IRenderer.RenderingState localRenderingState2;
    if (localIDanmakuIterator.hasNext())
    {
      localBaseDanmaku = localIDanmakuIterator.next();
      if (!localBaseDanmaku.isLate());
    }
    else
    {
      IRenderer.RenderingState localRenderingState1 = this.mRenderingState;
      if (this.mRenderingState.totalDanmakuCount != 0)
        break label381;
      bool = true;
      localRenderingState1.nothingRendered = bool;
      localRenderingState2 = this.mRenderingState;
      if (localBaseDanmaku == null)
        break label387;
    }
    label387: for (long l = localBaseDanmaku.time; ; l = -1L)
    {
      localRenderingState2.endTime = l;
      if (this.mRenderingState.nothingRendered)
        this.mRenderingState.beginTime = -1L;
      this.mRenderingState.incrementCount = (this.mRenderingState.totalDanmakuCount - i);
      this.mRenderingState.consumingTime = this.mStartTimer.update(System.currentTimeMillis());
      return this.mRenderingState;
      if ((localBaseDanmaku.time < paramLong) || ((localBaseDanmaku.priority == 0) && (DanmakuFilters.getDefault().filter(localBaseDanmaku, j, k, this.mStartTimer, false))))
        break;
      if (localBaseDanmaku.getType() == 1)
        j++;
      if (!localBaseDanmaku.isMeasured())
        localBaseDanmaku.measure(paramIDisplayer);
      DanmakusRetainer.fix(localBaseDanmaku, paramIDisplayer);
      if ((localBaseDanmaku.isOutside()) || (!localBaseDanmaku.isShown()) || ((localBaseDanmaku.lines == null) && (localBaseDanmaku.getBottom() > paramIDisplayer.getHeight())))
        break;
      int m = localBaseDanmaku.draw(paramIDisplayer);
      if (m == 1)
      {
        IRenderer.RenderingState localRenderingState4 = this.mRenderingState;
        localRenderingState4.cacheHitCount = (1L + localRenderingState4.cacheHitCount);
      }
      while (true)
      {
        this.mRenderingState.addCount(localBaseDanmaku.getType(), 1);
        this.mRenderingState.addTotalCount(1);
        break;
        if (m == 2)
        {
          IRenderer.RenderingState localRenderingState3 = this.mRenderingState;
          localRenderingState3.cacheMissCount = (1L + localRenderingState3.cacheMissCount);
        }
      }
      label381: bool = false;
      break label95;
    }
  }

  public void release()
  {
    DanmakusRetainer.release();
    DanmakuFilters.getDefault().release();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.renderer.android.DanmakuRenderer
 * JD-Core Version:    0.6.2
 */