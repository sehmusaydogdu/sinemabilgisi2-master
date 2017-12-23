package com.example.ayben.sinema_bilgisi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

public class SehirBilgiListesi extends Activity {

    private ListView lstView;
    private RadioButton radioBursa,radioIzmir;
    private DataBase dataBase;

    private void init(){
        lstView=findViewById(R.id.lstView);
        radioBursa=findViewById(R.id.radioBursa);
        radioIzmir=findViewById(R.id.radiİzmir);
        dataBase=new DataBase(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sehir_bilgi_listesi);
        init();
    }

    public void onKayitlariGoster(View view) {
        if (radioIzmir.isChecked()){
            List<String> gelen=dataBase.getIzmir();
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gelen);
            lstView.setAdapter(adapter);
        }
        else if(radioBursa.isChecked()){
            List<String> gelen=dataBase.getBursa();
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gelen);
            lstView.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(this, "Lütfen seçim yapınız", Toast.LENGTH_SHORT).show();
        }

    }
}
