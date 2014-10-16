package com.crazydudes.viewpagerexample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.crazydudes.viewpagerexample.MainActivity;
import com.crazydudes.viewpagerexample.R;
import com.crazydudes.viewpagerexample.data.RecordsAdapter;
import com.crazydudes.viewpagerexample.interfaces.OnItemsAdded;

/**
 * Failed Fragment
 */
public class FailedFragment extends Fragment implements OnItemsAdded {
    private RecordsAdapter adapter;

    public FailedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_general, null);
        ListView lv = (ListView) view.findViewById(R.id.lv_records);
        Button button = new Button(getActivity());
        button.setText("Load more...");
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addItems();
            }
        });

        adapter = new RecordsAdapter(getActivity(), RecordsAdapter.MODE_FAILED);

        lv.addFooterView(button);
        lv.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).registerListener(this);
        adapter.setData(((MainActivity) getActivity()).getRecordList());
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).unRegisterListener(this);
    }

    @Override
    public void onItemsAdded() {
        adapter.setData(((MainActivity)getActivity()).getRecordList());
    }
}
