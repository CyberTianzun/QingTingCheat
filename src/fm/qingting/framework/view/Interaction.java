package fm.qingting.framework.view;

public enum Interaction
{
  private String _name;

  static
  {
    CHANGE_TARGET = new Interaction("CHANGE_TARGET", 1, "change_target");
    SELECT = new Interaction("SELECT", 2, "select");
    SELECT_ALL = new Interaction("SELECT_ALL", 3, "select_all");
    SYNC = new Interaction("SYNC", 4, "sync");
    SYNC_SELECTED = new Interaction("SYNC_SELECTED", 5, "sync_selected");
    DELETE = new Interaction("DELETE", 6, "delete");
    DELETE_SELECTED = new Interaction("DELETE_SELECTED", 7, "delete_selected");
    UPDATE = new Interaction("UPDATE", 8, "update");
    LOGIN = new Interaction("LOGIN", 9, "login");
    COMBINE = new Interaction("COMBINE", 10, "combine");
    RETURN = new Interaction("RETURN", 11, "return");
    ADD = new Interaction("ADD", 12, "add");
    SEND_SMS = new Interaction("SEND_SMS", 13, "send_sms");
    CALL = new Interaction("CALL", 14, "call");
    DETAIL = new Interaction("DETAIL", 15, "detail");
    CANCEL = new Interaction("CANCEL", 16, "cancel");
    SAVE = new Interaction("SAVE", 17, "save");
    CLOSE = new Interaction("CLOSE", 18, "close");
    REFRESH = new Interaction("REFRESH", 19, "refresh");
    OPEN_CONTENT = new Interaction("OPEN_CONTENT", 20, "open_content");
    OPEN = new Interaction("OPEN", 21, "open");
    QUOTE = new Interaction("QUOTE", 22, "quote");
    LOGOUT = new Interaction("LOGOUT", 23, "logout");
    QUIT = new Interaction("QUIT", 24, "quit");
    CHANGE_THREAD_LIST = new Interaction("CHANGE_THREAD_LIST", 25, "change_thread_list");
    ON_SCROLL_END = new Interaction("ON_SCROLL_END", 26, "on_scroll_end");
    ON_SCROLL = new Interaction("ON_SCROLL", 27, "on_scroll");
    ON_ITEM_CLICK = new Interaction("ON_ITEM_CLICK", 28, "on_item_click");
    ON_HEADER_CLICK = new Interaction("ON_HEADER_CLICK", 29, "on_header_click");
    ON_FOOTER_CLICK = new Interaction("ON_FOOTER_CLICK", 30, "on_footer_click");
    BIND_URL = new Interaction("BIND_URL", 31, "bind_url");
    CLOSE_POP = new Interaction("CLOSE_POP", 32, "close_pop");
    UNKNOWN = new Interaction("UNKNOWN", 33, "unknown");
    Interaction[] arrayOfInteraction = new Interaction[34];
    arrayOfInteraction[0] = SEARCH;
    arrayOfInteraction[1] = CHANGE_TARGET;
    arrayOfInteraction[2] = SELECT;
    arrayOfInteraction[3] = SELECT_ALL;
    arrayOfInteraction[4] = SYNC;
    arrayOfInteraction[5] = SYNC_SELECTED;
    arrayOfInteraction[6] = DELETE;
    arrayOfInteraction[7] = DELETE_SELECTED;
    arrayOfInteraction[8] = UPDATE;
    arrayOfInteraction[9] = LOGIN;
    arrayOfInteraction[10] = COMBINE;
    arrayOfInteraction[11] = RETURN;
    arrayOfInteraction[12] = ADD;
    arrayOfInteraction[13] = SEND_SMS;
    arrayOfInteraction[14] = CALL;
    arrayOfInteraction[15] = DETAIL;
    arrayOfInteraction[16] = CANCEL;
    arrayOfInteraction[17] = SAVE;
    arrayOfInteraction[18] = CLOSE;
    arrayOfInteraction[19] = REFRESH;
    arrayOfInteraction[20] = OPEN_CONTENT;
    arrayOfInteraction[21] = OPEN;
    arrayOfInteraction[22] = QUOTE;
    arrayOfInteraction[23] = LOGOUT;
    arrayOfInteraction[24] = QUIT;
    arrayOfInteraction[25] = CHANGE_THREAD_LIST;
    arrayOfInteraction[26] = ON_SCROLL_END;
    arrayOfInteraction[27] = ON_SCROLL;
    arrayOfInteraction[28] = ON_ITEM_CLICK;
    arrayOfInteraction[29] = ON_HEADER_CLICK;
    arrayOfInteraction[30] = ON_FOOTER_CLICK;
    arrayOfInteraction[31] = BIND_URL;
    arrayOfInteraction[32] = CLOSE_POP;
    arrayOfInteraction[33] = UNKNOWN;
  }

  private Interaction(String arg3)
  {
    Object localObject;
    this._name = localObject;
  }

  public static Interaction getInteraction(String paramString)
  {
    try
    {
      Interaction localInteraction = (Interaction)Enum.valueOf(Interaction.class, paramString.toUpperCase());
      return localInteraction;
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
 * Qualified Name:     fm.qingting.framework.view.Interaction
 * JD-Core Version:    0.6.2
 */