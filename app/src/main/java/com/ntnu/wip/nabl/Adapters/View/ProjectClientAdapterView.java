package com.ntnu.wip.nabl.Adapters.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntnu.wip.nabl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectClientAdapterView implements IProjectClientViewCard {

    private View rootView;

    @BindView(R.id.project_client_indicator)
    TextView indicator;

    @BindView(R.id.indicator_name)
    TextView title;

    @BindView(R.id.cardHours)
    TextView cardHours;

    @BindView(R.id.cardOverTime)
    TextView cardOverTime;

    @BindView(R.id.cardHoursTotal)
    TextView cardTotalHours;

    public ProjectClientAdapterView(LayoutInflater inflater, ViewGroup viewGroup) {
        rootView = inflater.inflate(R.layout.project_client_list_element, viewGroup);
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void setType(String type) {
        indicator.setText(type);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setTotalHours(String totalHours) {
        cardTotalHours.setText(totalHours);
    }

    @Override
    public void setOverTime(String overTime) {
        cardOverTime.setText(overTime);
    }

    @Override
    public void setHours(String hours) {
        cardHours.setText(hours);
    }

    @Override
    public View getRootView() {
        return null;
    }
}
