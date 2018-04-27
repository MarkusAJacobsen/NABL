package com.ntnu.wip.nabl.Models;

import android.content.Context;

import com.ntnu.wip.nabl.R;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by klingen on 26.04.18.
 * This class will explain the basics of a timesheet
 * Data in this class should be enough to create a complete timesheet.
 */

public class TimeSheet {
    private Company company;            // A timesheet is for a company
    private Client client;              // or a client
    private List<WorkDay> workDays;     // Some days work
    private User user;                  // The one getting payed
    private Context context;            // Context for strings

    // XLXS-Sheet related variables
    private Workbook workbook;          // The document with possibility of several sheets
    private Sheet sheet;                // The actual excelSheet
    private CreationHelper creationHelper;  // Helps with various formatting


    // CONSTANTS
    private int HEADER_ROW_NUMBER = 5;
    private int FIRST_HEADER_COLUMN = 3;

    /**
     * Create a timesheet for a company
     * @param company to be paying
     * @param user getting payed
     * @param workDays the days he has worked
     */
    public TimeSheet(Context context, Company company, User user, List<WorkDay> workDays) {
        this.context = context;
        this.user = user;
        this.company = company;
        this.workDays = workDays;
        inizializeTimeSheet();
    }

    /**
     * Create a timesheet for a client
     * @param client - client getting payed
     * @param user - getting payed
     * @param workDays - workdays
     */
    public TimeSheet(Context context, Client client, User user, List<WorkDay> workDays) {
        this.context = context;
        this.client = client;
        this.user = user;
        this.workDays = workDays;
        inizializeTimeSheet();
    }

    private void inizializeTimeSheet() {
        workbook = new XSSFWorkbook();
        creationHelper = workbook.getCreationHelper();
        sheet = workbook.createSheet(this.extractPeriod());

    }


    /**
     * SHOULD NOT BE USED
     */
    private TimeSheet() {}


    /**
     * Create a header row with nice titles. Sometimes translated into norwegian
     */
    private List<String> createHeaderList() {
        List<String> headerNames = new ArrayList<>();
        headerNames.add(context.getString(R.string.headerDate));
        headerNames.add(context.getString(R.string.headerStart));
        headerNames.add(context.getString(R.string.headerEnd));
        headerNames.add(context.getString(R.string.headerHours));
        headerNames.add(context.getString(R.string.headerBreak));
        headerNames.add(context.getString(R.string.headerOverTime));
        headerNames.add(context.getString(R.string.headerTotal));
        return headerNames;
    }

    /**
     * Create the headers for the timeRows
     * @return row with nicely formatted headers
     */
    private Row workDayHeaderRow() {
        Row headerRow = sheet.createRow(HEADER_ROW_NUMBER);
        List<String> cellHeaders = createHeaderList();

        CellStyle cellStyle = headerCellStyle();

        for (int i = FIRST_HEADER_COLUMN; i < FIRST_HEADER_COLUMN + cellHeaders.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(cellHeaders.get(i-FIRST_HEADER_COLUMN));
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(i);
            sheet.getRow(HEADER_ROW_NUMBER).setHeight((short) 500);
        }


        return headerRow;
    }



    // ############################### CELL-STYLES ###############################

    /**
     * Style with bold larger font and border on all sides
     * @return cellstyle as described
     */
    private CellStyle headerCellStyle() {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Add border
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return headerCellStyle;
    }

    /**
     * Style with thin border normal text size
     * @return cellStyle as described
     */
    private CellStyle basicCellType() {
        Font basicFont = workbook.createFont();
        basicFont.setFontHeightInPoints((short) 12);

        CellStyle basicCellStyle = workbook.createCellStyle();
        basicCellStyle.setFont(basicFont);

        // Add border
        basicCellStyle.setBorderBottom(BorderStyle.THIN);
        basicCellStyle.setBorderTop(BorderStyle.THIN);
        basicCellStyle.setBorderRight(BorderStyle.THIN);
        basicCellStyle.setBorderLeft(BorderStyle.THIN);

        return basicCellStyle;
    }

    /**
     * Basic cellstyle with date formatting.
     * We use non-USA date formatting. While we do not like imperial nonsense!
     * @return cell-style for date fields
     */
    private CellStyle dateCellStyle() {
        CellStyle cellStyle = basicCellType();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        return cellStyle;
    }

    private CellStyle timeCellStyle() {
        CellStyle cellStyle = basicCellType();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("hh:mm"));
        return cellStyle;
    }


    // ############################### END CELL-STYLES ###############################










    /**
     * Extract if this is only for a month or from a month to another
     * @return either ex: april or april-june
     */
    private String extractPeriod() {

        if (workDays.size() == 0) {
            return "NONE";
        }


        WorkDay earliest = workDays.get(0); // Start with the first in the list
        WorkDay last = workDays.get(workDays.size()-1); // Last in list is probably last

        // Loop the workdays to see if the extraction is ordered
        for (WorkDay day: workDays) {
            // Is it the same object as earliest?
            if (day != earliest) {
                if (day.getDay().before(earliest)) {
                    earliest = day;
                }
            }

            // Is the day after the last?
            if (day != last) {
                if (day.getDay().after(last)) {
                    last = day;
                }
            }
        }

        // Earliest and last within same month
        if (earliest.getDay().get(Calendar.MONTH) == last.getDay().get(Calendar.MONTH)) {
            return earliest.getDay().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        } else { // Else create April - june
            return earliest.getDay().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                    +" - " + last.getDay().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        }
    }


    /**
     * Writes the current workbook to a file
     * @param filename filename of the resulting file
     * @throws IOException in case of emergency ;)
     */
    public void write(String filename) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        workDayHeaderRow();
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
