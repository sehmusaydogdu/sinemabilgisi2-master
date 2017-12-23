package com.example.ayben.sinema_bilgisi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayben on 24.12.2017.
 */

public class DataBase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION=1;//Database Version
    private static final String DATABASE_NAME="SQLite_Tiyotralar";//Database Name
    private static final String TABLE_NAME="Tiyatrolar";//Table Name

    private static String SEHIR_ID="sehir_id";
    private static String SEHIR_ADI="sehir_adi";
    private static String TİYATRO_ADI="tiyatro_adi";


    public DataBase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("
                +SEHIR_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +SEHIR_ADI+" TEXT NOT NULL,"
                +TİYATRO_ADI+" TEXT NOT NULL "+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    private void sehirSil(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,SEHIR_ID+"= ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void bilgiEkle(String sehirAdi,List<String> bilgiler){

        for (String item:bilgiler){
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(TİYATRO_ADI,item);
            values.put(SEHIR_ADI,sehirAdi);
            db.insert(TABLE_NAME,null,values);
            db.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }



    public List<String> getBursa() {
        List<String> bursalar = new ArrayList<>();
        String gecici = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{SEHIR_ID, SEHIR_ADI, TİYATRO_ADI}, SEHIR_ADI + "=?", new String[]{"Bursa"}, null, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                gecici = cursor.getString(1) + "   " + cursor.getString(2);
                bursalar.add(gecici);
            }
            while (cursor.moveToNext());
        }
        return bursalar;
    }

    public List<String> getIzmir(){
        List<String> izmirler = new ArrayList<>();
        String gecici = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{SEHIR_ID, SEHIR_ADI, TİYATRO_ADI}, SEHIR_ADI + "=?", new String[]{"İzmir"}, null, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                gecici = cursor.getString(1) + "   " + cursor.getString(2);
                izmirler.add(gecici);
            }
            while (cursor.moveToNext());
        }
        return izmirler;
    }


    public List<String> TumBilgiler(){
        List<String> tiyatrolar=new ArrayList<>();
        String selectQuery="SELECT sehir_adi FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                tiyatrolar.add(cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
        return tiyatrolar;
    }

}
