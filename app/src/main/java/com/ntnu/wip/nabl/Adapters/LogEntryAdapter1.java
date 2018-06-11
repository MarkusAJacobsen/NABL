package com.ntnu.wip.nabl.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntnu.wip.nabl.Models.WorkDay;
import com.ntnu.wip.nabl.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogEntryAdapter1 extends BaseAdapter {
    private List<WorkDay> entriesToAdd;
    private Context context;

    public LogEntryAdapter1(List<WorkDay> entriesToAdd, Context context) {
        this.entriesToAdd = entriesToAdd;
        this.context = context;
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

    private void fillOutGuiDetails(LogEntryHolder holder, WorkDay resource) {
        final String header = determineHeader(resource);
        holder.header.setText(header);

        final double hoursWorked = resource.getTotalHours();
        holder.blueCircle.setText(String.valueOf(hoursWorked));
    }


    static class LogEntryHolder {
        @BindView(R.id.header) TextView header;
        @BindView(R.id.blueCircle) TextView blueCircle;
        @BindView(R.id.background) ImageView background;


        LogEntryHolder(View root){
            ButterKnife.bind(this, root);
        }
    }
}
