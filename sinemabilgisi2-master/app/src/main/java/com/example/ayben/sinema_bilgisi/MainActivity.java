package com.example.ayben.sinema_bilgisi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    private ArrayAdapter<String> adapter;
    private ListView lstView;
    private Button btnIzmir,btnBursa;
    public List<String> liste=new ArrayList<>();
    private static String URL="http://www.devtiyatro.gov.tr/programlar-sehirler-izmir.html";
    private static String URLBursa="http://www.devtiyatro.gov.tr/programlar-sehirler-bursa.html";
    private ProgressDialog progressDialog;
    private String tiklanan="";
    private DataBase dataBase;

    private void init(){
        btnBursa=findViewById(R.id.btnBursa);
        btnIzmir=findViewById(R.id.btnIzmir);
        lstView = findViewById(R.id.list);
        dataBase=new DataBase(this);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, liste);
        init();
        btnBursa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklanan="Bursa";
                new VeriGetirBursa().execute();
                //ListeyiGoster();
                liste.clear();
            }
        });
        btnIzmir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklanan="İzmir";
                new VeriGetir().execute();
               // ListeyiGoster();
                liste.clear();

            }
        });
    }

    private void ListeyiGoster(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(
                this,android.R.layout.simple_list_item_1,liste);
        lstView.setAdapter(arrayAdapter);
    }

    public void onKaydet(View view) {

       if(tiklanan.equals("")){
           Toast.makeText(this, "Şehit Seçiniz", Toast.LENGTH_SHORT).show();
       }
       else
       {
           dataBase.bilgiEkle(tiklanan,liste);
       }

    }

    public void onGoster(View view) {
        Intent intent=new Intent(this,SehirBilgiListesi.class);
        startActivity(intent);
        finish();
    }

    private class VeriGetirBursa extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("TİYATRO");
            progressDialog.setMessage("Lütfen bekleyiniz..");
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc= Jsoup.connect(URLBursa).timeout(30*1000).get();

                Elements oyunadi=doc.select("div[title]");

                for (int i=0;i<oyunadi.size();i++){

                    liste.add(oyunadi.get(i).text());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            lstView.setAdapter(adapter);

        }

    }

    private class VeriGetir extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog= new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("TİYATRO");
            progressDialog.setMessage("Lütfen bekleyiniz..");
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc= Jsoup.connect(URL).timeout(30*1000).get();

                Elements oyunadi=doc.select("div[title]");

                for (int i=0;i<oyunadi.size();i++){

                    liste.add(oyunadi.get(i).text());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            lstView.setAdapter(adapter);

        }
    }
}
