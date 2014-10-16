package com.crazydudes.viewpagerexample;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.crazydudes.viewpagerexample.data.PagerContentProvider;
import com.crazydudes.viewpagerexample.data.PagerDatabaseTables;
import com.crazydudes.viewpagerexample.data.Record;
import com.crazydudes.viewpagerexample.data.RecordsPagerAdapter;
import com.crazydudes.viewpagerexample.fragments.AllFragment;
import com.crazydudes.viewpagerexample.fragments.FailedFragment;
import com.crazydudes.viewpagerexample.fragments.PassedFragment;
import com.crazydudes.viewpagerexample.interfaces.OnItemsAdded;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Simple activity with ActionBarTabs that dynamically change
 * and a view pager that changes the tabs.
 */
public class MainActivity extends FragmentActivity {

    private static final int INCREMENT_VALUE = 10;

    private ViewPager pager;
    private List<Record> recordList;
    private List<OnItemsAdded> listenerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* method to remove all records before anything happens */
        getContentResolver().delete(Uri.parse(PagerContentProvider.CONTENT_URI + PagerDatabaseTables.TABLE_NAME), null, null);

        pager = (ViewPager) findViewById(R.id.vp_main);
        addItems();

        AllFragment fragment = new AllFragment();
        FailedFragment failedFragment = new FailedFragment();
        PassedFragment passedFragment = new PassedFragment();

        List<Fragment> list = new ArrayList<Fragment>();
        list.add(fragment);
        list.add(failedFragment);
        list.add(passedFragment);
        RecordsPagerAdapter adapter = new RecordsPagerAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(adapter);
        setActionBar();

    }

    private void setActionBar() {
        final ActionBar actionBar = getActionBar();
        //set mode tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //tab listener
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());
            }
            //unused methods
            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}
            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
        };
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int i) {
                actionBar.getTabAt(i).select();
            }
            //unused methods
            @Override
            public void onPageScrolled(int i, float v, int i2) {}
            @Override
            public void onPageScrollStateChanged(int i) {}
        });
        for (int i = 0; i < pager.getAdapter().getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(pager.getAdapter().getPageTitle(i)).setTabListener(tabListener));
        }
    }

    public void addItems() {
          /* data creator, replace this with your api call*/
        String[] firstNames = {
                "Joseph",
                "Mark",
                "Tom",
                "Toni",
                "Barack",
                "Bill",
                "Pikachu",
                "Tyrone",
                "Tyrese",
                "Ben"
        };
        String[] lastNames = {
                "Dover",
                "Rogers",
                "Dick",
                "Smith",
                "Jones",
                "Mortimer",
                "Blake",
                "Tintin",
                "Waldo",
                "Outofideas"
        };
        for (int i = 0; i < INCREMENT_VALUE; i++) {
            String name = firstNames[new Random().nextInt(10)] + " " + lastNames[new Random().nextInt(10)];
            int grade = new Random().nextInt(100);
            ContentValues values = new ContentValues();
            values.put(PagerDatabaseTables.COLUMN_GRADE, grade);
            values.put(PagerDatabaseTables.COLUMN_NAME, name);
            getContentResolver().insert(Uri.parse(PagerContentProvider.CONTENT_URI + PagerDatabaseTables.TABLE_NAME), values);
        }
        /* end of data creator*/
        getData();
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    private void getData() {
        if (recordList != null)
            recordList.clear();
        else
            recordList = new ArrayList<Record>();

        Cursor cursor = getContentResolver().query(Uri.parse(PagerContentProvider.CONTENT_URI + PagerDatabaseTables.TABLE_NAME), null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(PagerDatabaseTables.COLUMN_NAME));
            int grade = cursor.getInt(cursor.getColumnIndex(PagerDatabaseTables.COLUMN_GRADE));
            recordList.add(new Record(grade, name));
        }
        cursor.close();

        if (listenerList != null)
            for (OnItemsAdded listener : listenerList) {
                listener.onItemsAdded();
            }
    }

    public void registerListener(OnItemsAdded listener) {
        if (listenerList == null) {
            listenerList = new ArrayList<OnItemsAdded>();
        }
        listenerList.add(listener);
    }

    public void unRegisterListener(OnItemsAdded listener) {
        for (int i = 0; i < listenerList.size(); i++) {
            if (listener == listenerList.get(i)) {
                listenerList.remove(i);
            }
        }
    }
}
