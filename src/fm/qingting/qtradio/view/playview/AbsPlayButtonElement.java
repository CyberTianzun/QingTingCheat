package fm.qingting.qtradio.view.playview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ViewElement;

public abstract class AbsPlayButtonElement extends ViewElement
{
  private static final int DELAY = 60;
  private static final int LENGTH = 33;
  private int mDegree = 0;
  private final Handler mLoadHandler = new Handler();
  private final Runnable mLoadRunnable = new Runnable()
  {
    public void run()
    {
      AbsPlayButtonElement.access$012(AbsPlayButtonElement.this, 10);
      if (AbsPlayButtonElement.this.mDegree > 360)
        AbsPlayButtonElement.access$002(AbsPlayButtonElement.this, 0);
      if (AbsPlayButtonElement.this.mState != AbsPlayButtonElement.State.BUFFER)
        AbsPlayButtonElement.access$102(AbsPlayButtonElement.this, AbsPlayButtonElement.State.BUFFER);
      AbsPlayButtonElement.this.invalidateAll();
      AbsPlayButtonElement.this.mLoadHandler.postDelayed(AbsPlayButtonElement.this.mLoadRunnable, 33L);
    }
  };
  private final Paint mPaint = new Paint();
  private State mState = State.UNDEF;

  public AbsPlayButtonElement(Context paramContext)
  {
    super(paramContext);
  }

  private void drawBuffer(Canvas paramCanvas)
  {
    int i = getBufferResource(isPressed());
    if (i != 0)
    {
      Bitmap localBitmap = BitmapResourceCache.getInstance().getResourceCacheByParent(getContext().getResources(), this.mOwnerId, i);
      Rect localRect = getBound();
      paramCanvas.rotate(this.mDegree, localRect.exactCenterX(), localRect.exactCenterY());
      paramCanvas.drawBitmap(localBitmap, null, localRect, this.mPaint);
    }
  }

  private void drawError(Canvas paramCanvas)
  {
    int i = getErrorResource(isPressed());
    if (i != 0)
      paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getContext().getResources(), this.mOwnerId, i), null, getBound(), this.mPaint);
  }

  private void drawPause(Canvas paramCanvas)
  {
    int i = getPauseResource(isPressed());
    if (i != 0)
      paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getContext().getResources(), this.mOwnerId, i), null, getBound(), this.mPaint);
  }

  private void drawPlay(Canvas paramCanvas)
  {
    int i = getPlayResource(isPressed());
    if (i != 0)
      paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getContext().getResources(), this.mOwnerId, i), null, getBound(), this.mPaint);
  }

  private void startBuffer()
  {
    this.mLoadHandler.removeCallbacks(this.mLoadRunnable);
    this.mLoadHandler.postDelayed(this.mLoadRunnable, 60L);
  }

  private void stopBufferIfNeed()
  {
    this.mLoadHandler.removeCallbacks(this.mLoadRunnable);
  }

  protected abstract int getBufferResource(boolean paramBoolean);

  protected abstract int getErrorResource(boolean paramBoolean);

  protected abstract int getPauseResource(boolean paramBoolean);

  protected abstract int getPlayResource(boolean paramBoolean);

  public State getState()
  {
    return this.mState;
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    switch (2.$SwitchMap$fm$qingting$qtradio$view$playview$AbsPlayButtonElement$State[this.mState.ordinal()])
    {
    default:
      drawPlay(paramCanvas);
      return;
    case 1:
      drawPlay(paramCanvas);
      return;
    case 2:
      drawPause(paramCanvas);
      return;
    case 3:
      drawError(paramCanvas);
      return;
    case 4:
    }
    drawBuffer(paramCanvas);
  }

  public void setState(State paramState)
  {
    switch (2.$SwitchMap$fm$qingting$qtradio$view$playview$AbsPlayButtonElement$State[paramState.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      invalidateElement();
      return;
      this.mState = paramState;
      stopBufferIfNeed();
      continue;
      this.mState = paramState;
      stopBufferIfNeed();
      continue;
      this.mState = paramState;
      stopBufferIfNeed();
      continue;
      startBuffer();
    }
  }

  public static enum State
  {
    static
    {
      PAUSE = new State("PAUSE", 1);
      BUFFER = new State("BUFFER", 2);
      ERROR = new State("ERROR", 3);
      UNDEF = new State("UNDEF", 4);
      State[] arrayOfState = new State[5];
      arrayOfState[0] = PLAY;
      arrayOfState[1] = PAUSE;
      arrayOfState[2] = BUFFER;
      arrayOfState[3] = ERROR;
      arrayOfState[4] = UNDEF;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.AbsPlayButtonElement
 * JD-Core Version:    0.6.2
 */