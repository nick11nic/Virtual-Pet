import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pet {
  private String nome;
  private Fome fome;
  private Energia energia;
  private Diversao diversao;
  private Timer timer;

  public Pet(String nome){
    this.nome = nome;
    this.fome = new Fome(100);
    this.energia = new Energia(100);
    this.diversao = new Diversao(100);

    timer = new Timer(3000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        fome.aumentar(3);
        energia.diminuir(2);
        diversao.diminuir(4);
      }
    });
    timer.start();
  }

  public void alimentar(){
    fome.diminuir(10);
    energia.diminuir(5);
  }

  public void dormir() {
    energia.aumentar(30);
    fome.aumentar(5);
  }

  public void brincar() {
    diversao.aumentar(25);
    energia.diminuir(15);
    fome.aumentar(10);
  }

  public String getNome() {
    return nome;
  }
  public Fome getFome() {
    return fome;
  }
  public Energia getEnergia() {
    return energia;
  }
  public Diversao getDiversao() {
    return diversao;
  }
}