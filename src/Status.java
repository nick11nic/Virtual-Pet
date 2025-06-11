public abstract class Status {
  protected int valor;
  protected final int MAXIMO = 100;
  protected final int MINIMO = 0;

  public Status(int valor) {
    this.valor = valor;
  }

  public void aumentar(int qtd) {
    valor = Math.min(valor + qtd, MAXIMO);
  }

  public void diminuir(int qtd) {
    valor = Math.max(valor - qtd, MINIMO);
  }

  public int getValor() {
    return valor;
  }

  public abstract String getNome();
}