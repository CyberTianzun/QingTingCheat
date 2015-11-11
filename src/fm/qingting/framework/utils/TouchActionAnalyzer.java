package fm.qingting.framework.utils;

import android.view.MotionEvent;

public class TouchActionAnalyzer
{
  private float horizontalAccuracy = 2.0F;
  public MoveHorizontalDirection lastHorizontalDirection = MoveHorizontalDirection.NONE;
  private long lastTime = 0L;
  public MoveVerticalDirection lastVerticalDirection = MoveVerticalDirection.NONE;
  private float lastX = 0.0F;
  private float lastY = 0.0F;
  private boolean startAnalyze = false;
  private float verticalAccuracy = 2.0F;

  public void sampleAction(MotionEvent paramMotionEvent)
  {
    long l = paramMotionEvent.getEventTime() - this.lastTime;
    this.lastTime = paramMotionEvent.getEventTime();
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    do
    {
      return;
      this.lastX = paramMotionEvent.getX();
      this.lastY = paramMotionEvent.getY();
      this.startAnalyze = true;
      return;
      if (l > 300L)
      {
        this.lastVerticalDirection = MoveVerticalDirection.NONE;
        this.lastHorizontalDirection = MoveHorizontalDirection.NONE;
      }
      this.startAnalyze = false;
      return;
      this.lastVerticalDirection = MoveVerticalDirection.NONE;
      this.lastHorizontalDirection = MoveHorizontalDirection.NONE;
    }
    while (!this.startAnalyze);
    float f1 = paramMotionEvent.getY() - this.lastY;
    float f2;
    if (Math.abs(f1) < this.verticalAccuracy)
    {
      this.lastVerticalDirection = MoveVerticalDirection.NONE;
      this.lastY = paramMotionEvent.getY();
      f2 = paramMotionEvent.getX() - this.lastX;
      if (Math.abs(f2) >= this.horizontalAccuracy)
        break label233;
      this.lastHorizontalDirection = MoveHorizontalDirection.NONE;
    }
    while (true)
    {
      this.lastX = paramMotionEvent.getX();
      return;
      if (f1 > 0.0F)
      {
        this.lastVerticalDirection = MoveVerticalDirection.DOWN;
        break;
      }
      if (f1 >= 0.0F)
        break;
      this.lastVerticalDirection = MoveVerticalDirection.UP;
      break;
      label233: if (f2 > 0.0F)
        this.lastHorizontalDirection = MoveHorizontalDirection.RIGHT;
      else if (f2 < 0.0F)
        this.lastHorizontalDirection = MoveHorizontalDirection.LEFT;
    }
  }

  public static enum MoveHorizontalDirection
  {
    static
    {
      LEFT = new MoveHorizontalDirection("LEFT", 1);
      RIGHT = new MoveHorizontalDirection("RIGHT", 2);
      MoveHorizontalDirection[] arrayOfMoveHorizontalDirection = new MoveHorizontalDirection[3];
      arrayOfMoveHorizontalDirection[0] = NONE;
      arrayOfMoveHorizontalDirection[1] = LEFT;
      arrayOfMoveHorizontalDirection[2] = RIGHT;
    }
  }

  public static enum MoveVerticalDirection
  {
    static
    {
      DOWN = new MoveVerticalDirection("DOWN", 2);
      MoveVerticalDirection[] arrayOfMoveVerticalDirection = new MoveVerticalDirection[3];
      arrayOfMoveVerticalDirection[0] = NONE;
      arrayOfMoveVerticalDirection[1] = UP;
      arrayOfMoveVerticalDirection[2] = DOWN;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.TouchActionAnalyzer
 * JD-Core Version:    0.6.2
 */