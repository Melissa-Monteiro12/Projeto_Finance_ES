import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanelaVisaoGeral extends JPanel {
    private JanelaInicial frame;
    private JPanel janelaVisaoGeral;
    private JButton cronologiaButton;
    private JButton visaoGeralButton;
    private JButton adicionarItemButton;
    private JScrollPane scrollPanel;
    private JPanel categoriaListaPanel;

    public JanelaVisaoGeral(JanelaInicial frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        add(janelaVisaoGeral, BorderLayout.CENTER);

        categoriaListaPanel.setLayout(new BoxLayout(categoriaListaPanel, BoxLayout.Y_AXIS));
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setViewportView(categoriaListaPanel);

        cronologiaButton.addActionListener(e -> frame.mostrarEcra("cronologia"));
        adicionarItemButton.addActionListener(e -> {

            frame.mostrarEcra("adicionarItem");
        });
    }

    public void atualizarVisaoGeral() {
        categoriaListaPanel.removeAll();

        Map<String, Double> totaisPorCategoria = new HashMap<>();

        for (Transacao transacao : frame.getGestorTransacoes().getTransacoes()) {
            String nomeCategoria = transacao.getCategoria().getNome();
            double totalAtual = totaisPorCategoria.getOrDefault(nomeCategoria, 0.0);

            if (transacao.getTipo() == Tipo.DESPESA) {
                totalAtual -= transacao.getValor();
            } else {
                totalAtual += transacao.getValor();
            }

            totaisPorCategoria.put(nomeCategoria, totalAtual);
        }

        List<Map.Entry<String, Double>> listaOrdenada = new ArrayList<>(totaisPorCategoria.entrySet());
        listaOrdenada.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

        for (Map.Entry<String, Double> entry : listaOrdenada) {
            categoriaListaPanel.add(criarItemCategoria(entry.getKey(), entry.getValue()));
        }

        categoriaListaPanel.revalidate();
        categoriaListaPanel.repaint();
    }


    private JPanel criarItemCategoria(String nomeCategoria, double total) {
        JPanel item = new JPanel(new BorderLayout(10, 0));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        item.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel categoriaLabel = new JLabel(nomeCategoria);
        JLabel totalLabel = new JLabel(String.format("%.2f €", total));

        if (total < 0) {
            totalLabel.setForeground(Color.RED);
        } else {
            totalLabel.setForeground(new Color(0, 128, 0));
        }

        item.add(categoriaLabel, BorderLayout.WEST);
        item.add(totalLabel, BorderLayout.EAST);

        return item;
    }

}
