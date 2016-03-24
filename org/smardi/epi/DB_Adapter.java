package org.smardi.epi;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DB_Adapter
{
  private static final String CREATETABLE_OWNERINFORMATION = "CREATE TABLE owner_information (uid INTEGER PRIMARY KEY, owner_id TEXT, owner_email TEXT);";
  private static final String CREATETABLE_POSITION = "CREATE TABLE scan_position (uid INTEGER PRIMARY KEY, position_name TEXT,position_id NUMERIC);";
  private static final String CREATETABLE_SCANRECORD = "CREATE TABLE scan_record (uid INTEGER PRIMARY KEY, uid_scan_position NUMERIC, uid_user_information NUMERIC, scan_value NUMERIC, scan_time NUMERIC, upload NUMERIC, location_x NUMERIC, location_y NUMERIC);";
  private static final String CREATETABLE_USERINFORMATION = "CREATE TABLE user_information (uid INTEGER PRIMARY KEY, uid_owner_information NUMERIC, user_name TEXT, user_gender TEXT, user_birthday NUMERIC);";
  private static final String DATABASE_NAME = "smardi_epi";
  private static final int DATABASE_VERSION = 1;
  public static final String KEY_BODY = "body";
  public static final String KEY_ROWID = "_id";
  public static final String KEY_TITLE = "title";
  private static final String TABLE_OWNERINFORMATION = "owner_information";
  private static final String TABLE_SCANPOSITION = "scan_position";
  private static final String TABLE_SCANRECORD = "scan_record";
  private static final String TABLE_USERINFORMATION = "user_information";
  private static final String TAG = "smardi_epi";
  private final Context mCtx;
  private SQLiteDatabase mDb;
  private DatabaseHelper mDbHelper;
  
  public DB_Adapter(Context paramContext)
  {
    this.mCtx = paramContext;
  }
  
  private long getTimestamp(String paramString)
  {
    try
    {
      long l = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(paramString).getTime();
      return l;
    }
    catch (ParseException paramString)
    {
      paramString.printStackTrace();
    }
    return 0L;
  }
  
  public long addOwner(String paramString1, String paramString2)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("owner_id", paramString1);
    localContentValues.put("owner_email", paramString2);
    return this.mDb.insert("owner_information", null, localContentValues);
  }
  
  public long addRecord(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("scan_value", Integer.valueOf(paramInt1));
    localContentValues.put("scan_time", Long.valueOf(new Date().getTime()));
    localContentValues.put("uid_scan_position", Integer.valueOf(paramInt3));
    localContentValues.put("uid_user_information", Integer.valueOf(paramInt2));
    localContentValues.put("upload", Integer.valueOf(0));
    localContentValues.put("location_x", Double.valueOf(paramDouble1));
    localContentValues.put("location_y", Double.valueOf(paramDouble2));
    return this.mDb.insert("scan_record", null, localContentValues);
  }
  
  public long addRecord(int paramInt1, long paramLong, int paramInt2, int paramInt3)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("scan_value", Integer.valueOf(paramInt1));
    localContentValues.put("scan_time", Long.valueOf(paramLong));
    localContentValues.put("uid_scan_position", Integer.valueOf(paramInt3));
    localContentValues.put("uid_user_information", Integer.valueOf(paramInt2));
    return this.mDb.insert("scan_record", null, localContentValues);
  }
  
  public long addUser(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("uid_owner_information", Integer.valueOf(paramInt));
    localContentValues.put("user_name", paramString1);
    localContentValues.put("user_gender", paramString2);
    localContentValues.put("user_birthday", paramString3);
    return this.mDb.insert("user_information", null, localContentValues);
  }
  
  public long changeUser(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("user_name", paramString1);
    localContentValues.put("user_gender", paramString2);
    localContentValues.put("user_birthday", paramString3);
    return this.mDb.update("user_information", localContentValues, "uid=" + paramInt, null);
  }
  
  public void close()
  {
    this.mDbHelper.close();
  }
  
  public boolean deleteRecord(long paramLong)
  {
    Log.i("Delete called", "value__" + paramLong);
    return this.mDb.delete("scan_record", "uid=" + paramLong, null) > 0;
  }
  
  public boolean deleteRecordOfUser(int paramInt)
  {
    Log.i("smardi_epi", "Delete records of User's uid(" + paramInt + ").");
    return this.mDb.delete("scan_record", "uid_scan_record", null) > 0;
  }
  
  public boolean deleteUser(int paramInt)
  {
    return this.mDb.delete("user_information", "uid=" + paramInt, null) > 0;
  }
  
  public Cursor fetchAllPoint()
  {
    return this.mDb.query("scan_position", new String[] { "uid", "position_name" }, null, null, null, null, null);
  }
  
  public Cursor fetchAllRecord()
  {
    return this.mDb.query("scan_record", new String[] { "uid", "uid_scan_position", "uid_user_information", "scan_value", "scan_time" }, null, null, null, null, null);
  }
  
  public Cursor fetchAllRecordByScanningPoint(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDb;
    paramString = "uid_scan_position=" + paramString;
    return localSQLiteDatabase.query("scan_record", new String[] { "uid", "uid_scan_position", "uid_user_information", "scan_value", "scan_time" }, paramString, null, null, null, null);
  }
  
  public Cursor fetchAllRecordByUser(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDb;
    paramString = "uid_user_information=" + paramString;
    return localSQLiteDatabase.query("scan_record", new String[] { "uid", "uid_scan_position", "uid_user_information", "scan_value", "scan_time" }, paramString, null, null, null, null);
  }
  
  public Cursor fetchAllRecordByUserAndScanningPoint(String paramString1, String paramString2)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDb;
    paramString1 = "uid_user_information=" + paramString1 + " AND uid_scan_position=" + paramString2;
    return localSQLiteDatabase.query("scan_record", new String[] { "uid", "uid_scan_position", "uid_user_information", "scan_value", "scan_time" }, paramString1, null, null, null, null);
  }
  
  public Cursor fetchAllRecordByUserAndScanningPointAtDay(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    long l1 = getTimestamp(paramInt3 + "-" + paramInt4 + "-" + paramInt5 + " 00:00:00");
    long l2 = getTimestamp(paramInt3 + "-" + paramInt4 + "-" + paramInt5 + " 23:59:59");
    SQLiteDatabase localSQLiteDatabase = this.mDb;
    String str = "uid_user_information=" + paramInt1 + " AND " + "uid_scan_position=" + paramInt2 + " AND " + "scan_time BETWEEN " + String.valueOf(l1) + " AND " + String.valueOf(l2);
    return localSQLiteDatabase.query("scan_record", new String[] { "uid", "scan_value", "scan_time" }, str, null, null, null, null);
  }
  
  public Cursor fetchAllRecordByUserAndScanningPointAtSomeMonth(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Object localObject = new GregorianCalendar(paramInt3, paramInt4 - 1, 1);
    paramInt3 = ((GregorianCalendar)localObject).getActualMaximum(5);
    long l1 = getTimestamp(new SimpleDateFormat("yyyy-MM-01").format(((GregorianCalendar)localObject).getTime()) + " 00:00:00");
    long l2 = getTimestamp(new StringBuilder(String.valueOf(new SimpleDateFormat("yyyy-MM-").format(((GregorianCalendar)localObject).getTime()))).append(paramInt3).toString() + " 00:00:00");
    localObject = this.mDb;
    String str = "uid_user_information=" + paramInt1 + " AND " + "uid_scan_position=" + paramInt2 + " AND " + "scan_time BETWEEN " + String.valueOf(l1) + " AND " + String.valueOf(l2);
    return ((SQLiteDatabase)localObject).query("scan_record", new String[] { "uid", "scan_value", "scan_time" }, str, null, null, null, null);
  }
  
  public Cursor fetchAllRecordByUserAndScanningPointFromSomedateToSomedate(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    long l1 = getTimestamp(paramString3 + " 00:00:00");
    long l2 = getTimestamp(paramString4 + " 23:59:59");
    paramString3 = this.mDb;
    paramString1 = "uid_user_information=" + paramString1 + " AND " + "uid_scan_position=" + paramString2 + " AND " + "scan_time BETWEEN " + String.valueOf(l1) + " AND " + String.valueOf(l2);
    return paramString3.query("scan_record", new String[] { "uid", "scan_value", "scan_time" }, paramString1, null, null, null, null);
  }
  
  public Cursor fetchAllRecordByUserAndScanningPointLast24hours(int paramInt1, int paramInt2)
  {
    long l = new Date().getTime();
    SQLiteDatabase localSQLiteDatabase = this.mDb;
    String str = "uid_user_information=" + paramInt1 + " AND " + "uid_scan_position=" + paramInt2 + " AND " + "scan_time BETWEEN " + String.valueOf(l - 86400000L) + " AND " + String.valueOf(l);
    return localSQLiteDatabase.query("scan_record", new String[] { "uid", "scan_value", "scan_time" }, str, null, null, null, null);
  }
  
  public Cursor fetchAllRecordByUserAndScanningPointLast30days(int paramInt1, int paramInt2)
  {
    long l1 = new Date().getTime();
    long l2 = getTimestamp(new SimpleDateFormat("yyyy-MM-dd").format(Long.valueOf(new Date(l1 - 2592000000L).getTime())) + " 00:00:00");
    SQLiteDatabase localSQLiteDatabase = this.mDb;
    String str = "uid_user_information=" + paramInt1 + " AND " + "uid_scan_position=" + paramInt2 + " AND " + "scan_time BETWEEN " + String.valueOf(l2) + " AND " + String.valueOf(l1);
    return localSQLiteDatabase.query("scan_record", new String[] { "uid", "scan_value", "scan_time" }, str, null, null, null, null);
  }
  
  public Cursor fetchAllRecordNotUploaded()
  {
    return this.mDb.query("scan_record", new String[] { "uid", "uid_scan_position", "uid_user_information", "scan_value", "scan_time", "upload", "location_x", "location_y" }, "upload='0'", null, null, null, null);
  }
  
  public Cursor fetchUser(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDb;
    String str = "uid=" + paramInt;
    return localSQLiteDatabase.query("user_information", new String[] { "uid", "user_name", "user_gender", "user_birthday" }, str, null, null, null, null);
  }
  
  public int getMaxOwnerUid()
  {
    int i = 0;
    Cursor localCursor = this.mDb.rawQuery("SELECT max(`uid`) FROM owner_information", null);
    if (localCursor.moveToNext()) {
      i = localCursor.getInt(0);
    }
    return i;
  }
  
  public int getOwnerUid(String paramString)
  {
    int i = 0;
    try
    {
      paramString = this.mDb.rawQuery("SELECT `uid` FROM owner_information WHERE `owner_id`='" + paramString + "'", null);
      if (paramString.moveToNext()) {
        i = paramString.getInt(0);
      }
      return i;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return 0;
  }
  
  public int getScanPositionID(long paramLong)
    throws SQLException
  {
    Object localObject = this.mDb;
    String str = "uid=" + paramLong;
    localObject = ((SQLiteDatabase)localObject).query(true, "scan_position", new String[] { "position_id" }, str, null, null, null, null, null);
    if (localObject != null) {
      ((Cursor)localObject).moveToFirst();
    }
    return ((Cursor)localObject).getInt(0);
  }
  
  public String getScanPositionName(long paramLong)
    throws SQLException
  {
    Object localObject = this.mDb;
    String str = "uid=" + paramLong;
    localObject = ((SQLiteDatabase)localObject).query(true, "scan_position", new String[] { "position_name" }, str, null, null, null, null, null);
    if (localObject != null) {
      ((Cursor)localObject).moveToFirst();
    }
    return ((Cursor)localObject).getString(0);
  }
  
  public Cursor getUserList(int paramInt)
    throws SQLException
  {
    SQLiteDatabase localSQLiteDatabase = this.mDb;
    String str = "uid_owner_information=" + paramInt;
    return localSQLiteDatabase.query(true, "user_information", new String[] { "user_name", "user_gender", "user_birthday", "uid" }, str, null, null, null, null, null);
  }
  
  public boolean onCreate()
  {
    this.mDbHelper = new DatabaseHelper(this.mCtx);
    return this.mDbHelper != null;
  }
  
  public DB_Adapter open()
    throws SQLException
  {
    this.mDbHelper = new DatabaseHelper(this.mCtx);
    this.mDb = this.mDbHelper.getWritableDatabase();
    return this;
  }
  
  public void updateResultDataUploading(int paramInt)
  {
    String str = "UPDATE scan_record SET `upload`='1' WHERE `uid`='" + paramInt + "';";
    this.mDb.execSQL(str);
  }
  
  private static class DatabaseHelper
    extends SQLiteOpenHelper
  {
    private Context context;
    
    DatabaseHelper(Context paramContext)
    {
      super("smardi_epi", null, 1);
      this.context = paramContext;
    }
    
    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      String str1 = this.context.getResources().getString(2131230726);
      String str2 = this.context.getResources().getString(2131230727);
      String str3 = this.context.getResources().getString(2131230728);
      String str4 = this.context.getResources().getString(2131230729);
      paramSQLiteDatabase.execSQL("CREATE TABLE owner_information (uid INTEGER PRIMARY KEY, owner_id TEXT, owner_email TEXT);");
      paramSQLiteDatabase.execSQL("CREATE TABLE user_information (uid INTEGER PRIMARY KEY, uid_owner_information NUMERIC, user_name TEXT, user_gender TEXT, user_birthday NUMERIC);");
      paramSQLiteDatabase.execSQL("CREATE TABLE scan_record (uid INTEGER PRIMARY KEY, uid_scan_position NUMERIC, uid_user_information NUMERIC, scan_value NUMERIC, scan_time NUMERIC, upload NUMERIC, location_x NUMERIC, location_y NUMERIC);");
      paramSQLiteDatabase.execSQL("CREATE TABLE scan_position (uid INTEGER PRIMARY KEY, position_name TEXT,position_id NUMERIC);");
      paramSQLiteDatabase.execSQL("INSERT INTO scan_position (position_name, position_id) VALUES ('" + str1 + "', 0);");
      paramSQLiteDatabase.execSQL("INSERT INTO scan_position (position_name, position_id) VALUES ('" + str3 + "', 1);");
      paramSQLiteDatabase.execSQL("INSERT INTO scan_position (position_name, position_id) VALUES ('" + str2 + "', 2);");
      paramSQLiteDatabase.execSQL("INSERT INTO scan_position (position_name, position_id) VALUES ('" + str4 + "', 3);");
    }
    
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      Log.w("smardi_epi", "Upgrading database from version " + paramInt1 + " to " + paramInt2 + ", which will destroy all old data");
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS owner_information");
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS user_information");
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS scan_position");
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS scan_record");
      onCreate(paramSQLiteDatabase);
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\DB_Adapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */