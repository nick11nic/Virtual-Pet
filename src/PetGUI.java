import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PetGUI {
    private Pet pet;
    private JFrame frame;
    private JProgressBar fomeBar, energiaBar, diversaoBar;
    private JLabel nomeLabel;
    
    public PetGUI() {
        mostrarTelaInicial();
    }
    
    private void mostrarTelaInicial() {
        frame = new JFrame("Virtual Pet - Novo Jogo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel titulo = new JLabel("Bem-vindo ao Virtual Pet!");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JLabel instrucao = new JLabel("Digite o nome do seu pet:");
        instrucao.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instrucao);
        
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JTextField nomeField = new JTextField(15);
        nomeField.setMaximumSize(new Dimension(200, 25));
        nomeField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nomeField);
        
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JButton startButton = new JButton("START");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(new Dimension(100, 30));
        startButton.addActionListener(e -> {
    String nomePet = nomeField.getText().trim();
    try {
        iniciarJogo(nomePet);
    } catch (NomePetInvalidoException ex) {
        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Nome Inválido", JOptionPane.ERROR_MESSAGE);
    }
});
        
        nomeField.addActionListener(e -> startButton.doClick());
        
        panel.add(startButton);
        
        frame.add(panel);
        frame.setVisible(true);
        
        nomeField.requestFocusInWindow();
    }
    
    private void iniciarJogo(String nomePet) throws NomePetInvalidoException {
    this.pet = new Pet(nomePet);
    frame.dispose();
    criarGUI();
    atualizarBarras();
}
    
    private void criarGUI() {
        frame = new JFrame("Virtual Pet - " + pet.getNome());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Nome do pet
        nomeLabel = new JLabel("Pet: " + pet.getNome(), SwingConstants.CENTER);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(nomeLabel);
        
        fomeBar = criarBarraStatus("Fome", new Color(231, 84, 128)); 
        energiaBar = criarBarraStatus("Energia", new Color(100, 181, 246)); 
        diversaoBar = criarBarraStatus("Diversão", new Color(144, 238, 144)); 
        
        panel.add(fomeBar);
        panel.add(energiaBar);
        panel.add(diversaoBar);
        
        JPanel botoesPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        botoesPanel.add(criarBotao("Alimentar", this::alimentar));
        botoesPanel.add(criarBotao("Dormir", this::dormir));
        botoesPanel.add(criarBotao("Brincar", this::brincar));
        panel.add(botoesPanel);
        
        frame.add(panel);
        frame.setVisible(true);
    }
    
    private JProgressBar criarBarraStatus(String nome, Color cor) {
        JProgressBar barra = new JProgressBar(0, 100);
        barra.setString(nome);
        barra.setStringPainted(true);
        barra.setForeground(cor);
        barra.setBorder(BorderFactory.createTitledBorder(nome));
        return barra;
    }
    
    private JButton criarBotao(String texto, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.addActionListener(acao);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        return botao;
    }
    
    private void atualizarBarras() {
        fomeBar.setValue(pet.getFome().getValor());
        energiaBar.setValue(pet.getEnergia().getValor());
        diversaoBar.setValue(pet.getDiversao().getValor());
    }
    
    private void alimentar(ActionEvent e) {
        pet.alimentar();
        atualizarBarras();
    }
    
    private void dormir(ActionEvent e) {
        pet.dormir();
        atualizarBarras();
    }
    
    private void brincar(ActionEvent e) {
        pet.brincar();
        atualizarBarras();
    }

}