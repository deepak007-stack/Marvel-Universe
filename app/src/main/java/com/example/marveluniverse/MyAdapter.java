package com.example.marveluniverse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.viewholder> {

    Context context;
    ArrayList<GetSet> arrayList;

    public MyAdapter(Context context, ArrayList<GetSet> getSet) {
        this.context = context;
        this.arrayList = getSet;
    }

    public void setSearchData(ArrayList<GetSet> filter_list){
        arrayList = filter_list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_view, parent, false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        GetSet data = arrayList.get(position);

        String url = data.getUrl();

        Picasso.get().load(url).into(holder.img);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(view.getContext(), Character_details.class);

                intent.putExtra("key_id",data.getId());
                intent.putExtra("ket_name",data.getName());
                intent.putExtra("key_url",data.getUrl());
                intent.putExtra("key_description",data.getDescription());
                intent.putExtra("key_modified",data.getModified());
                intent.putExtra("key_resourceUri",data.getResourceURI());

                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private ImageView img;
        private LinearLayout linearLayout;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout);


        }
    }
}
