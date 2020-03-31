package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.usb.UsbRequest;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

   public static final String DATABASE_NAME="UserInfo.db";
   public DBHandler(Context context) {super(context, DATABASE_NAME, null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UsersMaster.Users.TABLE_NAME+"("+
                        UsersMaster.Users.COLUMN_NAME_USERNAME+"TEXT," +
                        UsersMaster.Users.COLUMN_NAME_PASSWORD+"TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addInfo(String userName, String password){
       SQLiteDatabase db = getWritableDatabase();

        ContentValues values=new ContentValues();
       ///////////// UserMaster was added additionaly
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME,userName);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        long newRowId = db.insert(UsersMaster.Users.TABLE_NAME,null,values);

    }

    public List readAllInfo(){
       SQLiteDatabase db = getReadableDatabase();
       String[] projection = {
               UsersMaster.Users._ID,
               UsersMaster.Users.COLUMN_NAME_USERNAME,
               UsersMaster.Users.COLUMN_NAME_PASSWORD
       };
        String setOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + "Desc";

        Cursor cursor =db.query(UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                setOrder);
        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while(cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            passwords.add(password);
        }
        cursor.close();
        return userNames;
    }


    public void deleteInfo(String userName){
        SQLiteDatabase db =getReadableDatabase();
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = { userName };
        db.delete(UsersMaster.Users.TABLE_NAME, selection, selectionArgs);
    }

    public void updateInfo(String userName, String password){
       SQLiteDatabase db=getReadableDatabase();
       ContentValues values = new ContentValues();
       values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);
       String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
       String[] selectionArgs = { userName };

       int count = db.update(
               UsersMaster.Users.TABLE_NAME,
               values,
               selection,
               selectionArgs
       );
    }
}
