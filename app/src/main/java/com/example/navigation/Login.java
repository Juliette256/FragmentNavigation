package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class Login extends AppCompatActivity {

    //    private InputValidation inputValidation;
    private DatabaseAdapter databaseAdapter;
    private User user;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login=findViewById(R.id.btn_login);
        TextView SignUpHere=findViewById(R.id.signUpHere);

        final EditText editTextEmail=findViewById(R.id.emailAddress);
        final EditText editTextPassword=findViewById(R.id.password);

        databaseAdapter = new DatabaseAdapter(this);
        user = new User();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.emailAddress, Patterns.EMAIL_ADDRESS, R.string.errorMessage1);
        awesomeValidation.addValidation(this,R.id.password,".{6,}", R.string.errorMessage1);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    String email= editTextEmail.getText().toString().trim();
                    String password=editTextPassword.getText().toString().trim();

                    if(databaseAdapter.CheckUserForLogin(email, password)){

                        Intent intent=new Intent(Login.this, MainActivity.class);
                        intent.putExtra("Email", email);
                        startActivity(intent);
                        editTextEmail.setText(null);
                        editTextPassword.setText(null);}
                }}
        });

        SignUpHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Login.this, SignUp.class);
                editTextEmail.setText(null);
                editTextPassword.setText(null);
                startActivity(intent);
            }
        });
    }
}