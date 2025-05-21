public class Diversao extends Status {

    public Diversao(int valor) {
        super(valor);
    }

    @Override
    public void atualizar() {
        setValor(valor - 4); 
    }

    public void brincar() {
        setValor(valor + 25);
    }
}
