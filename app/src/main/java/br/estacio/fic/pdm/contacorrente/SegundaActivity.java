package br.estacio.fic.pdm.contacorrente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import br.estacio.fic.pdm.contacorrente.Entidade.ContaCorrente;
import br.estacio.fic.pdm.contacorrente.Persistencia.BancoDados;
import br.estacio.fic.pdm.contacorrente.Persistencia.ContaCorrenteDAO;


public class SegundaActivity extends Activity {

    private String operacao;
    private String codigo;

    private int ano, mes, dia;
    private EditText dataNovaConta;
    private Spinner subcategoria;
    private RadioGroup radioGroup;
    private EditText descricao;
    private EditText valor;
    private ImageButton imgParcelaButton;
    private EditText editParcela;
    private RadioButton radioButton;

    private ContaCorrenteDAO contaCorrenteDAO;
    private Cursor cursor;

    private BancoDados myBanco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        try{
            codigo = this.getIntent().getStringExtra("codigo");

            android.app.ActionBar action = getActionBar();
            action.setDisplayHomeAsUpEnabled(true);

            contaCorrenteDAO = new ContaCorrenteDAO(this.getBaseContext());
            myBanco = new BancoDados(this);

            Calendar calendar = Calendar.getInstance();
            ano = calendar.get(Calendar.YEAR);
            mes = calendar.get(Calendar.MONTH);
            dia = calendar.get(Calendar.DAY_OF_MONTH);

            dataNovaConta = (EditText) findViewById(R.id.edtDataId);
            dataNovaConta.setText(dia + "/" + (mes + 1) + "/" + ano);

            subcategoria = (Spinner) findViewById(R.id.spnCategoriaId);
            subcategoria.setEnabled(false);

            radioButton = (RadioButton) findViewById(R.id.rdbReceitaId);
            radioGroup = (RadioGroup) findViewById(R.id.rdgId);
            imgParcelaButton = (ImageButton) findViewById(R.id.imgParcelaId);
            descricao = (EditText) findViewById(R.id.descricaoId);
            valor = (EditText) findViewById(R.id.edtValorId);
            editParcela = (EditText) findViewById(R.id.edtParcelaId);

            cursor = contaCorrenteDAO.carregaDadoById(Integer.parseInt(codigo));
            descricao.setText(cursor.getString(cursor.getColumnIndexOrThrow(myBanco.DESCRICAO)));
            dataNovaConta.setText(cursor.getString(cursor.getColumnIndexOrThrow(myBanco.DATA)));
            valor.setText(cursor.getString(cursor.getColumnIndexOrThrow(myBanco.VALOR)));


            imgParcelaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numberPickerDialog();
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    //NumberPicker - código números de quantidades
    private void numberPickerDialog() {
        final NumberPicker number_picker = new NumberPicker(this);
        number_picker.setMaxValue(10);
        number_picker.setMinValue(0);

        final NumberPicker.OnValueChangeListener eventoListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                editParcela.setText("" + newVal);
            }
        };
        number_picker.setOnValueChangedListener(eventoListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(number_picker);
        builder.setTitle("Nº Parcelas");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }


    //ActionBar Menu do App
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MenuItem m1 = menu.add(0, 0, 0, "Incluir");
        m1.setIcon(R.drawable.excluir);
        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        super.onCreateOptionsMenu(menu);
        MenuItem m2 = menu.add(0, 1, 1, "Lista");
        m2.setIcon(R.drawable.salvar);
        m2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return (true);

    }

    //Evento menu telaPrincipal
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case 0:
                contaCorrenteDAO.deletaRegistro(Integer.parseInt(codigo));
                Intent intent = new Intent(this, listaActivity.class);
                startActivity(intent);
                finish();
                break;

            case 1:
                try {
                    contaCorrenteDAO.alteraRegistro(Integer.parseInt(codigo), radioButton.getText().toString(),
                            subcategoria.getSelectedItem().toString(), descricao.getText().toString(), dataNovaConta.getText().toString(),
                            Float.parseFloat(valor.getText().toString()));
                    Intent intent1 = new Intent(this, listaActivity.class);
                    Toast.makeText(this, "Dados Alterados Com Sucesso !!", Toast.LENGTH_SHORT).show();
                    startActivity(intent1);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Favor preencha todos os campos ", Toast.LENGTH_SHORT).show();
                }


                break;

            case android.R.id.home:
                Intent intencao3 = new Intent(this, PainelActivity.class);
                startActivity(intencao3);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void salvarLancamento(View view) {

        try {

            ContaCorrente contaCorrente = new ContaCorrente();

            RadioButton radioButton = null;

            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioButton = ((RadioButton) radioGroup.getChildAt(i));
                if (radioButton.isChecked())
                    break;
            }

            contaCorrente.setCategoria(radioButton.getText().toString());
            contaCorrente.setSubcategoria(subcategoria.getSelectedItem().toString());
            contaCorrente.setDescricao(descricao.getText().toString());
            contaCorrente.setValor(Float.parseFloat(valor.getText().toString()));
            contaCorrente.setData(dataNovaConta.getText().toString());

            boolean result = contaCorrenteDAO.insert(contaCorrente);

            if (result) {
                Toast.makeText(this, "Lançamento cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, listaActivity.class));
                radioButton.setText("");
                descricao.setText("");
                valor.setText("");
                dataNovaConta.setText("");
                editParcela.setText("");
            } else {
                Toast.makeText(this, "Cadastro falhou!!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Favor realizar o devido lançamento, selecione as opções!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void selecionarCategoria(View view) {
        RadioButton radio = (RadioButton) view;
        ArrayAdapter<CharSequence> adapter = null;

        if ("Receitas".equals(radio.getText()))
            adapter = ArrayAdapter.createFromResource(this, R.array.lista_receitas, android.R.layout.simple_spinner_item);
        else if ("Despesas".equals(radio.getText()))
            adapter = ArrayAdapter.createFromResource(this, R.array.lista_despesas, android.R.layout.simple_spinner_item);
        if ("Investimentos".equals(radio.getText()))
            adapter = ArrayAdapter.createFromResource(this, R.array.lista_investimentos, android.R.layout.simple_spinner_item);

        subcategoria.setAdapter(adapter);
        subcategoria.setEnabled(true);
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (R.id.imgCalendarioId == id)
            return new DatePickerDialog(this, listener, ano, mes, dia);
        return null;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;

            dataNovaConta.setText(dia + "/" + (mes + 1) + "/" + ano);
        }
    };

}


