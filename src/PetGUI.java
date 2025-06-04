import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PetGUI{
  private Pet pet;
  private JFrame frame;
  private JProgressBar fomeBar, energiaBar, diversaoBar;

  public PetGUI(Pet pet){
    this.pet = pet;
    criarGUI();
    atualizarBarras();
  }

  private void criarGUI(){
    frame = new JFrame("Virtual Pet");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);

    JPanel panel = new JPanel(new GridLayout(4, 1));

    fomeBar = criarBarraStatus("Fome", Color.RED);
    energiaBar = criarBarraStatus("Energia", Color.BLUE);
    diversaoBar = criarBarraStatus("Diversão", Color.GREEN);

    panel.add(fomeBar);
    panel.add(energiaBar);
    panel.add(diversaoBar);

    JPanel botoesPanel = new JPanel(new GridLayout(1, 3));
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
    return barra;
  }

  private JButton criarBotao(String texto, ActionListener acao) {
    JButton botao = new JButton(texto);
    botao.addActionListener(acao);
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
