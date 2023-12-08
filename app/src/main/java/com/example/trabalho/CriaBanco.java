package com.example.trabalho;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper
{

    public static final String NOME_BANCO = "banco.db";
    public static final String TABELA = "placas";
    public static final String ID= "_id";
    public static final String  MARCA = "marca";
    public static final String MODELO ="modelo";
    public static final String QUANTIDADE ="quantidade";
    private static final int VERSAO = 1;
    //construtor
    public CriaBanco(Context context){
        super (context,NOME_BANCO,null,VERSAO);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

       final String sql ="CREATE TABLE "+TABELA+"("
                + ID + " integer primary key autoincrement, "
                + MARCA + " TEXT, "
                + MODELO + " TEXT, "
                + QUANTIDADE + " TEXT)";

       db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABELA);
        onCreate(db);
    }
}
