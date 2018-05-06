package com.ntnu.wip.nabl.MVCView.SummaryView;

import com.ntnu.wip.nabl.Adapters.CompanyAdapter;
import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Project;

import java.util.List;

/**
 * Created by klingen on 01.05.18.
 */

public interface ISummaryView extends IAbstractMvcView {
    /**
     * Sets an adapter with a set of nested adapters
     * @param adapters adapters with adapters in them
     */
    void setCompanyAdapters(CompanyAdapter adapters);


    interface ISummaryListener {
        void projectClicked(Project project);
        void clientClicked(Client client);
    }
}
