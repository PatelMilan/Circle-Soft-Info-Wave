package com.k13.secondeventbus;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Patel Milan on 4/5/16.
 */

public class AsyncListFragment extends ListFragment
{
    private ArrayAdapter<String> adapter=null;
    private ArrayList<String> model=null;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        adapter=
                new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_list_item,
                        model);

        getListView().setScrollbarFadingEnabled(false);
        setListAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        /*  EventBus Register */
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach()
    {
        /*  Unregister EventBus */
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }
    /*  Event For Subscriber    */
    public void onEventMainThread(WordReadyEvent event)
    {
        adapter.add(event.getWord());
    }

    /*  Set the Model Data Into the ListView    */
    public void setModel(ArrayList<String> model)
    {
        this.model=model;
    }
}