package com.example.testing3.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing3.BaseActivity;
import com.example.testing3.R;
import com.example.testing3.pojo.PeopleDetailPojo;
import com.example.testing3.realm.RealmOperations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;


public class RegisterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    //for otp
    private FirebaseAuth mAuth;
    EditText mobileNumber , enterOtp;
    TextView sendOtp , resendOtp, verifyOtp;
    ProgressBar numberProgressBar;
    PhoneAuthProvider.ForceResendingToken token;
    String verificatonId;
    boolean verificationInProgress = false;
    Realm realm;


    EditText editTextFName , editTextLName;
    
    //for rest
    TextView email,password,confirmPassword, registerButton;
    EditText editTextEmail, editTextPassword,editTextConfirmPassword;
    boolean isOtpSuccessful = false;
    
    //blood spinner
    Spinner blood;
    TextView dob;
    String[] stringArrayList;
    Context context;
    //for setting date
    private DatePickerDialog datePicker;

    NavController navController;

    String emailIdentity;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        stringArrayList = new String[9];
        stringArrayList=getResources().getStringArray(R.array.blood_names);
        Log.e("RegisterFragment", stringArrayList + "");
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        init(view);

        setToolBarText();

        spinnerWorking();

        dobMethods(view);

        getInstanceMethod();

        onClickSendOtp();

        onClickVerifyOtp();

        onClickRegister();

        return view;
    }

    private void setToolBarText() {
        BaseActivity baseActivity = (BaseActivity)requireActivity();
        baseActivity.onToolbarTextChange(getResources().getString(R.string.register));
    }

    private void spinnerWorking() {

        ArrayAdapter aa = new ArrayAdapter(requireActivity(),R.layout.support_simple_spinner_dropdown_item,stringArrayList);
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        blood.setAdapter(aa);
        blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(requireActivity(),country[position] , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void onClickRegister() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailIdentity = editTextEmail.getText().toString().trim();
                if (checkNullValues() && checkNullValuesAfterOTP()) {

                    if (isOtpSuccessful && !checkEmail(emailIdentity)) {
                        if (editTextPassword.getText().toString().trim().equals(editTextConfirmPassword.getText().toString().trim())) {
                            insertData();
                            Toast.makeText(requireActivity(), "Registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireActivity(), "password is incorrect , enter again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireActivity(), "email id is already used", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                        Log.e("RegisterFragment","nullvalues in onCLickRegister");
                    }
            }
        });
    }

    private boolean checkNullValuesAfterOTP() {
        String email = editTextEmail.getText().toString().trim();
        if (editTextEmail.length()==0)
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
        else if (editTextConfirmPassword.length()==0)
        {
            editTextConfirmPassword.requestFocus();
            editTextConfirmPassword.setError("Enter Confirm Password");
        }
        else if (!editTextPassword.getText().toString().trim().equals(editTextConfirmPassword.getText().toString().trim()))
        {
            editTextConfirmPassword.requestFocus();
            editTextConfirmPassword.setError("Password does not match , Enter again ");
            editTextPassword.clearFocus();
            editTextConfirmPassword.clearFocus();
        }
        else return true;

        return false;    }

    private boolean checkNullValues() {
        if (editTextFName.length()==0)
        {
            editTextFName.requestFocus();
            editTextFName.setError("Enter Name");
        }
        else if (editTextFName.length()<3)
        {
            editTextFName.requestFocus();
            editTextFName.setError("It must contain atleast 3 digits");
        }
        else  if (editTextLName.length()==0)
        {
            editTextLName.requestFocus();
            editTextLName.setError("Enter Name");
        }
        else if (editTextLName.length()<3)
        {
            editTextLName.requestFocus();
            editTextLName.setError("It must contain atleast 3 digits");
        }
        else if (mobileNumber.length()<10)
        {
            mobileNumber.requestFocus();
            mobileNumber.setError("It must contain atleast 10 digits");
        }
//        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
//        {
//            editTextEmail.setError("Invalid Email");
//        }
//        else if (editTextPassword.length()==0)
//        {
//            editTextPassword.setError("Enter Password");
//        }
//        else if (editTextPassword.length()<6)
//        {
//            editTextPassword.setError("Password length must be greater than 6");
//        }
        else return true;
        return false;
    }

    private boolean checkEmail(String email) {

        RealmResults<PeopleDetailPojo> modals = realm.where(PeopleDetailPojo.class).findAll();
        for (PeopleDetailPojo modal : modals) {
            if (email.equals(modal.getEmail()) ) {
//                Intent intent = new Intent(RegisterActivity.this,DisplayActivity.class);
//
//                startActivity(intent);
//                finish();
                return true;
            }
            else
            {
                //Toast.makeText(this, " Fails", Toast.LENGTH_SHORT).show();
            }
        }

        return  false;
    }


    private void onClickVerifyOtp() {
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String getOtp = enterOtp.getText().toString();
                    if (!getOtp.isEmpty() && enterOtp.getText().toString().length() == 6) {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatonId, getOtp);
                        verifyAuth(credential);
                    } else {
                        enterOtp.setError("Valid OTP is required");
                        Log.e("RegisterFragment ", "valid otp is required");
                    }

            }
        });
    }

    //onClick send otp
    private void onClickSendOtp() {
        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNullValues()) {
                    if (!verificationInProgress) {
                        if (!mobileNumber.getText().toString().isEmpty() && mobileNumber.getText().length() == 10) {
                            //country code picker library can also be added
                            String number = "+91" + mobileNumber.getText().toString();
                            Log.e("RegisterFragment ", "Phone Number added : " + number);
                            numberProgressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(requireActivity(), "Sending OTP", Toast.LENGTH_SHORT).show();
                            Log.e("RegisterFragment ", "Sending OTP to : " + number);
                            requesOtp(number);

                        } else {
                            mobileNumber.setError("Phone Number is not valid");
                            Log.e("RegisterFragment ", "Phone Number is not valid");
                        }
                    } else {
                        verifyOtp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String getOtp = enterOtp.getText().toString();
                                if (!getOtp.isEmpty() && enterOtp.getText().toString().length() == 6) {
                                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatonId, getOtp);
                                    verifyAuth(credential);
                                } else {
                                    enterOtp.setError("Valid OTP is required");
                                    Log.e("RegisterFragment ", "valid otp is required");
                                }
                            }
                        });

                    }
                }
                else
                {
                    Log.e("RegisterFragment","nullvalues in getOtp");
                }
            }
        });
    }

    //verify otp
    private void verifyAuth(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if otp is authenticated
                if (task.isSuccessful())
                {
                    setRestDataVisible();
                    isOtpSuccessful = true;
                    Toast.makeText(requireActivity(), "Authentication is successful", Toast.LENGTH_SHORT).show();
                    Log.e("RegisterFragment ", "Authentication is successful");

                }
                else
                {
                    isOtpSuccessful = false;
                    Toast.makeText(requireActivity(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                    Log.e("RegisterFragment ", "Authentication Failed");
                }
            }
        });
    }

