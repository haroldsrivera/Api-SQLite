package model;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class ManagerDB extends SQLiteOpenHelper {

    private static final String DATA_BASE_USERS="dbusuarios";
    private static final int VERSION =1;
    private static final String TABLE_USERS="users";

    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_USERS+" (" +
            "usu_document INTEGER PRIMARY KEY," +
            "usu_user varchar(35) NOT NULL," +
            "usu_names varchar(100) NOT NULL," +
            "usu_last_names varchar(100) NOT NULL," +
            "usu_pass varchar(20) NOT NULL," +
            "usu_status int(1) NOT NULL);";


    private static final String DELTE_TABLE="DROP TABLE IF EXISTS "+TABLE_USERS;

    public ManagerDB(Context context) {
        super(context, DATA_BASE_USERS,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlLiteDataBase) {
        sqlLiteDataBase.execSQL(CREATE_TABLE);
        Log.i("DATABASE","Se creó la tabla:"+CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlLiteDataBase, int i, int i1) {
        sqlLiteDataBase.execSQL(DELTE_TABLE);
        onCreate(sqlLiteDataBase);
    }
}

