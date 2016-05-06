package com.k13.secondeventbus;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Patel Milan on 4/5/16.
 */

public class MainActivity extends Activity
{
    private static final String MODEL_TAG = "model";
    private ModelFragment mFrag = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Get the Fragment Manager */
        FragmentManager mgr = getFragmentManager();

        /* Start or Bind the Fragment Transaction*/
        FragmentTransaction transaction = mgr.beginTransaction();

        mFrag = (ModelFragment) mgr.findFragmentByTag(MODEL_TAG);

        if (mFrag == null) {
            mFrag = new ModelFragment();
            transaction.add(mFrag, MODEL_TAG);
        }

        AsyncListFragment demo = (AsyncListFragment) mgr.findFragmentById(android.R.id.content);

        if (demo == null)
        {
            demo = new AsyncListFragment();
            transaction.add(android.R.id.content, demo);
        }
        demo.setModel(mFrag.getModel());

        if (!transaction.isEmpty())
        {
            transaction.commit();
        }
    }
}