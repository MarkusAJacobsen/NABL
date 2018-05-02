package com.ntnu.wip.nabl.Models;

import android.content.Context;

import com.ntnu.wip.nabl.R;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private Project project;

    // XLXS-Sheet related variables
    private Workbook workbook;          // The document with possibility of several sheets
    private Sheet sheet;                // The actual excelSheet
    private CreationHelper creationHelper;  // Helps with various formatting


    // CONSTANTS
    private static final int HEADER_ROW_NUMBER = 7;
    private static final int FIRST_HEADER_COLUMN = 3;

    private static final int DATE_COLUMN = 3;
    private static final int FROM_TIME_COLUMN = 4;
    private static final int TO_TIME_COLUMN = 5;
    private static final int HOURS_COLUMN = 6;
    private static final int BREAK_TIME_COLUMN = 7;
    private static final int OVER_TIME_COLUMN = 8;
    private static final int HOLY_TIME_COLUMN = 9;
    private static final int WEEKEND_TIME_COLUMN = 10;
    private static final int TOTAL_HOURS = 11;
    private static final int DESCRIPTION_COLUMN = 12;

    private static final int MAIN_TITLE_ROW = 1;
    private static final int EMPLOYEE_INFORMATION_ROW = 3;
    private static final int RECIPIENT_INFORMATION = 4;
    private static final int PROJECT_INFORMATION = 5;


    private static final int ROWS_TO_SUM[] = {6, 7, 8, 9, 10, 11};

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

    /**
     * Create a timesheet for a project that is ruled
     * by a project
     * @param context you know...
     * @param project project to bill
     * @param user the user getting payed
     * @param workDays number of workdays
     */
    public TimeSheet(Context context, Project project, User user, List<WorkDay> workDays) {
        this.context = context;
        this.project = project;
        this.user = user;
        this.workDays = workDays;
        inizializeTimeSheet();
    }

    private void inizializeTimeSheet() {
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        workbook = new XSSFWorkbook();
        creationHelper = workbook.getCreationHelper();
        sheet = workbook.createSheet(this.extractPeriod());

    }

    /**
     * The autoresize-function provided by POI does not work when there is no java.awt library
     * this function makes resize the column based on the number of characters.
     * NOTE that this method is not that precise
     * @param row the row that will be used for resize
     * @param column the column that should be resized
     */
    private void resizeColumn(Row row, int column, double degree) {
        Cell cell = row.getCell(column);
        cell.getCellStyle().getFontIndex();
        int contentLength = cell.getStringCellValue().length();
        Double addition = contentLength*450.0*degree;
        int width =  addition.intValue();
        sheet.setColumnWidth(column, width);
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
        headerNames.add(context.getString(R.string.weekendTime));
        headerNames.add(context.getString(R.string.holyTime));
        headerNames.add(context.getString(R.string.headerTotal));
        headerNames.add(context.getString(R.string.headerDescription));
        return headerNames;
    }

    /**
     * Writes the workdays into the sheet
     * @return the number of rows written
     */
    private int createTimeRows() {
        CellStyle dateCellstyle = dateCellStyle();
        CellStyle timeCellStyle = timeCellStyle();
        CellStyle textCellStyle = basicCellType();
        Cell cell;

        Row row;
        int rowIndex = HEADER_ROW_NUMBER;
        for (WorkDay workDay: workDays) {
            row = sheet.createRow(++rowIndex);

            // Set the date in the sheet
            cell = row.createCell(DATE_COLUMN);
            cell.setCellStyle(dateCellstyle);
            cell.setCellValue(workDay.getDay());

            cell = row.createCell(DESCRIPTION_COLUMN);
            cell.setCellStyle(textCellStyle);
            cell.setCellValue(workDay.getDescription());

            // Set the start-time
            cell = row.createCell(FROM_TIME_COLUMN);
            cell.setCellStyle(timeCellStyle);
            cell.setCellValue(workDay.getStartTime().toDate());

            // Set stop-time
            cell = row.createCell(TO_TIME_COLUMN);
            cell.setCellStyle(timeCellStyle);
            cell.setCellValue(workDay.getEndTime().toDate());

            // Add the hours between start and stop
            setNumericCell(row.createCell(HOURS_COLUMN), textCellStyle, workDay.getTotalHours());

            // Break time
            setNumericCell(row.createCell(BREAK_TIME_COLUMN), textCellStyle, workDay.getBreakTime());

            // Holy time
            setNumericCell(row.createCell(HOLY_TIME_COLUMN), textCellStyle, workDay.getHolyDay());

            // WeekEnd Time
            setNumericCell(row.createCell(WEEKEND_TIME_COLUMN), textCellStyle, workDay.getWeekEnd());


            setNumericCell(row.createCell(OVER_TIME_COLUMN), textCellStyle, workDay.getOverTime());

            setNumericCell(row.createCell(TOTAL_HOURS), textCellStyle, workDay.getTotal());

        }
        return rowIndex;
    }

    /**
     * A part of code refactoring
     * @param cell cell to be modified
     * @param style cellStyle
     * @param value value of the cell
     */
    private void setNumericCell(Cell cell, CellStyle style, double value) {
        cell.setCellValue(value);
        cell.setCellStyle(style);
        cell.setCellType(CellType.NUMERIC);
    }


    /**
     * Create the headers for the timeRows
     * @return row with nicely formatted headers
     */
    private Row workDayHeaderRow() {
        Row headerRow = sheet.createRow(HEADER_ROW_NUMBER);
        List<String> cellHeaders = createHeaderList();

        CellStyle cellStyle = headerCellStyle();
        Cell cell;

        for (int i = FIRST_HEADER_COLUMN; i < cellHeaders.size()+FIRST_HEADER_COLUMN; i++) {
            cell = headerRow.createCell(i);
            String cellValue = cellHeaders.get(i-FIRST_HEADER_COLUMN);
            cell.setCellValue(cellValue);
            cell.setCellStyle(cellStyle);
            if (cellValue.equals(context.getString(R.string.headerDate)) ||
                cellValue.equals(context.getString(R.string.headerDescription))) {
                resizeColumn(headerRow, i, 2);
            } else {
                resizeColumn(headerRow, i, 1.0);
            }

        }

        sheet.getRow(HEADER_ROW_NUMBER).setHeight((short) 500);
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
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        // Add border
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return headerCellStyle;
    }


    /**
     * Creates a large centered, bold
     * cell style based on headerCellStyle
     * @return the said cell-style the said cell-style
     */
    private CellStyle titleCellStyle() {

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 20);

        CellStyle style = headerCellStyle();
        style.setFont(headerFont);

        return style;
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

    /**
     * Cellstyle based on the basic cell-style that formats time
     * @return the said style
     */
    private CellStyle timeCellStyle() {
        CellStyle cellStyle = basicCellType();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("hh:mm"));
        return cellStyle;
    }

    /**
     * Double dash on the bootom and no left/right border
     * @return the said style
     */
    private CellStyle sumRowCellStyle() {
        CellStyle style = basicCellType();
        style.setBorderBottom(BorderStyle.DOUBLE);
        style.setBorderLeft(BorderStyle.NONE);
        style.setBorderRight(BorderStyle.NONE);
        return style;
    }


    /**
     * Used for the information about the user company and project
     * @return nice simple style
     */
    private CellStyle informationStyle() {
        CellStyle style = headerCellStyle();

        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderLeft(BorderStyle.NONE);
        style.setBorderRight(BorderStyle.NONE);
        style.setBorderTop(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.NONE);

        return style;

    }


    // ############################### END CELL-STYLES ###############################



    /**
     * Extract if this is only for a month or from a month to another
     * @return either ex: april or april-june
     */
    public String extractPeriod() {

        if (workDays.size() == 0) {
            return "NONE";
        }


        WorkDay earliest = workDays.get(0); // Start with the first in the list
        WorkDay last = workDays.get(workDays.size()-1); // Last in list is probably last

        if (workDays.size() == 0) {
            last = workDays.get(0);
        }
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
        addEmployeeInformation();

        if (project != null) {
            addProjectInformation();
        } else if (company != null) {
            addCompanyInformation(this.company);
        } else if (client != null) {
            addClientInformation();
        }

        createTitleInformation();
        int lastRow = createTimeRows();
        createSumRow(lastRow);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }


    /**
     * Create a row that sums specific values
     * @param rowNumber where the sumRow should start
     * @return a row with formulas
     */
    private void createSumRow(int rowNumber) {
        Row row = sheet.createRow(rowNumber+2);
        int columns = createHeaderList().size();

        Cell cell;

        for (int i = FIRST_HEADER_COLUMN; i < FIRST_HEADER_COLUMN+columns; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(sumRowCellStyle());

            if (i == FIRST_HEADER_COLUMN) {
                cell.setCellValue("SUM");
            }

            for (int j = 0; j < ROWS_TO_SUM.length; j++) {
                if (i == ROWS_TO_SUM[j]) {
                    if (rowNumber-HEADER_ROW_NUMBER > 0) {
                        String firstCell = this.sheet.getRow(HEADER_ROW_NUMBER+2).getCell(i).getAddress().formatAsString();
                        String lastCell = this.sheet.getRow(rowNumber-2).getCell(i).getAddress().formatAsString();
                        cell.setCellFormula("SUM("+firstCell+":"+lastCell+")");
                    }
                }
            }
        }
    }

    /**
     * Create a title information that specifies
     * name of the employee and the recipient of the sheet
     */
    private void createTitleInformation() {
        Row upperTitle = sheet.createRow(MAIN_TITLE_ROW);
        CellRangeAddress adresses = new CellRangeAddress(MAIN_TITLE_ROW, MAIN_TITLE_ROW, FIRST_HEADER_COLUMN+2, FIRST_HEADER_COLUMN+7);
        sheet.addMergedRegion(adresses);
        upperTitle.setHeight((short) 700);

        Cell cell = upperTitle.createCell(FIRST_HEADER_COLUMN+2);
        cell.setCellValue(context.getString(R.string.sheetTitle)+" - "+this.extractPeriod());
        cell.setCellStyle(titleCellStyle());
    }

    /**
     * Add information about the user to the timeSheet
     * So he will get payed.
     * WE NO WORK FOR NO MONEY!!!!!!
     */
    private void addEmployeeInformation() {
        Row row = sheet.createRow(EMPLOYEE_INFORMATION_ROW);

        CellRangeAddress address = new CellRangeAddress(EMPLOYEE_INFORMATION_ROW, EMPLOYEE_INFORMATION_ROW, FIRST_HEADER_COLUMN, FIRST_HEADER_COLUMN+8);
        sheet.addMergedRegion(address);
        row.setHeight((short) 400);

        Cell cell = row.createCell(FIRST_HEADER_COLUMN);
        cell.setCellStyle(informationStyle());
        cell.setCellValue(user.getName()+" - "+user.getContactInformation().getEmail()+" - "+user.getContactInformation().getPhoneNumber());
    }


    /**
     * Writes company information to the excel sheet
     * @param company company from a project or similar
     */
    private void addCompanyInformation(Company  company) {
        Row row = sheet.createRow(RECIPIENT_INFORMATION);

        CellRangeAddress address = new CellRangeAddress(RECIPIENT_INFORMATION, RECIPIENT_INFORMATION, FIRST_HEADER_COLUMN, FIRST_HEADER_COLUMN+8);
        sheet.addMergedRegion(address);
        row.setHeight((short) 400);

        Cell cell = row.createCell(FIRST_HEADER_COLUMN);
        cell.setCellStyle(informationStyle());
        cell.setCellValue(company.getName()+" - "+company.getOrgnr());
    }

    /**
     * Write a project to the sheet and write the projects company
     */
    private void addProjectInformation() {
        Row row = sheet.createRow(EMPLOYEE_INFORMATION_ROW);

        CellRangeAddress address = new CellRangeAddress(PROJECT_INFORMATION, PROJECT_INFORMATION, FIRST_HEADER_COLUMN, FIRST_HEADER_COLUMN+8);
        sheet.addMergedRegion(address);
        row.setHeight((short) 400);

        Cell cell = row.createCell(FIRST_HEADER_COLUMN);
        cell.setCellStyle(informationStyle());
        cell.setCellValue(this.project.getName()+" - "+project.getCategory().name());
        addCompanyInformation(project.getCompany());
    }


    /**
     * Writes client information to the sheet
     */
    private void addClientInformation() {
        Row row = sheet.createRow(EMPLOYEE_INFORMATION_ROW);

        CellRangeAddress address = new CellRangeAddress(RECIPIENT_INFORMATION, RECIPIENT_INFORMATION, FIRST_HEADER_COLUMN, FIRST_HEADER_COLUMN+8);
        sheet.addMergedRegion(address);
        row.setHeight((short) 400);

        Cell cell = row.createCell(FIRST_HEADER_COLUMN);
        cell.setCellStyle(informationStyle());
        cell.setCellValue(this.client.getName()+" - "+this.client.getAddress());
    }
}
