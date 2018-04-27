package com.ntnu.wip.nabl;

import android.content.Context;
import android.test.mock.MockContext;

import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.TimeSheet;
import com.ntnu.wip.nabl.Models.User;
import com.ntnu.wip.nabl.Models.WorkDay;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by klingen on 26.04.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExcelSheetCreationTest {

    @Mock
    private Context mockApplicationContext;

    @Test
    public void createExcelFile() {

        when (mockApplicationContext.getString(anyInt()))
                .thenReturn("Loooooong word");

        Company company = new Company();
        User user = new User();
        List<WorkDay> dayList = new ArrayList<>();

        TimeSheet timeSheet = new TimeSheet(mockApplicationContext, company, user, dayList);

        try {
            timeSheet.write("/home/klingen/uniqueName.xlxs");
        } catch (IOException e) {
            // If exception... something is wrong
            Assert.assertFalse(true);
        }
    }

}
