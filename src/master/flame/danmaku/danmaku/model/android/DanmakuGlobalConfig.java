package master.flame.danmaku.danmaku.model.android;

import android.graphics.Typeface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import master.flame.danmaku.controller.DanmakuFilters;
import master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.GlobalFlagValues;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;

public class DanmakuGlobalConfig
{
  public static final int DANMAKU_STYLE_DEFAULT = -1;
  public static final int DANMAKU_STYLE_NONE = 0;
  public static final int DANMAKU_STYLE_PROJECTION = 3;
  public static final int DANMAKU_STYLE_SHADOW = 1;
  public static final int DANMAKU_STYLE_STROKEN = 2;
  public static DanmakuGlobalConfig DEFAULT = new DanmakuGlobalConfig();
  public boolean FBDanmakuVisibility = true;
  public boolean FTDanmakuVisibility = true;
  public boolean L2RDanmakuVisibility = true;
  public boolean R2LDanmakuVisibility = true;
  public boolean SecialDanmakuVisibility = true;
  public boolean isTextScaled = false;
  public boolean isTranslucent = false;
  private boolean mBlockGuestDanmaku = false;
  private ArrayList<ConfigChangedCallback> mCallbackList;
  List<Integer> mColorValueWhiteList = new ArrayList();
  private boolean mDuplicateMergingEnable = false;
  List<Integer> mFilterTypes = new ArrayList();
  public Typeface mFont = null;
  List<String> mUserHashBlackList = new ArrayList();
  List<Integer> mUserIdBlackList = new ArrayList();
  public int maximumNumsInScreen = -1;
  public int refreshRateMS = 15;
  public float scaleTextSize = 1.0F;
  public float scrollSpeedFactor = 1.0F;
  public int shadowRadius = 3;
  public BorderType shadowType = BorderType.SHADOW;
  public int transparency = AlphaValue.MAX;

  private void notifyConfigureChanged(DanmakuConfigTag paramDanmakuConfigTag, Object[] paramArrayOfObject)
  {
    if (this.mCallbackList != null)
    {
      Iterator localIterator = this.mCallbackList.iterator();
      while (localIterator.hasNext())
        ((ConfigChangedCallback)localIterator.next()).onDanmakuConfigChanged(this, paramDanmakuConfigTag, paramArrayOfObject);
    }
  }

  private void setDanmakuVisible(boolean paramBoolean, int paramInt)
  {
    if (paramBoolean)
      this.mFilterTypes.remove(Integer.valueOf(paramInt));
    while (this.mFilterTypes.contains(Integer.valueOf(paramInt)))
      return;
    this.mFilterTypes.add(Integer.valueOf(paramInt));
  }

  private <T> void setFilterData(String paramString, T paramT)
  {
    DanmakuFilters.getDefault().get(paramString).setData(paramT);
  }

  public DanmakuGlobalConfig addUserHashBlackList(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
      return this;
    Collections.addAll(this.mUserHashBlackList, paramArrayOfString);
    setFilterData("1015_Filter", this.mUserHashBlackList);
    DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.USER_HASH_BLACK_LIST;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mUserHashBlackList;
    notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    return this;
  }

