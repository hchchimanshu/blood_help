package com.example.testing3.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing3.BaseActivity;
import com.example.testing3.R;
import com.example.testing3.adapter.PeopleDetailsAdapter;
import com.example.testing3.pojo.PeopleDetailPojo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DashboardFragment extends Fragment {

    List<PeopleDetailPojo> detailPojoList = new ArrayList<>();
    PeopleDetailsAdapter adapter;
    RecyclerView peopleDetailRecyclerView;
    Realm realm;
    List<String> list= new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        init(root);

        setData();

//        PeopleDetailPojo pojo= new PeopleDetailPojo();
//        pojo.setName("hchchchc");
////        pojo.setCity("Jalandhar");
//        detailPojoList.add(pojo);

        setLayoutManager();

        setToolbarText();

        return root;
    }

    private void setData() {
        String blood = getArguments().getString("item");

        RealmResults<PeopleDetailPojo> modals = realm.where(PeopleDetailPojo.class).findAll();
        for (PeopleDetailPojo modal : modals) {
            if (blood.equals(modal.getBloodGroup())) {
                PeopleDetailPojo pojo= new PeopleDetailPojo();
                pojo.setName(modal.getName());
                pojo.setEmail(modal.getEmail());
                pojo.setMob(modal.getMob());
                pojo.setCity(getResources().getString(R.string.jalandhar));
                detailPojoList.add(pojo);

            }
            else
            {
                Toast.makeText(requireActivity(), "not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init(View root) {
        peopleDetailRecyclerView = root.findViewById(R.id.people_detail_RV);
        realm= Realm.getDefaultInstance();

    }

    private void setLayoutManager() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(requireActivity());
        peopleDetailRecyclerView.setLayoutManager(linearLayoutManager);
        adapter =  new PeopleDetailsAdapter(detailPojoList,requireActivity());
        peopleDetailRecyclerView.setAdapter(adapter);
    }

    private void setToolbarText() {
        BaseActivity baseActivity = (BaseActivity)requireActivity();
        baseActivity.onToolbarTextChange(getResources().getString(R.string.list_people));
    }
}