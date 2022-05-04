package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.sql.SQLException;

public abstract class TableManager extends SQLiteOpenHelper {
    private static final String dbName = "190544E_expenseManager.db";
    private static int version = 1;

    protected static final String tbName_1 = "transactions";
    protected static final String col_1_1 = "transactionId";
    protected static final String col_1_2 = "accountNo";
    protected static final String col_1_3 = "date";
    protected static final String col_1_4 = "expenseType";
    protected static final String col_1_5 = "amount";

    protected static final String tbName_2 = "accounts";
    protected static final String col_2_1 = "accountNo";
    protected static final String col_2_2 = "bankName";
    protected static final String col_2_3 = "accountHolderName";
    protected static final String col_2_4 = "balance";

    public TableManager(@Nullable Context context) {
        super(context, dbName, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String statement1 = "CREATE TABLE " + tbName_2 + " ("+
                    col_2_1+" TEXT PRIMARY KEY, "+
                    col_2_3+" TEXT, "+
                    col_2_2+" TEXT, "+
                    col_2_4+" REAL);";
            String statement2 = "CREATE TABLE " + tbName_1+ " ("+
                    col_1_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    col_1_3+" TEXT, "+
                    col_1_2+" TEXT, "+
                    col_1_5+" REAL, "+
                    col_1_4+" TEXT," +
                    " FOREIGN KEY("+col_1_2+") REFERENCES " + tbName_2 + "("+col_2_1+"));";
            sqLiteDatabase.execSQL(statement1);
            sqLiteDatabase.execSQL(statement2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tbName_1);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tbName_2);
            version = newV;
            onCreate(sqLiteDatabase);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
