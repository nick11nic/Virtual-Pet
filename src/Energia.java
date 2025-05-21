public class Energia extends Status {

    public Energia(int valor) {
        super(valor);
    }

    @Override
    public void atualizar() {
        setValor(valor - 3); 
    }

    public void dormir() {
        setValor(valor + 30);
    }
}
