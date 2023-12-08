package com.example.trabalho;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //meus comandos
        Button botao = (Button) findViewById(R.id.btncadastrar);
        botao.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                BancoControle obj = new BancoControle(getBaseContext());
                EditText marca = (EditText)findViewById(R.id.txtmarca);
                EditText modelo = (EditText)findViewById(R.id.txtmodelo);
                EditText quantidade = (EditText)findViewById(R.id.txtquantidade);

                String marcastring= marca.getText().toString();
                String modelostring= modelo.getText().toString();
                String quantidadestring= quantidade.getText().toString();
                String resultado;

                resultado = obj.Inseredados(marcastring,modelostring,quantidadestring);
                Toast.makeText(getApplicationContext(),resultado,Toast.LENGTH_LONG).show();


            }
        });
    }
    public void Nova(View view){
        setContentView(R.layout.test);
        BancoControle obj = new BancoControle(getBaseContext());
         cursor = obj.Carregadb();

        String[] nomecampos = new String[]{
                CriaBanco.ID,CriaBanco.MARCA
        };
        int[] idViews =  new int[]{R.id.id,R.id.marca};
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.test,cursor,nomecampos,idViews, 0);
                lista = (ListView)findViewById(R.id.listView);
                lista.setAdapter(adaptador);

                //seleciona item da lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ID));//seleciona id de obj na base de dados
                setContentView(R.layout.tela_altera_deleta);//chama layout de alterar e deletar
                //exibe id selecionado
                Toast.makeText(getApplicationContext(),codigo,Toast.LENGTH_SHORT).show();
                 //consulta banco com id usando o where
                final int icodigo = Integer.parseInt(codigo);//converte a variavel para int
                BancoControle obj = new BancoControle(getBaseContext());
                EditText marca = (EditText) findViewById(R.id.altmarca);
                EditText modelo = (EditText) findViewById(R.id.altmodelo);
                EditText quantidade = (EditText) findViewById(R.id.altquantidade);
                cursor=obj.CarregaDadoById(icodigo);// chama o metodo que faz o filtro na base de dados
                // seta dados carregado da base de dados nos obj edittext
                marca.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.MARCA)));
                modelo.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.MODELO)));
                quantidade.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.QUANTIDADE)));
                Button botaoalterar = (Button) findViewById(R.id.btnalterar);
                Button botaoapagar = (Button) findViewById(R.id.btnapagar);
                
                //botão apaga dados
                botaoapagar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //alerta para confirmar a exclusão
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Apagar")
                                .setMessage("Deseja apagar o registro")
                                .setPositiveButton(R.string.sim,    new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog,int
                                            which) {
                                        //apaga dado
                                        BancoControle obj = new BancoControle(getBaseContext());
                                        obj.ApagaDado(icodigo);
                                        String resultado = "Removido com Sucesso!";
                                        Toast.makeText(getApplicationContext(),resultado,Toast.LENGTH_SHORT).show();
                                        //navega para outra  pagina
                                        Intent intent= new Intent(MainActivity.this,MainActivity.class);
                                        startActivity(intent);
                                                    }
                                        })   .setNegativeButton(R.string.nao,   new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int
                                    which) {
                                dialog.dismiss();
                            }
                        })
                            .show();



                    }
                });
                //click botão alterar dados
                botaoalterar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //alerta para confirmar a alteração
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Alterar")
                                .setMessage("Deseja editar o registro")
                                .setPositiveButton(R.string.sim,    new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog,int
                                            which) {
                                        //alterar dado
                                        BancoControle obj = new BancoControle(getBaseContext());
                                        String txtmarca =  ((EditText) findViewById(R.id.altmarca)).getText().toString();
                                        String txtmodelo = ((EditText) findViewById(R.id.altmodelo)).getText().toString();
                                        String txtquantidade = ((EditText) findViewById(R.id.altquantidade)).getText().toString();
                                        obj.AlteraRegistro(icodigo,txtmarca,txtmodelo,txtquantidade);
                                        //navega entre paginas//
                                        Intent intent= new Intent(MainActivity.this,MainActivity.class);
                                        startActivity(intent);


                                    }
                                })   .setNegativeButton(R.string.nao,   new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int
                                    which) {
                                dialog.dismiss();
                                Intent intent= new Intent(MainActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        })
                                .show();

                    }
                });

            }
        });


    }

}