import compilador.Token;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CompiladorMain extends JFrame implements ActionListener {
    private JTextArea textAreaCodigo;
    private JTextArea textAreaResultado;
    private JButton buttonCompilar;

    public CompiladorMain() {
        setTitle("Compilador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textAreaCodigo = new JTextArea();
        textAreaResultado = new JTextArea();
        buttonCompilar = new JButton("Compilar");

        buttonCompilar.addActionListener(this);

        JPanel panelCodigo = new JPanel(new BorderLayout());
        panelCodigo.add(new JScrollPane(textAreaCodigo), BorderLayout.CENTER);

        JPanel panelBotao = new JPanel();
        panelBotao.add(buttonCompilar);

        JPanel panelResultado = new JPanel(new BorderLayout());
        panelResultado.add(new JScrollPane(textAreaResultado), BorderLayout.CENTER);

        add(panelCodigo, BorderLayout.CENTER);
        add(panelBotao, BorderLayout.SOUTH);
        add(panelResultado, BorderLayout.EAST);

        setVisible(true);
    }

    public static void main(String[] args) {
        new CompiladorMain();
    }

@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == buttonCompilar) {
        String codigoFonte = textAreaCodigo.getText();

        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.lexicalAnalysis(codigoFonte);
        
        Parser parser = new Parser(tokens);
        parser.parse();

        StringBuilder output = new StringBuilder();
        for (Token token : tokens) {
            output.append(token.getType()).append(": ").append(token.getValue()).append("\n");
        }
        textAreaResultado.setText(output.toString());
    }
}


}
