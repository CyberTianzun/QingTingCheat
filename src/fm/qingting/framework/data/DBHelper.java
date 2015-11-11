package fm.qingting.framework.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DBHelper extends SQLiteOpenHelper
{
  protected Context context;
  private IDBHelperDelegate delegate;
  protected SQLiteDatabase.CursorFactory factory;
  Map<String, String> indexes;
  protected String name;
  Map<String, Map<String, String>> tables;
  protected int version;

  public DBHelper(Map<String, Map<String, String>> paramMap, Map<String, String> paramMap1, Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt, IDBHelperDelegate paramIDBHelperDelegate)
  {
    super(paramContext, paramString, paramCursorFactory, paramInt);
    this.delegate = paramIDBHelperDelegate;
    this.context = paramContext;
    this.name = paramString;
    this.factory = paramCursorFactory;
    this.version = paramInt;
    this.tables = paramMap;
  }

  private void createDatabase(SQLiteDatabase paramSQLiteDatabase)
  {
    Iterator localIterator2;
    Iterator localIterator1;
    if (this.tables != null)
    {
      localIterator2 = this.tables.keySet().iterator();
      if (localIterator2.hasNext());
    }
    else if (this.indexes != null)
    {
      localIterator1 = this.indexes.keySet().iterator();
    }
    while (true)
    {
      if (!localIterator1.hasNext())
      {
        return;
        String str2 = (String)localIterator2.next();
        paramSQLiteDatabase.execSQL(createTableSQL(str2, (Map)this.tables.get(str2)));
        break;
      }
      String str1 = (String)localIterator1.next();
      paramSQLiteDatabase.execSQL(createIndexSQL(str1, (String)this.indexes.get(str1)));
    }
  }

  private String createIndexSQL(String paramString1, String paramString2)
  {
    return "create index " + paramString1 + " on " + paramString2;
  }

  private String createTableSQL(String paramString, Map<String, String> paramMap)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CREATE TABLE IF NOT EXISTS " + paramString + "(");
    Iterator localIterator = paramMap.keySet().iterator();
    for (int i = 1; ; i = 0)
    {
      if (!localIterator.hasNext())
      {
        localStringBuilder.append(")");
        return localStringBuilder.toString();
      }
      if (i == 0)
        localStringBuilder.append(", ");
      String str = (String)localIterator.next();
      localStringBuilder.append(str);
      localStringBuilder.append(" ");
      localStringBuilder.append((String)paramMap.get(str));
    }
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    if ((this.delegate == null) || (!this.delegate.onCreate(paramSQLiteDatabase, this.name)))
      createDatabase(paramSQLiteDatabase);
    if (this.delegate != null)
      this.delegate.onCreateComplete(paramSQLiteDatabase, this.name);
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    if ((this.delegate == null) || (!this.delegate.onUpgrade(paramSQLiteDatabase, this.name, paramInt1, paramInt2)))
      updateDatabase(paramSQLiteDatabase, paramInt1, paramInt2);
    if (this.delegate != null)
      this.delegate.onUpgradeComplete(paramSQLiteDatabase, this.name, paramInt1, paramInt2);
  }

  public void setDelegate(IDBHelperDelegate paramIDBHelperDelegate)
  {
    this.delegate = paramIDBHelperDelegate;
  }

  protected void updateDatabase(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.tables.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        createDatabase(paramSQLiteDatabase);
        return;
      }
      String str = (String)localIterator.next();
      paramSQLiteDatabase.execSQL("drop table if exists " + str);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.data.DBHelper
 * JD-Core Version:    0.6.2
 */