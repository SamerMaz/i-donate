package com.example.i_donate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.i_donate.Adapters.RequestAdapter;
import com.example.i_donate.DataModels.RequestDataModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomePageFragment extends Fragment {


    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference patient_ref = fStore.collection("patients");
    
    private RequestAdapter rqstAdapter;

    


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.homefragments, container, false);



        TextView makeRequest = view.findViewById(R.id.makeRequest);
        makeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), MakeRequest.class));
            }
        });


        setUpRecyclerView();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(rqstAdapter);
        rqstAdapter.notifyDataSetChanged();


         return view;
    }

    private void setUpRecyclerView() {
        Query query = patient_ref.orderBy("details", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<RequestDataModel> options = new FirestoreRecyclerOptions.Builder<RequestDataModel>()
                .setQuery(query, RequestDataModel.class)
                .build();
        rqstAdapter = new RequestAdapter(options,getContext());



    }



    @Override
    public void onStart() {
        super.onStart();
        rqstAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (rqstAdapter != null){
            rqstAdapter.stopListening();
        }
    }
}


