package fm.qingting.download.qtradiodownload.persistent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FileDownloadHelper extends SQLiteOpenHelper
{
  private static final String DATABASE_NAME = "downloadtask.db";
  private static final int DATABASE_VERSION = 1;
  public static final String TABLE_DOWNLOAD_TASK = "downloadtask";
  private static final String TAG = "FileDownloadHelper";
  private static FileDownloadHelper helper;

  private FileDownloadHelper(Context paramContext)
  {
    super(paramContext, "downloadtask.db", null, 1);
  }

  public static FileDownloadHelper getInstance(Context paramContext)
  {
    try
    {
      if (helper == null)
        helper = new FileDownloadHelper(paramContext.getApplicationContext());
      FileDownloadHelper localFileDownloadHelper = helper;
      return localFileDownloadHelper;
    }
    finally
    {
    }
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("create table downloadtask (_id integer primary key, uuid TEXT, url TEXT, directory TEXT, filename TEXT, isfinished TEXT, size integer, curlength integer, threadsize integer, parts TEXT )");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("drop table if exists downloadtask");
    onCreate(paramSQLiteDatabase);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.persistent.FileDownloadHelper
 * JD-Core Version:    0.6.2
 */