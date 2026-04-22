import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JanelaInicial extends JFrame {
    CardLayout cardLayout;
    JPanel container;

    private JPanel janelaInicial;
    private JButton entrarButton;

    private GestorTransacoes gestorTransacoes;
    private JanelaCronologia janelaCronologia;
    private JanelaAdicionarItem janelaAdicionarItem;
    private JanelaVisaoGeral janelaVisaoGeral;



    public JanelaInicial() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        //pack();

        gestorTransacoes = new GestorTransacoes();
        gestorTransacoes.lerOFicheiro();
        janelaCronologia = new JanelaCronologia(this);
        janelaAdicionarItem = new JanelaAdicionarItem(this);
        janelaVisaoGeral = new JanelaVisaoGeral(this);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        container.add(janelaInicial, "inicial");
        container.add(janelaCronologia, "cronologia");
        container.add(janelaVisaoGeral, "visaoGeral");
        container.add(janelaAdicionarItem, "adicionarItem");

        setContentPane(container);


        entrarButton.addActionListener(this::btnEntrar);

    }

    public void mostrarEcra(String nome) {
        cardLayout.show(container, nome);
    }

    private void btnEntrar(ActionEvent e) {
        mostrarEcra("cronologia");
    }

    public GestorTransacoes getGestorTransacoes() {
        return gestorTransacoes;
    }

    public void abrirCronologia() {
        janelaCronologia.atualizarCronologia();
        mostrarEcra("cronologia");
    }

    public JanelaAdicionarItem getJanelaAdicionarItem() {
        return janelaAdicionarItem;
    }

    public void abrirVisaoGeral() {
        janelaVisaoGeral.atualizarVisaoGeral();
        mostrarEcra("visaoGeral");
    }
}
