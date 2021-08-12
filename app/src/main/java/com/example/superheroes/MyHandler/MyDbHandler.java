package com.example.superheroes.MyHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.superheroes.Appearance;
import com.example.superheroes.Data;
import com.example.superheroes.Images;
import com.example.superheroes.Params.params;
import com.example.superheroes.Powerstats;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
   public MyDbHandler(Context context)
    {
        super(context, params.DB_NAME,null,params.DB_VERSION);
        Log.d("vipuldb","Sucess Database");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE "+params.TABLE_NAME + "(" + params.KEY_ID + " INTEGER PRIMARY KEY," + params.KEY_NAME + " TEXT," + params.KEY_URL + " TEXT," + params.KEY_GENDER + " TEXT," + params.KEY_Height + " TEXT," + params.KEY_WEIGHT + " TEXT," + params.KEY_POWER + " TEXT," + params.KEY_COMBAT + " TEXT," + params.KEY_DURABILITY + " TEXT," + params.KEY_SPEED + " TEXT," + params.KEY_INTELLIGENCE + " TEXT)";
        db.execSQL(create);
        Log.d("vipuldb","Sucessful"+create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addFav(Data data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
          values.put(params.KEY_ID,data.getId());
           values.put(params.KEY_NAME,data.getName());
           values.put(params.KEY_URL,data.getImages().getSm());
           values.put(params.KEY_GENDER,data.getAppearance().getGender());
           values.put(params.KEY_Height,data.getAppearance().getHeight()[1]);
           values.put(params.KEY_WEIGHT,data.getAppearance().getWeight()[1]);
            values.put(params.KEY_POWER,data.getPowerstats().getPower());
           values.put(params.KEY_SPEED,data.getPowerstats().getSpeed());
           values.put(params.KEY_INTELLIGENCE,data.getPowerstats().getIntelligence());
           values.put(params.KEY_DURABILITY,data.getPowerstats().getDurability());
           values.put(params.KEY_COMBAT,data.getPowerstats().getCombat());
         db.insert(params.TABLE_NAME,null,values);
        Log.d("vipuldb","Sucessfully Added"+data.getPowerstats().getPower());
         db.close();
    }
    public List<Data> getalldata(){
       List<Data> favlist = new ArrayList<>();
       SQLiteDatabase db = this.getReadableDatabase();
       String select = "SELECT * FROM "+params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst())
        {
            do{
                Data data = new Data();
                Images images=new Images();
                Appearance appearance = new Appearance();
                Powerstats powerstats = new Powerstats();
                data.setId(cursor.getInt(0));
                data.setName(cursor.getString(1));
                images.setSm(cursor.getString(2));
                appearance.setGender(cursor.getString(3));
                appearance.setHeight(new String[]{"0",cursor.getString(4)});
                appearance.setWeight(new String[]{"0",cursor.getString(5)});
                powerstats.setPower(cursor.getString(6));
                powerstats.setCombat(cursor.getString(7));
                powerstats.setDurability(cursor.getString(8));
                powerstats.setSpeed(cursor.getString(9));
                powerstats.setIntelligence(cursor.getString(10));
                Log.d("vipult",""+cursor.getString(6));
                data.setPowerstats(powerstats);
                data.setAppearance(appearance);
                data.setImages(images);
                favlist.add(data);
            }while(cursor.moveToNext());
        }
        return favlist;
    }
    public void delete(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(params.TABLE_NAME,params.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

}
