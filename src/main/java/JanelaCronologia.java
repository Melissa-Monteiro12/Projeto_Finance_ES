import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JPanel {
    private JanelaInicial frame;
    private JPanel janelaPrincipal;
    private JPanel painelLista;
    private JButton cronologiaButton;
    private JButton visaoGeralButton;
    private JButton adicionarItemButton;
    private JLabel fluxoCaixaLabel;
    private JScrollPane scrollPanel;

    public JanelaPrincipal(JanelaInicial frame) {
        this.frame = frame;

        setLayout(new BorderLayout());
        add(janelaPrincipal, BorderLayout.CENTER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
    }
}
