public class Saude extends Status {

    public Saude(int valor) {
        super(valor);
    }

    @Override
    public void atualizar() {
        setValor(valor - 2);
    }

    public void tomarBanho() {
        setValor(valor + 10);
    }
}
