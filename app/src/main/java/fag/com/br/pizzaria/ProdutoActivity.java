package fag.com.br.pizzaria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import fag.com.br.pizzaria.adapter.AdapterProduto;
import fag.com.br.pizzaria.obj.Entity.Produto;
import fag.com.br.pizzaria.util.Mensagem;

public class ProdutoActivity extends AppCompatActivity {


    EditText etCodigo, etDescricao, etPsBruto, etVlProduto;
    ToggleButton tgAtivo;
    List<Produto> produtoList = new ArrayList<>();
    AdapterProduto adapterProduto;
    Button btSalvar;
    Produto produto = new Produto();
    ListView lvProduto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findComponents();

        carregaLista();
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCodigo.getText().toString() == null || etCodigo.getText().toString().trim().length() < 1){
                    Mensagem.ExibirMensagem(ProdutoActivity.this,"É necessário preencher um código",1);
                }else if ( etDescricao.getText().toString() == null || etDescricao.getText().toString().trim().length() < 1) {
                    Mensagem.ExibirMensagem(ProdutoActivity.this, "É necessário preencher uma descrição", 1);
                } else if (etPsBruto.getText().toString() == null || etPsBruto.getText().toString().trim().length() < 1){
                    Mensagem.ExibirMensagem(ProdutoActivity.this,"É necessário preencher um peso para o produto",1);
                } else if (etVlProduto.getText().toString() == null || etVlProduto.getText().toString().trim().length() < 1){
                    Mensagem.ExibirMensagem(ProdutoActivity.this,"É necessário preencher um valor para o produto",1);
                } else {
                    produto.setCdProduto(etCodigo.getText().toString());
                    produto.setDsProduto(etDescricao.getText().toString());
                    produto.setPsBruto(Double.parseDouble(etPsBruto.getText().toString()));
                    produto.setVlProduto(Double.parseDouble(etVlProduto.getText().toString()));
                    produto.setStAtivo(tgAtivo.isChecked());
                    produto.save();
                    Mensagem.ExibirMensagem(ProdutoActivity.this,"Salvo com sucesso",1);
                    produto = new Produto();
                    exibeProduto(produto);
                    carregaLista();
                }
            }
        });
        lvProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produto = (Produto)lvProduto.getItemAtPosition(position);
                exibeProduto(produto);
            }
        });
        carregaLista();
    }

    private void findComponents() {
        etCodigo = findViewById(R.id.etCodigo);
        etDescricao = findViewById(R.id.etDescricao);
        etPsBruto = findViewById(R.id.etPsBruto);
        etVlProduto = findViewById(R.id.etVlProduto);
        btSalvar = findViewById(R.id.btSalvar);
        tgAtivo = findViewById(R.id.tgAtivo);
        lvProduto = findViewById(R.id.lvProduto);
    }

    private void exibeProduto(Produto produto) {
        etDescricao.setText(produto.getDsProduto());
        etCodigo.setText(produto.getCdProduto());
        etPsBruto.setText(String.valueOf(produto.getPsBruto()));
        etVlProduto.setText(String.valueOf(produto.getVlProduto()));
        tgAtivo.setChecked(produto.isStAtivo());
    }
    private void carregaLista() {
        produtoList = Produto.listAll(Produto.class);//Lista com Ordenacao
        adapterProduto = new AdapterProduto(this, produtoList);
        lvProduto.setAdapter(adapterProduto);//Amarro a ListView com o Adapter criado
    }
}
