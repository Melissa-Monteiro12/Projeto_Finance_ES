import javax.swing.*;
import java.awt.*;

public class JanelaVisaoGeral extends JPanel {
    private JanelaInicial frame;
    private JPanel janelaVisaoGeral;
    private JButton cronologiaButton;
    private JButton visaoGeralButton;
    private JButton adicionarItemButton;
    private JScrollPane categoriaListaPanel;

    public JanelaVisaoGeral(JanelaInicial frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        add(janelaVisaoGeral, BorderLayout.CENTER);

        cronologiaButton.addActionListener(e -> frame.mostrarEcra("cronologia"));
        adicionarItemButton.addActionListener(e -> frame.mostrarEcra("adicionarItem"));
    }
}
