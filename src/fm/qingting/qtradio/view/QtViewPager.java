package fm.qingting.qtradio.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.utils.InputMethodUtil.InputStateDelegate;
import fm.qingting.utils.QTMSGManage;

public abstract class QtViewPager extends ViewGroupViewImpl
{
  public static final int DATA_SET_MODE_ALWAYS = 1;
  public static final int DATA_SET_MODE_NORMAL;
  private int mCurrentIndex = 0;
  private int mDataSetMode = 0;
  private InputMethodUtil.InputStateDelegate mDelegate;
  private IView[] mLstViews = new IView[getSubViewCnt()];
  protected String mStatisticalType;
  private CustomTabView mTabView;
  private UserInterfaceViewPager mViewPager;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private final ViewLayout tabLayout = this.standardLayout.createChildLT(720, 90, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public QtViewPager(Context paramContext)
  {
    super(paramContext);
    this.mViewPager = new UserInterfaceViewPager(paramContext);
    this.mViewPager.setAdapter(new MyPageViewAdapter(null));
    this.mViewPager.setOnPageChangeListener(new MyPageChangeListener(null));
    addView(this.mViewPager);
    this.mTabView = new CustomTabView(paramContext);
    addView(this.mTabView);
    if (enableSlide())
      this.mTabView.update("enableSlide", null);
  }

  private void selectTab(int paramInt)
  {
    this.mCurrentIndex = paramInt;
    this.mViewPager.setCurrentItem(this.mCurrentIndex);
  }

  public void close(boolean paramBoolean)
  {
    if (this.mLstViews != null)
      for (int i = 0; i < this.mLstViews.length; i++)
        if (this.mLstViews[i] != null)
          this.mLstViews[i].close(paramBoolean);
  }

  protected abstract boolean enableSlide();

  protected abstract IView generateView(int paramInt);

  public IView getCertainView(int paramInt)
  {
    if ((this.mLstViews == null) || (paramInt >= this.mLstViews.length))
      return null;
    return this.mLstViews[paramInt];
  }

  public int getCurrentIndex()
  {
    return this.mCurrentIndex;
  }

  protected abstract int getSubViewCnt();

  protected abstract String getTab(int paramInt);

  public int getTabHeight()
  {
    return this.tabLayout.height;
  }

  public void initCurrentItem(int paramInt)
  {
    this.mTabView.initShift(paramInt, getSubViewCnt());
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mTabView.layout(0, 0, this.tabLayout.width, this.tabLayout.height);
    this.mViewPager.layout(0, this.tabLayout.height, this.standardLayout.width, this.standardLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.tabLayout.scaleToBounds(this.standardLayout);
    this.tabLayout.measureView(this.mTabView);
    this.mViewPager.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.tabLayout.height, 1073741824));
    setMeasuredDimension(i, j);
  }

  public void refreshTab()
  {
    this.mTabView.invalidate();
  }

  public void setAllViewData()
  {
    if (this.mLstViews == null);
    while (true)
    {
      return;
      for (int i = 0; i < this.mLstViews.length; i++)
        if (this.mLstViews[i] != null)
          setSubViewData(this.mLstViews[i], i);
    }
  }

  public void setCurrentItem(int paramInt, boolean paramBoolean)
  {
    this.mViewPager.setCurrentItem(paramInt, paramBoolean);
  }

  public void setDataSetMode(int paramInt)
  {
    this.mDataSetMode = paramInt;
  }

  public void setInputStateDelegate(InputMethodUtil.InputStateDelegate paramInputStateDelegate)
  {
    this.mDelegate = paramInputStateDelegate;
  }

  protected abstract void setSubViewData(IView paramIView, int paramInt);

  public void setSubViewLoading(int paramInt)
  {
    if (this.mLstViews == null);
    while ((paramInt >= this.mLstViews.length) || (this.mLstViews[paramInt] == null))
      return;
    this.mLstViews[paramInt].update("loading", null);
  }

  public void setTabBackgroundColor(int paramInt)
  {
    this.mTabView.setBackgroundColor(paramInt);
  }

  public void setTabHasVerticalLine(boolean paramBoolean)
  {
    this.mTabView.setHasVerticalLine(paramBoolean);
  }

  public void setTabHighlightTextColor(int paramInt)
  {
    this.mTabView.setHighlightTextColor(paramInt);
  }

