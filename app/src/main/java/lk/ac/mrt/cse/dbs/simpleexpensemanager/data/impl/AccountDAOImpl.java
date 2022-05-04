package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class AccountDAOImpl extends TableManager implements AccountDAO {

    public AccountDAOImpl(Context context) {
        super(context);
    }

    /***
     *
     * @return List of Account numbers
     */
    @Override
    public List<String> getAccountNumbersList() {
        String query = "SELECT accountNo FROM " + tbName_2;

        List<String> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery(query, null);

        if (res.moveToFirst()) {
            do {
                list.add(res.getString(res.getColumnIndex(col_2_1)));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return list;
    }

    /***
     *
     * @return list of accounts
     */
    @Override
    public List<Account> getAccountsList() {
        String query = "SELECT * FROM " + tbName_2;

        List<Account> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                list.add(new Account(res.getString(res.getColumnIndex(col_2_1)),
                        res.getString(res.getColumnIndex(col_2_2)),
                        res.getString(res.getColumnIndex(col_2_3)),
                        res.getDouble(res.getColumnIndex(col_2_4))));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return list;
    }

    /***
     *
     * @param accountNo as String
     * @return Account related to account number
     */
    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        String query = "SELECT * FROM " + tbName_2 + " WHERE accountNo = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery(query, new String[]{accountNo});
        if (res.moveToFirst()) {
            Account account = new Account(res.getString(res.getColumnIndex(col_2_1)),
                    res.getString(res.getColumnIndex(col_2_2)),
                    res.getString(res.getColumnIndex(col_2_3)),
                    res.getDouble(res.getColumnIndex(col_2_4)));
            res.close();
            db.close();
            return account;

        } else {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
    }

    /***
     *
     * @param account - the account to be added.
     */
    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2_1, account.getAccountNo());
        contentValues.put(col_2_2, account.getBankName());
        contentValues.put(col_2_3, account.getAccountHolderName());
        contentValues.put(col_2_4, account.getBalance());

        db.insert(tbName_2, null, contentValues);
        db.close();
    }


    /***
     *
     * @param accountNo - of the account to be removed.
     */
    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete(tbName_2, col_2_1 + " = ? ", new String[]{col_2_1}) == 0) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        db.close();
    }

    /***
     *
     * @param accountNo   - account number of the respective account
     * @param expenseType - the type of the transaction
     * @param amount      - amount involved
     */
    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account account = getAccount(accountNo);
        if (account != null) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_2_1, accountNo);
            contentValues.put(col_2_2, account.getBankName());
            contentValues.put(col_2_3, account.getAccountHolderName());


            switch (expenseType) {
                case EXPENSE:
                    contentValues.put(col_2_4, account.getBalance() - amount);
                    break;
                case INCOME:
                    contentValues.put(col_2_4, account.getBalance() + amount);
                    break;
            }
            db.update(tbName_2, contentValues, col_2_1 + " = ? ", new String[]{col_2_1});
            db.close();

        } else {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
    }


}
