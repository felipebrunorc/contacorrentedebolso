package br.estacio.fic.pdm.contacorrente.Entidade;

/**
 * Created by Felipe Bruno on 13/06/2017.
 */

public class ContaCorrente {

    private String categoria;

    private String subcategoria;

    private String descricao;

    private float valor;

    private String data;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Categoria: " + getCategoria());
        builder.append(" Subcategoria: " + getSubcategoria());
        builder.append(" Descrição: " + getDescricao());
        builder.append(" Valor: " + getValor());
        builder.append(" Data: " + getData());

        return builder.toString();
    }


}


