package fm.qingting.framework.view;

import android.graphics.Canvas;
import android.graphics.Paint.FontMetricsInt;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.text.TextUtils;
import fm.qingting.framework.manager.EventDispacthManager;

class QtLayout
{
  private static final char CHAR_COLON = ':';
  private static final char CHAR_COMMA = ',';
  private static final char CHAR_DOT = '.';
  private static final char CHAR_HYPHEN = '-';
  private static final char CHAR_NEW_LINE = '\n';
  private static final char CHAR_SEMICOLON = ';';
  private static final char CHAR_SLASH = '/';
  private static final char CHAR_SPACE = ' ';
  private static final char CHAR_TAB = '\t';
  private static final String SINGLE_CHINESE = "嘿";
  private int mActualLineCnt = 0;
  private Layout.Alignment mAlignment = Layout.Alignment.ALIGN_NORMAL;
  private boolean mHasSeperated = false;
  private float mIndentation = 0.0F;
  int[] mLineBottom;
  int[] mLineEnds;
  int[] mLineMarkers;
  float[] mMarkersWidth;
  private int mMaxHeight;
  private int mMaxLineCnt = 5;
  private int mMaxWidth;
  private TextPaint mPaint;
  private String mText;
  private int mVerticalOffset = 0;
  char[] mWorkChars;
  float[] mWorkWidths;

  private float getXoffset(int paramInt)
  {
    float f = 0.0F;
    switch ($SWITCH_TABLE$android$text$Layout$Alignment()[this.mAlignment.ordinal()])
    {
    default:
      return 0.0F;
    case 2:
      if (paramInt == 0)
        return this.mIndentation;
      return 0.0F;
    case 1:
      int m = 0;
      if (paramInt > 0)
        m = this.mLineEnds[(paramInt - 1)];
      int n = this.mLineEnds[paramInt];
      for (int i1 = m; ; i1++)
      {
        if (i1 >= n)
          return (this.mMaxWidth - f) / 2.0F;
        f += this.mWorkWidths[i1];
      }
    case 3:
    }
    int i = 0;
    if (paramInt > 0)
      i = this.mLineEnds[(paramInt - 1)];
    int j = this.mLineEnds[paramInt];
    for (int k = i; ; k++)
    {
      if (k >= j)
        return this.mMaxWidth - f;
      f += this.mWorkWidths[k];
    }
  }

  private float getYoffset(int paramInt)
  {
    return this.mLineBottom[paramInt] + this.mVerticalOffset;
  }