  public void setTabLineColor(int paramInt)
  {
    this.mTabView.setLineColor(paramInt);
  }

  public void setTabNormalTextColor(int paramInt)
  {
    this.mTabView.setNormalTextColor(paramInt);
  }

  public void setWillIntercept(boolean paramBoolean)
  {
    this.mViewPager.setWillIntercept(paramBoolean);
  }

  private class CustomTabView extends QtListItemView
  {
    private final ViewLayout labelLayout = this.standardLayout.createChildLT(80, 4, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
    private final ViewLayout lineLayout = this.standardLayout.createChildLT(720, 1, 33, 0, ViewLayout.SCALE_FLAG_SLTCW);
    private int mCheckedIndex = 0;
    private boolean mHasVerticalLine = true;
    private final Paint mHighlightTextPaint = new Paint();
    private boolean mInTouchMode = false;
    private float mInitOffset;
    private boolean mInitState = false;
    private float mLastMotionX;
    private float mLastMotionY;
    private final Paint mLinePaint = new Paint();
    private final Paint mNormalTextPaint = new Paint();
    private int mOffset = 0;
    private int mSelectIndex = -1;
    private final Rect mTextBound = new Rect();
    private final ViewLayout nameLayout = this.standardLayout.createChildLT(80, 55, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
    private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 90, 720, 90, 0, 0, ViewLayout.FILL);

    public CustomTabView(Context arg2)
    {
      super();
      this.mNormalTextPaint.setColor(SkinManager.getTextColorNormal());
      this.mHighlightTextPaint.setColor(SkinManager.getTextColorHighlight());
      this.mLinePaint.setColor(-2236963);
      setBackgroundColor(-328966);
    }

    private void drawBg(Canvas paramCanvas)
    {
      if (this.mLinePaint.getColor() != 0)
        paramCanvas.drawLine(0.0F, this.standardLayout.height - this.lineLayout.height / 2.0F, this.standardLayout.width, this.standardLayout.height - this.lineLayout.height / 2.0F, this.mLinePaint);
    }

    private void drawItem(Canvas paramCanvas, String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
      if (paramString == null)
        return;
      int i = this.standardLayout.height / 2;
      this.mNormalTextPaint.getTextBounds(paramString, 0, paramString.length(), this.mTextBound);
      if ((paramBoolean1) && (!QtViewPager.this.enableSlide()))
      {
        int j = paramCanvas.save();
        paramCanvas.clipRect(paramInt1 - paramInt2 / 2, this.standardLayout.height - this.labelLayout.height, paramInt1 + paramInt2 / 2, this.standardLayout.height);
        paramCanvas.drawColor(SkinManager.getTextColorHighlight());
        paramCanvas.restoreToCount(j);
      }
      float f1 = paramInt1 - this.mTextBound.width() / 2;
      float f2 = i - (this.mTextBound.top + this.mTextBound.bottom) / 2;
      if ((paramBoolean1) || (paramBoolean2));
      for (Paint localPaint = this.mHighlightTextPaint; ; localPaint = this.mNormalTextPaint)
      {
        paramCanvas.drawText(paramString, f1, f2, localPaint);
        return;
      }
    }

    private void drawItems(Canvas paramCanvas)
    {
      int i = QtViewPager.this.getSubViewCnt();
      int j = this.standardLayout.width / i;
      int k = j / 2;
      int m = 0;
      if (m < i)
      {
        String str = QtViewPager.this.getTab(m);
        boolean bool1;
        if (this.mCheckedIndex == m)
        {
          bool1 = true;
          label55: if (this.mSelectIndex != m)
            break label118;
        }
        label118: for (boolean bool2 = true; ; bool2 = false)
        {
          drawItem(paramCanvas, str, k, j, bool1, bool2);
          if (m < i - 1)
            drawVerticalLine(paramCanvas, k + j / 2);
          k += j;
          m++;
          break;
          bool1 = false;
          break label55;
        }
      }
      if (QtViewPager.this.enableSlide())
        drawSlide(paramCanvas);
    }

    private void drawSlide(Canvas paramCanvas)
    {
      int i = QtViewPager.this.getSubViewCnt();
      int j = this.standardLayout.width / i;
      if (this.mInitState)
      {
        int m = paramCanvas.save();
        int n = (int)(this.mInitOffset * this.standardLayout.width);
        paramCanvas.clipRect(n, this.standardLayout.height - this.labelLayout.height, j + n, this.standardLayout.height);
        paramCanvas.drawColor(SkinManager.getTextColorHighlight());
        paramCanvas.restoreToCount(m);
        return;
      }
      int k = paramCanvas.save();
      paramCanvas.clipRect(this.mOffset, this.standardLayout.height - this.labelLayout.height, j + this.mOffset, this.standardLayout.height);
      paramCanvas.drawColor(SkinManager.getTextColorHighlight());
      paramCanvas.restoreToCount(k);
    }

    private void drawVerticalLine(Canvas paramCanvas, int paramInt)
    {
      if ((this.mHasVerticalLine) && (this.mLinePaint.getColor() != 0))
        paramCanvas.drawLine(paramInt, (this.standardLayout.height - this.lineLayout.leftMargin) / 2, paramInt, (this.standardLayout.height + this.lineLayout.leftMargin) / 2, this.mLinePaint);
    }

    private int getSelectIndex()
    {
      if ((this.mLastMotionY < 0.0F) || (this.mLastMotionY > this.standardLayout.height));
      while ((this.mLastMotionX < 0.0F) || (this.mLastMotionX > this.standardLayout.width))
        return -1;
      int i = QtViewPager.this.getSubViewCnt();
      return (int)(this.mLastMotionX / (this.standardLayout.width / i));
    }

    public void initShift(int paramInt1, int paramInt2)
    {
      this.mInitState = true;
      this.mInitOffset = (paramInt1 / paramInt2);
    }

    protected void onDraw(Canvas paramCanvas)
    {
      paramCanvas.setDrawFilter(SkinManager.getInstance().getDrawFilter());
      paramCanvas.save();
      drawBg(paramCanvas);
      drawItems(paramCanvas);
      paramCanvas.restore();
    }

    protected void onMeasure(int paramInt1, int paramInt2)
    {
      this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
      this.nameLayout.scaleToBounds(this.standardLayout);
      this.labelLayout.scaleToBounds(this.standardLayout);
      this.lineLayout.scaleToBounds(this.standardLayout);
      this.mNormalTextPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
      this.mHighlightTextPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
      this.mLinePaint.setStrokeWidth(this.lineLayout.height);
      setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      int i = paramMotionEvent.getAction();
      if ((!this.mInTouchMode) && (i != 0));
      do
      {
        int j;
        do
        {
          do
          {
            return true;
            switch (i)
            {
            default:
              return true;
            case 0:
              this.mLastMotionX = paramMotionEvent.getX();
              this.mLastMotionY = paramMotionEvent.getY();
              this.mInTouchMode = true;
              this.mSelectIndex = getSelectIndex();
              invalidate();
              return true;
            case 2:
            case 3:
            case 1:
            }
          }
          while (!this.mInTouchMode);
          this.mLastMotionX = paramMotionEvent.getX();
          this.mLastMotionY = paramMotionEvent.getY();
          j = getSelectIndex();
        }
        while ((this.mSelectIndex <= -1) || (j == this.mSelectIndex));
        this.mInTouchMode = false;
        this.mSelectIndex = -1;
        invalidate();
        return true;
        this.mSelectIndex = -1;
        invalidate();
        return true;
      }
      while ((!this.mInTouchMode) || (this.mSelectIndex >= QtViewPager.this.getSubViewCnt()) || (this.mSelectIndex <= -1));
      if (this.mCheckedIndex != this.mSelectIndex)
      {
        this.mCheckedIndex = this.mSelectIndex;
        QtViewPager.this.selectTab(this.mCheckedIndex);
      }
      this.mSelectIndex = -1;
      invalidate();
      return true;
    }

    public void setHasVerticalLine(boolean paramBoolean)
    {
      this.mHasVerticalLine = paramBoolean;
    }

    public void setHighlightTextColor(int paramInt)
    {
      this.mHighlightTextPaint.setColor(paramInt);
    }

    public void setLineColor(int paramInt)
    {
      this.mLinePaint.setColor(paramInt);
    }

    public void setNormalTextColor(int paramInt)
    {
      this.mNormalTextPaint.setColor(paramInt);
    }

    public void update(String paramString, Object paramObject)
    {
      if (paramString.equalsIgnoreCase("shift"))
        if (QtViewPager.this.enableSlide())
        {
          if (this.mInitState)
            this.mInitState = false;
          int j = ((Integer)paramObject).intValue();
          if (this.mOffset != j)
          {
            this.mOffset = j;
            invalidate();
          }
        }
      int i;
      do
      {
        do
          return;
        while (!paramString.equalsIgnoreCase("changeIndex"));
        i = ((Integer)paramObject).intValue();
      }
      while (this.mCheckedIndex == i);
      this.mCheckedIndex = i;
      invalidate();
    }
  }

