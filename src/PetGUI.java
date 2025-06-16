import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PetGUI {
    private Pet petAtual;
    private JFrame frame;
    private JProgressBar fomeBar, energiaBar, diversaoBar, saudeBar;
    private JLabel nomeLabel, statusLabel, diasLabel;
    private final String ARQUIVO_PETS = "pets.csv";
    
    public PetGUI() {
        mostrarTelaInicial();
    }
    
    private void mostrarTelaInicial() {
        frame = new JFrame("🐾 Virtual Pet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        JLabel titulo = new JLabel("🐾 Virtual Pet", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        centerPanel.setBackground(Color.WHITE);
        
        JLabel instrucao = new JLabel("Cuide do seu pet virtual!", SwingConstants.CENTER);
        instrucao.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(instrucao);
        
        JButton btnNovoJogo = new JButton("🆕 Novo Pet");
        btnNovoJogo.setFont(new Font("Arial", Font.BOLD, 16));
        btnNovoJogo.setPreferredSize(new Dimension(200, 40));
        btnNovoJogo.addActionListener(e -> criarNovoPet());
        centerPanel.add(btnNovoJogo);
        
        File arquivo = new File(ARQUIVO_PETS);
        if (arquivo.exists()) {
            JButton btnEscolherPet = new JButton("📁 Escolher Pet");
            btnEscolherPet.setFont(new Font("Arial", Font.BOLD, 16));
            btnEscolherPet.setPreferredSize(new Dimension(200, 40));
            btnEscolherPet.addActionListener(e -> mostrarEscolhaPets());
            centerPanel.add(btnEscolherPet);
        }
        
        JButton btnSair = new JButton("❌ Sair");
        btnSair.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSair.addActionListener(e -> System.exit(0));
        centerPanel.add(btnSair);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    
    private void criarNovoPet() {
        boolean nomeValido = false;
        
        while (!nomeValido) {
            String nome = JOptionPane.showInputDialog(frame, 
                "Nome do seu pet:\n(2-20 caracteres, apenas letras, números e espaços)", 
                "Novo Pet", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (nome == null) {
                return; // Usuário cancelou
            }
            
            try {
                petAtual = new Pet(nome);
                nomeValido = true;
                frame.dispose();
                criarJanelaJogo();
                
            } catch (NomePetInvalidoException e) {
                JOptionPane.showMessageDialog(frame, 
                    "❌ " + e.getMessage() + "\n\nTente novamente!", 
                    "Nome Inválido", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void mostrarEscolhaPets() {
        try {
            Pet[] pets = Pet.carregarPetsCSV(ARQUIVO_PETS);
            if (pets.length == 0) {
                JOptionPane.showMessageDialog(frame, 
                    "Nenhum pet salvo encontrado!", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            JDialog dialogoEscolha = new JDialog(frame, "Escolher Pet", true);
            dialogoEscolha.setSize(450, 350);
            dialogoEscolha.setLocationRelativeTo(frame);
            
            JPanel painelEscolha = new JPanel(new BorderLayout());
            painelEscolha.setBackground(Color.WHITE);
            
            JLabel tituloEscolha = new JLabel("Escolha seu Pet:", SwingConstants.CENTER);
            tituloEscolha.setFont(new Font("Arial", Font.BOLD, 18));
            tituloEscolha.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
            painelEscolha.add(tituloEscolha, BorderLayout.NORTH);
            
            JPanel listaPets = new JPanel();
            listaPets.setLayout(new BoxLayout(listaPets, BoxLayout.Y_AXIS));
            listaPets.setBackground(Color.WHITE);
            
            for (int i = 0; i < pets.length; i++) {
                Pet pet = pets[i];
                JPanel petPanel = criarPainelPet(pet, i, pets, dialogoEscolha);
                listaPets.add(petPanel);
                if (i < pets.length - 1) {
                    listaPets.add(Box.createRigidArea(new Dimension(0, 5)));
                }
            }
            
            JScrollPane scrollPane = new JScrollPane(listaPets);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            painelEscolha.add(scrollPane, BorderLayout.CENTER);
            
            JPanel painelBotoes = new JPanel(new FlowLayout());
            painelBotoes.setBackground(Color.WHITE);
            JButton btnCancelar = new JButton("❌ Cancelar");
            btnCancelar.addActionListener(e -> dialogoEscolha.dispose());
            painelBotoes.add(btnCancelar);
            painelEscolha.add(painelBotoes, BorderLayout.SOUTH);
            
            dialogoEscolha.add(painelEscolha);
            dialogoEscolha.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, 
                "Erro ao carregar pets: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JPanel criarPainelPet(Pet pet, int indice, Pet[] todosPets, JDialog dialogo) {
        JPanel petPanel = new JPanel(new BorderLayout());
        petPanel.setBackground(new Color(248, 249, 250));
        petPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(new Color(248, 249, 250));
        
        String statusIcon = pet.isEstaVivo() ? "🟢" : "💀";
        String statusText = pet.isEstaVivo() ? "Vivo" : "Morreu";
        
        JLabel nomePet = new JLabel(statusIcon + " " + pet.getNome() + " (" + statusText + ")");
        nomePet.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel infoPet = new JLabel("Dias vividos: " + pet.getDiasVividos() + " | Status: " + pet.getStatusGeral());
        infoPet.setFont(new Font("Arial", Font.PLAIN, 12));
        infoPet.setForeground(Color.GRAY);
        
        infoPanel.add(nomePet);
        infoPanel.add(infoPet);
        
        petPanel.add(infoPanel, BorderLayout.CENTER);
        
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesPanel.setBackground(new Color(248, 249, 250));
        
        JButton btnJogar = new JButton("🎮 Jogar");
        btnJogar.setFont(new Font("Arial", Font.BOLD, 12));
        btnJogar.setBackground(new Color(144, 238, 144));
        btnJogar.addActionListener(e -> {
            petAtual = pet;
            dialogo.dispose();
            frame.dispose();
            criarJanelaJogo();
        });
        
        JButton btnExcluir = new JButton("🗑️");
        btnExcluir.setFont(new Font("Arial", Font.PLAIN, 10));
        btnExcluir.setBackground(new Color(255, 182, 193));
        btnExcluir.setToolTipText("Excluir este pet");
        btnExcluir.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(dialogo,
                "Tem certeza que deseja excluir " + pet.getNome() + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);
            
            if (resposta == JOptionPane.YES_OPTION) {
                excluirPet(indice, todosPets);
                dialogo.dispose();
                mostrarEscolhaPets();
            }
        });
        
        botoesPanel.add(btnJogar);
        botoesPanel.add(btnExcluir);
        
        petPanel.add(botoesPanel, BorderLayout.EAST);
        
        return petPanel;
    }
    
    private void excluirPet(int indiceParaExcluir, Pet[] pets) {
        try {
            Pet[] novosPets = new Pet[pets.length - 1];
            int novoIndice = 0;
            
            for (int i = 0; i < pets.length; i++) {
                if (i != indiceParaExcluir) {
                    novosPets[novoIndice++] = pets[i];
                }
            }
            
            if (novosPets.length > 0) {
                Pet.salvarPetsCSV(novosPets, ARQUIVO_PETS);
            } else {
                File arquivo = new File(ARQUIVO_PETS);
                arquivo.delete();
            }
            
            JOptionPane.showMessageDialog(frame, 
                "Pet excluído com sucesso!", 
                "Excluir", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, 
                "Erro ao excluir pet: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void criarJanelaJogo() {
        frame = new JFrame("🐾 " + petAtual.getNome());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                sairDoJogo();
            }
        });
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        nomeLabel = new JLabel("🐾 " + petAtual.getNome(), SwingConstants.CENTER);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(nomeLabel, BorderLayout.NORTH);
        
        statusLabel = new JLabel("Status", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(statusLabel, BorderLayout.CENTER);
        
        diasLabel = new JLabel("Dias vividos: 0", SwingConstants.CENTER);
        diasLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(diasLabel, BorderLayout.SOUTH);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 5, 8));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        centerPanel.setBackground(Color.WHITE);
        
        fomeBar = criarBarra("🍽️ Saciedade", new Color(255, 99, 132));
        energiaBar = criarBarra("⚡ Energia", new Color(54, 162, 235));
        diversaoBar = criarBarra("🎮 Diversão", new Color(255, 206, 86));
        saudeBar = criarBarra("❤️ Saúde", new Color(75, 192, 192));
        
        centerPanel.add(fomeBar);
        centerPanel.add(energiaBar);
        centerPanel.add(diversaoBar);
        centerPanel.add(saudeBar);
        
        JPanel botoesPanel = new JPanel(new GridLayout(1, 4, 8, 5));
        botoesPanel.setBackground(Color.WHITE);
        
        String[] acoes = {"🍎 Alimentar", "😴 Dormir", "🎾 Brincar", "💊 Medicar"};
        JButton[] botoesAcao = new JButton[4];
        
        for (int i = 0; i < 4; i++) {
            botoesAcao[i] = new JButton(acoes[i]);
            botoesAcao[i].setFont(new Font("Arial", Font.BOLD, 11));
            botoesAcao[i].addActionListener(criarAcaoListener(i, botoesAcao));
            botoesPanel.add(botoesAcao[i]);
        }
        
        centerPanel.add(botoesPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(245, 245, 245));
        
        JButton btnSalvar = new JButton("💾 Salvar");
        btnSalvar.addActionListener(e -> salvarJogo());
        bottomPanel.add(btnSalvar);
        
        JButton btnMenu = new JButton("🏠 Menu");
        btnMenu.addActionListener(e -> voltarAoMenu());
        bottomPanel.add(btnMenu);
        
        JButton btnSair = new JButton("🚪 Sair");
        btnSair.addActionListener(e -> sairDoJogo());
        bottomPanel.add(btnSair);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        frame.setVisible(true);
        
        Timer uiTimer = new Timer(1000, e -> atualizarInterface());
        uiTimer.start();
        
        atualizarInterface();
    }
    
    private JProgressBar criarBarra(String nome, Color cor) {
        JProgressBar barra = new JProgressBar(0, 100);
        barra.setStringPainted(true);
        barra.setForeground(cor);
        barra.setFont(new Font("Arial", Font.BOLD, 12));
        barra.setBorder(BorderFactory.createTitledBorder(nome));
        return barra;
    }
    
    private ActionListener criarAcaoListener(int indice, JButton[] botoes) {
        return e -> {
            if (petAtual != null && petAtual.isEstaVivo()) {
                switch (indice) {
                    case 0: petAtual.alimentar(); break;
                    case 1: petAtual.dormir(); break;
                    case 2: petAtual.brincar(); break;
                    case 3: petAtual.medicar(); break;
                }
                atualizarInterface();
                salvarAutomatico();
            } else if (petAtual != null && !petAtual.isEstaVivo()) {
                JOptionPane.showMessageDialog(frame, 
                    "💀 " + petAtual.getNome() + " morreu!\nVolte ao menu para escolher outro pet.", 
                    "Game Over", 
                    JOptionPane.WARNING_MESSAGE);
                
                for (JButton botao : botoes) {
                    botao.setEnabled(false);
                }
            }
        };
    }
    
    private void atualizarInterface() {
        if (petAtual != null) {
            statusLabel.setText(petAtual.getStatusGeral());
            diasLabel.setText("Dias vividos: " + petAtual.getDiasVividos());
            
            fomeBar.setValue(100 - petAtual.getFome().getValor());
            energiaBar.setValue(petAtual.getEnergia().getValor());
            diversaoBar.setValue(petAtual.getDiversao().getValor());
            saudeBar.setValue(petAtual.getSaude().getValor());
            
            frame.setTitle("🐾 " + petAtual.getNome() + " - " + petAtual.getDiasVividos() + " dias");
        }
    }
    
    private void salvarJogo() {
        try {
            Pet[] petsExistentes = new Pet[0];
            File arquivo = new File(ARQUIVO_PETS);
            if (arquivo.exists()) {
                petsExistentes = Pet.carregarPetsCSV(ARQUIVO_PETS);
            }
            
            boolean petExiste = false;
            for (int i = 0; i < petsExistentes.length; i++) {
                if (petsExistentes[i].getNome().equals(petAtual.getNome())) {
                    petsExistentes[i] = petAtual;
                    petExiste = true;
                    break;
                }
            }
            
            if (!petExiste) {
                Pet[] novoArray = new Pet[petsExistentes.length + 1];
                System.arraycopy(petsExistentes, 0, novoArray, 0, petsExistentes.length);
                novoArray[petsExistentes.length] = petAtual;
                petsExistentes = novoArray;
            }
            
            Pet.salvarPetsCSV(petsExistentes, ARQUIVO_PETS);
            JOptionPane.showMessageDialog(frame, 
                "Jogo salvo!", 
                "Salvar", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, 
                "Erro ao salvar: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarAutomatico() {
        try {
            Pet[] petsExistentes = new Pet[0];
            File arquivo = new File(ARQUIVO_PETS);
            if (arquivo.exists()) {
                petsExistentes = Pet.carregarPetsCSV(ARQUIVO_PETS);
            }
            
            boolean petExiste = false;
            for (int i = 0; i < petsExistentes.length; i++) {
                if (petsExistentes[i].getNome().equals(petAtual.getNome())) {
                    petsExistentes[i] = petAtual;
                    petExiste = true;
                    break;
                }
            }
            
            if (!petExiste) {
                Pet[] novoArray = new Pet[petsExistentes.length + 1];
                System.arraycopy(petsExistentes, 0, novoArray, 0, petsExistentes.length);
                novoArray[petsExistentes.length] = petAtual;
                petsExistentes = novoArray;
            }
            
            Pet.salvarPetsCSV(petsExistentes, ARQUIVO_PETS);
        } catch (Exception e) {
            System.err.println("Erro no salvamento automático: " + e.getMessage());
        }
    }
    
    private void voltarAoMenu() {
        int resposta = JOptionPane.showConfirmDialog(frame, 
            "Salvar antes de voltar ao menu?", 
            "Voltar ao Menu", 
            JOptionPane.YES_NO_CANCEL_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            salvarJogo();
            petAtual.pararTimer();
            frame.dispose();
            mostrarTelaInicial();
        } else if (resposta == JOptionPane.NO_OPTION) {
            petAtual.pararTimer();
            frame.dispose();
            mostrarTelaInicial();
        }
    }
    
    private void sairDoJogo() {
        int resposta = JOptionPane.showConfirmDialog(frame, 
            "Salvar antes de sair?", 
            "Sair", 
            JOptionPane.YES_NO_CANCEL_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            salvarJogo();
            System.exit(0);
        } else if (resposta == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PetGUI();
        });
    }
}
