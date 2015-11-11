package fm.qingting.qtradio.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.qtradio.im.IMContact;
import fm.qingting.qtradio.im.message.IMMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IMDatabaseDS
  implements IDataSource
{
  private static IMDatabaseDS instance = null;

  public static IMDatabaseDS getInstance()
  {
    if (instance == null)
      instance = new IMDatabaseDS();
    return instance;
  }

  private IMContact restoreContact(Cursor paramCursor, Boolean paramBoolean)
  {
    int i = 1;
    try
    {
      IMContact localIMContact = new IMContact();
      localIMContact.Avatar = paramCursor.getString(paramCursor.getColumnIndex("avatar"));
      localIMContact.Gender = paramCursor.getString(paramCursor.getColumnIndex("gender"));
      localIMContact.IsGroupContact = paramBoolean;
      if (paramCursor.getInt(paramCursor.getColumnIndex("isBlocked")) == i);
      while (true)
      {
        localIMContact.IsBlocked = Boolean.valueOf(i);
        localIMContact.Level = paramCursor.getInt(paramCursor.getColumnIndex("level"));
        localIMContact.Name = paramCursor.getString(paramCursor.getColumnIndex("name"));
        return localIMContact;
        int j = 0;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private IMContact restoreGroupContact(Cursor paramCursor)
  {
    return restoreContact(paramCursor, Boolean.valueOf(true));
  }

  private IMContact restoreUserContact(Cursor paramCursor)
  {
    return restoreContact(paramCursor, Boolean.valueOf(false));
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public void appendGroupMessage(IMMessage paramIMMessage, boolean paramBoolean)
  {
    if (paramIMMessage == null);
    while (true)
    {
      return;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imDatabase");
        localSQLiteDatabase.beginTransaction();
        Object[] arrayOfObject = new Object[9];
        arrayOfObject[0] = Long.valueOf(paramIMMessage.msgSeq);
        arrayOfObject[1] = "";
        arrayOfObject[2] = paramIMMessage.mFromGroupId;
        arrayOfObject[3] = paramIMMessage.mFromID;
        arrayOfObject[4] = Integer.valueOf(0);
        arrayOfObject[5] = paramIMMessage.mMessage;
        arrayOfObject[6] = Long.valueOf(paramIMMessage.publish);
        arrayOfObject[7] = paramIMMessage.mFromAvatar;
        arrayOfObject[8] = paramIMMessage.mFromName;
        localSQLiteDatabase.execSQL("INSERT INTO groupMessage(msgSeq,msgId,groupId, fromContactId, contentType, content, timestamp,avatar,name)VALUES(?, ?, ?,       ?,         ?,           ?,       ?,?,?)", arrayOfObject);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        if (paramBoolean)
        {
          localSQLiteDatabase.close();
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void appendListGroupMessage(List<IMMessage> paramList, boolean paramBoolean)
  {
    if (paramList == null);
    while (true)
    {
      return;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imDatabase");
        localSQLiteDatabase.beginTransaction();
        for (int i = 0; i < paramList.size(); i++)
        {
          Object[] arrayOfObject = new Object[9];
          arrayOfObject[0] = Long.valueOf(((IMMessage)paramList.get(i)).msgSeq);
          arrayOfObject[1] = "";
          arrayOfObject[2] = ((IMMessage)paramList.get(i)).mFromGroupId;
          arrayOfObject[3] = ((IMMessage)paramList.get(i)).mFromID;
          arrayOfObject[4] = Integer.valueOf(0);
          arrayOfObject[5] = ((IMMessage)paramList.get(i)).mMessage;
          arrayOfObject[6] = Long.valueOf(((IMMessage)paramList.get(i)).publish);
          arrayOfObject[7] = ((IMMessage)paramList.get(i)).mFromAvatar;
          arrayOfObject[8] = ((IMMessage)paramList.get(i)).mFromName;
          localSQLiteDatabase.execSQL("INSERT INTO groupMessage(msgSeq, msgId,groupId, fromContactId, contentType, content, timestamp,avatar,name)VALUES(?, ?, ?,       ?,         ?,           ?,       ?,? ,?)", arrayOfObject);
        }
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        if (paramBoolean)
        {
          localSQLiteDatabase.close();
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void appendListPrivateMessage(List<IMMessage> paramList, boolean paramBoolean)
  {
    if (paramList == null);
    while (true)
    {
      return;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imDatabase");
        localSQLiteDatabase.beginTransaction();
        for (int i = 0; i < paramList.size(); i++)
        {
          Object[] arrayOfObject = new Object[9];
          arrayOfObject[0] = Long.valueOf(((IMMessage)paramList.get(i)).msgSeq);
          arrayOfObject[1] = "";
          arrayOfObject[2] = ((IMMessage)paramList.get(i)).mFromID;
          arrayOfObject[3] = ((IMMessage)paramList.get(i)).mToUserId;
          arrayOfObject[4] = Integer.valueOf(0);
          arrayOfObject[5] = ((IMMessage)paramList.get(i)).mMessage;
          arrayOfObject[6] = Long.valueOf(((IMMessage)paramList.get(i)).publish);
          arrayOfObject[7] = ((IMMessage)paramList.get(i)).mFromAvatar;
          arrayOfObject[8] = ((IMMessage)paramList.get(i)).mFromName;
          localSQLiteDatabase.execSQL("INSERT INTO userMessage(msgSeq,msgId,fromContactId, toContactId, contentType, content, timestamp,avatar,name)VALUES(?, ? ,?,             ?,           ?,           ?,  ?,?,?)", arrayOfObject);
        }
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        if (paramBoolean)
        {
          localSQLiteDatabase.close();
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void appendPrivateMessage(IMMessage paramIMMessage, boolean paramBoolean)
  {
    if (paramIMMessage == null);
    while (true)
    {
      return;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imDatabase");
        localSQLiteDatabase.beginTransaction();
        Object[] arrayOfObject = new Object[9];
        arrayOfObject[0] = Long.valueOf(paramIMMessage.msgSeq);
        arrayOfObject[1] = "";
        arrayOfObject[2] = paramIMMessage.mFromID;
        arrayOfObject[3] = paramIMMessage.mToUserId;
        arrayOfObject[4] = Integer.valueOf(0);
        arrayOfObject[5] = paramIMMessage.mMessage;
        arrayOfObject[6] = Long.valueOf(paramIMMessage.publish);
        arrayOfObject[7] = paramIMMessage.mFromAvatar;
        arrayOfObject[8] = paramIMMessage.mFromName;
        localSQLiteDatabase.execSQL("INSERT INTO userMessage(msgSeq,msgId,fromContactId, toContactId, contentType, content, timestamp,avatar,name)VALUES(?,   ?,  ?,             ?,           ?,           ?,       ?,?,?)", arrayOfObject);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        if (paramBoolean)
        {
          localSQLiteDatabase.close();
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public String dataSourceName()
  {
    return "IMDatabaseDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    return null;
  }

  public IMContact getGroupContact(String paramString)
  {
    while (true)
    {
      try
      {
        Cursor localCursor = DBManager.getInstance().getReadableDB("imDatabase").rawQuery("SELECT * FROM groupContact WHERE (id = '" + paramString + "')", null);
        if (localCursor.moveToNext())
        {
          localIMContact = restoreGroupContact(localCursor);
          localCursor.close();
          return localIMContact;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      IMContact localIMContact = null;
    }
  }

  public List<IMMessage> getGroupConversation(String paramString, int paramInt)
  {
    if (paramString == null)
      return null;
    ArrayList localArrayList;
    Cursor localCursor;
    try
    {
      localArrayList = new ArrayList();
      localCursor = DBManager.getInstance().getReadableDB("imDatabase").rawQuery("SELECT * FROM groupMessage WHERE ( groupId = '" + paramString + "' ) ORDER BY msgSeq DESC LIMIT " + paramInt, null);
      while (localCursor.moveToNext())
      {
        IMMessage localIMMessage = new IMMessage();
        localIMMessage.chatType = 1;
        localIMMessage.mFromID = localCursor.getString(localCursor.getColumnIndex("fromContactId"));
        localIMMessage.mFromGroupId = paramString;
        localIMMessage.mToUserId = null;
        localIMMessage.mMessage = localCursor.getString(localCursor.getColumnIndex("content"));
        localIMMessage.publish = localCursor.getLong(localCursor.getColumnIndex("timestamp"));
        localIMMessage.msgSeq = localCursor.getInt(localCursor.getColumnIndex("msgSeq"));
        localIMMessage.mFromAvatar = localCursor.getString(localCursor.getColumnIndex("avatar"));
        localIMMessage.mFromName = localCursor.getString(localCursor.getColumnIndex("name"));
        localArrayList.add(localIMMessage);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localCursor.close();
    return localArrayList;
  }

  public List<IMMessage> getGroupConversationLessThan(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString == null)
      return null;
    ArrayList localArrayList;
    Cursor localCursor;
    try
    {
      localArrayList = new ArrayList();
      localCursor = DBManager.getInstance().getReadableDB("imDatabase").rawQuery("SELECT * FROM groupMessage WHERE ( groupId = '" + paramString + "' AND msgSeq < '" + paramInt2 + "' ) ORDER BY msgSeq DESC LIMIT " + paramInt1, null);
      while (localCursor.moveToNext())
      {
        IMMessage localIMMessage = new IMMessage();
        localIMMessage.chatType = 1;
        localIMMessage.mFromID = localCursor.getString(localCursor.getColumnIndex("fromContactId"));
        localIMMessage.mFromGroupId = paramString;
        localIMMessage.mToUserId = null;
        localIMMessage.mMessage = localCursor.getString(localCursor.getColumnIndex("content"));
        localIMMessage.publish = localCursor.getLong(localCursor.getColumnIndex("timestamp"));
        localIMMessage.msgSeq = localCursor.getInt(localCursor.getColumnIndex("msgSeq"));
        localIMMessage.mFromAvatar = localCursor.getString(localCursor.getColumnIndex("avatar"));
        localIMMessage.mFromName = localCursor.getString(localCursor.getColumnIndex("name"));
        localArrayList.add(localIMMessage);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localCursor.close();
    return localArrayList;
  }

  public List<IMMessage> getPrivateConversation(String paramString1, String paramString2, int paramInt)
  {
    if ((paramString1 == null) || (paramString2 == null))
      return null;
    ArrayList localArrayList;
    Cursor localCursor;
    try
    {
      localArrayList = new ArrayList();
      localCursor = DBManager.getInstance().getReadableDB("imDatabase").rawQuery("SELECT * FROM userMessage WHERE (fromContactId = '" + paramString1 + "' AND toContactId = '" + paramString2 + "' )" + " OR (toContactId = '" + paramString1 + "' AND fromContactId = '" + paramString2 + "')" + " ORDER BY msgSeq DESC LIMIT " + paramInt, null);
      while (localCursor.moveToNext())
      {
        IMMessage localIMMessage = new IMMessage();
        localIMMessage.chatType = 0;
        localIMMessage.mFromID = localCursor.getString(localCursor.getColumnIndex("fromContactId"));
        localIMMessage.mToUserId = localCursor.getString(localCursor.getColumnIndex("toContactId"));
        localIMMessage.mMessage = localCursor.getString(localCursor.getColumnIndex("content"));
        localIMMessage.publish = localCursor.getLong(localCursor.getColumnIndex("timestamp"));
        localIMMessage.mFromAvatar = localCursor.getString(localCursor.getColumnIndex("avatar"));
        localIMMessage.mFromName = localCursor.getString(localCursor.getColumnIndex("name"));
        localIMMessage.msgSeq = localCursor.getInt(localCursor.getColumnIndex("msgSeq"));
        localArrayList.add(localIMMessage);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localCursor.close();
    return localArrayList;
  }

  public List<IMMessage> getPrivateConversationLessThan(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    if ((paramString1 == null) || (paramString2 == null))
      return null;
    ArrayList localArrayList;
    Cursor localCursor;
    try
    {
      localArrayList = new ArrayList();
      localCursor = DBManager.getInstance().getReadableDB("imDatabase").rawQuery("SELECT * FROM userMessage WHERE ( (fromContactId = '" + paramString1 + "' AND toContactId = '" + paramString2 + "' ) " + " OR ( toContactId = '" + paramString1 + "' AND fromContactId = '" + paramString2 + "' ) " + ") AND msgSeq < '" + paramInt2 + "' ORDER BY msgSeq DESC LIMIT " + paramInt1, null);
      while (localCursor.moveToNext())
      {
        IMMessage localIMMessage = new IMMessage();
        localIMMessage.chatType = 0;
        localIMMessage.mFromID = localCursor.getString(localCursor.getColumnIndex("fromContactId"));
        localIMMessage.mToUserId = localCursor.getString(localCursor.getColumnIndex("toContactId"));
        localIMMessage.mMessage = localCursor.getString(localCursor.getColumnIndex("content"));
        localIMMessage.publish = localCursor.getLong(localCursor.getColumnIndex("timestamp"));
        localIMMessage.msgSeq = localCursor.getInt(localCursor.getColumnIndex("msgSeq"));
        localIMMessage.mFromAvatar = localCursor.getString(localCursor.getColumnIndex("avatar"));
        localIMMessage.mFromName = localCursor.getString(localCursor.getColumnIndex("name"));
        localArrayList.add(localIMMessage);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localCursor.close();
    return localArrayList;
  }

  public IMContact getUserContact(String paramString)
  {
    if (paramString == null)
      return null;
    while (true)
    {
      try
      {
        Cursor localCursor = DBManager.getInstance().getReadableDB("imDatabase").rawQuery("SELECT * FROM userContact WHERE (id = '" + paramString + "')", null);
        if (localCursor.moveToNext())
        {
          localIMContact = restoreUserContact(localCursor);
          localCursor.close();
          return localIMContact;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      IMContact localIMContact = null;
    }
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return false;
  }

  public void removeGroupContacts()
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imDatabase");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("DELETE FROM groupContact");
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void removeGroupMessageOlderThan(long paramLong)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imDatabase");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("DELETE FROM groupMessage where timestamp < " + paramLong);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void removePrivateMessageOlderThan(long paramLong)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imDatabase");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("DELETE FROM userMessage where timestamp < " + paramLong);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void removeUserContacts(List<String> paramList)
  {
    if (paramList != null)
      try
      {
        if (paramList.size() == 0)
          return;
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imDatabase");
        localSQLiteDatabase.beginTransaction();
        String str = TextUtils.join(", ", paramList);
        localSQLiteDatabase.execSQL("DELETE FROM userContact WHERE id in ( '" + str + "' )");
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
  }

  public void updateGroupContact(IMContact paramIMContact)
  {
    if (paramIMContact == null)
      return;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imDatabase");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("DELETE FROM groupContact WHERE id = '" + paramIMContact.Id + "'");
      Object[] arrayOfObject = new Object[5];
      arrayOfObject[0] = paramIMContact.Id;
      arrayOfObject[1] = paramIMContact.Avatar;
      arrayOfObject[2] = paramIMContact.Gender;
      arrayOfObject[3] = paramIMContact.IsBlocked;
      arrayOfObject[4] = Integer.valueOf(paramIMContact.Level);
      localSQLiteDatabase.execSQL("INSERT INTO userContact(id, avatar, name, gender, isBlocked, level)VALUES(?,  ?,      ?,    ?,      ?,         ?)", arrayOfObject);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void updateUserContact(IMContact paramIMContact, String paramString)
  {
    if ((paramIMContact == null) || (paramString == null))
      return;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imDatabase");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("DELETE FROM userContact WHERE id = '" + paramIMContact.Id + "' AND myId = '" + paramString + "'");
      Object[] arrayOfObject = new Object[6];
      arrayOfObject[0] = paramIMContact.Id;
      arrayOfObject[1] = paramIMContact.Avatar;
      arrayOfObject[2] = paramIMContact.Gender;
      arrayOfObject[3] = paramIMContact.IsBlocked;
      arrayOfObject[4] = Integer.valueOf(paramIMContact.Level);
      arrayOfObject[5] = paramString;
      localSQLiteDatabase.execSQL("INSERT INTO userContact(id, avatar, name, gender, isBlocked, level, myId)VALUES(?,  ?,      ?,    ?,      ?,         ?,     ?)", arrayOfObject);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.IMDatabaseDS
 * JD-Core Version:    0.6.2
 */