  private class MyPageChangeListener
    implements ViewPager.OnPageChangeListener
  {
    private MyPageChangeListener()
    {
    }

    public void onPageScrollStateChanged(int paramInt)
    {
      if ((paramInt == 1) && (QtViewPager.this.mDelegate != null))
        QtViewPager.this.mDelegate.closeIfNeed();
    }

    public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
    {
      if (QtViewPager.this.enableSlide())
        QtViewPager.this.mTabView.update("shift", Integer.valueOf((paramInt2 + paramInt1 * QtViewPager.this.standardLayout.width) / QtViewPager.this.getSubViewCnt()));
    }

    public void onPageSelected(int paramInt)
    {
      QtViewPager.access$302(QtViewPager.this, paramInt % QtViewPager.this.getSubViewCnt());
      QtViewPager.this.mTabView.update("changeIndex", Integer.valueOf(QtViewPager.this.mCurrentIndex));
      QtViewPager.this.mTabView.update("shift", Integer.valueOf(paramInt * QtViewPager.this.standardLayout.width / QtViewPager.this.getSubViewCnt()));
      QtViewPager.this.dispatchActionEvent("pagechanged", Integer.valueOf(QtViewPager.this.mCurrentIndex));
      if (QtViewPager.this.mStatisticalType != null)
      {
        String str = QtViewPager.this.getTab(QtViewPager.this.mCurrentIndex);
        if (str != null)
          QTMSGManage.getInstance().sendStatistcsMessage(QtViewPager.this.mStatisticalType, str);
      }
      if (QtViewPager.this.mDelegate != null)
        QtViewPager.this.mDelegate.closeIfNeed();
    }
  }

