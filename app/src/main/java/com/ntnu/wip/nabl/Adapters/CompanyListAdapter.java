package com.ntnu.wip.nabl.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.ntnu.wip.nabl.Adapters.ViewHolders.CompanyListViewHolder;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.R;

import java.util.List;

public class CompanyListAdapter extends BaseAdapter {
    private Context context;
    private List<Company> companies;
    private View row;
    private CompanyListViewHolder holder;

    public CompanyListAdapter(Context context, List<Company> companies) {
        this.context = context;
        this.companies = companies;
    }

    @Override
    public int getCount() {
        return companies.size();
    }

    @Override
    public Object getItem(int i) {
        return companies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.company_list_item, viewGroup, false);

        holder = new CompanyListViewHolder();

        initialiseGUIComponents(holder);

        holder.description.setText(companies.get(position).toString());

        holder.delete.setOnClickListener(pressedView -> {
            Toast.makeText(context, "Delete pressed in item "+position, Toast.LENGTH_SHORT).show();
        });

        return row;
    }

    private void initialiseGUIComponents(CompanyListViewHolder holder){
        if(row == null) {
            return;
        }

        holder.description = row.findViewById(R.id.description);
        holder.delete = row.findViewById(R.id.delete);
        Drawable deleteIcon = context.getDrawable(android.R.drawable.ic_delete);
        holder.delete.setImageDrawable(deleteIcon);
        holder.workspace = row.findViewById(R.id.workspace);
    }

    public CompanyListViewHolder getHolder(){
        return holder;
    }
}
