package br.estacio.fic.pdm.contacorrente;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.estacio.fic.pdm.contacorrente.Persistencia.BancoDados;

/**
 * Created by Felipe Bruno on 13/06/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)

public class MainActivity extends Activity {

    BancoDados bancoDados;
    private Button btnEntrar;
    private EditText edtSenha;
    private String senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //excluirTabela();


        btnEntrar = (Button) findViewById(R.id.btnEntrarId);
        edtSenha = (EditText) findViewById(R.id.edtSenhaId);


        /*TESTE CRUD*/

        btnEntrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intencao = new Intent(MainActivity.this, PainelActivity.class);
                senha = edtSenha.getText().toString();

                if (senha.equals("admin")) {
                    mensagem("Seja Bem vindo");
                    edtSenha.setText("");
                    startActivity(intencao);

                } else {
                    mensagem("Digite novamente Senha Incorreta !");
                }
            }
        });

    }


    private void mensagem(String m) {
        Toast.makeText(this, m, Toast.LENGTH_LONG).show();
    }
}

