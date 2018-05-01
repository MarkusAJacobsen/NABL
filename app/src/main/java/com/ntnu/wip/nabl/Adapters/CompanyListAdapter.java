package com.ntnu.wip.nabl.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.ntnu.wip.nabl.Adapters.ViewHolders.CompanyListViewHolder;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.R;

import java.util.List;

/**
 * Custom adapter for settings company list containg
 * a description field and to buttons
 */
public class CompanyListAdapter extends BaseAdapter {
    private Context context;
    private List<Company> companies;
    private View row;
    private CompanyListViewHolder holder;
    private ICompanyListAdapterCallback listener;
    private RadioButton listRadioButton = null;
    private int listIndex = -1;

    /**
     * Constructor
     * @param context Context
     * @param companies List<Company>
     */
    public CompanyListAdapter(Context context, List<Company> companies) {
        this.context = context;
        this.companies = companies;
    }

    /**
     * Constructor
     * @param context Context
     * @param companies List<Company>
     * @param savedOption int - which item to toggle the radiobutton for
     */
    public CompanyListAdapter(Context context, List<Company> companies, int savedOption) {
        this.context = context;
        this.companies = companies;
        listIndex = savedOption;
    }

    /**
     * Get GUI item holder
     * @return {@link CompanyListViewHolder}
     */
    public CompanyListViewHolder getHolder(){
        return holder;
    }

    /**
     * Set listener for buttons
     * @param listener {@link ICompanyListAdapterCallback}
     */
    public void setListener(ICompanyListAdapterCallback listener) {
        this.listener = listener;
    }

    /**
     * Get adapter item count
     * @return int
     */
    @Override
    public int getCount() {
        return companies.size();
    }

    /**
     * Get item
     * @param i int - position
     * @return Object
     */
    @Override
    public Object getItem(int i) {
        return companies.get(i);
    }

    /**
     * Not implementet
     * @param i int
     * @return 0
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Create a list entry
     * @param position int
     * @param view View
     * @param viewGroup ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.company_list_item, viewGroup, false);

        holder = new CompanyListViewHolder();

        initialiseGUIComponents(holder);

        holder.description.setText(companies.get(position).toString());

        if(position == listIndex) {
            holder.workspace.toggle();
            listRadioButton = holder.workspace;
        }

        //Button clicks, call listener behaviour
        if(listener != null) {
            holder.delete.setOnClickListener(pressedView -> {
                listener.deletePressed(position);
            });

            holder.workspace.setOnClickListener(pressedView -> {
                listener.selectedWorkspace(position);
                deselectEverythingElse(pressedView);
            });
        }

        return row;
    }

    /**
     * Fetch GUI items
     * @param holder {@link CompanyListViewHolder} - where to place items
     */
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


    /**
     * Making sure only one radiobutton in the adapter is checked at one time
     * https://stackoverflow.com/a/11894068/7036624
     * @param pressedView View
     */
    private void deselectEverythingElse(View pressedView){
        View vMain = ((View) pressedView.getParent());
        int newIndex = ((ViewGroup) vMain.getParent()).indexOfChild(vMain);
        if (listIndex == newIndex) return;

        if (listRadioButton != null) {
            listRadioButton.setChecked(false);
        }

        listRadioButton = (RadioButton) pressedView;
        listIndex = newIndex;
    }
}
