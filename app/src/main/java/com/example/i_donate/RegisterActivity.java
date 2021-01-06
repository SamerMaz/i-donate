package com.example.i_donate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private Button btnREGISTER;
    private TextView txtGender, txtBloodType;
    private EditText edtTxtFullName, edtEmail, edtTxtPass, edtTxtPassRepeat;
    private RadioGroup rgGender;
    private Spinner bloodSpinner;

    private FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;



    private TextView txtLinkLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        initViews();


        btnREGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initRegister();

                final String name = edtTxtFullName.getText().toString();
                final String email = edtEmail.getText().toString().trim();
                final String password = edtTxtPass.getText().toString().trim();
                final String passRepeat = edtTxtPassRepeat.getText().toString().trim();
                final String spinner = bloodSpinner.getSelectedItem().toString();



                if (TextUtils.isEmpty(name)){
                    edtTxtFullName.setError("Name is Required");
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    edtEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    edtTxtPass.setError("Password is required");
                    return;
                }
                if (password.length()<6){
                    edtTxtPass.setError("Password Must be ≥ 6 Character");
                }

                if (TextUtils.isEmpty(passRepeat)){
                    edtTxtPassRepeat.setError("Confirm Password is required");
                    return;
                }

                if (!edtTxtPass.getText().toString().equals(edtTxtPassRepeat.getText().toString())){
                    edtTxtPassRepeat.setError("password doesn't match");
                    return;
                }



                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            userId = fAuth.getCurrentUser().getUid();  //saving the userId of the currentUser
                            DocumentReference documentReference = fStore.collection("users").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName", name);
                            user.put("email", email);
                            user.put("password", password);
                            user.put("password_repeat", passRepeat);
                            String gender = "";
                            switch(rgGender.getCheckedRadioButtonId()) {
                                case R.id.rbMale:
                                    user.put("gender", "male");
                                    //gender = "Male";
                                    break;
                                case R.id.rbFemale:
                                    user.put("gender", "female");
                                    //gender = "Female";
                                    break;
                                default:
                                    break;
                            }
                            user.put("Blood Type", spinner);




                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: uer Profile is created for " + userId);
                                }
                            });


                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        txtLinkLogin.setMovementMethod(LinkMovementMethod.getInstance());
        txtLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    private void initViews() {
        btnREGISTER = findViewById(R.id.btnREGISTER);

        txtGender = findViewById(R.id.txtGender);
        txtBloodType= findViewById(R.id.txtBloodType);

        edtTxtFullName =findViewById(R.id.edtTxtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtTxtPass = findViewById(R.id.edttxtPass);
        edtTxtPassRepeat = findViewById(R.id.edtTxtPassRepeat);



        rgGender = findViewById(R.id.rgGender);
        bloodSpinner = findViewById(R.id.spinner);
        txtLinkLogin=findViewById(R.id.txtLinkLogin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();



        ArrayAdapter<CharSequence> bloodAdapter =  ArrayAdapter.createFromResource(RegisterActivity.this, R.array.bloodType, android.R.layout.simple_spinner_item);
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodSpinner.setAdapter(bloodAdapter);


        bloodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(RegisterActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }




}