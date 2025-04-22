package com.trabalhopratico.app;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import com.trabalhopratico.model.Atracao;
import com.trabalhopratico.model.AtracaoCliente;
import com.trabalhopratico.model.AtracaoReport;
import com.trabalhopratico.model.Bilheteria;
import com.trabalhopratico.model.BilheteriaReport;
import com.trabalhopratico.model.Cliente;
import com.trabalhopratico.model.FormaPagamento;
import com.trabalhopratico.model.Ingresso;
import com.trabalhopratico.persistence.dao.AtracaoClienteDAO;
import com.trabalhopratico.persistence.dao.AtracaoDAO;
import com.trabalhopratico.persistence.dao.BilheteriaDAO;
import com.trabalhopratico.persistence.dao.ClienteDAO;
import com.trabalhopratico.persistence.dao.IngressoDAO;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application
{

    /* Persistência */
    // Objetos DAO para acesso ao banco de dados para cada entidade do sistema
    private AtracaoClienteDAO atracaoClienteDAO;
    private AtracaoDAO atracaoDAO;
    private BilheteriaDAO bilheteriaDAO;
    private ClienteDAO clienteDAO;
    private IngressoDAO ingressoDAO;

    /* Homepage */
    // Componentes da tela de login
    private VBox loginRoot;           // Container principal da tela de login
    private TextField usernameField;  // Campo para inserir nome de usuário
    private TextField passwordField;  // Campo para inserir senha
    private Button loginBtn;          // Botão para realizar login
    private Button signupBtn;         // Botão para ir para tela de cadastro
    private Scene home;               // Cena da tela de login

    /* Registro */
    // Componentes da tela de cadastro
    private Scene signupScene;            // Cena da tela de cadastro
    private VBox signupRoot;              // Container principal da tela de cadastro
    private TextField nameField;          // Campo para inserir nome completo
    private TextField emailField;         // Campo para inserir email
    private TextField signupUsernameField; // Campo para inserir nome de usuário
    private PasswordField signupPasswordField; // Campo para inserir senha
    private Button backBtn;               // Botão para voltar à tela de login
    private Button registerBtn;           // Botão para confirmar cadastro

    /* Dashboard */
    // Componentes da tela principal após login
    private Scene dashboardScene;     // Cena do dashboard
    private HBox dashboardRoot;       // Container principal do dashboard
    private Button attractionsBtn;    // Botão para ver atrações disponíveis
    private Button buyBtn;            // Botão para comprar ingressos
    private Button reportBtn; // Botão para ver o relatório

    /* Atrações */
    // Componentes da tela de atrações
    private Scene atracoesScene; // Cena das atrações
    private VBox attractionsList; // Container secundário das atrações
    private ScrollPane scrollPane; // Rolagem para visualização de atrações
    private Button backBtn2; // Botão de voltar
    private VBox attractionsRoot; // Container principal das atrações

    /* Comprar ingressos */
    // Componentes da tela de bilheteria
    private Scene bilheteriaScene;    // Cena da bilheteria
    private VBox bilheteriaRoot;      // Container principal da bilheteria

    /* Aplicação principal */
    private Stage arg0;               // Janela principal da aplicação

    /* Usuário */
    private Cliente usuario;          // Objeto que armazena dados do usuário logado

    @Override
    public void start(Stage arg0) {
        // Método inicial da aplicação JavaFX
        this.arg0 = arg0;

        // Inicializa os objetos DAO para acesso ao banco de dados
        this.atracaoClienteDAO = new AtracaoClienteDAO();
        this.atracaoDAO = new AtracaoDAO();
        this.bilheteriaDAO = new BilheteriaDAO();
        this.clienteDAO = new ClienteDAO();
        this.ingressoDAO = new IngressoDAO();

        // Cria as diferentes telas da aplicação
        createHomeScene();        // Cria a tela de login
        createSignupScene();      // Cria a tela de cadastro
        createDashboardScene();   // Cria a tela principal
        createAttractionsScene(); // Cria a tela de atrações

        // Configura a janela principal
        arg0.setTitle("Trabalho Prático I");
        arg0.setScene(this.home); // Define a tela inicial como a tela de login
        arg0.show();              // Exibe a janela
    }

    public void createHomeScene() {
        // Cria a tela de login
        
        // Campo de usuário
        this.usernameField = new TextField();
        this.usernameField.getStyleClass().add("login_tf");
        this.usernameField.setPromptText("Insira seu nome de usuário");

        // Campo de senha
        this.passwordField = new PasswordField();
        this.passwordField.getStyleClass().add("login_tf");
        this.passwordField.setPromptText("Insira sua senha");
        
        // Botão de login
        this.loginBtn = new Button("Entrar");
        this.loginBtn.getStyleClass().add("login_btn");
        this.loginBtn.setOnAction(e -> {
            // Busca todos os clientes no banco de dados
            List<Cliente> clientes = this.clienteDAO.selectAll();

            // Verifica se as credenciais estão corretas
            for (Cliente c: clientes) {
                if (c.getUsuario().equals(this.usernameField.getText()) &&
                    c.getSenha().equals(this.passwordField.getText())) {
                        // Limpa os campos após login bem-sucedido
                        this.usernameField.setText("");
                        this.passwordField.setText("");

                        // Armazena o usuário logado e navega para o dashboard
                        this.usuario = c;
                        this.arg0.setScene(dashboardScene);
                        return;
                    }
            }

            // Exibe alerta se as credenciais estiverem incorretas
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Tentativa de login inválida");
            alert.setHeaderText("Usuário e/ou senha incorreta.");
            alert.setContentText("Por favor, informe corretamente os dados de acesso.");
            alert.showAndWait();
        });

        // Botão para ir para tela de cadastro
        this.signupBtn = new Button("Cadastrar-se");
        this.signupBtn.getStyleClass().add("login_btn");
        this.signupBtn.setOnAction(e -> this.arg0.setScene(signupScene));

        // Organiza os componentes na tela
        this.loginRoot = new VBox(10, usernameField, passwordField, loginBtn, signupBtn);
        this.loginRoot.setAlignment(Pos.CENTER);
        this.loginRoot.setPadding(new Insets(40));

        // Cria a cena e aplica o estilo CSS
        this.home = new Scene(loginRoot, 360, 420);
        this.home.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
    }

    public void createSignupScene() {
        // Cria a tela de cadastro
        
        // Campo para nome completo
        this.nameField = new TextField();
        this.nameField.getStyleClass().add("login_tf");
        this.nameField.setPromptText("Nome completo");

        // Campo para email
        this.emailField = new TextField();
        this.emailField.getStyleClass().add("login_tf");
        this.emailField.setPromptText("Email");

        // Campo para nome de usuário
        this.signupUsernameField = new TextField();
        this.signupUsernameField.getStyleClass().add("login_tf");
        this.signupUsernameField.setPromptText("Usuário");

        // Campo para senha
        this.signupPasswordField = new PasswordField();
        this.signupPasswordField.getStyleClass().add("login_tf");
        this.signupPasswordField.setPromptText("Senha");

        // Botão para voltar à tela de login
        this.backBtn = new Button("Voltar");
        this.backBtn.getStyleClass().add("login_btn");
        this.backBtn.setOnAction(e -> this.arg0.setScene(home));

        // Botão para confirmar cadastro
        this.registerBtn = new Button("Registrar");
        this.registerBtn.getStyleClass().add("login_btn");
        this.registerBtn.setOnAction(e -> {
            // Verifica se todos os campos foram preenchidos
            if (this.nameField.getText().isEmpty() ||
                this.emailField.getText().isEmpty() ||
                this.signupUsernameField.getText().isEmpty() ||
                this.signupPasswordField.getText().isEmpty()
            ) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Tentativa de cadastro inválida");
                alert.setHeaderText("Campos não preenchidos");
                alert.setContentText("Por favor, preencha todos os campos.");
                alert.showAndWait();
                return;
            }

            // Busca todos os clientes para verificar duplicidade
            List<Cliente> clientes = this.clienteDAO.selectAll();

            // Verifica se o email ou usuário já existem
            for (Cliente c: clientes) {
                if (c.getEmail().equals(this.emailField.getText())) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Tentativa de cadastro inválida");
                    alert.setHeaderText("E-mail já cadastrado");
                    alert.setContentText("Por favor, insira outro endereço de e-mail.");
                    alert.showAndWait();
                    return;
                } else if (c.getUsuario().equals(this.signupUsernameField.getText())) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Tentativa de cadastro inválida");
                    alert.setHeaderText("Nome de usuário já cadastrado");
                    alert.setContentText("Por favor, insira outro nome de usuário.");
                    alert.showAndWait();
                    return;
                }
            }

            // Cria um novo cliente com os dados informados
            Cliente cliente = new Cliente(
                0, nameField.getText(),
                emailField.getText(), 
                signupUsernameField.getText(), 
                signupPasswordField.getText()
            );

            // Salva o cliente no banco de dados e volta para tela de login
            this.clienteDAO.create(cliente);
            this.arg0.setScene(home);

            // Limpa os campos após o cadastro
            this.nameField.setText("");
            this.emailField.setText("");
            this.signupUsernameField.setText("");
            this.signupPasswordField.setText("");
        });

        // Organiza os componentes na tela
        this.signupRoot = new VBox(10, nameField, emailField, signupUsernameField, signupPasswordField, registerBtn, backBtn);
        this.signupRoot.setAlignment(Pos.CENTER);
        this.signupRoot.setPadding(new Insets(40));

        // Cria a cena e aplica o estilo CSS
        this.signupScene = new Scene(signupRoot, 360, 500);
        this.signupScene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
    }

    public void createDashboardScene() {
        // Cria a tela principal (dashboard)
        
        // Botão para ver atrações disponíveis
        this.attractionsBtn = new Button("Atrações disponíveis");
        this.attractionsBtn.setMinSize(200, 200);
        this.attractionsBtn.getStyleClass().add("square_btn");
        this.attractionsBtn.setOnAction(e -> this.arg0.setScene(atracoesScene));

        // Botão para comprar ingressos
        this.buyBtn = new Button("Comprar ingressos");
        this.buyBtn.setMinSize(200, 200);
        this.buyBtn.getStyleClass().add("square_btn");
        this.buyBtn.setOnAction(e -> {
            createBilheteriaScene(); // Cria a tela de bilheteria dinamicamente
            this.arg0.setScene(bilheteriaScene);
        });
    
        // Botão para acessar relatórios
        this.reportBtn = new Button("Relatório");
        this.reportBtn.setMinSize(200, 200);
        this.reportBtn.getStyleClass().add("square_btn");
        this.reportBtn.setOnAction(e -> {
            createReportScene(); // Cria a tela de relatório dinamicamente
            this.arg0.setScene(reportScene);
        });


        // Organiza os botões horizontalmente
        this.dashboardRoot = new HBox(40, attractionsBtn, buyBtn, reportBtn);
        this.dashboardRoot.setAlignment(Pos.CENTER);
        this.dashboardRoot.setPadding(new Insets(60));
    
        // Cria a cena e aplica o estilo CSS
        this.dashboardScene = new Scene(dashboardRoot, 800, 600);
        this.dashboardScene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
    }

    public void createBilheteriaScene() {
        // Cria a tela de bilheteria
        
        // Busca todas as bilheterias no banco de dados
        List<Bilheteria> bilheterias = this.bilheteriaDAO.selectAll();
    
        // Container para listar as bilheterias
        VBox bilheteriaList = new VBox(20);
        bilheteriaList.setPadding(new Insets(20));
        bilheteriaList.setAlignment(Pos.TOP_CENTER);
    
        // Para cada bilheteria, cria um card com suas informações
        for (Bilheteria b : bilheterias) {
            VBox box = new VBox(10);
            box.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10; -fx-padding: 15;");
            box.setPrefWidth(600);
    
            // Formata as informações da bilheteria
            String info = String.format(
                "Bilheteria Nº %d\nPreço: R$ %.2f\nHorário de funcionamento: %s\nData de funcionamento: %s\nIngressos disponíveis: %d",
                b.getID(),
                b.getPreco(),
                b.getHorarioFechamento().toString(),
                b.getFuncionamento().toString(),
                b.getQuantidadeDisponivel()
            );
    
            javafx.scene.control.Label label = new Label(info);
            label.setStyle("-fx-font-size: 14px;");
    
            // Adiciona botão de compra apenas se houver ingressos disponíveis
            if (b.getQuantidadeDisponivel() > 0) {
                Button buyBtn2 = new Button("Comprar ingresso");
                buyBtn2.getStyleClass().add("login_btn");
                buyBtn2.setOnAction(e -> showAttractionSelectionModal(b));
                box.getChildren().addAll(label, buyBtn2);
            } else {
                box.getChildren().addAll(label);
            }
            
            bilheteriaList.getChildren().add(box);
        }
    
        // Botão para voltar ao dashboard
        Button voltarBtn = new Button("Voltar");
        voltarBtn.getStyleClass().add("login_btn");
        voltarBtn.setOnAction(e -> this.arg0.setScene(dashboardScene));
        bilheteriaList.getChildren().add(voltarBtn);
    
        // Adiciona rolagem para a lista de bilheterias
        ScrollPane scrollPane = new ScrollPane(bilheteriaList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(20));
        scrollPane.setStyle("-fx-background: #f0f0f0;");
    
        // Cria a cena e aplica o estilo CSS
        this.bilheteriaRoot = new VBox(scrollPane);
        this.bilheteriaScene = new Scene(bilheteriaRoot, 800, 600);
        this.bilheteriaScene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
    }
    
    private void showAttractionSelectionModal(Bilheteria bilheteria) {
        // Exibe modal para selecionar a atração ao comprar um ingresso
        
        // Busca todas as atrações no banco de dados
        List<Atracao> atracoes = this.atracaoDAO.selectAll();

        // Cria uma nova janela modal
        Stage modal = new Stage();
        modal.setTitle("Escolha a atração");

        // Container principal do modal
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Instruções para o usuário
        Label label = new javafx.scene.control.Label("Selecione a atração para associar ao ingresso:");
        label.setStyle("-fx-font-size: 14px;");

        // Dropdown para selecionar a atração
        ComboBox<Atracao> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(atracoes);
        comboBox.setPromptText("Selecione uma atração");

        // Botão para continuar o processo de compra
        Button continuarBtn = new Button("Continuar para pagamento");
        continuarBtn.setDisable(true); // Inicialmente desabilitado até que uma atração seja selecionada

        // Habilita o botão quando uma atração é selecionada
        comboBox.setOnAction(e -> {
            continuarBtn.setDisable(comboBox.getValue() == null);
        });

        // Ao clicar em continuar, abre o modal de pagamento
        continuarBtn.setOnAction(e -> {
            Atracao selected = comboBox.getValue();
            modal.close();
            showPaymentModal(bilheteria, selected);
        });

        // Organiza os componentes no modal
        root.getChildren().addAll(label, comboBox, continuarBtn);

        // Configura e exibe o modal
        Scene scene = new Scene(root, 400, 250);
        modal.setScene(scene);
        modal.initOwner(this.arg0);
        modal.initModality(javafx.stage.Modality.WINDOW_MODAL);
        modal.show();
    }

    private void showPaymentModal(Bilheteria bilheteria, Atracao atracao) {
        // Exibe modal para selecionar a forma de pagamento
        
        // Cria uma nova janela modal
        Stage modal = new Stage();
        modal.setTitle("Escolha o método de pagamento");
    
        // Container principal do modal
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
    
        // Instruções e informações de preço
        Label label = new Label(
            "Escolha o método de pagamento para a bilheteria:\nPreço: R$ " + String.format("%.2f", bilheteria.getPreco())
        );
        label.setWrapText(true);
        label.setStyle("-fx-font-size: 16px;");
    
        // Botões para cada forma de pagamento
        Button pixBtn = new Button("Pix");
        Button creditoBtn = new Button("Cartão de Crédito");
        Button debitoBtn = new Button("Cartão de Débito");
        Button dinheiroBtn = new Button("Dinheiro");

        // Manipulador de evento comum para todos os botões de pagamento
        EventHandler<ActionEvent> handler = evt -> {
            Button source = (Button) evt.getSource();
            String metodo = source.getText();
    
            // Exibe mensagem de confirmação da compra
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pagamento realizado");
            alert.setHeaderText(null);
            alert.setContentText("Ingresso comprado com " + metodo + "!");
            alert.showAndWait();
            
            // Determina o enum FormaPagamento com base no método selecionado
            FormaPagamento forma = null;
            if (metodo.equals("Pix")) {
                forma = FormaPagamento.PIX; 
            } else if (metodo.equals("Cartão de Crédito")) {
                forma = FormaPagamento.CREDITO; 
            } else if (metodo.equals("Cartão de Débito")) {
                forma = FormaPagamento.DEBITO; 
            } else {
                forma = FormaPagamento.DINHEIRO; 
            }
    
            // Cria um novo ingresso no banco de dados
            this.ingressoDAO.create(new Ingresso(
                null,
                new Date(System.currentTimeMillis()),
                forma, 
                usuario, 
                bilheteria
            ));
    
            // Fecha o modal e volta para o dashboard
            modal.close();
            this.arg0.setScene(dashboardScene);
        };
    
        // Associa o manipulador de evento a cada botão de pagamento
        pixBtn.setOnAction(handler);
        creditoBtn.setOnAction(handler);
        debitoBtn.setOnAction(handler);
        dinheiroBtn.setOnAction(handler);
    
        // Organiza os componentes no modal
        root.getChildren().addAll(label, pixBtn, creditoBtn, debitoBtn, dinheiroBtn);
    
        // Configura e exibe o modal
        Scene scene = new Scene(root, 350, 300);
        modal.setScene(scene);
        modal.initOwner(this.arg0);
        modal.initModality(javafx.stage.Modality.WINDOW_MODAL);
        modal.show();
    }
    
    public void createAttractionsScene() {
        // Cria a tela de atrações disponíveis
        
        // Busca todas as atrações no banco de dados
        List<Atracao> atracoes = this.atracaoDAO.selectAll();
    
        // Container para listar as atrações
        this.attractionsList = new VBox(20);
        this.attractionsList.setPadding(new Insets(20));
        this.attractionsList.setAlignment(Pos.TOP_CENTER);
    
        // Para cada atração, cria um card com suas informações
        for (Atracao a : atracoes) {
            VBox atracaoBox = new VBox(5);
            atracaoBox.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10; -fx-padding: 10;");
            atracaoBox.setPrefWidth(600);
    
            // Formata as informações da atração
            Label info = new Label(
                String.format("Nome: %s\nDescrição: %s\nInício: %s\nFim: %s",
                    a.getNome(), a.getDescricao(), a.getHorarioInicio(), a.getHorarioFim())
            );
            info.setStyle("-fx-font-size: 14px;");
    
            atracaoBox.getChildren().add(info);
    
            // Botão para assistir à atração
            Button watchBtn = new Button("Assistir");
            watchBtn.getStyleClass().add("login_btn");
            watchBtn.setOnAction(e -> {
                // Verifica se o usuário possui um ingresso não utilizado
                Ingresso in = this.ingressoDAO.selectByClientIdIfNotInAtracaoCliente(usuario.getID());
    
                if (in != null) {
                    // Registra que o cliente assistiu à atração
                    this.atracaoClienteDAO.create(
                            new AtracaoCliente(
                                null,
                                a,
                                in,
                                new Timestamp(System.currentTimeMillis()))
                        );
                    
                    // Exibe mensagem de confirmação
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Show assistido!");
                    alert.setHeaderText(a.getNome());
                    alert.setContentText("Você se divertiu muito assistindo a(o) " + a.getNome());
                    alert.showAndWait();
                } else {
                    // Exibe mensagem de erro se não houver ingresso disponível
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Para entrar você deve comprar um ingresso.");
                    alert.setHeaderText(a.getNome());
                    alert.setContentText("Para assistir ao show " + a.getNome() + " você deve comprar um ingresso na seção de bilheteria.");
                    alert.showAndWait();
                }
            });
    
            this.attractionsList.getChildren().addAll(atracaoBox, watchBtn);
        }
    
        // Botão para voltar ao dashboard
        this.backBtn2 = new Button("Voltar");
        this.backBtn2.getStyleClass().add("login_btn");
        this.backBtn2.setOnAction(e -> this.arg0.setScene(dashboardScene));
        this.attractionsList.getChildren().add(this.backBtn2);
    
        // Adiciona rolagem para a lista de atrações
        this.scrollPane = new ScrollPane(this.attractionsList);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setPadding(new Insets(20));
    
        // Cria a cena e aplica o estilo CSS
        this.attractionsRoot = new VBox(scrollPane);
        this.atracoesScene = new Scene(this.attractionsRoot, 800, 600);
        atracoesScene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());        
    }    
    
    private Scene reportScene; // Cena do relatório

    public void createReportScene() {
        // Cria a tabela para exibir o relatório de visualizações de atrações
        TableView<AtracaoReport> atracaoTableView = new TableView<>();

        // Coluna para o nome da atração
        TableColumn<AtracaoReport, String> atracaoNameColumn = new TableColumn<>("Atração");
        atracaoNameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        atracaoNameColumn.setMinWidth(200);

        // Coluna para a porcentagem de visualização
        TableColumn<AtracaoReport, Double> atracaoPercentageColumn = new TableColumn<>("Porcentagem de Visualização");
        atracaoPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("porcentagemVisualizacao"));
        atracaoPercentageColumn.setMinWidth(200);

        // Adiciona as colunas à tabela de atrações
        atracaoTableView.getColumns().addAll(atracaoNameColumn, atracaoPercentageColumn);

        // Preenche a tabela de atrações com dados
        List<Atracao> atracoes = this.atracaoDAO.selectAll();
        List<AtracaoCliente> atracaoClientes = this.atracaoClienteDAO.selectAll();

        for (Atracao atracao : atracoes) {
            long totalVisualizacoes = atracaoClientes.stream()
                .filter(ac -> ac.getAtracao().getID().equals(atracao.getID()))
                .count();
            double porcentagemVisualizacao = (double) totalVisualizacoes / atracaoClientes.size() * 100;
            atracaoTableView.getItems().add(new AtracaoReport(atracao.getNome(), porcentagemVisualizacao));
        }

        // Cria a tabela para exibir o relatório de compras nas bilheterias
        TableView<BilheteriaReport> bilheteriaTableView = new TableView<>();

        // Coluna para o nome da bilheteria
        TableColumn<BilheteriaReport, String> bilheteriaNameColumn = new TableColumn<>("Bilheteria");
        bilheteriaNameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        bilheteriaNameColumn.setMinWidth(200);

        // Coluna para a porcentagem de compra
        TableColumn<BilheteriaReport, Double> bilheteriaPercentageColumn = new TableColumn<>("Porcentagem de Compra");
        bilheteriaPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("porcentagemCompra"));
        bilheteriaPercentageColumn.setMinWidth(200);

        // Adiciona as colunas à tabela de bilheterias
        bilheteriaTableView.getColumns().addAll(bilheteriaNameColumn, bilheteriaPercentageColumn);

        // Preenche a tabela de bilheterias com dados
        List<Bilheteria> bilheterias = this.bilheteriaDAO.selectAll();
        List<Ingresso> ingressos = this.ingressoDAO.selectAll();

        for (Bilheteria bilheteria : bilheterias) {
            long totalCompras = ingressos.stream()
                .filter(in -> in.getBilheteria().getID().equals(bilheteria.getID()))
                .count();
            double porcentagemCompra = (double) totalCompras / ingressos.size() * 100;
            bilheteriaTableView.getItems().add(new BilheteriaReport("Bilheteria " + bilheteria.getID(), porcentagemCompra));
        }

        // Botão para voltar ao dashboard
        Button voltarBtn = new Button("Voltar");
        voltarBtn.getStyleClass().add("login_btn");
        voltarBtn.setOnAction(e -> this.arg0.setScene(dashboardScene));

        // Organiza os componentes na tela
        VBox root = new VBox(20, atracaoTableView, bilheteriaTableView, voltarBtn);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Cria a cena e aplica o estilo CSS
        this.reportScene = new Scene(root, 800, 600);
        this.reportScene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
    }
    public static void main(String[] args)
    {
        // Método principal que inicia a aplicação JavaFX
        launch(args);
    }
}
