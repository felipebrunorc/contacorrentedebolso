package br.estacio.fic.pdm.contacorrente.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.estacio.fic.pdm.contacorrente.Entidade.ContaCorrente;

/**
 * Created by Felipe Bruno on 16/06/2017.
 */

public class ContaCorrenteDAO {

    List<ContaCorrente> listaContaCorrente = new ArrayList<>();

    private SQLiteDatabase db;
    private BancoDados bancoDados;

    public ContaCorrenteDAO(Context context) {
        bancoDados = new BancoDados(context);
    }

    public boolean insert(ContaCorrente mycontacorrente) {
        db = bancoDados.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(bancoDados.CATEGORIA, mycontacorrente.getCategoria());
        valores.put(bancoDados.SUBCATEGORIA, mycontacorrente.getSubcategoria());
        valores.put(bancoDados.DESCRICAO, mycontacorrente.getDescricao());
        valores.put(bancoDados.VALOR, mycontacorrente.getValor());
        valores.put(bancoDados.DATA, mycontacorrente.getData());

        long result = db.insert(bancoDados.TABELA, null, valores);
        db.close();

        return result == -1 ? false : true;
    }

    public List<ContaCorrente> selectAll() {
        Cursor cursor;

        String[] campos = {bancoDados.ID, bancoDados.CATEGORIA, bancoDados.SUBCATEGORIA,
                bancoDados.DESCRICAO, bancoDados.VALOR, bancoDados.DATA};

        db = bancoDados.getReadableDatabase();

        cursor = db.query(bancoDados.TABELA, campos, null, null, null, null, "data DESC");



        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                ContaCorrente mycontacorrente = new ContaCorrente();
                mycontacorrente.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(BancoDados.CATEGORIA)));
                mycontacorrente.setSubcategoria(cursor.getString(cursor.getColumnIndexOrThrow(BancoDados.SUBCATEGORIA)));
                mycontacorrente.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(BancoDados.DESCRICAO)));
                mycontacorrente.setData(cursor.getString(cursor.getColumnIndexOrThrow(BancoDados.DATA)));
                mycontacorrente.setValor(cursor.getFloat(cursor.getColumnIndexOrThrow(BancoDados.VALOR)));
                listaContaCorrente.add(mycontacorrente);
            } while (cursor.moveToNext());
        }

        return listaContaCorrente;
    }

    public float selecioneTotalContaCorrente(String categoria) {
        String query = "SELECT SUM(valor) FROM appcontacorrente WHERE categoria = ?;";
        db = bancoDados.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{categoria});

        float total = 0;

        if (cursor.moveToFirst())
            total = cursor.getFloat(0);

        return total;
    }

    public Cursor carregaDadoById(int id){
        Cursor cursor;
        String[] campos =  {bancoDados.ID,bancoDados.CATEGORIA,bancoDados.SUBCATEGORIA,bancoDados.DESCRICAO,bancoDados.VALOR,bancoDados.DATA};
        String where = bancoDados.ID + "=" + id;
        db = bancoDados.getReadableDatabase();
        cursor = db.query(bancoDados.TABELA,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void alteraRegistro(Integer id, String categoria, String subcategoria, String descricao, String data, Float valor){
        ContentValues valores;
        String where;

        db = bancoDados.getWritableDatabase();

        where = bancoDados.ID + "=" + id;

        valores = new ContentValues();
        valores.put(bancoDados.CATEGORIA, categoria);
        valores.put(bancoDados.SUBCATEGORIA, subcategoria);
        valores.put(bancoDados.DESCRICAO, descricao);
        valores.put(bancoDados.DATA, data);
        valores.put(bancoDados.VALOR, valor);

        db.update(bancoDados.TABELA,valores,where,null);
        db.close();
    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {bancoDados.ID,bancoDados.CATEGORIA,bancoDados.SUBCATEGORIA,bancoDados.DESCRICAO,bancoDados.VALOR,bancoDados.DATA};
        db = bancoDados.getReadableDatabase();
        cursor = db.query(bancoDados.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void deletaRegistro(int id){
        String where = bancoDados.ID + "=" + id;
        db = bancoDados.getReadableDatabase();
        db.delete(bancoDados.TABELA,where,null);
        db.close();
    }




}
