package com.idirin.catfactstask.ui.mvp.facts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.idirin.catfactstask.R;
import com.idirin.catfactstask.event.ShareClickedEvent;
import com.idirin.catfactstask.ui.adapters.FactAdapter;
import com.idirin.catfactstask.ui.fragments.BaseFragment;
import com.idirin.catfactstask.ui.listeners.EndlessScrollListener;
import com.idirin.catfactstask.util.TestUtil;
import com.squareup.otto.Subscribe;
import com.xw.repo.BubbleSeekBar;

import butterknife.BindView;

public class FactsFragment extends BaseFragment implements FactsMvp.ViewOps {

    @BindView(R.id.seekBar)
    protected BubbleSeekBar seekBar;
    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txtFactCount)
    protected TextView txtFactCount;

    FactsMvp.ViewPresenterOps mPresenter;

    private FactAdapter factAdapter;
    private LinearLayoutManager linearLayoutManager;


    /* Abstract Methods */
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_facts;
    }

    @Override
    protected int getTitleResId() {
        return R.string.app_name;
    }

    @Override
    protected void init() {
        setupMvp();
        setupViews();
    }




    /* Class Methods */
    private void setupViews() {
        swipeRefreshLayout.setEnabled(false);
        seekBar.setOnProgressChangedListener(progressChangedListener);
        factAdapter = new FactAdapter(mPresenter);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(factAdapter);
    }




    /* Mvp Methods */
    @Override
    public void setupMvp() {
        FactsPresenter presenter = new FactsPresenter(this);
        FactsModel model = new FactsModel(presenter);
        presenter.setModel(model);
        mPresenter = presenter;
    }

    @Override
    public Context getActivityContext() {
        return activity;
    }

    @Override
    public void showToast(@NonNull String msg) {
        Toast.makeText(getActivityContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean showEnabled) {
        if (showEnabled) {
            swipeRefreshLayout.setRefreshing(true);
            seekBar.setEnabled(false);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            seekBar.setEnabled(true);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        factAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyDataRangeInserted(int startPosition, int itemCount) {
        factAdapter.notifyItemRangeInserted(startPosition, itemCount);
    }

    @Override
    public void setFactCount(int totalFactCount) {
        txtFactCount.setText(getString(R.string.count_facts, totalFactCount));
        recyclerView.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                mPresenter.loadFacts(false, seekBar.getProgress());
            }
        });
    }




    /* User Interactions */
    @Subscribe
    public void onShareClicked(ShareClickedEvent event) {
        mPresenter.share(event.getClickedFactPosition());
    }

    BubbleSeekBar.OnProgressChangedListener progressChangedListener = new BubbleSeekBar.OnProgressChangedListener() {
        @Override
        public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            if (TestUtil.isRunningEspressoTest()) {
                mPresenter.resetFacts();
                notifyDataSetChanged();
                mPresenter.loadFacts(true, seekBar.getProgress());
            }
        }

        @Override
        public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            if (!TestUtil.isRunningEspressoTest()) {
                mPresenter.resetFacts();
                notifyDataSetChanged();
                mPresenter.loadFacts(true, seekBar.getProgress());
            }
        }

        @Override
        public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

        }
    };


}