  private class MyPageViewAdapter extends PagerAdapter
  {
    private int mAddedIndex = 0;

    private MyPageViewAdapter()
    {
    }

    private boolean hasAdded(int paramInt)
    {
      int i = (int)Math.pow(2.0D, paramInt);
      if ((i & this.mAddedIndex) == i)
        return true;
      this.mAddedIndex = (i | this.mAddedIndex);
      return false;
    }

    public void destroyItem(View paramView, int paramInt, Object paramObject)
    {
    }

    public void finishUpdate(View paramView)
    {
    }

    public int getCount()
    {
      return QtViewPager.this.getSubViewCnt();
    }

    public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
    {
      int i = paramInt % QtViewPager.this.getSubViewCnt();
      if (i < 0)
        i += QtViewPager.this.getSubViewCnt();
      if (!hasAdded(i))
      {
        IView localIView = QtViewPager.this.generateView(i);
        if (localIView != null)
        {
          if (QtViewPager.this.mDataSetMode == 0)
            QtViewPager.this.setSubViewData(localIView, i);
          QtViewPager.this.mLstViews[i] = localIView;
          ((ViewPager)paramViewGroup).addView(localIView.getView());
        }
      }
      if (QtViewPager.this.mDataSetMode == 1)
      {
        QtViewPager.this.setSubViewData(QtViewPager.this.mLstViews[i], i);
        QtViewPager.this.mLstViews[i].getView().requestLayout();
      }
      return QtViewPager.this.mLstViews[i].getView();
    }

    public boolean isViewFromObject(View paramView, Object paramObject)
    {
      return paramView == paramObject;
    }

    public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
    {
    }

    public Parcelable saveState()
    {
      return null;
    }

    public void startUpdate(View paramView)
    {
    }
  }

  private class UserInterfaceViewPager extends ViewPager
  {
    private boolean mIntercept = true;

    public UserInterfaceViewPager(Context arg2)
    {
      super();
    }

    private void setWillIntercept(boolean paramBoolean)
    {
      this.mIntercept = paramBoolean;
    }

    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      if (this.mIntercept)
        return super.onInterceptTouchEvent(paramMotionEvent);
      return false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.QtViewPager
 * JD-Core Version:    0.6.2
 */