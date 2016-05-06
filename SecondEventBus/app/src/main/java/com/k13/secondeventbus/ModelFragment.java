package com.k13.secondeventbus;

import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Patel Milan on 4/5/16.
 */
public class ModelFragment extends Fragment
{
    private static final String[] items=
            {"Mumbai","Kolkata","Delhi","Chennai","Bangalore","Hyderabad","Ahmadabad",
             "Pune","Surat","Kanpur","Jaipur","Lucknow","Nagpur","Patna",
             "Indore","Vadodara","Bhopal","Coimbatore","Ludhiana","Kochi","Visakhapatnam",
             "Agra","Varanasi","Madurai","Meerut","Nashik","Jabalpur","Jamshedpur",
             "Asansol","Dhanbad","Faridabad","Allahabad","Amritsar","Vijayawada","Rajkot"};
    private List<String> model=
            Collections.synchronizedList(new ArrayList<String>());
    private boolean isStarted=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if (!isStarted) {
            isStarted=true;
            new LoadWordsThread().start();
        }
    }

    public ArrayList<String> getModel() {
        return(new ArrayList<String>(model));
    }

    class LoadWordsThread extends Thread
    {
        @Override
        public void run() {
            for (String item : items) {
                if (!isInterrupted())
                {
                    model.add(item);
                    EventBus.getDefault().post(new WordReadyEvent(item));
                    SystemClock.sleep(1000);
                }
            }
        }
    }
}
