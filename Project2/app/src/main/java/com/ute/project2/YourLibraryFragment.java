package com.ute.project2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ute.project2.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class YourLibraryFragment extends Fragment {

    TabLayout tlHeader;
    ViewPager2 vpContent;
    FragmentAdapter fragmentAdapter;
    List<Fragment> fragmentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_your_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentList = new ArrayList<>();
        fragmentList.add(new FavoriteFragment());
        fragmentList.add(new DownloadFragment());

        tlHeader = view.findViewById(R.id.tlHeader);
        vpContent = view.findViewById(R.id.vpContent);

        fragmentAdapter = new FragmentAdapter(requireActivity(), fragmentList);
        vpContent.setAdapter(fragmentAdapter);
        new TabLayoutMediator(tlHeader, vpContent, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.favorites).setIcon(R.drawable.md_favorite);
                    break;
                case 1:
                    tab.setText(R.string.download).setIcon(R.drawable.md_download);
                    break;
            }
        }).attach();
    }

}