import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JanelaCronologia extends JPanel {
    private JanelaInicial frame;
    private JPanel janelaCronologia;
    private JPanel itemListaPanel;
    private JButton cronologiaButton;
    private JButton visaoGeralButton;
    private JButton adicionarItemButton;
    private JLabel fluxoCaixaLabel;
    private JScrollPane scrollPanel;

    public JanelaCronologia(JanelaInicial frame) {
        this.frame = frame;

        setLayout(new BorderLayout());
        add(janelaCronologia, BorderLayout.CENTER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        itemListaPanel.setLayout(new BoxLayout(itemListaPanel, BoxLayout.Y_AXIS));

        atualizarCronologia();

        visaoGeralButton.addActionListener(e -> frame.mostrarEcra("visaoGeral"));
        adicionarItemButton.addActionListener(e -> frame.mostrarEcra("adicionarItem"));
    }

    public void atualizarCronologia() {
        itemListaPanel.removeAll();
        double count = 0;

        List<Transacao> transacoes = new ArrayList<>(frame.getGestorTransacoes().getTransacoes());

        // ordenar por data (mais recente primeiro)
        transacoes.sort(Comparator.comparing(Transacao::getData).reversed());

        for (Transacao transacao : transacoes) {
            itemListaPanel.add(criarItem(transacao));
            if(transacao.getTipo() == Tipo.DESPESA){
                count -= transacao.getValor();
            } else {
                count += transacao.getValor();
            }
        }

        fluxoCaixaLabel.setText(count + " €");

        itemListaPanel.revalidate();
        itemListaPanel.repaint();
    }

    public JPanel criarItem(Transacao transacao) {
        JPanel item = new JPanel(new BorderLayout(10,0));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel data = new JLabel(String.valueOf(transacao.getData()));
        JLabel categoriaLabel = new JLabel(String.valueOf(transacao.getCategoria().getNome()));
        JLabel descricaoLabel = new JLabel(transacao.getDescricao());
        JLabel valorLabel = new JLabel();


        JPanel esquerda = new JPanel();
        esquerda.setLayout(new BoxLayout(esquerda, BoxLayout.Y_AXIS));

        esquerda.add(categoriaLabel);
        esquerda.add(descricaoLabel);
        esquerda.add(data);

        item.add(esquerda, BorderLayout.CENTER);

        JPanel direita = new JPanel(new BorderLayout());
        direita.add(valorLabel, BorderLayout.NORTH);

        item.add(direita, BorderLayout.EAST);

        if (transacao.getTipo() == Tipo.DESPESA) {
            valorLabel.setText("- " + transacao.getValor() + " €");
            valorLabel.setForeground(Color.RED);
        } else {
            valorLabel.setText("+ " + transacao.getValor() + " €");
            valorLabel.setForeground(Color.GREEN);
        }

        return item;

    }

}
