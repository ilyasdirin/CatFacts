package com.idirin.catfactstask;

import android.content.Intent;

import com.google.gson.Gson;
import com.idirin.catfactstask.service.models.BaseModel;
import com.idirin.catfactstask.service.models.FactModel;
import com.idirin.catfactstask.service.responses.FactResponse;
import com.idirin.catfactstask.ui.mvp.facts.FactsFragment;
import com.idirin.catfactstask.ui.mvp.facts.FactsModel;
import com.idirin.catfactstask.ui.mvp.facts.FactsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by
 * idirin on 02/11/2017...
 */

@RunWith(PowerMockRunner.class)
public class FactsPresenterTest extends BaseUnitTest {

    private FactsPresenter mPresenter;
    private FactsModel mModel;
    private FactsFragment mView;

    @Before
    public void set() {
        mView = mock(FactsFragment.class);
        mPresenter = new FactsPresenter(mView, true);
        mModel = new FactsModel(mPresenter);
        mPresenter.setModel(mModel);

        //Return mocked context when needed
        when(mPresenter.getActivityContext()).thenReturn(context);
    }


    @Test
    public void resetFactsTest() {
        FactResponse response = prepareResponse();
        mModel.appendFacts(response);
        mPresenter.resetFacts();
        assertTrue(mModel.getFacts().size() == 0);
        assertTrue(mModel.getTotalFactsCount() == -1);
    }

    @Test
    public void getFactCountTest() {
        FactResponse response = prepareResponse();
        int size = response.getFacts().size();
        mModel.appendFacts(response);
        assertTrue(mModel.getFacts().size() == size);
    }

    @Test
    public void onDestroyFactsTest() {
        FactResponse response = prepareResponse();
        mModel.appendFacts(response);
        mPresenter.onDestroy();
        assertNull(mModel.getFacts());
        assertTrue(mModel.getTotalFactsCount() == -1);
    }

    @Test
    public void getViewTest() {
        assertEquals(mPresenter.getView(), mView);
    }

    @Test
    public void getViewActivityContextTest() {
        when(mView.getActivityContext()).thenReturn(context);
        assertEquals(mPresenter.getActivityContext(), context);
    }

    @Test
    public void shareTest() {
        FactResponse response = prepareResponse();
        mModel.appendFacts(response);
        mPresenter.share(0);
        verify(context).startActivity(any(Intent.class));
    }







    private FactResponse prepareResponse() {
        String factJson = loadJSONFromAsset("fact.json");
        FactModel fact = (new Gson()).fromJson(factJson, FactModel.class);

        String baseJson = loadJSONFromAsset("base.json");
        BaseModel base = (new Gson()).fromJson(baseJson, BaseModel.class);

        List<FactModel> facts = new ArrayList<>();
        facts.add(fact);
        facts.add(fact);

        FactResponse response = new FactResponse();
        response.setFacts(facts);

        response.setTotal(base.getTotal());
        return response;
    }







}
