package com.example.shravanram.greenauction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Step1Reg extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Switch s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1_reg);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editText);
        editTextPassword = (EditText) findViewById(R.id.editText2);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        s = (Switch) findViewById(R.id.farmerConsumer);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), ConsumerProfileActivity.class));
        }

    }
    private void registerUser()
    {
        String email=editTextEmail.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering user");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(Step1Reg.this,"Registered successfully",Toast.LENGTH_SHORT ).show();
                            Log.v("hello",password.toString());
                            Intent intent=new Intent(getApplicationContext(),Step2Reg.class);
                            if(s.isChecked()) {
                                intent.putExtra("farmerOrConsumer", "Consumer");

                            }
                            else{
                                intent.putExtra("farmerOrConsumer", "Farmer");
                            }

                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(Step1Reg.this,"Registered unsuccessfully ",Toast.LENGTH_SHORT ).show();

                        }

                    }
                });
    }


    @Override

    public void onClick(View view) {
        if (view== buttonRegister)
        {
            registerUser();
        }
        if(view== textViewSignin)
        {
            progressDialog.dismiss();
            //finish();
            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
            //finished reg?no;
            //i.putExtra("finished","no");
            startActivity(i);
        }

    }
}
