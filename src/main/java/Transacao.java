import java.io.Serializable;
import java.util.Date;

public class Transacao implements Serializable {
    private Tipo tipo;
    private Categoria categoria;
    private Double valor;
    private String descricao;
    private Date data;

    public Transacao(Tipo tipo, Categoria categoria, Double valor, String descricao, Date data) {
        this.tipo = tipo;
        this.categoria = categoria;
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getData() {
        return data;
    }
}
