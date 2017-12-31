package com.firstproject.amit.bitcoinportfolio.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION_NO = 1;
    private static final String DATABASE_NAME = "BitCoinPortFolioApp";

    static final String TABLE_NAME_INVESTMENTS_DETAILS = "INVESTMENTS_DETAILS";
    public static String COLUMN_INVESTMENT_ID = "INVESTMENT_ID";
    public static String COLUMN_INVESTMENT_TIMESTAMP = "INVESTMENT_TIMESTAMP";
    public static String COLUMN_INVESTMENT_IS_BUY = "INVESTMENT_IS_BUY";
    public static String COLUMN_INVESTMENT_AMOUNT = "INVESTMENT_AMOUNT";
    public static String COLUMN_INVESTMENT_RATE = "INVESTMENT_RATE";
    public static String COLUMN_INVESTMENT_TOTAL_PRICE = "INVESTMENT_TOTAL_PRICE";

    private static final String CREATE_TABLE_INVESTMENT = "create table " + TABLE_NAME_INVESTMENTS_DETAILS + "  ("
            + COLUMN_INVESTMENT_ID + " integer primary key autoincrement, "
            + COLUMN_INVESTMENT_TIMESTAMP + " text,"
            + COLUMN_INVESTMENT_AMOUNT + " integer,"
            + COLUMN_INVESTMENT_IS_BUY + " integer,"
            + COLUMN_INVESTMENT_RATE + " integer,"
            + COLUMN_INVESTMENT_TOTAL_PRICE + " integer )";

    Context context;

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        createTables(database);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        UserPreference.getInstance(context).setStringField(UserPreference.FIELD_TIME_STAMP_MESSAGE, "");
//        UserPreference.getInstance(context).setBooleanField(UserPreference.FIELD_IS_FIRST_TIME, true);

        try {
            db.execSQL("DROP TABLE IF EXISTS " + DbOpenHelper.TABLE_NAME_INVESTMENTS_DETAILS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        createTables(db);
    }

    public void createTables(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_INVESTMENT);
    }
}
