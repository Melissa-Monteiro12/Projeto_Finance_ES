import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
// Luis
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat; // para o format yyyy-MM-dd para a edição

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

        //JLabel data = new JLabel(String.valueOf(transacao.getData())); Melissa
        // Luis - alteração, qualquer coisa é alterar o formato
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        JLabel data = new JLabel(formato.format(transacao.getData()));

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

        // Luis
        MouseAdapter clique = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Mostrar POPUP com a info do item clicado
                /*JOptionPane.showMessageDialog(
                        JanelaCronologia.this,
                        "Tipo: " + transacao.getTipo() +
                                "\nCategoria: " + transacao.getCategoria().getNome() +
                                "\nValor: " + transacao.getValor() +
                                "\nData: " + transacao.getData() +
                                "\nDescrição: " + transacao.getDescricao()
                );*/
                Object[] opcoes = {"Editar", "Apagar", "Cancelar"};

                int escolha = JOptionPane.showOptionDialog(
                        JanelaCronologia.this,
                        "O que queres fazer a esta transação?",
                        "Transação",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]
                );

                if (escolha == 0) {
                    //JOptionPane.showMessageDialog(JanelaCronologia.this, "Escolheste Editar");
                    editarTransacao(transacao);
                } else if (escolha == 1) {
                    //JOptionPane.showMessageDialog(JanelaCronologia.this, "Escolheste Apagar");
                    int confirmar = JOptionPane.showConfirmDialog(
                            JanelaCronologia.this,
                            "Queres mesmo apagar esta transação?",
                            "Confirmar",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmar == JOptionPane.YES_OPTION) {
                        frame.getGestorTransacoes().removerTransacao(transacao);
                        atualizarCronologia();
                    }
                }
            }
        };

        item.addMouseListener(clique);
        data.addMouseListener(clique);
        categoriaLabel.addMouseListener(clique);
        descricaoLabel.addMouseListener(clique);
        valorLabel.addMouseListener(clique);

        return item;

    }

    // Luis
    private void editarTransacao(Transacao transacao) {
        // Usar SimpleDateFormat para o formato de data
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setLenient(false);

        String novaValorTexto = JOptionPane.showInputDialog(
                this,
                "Novo valor:",
                transacao.getValor()
        );
        if (novaValorTexto == null) return;

        String novaDataTexto = JOptionPane.showInputDialog(
                this,
                "Nova data (yyyy-MM-dd):",
                formato.format(transacao.getData())
        );
        if (novaDataTexto == null) return;

        String novaDescricao = JOptionPane.showInputDialog(
                this,
                "Nova descricao:",
                transacao.getDescricao()
        );
        // Não é necessario validacao, pois é opcional

        try {
            Double novoValor = Double.parseDouble(novaValorTexto);
            Date novaData    = formato.parse(novaDataTexto);

            transacao.atualizar(novoValor, novaDescricao, novaData);
            frame.getGestorTransacoes().guardarNoFicheiro();
            atualizarCronologia();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Valor ou data inválidos", JOptionPane.ERROR_MESSAGE);
        }
    }

}
