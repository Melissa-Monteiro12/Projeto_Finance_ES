import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JanelaLogin extends JPanel {
    private JanelaInicial frame;
    private JPanel janelaLogin;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton entrarButton;
    private JButton voltarButton;


    public JanelaLogin(JanelaInicial frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        add(janelaLogin, BorderLayout.CENTER);


        voltarButton.addActionListener(this::btnVoltarAtras);
        entrarButton.addActionListener(this::btnEntrar);
    }

    private void btnEntrar(ActionEvent e) {
        frame.mostrarEcra("cronologia");
    }

    private void btnVoltarAtras(ActionEvent e){
        frame.mostrarEcra("inicial");
    }
}
