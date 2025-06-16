import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String nome;
    private Fome fome;
    private Energia energia;
    private Diversao diversao;
    private Saude saude;
    private Timer timer;
    private int diasVividos;
    private boolean estaVivo;

    public Pet(String nome) throws NomePetInvalidoException {
        validarNome(nome);
        this.nome = nome;
        this.fome = new Fome(50);
        this.energia = new Energia(70);
        this.diversao = new Diversao(60);
        this.saude = new Saude(100);
        this.diasVividos = 0;
        this.estaVivo = true;

        iniciarTimer();
    }

    // Construtor para carregar do CSV (sem validação pois já foi validado)
    public Pet(String nome, int fome, int energia, int diversao, int saude, int diasVividos) {
        this.nome = nome;
        this.fome = new Fome(fome);
        this.energia = new Energia(energia);
        this.diversao = new Diversao(diversao);
        this.saude = new Saude(saude);
        this.diasVividos = diasVividos;
        this.estaVivo = saude > 0;

        iniciarTimer();
    }

    private void validarNome(String nome) throws NomePetInvalidoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomePetInvalidoException("O nome do pet não pode estar vazio!");
        }
        
        if (nome.trim().length() < 2) {
            throw new NomePetInvalidoException("O nome do pet deve ter pelo menos 2 caracteres!");
        }
        
        if (nome.trim().length() > 20) {
            throw new NomePetInvalidoException("O nome do pet não pode ter mais de 20 caracteres!");
        }
        
        // Verificar se contém apenas letras, números e espaços
        if (!nome.trim().matches("[a-zA-ZÀ-ÿ0-9\\s]+")) {
            throw new NomePetInvalidoException("O nome do pet só pode conter letras, números e espaços!");
        }
        
        // Verificar se não é só números
        if (nome.trim().matches("\\d+")) {
            throw new NomePetInvalidoException("O nome do pet não pode ser apenas números!");
        }
    }

    private void iniciarTimer() {
        timer = new Timer(8000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estaVivo) {
                    atualizarStatusComTempo();
                }
            }
        });
        timer.start();
    }

    private void atualizarStatusComTempo() {
        fome.aumentar(2);
        energia.diminuir(1);
        diversao.diminuir(1);
        verificarSaude();
    }

    private void verificarSaude() {
        if (fome.getValor() > 85) {
            saude.diminuir(3);
        }
        else if (energia.getValor() < 15) {
            saude.diminuir(2);
        }
        else if (diversao.getValor() < 15) {
            saude.diminuir(1);
        }
        else if (fome.getValor() < 40 && energia.getValor() > 50 && diversao.getValor() > 40) {
            saude.aumentar(1);
        }

        if (saude.getValor() <= 0) {
            estaVivo = false;
            timer.stop();
        }
    }

    public void alimentar() {
        if (!estaVivo) return;
        fome.diminuir(30);
        energia.diminuir(5);
        diasVividos++;
    }

    public void dormir() {
        if (!estaVivo) return;
        energia.aumentar(40);
        fome.aumentar(10);
        diversao.diminuir(5);
        diasVividos++;
    }

    public void brincar() {
        if (!estaVivo) return;
        diversao.aumentar(35);
        energia.diminuir(15);
        fome.aumentar(8);
        diasVividos++;
    }

    public void medicar() {
        if (!estaVivo) return;
        saude.aumentar(25);
        energia.diminuir(10);
        diasVividos++;
    }

    public String getStatusGeral() {
        if (!estaVivo) {
            return "💀 Morreu";
        }
        
        int media = (100 - fome.getValor() + energia.getValor() + diversao.getValor() + saude.getValor()) / 4;
        
        if (media >= 80) return "😄 Muito Feliz";
        else if (media >= 65) return "😊 Feliz";
        else if (media >= 50) return "😐 Normal";
        else if (media >= 35) return "😟 Triste";
        else if (media >= 20) return "😰 Muito Triste";
        else return "💀 Crítico";
    }

    public void pararTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    // Métodos para salvar/carregar CSV
    public static void salvarPetsCSV(Pet[] pets, String nomeArquivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("nome,fome,energia,diversao,saude,diasVividos");
            
            for (Pet pet : pets) {
                writer.printf("%s,%d,%d,%d,%d,%d%n",
                    pet.getNome(),
                    pet.getFome().getValor(),
                    pet.getEnergia().getValor(),
                    pet.getDiversao().getValor(),
                    pet.getSaude().getValor(),
                    pet.getDiasVividos()
                );
            }
        }
    }

    public static Pet[] carregarPetsCSV(String nomeArquivo) throws IOException {
        List<Pet> pets = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = reader.readLine(); // Pular cabeçalho
            
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 5) {
                    String nome = dados[0];
                    int fome = Integer.parseInt(dados[1]);
                    int energia = Integer.parseInt(dados[2]);
                    int diversao = Integer.parseInt(dados[3]);
                    int saude = Integer.parseInt(dados[4]);
                    int diasVividos = dados.length > 5 ? Integer.parseInt(dados[5]) : 0;
                    
                    pets.add(new Pet(nome, fome, energia, diversao, saude, diasVividos));
                }
            }
        }
        
        return pets.toArray(new Pet[0]);
    }

    // Getters
    public String getNome() { return nome; }
    public Fome getFome() { return fome; }
    public Energia getEnergia() { return energia; }
    public Diversao getDiversao() { return diversao; }
    public Saude getSaude() { return saude; }
    public int getDiasVividos() { return diasVividos; }
    public boolean isEstaVivo() { return estaVivo; }
}
