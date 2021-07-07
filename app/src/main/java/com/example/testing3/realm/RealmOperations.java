package com.example.testing3.realm;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.testing3.pojo.PeopleDetailPojo;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmOperations {

    Realm realm;
    Context context;

    private void insert()
    {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        PeopleDetailPojo modal= new PeopleDetailPojo();

        Number current_id  = realm.where(PeopleDetailPojo.class).max("sno");

        int nextId;
        if (current_id==null)
        {
            nextId=1;

        }
        else
        {
            nextId=current_id.intValue()+1;
        }
        modal.setSno(nextId);

        modal.setName("hc");
        modal.setmob("7696");
        modal.setCity("jalandhar");
        modal.setEmail("abc@gmail.com");
        modal.setPassword("password");

        realm.insert(modal);




        realm.commitTransaction();

    }

    private boolean checkUser(String email,String password) {

        RealmResults<PeopleDetailPojo> modals = realm.where(PeopleDetailPojo.class).findAll();
        for (PeopleDetailPojo modal : modals) {
            if (email.equals(modal.getEmail()) && password.equals(modal.getPassword())) {
                
                return true;
            }
            else
            {
                //Toast.makeText(this, "Login Fails", Toast.LENGTH_SHORT).show();
            }
        }

        return  false;
    }

    private void showData() {
        //realm.beginTransaction();
        List<PeopleDetailPojo> dataModals=realm.where(PeopleDetailPojo.class).findAll();
        dataModals.size();
//        output.setText("");
        for (int i=0;i<dataModals.size();i++){

//            output.append("S No : "+dataModals.get(i).getSno()+
//                    " Name : "+dataModals.get(i).getName() +
//                    " Mob : "+dataModals.get(i).getNumber() +
//                    " Dob : "+dataModals.get(i).getDob() +
//                    " Email : "+dataModals.get(i).getEmail() +
//                    " Pass : "+dataModals.get(i).getPassword() +"\n");
        }

    }

    public void saveListinDb(PeopleDetailPojo peopleDetailPojo) {
        Realm realm = Realm.getDefaultInstance();
        try {

            if (realm.isInTransaction())
                realm.commitTransaction();
            realm.beginTransaction();
            realm.insert(peopleDetailPojo);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            if (realm.isInTransaction())
                realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }
}
