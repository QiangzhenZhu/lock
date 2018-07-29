package com.hzdongcheng.parcellocker.views.pickup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickupOpeningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickupOpeningFragment extends WrapperFragment {


    public PickupOpeningFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PickupOpeningFragment.
     */
    public static PickupOpeningFragment newInstance(String param1, String param2) {
        PickupOpeningFragment fragment = new PickupOpeningFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pickup_opening, container, false);
    }

}
