package com.example.superheroes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.example.superheroes.MyHandler.MyDbHandler;
import com.squareup.picasso.Picasso;

public class MainActivity2 extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    Button share,favourite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView = findViewById(R.id.imageView2);
        textView = findViewById(R.id.textView2);
        share = findViewById(R.id.button);
        favourite = findViewById(R.id.button2);
        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("url")).resize(400, 400).into(imageView); ;
        textView.setText("Name:"+intent.getStringExtra("name"));
        textView.append("\nGender:"+intent.getStringExtra("gender"));
        textView.append("\nHeight:"+intent.getStringExtra("height"));
        textView.append("\nWeight:"+intent.getStringExtra("weight"));
        textView.append("\nPower:"+intent.getStringExtra("power"));
        textView.append("\nSpeed:"+intent.getStringExtra("speed"));
        textView.append("\nCombat:"+intent.getStringExtra("combat"));
        textView.append("\nDurability:"+intent.getStringExtra("durability"));
        textView.append("\nIntelligence:"+intent.getStringExtra("intelligence"));
        MyDbHandler db = new MyDbHandler(MainActivity2.this);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });
       favourite.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Data data = new Data();
               Appearance appearance = new Appearance();
               Powerstats powerstats = new Powerstats();
               Images img = new Images();
               img.setSm(intent.getStringExtra("url"));
               data.setId(intent.getIntExtra("id",0));
               data.setName(intent.getStringExtra("name"));
               appearance.setWeight(new String[]{"0",intent.getStringExtra("weight")});
               appearance.setGender(intent.getStringExtra("gender"));
               appearance.setHeight(new String[]{"0",intent.getStringExtra("height")});
               powerstats.setSpeed(intent.getStringExtra("speed"));
               powerstats.setIntelligence(intent.getStringExtra("intelligence"));
               powerstats.setDurability(intent.getStringExtra("durability"));
               powerstats.setCombat(intent.getStringExtra("combat"));
               powerstats.setPower(intent.getStringExtra("power"));
               data.setImages(img);
               data.setAppearance(appearance);
               data.setPowerstats(powerstats);
               db.addFav(data);
           }
       });
    }
    public void shareImage() {
        BitmapDrawable bitmapDrawable =(BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"Heroes","Hello Share");
        Uri bitmapuri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM,bitmapuri);
        startActivity(Intent.createChooser(intent,"Share Image"));
    }
}