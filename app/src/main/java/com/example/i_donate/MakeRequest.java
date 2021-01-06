package com.example.i_donate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MakeRequest extends AppCompatActivity {

    EditText messageText;
    Button btnSubmit;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    EditText phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);
        messageText= findViewById(R.id.messageText);

        fStore = FirebaseFirestore.getInstance();
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);
        phone = findViewById(R.id.phone);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()){
                    String message = messageText.getText().toString();
                    String phoneNum = phone.getText().toString().trim();
                    progressBar.setVisibility(View.VISIBLE);

                    DocumentReference docRef = fStore.collection("patients").document();
                    Map<String,Object> patient = new HashMap<>();
                    patient.put("details", message);
                    patient.put("phone", phoneNum);

                    docRef.set(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MakeRequest.this, "Request Added", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(MakeRequest.this, Testactivity.class));
                            onBackPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MakeRequest.this, "Error, Try again", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });

                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);


                }
            }
        });

    }

    private boolean isValid(){
        if (messageText.getText().toString().isEmpty()){
            showMessage("Request message should not be empty");
            return false;
        }else if (phone.getText().toString().isEmpty()){
            showMessage("phone number should not be empty");
            return false;
        }
        return true;
    }

    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}