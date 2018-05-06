package com.ntnu.wip.nabl.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ntnu.wip.nabl.Adapters.View.ProjectClient.ProjectClientAdapterView;
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
        SummaryContainer container = list.get(position);
        convertView = inflater.inflate(R.layout.project_client_list_element, null);
        TextView indicator = convertView.findViewById(R.id.project_client_indicator);
        TextView title = convertView.findViewById(R.id.indicator_name);
        TextView cardHours = convertView.findViewById(R.id.cardHours);
        TextView cardOverTime = convertView.findViewById(R.id.cardOverTime);
        TextView cardTotalTime = convertView.findViewById(R.id.cardHoursTotal);

        title.setText(container.getTitleString());
        indicator.setText(container.getType().toString());
        cardHours.setText(formatHourIndicator(context.getString(R.string.summaryHours), container.getHours()));
        cardOverTime.setText(formatHourIndicator(context.getString(R.string.summaryOverTime), container.getOverTime()));
        cardTotalTime.setText(formatHourIndicator(context.getString(R.string.summaryHoursTotal), container.getTotalHours()));
        return convertView;//mvcView.getRootView();
    }

    private String formatHourIndicator(String text, long hours) {
        return text + " "+hours;
    }
}
