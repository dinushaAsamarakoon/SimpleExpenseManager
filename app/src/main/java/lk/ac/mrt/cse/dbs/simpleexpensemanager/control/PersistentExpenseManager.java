package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import java.util.Date;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.AccountDAOImpl;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.TransactionsDAOImpl;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;


public class PersistentExpenseManager extends ExpenseManager {
    private final Context context;

    public PersistentExpenseManager(Context context) {
        this.context = context;
        setup();


    }

    /***
     * create objects of interface implementations and insert dummy data for once
     */
    @Override
    public void setup() {
        TransactionDAO transactionsDAO = new TransactionsDAOImpl(context);
        setTransactionsDAO(transactionsDAO);

        AccountDAO accountDAO = new AccountDAOImpl(context);
        setAccountsDAO(accountDAO);

        // dummy data
        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);

        Transaction transaction = new Transaction(new Date(), "12345A", ExpenseType.EXPENSE, 100.0);
        getTransactionsDAO().logTransaction(transaction.getDate(), transaction.getAccountNo(),
                transaction.getExpenseType(), transaction.getAmount());
    }
}
