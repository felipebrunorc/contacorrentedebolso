package br.estacio.fic.pdm.contacorrente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.estacio.fic.pdm.contacorrente.Persistencia.ContaCorrenteDAO;

public class PainelActivity extends Activity {


    TextView totalReceitas;
    TextView totalDespesas;
    TextView totalInvestimentos;
    TextView totalSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel);

        totalReceitas = (TextView) findViewById(R.id.totalReceitaId);
        totalDespesas = (TextView) findViewById(R.id.totalDespesaId);
        totalInvestimentos = (TextView) findViewById(R.id.totalInvestimentoId);
        totalSaldo = (TextView) findViewById(R.id.totalSaldoId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ContaCorrenteDAO contaCorrenteDAO = new ContaCorrenteDAO(getBaseContext());

        totalReceitas.setText(String.valueOf("R$ " + contaCorrenteDAO.selecioneTotalContaCorrente("Receitas")));
        totalDespesas.setText(String.valueOf("R$ " + contaCorrenteDAO.selecioneTotalContaCorrente("Despesas")));
        totalInvestimentos.setText(String.valueOf("R$ " + contaCorrenteDAO.selecioneTotalContaCorrente("Investimentos")));

        float total = (contaCorrenteDAO.selecioneTotalContaCorrente("Investimentos") + contaCorrenteDAO.selecioneTotalContaCorrente("Receitas")) - contaCorrenteDAO.selecioneTotalContaCorrente("Despesas");

        totalSaldo.setText((String.valueOf("R$" + total)));
    }

    public void novoLancamento(View view) {
        startActivity(new Intent(this, SegundaActivity.class));
    }

    public void listarLancamentos(View view) {
        startActivity(new Intent(this, listaActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MenuItem m1 = menu.add(0, 0, 0, "Incluir");
        m1.setIcon(R.drawable.adicionar);
        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        super.onCreateOptionsMenu(menu);
        MenuItem m2 = menu.add(0, 2, 2, "Sair");
        m2.setIcon(R.drawable.cancelar);
        m2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        super.onCreateOptionsMenu(menu);
        MenuItem m3 = menu.add(0, 1, 1, "Lista");
        m3.setIcon(R.drawable.lista);
        m3.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        return (true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intencao = new Intent(PainelActivity.this, SegundaActivity.class);
                startActivity(intencao);
                break;
            case 1:
                Intent intencao2 = new Intent(PainelActivity.this, listaActivity.class);
                startActivity(intencao2);
                break;
            case 2:
                Intent intencao3 = new Intent(PainelActivity.this, MainActivity.class);
                startActivity(intencao3);
                break;
            case android.R.id.home:
                Intent intencao4 = new Intent(this, PainelActivity.class);
                startActivity(intencao4);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


}
