package com.example.trabalho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

//classe de manipular a base de dados
public class BancoControle {
    private SQLiteDatabase db;
    private CriaBanco banco;
    //construtor
    public BancoControle(Context context){
        banco = new CriaBanco(context);
    }

    //adiciona dados a tabela da base de dados
    public String Inseredados(String mar,String mode,String quanti){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.MARCA, mar);
        valores.put(CriaBanco.MODELO, mode);
        valores.put(CriaBanco.QUANTIDADE, quanti);
        resultado = db.insert(CriaBanco.TABELA,null, valores);
        db.close();

        if(resultado==-1){
            return "Erro ao inserir registro!";
        }
        else{
            return "Sucesso ao inserir dados!";
        }
    }
    //carrega toda a base de dados
    public Cursor Carregadb(){
        Cursor cursor;
        String[] campos = {banco.ID,banco.MARCA};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA,campos,null,null,null,null,null,null);
            if(cursor!=null){
                cursor.moveToFirst();
            }
            db.close();
            return cursor;

    }
    //carrega  dados por id na base de dados
    public Cursor CarregaDadoById(int id){
        Cursor cursor;
        String[]campos = {banco.ID,banco.MARCA,banco.MODELO,banco.QUANTIDADE};
        String where = CriaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.TABELA,campos,where,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return  cursor;
    }
    //atualiza dados na base de dados
    public void AlteraRegistro(int id, String marca, String modelo, String quantidade){
        ContentValues valores;
        String where;
        db= banco.getWritableDatabase();
        where = CriaBanco.ID + "=" + id;
        valores = new ContentValues();
        valores.put(CriaBanco.MARCA,marca);
        valores.put(CriaBanco.MODELO,modelo);
        valores.put(CriaBanco.QUANTIDADE,quantidade);
        db.update(CriaBanco.TABELA,valores,where,null);
        db.close();
    }

    //apaga dados na base de dados
    public void ApagaDado(int id){
        String where = CriaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.TABELA,where,null);
        db.close();
    }
}
