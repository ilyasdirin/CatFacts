package com.idirin.catfactstask.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idirin.catfactstask.R;
import com.idirin.catfactstask.ui.listeners.RecyclerViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by
 * idirin on 01/11/2017...
 */

public class FactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.imViewShare)
    ImageView imViewShare;
    @BindView(R.id.txtCatFact)
    public TextView txtCatFact;
    @BindView(R.id.txtLength)
    public TextView txtLength;
    @BindView(R.id.txtIndex)
    public TextView txtIndex;

    private RecyclerViewClickListener clickListener;

    public FactHolder(View v, RecyclerViewClickListener clickListener) {
        super(v);
        ButterKnife.bind(this, v);
        imViewShare.setOnClickListener(this);
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        clickListener.recyclerViewListClicked(v, this.getAdapterPosition());
    }
}
