import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JanelaInicial extends JFrame {
    CardLayout cardLayout;
    JPanel container;

    private JPanel janelaInicial;
    private JButton iniciarSessaoButton;
    private JButton criarContaButton;

    private GestorTransacoes gestorTransacoes;
    private JanelaCronologia janelaCronologia;


    public JanelaInicial() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        //pack();

        gestorTransacoes = new GestorTransacoes();
        gestorTransacoes.lerOFicheiro();
        janelaCronologia = new JanelaCronologia(this);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        container.add(janelaInicial, "inicial");
        container.add(new JanelaLogin(this), "login");
        container.add(new JanelaCriarConta(this), "criarConta");
        container.add(janelaCronologia, "cronologia");
        container.add(new JanelaVisaoGeral(this), "visaoGeral");
        container.add(new JanelaAdicionarItem(this), "adicionarItem");

        setContentPane(container);


        iniciarSessaoButton.addActionListener(this::btnJanelaLogin);
        criarContaButton.addActionListener(this::btnJanelaCriarConta);

    }

    public void mostrarEcra(String nome) {
        cardLayout.show(container, nome);
    }

    private void btnJanelaLogin(ActionEvent e) {
        mostrarEcra("login");
    }

    private void btnJanelaCriarConta(ActionEvent e) {
        mostrarEcra("criarConta");
    }

    public GestorTransacoes getGestorTransacoes() {
        return gestorTransacoes;
    }

    public void abrirCronologia() {
        janelaCronologia.atualizarCronologia();
        mostrarEcra("cronologia");
    }
}
