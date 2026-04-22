import javax.swing.*;
import java.awt.*;
import java.security.spec.ECField;
//import java.sql.Date; BUG REAL - Luis
import java.util.ArrayList;
import java.util.List;
// Luis
import java.util.Date;
import java.text.SimpleDateFormat;

public class JanelaAdicionarItem extends JPanel {
    private JanelaInicial frame;
    private JPanel janelaAdicionarItem;
    private JButton cronologiaButton;
    private JButton visaoGeralButton;
    private JButton adicionarItemButton;
    private JTextField valor;
    private JTextField descricaoField;
    private JTextField dataField;
    private JRadioButton despesaRadioButton;
    private JRadioButton receitaRadioButton;
    private JButton guardarTransacaoButton;
    private JPanel categoriasPainel;

    private Tipo tipoSelecionado = Tipo.DESPESA;
    private Categoria categoriaSelecionada = null;

    public JanelaAdicionarItem(JanelaInicial frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        add(janelaAdicionarItem, BorderLayout.CENTER);

        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(despesaRadioButton);
        grupoTipo.add(receitaRadioButton);
        despesaRadioButton.setSelected(true);

        despesaRadioButton.addActionListener( e -> {
            tipoSelecionado = Tipo.DESPESA;
            atualizarCategorias();
        });

        receitaRadioButton.addActionListener( e -> {
            tipoSelecionado = Tipo.RECEITA;
            atualizarCategorias();
        });

        atualizarCategorias();

        guardarTransacaoButton.addActionListener( e -> guardarTransacao());

        cronologiaButton.addActionListener(e -> frame.mostrarEcra("cronologia"));
        visaoGeralButton.addActionListener(e -> frame.mostrarEcra("visaoGeral"));
    }

    private List<Categoria> getCategoriasPorTipo(Tipo tipo){
       List<Categoria> lista = new ArrayList<>();

       for(Categoria categoria : Categoria.values()){
           if(categoria.getTipo() == tipo){
               lista.add(categoria);
           }
       }
       return lista;
    }

    private void atualizarCategorias() {
        categoriasPainel.removeAll();

        ButtonGroup grupoCategoriasButtonGroup = new ButtonGroup();

        for(Categoria categoria : getCategoriasPorTipo(tipoSelecionado)){
            JRadioButton categoriaRadioButton = new JRadioButton(categoria.getNome());
            categoriaRadioButton.addActionListener(e -> categoriaSelecionada = categoria);

            grupoCategoriasButtonGroup.add(categoriaRadioButton);
            categoriasPainel.add(categoriaRadioButton);
        }


        categoriasPainel.revalidate();
        categoriasPainel.repaint();
    }

    private void guardarTransacao() {
        String dataString = dataField.getText();
        String valorString = valor.getText();
        String descricao = descricaoField.getText();

        if(dataString.isEmpty() || valorString.isEmpty() || categoriaSelecionada == null){
            JOptionPane.showMessageDialog(this, "Preencha os campos Data, Valor e Categoria.");
            return;
        }

        /*double valor = Double.parseDouble(valorString);
        Date data;
        try {
            data = Date.valueOf(dataString);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Usa o formato yyyy-MM-dd.");
            return;
        }*/

        // Luís - ALTERAÇÃO
        // Input Valor
        double valor;
        try {
            valor = Double.parseDouble(valorString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido");
            return;
        }

        // Input Data
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setLenient(false);

        Date data;
        try {
            data = formato.parse(dataString);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Usa o formato yyyy-MM-dd.");
            return;
        }

        Transacao transacao = new Transacao(tipoSelecionado,categoriaSelecionada,valor,descricao,data);

        frame.getGestorTransacoes().adicionarTransacao(transacao);

        this.valor.setText("0");
        descricaoField.setText("");
        categoriaSelecionada = null;
        despesaRadioButton.setSelected(true);
        tipoSelecionado = Tipo.DESPESA;
        dataField.setText("");
        atualizarCategorias();

        frame.abrirCronologia();
    }
}
