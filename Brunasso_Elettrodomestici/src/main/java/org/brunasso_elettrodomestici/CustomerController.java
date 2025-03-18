package org.brunasso_elettrodomestici;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.io.*;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public void initialize(URL url, ResourceBundle resourceBundle) { // called everytime the interface is loaded
        if (loginButton != null && loginRegisterButton != null) { // sets hover effect in login interface
            loginButton.setOnMouseEntered(_ -> loginButton.setEffect(new DropShadow()));
            loginButton.setOnMouseExited(_ -> loginButton.setEffect(null));
            loginRegisterButton.setOnMouseEntered(_ -> loginRegisterButton.setUnderline(true));
            loginRegisterButton.setOnMouseExited(_ -> loginRegisterButton.setUnderline(false));
            loginUsername.setOnMouseEntered(_ -> loginUsername.setEffect(new DropShadow()));
            loginUsername.setOnMouseExited(_ -> loginUsername.setEffect(null));
            loginPassword.setOnMouseEntered(_ -> loginPassword.setEffect(new DropShadow()));
            loginPassword.setOnMouseExited(_ -> loginPassword.setEffect(null));
        }
    }

    @FXML
    private Text
            loginError,
            registerError,
            loginRegisterButton,
            registerLoginButton;

    @FXML
    private TextField loginUsername,
            registerUsername;

    @FXML
    private PasswordField loginPassword,
            registerPassword,
            confirmPassword;

    @FXML
    private Button loginButton,
                   registerButton;

    private final CustomerModel model;                      // reference to Model


    public CustomerController() { // constructor
        model = CustomerModel.getInstance();
    }


    @FXML
    private void login() { // manages customer's login
        String username = loginUsername.getText().trim(); // gets username from the interface
        String password = loginPassword.getText().trim(); // gets password from the interface
        try {
            if (username.isEmpty() || password.isEmpty()) { // checks if the customer has entered null values
                loginError.setText("Credenziali incomplete");
                loginError.setVisible(true);
            } else if (model.loginUser(username, password)) { // if the login works, shows get seats interface
                if ("admin".equals(username)) {
                    // Se è l'amministratore, mostra l'interfaccia dell'amministratore
                    showMenuAdministrator();
                } else {
                    // Se è un normale utente, mostra l'interfaccia del cliente
                    showInterfaceClient();
                }
            } else { // if the login doesn't work, shows an error message
                loginError.setText("Credenziali errate, riprovare");
                loginError.setVisible(true);
            }
        } catch (SQLException | NoSuchAlgorithmException e) { // if database isn't reachable
            loginError.setText("Database non raggiungibile");
            loginError.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void register() { // manages customer's registration
        String username = registerUsername.getText().trim(); // gets username from the interface
        String password = registerPassword.getText().trim(); // gets password from the interface
        String confirmedPassword = confirmPassword.getText().trim(); // gets confirmed password from the interface
        try {
            if (username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()) { // checks if the customer has entered null values
                registerError.setText("Credenziali incomplete");
                registerError.setVisible(true);
            } else if (!validPassword(password)) { // checks if the password respects the standard
                registerError.setText("La password deve contenere almeno 8 caratteri, una lettera maiuscola, un carattere speciale e un numero");
                registerError.setVisible(true);
            } else if (!confirmedPassword.equals(password)) { // checks if the password isn't correctly confirmed
                registerError.setText("Conferma password errata");
                registerError.setVisible(true);
            } else if (username.contains(" ") || password.contains(" ")) { // checks if username or password contain spaces
                registerError.setText("Username e password non possono contenere spazi");
                registerError.setVisible(true);
            } else if (model.registerUser(username, password)) { // if the register works, shows login interface
                showLoginInterface();
            } else { // checks if the username is available
                registerError.setText("Username non disponibile");
                registerError.setVisible(true);
            }
        } catch (SQLException | NoSuchAlgorithmException | IOException e) { // if database isn't reachable
            registerError.setText("Database non raggiungibile");
            registerError.setVisible(true);
        }
    }

    @FXML
    private void showLoginInterface() throws IOException { // switches the interface to the login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginInterface.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) registerUsername.getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true); // sets fullscreen
        initializeLoginInterfaceElements(scene); // initializes interface's elements
        stage.show(); // shows the interface
    }

    @FXML
    private void showRegisterInterface() throws IOException { // switches the interface to the registration
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterInterface.fxml")); // loads the fxml containing the interface
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginUsername.getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true); // sets fullscreen
        initializeRegisterInterfaceElements(scene); // initializes interface's elements
        stage.show(); // shows the interface
    }

    @FXML
    private void showMenuAdministrator() throws IOException { // switches the interface to the login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/elettrodomestici/brunasso_vittorio_elettrodomestici/amministratore_menu.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginUsername.getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true); // sets fullscreen
        initializeLoginInterfaceElements(scene); // initializes interface's elements
        stage.show(); // shows the interface
    }


    @FXML
    private void showInterfaceClient() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/elettrodomestici/brunasso_vittorio_elettrodomestici/client_view.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginUsername.getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true); // Apre a schermo intero
        initializeLoginInterfaceElements(scene); // Inizializza gli elementi dell'interfaccia
        stage.show(); // Mostra la finestra
    }

    @FXML
    private void initializeRegisterInterfaceElements(Scene scene) { // initializes RegisterInterface's elements
        registerButton = (Button) scene.lookup("#registerButton");
        registerLoginButton = (Text) scene.lookup("#registerLoginButton");
        registerUsername = (TextField) scene.lookup("#registerUsername");
        registerPassword = (PasswordField) scene.lookup("#registerPassword");
        confirmPassword = (PasswordField) scene.lookup("#confirmPassword");
        registerUsername.setOnMouseEntered(_ -> registerUsername.setEffect(new DropShadow()));
        registerUsername.setOnMouseExited(_ -> registerUsername.setEffect(null));
        registerPassword.setOnMouseEntered(_ -> registerPassword.setEffect(new DropShadow()));
        registerPassword.setOnMouseExited(_ -> registerPassword.setEffect(null));
        confirmPassword.setOnMouseEntered(_ -> confirmPassword.setEffect(new DropShadow()));
        confirmPassword.setOnMouseExited(_ -> confirmPassword.setEffect(null));
        registerButton.setOnMouseEntered(_ -> registerButton.setEffect(new DropShadow()));
        registerButton.setOnMouseExited(_ -> registerButton.setEffect(null));
        registerLoginButton.setOnMouseEntered(_ -> registerLoginButton.setUnderline(true));
        registerLoginButton.setOnMouseExited(_ -> registerLoginButton.setUnderline(false));
    }

    @FXML
    private void initializeLoginInterfaceElements(Scene scene) { // initializes LoginInterface's elements
        loginButton = (Button) scene.lookup("#loginButton");
        loginRegisterButton = (Text) scene.lookup("#loginRegisterButton");
        loginUsername = (TextField) scene.lookup("#loginUsername");
        loginPassword = (PasswordField) scene.lookup("#loginPassword");
        loginButton.setOnMouseEntered(_ -> loginButton.setEffect(new DropShadow()));
        loginButton.setOnMouseExited(_ -> loginButton.setEffect(null));
        loginUsername.setOnMouseEntered(_ -> loginUsername.setEffect(new DropShadow()));
        loginUsername.setOnMouseExited(_ -> loginUsername.setEffect(null));
        loginPassword.setOnMouseEntered(_ -> loginPassword.setEffect(new DropShadow()));
        loginPassword.setOnMouseExited(_ -> loginPassword.setEffect(null));
        loginRegisterButton.setOnMouseEntered(_ -> loginRegisterButton.setUnderline(true));
        loginRegisterButton.setOnMouseExited(_ -> loginRegisterButton.setUnderline(false));
    }

    private boolean validPassword(String password) { // checks if the password respects the standard
        String regex = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"|,.<>?])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return password.matches(regex);
    }
}