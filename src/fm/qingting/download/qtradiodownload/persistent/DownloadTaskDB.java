package fm.qingting.download.qtradiodownload.persistent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fm.qingting.download.qtradiodownload.network.filedownload.model.DownloadTask;
import java.util.ArrayList;

public class DownloadTaskDB
{
  private static final String TAG = "DownloadTaskDB";
  private static DownloadTaskDB instance = null;
  private FileDownloadHelper helper = null;

  private DownloadTaskDB(Context paramContext)
  {
    this.helper = FileDownloadHelper.getInstance(paramContext.getApplicationContext());
  }

  public static void createInstance(Context paramContext)
  {
    try
    {
      if (instance == null)
        instance = new DownloadTaskDB(paramContext);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static DownloadTaskDB getInstance()
  {
    return instance;
  }

  public void deleteDownloadTask(DownloadTask paramDownloadTask)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramDownloadTask.getUUId();
      localSQLiteDatabase.delete("downloadtask", "uuid=?", arrayOfString);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void deleteDownloadTask(String paramString)
  {
    try
    {
      this.helper.getWritableDatabase().delete("downloadtask", "uuid=?", new String[] { paramString });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public long insertDownloadTask(DownloadTask paramDownloadTask)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("uuid", paramDownloadTask.getUUId());
      localContentValues.put("url", paramDownloadTask.getResourceUrl());
      localContentValues.put("filename", paramDownloadTask.getFileName());
      localContentValues.put("directory", paramDownloadTask.getFileDirectory());
      localContentValues.put("isfinished", "false");
      localContentValues.put("size", Integer.valueOf(paramDownloadTask.getSize()));
      long l = localSQLiteDatabase.insert("downloadtask", null, localContentValues);
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public ArrayList<DownloadTask> queryAllDownloadTask()
  {
    Cursor localCursor;
    ArrayList localArrayList;
    try
    {
      localCursor = this.helper.getReadableDatabase().query("downloadtask", null, null, null, null, null, null);
      localArrayList = new ArrayList();
      while (localCursor.moveToNext())
      {
        String str1 = localCursor.getString(localCursor.getColumnIndex("uuid"));
        String str2 = localCursor.getString(localCursor.getColumnIndex("url"));
        String str3 = localCursor.getString(localCursor.getColumnIndex("directory"));
        String str4 = localCursor.getString(localCursor.getColumnIndex("filename"));
        int i = localCursor.getInt(localCursor.getColumnIndex("size"));
        boolean bool = Boolean.parseBoolean(localCursor.getString(localCursor.getColumnIndex("isfinished")));
        DownloadTask localDownloadTask = new DownloadTask(str2, str3, str4, 0);
        localDownloadTask.setUuid(str1);
        localDownloadTask.setSize(i);
        localDownloadTask.setTaskFinished(bool);
        if (!bool)
        {
          int j = localCursor.getInt(localCursor.getColumnIndex("curlength"));
          int k = localCursor.getInt(localCursor.getColumnIndex("threadsize"));
          String str5 = localCursor.getString(localCursor.getColumnIndex("parts"));
          localDownloadTask.setPreLength(j);
          localDownloadTask.setThreadSize(k);
          if ((str5 != null) && (!str5.equals("")))
          {
            PartDataTool.parsePartsData(localDownloadTask, str5);
            localDownloadTask.setState(3);
          }
        }
        localArrayList.add(localDownloadTask);
      }
    }
    finally
    {
    }
    localCursor.close();
    return localArrayList;
  }

  public void updateDownloadTask(DownloadTask paramDownloadTask)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("isfinished", "" + paramDownloadTask.isTaskFinished());
      localContentValues.put("size", Integer.valueOf(paramDownloadTask.getSize()));
      localContentValues.put("curlength", Integer.valueOf(paramDownloadTask.getCurLength()));
      localContentValues.put("threadsize", Integer.valueOf(paramDownloadTask.getThreadSize()));
      localContentValues.put("parts", PartDataTool.buildPartsData(paramDownloadTask.getParts()));
      String[] arrayOfString = new String[1];
      arrayOfString[0] = (paramDownloadTask.getUUId() + "");
      localSQLiteDatabase.update("downloadtask", localContentValues, "uuid = ? ", arrayOfString);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.persistent.DownloadTaskDB
 * JD-Core Version:    0.6.2
 */