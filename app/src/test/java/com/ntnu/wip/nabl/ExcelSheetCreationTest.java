package com.ntnu.wip.nabl;

import android.content.Context;

import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.ContactInformation;
import com.ntnu.wip.nabl.Models.TimeSheet;
import com.ntnu.wip.nabl.Models.User;
import com.ntnu.wip.nabl.Models.WorkDay;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by klingen on 26.04.18.
 * Tests considering the creation of excel-sheets
 */
@RunWith(MockitoJUnitRunner.class)
public class ExcelSheetCreationTest {

    @Mock
    private Context mockApplicationContext;

    @Test
    public void createExcelFile() {

        when (mockApplicationContext.getString(anyInt()))
                .thenReturn("Loooooong word");
        Calendar cal = new GregorianCalendar();
        Company company = new Company();
        company.setName("SuperFIrm");
        company.setOrgnr("skdjfnsdjkfsdn");
        User user = new User("Martin Klingenberg", new ContactInformation("mail@main.com", 123456789));
        List<WorkDay> dayList = new ArrayList<>();

        for (int i = 1; i < 20; i++) {
            DateTime time = new DateTime();
            time = time.dayOfMonth().setCopy(i);

            WorkDay day = new WorkDay(time.hourOfDay().setCopy(i).minuteOfHour().setCopy(0), time.hourOfDay().setCopy(23));
            dayList.add(day);
        }

        TimeSheet timeSheet = new TimeSheet(mockApplicationContext, company, user, dayList);

        try {
            timeSheet.write("/home/klingen/uniqueName.xlsx");
        } catch (IOException e) {
            // If exception... something is wrong
            Assert.assertFalse(true);
        }

    }

    @Test
    public void timeBetween() {
        DateTime time = new DateTime();
        time = time.hourOfDay().setCopy(20);
        time = time.minuteOfHour().setCopy(30);

        // Hours between whole hour format
        WorkDay day = new WorkDay(time.hourOfDay().addToCopy(5), time.hourOfDay().addToCopy(9));
        Assert.assertEquals(4.0, day.getTotalHours(), 0.01);

        // Hours when rounding to closest 30 mins
        day = new WorkDay(time.hourOfDay().setCopy(9).minuteOfHour().setCopy(0), time.hourOfDay().setCopy(17).minuteOfHour().setCopy(27));
        Assert.assertEquals(8.5, day.getTotalHours(), 0.01);

        // When starting one day and ending the other
        day = new WorkDay(time.hourOfDay().addToCopy(5).minusDays(1), time.hourOfDay().addToCopy(5));
        Assert.assertEquals(24, day.getTotalHours(), 0.01);


        // First begun half-hour
        day = new WorkDay(time.hourOfDay().setCopy(9).minuteOfHour().setCopy(0), time.hourOfDay().setCopy(17).minuteOfHour().setCopy(40));
        Assert.assertEquals(9, day.getTotalHours(), 0.01);

    }
}
