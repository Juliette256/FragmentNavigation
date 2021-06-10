package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private DatabaseAdapter databaseAdapter;
    private User user;
    EditText edtFirstName, edtLastName, edtEmailAddress,edtCreatePassword, edtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button btnSignUp = findViewById(R.id.btn_signUp);
        TextView textView = findViewById(R.id.backToLogin);

        edtFirstName=findViewById(R.id.firstName);
        edtLastName=findViewById(R.id.lastName);
        edtEmailAddress=findViewById(R.id.create_emailAddress);
        edtCreatePassword=findViewById(R.id.create_password);
        edtConfirmPassword=findViewById(R.id.confirm_password);

        databaseAdapter = new DatabaseAdapter(this);
        user = new User();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ValidateData()){
                    String password=edtCreatePassword.getText().toString().trim();
                    String confirmedPassword=edtConfirmPassword.getText().toString().trim();
                    String first_Name=edtFirstName.getText().toString().trim();
                    String last_Name=edtLastName.getText().toString().trim();
                    String email= edtEmailAddress.getText().toString().trim();


                    user.setFirstName(first_Name);
                    user.setLastName(last_Name);
                    user.setEmail(email);
                    user.setPass(confirmedPassword);

                    if(!databaseAdapter.CheckUserForSignUp(email)){
                        databaseAdapter.Register(user);
                        Toast.makeText(getApplicationContext(), "Successful Registration" , Toast.LENGTH_LONG).show();

                        Intent intent= new Intent(SignUp.this, Login.class);
                        edtFirstName.setText(null);
                        edtLastName.setText(null);
                        edtEmailAddress.setText(null);
                        edtCreatePassword.setText(null);
                        edtConfirmPassword.setText(null);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Email Address already registered" , Toast.LENGTH_LONG).show();
                    }
                }}
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp.this, Login.class);
                edtFirstName.setText(null);
                edtLastName.setText(null);
                edtEmailAddress.setText(null);
                edtCreatePassword.setText(null);
                edtConfirmPassword.setText(null);

                startActivity(intent);
            }
        });
    }
    public boolean ValidateData(){
        boolean check=true;
        if(edtFirstName.getText().toString().trim()==null || edtFirstName.getText().toString().trim().isEmpty() ) {
            check=false;
            edtFirstName.setError("Can not be empty");}

        else if(edtLastName.getText().toString().trim()==null || edtLastName.getText().toString().trim().isEmpty()){
            check=false;
            edtLastName.setError("Can not be empty"); }

        else if(edtEmailAddress.getText().toString().trim()==null || edtEmailAddress.getText().toString().trim().isEmpty()){
            check=false;
            edtEmailAddress.setError("Can not be empty"); }

        else if(!Patterns.EMAIL_ADDRESS.matcher(edtEmailAddress.getText().toString()).matches()){
            check=false;
            edtEmailAddress.setError("Invalid email address");
        }

        else if(edtCreatePassword.getText().toString().trim()==null || edtCreatePassword.getText().toString().trim().isEmpty()){
            check=false;
            edtCreatePassword.setError("Can not be empty"); }

        else if(edtCreatePassword.getText().toString().length()<6){
            check=false;
            edtCreatePassword.setError("Enter password up to 6 characters"); }

        else if(edtConfirmPassword.getText().toString().trim()==null || edtConfirmPassword.getText().toString().trim().isEmpty()){
            check=false;
            edtConfirmPassword.setError("Can't be empty"); }

        else if (!edtConfirmPassword.getText().toString().trim().equals(edtCreatePassword.getText().toString().trim())){
            check =false;
            edtConfirmPassword.setError("Passwords do not match"); }

        return check;
    }

    @Override
    public void onBackPressed() {
    }
}

