package com.example.marveluniverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Character_details extends AppCompatActivity {

    private AppCompatImageView back;
    private TextView ctitle;
    private ImageView cimage;
    private TextView cname;
    private TextView cid;
    private TextView cmodified;
    private TextView cresourceURI;
    private TextView cdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        back = (AppCompatImageView) findViewById(R.id.back);
        ctitle = (TextView) findViewById(R.id.ctitle);
        cimage = (ImageView) findViewById(R.id.cimage);
        cname = (TextView) findViewById(R.id.cname);
        cid = (TextView) findViewById(R.id.cid);
        cmodified = (TextView) findViewById(R.id.modified);
        cresourceURI = (TextView) findViewById(R.id.resource);
        cdescription = (TextView) findViewById(R.id.description);


        Intent intent = getIntent();

        String id = intent.getStringExtra("key_id");
        String name = intent.getStringExtra("ket_name");
        String url = intent.getStringExtra("key_url");
        String description = intent.getStringExtra("key_description");
        String modified = intent.getStringExtra("key_modified");
        String resourceURI = intent.getStringExtra("key_resourceUri");

        Picasso.get().load(url).into(cimage);
        cid.setText("ID : " + id);
        cname.setText("Name  : " + name);
        ctitle.setText(name);
        cmodified.setText("Modified : " + modified);
        cresourceURI.setText("resourceURI : " + resourceURI);
        cdescription.setText("Description : " + description);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);

                startActivity(intent1);
                finish();
            }
        });



    }
}