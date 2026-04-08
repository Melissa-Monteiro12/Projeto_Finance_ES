import java.io.Serializable;

public enum Categoria implements Serializable {
    ALIMENTACAO("Alimentação", Tipo.DESPESA),
    CARRO("Carro",Tipo.DESPESA),
    CASA("Casa", Tipo.DESPESA),
    SAUDE("Saúde", Tipo.DESPESA),
    EDUCACAO("Educação", Tipo.DESPESA),
    FERIAS("Férias", Tipo.DESPESA),
    PESSOAIS("Pessoais", Tipo.DESPESA),

    SALARIO("Salário", Tipo.RECEITA),
    INVESTIMENTOS("Investimentos", Tipo.RECEITA),
    TRANSFERENCIAS("Transferencias", Tipo.RECEITA),
    OUTROS("Outros", Tipo.RECEITA);

    private String nome;
    private Tipo tipo;

    Categoria(String nome, Tipo tipo){
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
