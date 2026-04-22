import java.io.Serializable;
import java.util.Date;

public class Transacao implements Serializable {
    // Luis
    private static final long serialVersionUID = 1L;
    // ISTO ACIMA É IMPORTANTE: qualquer alteração a esta classe
    // pode fazer com que o ficheiro que já foi criado anteriormente
    // precise da versão da classe. Como foi modificada, então a versão da
    // classe pode mudar. Ao ler, vai reparar que tem uma versão
    // diferente. Então defini um serialVersionUID final para
    // garantir compatibilidade daqui para a frente.

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

    // Luis
    public void atualizar(Double valor, String descricao, Date data) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
    }
}
