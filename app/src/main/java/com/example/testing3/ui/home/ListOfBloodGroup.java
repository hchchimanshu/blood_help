package com.example.testing3.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testing3.BaseActivity;
import com.example.testing3.adapter.MyAdapter;
import com.example.testing3.R;
import com.example.testing3.pojo.DataModal;

import java.util.ArrayList;
import java.util.List;

public class ListOfBloodGroup extends Fragment {

    RecyclerView listOfBlood;
    List<DataModal> arrayListOfBlood;
    DataModal dataModal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list_of_blood_group, container, false);

        init(view);

        addingDataToList();

//        listOfBlood.setLayoutManager(new LinearLayoutManager(getActivity()));
        setLayoutManager();

        setToolbarText();

        return view;
    }

    private void setLayoutManager() {
        listOfBlood.setLayoutManager(new GridLayoutManager(getActivity(),2));
        MyAdapter adapter = new MyAdapter(arrayListOfBlood,getActivity());
        listOfBlood.setAdapter(adapter);
    }

    private void setToolbarText() {
        BaseActivity baseActivity = (BaseActivity)requireActivity();
        baseActivity.onToolbarTextChange(getResources().getString(R.string.list_of_blood));
    }

    private void addingDataToList() {
        dataModal.setBlood("O+");
        arrayListOfBlood.add(dataModal);
        DataModal d2 =  new DataModal();
        d2.setBlood("O-");
        arrayListOfBlood.add(d2);
        DataModal d3 =  new DataModal();
        d3.setBlood("A+");
        arrayListOfBlood.add(d3);
        DataModal d4 =  new DataModal();
        d4.setBlood("A-");
        arrayListOfBlood.add(d4);
        DataModal d5 =  new DataModal();
        d5.setBlood("B+");
        arrayListOfBlood.add(d5);
        DataModal d6 =  new DataModal();
        d6.setBlood("B-");
        arrayListOfBlood.add(d6);
        DataModal d7 =  new DataModal();
        d7.setBlood("AB+");
        arrayListOfBlood.add(d7);
        DataModal d8 =  new DataModal();
        d8.setBlood("AB-");
        arrayListOfBlood.add(d8);
    }

    private void init(View view) {
        listOfBlood=view.findViewById(R.id.list_of_blood_RV);
        arrayListOfBlood= new ArrayList<>();
        dataModal = new DataModal();
    }
}