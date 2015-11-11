package fm.qingting.qtradio.view.chatroom;

import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.room.UserInfo;
import java.util.HashMap;
import java.util.Map;

public class FlowerInfo
{
  public static final int FLOWER_INTERVAL = 7200000;
  public static final int MAX_FLOWER_CNT = 10;
  private static long MAX_FLOWER_INTERVAL = 10L;
  private static long mLastFlowerTime;
  private static Map<String, Integer> mLeftFlowerCnt = new HashMap();
  private static String mRoomId;

  static
  {
    mLastFlowerTime = 0L;
  }

  public static boolean allowFlower(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null);
    long l;
    do
    {
      return false;
      l = System.currentTimeMillis();
    }
    while ((l - mLastFlowerTime) / 1000L <= MAX_FLOWER_INTERVAL);
    mLastFlowerTime = l;
    return true;
  }

  public static void checkFlowerCnt(String paramString)
  {
    mRoomId = paramString;
    long l = SharedCfg.getInstance().getFlowerChangeTime(mRoomId);
    int i = (int)((System.currentTimeMillis() - l) / 7200000L);
    if (i < 0)
      i = 0;
    if (increaseFlowerCnt(i) < 10)
      SharedCfg.getInstance().updateFlowerChangeTime(mRoomId, l + i * 7200000);
  }

  public static int decreaseFlowerCnt()
  {
    if (mRoomId == null);
    while (!mLeftFlowerCnt.containsKey(mRoomId))
      return 0;
    int i = ((Integer)mLeftFlowerCnt.get(mRoomId)).intValue();
    if (i == 10)
      SharedCfg.getInstance().updateFlowerChangeTime(mRoomId, System.currentTimeMillis());
    int j = i - 1;
    if (j < 0)
      j = 0;
    mLeftFlowerCnt.put(mRoomId, Integer.valueOf(j));
    SharedCfg.getInstance().updateFlowerCnt(mRoomId, j);
    return j;
  }

  public static int getFlowerCnt()
  {
    int i;
    if (mRoomId == null)
      i = 10;
    do
    {
      return i;
      if (!mLeftFlowerCnt.containsKey(mRoomId))
        initFlowerCnt();
      i = ((Integer)mLeftFlowerCnt.get(mRoomId)).intValue();
    }
    while (i >= 0);
    return 0;
  }

  public static int increaseFlowerCnt(int paramInt)
  {
    int i = 10;
    if (mRoomId == null)
      return 0;
    int j = getFlowerCnt();
    if (paramInt == 0)
      return j;
    int k = j + paramInt;
    if (k >= i);
    while (true)
    {
      mLeftFlowerCnt.put(mRoomId, Integer.valueOf(i));
      SharedCfg.getInstance().updateFlowerCnt(mRoomId, i);
      return i;
      i = k;
    }
  }

  public static void initFlowerCnt()
  {
    int i = 10;
    int j = SharedCfg.getInstance().getFlowerCnt(mRoomId);
    if (j < 0)
      j = 0;
    if (j > i);
    while (true)
    {
      mLeftFlowerCnt.put(mRoomId, Integer.valueOf(i));
      return;
      i = j;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.FlowerInfo
 * JD-Core Version:    0.6.2
 */