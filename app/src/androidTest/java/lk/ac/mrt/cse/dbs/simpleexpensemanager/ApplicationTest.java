/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest{
    private static ExpenseManager exManager;


    @Before
    public void setup(){
        Context context = ApplicationProvider.getApplicationContext();
        exManager = new PersistentExpenseManager(context);
    }

    @Test
    public void testInsertAccount(){
        exManager.addAccount("0085806687","Commercial bank","Samarakoon S.M.D.A.",7500.0);
        List<String> storedAccountNumbers = exManager.getAccountNumbersList();
        assertTrue(storedAccountNumbers.contains("0085806687"));
    }

    @Test
    public void testLogTransaction() throws InvalidAccountException {
        int rowId_pass = exManager.updateAccountBalance("0085806687",12,5,2022, ExpenseType.EXPENSE,"3500.0");
        assertTrue(rowId_pass!=-1);

//        int rowId_fail = exManager.updateAccountBalance("008580668",12,5,2022, ExpenseType.EXPENSE,"3500.0");
//        assertTrue(rowId_fail!=-1);

    }

}