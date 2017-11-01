package com.idirin.catfactstask.ui.mvp.facts;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.idirin.catfactstask.service.models.FactModel;
import com.idirin.catfactstask.service.responses.FactResponse;
import com.idirin.catfactstask.ui.holders.FactHolder;
import com.idirin.catfactstask.ui.mvp.BaseMvp;

import java.util.List;

/**
 * Created by
 * idirin on 01/11/2017...
 */

public interface FactsMvp {

    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     * Presenter to View
     */
    interface ViewOps extends BaseMvp.ViewOps {

        void showToast(@NonNull String msg);
        void showLoading(boolean showEnabled);
        void notifyDataSetChanged();
        void notifyDataRangeInserted(int startPosition, int itemCount);
        void setFactCount(int totalFactCount);
    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Process user interaction, sends data requests to Model, etc.
     * View to Presenter
     */
    interface ViewPresenterOps extends BaseMvp.ViewPresenterOps {

        FactsMvp.ViewOps getView();
        void loadFacts(boolean newFactLength, int maxLength);

        int getFactCount();
        FactHolder createViewHolder(ViewGroup parent, int viewType);
        void bindViewHolder(FactHolder holder, int position);
        void share(int position);
        void resetFacts();
    }

    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     * Presenter to Model
     */
    interface ModelPresenterOps extends BaseMvp.ModelPresenterOps {


    }

    /**
     * Required Presenter methods available to Model.
     * Model to Presenter
     */
    interface ModelOps extends BaseMvp.ModelOps {

        List<FactModel> getFacts();
        int getFactCount();
        FactModel getFact(int position);
        void resetFacts();
        void appendFacts(@NonNull FactResponse response);
        int getTotalFactsCount();
    }


}
