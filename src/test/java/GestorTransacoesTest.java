import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class GestorTransacoesTest {

    private GestorTransacoes gestor;
    private Transacao transacao;

    @BeforeEach
    void setUp() {
        gestor = new GestorTransacoes(false);

        transacao = new Transacao(
                Tipo.DESPESA,
                Categoria.ALIMENTACAO,
                25.0,
                "Supermercado",
                new Date()
        );
    }

    @Test
    void deveAdicionarTransacao() {
        int tamanhoInicial = gestor.getTransacoes().size();

        gestor.adicionarTransacao(transacao);

        assertEquals(tamanhoInicial + 1, gestor.getTransacoes().size());
        assertTrue(gestor.getTransacoes().contains(transacao));
    }

    @Test
    void deveRemoverTransacao() {
        gestor.adicionarTransacao(transacao);

        assertTrue(gestor.getTransacoes().contains(transacao));

        gestor.removerTransacao(transacao);

        assertFalse(gestor.getTransacoes().contains(transacao));
        assertEquals(0, gestor.getTransacoes().size());
    }
}
