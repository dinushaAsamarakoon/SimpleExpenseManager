package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class TransactionsDAOImpl extends TableManager implements TransactionDAO {


    public TransactionsDAOImpl(Context context) {
        super(context);
    }

    /***
     *  @param date        - date of the transaction
     * @param accountNo   - account number involved
     * @param expenseType - type of the expense
     * @param amount      - amount involved
     * @return
     */
    @Override
    public int logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_1_2, accountNo);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            contentValues.put(col_1_3, dateFormat.format(date));
            contentValues.put(col_1_4, expenseType.toString());
            contentValues.put(col_1_5, amount);

            int rowId = (int) db.insert(tbName_1, null, contentValues);
            db.close();
            return rowId;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /***
     *
     * @return list of all transactions
     */
    @Override
    public List<Transaction> getAllTransactionLogs() {
        String query = "SELECT * FROM " + tbName_1;

        List<Transaction> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {

            do {
                String fDate = res.getString(1);

                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = dateFormat.parse(fDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                list.add(new Transaction(date, res.getString(res.getColumnIndex(col_1_2)),
                        ExpenseType.valueOf(res.getString(res.getColumnIndex(col_1_4))),
                        res.getDouble(res.getColumnIndex(col_1_5))));

            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return list;
    }

    /***
     *
     * @param limit - number of transactions to be returned
     * @return limited transactions selected
     */
    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactions = getAllTransactionLogs();
        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        return transactions.subList(size - limit, size);
    }


}