  private void out(char[] paramArrayOfChar, float[] paramArrayOfFloat)
    throws IllegalStateException
  {
    if ((this.mLineMarkers == null) || (this.mLineMarkers.length == 0))
    {
      this.mWorkChars = paramArrayOfChar;
      this.mWorkWidths = paramArrayOfFloat;
    }
    int m;
    int n;
    int i1;
    do
    {
      return;
      int i = 0;
      for (int j = 0; ; j++)
      {
        if (j >= this.mActualLineCnt)
        {
          if (i != 0)
            break;
          this.mWorkChars = paramArrayOfChar;
          this.mWorkWidths = paramArrayOfFloat;
          return;
        }
        i += this.mLineMarkers[j];
      }
      this.mWorkChars = new char[i + paramArrayOfChar.length];
      this.mWorkWidths = new float[i + paramArrayOfFloat.length];
      k = 0;
      m = 0;
      n = 0;
      i1 = 0;
    }
    while (i1 >= this.mActualLineCnt);
    int i2 = this.mLineMarkers[i1];
    int i3 = this.mLineEnds[i1];
    if (i3 - k < 0)
      throw new IllegalStateException();
    System.arraycopy(paramArrayOfChar, k, this.mWorkChars, m, i3 - k);
    System.arraycopy(paramArrayOfFloat, k, this.mWorkWidths, m, i3 - k);
    int k = i3;
    switch (i2)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      int[] arrayOfInt = this.mLineEnds;
      arrayOfInt[i1] = (n + arrayOfInt[i1]);
      i1++;
      break;
      m = i3 + n;
      continue;
      this.mWorkChars[(i3 + n)] = '-';
      this.mWorkWidths[(i3 + n)] = this.mMarkersWidth[0];
      n++;
      m = i3 + n;
      continue;
      this.mWorkChars[(i3 + n)] = '.';
      this.mWorkChars[(1 + (i3 + n))] = '.';
      float f = this.mMarkersWidth[1];
      this.mWorkWidths[(i3 + n)] = f;
      this.mWorkWidths[(1 + (i3 + n))] = f;
      n += 2;
      m = i3 + n;
    }
  }

  private void seperate(TextPaint paramTextPaint, Layout.Alignment paramAlignment, TextViewElement.VerticalAlignment paramVerticalAlignment, int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    this.mPaint = paramTextPaint;
    this.mAlignment = paramAlignment;
    this.mMaxWidth = paramInt1;
    this.mMaxHeight = paramInt2;
    if ((this.mHasSeperated) || (this.mText == null));
    int i;
    char[] arrayOfChar;
    float[] arrayOfFloat;
    Paint.FontMetricsInt localFontMetricsInt;
    label221: String str2;
    int i13;
    label278: label298: float f1;
    int j;
    int k;
    label322: 
    do
    {
      return;
      this.mHasSeperated = true;
      i = this.mText.length();
      arrayOfChar = new char[i];
      arrayOfFloat = new float[i];
      if ((this.mLineEnds == null) || (this.mLineEnds.length == 0))
        this.mLineEnds = new int[this.mMaxLineCnt];
      if ((this.mLineBottom == null) || (this.mLineBottom.length == 0))
        this.mLineBottom = new int[this.mMaxLineCnt];
      if ((this.mLineMarkers == null) || (this.mLineMarkers.length == 0))
        this.mLineMarkers = new int[this.mMaxLineCnt];
      if ((this.mMarkersWidth == null) || (this.mMarkersWidth.length == 0))
        this.mMarkersWidth = new float[2];
      localFontMetricsInt = this.mPaint.getFontMetricsInt();
      TextUtils.getChars(this.mText, 0, i, arrayOfChar, 0);
      switch ($SWITCH_TABLE$fm$qingting$framework$view$TextViewElement$VerticalAlignment()[paramVerticalAlignment.ordinal()])
      {
      case 2:
      default:
        this.mVerticalOffset = 0;
        this.mPaint.getTextWidths(this.mText, arrayOfFloat);
        this.mPaint.getTextWidths(new char[] { 45, 46 }, 0, this.mMarkersWidth.length, this.mMarkersWidth);
        if (paramInt3 <= 0)
          break label441;
        str2 = "";
        i13 = 0;
        if (i13 < paramInt3)
          break label413;
        this.mIndentation = this.mPaint.measureText(str2);
        f1 = this.mIndentation;
        this.mActualLineCnt = 0;
        j = 0;
        k = 0;
        if (k < i)
          break label450;
        try
        {
          out(arrayOfChar, arrayOfFloat);
          return;
        }
        catch (IllegalStateException localIllegalStateException)
        {
        }
      case 1:
      case 3:
      }
    }
    while (this.mText == null);
    String str1 = "";
    for (int i5 = 0; ; i5++)
    {
      if (i5 >= this.mLineEnds.length)
      {
        EventDispacthManager.getInstance().dispatchAction("sendErrorLog", this.mText + str1);
        return;
        this.mVerticalOffset = (-localFontMetricsInt.descent);
        break label221;
        this.mVerticalOffset = localFontMetricsInt.ascent;
        break;
        label413: str2 = str2 + "嘿";
        i13++;
        break label278;
        label441: this.mIndentation = paramFloat;
        break label298;
        label450: char c1 = arrayOfChar[k];
        float f2 = arrayOfFloat[k];
        if (k == i - 1)
          if (this.mActualLineCnt == -1 + this.mMaxLineCnt)
          {
            j = j + localFontMetricsInt.bottom - localFontMetricsInt.top;
            this.mLineBottom[this.mActualLineCnt] = j;
            if (f1 + f2 > this.mMaxWidth)
            {
              float f3 = arrayOfFloat[k];
              this.mLineMarkers[this.mActualLineCnt] = 2;
              if (f1 - f3 + 2.0F * this.mMarkersWidth[1] <= this.mMaxWidth)
              {
                int[] arrayOfInt11 = this.mLineEnds;
                int i12 = this.mActualLineCnt;
                this.mActualLineCnt = (i12 + 1);
                arrayOfInt11[i12] = (k - 1);
              }
            }
          }
        label596: label993: 
        do
          while (true)
          {
            k++;
            break;
            if ((k > 0) && (f1 - (arrayOfFloat[k] + arrayOfFloat[(k - 1)]) + 2.0F * this.mMarkersWidth[1] <= this.mMaxWidth))
            {
              int[] arrayOfInt10 = this.mLineEnds;
              int i11 = this.mActualLineCnt;
              this.mActualLineCnt = (i11 + 1);
              arrayOfInt10[i11] = (k - 2);
              continue;
              int[] arrayOfInt9 = this.mLineEnds;
              int i10 = this.mActualLineCnt;
              this.mActualLineCnt = (i10 + 1);
              arrayOfInt9[i10] = (k + 1);
              f1 = 0.0F;
              continue;
              if (this.mActualLineCnt >= this.mMaxLineCnt)
                break label322;
              j = j + localFontMetricsInt.bottom - localFontMetricsInt.top;
              if (f1 + f2 > this.mMaxWidth)
              {
                this.mLineBottom[this.mActualLineCnt] = j;
                int[] arrayOfInt7 = this.mLineEnds;
                int i8 = this.mActualLineCnt;
                this.mActualLineCnt = (i8 + 1);
                arrayOfInt7[i8] = k;
                this.mLineBottom[this.mActualLineCnt] = (j + localFontMetricsInt.bottom - localFontMetricsInt.top);
                int[] arrayOfInt8 = this.mLineEnds;
                int i9 = this.mActualLineCnt;
                this.mActualLineCnt = (i9 + 1);
                arrayOfInt8[i9] = (k + 1);
              }
              while (true)
              {
                f1 = f2;
                break;
                this.mLineBottom[this.mActualLineCnt] = j;
                int[] arrayOfInt6 = this.mLineEnds;
                int i7 = this.mActualLineCnt;
                this.mActualLineCnt = (i7 + 1);
                arrayOfInt6[i7] = (k + 1);
              }
              if (c1 == '\n')
              {
                if (this.mActualLineCnt >= this.mMaxLineCnt)
                  break label322;
                j = j + localFontMetricsInt.bottom - localFontMetricsInt.top;
                this.mLineBottom[this.mActualLineCnt] = j;
                int[] arrayOfInt5 = this.mLineEnds;
                int i6 = this.mActualLineCnt;
                this.mActualLineCnt = (i6 + 1);
                arrayOfInt5[i6] = (k + 1);
                f1 = 0.0F;
                continue;
              }
              if (f1 + f2 > this.mMaxWidth)
                break label993;
              f1 += f2;
            }
          }
        while (this.mActualLineCnt >= this.mMaxLineCnt);
        boolean bool1 = isCharacter(c1);
        int m = 0;
        int n = 0;
        if (bool1)
        {
          m = 0;
          n = 0;
          if (k > 0)
          {
            boolean bool2 = isCharacter(arrayOfChar[(k - 1)]);
            m = 0;
            n = 0;
            if (bool2)
            {
              m = 0;
              n = 0;
              if (k > 1)
              {
                char c2 = arrayOfChar[(k - 2)];
                m = 1;
                n = 1;
                if (!isCharacter(c2))
                  break label1228;
                this.mLineMarkers[this.mActualLineCnt] = 1;
              }
            }
          }
        }
        label1104: if (this.mActualLineCnt < this.mMaxLineCnt)
        {
          j = j + localFontMetricsInt.bottom - localFontMetricsInt.top;
          this.mLineBottom[this.mActualLineCnt] = j;
          if ((this.mActualLineCnt != -1 + this.mMaxLineCnt) || (k >= i - 1))
            break label1370;
          this.mLineMarkers[this.mActualLineCnt] = 2;
          if (f1 + 2.0F * this.mMarkersWidth[1] > this.mMaxWidth)
            break label1241;
          int[] arrayOfInt4 = this.mLineEnds;
          int i4 = this.mActualLineCnt;
          this.mActualLineCnt = (i4 + 1);
          arrayOfInt4[i4] = k;
        }
        while (true)
        {
          f1 = f2;
          break label596;
          label1228: this.mLineMarkers[this.mActualLineCnt] = 0;
          break label1104;
          break;
          label1241: if (f1 - arrayOfFloat[k] + 2.0F * this.mMarkersWidth[1] <= this.mMaxWidth)
          {
            int[] arrayOfInt3 = this.mLineEnds;
            int i3 = this.mActualLineCnt;
            this.mActualLineCnt = (i3 + 1);
            arrayOfInt3[i3] = (k - 1);
          }
          else if ((k > 0) && (f1 - (arrayOfFloat[k] + arrayOfFloat[(k - 1)]) + 2.0F * this.mMarkersWidth[1] <= this.mMaxWidth))
          {
            int[] arrayOfInt2 = this.mLineEnds;
            int i2 = this.mActualLineCnt;
            this.mActualLineCnt = (i2 + 1);
            arrayOfInt2[i2] = (k - 2);
            continue;
            label1370: if (n == 0)
              this.mLineMarkers[this.mActualLineCnt] = 0;
            int[] arrayOfInt1 = this.mLineEnds;
            int i1 = this.mActualLineCnt;
            this.mActualLineCnt = (i1 + 1);
            arrayOfInt1[i1] = (k - m);
          }
        }
      }
      str1 = str1 + "_" + this.mLineEnds[i5];
    }
  }

  private void simpleOut(char[] paramArrayOfChar, float[] paramArrayOfFloat)
  {
    this.mWorkChars = paramArrayOfChar;
    this.mWorkWidths = paramArrayOfFloat;
  }

  public void draw(Canvas paramCanvas, TextPaint paramTextPaint, Layout.Alignment paramAlignment, TextViewElement.VerticalAlignment paramVerticalAlignment, int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    if (this.mText == null);
    while (true)
    {
      return;
      if ((this.mPaint != paramTextPaint) || (this.mAlignment != paramAlignment) || (this.mMaxWidth != paramInt1) || (this.mMaxHeight != paramInt2))
        this.mHasSeperated = false;
      if (!this.mHasSeperated)
        seperate(paramTextPaint, paramAlignment, paramVerticalAlignment, paramInt1, paramInt2, paramInt3, paramFloat);
      if ((this.mWorkChars != null) && (this.mWorkChars.length != 0) && (this.mWorkWidths != null) && (this.mWorkWidths.length != 0) && (this.mLineEnds != null) && (this.mLineEnds.length != 0))
      {
        int i = 0;
        for (int j = 0; j < this.mActualLineCnt; j++)
        {
          int k = i;
          i = this.mLineEnds[j];
          float f1 = getXoffset(j);
          float f2 = getYoffset(j);
          if ((i > k) && (this.mWorkChars.length >= i))
            paramCanvas.drawText(this.mWorkChars, k, i - k, f1, f2, this.mPaint);
        }
      }
    }
  }

  public int getHeight(TextPaint paramTextPaint, Layout.Alignment paramAlignment, TextViewElement.VerticalAlignment paramVerticalAlignment, int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    if ((this.mText == null) || (this.mText.equalsIgnoreCase("")));
    do
    {
      return 0;
      if (!this.mHasSeperated)
        seperate(paramTextPaint, paramAlignment, paramVerticalAlignment, paramInt1, paramInt2, paramInt3, paramFloat);
    }
    while (this.mActualLineCnt == 0);
    return (int)this.mLineBottom[(-1 + this.mActualLineCnt)];
  }

  int getLineBase(int paramInt)
  {
    return (int)getYoffset(paramInt);
  }

  int getLineCnt(TextPaint paramTextPaint, Layout.Alignment paramAlignment, TextViewElement.VerticalAlignment paramVerticalAlignment, int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    if ((this.mText == null) || (this.mText.equalsIgnoreCase("")))
      return 0;
    if (!this.mHasSeperated)
      seperate(paramTextPaint, paramAlignment, paramVerticalAlignment, paramInt1, paramInt2, paramInt3, paramFloat);
    return this.mActualLineCnt;
  }

  public int getWidth(TextPaint paramTextPaint, Layout.Alignment paramAlignment, TextViewElement.VerticalAlignment paramVerticalAlignment, int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    if ((this.mText == null) || (this.mText.equalsIgnoreCase("")));
    do
    {
      return 0;
      if (!this.mHasSeperated)
        seperate(paramTextPaint, paramAlignment, paramVerticalAlignment, paramInt1, paramInt2, paramInt3, paramFloat);
    }
    while (this.mActualLineCnt == 0);
    if (this.mActualLineCnt == 1)
    {
      float f = 0.0F;
      int i = this.mLineEnds[0];
      for (int j = 0; ; j++)
      {
        if (j >= i)
          return (int)f;
        f += this.mWorkWidths[j];
      }
    }
    return this.mMaxWidth;
  }

  boolean isCharacter(char paramChar)
  {
    return ((paramChar >= 'A') && (paramChar <= 'Z')) || ((paramChar >= 'a') && (paramChar <= 'z'));
  }

  public void setMaxLine(int paramInt)
  {
    this.mMaxLineCnt = paramInt;
  }

  public void setText(String paramString)
  {
    this.mHasSeperated = false;
    if (this.mLineEnds != null)
      this.mLineEnds = null;
    if (this.mLineBottom != null)
      this.mLineBottom = null;
    if (this.mLineMarkers != null)
      this.mLineMarkers = null;
    if (this.mWorkChars != null)
      this.mWorkChars = null;
    if (this.mWorkWidths != null)
      this.mWorkWidths = null;
    if (this.mMarkersWidth != null)
      this.mMarkersWidth = null;
    this.mText = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.QtLayout
 * JD-Core Version:    0.6.2
 */