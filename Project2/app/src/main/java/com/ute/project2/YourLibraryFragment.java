package com.ute.project2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ute.project2.adapter.LibraryAdapter;
import com.ute.project2.constant.Constant;
import com.ute.project2.decoration.ItemDecoration;
import com.ute.project2.event.ItemLibraryListener;
import com.ute.project2.item.ItemLibrary;

public class YourLibraryFragment extends Fragment implements ItemLibraryListener {
    private ItemLibraryListener itemLibraryListener;
    private View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ItemLibraryListener) {
            itemLibraryListener = (ItemLibraryListener) context;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_your_library, container, false);
        initializeView();
        return view;
    }

    private void initializeView() {
        if (view != null) {
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view_library);
            LibraryAdapter libraryAdapter = new LibraryAdapter(getContext(), itemLibraryListener, Constant.ITEM_LIBRARY_LIST);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new ItemDecoration(22));
            recyclerView.setAdapter(libraryAdapter);
        }
    }

    @Override
    public void onItemLibraryOnClick(ItemLibrary itemLibrary) {
        itemLibraryListener.onItemLibraryOnClick(itemLibrary);
    }
}