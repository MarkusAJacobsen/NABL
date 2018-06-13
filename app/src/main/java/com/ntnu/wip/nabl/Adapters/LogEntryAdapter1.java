package com.ntnu.wip.nabl.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntnu.wip.nabl.Models.WorkDay;
import com.ntnu.wip.nabl.R;
import com.ntnu.wip.nabl.Utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogEntryAdapter1 extends BaseAdapter {
    private List<WorkDay> entriesToAdd;
    private Context context;
    private String hourIndicator;

    public LogEntryAdapter1(List<WorkDay> entriesToAdd, Context context) {
        this.entriesToAdd = entriesToAdd;
        this.context = context;

        hourIndicator = context.getString(R.string.hourIndicator);
    }

    @Override
    public int getCount() {
        return entriesToAdd.size();
    }

    @Override
    public Object getItem(int i) {
        return entriesToAdd.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LogEntryHolder holder;
        final WorkDay currentLogEntry = entriesToAdd.get(i);

        if(view == null) {
            view = View.inflate(context, R.layout.log_entry_list_item_1, null);
            holder = new LogEntryHolder(view);
            view.setTag(holder);
        } else {
            holder = (LogEntryHolder) view.getTag();
        }


        fillOutGuiDetails(holder, currentLogEntry);


        return view;

    }

    private String determineHeader(WorkDay dayToCheck){
        if(!dayToCheck.getClientId().equals("")){
            return dayToCheck.getClientId();
        } else {
            return dayToCheck.getProjectId();
        }
    }

    private String getFullDateString(final WorkDay resource) {
        String toBeReturned;

        final DateTime startDateFull = resource.getStartTime();
        final DateTime endDateFull = resource.getEndTime();

        final String startDate = getDayMonthString(startDateFull);
        final String possibleOtherEndDate = getDayMonthString(endDateFull);

        if(startDate.equals(possibleOtherEndDate)) {
            toBeReturned = startDate;
        } else {
            toBeReturned = String.format("%s%s%s", startDate, "-\n", possibleOtherEndDate);
        }


        return toBeReturned;
    }

    private String getDayMonthString(final DateTime dateToConvert) {
        final LocalDate localStartDate = dateToConvert.toLocalDate();

        final int day = localStartDate.getDayOfMonth();
        final int month = localStartDate.getMonthOfYear();

        final String dayString = Utils.addMissingZero(day);
        final String monthString = Utils.addMissingZero(month);

        return String.format(Locale.getDefault(), "%s%s%s", dayString, '.', monthString);
    }

    private String getWorkDayHoursString(WorkDay resource) {
        final DateTime startDateWithTime = resource.getStartTime();
        final DateTime endDateWithTime = resource.getEndTime();

        final LocalTime startTime = startDateWithTime.toLocalTime();
        final LocalTime endTime = endDateWithTime.toLocalTime();

        final String startTimeStringFormat = getTimeString(startTime);
        final String endTimeStringFormat = getTimeString(endTime);

        return String.format("%s%s%s", startTimeStringFormat, '-', endTimeStringFormat);
    }

    private String getTimeString(LocalTime time) {
        final int hour = time.getHourOfDay();
        final int minutes = time.getMinuteOfHour();

        final String hourString = Utils.addMissingZero(hour);
        final String minuteString = Utils.addMissingZero(minutes);

        return String.format("%s%s%s", hourString, ":", minuteString);
    }

    private void fillOutGuiDetails(LogEntryHolder holder, WorkDay resource) {
        final String header = determineHeader(resource);
        holder.header.setText(header);

        holder.description.setText(resource.getDescription());

        final String dateString = getFullDateString(resource);
        holder.date.setText(dateString);

        final String hourString = getWorkDayHoursString(resource);
        holder.clock.setText(hourString);

        final double hoursWorked = resource.getTotalHours();
        final String hoursWorkedString = "" + hoursWorked + hourIndicator;
        holder.totalHours.setText(hoursWorkedString);
    }


    static class LogEntryHolder {
        @BindView(R.id.header) TextView header;
        @BindView(R.id.background) ImageView background;
        @BindView(R.id.description) TextView description;
        @BindView(R.id.date) TextView date;
        @BindView(R.id.clock) TextView clock;
        @BindView(R.id.totalHours) TextView totalHours;


        LogEntryHolder(View root){
            ButterKnife.bind(this, root);
        }
    }
}
