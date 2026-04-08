import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorTransacoes {
    private List<Transacao> transacoes;

    public GestorTransacoes() {
        transacoes = new ArrayList<>();
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        guardarNoFicheiro();

    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void guardarNoFicheiro() {
        ObjectOutputStream oos = null;
        try {
            File f =new File(System.getProperty("user.home")+File.separator+"falenciatraker.transacoes");
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(transacoes);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(GestorTransacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void lerOFicheiro(){
        ObjectInputStream ois = null;
        File f = new File(System.getProperty("user.home")+File.separator+"falenciatraker.transacoes");
        if (!f.exists()) return;
        if (f.canRead()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                transacoes = (List<Transacao>) ois.readObject();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(GestorTransacoes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GestorTransacoes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
