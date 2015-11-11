package fm.qingting.qtradio.fm;

public enum ErrorMessage
{
  private String _name;

  static
  {
    ERROR_DECODER_PAUSE = new ErrorMessage("ERROR_DECODER_PAUSE", 3, "error_decoder_pause");
    ERROR_DECODER_RENEW = new ErrorMessage("ERROR_DECODER_RENEW", 4, "error_decoder_renew");
    ERROR_DECODER_RELEASE = new ErrorMessage("ERROR_DECODER_RELEASE", 5, "error_decoder_release");
    ERROR_DECODER_CONNECTION = new ErrorMessage("ERROR_DECODER_CONNECTION", 6, "error_decoder_connection");
    ERROR_DECODER_LOADING = new ErrorMessage("ERROR_DECODER_LOADING", 7, "error_decoder_loading");
    UNKNOWN = new ErrorMessage("UNKNOWN", 8, "unknown");
    ErrorMessage[] arrayOfErrorMessage = new ErrorMessage[9];
    arrayOfErrorMessage[0] = ERROR_DECODER_DECODING;
    arrayOfErrorMessage[1] = ERROR_DECODER_PLAYING;
    arrayOfErrorMessage[2] = ERROR_DECODER_STOP;
    arrayOfErrorMessage[3] = ERROR_DECODER_PAUSE;
    arrayOfErrorMessage[4] = ERROR_DECODER_RENEW;
    arrayOfErrorMessage[5] = ERROR_DECODER_RELEASE;
    arrayOfErrorMessage[6] = ERROR_DECODER_CONNECTION;
    arrayOfErrorMessage[7] = ERROR_DECODER_LOADING;
    arrayOfErrorMessage[8] = UNKNOWN;
  }

  private ErrorMessage(String paramString)
  {
    this._name = paramString;
  }

  public static ErrorMessage GetInteraction(String paramString)
  {
    try
    {
      ErrorMessage localErrorMessage = (ErrorMessage)Enum.valueOf(ErrorMessage.class, paramString.toUpperCase());
      return localErrorMessage;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
    return UNKNOWN;
  }

  public String toString()
  {
    return this._name;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.fm.ErrorMessage
 * JD-Core Version:    0.6.2
 */