public abstract class Status {
    protected int valor;

    public Status(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = Math.max(0, Math.min(100, valor)); }

    public abstract void atualizar();
}
