package br.estacio.fic.pdm.contacorrente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.estacio.fic.pdm.contacorrente.Entidade.ContaCorrente;
import br.estacio.fic.pdm.contacorrente.Persistencia.BancoDados;
import br.estacio.fic.pdm.contacorrente.Persistencia.ContaCorrenteDAO;

public class listaActivity extends Activity {

    private ListView lista;
    BancoDados myBanco;


    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        ContaCorrenteDAO crud = new ContaCorrenteDAO(getBaseContext());
        cursor = crud.carregaDados();

        final Context context = this;

        myBanco = new BancoDados(this);

        android.app.ActionBar action = getActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        ContaCorrenteDAO lancamentoDAO = new ContaCorrenteDAO(getBaseContext());
        List<ContaCorrente> listaLancamentos = lancamentoDAO.selectAll();

        ArrayAdapter<ContaCorrente> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listaLancamentos);

        lista = (ListView) findViewById(R.id.ListViewId);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    String codigo;
                    cursor.moveToPosition(position);
                    codigo = cursor.getString(cursor.getColumnIndexOrThrow(myBanco.ID));
                    Intent intent = new Intent(context, SegundaActivity.class);
                    intent.putExtra("codigo", codigo);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                   e.printStackTrace();

                }

            }
        });


    }

    public void novoLancamento(View view) {
        startActivity(new Intent(this, SegundaActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent intencao = new Intent(this, PainelActivity.class);
                startActivity(intencao);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void voltar(View view) {
        Intent intencao = new Intent(this, PainelActivity.class);
        startActivity(intencao);
    }


}

