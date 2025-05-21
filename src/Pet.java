public class Pet {
    
    //armazenar o estado do pet

    private String nome;
    private String sobrenome;

    public Pet(String nome, String sobrenome){
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }
    

}
