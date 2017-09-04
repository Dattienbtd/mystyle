package com.btd.mystyle.home.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btd.mystyle.databinding.FragmentMessageBinding;
import com.btd.mystyle.databinding.FragmentShopBinding;

/**
 * Created by dattien on 7/16/17.
 */

public class MessageFragment extends Fragment {

    private FragmentMessageBinding fragmentMessageBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMessageBinding = FragmentMessageBinding.inflate(inflater, container, false);
        return fragmentMessageBinding.getRoot();
    }
}
