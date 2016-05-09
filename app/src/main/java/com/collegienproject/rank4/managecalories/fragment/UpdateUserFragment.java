package com.collegienproject.rank4.managecalories.fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.dao.UserDao;
import com.collegienproject.rank4.managecalories.sqlite.DatabaseHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUserFragment extends Fragment{

        DatabaseHelper db;
        EditText nameText;
        EditText emailText;
        EditText dateDialog;
        EditText weightNum;
        EditText heightNum;
        RadioGroup sexText;
        Button signupButton;
        Menu menu;


        public UpdateUserFragment() {
            super();
        }

        public static SignupFragment newInstance() {
            SignupFragment fragment = new SignupFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            init(savedInstanceState);

            if (savedInstanceState != null)
                onRestoreInstanceState(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
            initInstances(rootView, savedInstanceState);
            return rootView;
        }

        @SuppressWarnings("UnusedParameters")
        private void init(Bundle savedInstanceState) {
            // Init Fragment level's variable(s) here
        }

        @SuppressWarnings("UnusedParameters")
        private void initInstances(View rootView, Bundle savedInstanceState) {
            // Init 'View' instance(s) with rootView.findViewById here
            // Note: State of variable initialized here could not be saved
            //       in onSavedInstanceState

            nameText = (EditText) rootView.findViewById(R.id.input_name);
            emailText = (EditText) rootView.findViewById(R.id.input_email);
            dateDialog = (EditText) rootView.findViewById(R.id.input_birthdate);
            signupButton = (Button) rootView.findViewById(R.id.btn_signup);
            weightNum = (EditText) rootView.findViewById(R.id.input_weight);
            heightNum = (EditText) rootView.findViewById(R.id.input_height);
            sexText = (RadioGroup) rootView.findViewById(R.id.rgSex);
            menu = (Menu) rootView.findViewById(R.id.miSaveuser);



            dateDialog.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(getActivity(),
                            date,
                            myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH))
                            .show();
                }
            });

            signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    if (!validate()) {
                        onSignupFailed();
                        return;
                    }
                    signupButton.setEnabled(false);

                    final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Updating Account...");
                    progressDialog.show();

                    boolean didItWork = true;
                    try {
                        String userGender="";

                        switch (sexText.getCheckedRadioButtonId()) {
                            case R.id.rbMale:
                                userGender = "male";
                                break;
                            case R.id.rbFemale:
                                userGender = "female";
                                break;
                        }


                        String name = nameText.getText().toString();
                        String email = emailText.getText().toString();
                        String date = dateDialog.getText().toString();
                        String weight = weightNum.getText().toString();
                        String height = heightNum.getText().toString();


                        db = new DatabaseHelper(getActivity());



                        UserDao user = new UserDao();
                        user.setUser_name(name);
                        user.setUser_sex(userGender);
                        user.setUser_email(email);

                        float weightCon = Float.parseFloat(weight);
                        user.setUser_weight(weightCon);

                        float heightCon = Float.parseFloat(height);
                        user.setUser_height(heightCon);

                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date1 = df.parse(date);
                            user.setUser_birthdate(date1);
                        }catch (ParseException e){
                            e.printStackTrace();
                        }

                        db.updateUser(user);
                        db.closeDB();



                    } catch (Exception e) {
                        didItWork = false;
                        Log.e("SignupActivity", "Database didn't upgrade when button pressed");
                    } finally {
                        if (didItWork) {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onSignupSuccess or onSignupFailed
                                            // depending on success
                            onSignupSuccess();
                            // onSignupFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);
                        }

                    }
                }

            });


        }
        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            // Save Instance (Fragment level's variables) State here

        }


        public void onSignupSuccess() {
            signupButton.setEnabled(true);

            Toast.makeText(getContext(), "Account Successfully Updated ", Toast.LENGTH_LONG).show();
        }

        private void onSignupFailed() {
            Toast.makeText(getContext(),
                    "Login failed",
                    Toast.LENGTH_LONG)
                    .show();

            signupButton.setEnabled(true);
        }

        private boolean validate() {
            boolean valid = true;

            String name = nameText.getText().toString();
            String email = emailText.getText().toString();
            String date = dateDialog.getText().toString();
            String weight = weightNum.getText().toString();
            String height = heightNum.getText().toString();

            if (name.isEmpty() || name.length() < 3) {
                nameText.setError("at least 3 characters");
                valid = false;
            } else {
                nameText.setError(null);
            }

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailText.setError("enter a valid email address");
                valid = false;
            } else {
                emailText.setError(null);
            }

            if (date.isEmpty()) {
                dateDialog.setError("enter Birthdate please");
                valid = false;
            } else {
                dateDialog.setError(null);
            }

            float valueW = 0;

            try {
                valueW = Float.parseFloat(weightNum.getText().toString());
            }
            catch(NumberFormatException ex) {
                weightNum.setError("enter weight < 300kg please");
            }

            float valueH = 0;
            try {
                valueH = Float.parseFloat(heightNum.getText().toString());
            }
            catch(NumberFormatException ex) {
                heightNum.setError("enter height < 300cm please");
            }


            if (weight.isEmpty()|| valueW > 300.1f ) {
                weightNum.setError("enter weight < 300kg please");
                valid = false;
            } else {
                weightNum.setError(null);
            }

            if (height.isEmpty()|| valueH > 300.1f ) {
                heightNum.setError("enter height < 300cm please");
                valid = false;
            } else {
                heightNum.setError(null);
            }


            return valid;
        }

        @SuppressWarnings("UnusedParameters")
        private void onRestoreInstanceState(Bundle savedInstanceState) {
            // Restore Instance (Fragment level's variables) State here
        }

        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        String mydate;
        private void updateLabel()

        {

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//mydate = myCalendar.ge
            dateDialog.setText(sdf.format(myCalendar.getTime()));
        }

    }
