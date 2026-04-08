import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JanelaCriarConta extends JPanel {
    private JanelaInicial frame;
    private JPanel janelaCriarConta;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton criarContaButton;
    private JButton voltarButton;


    public JanelaCriarConta(JanelaInicial frame) {
        this.frame = frame;

        setLayout(new BorderLayout());
        add(janelaCriarConta, BorderLayout.CENTER);

        voltarButton.addActionListener(this::btnVoltarAtras);
    }
    private void btnVoltarAtras(ActionEvent e) {
        frame.mostrarEcra("inicial");
    }
}