//    setting rest of data visisble after verifying otp
    private void setRestDataVisible() {
        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        confirmPassword.setVisibility(View.VISIBLE);
        editTextEmail.setVisibility(View.VISIBLE);
        editTextPassword.setVisibility(View.VISIBLE);
        editTextConfirmPassword.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
    }

    private void requesOtp(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60L, TimeUnit.SECONDS, requireActivity(), new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                numberProgressBar.setVisibility(View.INVISIBLE);
                verificatonId=s;
                token=forceResendingToken;
                enterOtp.setVisibility(View.VISIBLE);
                verifyOtp.setVisibility(View.VISIBLE);
                verificationInProgress=true;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(requireContext()," Failed : "+e.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("RegisterFragment "," Failed : "+e.getMessage());
            }
        });
    }

    private void getInstanceMethod() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void dobMethods(View view) {
        openDataPicker(view);

        setDateOfBirth();

        dob.setText(todaysDate());
    }

    private void init(View view) {

        realm=Realm.getDefaultInstance();
        //editText
        mobileNumber = view.findViewById(R.id.mobile_ET);
        enterOtp=view.findViewById(R.id.enter_otp_ET);
        editTextEmail=view.findViewById(R.id.email_register_ET);
        editTextPassword=view.findViewById(R.id.password_register_ET);
        editTextConfirmPassword=view.findViewById(R.id.confirm_password_ET);
        //spinner
        blood = view.findViewById(R.id.list_blood_Sp);
        //textView
        dob=view.findViewById(R.id.set_dob_TV);
        email=view.findViewById(R.id.email_TV);
        password=view.findViewById(R.id.password_register_TV);
        confirmPassword=view.findViewById(R.id.confirm_password_TV);
        //progressBar
        numberProgressBar=view.findViewById(R.id.progress_bar);
        //textView as button
        registerButton=view.findViewById(R.id.register_click_TV);
        sendOtp=view.findViewById(R.id.send_otp_TV);
        resendOtp=view.findViewById(R.id.resend_otp_TV);
        verifyOtp=view.findViewById(R.id.verify_otp_TV);

        editTextFName = view.findViewById(R.id.first_name_ET);
        editTextLName = view.findViewById(R.id.last_name_ET);

        //set data visisbility gone before verifying otp
        setDataGone();



//use EditText.setFocusable(false) to disable editing
//EditText.setFocusableInTouchMode(true) to enable editing;
    }

    //set data visisbility gone before verifying otp
    private void setDataGone() {
        numberProgressBar.setVisibility(View.GONE);
        enterOtp.setVisibility(View.GONE);
        verifyOtp.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        confirmPassword.setVisibility(View.GONE);
        editTextEmail.setVisibility(View.GONE);
        editTextPassword.setVisibility(View.GONE);
        editTextConfirmPassword.setVisibility(View.GONE);
        registerButton.setVisibility(View.GONE);
    }

    //getting dob
    private void setDateOfBirth() {
        DatePickerDialog.OnDateSetListener dateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDataString(day,month,year);
                dob.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePicker = new DatePickerDialog(requireActivity(),style,dateSetListner,day,month,year);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    //returning dob
    private String makeDataString(int day, int month, int year) {
        return day + " "+getMonthFormat(month) + " "+year;
    }

//    getting month in string
    private String getMonthFormat(int month) {
        switch (month)
        {
            case 1 : return "JAN";
            case 2 : return "FEB";
            case 3 : return "MAR";
            case 4 : return "APR";
            case 5 : return "MAY";
            case 6 : return "JUN";
            case 7 : return "JUL";
            case 8 : return "AUG";
            case 9 : return "SEP";
            case 10 : return "OCT";
            case 11 : return "NOV";
            case 12 : return "DEC";
        }

        return "JAN";
    }

//    onClick dob
    private void openDataPicker(View view)
    {
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });

    }

//    getting todays date
    private String todaysDate()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month=month+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return  makeDataString(day,month,year);
    }

    private void insertData() {
//        realm.beginTransaction();
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

        // modal.setSno(3);
        modal.setName(editTextFName.getText().toString().trim());
        modal.setLname(editTextLName.getText().toString().trim());
        modal.setmob(mobileNumber.getText().toString().trim());
        modal.setBloodGroup(blood.getSelectedItem().toString().trim());
        modal.setDob(dob.getText().toString().trim());
        modal.setEmail(editTextEmail.getText().toString().trim());
        modal.setPassword(editTextPassword.getText().toString().trim());
        RealmOperations realmOperations= new RealmOperations();
        realmOperations.saveListinDb(modal);
//        realm.insert(modal);
//        realm.commitTransaction();



//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealm(modal);
//            }
//        });
//        realm.commitTransaction();
//        Intent intent = new Intent(RegisterActivity.this, DisplayActivity.class);
        Toast.makeText(requireActivity(), "Registered", Toast.LENGTH_SHORT).show();
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        navController.navigate(R.id.navigation_home);

//        startActivity(intent);
//        requireActivity().finish();



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}