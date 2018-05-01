package com.ntnu.wip.nabl.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ntnu.wip.nabl.Adapters.View.ProjectClientAdapterView;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Models.SummaryContainer;
import com.ntnu.wip.nabl.R;

import java.util.List;

/**
 * This adapter takes two different kinds of objects
 * It treats them both as equals and creates one list of elements.
 * Projects are represented in the first indexes while clients are last
 * Created by klingen on 01.05.18.
 */

public class ClientProjectAdapter extends BaseAdapter {

    private Context context;
    private List<SummaryContainer> list;

    ClientProjectAdapter(Context context, List<SummaryContainer> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ProjectClientAdapterView mvcView = new ProjectClientAdapterView(inflater, parent);

        SummaryContainer container = list.get(position);

        mvcView.setHours(formatHourIndicator(context.getString(R.string.summaryHours), container.getHours()));
        mvcView.setOverTime(formatHourIndicator(context.getString(R.string.summaryOverTime), container.getOverTime()));
        mvcView.setTotalHours(formatHourIndicator(context.getString(R.string.summaryHoursTotal), container.getTotalHours()));

        mvcView.setType(container.getType().toString());
        mvcView.setTitle(container.getTitleString());

        return mvcView.getRootView();
    }

    private String formatHourIndicator(String text, long hours) {
        return text + " "+hours;
    }
}
