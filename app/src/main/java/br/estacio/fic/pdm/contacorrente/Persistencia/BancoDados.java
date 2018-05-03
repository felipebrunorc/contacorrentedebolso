package br.estacio.fic.pdm.contacorrente.Persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Felipe Bruno on 15/06/2017.
 */

public class BancoDados extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "contacorrente.bd";
    public static final String TABELA = "appcontacorrente";

    public static final String ID = "_id";
    public static final String CATEGORIA = "categoria";
    public static final String SUBCATEGORIA = "subcategoria";
    public static final String DESCRICAO = "descricao";
    public static final String VALOR = "valor";
    public static final String DATA = "data";
    public static final int VERSAO = 1;


    public BancoDados(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORIA + " TEXT, "
                + SUBCATEGORIA + " TEXT, "
                + DESCRICAO + " TEXT, "
                + VALOR + " FLOAT, "
                + DATA + " DATE"
                + ")";

        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }

}





//###########################################################################################################################
