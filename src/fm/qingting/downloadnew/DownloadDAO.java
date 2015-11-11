package fm.qingting.downloadnew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class DownloadDAO
{
  public static boolean deleteTask(Context paramContext, String paramString)
  {
    return DownloadDBHelper.getInstance(paramContext).getWritableDatabase().delete("download_task", "task_id=?", new String[] { paramString }) > 0;
  }

  public static List<DownloadTask> getAllTasks(Context paramContext)
  {
    Cursor localCursor = DownloadDBHelper.getInstance(paramContext).getReadableDatabase().query("download_task", null, null, null, null, null, "_id ASC");
    ArrayList localArrayList = null;
    if (localCursor != null)
    {
      localArrayList = new ArrayList();
      while (localCursor.moveToNext())
      {
        DownloadTask localDownloadTask = new DownloadTask();
        localDownloadTask.mId = localCursor.getInt(localCursor.getColumnIndex("_id"));
        localDownloadTask.mTaskId = localCursor.getString(localCursor.getColumnIndex("task_id"));
        localDownloadTask.mUrls = localCursor.getString(localCursor.getColumnIndex("url")).split(";");
        localDownloadTask.mFileName = localCursor.getString(localCursor.getColumnIndex("file"));
        localDownloadTask.mState = DownloadState.valueOf(localCursor.getInt(localCursor.getColumnIndex("state")));
        localDownloadTask.mCurSize = localCursor.getLong(localCursor.getColumnIndex("download_size"));
        localDownloadTask.mTotalSize = localCursor.getLong(localCursor.getColumnIndex("total_size"));
        localDownloadTask.mFinishTimeMS = localCursor.getLong(localCursor.getColumnIndex("finish_time"));
        localDownloadTask.mExtra = localCursor.getString(localCursor.getColumnIndex("extra_info"));
        localDownloadTask.mRetryPolicy = new DefaultRetryPolicy(localDownloadTask.mUrls);
        localArrayList.add(localDownloadTask);
      }
      localCursor.close();
    }
    return localArrayList;
  }

  public static boolean insertNewTask(Context paramContext, DownloadTask paramDownloadTask)
  {
    try
    {
      long l = insertOneTask(paramDownloadTask, DownloadDBHelper.getInstance(paramContext).getWritableDatabase());
      if (l == -1L)
      {
        bool = false;
        return bool;
      }
      boolean bool = true;
    }
    finally
    {
    }
  }

  public static void insertNewTaskInBatch(Context paramContext, List<DownloadTask> paramList)
  {
    SQLiteDatabase localSQLiteDatabase = DownloadDBHelper.getInstance(paramContext).getWritableDatabase();
    try
    {
      localSQLiteDatabase.beginTransaction();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
        insertOneTask((DownloadTask)localIterator.next(), localSQLiteDatabase);
    }
    catch (SQLException localSQLException)
    {
      return;
      localSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static long insertOneTask(DownloadTask paramDownloadTask, SQLiteDatabase paramSQLiteDatabase)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("task_id", paramDownloadTask.mTaskId);
    localContentValues.put("url", toUrlString(paramDownloadTask.mUrls));
    localContentValues.put("file", DownloadUtils.notNull(paramDownloadTask.mFileName));
    localContentValues.put("state", Integer.valueOf(paramDownloadTask.mState.ordinal()));
    localContentValues.put("finish_time", Long.valueOf(paramDownloadTask.mFinishTimeMS));
    localContentValues.put("download_size", Long.valueOf(paramDownloadTask.mCurSize));
    localContentValues.put("total_size", Long.valueOf(paramDownloadTask.mTotalSize));
    localContentValues.put("extra_info", DownloadUtils.notNull(paramDownloadTask.mExtra));
    return paramSQLiteDatabase.insert("download_task", null, localContentValues);
  }

  private static String toUrlString(String[] paramArrayOfString)
  {
    StringBuilder localStringBuilder = new StringBuilder("");
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
      return localStringBuilder.toString();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
      localStringBuilder.append(paramArrayOfString[j]).append(";");
    localStringBuilder.setLength(-1 + localStringBuilder.length());
    return localStringBuilder.toString();
  }

  public static void updateTask(Context paramContext, DownloadTask paramDownloadTask)
  {
    SQLiteDatabase localSQLiteDatabase = DownloadDBHelper.getInstance(paramContext).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("download_size", Long.valueOf(paramDownloadTask.mCurSize));
    localContentValues.put("total_size", Long.valueOf(paramDownloadTask.mTotalSize));
    localContentValues.put("state", Integer.valueOf(paramDownloadTask.mState.ordinal()));
    localContentValues.put("finish_time", Long.valueOf(paramDownloadTask.mFinishTimeMS));
    localContentValues.put("extra_info", paramDownloadTask.mExtra);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramDownloadTask.mTaskId;
    localSQLiteDatabase.update("download_task", localContentValues, "task_id=?", arrayOfString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.downloadnew.DownloadDAO
 * JD-Core Version:    0.6.2
 */