package com.example.superheroes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.superheroes.MyHandler.MyDbHandler;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NavigationView nav;
    SearchView searchbyid;
    SearchView searchbyname;
    List<Data> data;
    ActionBarDrawerToggle toggle;
    DrawerLayout dl;
    Adapter adapter;
    int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl = (DrawerLayout)findViewById(R.id.dl);
        recyclerView = findViewById(R.id.recycleview);
        searchbyid = findViewById(R.id.searchbyid);
        searchbyname =findViewById(R.id.searchbyname);
        recyclerView.hasFixedSize();
        toggle = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        toggle.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        datafetch();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.all) {
                    Log.d("vipulcheck","1");
                    check=0;
                    data.clear();
                    adapter= new Adapter(getApplicationContext(),data);
                    recyclerView.setAdapter(adapter);
                    dl.closeDrawer(GravityCompat.START);
                    datafetch();
                }
                if(id==R.id.male){
                   check=1;
                   data.clear();
                    adapter= new Adapter(getApplicationContext(),data);
                    recyclerView.setAdapter(adapter);
                    dl.closeDrawer(GravityCompat.START);
                    datafetch();
                }
                if(id==R.id.female) {
                    check=2;
                    data.clear();
                    adapter= new Adapter(getApplicationContext(),data);
                    recyclerView.setAdapter(adapter);
                    dl.closeDrawer(GravityCompat.START);
                    datafetch();
                }
                if(id==R.id.fav) {
                    data.clear();
                    adapter= new Adapter(getApplicationContext(),data);
                    recyclerView.setAdapter(adapter);
                    dl.closeDrawer(GravityCompat.START);
                    createfav();
                }
                return true;
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        searchbyid.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int s =Integer.parseInt(query);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter =new Adapter(getApplicationContext(),null);
                recyclerView.setAdapter(adapter);
                List<Data> data3 =new ArrayList<>();
                int x=0;
                for(int i=0;i<data.size();i++)
                {
                    Data d=data.get(i);
                    if(s==d.getId())
                    {
                        data3.add(d);
                        x++;
                    }
                }
                if(x==0)
                    Toast.makeText(MainActivity.this, "No Data found!", Toast.LENGTH_SHORT).show();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter =new Adapter(getApplicationContext(),data3);
                recyclerView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchbyid.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter =new Adapter(getApplicationContext(),data);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });
        searchbyname.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter =new Adapter(getApplicationContext(),null);
                recyclerView.setAdapter(adapter);
                List<Data> data3 =new ArrayList<>();
                int x=0;
                for(int i=0;i<data.size();i++)
                {
                    Data d=data.get(i);
                    if(d.getName().contains(query))
                    {
                        data3.add(d);
                        x++;
                    }
                }
                if(x==0)
                    Toast.makeText(MainActivity.this, "No Data found!", Toast.LENGTH_SHORT).show();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter =new Adapter(getApplicationContext(),data3);
                recyclerView.setAdapter(adapter);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return toggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }

    public void datafetch()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Data>> call = jsonPlaceHolderApi.getData();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
             if(!response.isSuccessful()){
                 Log.d("apidata",""+response.code());
                 return;
             }
             if(check==0) {
                 List<Data> d =response.body();
                 data = new ArrayList<>();
               data=d;
             }
             if(check==1)
             {
                 List<Data> d =response.body();
                 data = new ArrayList<>();
                 for(int i=0;i<d.size();i++)
                 {
                     if (d.get(i).getApperance().getGender().equals("Male"))
                         data.add(d.get(i));
                 }
             }
                if(check==2)
                {
                    List<Data> d =response.body();
                    data = new ArrayList<>();
                    for(int i=0;i<d.size();i++)
                    {
                        if (d.get(i).getApperance().getGender().equals("Female"))
                            data.add(d.get(i));
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter =new Adapter(getApplicationContext(),data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.d("apidata", t.getMessage());
            }
        });
    }
    void createfav()
    {
        MyDbHandler db = new MyDbHandler(MainActivity.this);
        List<Data> favdata = db.getalldata();
        for(Data d:favdata)
        {
            data.add(d);
           // Log.d("vipul",""+d.getPowerstats().getPower());
        }
       recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter =new Adapter(getApplicationContext(),data);
       recyclerView.setAdapter(adapter);
    }

}