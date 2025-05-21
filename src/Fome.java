public class Fome extends Status {

    public Fome(int valor) {
        super(valor);
    }

    @Override
    public void atualizar() {
        setValor(valor + 5); 
    }

    public void comer() {
        setValor(valor - 20);
    }
}
