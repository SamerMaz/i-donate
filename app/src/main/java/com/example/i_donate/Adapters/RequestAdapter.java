package com.example.i_donate.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.i_donate.DataModels.RequestDataModel;
import com.example.i_donate.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;




public class RequestAdapter extends FirestoreRecyclerAdapter<RequestDataModel, RequestAdapter.ViewHolder>{


   Context context;
    public RequestAdapter(@NonNull FirestoreRecyclerOptions<RequestDataModel> options,Context context ) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, final int i, @NonNull final RequestDataModel model) {
        holder.message.setText(model.getDetails());
        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + model.getPhone()));
                context.startActivity(intent);
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Need blood donation for:\n"+
                        model.getDetails()+ "\nContact: " + model.getPhone());
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Hey could you help here");
                context.startActivity(Intent.createChooser(shareIntent, "Share..."));


            }
        });

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView message;
        ImageView imageView, callButton, shareButton;
        EditText phone;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            imageView = itemView.findViewById(R.id.image);
            callButton = itemView.findViewById(R.id.call_button);
            shareButton = itemView.findViewById(R.id.share_button);
            phone = itemView.findViewById(R.id.phone);


        }

    }
}














