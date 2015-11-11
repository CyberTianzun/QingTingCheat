package fm.qingting.download.qtradiodownload.network.http.exception;

import java.util.HashMap;

public class ErrorMessage
{
  public static final int ERROR_CODE_MALFORMED_URL = 101;
  public static final int ERROR_CODE_NETWORK_INVALID = 100;
  public static final int ERROR_CODE_OPEN_CONNECTION = 102;
  public static final int ERROR_OTHER_EXCEPTION = 105;
  public static final int ERROR_SERVER_UNEXPECTED_RESP = 103;
  public static final int ERROR_WRITE_READ_DATA = 104;
  private static HashMap<Integer, String> errors = new HashMap();

  static
  {
    errors.put(Integer.valueOf(100), "没有可用的网络连接");
    errors.put(Integer.valueOf(101), "错误的URL");
    errors.put(Integer.valueOf(102), "无法连接到服务器");
    errors.put(Integer.valueOf(105), "空指针造成连接失败");
  }

  public static String getErrorMessage(int paramInt)
  {
    return (String)errors.get(Integer.valueOf(paramInt));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.http.exception.ErrorMessage
 * JD-Core Version:    0.6.2
 */