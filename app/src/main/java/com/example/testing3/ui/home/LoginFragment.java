package com.example.testing3.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.testing3.BaseActivity;
import com.example.testing3.R;
import com.example.testing3.pojo.PeopleDetailPojo;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginFragment extends Fragment {


    TextView notRegister,login , forgotPassword;
    EditText editTextEmail , editTextPassword;
    String email;
    String password;
    Realm realm;
    NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
     View root = inflater.inflate(R.layout.fragment_login, container, false);

        init(root);//checking navigationController

        onClickNotRegistered(root);//checkingg

        onClickLogin();

        onClickForgotPassword();

       setToolbarText();

        return root;
    }

    private void onClickForgotPassword() {
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setToolbarText() {
        BaseActivity baseActivity = (BaseActivity)requireActivity();
        baseActivity.onToolbarTextChange(getResources().getString(R.string.login));
    }

    private void onClickLogin() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
            if (checkNullValues()) {
                if (checkEmailPassword(email, password)) {
                    navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.navigation_list_of_blood);
                } else {
                    Toast.makeText(requireActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                    Log.e("LoginFragment","nullvalues in onClickLogin");
            }
            }
        });
    }

    private boolean checkNullValues() {
        if (email.length()==0)
        {
            editTextEmail.requestFocus();
            editTextEmail.setError("Enter Email");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.requestFocus();
            editTextEmail.setError("Invalid Email");
        }
        else if (editTextPassword.length()==0)
        {
            editTextPassword.requestFocus();
            editTextPassword.setError("Enter Password");
        }
        else if (editTextPassword.length()<6)
        {
            editTextPassword.requestFocus();
            editTextPassword.setError("Password length must be greater than 6");
        }
        else return true;

        return false;

        }


        private boolean checkEmailPassword(String email, String password) {

        RealmResults<PeopleDetailPojo> modals = realm.where(PeopleDetailPojo.class).findAll();
        for (PeopleDetailPojo modal : modals) {
            if (email.equals(modal.getEmail()) && password.equals(modal.getPassword())) {

                return true;
            }
            else
            {
                Toast.makeText(requireActivity(), "Login Fails", Toast.LENGTH_SHORT).show();
            }
        }
        return  false;
    }

    private void onClickNotRegistered(View root) {
        notRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController=Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_register);
            }
        });
    }

    private void init(View root) {
        realm=Realm.getDefaultInstance();

        notRegister=root.findViewById(R.id.not_register_TV);
        login=root.findViewById(R.id.login_button_TV);
        editTextEmail=root.findViewById(R.id.email_ET);
        editTextPassword=root.findViewById(R.id.pswrd_ET);
        forgotPassword=root.findViewById(R.id.forget_pswrd_TV);
    }
}