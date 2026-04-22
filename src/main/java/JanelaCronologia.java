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

        visaoGeralButton.addActionListener(e -> frame.abrirVisaoGeral());
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


    private void editarTransacao(Transacao transacao) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setLenient(false);

        JTextField valorField = new JTextField(String.valueOf(transacao.getValor()));
        JTextField dataField = new JTextField(formato.format(transacao.getData()));
        JTextField descricaoField = new JTextField(transacao.getDescricao());

        JRadioButton despesaRadio = new JRadioButton("Despesa");
        JRadioButton receitaRadio = new JRadioButton("Receita");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(despesaRadio);
        grupoTipo.add(receitaRadio);

        if (transacao.getTipo() == Tipo.DESPESA) {
            despesaRadio.setSelected(true);
        } else {
            receitaRadio.setSelected(true);
        }

        JComboBox<Categoria> categoriaComboBox = new JComboBox<>();

        Runnable atualizarCategorias = () -> {
            categoriaComboBox.removeAllItems();

            Tipo tipoSelecionado = despesaRadio.isSelected() ? Tipo.DESPESA : Tipo.RECEITA;

            for (Categoria categoria : Categoria.values()) {
                if (categoria.getTipo() == tipoSelecionado) {
                    categoriaComboBox.addItem(categoria);
                }
            }

            if (transacao.getCategoria().getTipo() == tipoSelecionado) {
                categoriaComboBox.setSelectedItem(transacao.getCategoria());
            } else if (categoriaComboBox.getItemCount() > 0) {
                categoriaComboBox.setSelectedIndex(0);
            }
        };

        despesaRadio.addActionListener(e -> atualizarCategorias.run());
        receitaRadio.addActionListener(e -> atualizarCategorias.run());

        atualizarCategorias.run();

        JPanel tipoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoPanel.add(despesaRadio);
        tipoPanel.add(receitaRadio);

        JPanel painel = new JPanel(new GridLayout(0, 1, 5, 5));
        painel.add(new JLabel("Tipo:"));
        painel.add(tipoPanel);
        painel.add(new JLabel("Categoria:"));
        painel.add(categoriaComboBox);
        painel.add(new JLabel("Valor:"));
        painel.add(valorField);
        painel.add(new JLabel("Data (yyyy-MM-dd):"));
        painel.add(dataField);
        painel.add(new JLabel("Descrição:"));
        painel.add(descricaoField);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                painel,
                "Editar transação",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }

        String novoValorTexto = valorField.getText().trim();
        String novaDataTexto = dataField.getText().trim();
        String novaDescricao = descricaoField.getText().trim();

        Tipo novoTipo = despesaRadio.isSelected() ? Tipo.DESPESA : Tipo.RECEITA;
        Categoria novaCategoria = (Categoria) categoriaComboBox.getSelectedItem();

        if (novoValorTexto.isEmpty() || novaDataTexto.isEmpty() || novaCategoria == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Preencha os campos Categoria, Valor e Data.",
                    "Campos em falta",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        double novoValor;
        try {
            novoValor = Double.parseDouble(novoValorTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Valor inválido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if (novoValor <= 0) {
            JOptionPane.showMessageDialog(this, "O valor tem de ser positivo e numérico.");
            return;
        }

        Date novaData;
        try {
            novaData = formato.parse(novaDataTexto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Data inválida. Usa o formato yyyy-MM-dd.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        transacao.atualizar(novoTipo, novaCategoria, novoValor, novaDescricao, novaData);
        frame.getGestorTransacoes().guardarNoFicheiro();
        atualizarCronologia();
    }

}