  public DanmakuGlobalConfig addUserIdBlackList(Integer[] paramArrayOfInteger)
  {
    if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0))
      return this;
    Collections.addAll(this.mUserIdBlackList, paramArrayOfInteger);
    setFilterData("1014_Filter", this.mUserIdBlackList);
    DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.USER_ID_BLACK_LIST;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mUserIdBlackList;
    notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    return this;
  }

  public DanmakuGlobalConfig blockGuestDanmaku(boolean paramBoolean)
  {
    if (this.mBlockGuestDanmaku != paramBoolean)
    {
      this.mBlockGuestDanmaku = paramBoolean;
      if (!paramBoolean)
        break label51;
      setFilterData("1016_Filter", Boolean.valueOf(paramBoolean));
    }
    while (true)
    {
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.BLOCK_GUEST_DANMAKU;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
      return this;
      label51: DanmakuFilters.getDefault().unregisterFilter("1016_Filter");
    }
  }

  public List<Integer> getColorValueWhiteList()
  {
    return this.mColorValueWhiteList;
  }

  public boolean getFBDanmakuVisibility()
  {
    return this.FBDanmakuVisibility;
  }

  public boolean getFTDanmakuVisibility()
  {
    return this.FTDanmakuVisibility;
  }

  public boolean getL2RDanmakuVisibility()
  {
    return this.L2RDanmakuVisibility;
  }

  public boolean getR2LDanmakuVisibility()
  {
    return this.R2LDanmakuVisibility;
  }

  public boolean getSecialDanmakuVisibility()
  {
    return this.SecialDanmakuVisibility;
  }

  public List<String> getUserHashBlackList()
  {
    return this.mUserHashBlackList;
  }

  public List<Integer> getUserIdBlackList()
  {
    return this.mUserIdBlackList;
  }

  public boolean isDuplicateMergingEnabled()
  {
    return this.mDuplicateMergingEnable;
  }

  public void registerConfigChangedCallback(ConfigChangedCallback paramConfigChangedCallback)
  {
    if (this.mCallbackList == null)
      this.mCallbackList = new ArrayList();
    this.mCallbackList.add(paramConfigChangedCallback);
  }

  public DanmakuGlobalConfig removeUserHashBlackList(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
      return this;
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramArrayOfString[j];
      this.mUserHashBlackList.remove(str);
    }
    setFilterData("1015_Filter", this.mUserHashBlackList);
    DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.USER_HASH_BLACK_LIST;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mUserHashBlackList;
    notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    return this;
  }

  public DanmakuGlobalConfig removeUserIdBlackList(Integer[] paramArrayOfInteger)
  {
    if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0))
      return this;
    int i = paramArrayOfInteger.length;
    for (int j = 0; j < i; j++)
    {
      Integer localInteger = paramArrayOfInteger[j];
      this.mUserIdBlackList.remove(localInteger);
    }
    setFilterData("1014_Filter", this.mUserIdBlackList);
    DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.USER_ID_BLACK_LIST;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mUserIdBlackList;
    notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    return this;
  }

  public DanmakuGlobalConfig setColorValueWhiteList(Integer[] paramArrayOfInteger)
  {
    this.mColorValueWhiteList.clear();
    if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0))
      DanmakuFilters.getDefault().unregisterFilter("1013_Filter");
    while (true)
    {
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.COLOR_VALUE_WHITE_LIST;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mColorValueWhiteList;
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
      return this;
      Collections.addAll(this.mColorValueWhiteList, paramArrayOfInteger);
      setFilterData("1013_Filter", this.mColorValueWhiteList);
    }
  }

  public DanmakuGlobalConfig setDanmakuBold(boolean paramBoolean)
  {
    AndroidDisplayer.setFakeBoldText(paramBoolean);
    DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.DANMAKU_BOLD;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Boolean.valueOf(paramBoolean);
    notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    return this;
  }

  public DanmakuGlobalConfig setDanmakuStyle(int paramInt, float[] paramArrayOfFloat)
  {
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    case -1:
    case 2:
    case 3:
    }
    while (true)
    {
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.DANMAKU_STYLE;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = Float.valueOf(paramArrayOfFloat[0]);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
      return this;
      AndroidDisplayer.CONFIG_HAS_SHADOW = false;
      AndroidDisplayer.CONFIG_HAS_STROKE = false;
      AndroidDisplayer.CONFIG_HAS_PROJECTION = false;
      continue;
      AndroidDisplayer.CONFIG_HAS_SHADOW = true;
      AndroidDisplayer.CONFIG_HAS_STROKE = false;
      AndroidDisplayer.CONFIG_HAS_PROJECTION = false;
      AndroidDisplayer.setShadowRadius(paramArrayOfFloat[0]);
      continue;
      AndroidDisplayer.CONFIG_HAS_SHADOW = false;
      AndroidDisplayer.CONFIG_HAS_STROKE = true;
      AndroidDisplayer.CONFIG_HAS_PROJECTION = false;
      AndroidDisplayer.setPaintStorkeWidth(paramArrayOfFloat[0]);
      continue;
      AndroidDisplayer.CONFIG_HAS_SHADOW = false;
      AndroidDisplayer.CONFIG_HAS_STROKE = false;
      AndroidDisplayer.CONFIG_HAS_PROJECTION = true;
      AndroidDisplayer.setProjectionConfig(paramArrayOfFloat[0], paramArrayOfFloat[1], (int)paramArrayOfFloat[2]);
    }
  }

  public DanmakuGlobalConfig setDanmakuTransparency(float paramFloat)
  {
    int i = (int)(paramFloat * AlphaValue.MAX);
    if (i != this.transparency)
    {
      this.transparency = i;
      if (i == AlphaValue.MAX)
        break label64;
    }
    label64: for (boolean bool = true; ; bool = false)
    {
      this.isTranslucent = bool;
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.TRANSPARENCY;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Float.valueOf(paramFloat);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
      return this;
    }
  }

  public DanmakuGlobalConfig setDuplicateMergingEnabled(boolean paramBoolean)
  {
    if (this.mDuplicateMergingEnable != paramBoolean)
    {
      this.mDuplicateMergingEnable = paramBoolean;
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.DUPLICATE_MERGING_ENABLED;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    }
    return this;
  }

  public DanmakuGlobalConfig setFBDanmakuVisibility(boolean paramBoolean)
  {
    setDanmakuVisible(paramBoolean, 4);
    setFilterData("1010_Filter", this.mFilterTypes);
    if (this.FBDanmakuVisibility != paramBoolean)
    {
      this.FBDanmakuVisibility = paramBoolean;
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.FB_DANMAKU_VISIBILITY;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    }
    return this;
  }

  public DanmakuGlobalConfig setFTDanmakuVisibility(boolean paramBoolean)
  {
    setDanmakuVisible(paramBoolean, 5);
    setFilterData("1010_Filter", this.mFilterTypes);
    if (this.FTDanmakuVisibility != paramBoolean)
    {
      this.FTDanmakuVisibility = paramBoolean;
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.FT_DANMAKU_VISIBILITY;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    }
    return this;
  }

  public DanmakuGlobalConfig setL2RDanmakuVisibility(boolean paramBoolean)
  {
    setDanmakuVisible(paramBoolean, 6);
    setFilterData("1010_Filter", this.mFilterTypes);
    if (this.L2RDanmakuVisibility != paramBoolean)
    {
      this.L2RDanmakuVisibility = paramBoolean;
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.L2R_DANMAKU_VISIBILITY;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    }
    return this;
  }

  public DanmakuGlobalConfig setMaximumVisibleSizeInScreen(int paramInt)
  {
    this.maximumNumsInScreen = paramInt;
    if (paramInt == 0)
    {
      DanmakuFilters.getDefault().unregisterFilter("1011_Filter");
      DanmakuFilters.getDefault().unregisterFilter("1012_Filter");
      DanmakuConfigTag localDanmakuConfigTag3 = DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(paramInt);
      notifyConfigureChanged(localDanmakuConfigTag3, arrayOfObject3);
      return this;
    }
    if (paramInt == -1)
    {
      DanmakuFilters.getDefault().unregisterFilter("1011_Filter");
      DanmakuFilters.getDefault().registerFilter("1012_Filter");
      DanmakuConfigTag localDanmakuConfigTag2 = DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(paramInt);
      notifyConfigureChanged(localDanmakuConfigTag2, arrayOfObject2);
      return this;
    }
    setFilterData("1011_Filter", Integer.valueOf(paramInt));
    DanmakuConfigTag localDanmakuConfigTag1 = DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN;
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(paramInt);
    notifyConfigureChanged(localDanmakuConfigTag1, arrayOfObject1);
    return this;
  }

  public DanmakuGlobalConfig setR2LDanmakuVisibility(boolean paramBoolean)
  {
    setDanmakuVisible(paramBoolean, 1);
    setFilterData("1010_Filter", this.mFilterTypes);
    if (this.R2LDanmakuVisibility != paramBoolean)
    {
      this.R2LDanmakuVisibility = paramBoolean;
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.R2L_DANMAKU_VISIBILIY;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    }
    return this;
  }

  public DanmakuGlobalConfig setScaleTextSize(float paramFloat)
  {
    boolean bool = true;
    if (this.scaleTextSize != paramFloat)
    {
      this.scaleTextSize = paramFloat;
      AndroidDisplayer.clearTextHeightCache();
      GlobalFlagValues.updateMeasureFlag();
      GlobalFlagValues.updateVisibleFlag();
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.SCALE_TEXTSIZE;
      Object[] arrayOfObject = new Object[bool];
      arrayOfObject[0] = Float.valueOf(paramFloat);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    }
    if (this.scaleTextSize != 1.0F);
    while (true)
    {
      this.isTextScaled = bool;
      return this;
      bool = false;
    }
  }

  public DanmakuGlobalConfig setScrollSpeedFactor(float paramFloat)
  {
    if (this.scrollSpeedFactor != paramFloat)
    {
      this.scrollSpeedFactor = paramFloat;
      DanmakuFactory.updateDurationFactor(paramFloat);
      GlobalFlagValues.updateMeasureFlag();
      GlobalFlagValues.updateVisibleFlag();
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.SCROLL_SPEED_FACTOR;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Float.valueOf(paramFloat);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    }
    return this;
  }

  public DanmakuGlobalConfig setSpecialDanmakuVisibility(boolean paramBoolean)
  {
    setDanmakuVisible(paramBoolean, 7);
    setFilterData("1010_Filter", this.mFilterTypes);
    if (this.SecialDanmakuVisibility != paramBoolean)
    {
      this.SecialDanmakuVisibility = paramBoolean;
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.SPECIAL_DANMAKU_VISIBILITY;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
    }
    return this;
  }

  public DanmakuGlobalConfig setTypeface(Typeface paramTypeface)
  {
    if (this.mFont != paramTypeface)
    {
      this.mFont = paramTypeface;
      AndroidDisplayer.clearTextHeightCache();
      AndroidDisplayer.setTypeFace(paramTypeface);
      notifyConfigureChanged(DanmakuConfigTag.TYPEFACE, new Object[0]);
    }
    return this;
  }

  public DanmakuGlobalConfig setUserHashBlackList(String[] paramArrayOfString)
  {
    this.mUserHashBlackList.clear();
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
      DanmakuFilters.getDefault().unregisterFilter("1015_Filter");
    while (true)
    {
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.USER_HASH_BLACK_LIST;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mUserHashBlackList;
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
      return this;
      Collections.addAll(this.mUserHashBlackList, paramArrayOfString);
      setFilterData("1015_Filter", this.mUserHashBlackList);
    }
  }

  public DanmakuGlobalConfig setUserIdBlackList(Integer[] paramArrayOfInteger)
  {
    this.mUserIdBlackList.clear();
    if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0))
      DanmakuFilters.getDefault().unregisterFilter("1014_Filter");
    while (true)
    {
      DanmakuConfigTag localDanmakuConfigTag = DanmakuConfigTag.USER_ID_BLACK_LIST;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mUserIdBlackList;
      notifyConfigureChanged(localDanmakuConfigTag, arrayOfObject);
      return this;
      Collections.addAll(this.mUserIdBlackList, paramArrayOfInteger);
      setFilterData("1014_Filter", this.mUserIdBlackList);
    }
  }

  public void unregisterConfigChangedCallback(ConfigChangedCallback paramConfigChangedCallback)
  {
    if (this.mCallbackList == null)
      return;
    this.mCallbackList.remove(paramConfigChangedCallback);
  }

  public static enum BorderType
  {
    static
    {
      BorderType[] arrayOfBorderType = new BorderType[3];
      arrayOfBorderType[0] = NONE;
      arrayOfBorderType[1] = SHADOW;
      arrayOfBorderType[2] = STROKEN;
    }
  }

  public static abstract interface ConfigChangedCallback
  {
    public abstract boolean onDanmakuConfigChanged(DanmakuGlobalConfig paramDanmakuGlobalConfig, DanmakuGlobalConfig.DanmakuConfigTag paramDanmakuConfigTag, Object[] paramArrayOfObject);
  }

  public static enum DanmakuConfigTag
  {
    static
    {
      FB_DANMAKU_VISIBILITY = new DanmakuConfigTag("FB_DANMAKU_VISIBILITY", 1);
      L2R_DANMAKU_VISIBILITY = new DanmakuConfigTag("L2R_DANMAKU_VISIBILITY", 2);
      R2L_DANMAKU_VISIBILIY = new DanmakuConfigTag("R2L_DANMAKU_VISIBILIY", 3);
      SPECIAL_DANMAKU_VISIBILITY = new DanmakuConfigTag("SPECIAL_DANMAKU_VISIBILITY", 4);
      TYPEFACE = new DanmakuConfigTag("TYPEFACE", 5);
      TRANSPARENCY = new DanmakuConfigTag("TRANSPARENCY", 6);
      SCALE_TEXTSIZE = new DanmakuConfigTag("SCALE_TEXTSIZE", 7);
      MAXIMUM_NUMS_IN_SCREEN = new DanmakuConfigTag("MAXIMUM_NUMS_IN_SCREEN", 8);
      DANMAKU_STYLE = new DanmakuConfigTag("DANMAKU_STYLE", 9);
      DANMAKU_BOLD = new DanmakuConfigTag("DANMAKU_BOLD", 10);
      COLOR_VALUE_WHITE_LIST = new DanmakuConfigTag("COLOR_VALUE_WHITE_LIST", 11);
      USER_ID_BLACK_LIST = new DanmakuConfigTag("USER_ID_BLACK_LIST", 12);
      USER_HASH_BLACK_LIST = new DanmakuConfigTag("USER_HASH_BLACK_LIST", 13);
      SCROLL_SPEED_FACTOR = new DanmakuConfigTag("SCROLL_SPEED_FACTOR", 14);
      BLOCK_GUEST_DANMAKU = new DanmakuConfigTag("BLOCK_GUEST_DANMAKU", 15);
      DUPLICATE_MERGING_ENABLED = new DanmakuConfigTag("DUPLICATE_MERGING_ENABLED", 16);
      DanmakuConfigTag[] arrayOfDanmakuConfigTag = new DanmakuConfigTag[17];
      arrayOfDanmakuConfigTag[0] = FT_DANMAKU_VISIBILITY;
      arrayOfDanmakuConfigTag[1] = FB_DANMAKU_VISIBILITY;
      arrayOfDanmakuConfigTag[2] = L2R_DANMAKU_VISIBILITY;
      arrayOfDanmakuConfigTag[3] = R2L_DANMAKU_VISIBILIY;
      arrayOfDanmakuConfigTag[4] = SPECIAL_DANMAKU_VISIBILITY;
      arrayOfDanmakuConfigTag[5] = TYPEFACE;
      arrayOfDanmakuConfigTag[6] = TRANSPARENCY;
      arrayOfDanmakuConfigTag[7] = SCALE_TEXTSIZE;
      arrayOfDanmakuConfigTag[8] = MAXIMUM_NUMS_IN_SCREEN;
      arrayOfDanmakuConfigTag[9] = DANMAKU_STYLE;
      arrayOfDanmakuConfigTag[10] = DANMAKU_BOLD;
      arrayOfDanmakuConfigTag[11] = COLOR_VALUE_WHITE_LIST;
      arrayOfDanmakuConfigTag[12] = USER_ID_BLACK_LIST;
      arrayOfDanmakuConfigTag[13] = USER_HASH_BLACK_LIST;
      arrayOfDanmakuConfigTag[14] = SCROLL_SPEED_FACTOR;
      arrayOfDanmakuConfigTag[15] = BLOCK_GUEST_DANMAKU;
      arrayOfDanmakuConfigTag[16] = DUPLICATE_MERGING_ENABLED;
    }

    public boolean isVisibilityRelatedTag()
    {
      return (equals(FT_DANMAKU_VISIBILITY)) || (equals(FB_DANMAKU_VISIBILITY)) || (equals(L2R_DANMAKU_VISIBILITY)) || (equals(R2L_DANMAKU_VISIBILIY)) || (equals(SPECIAL_DANMAKU_VISIBILITY)) || (equals(COLOR_VALUE_WHITE_LIST)) || (equals(USER_ID_BLACK_LIST));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.android.DanmakuGlobalConfig
 * JD-Core Version:    0.6.2
 */