package org.example.javafxdb_sql_shellcode;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private HBox dobHbox;

    @FXML
    private Label dobLbl;

    @FXML
    private TextField dobTF;

    @FXML
    private Label dobVldLbl;

    @FXML
    private HBox emailHbox;

    @FXML
    private Label emailLbl;

    @FXML
    private TextField emailTF;

    @FXML
    private Label emailVldLbl;

    @FXML
    private Label fNameLbl;

    @FXML
    private TextField fNameTF;

    @FXML
    private HBox f_NameHbox;

    @FXML
    private Label f_NameVldLbl;

    @FXML
    private Label lNameLbl;

    @FXML
    private TextField lNameTF;

    @FXML
    private HBox l_NameHbox;

    @FXML
    private Label l_NameVldLbl;

    @FXML
    private HBox zipHbox;

    @FXML
    private Label zipLbl;

    @FXML
    private TextField zipTF;

    @FXML
    private Label zipVldLbl;


    //Set visibility of vldLbls to Off unless invalid entry input
    //Color vldLbls to red to bring attention to problem
    //Req says when focus is moved off TextField, validate -> Regex validation -> Visibility of label

    //Disable addUserBtn until all fields have valid input
    @FXML
    void addUser(ActionEvent event) {
        System.out.println("User Added!");

        //Open new scene to simulate navigating to new UI
        BorderPane bp = new BorderPane();
        Label userAddedLbl = new Label("User Added!");
        bp.setCenter(userAddedLbl);
        Stage newStage = new Stage();
        Scene scene = new Scene(bp, 700, 480);
        newStage.setTitle("Add User Button Pressed");
        newStage.setScene(scene);
        newStage.show();
    }

    //Initializes labels and buttons to their startup state, creates listeners
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set visibility of labels before program displays to user
        //Add listeners to apply focus validation
        //Can i make this so that it does not display until focus is given first? then validate?
        f_NameVldLbl.setVisible(false);
        fNameTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // Focus lost
                validateFirstName();
                validateAddUser();
            }
        });

        l_NameVldLbl.setVisible(false);
        lNameTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // Focus lost
                validateLastName();
                validateAddUser();
            }
        });

        emailVldLbl.setVisible(false);
        emailTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // Focus lost
                validateEmail();
                validateAddUser();
            }
        });

        dobVldLbl.setVisible(false);
        dobTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // Focus lost
                validateDateOfBirth();
                validateAddUser();
            }
        });

        zipVldLbl.setVisible(false);
        zipTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // Focus lost
                validateZipCode();
                validateAddUser();
            }
        });

        addBtn.setDisable(true);
    }
    //Validate Add User button if all valid checks pass, which should be guaranteed by following label
    //code. if all labels are invisible and no fields are null, enable button


    /**
     *The method provides validation of the Add User button
     * <p>
     *     If all fields are deemed valid via the logic in other methods, the Add User button is
     *     enabled. Validation is done by boolean flags which validate with similar logic to their
     *     label validating counterparts. These counterparts validate via regex patterns.
     *     The GUI is found in registration-view.fxml
     * </p>
     * @see #validateFirstName()
     * @see #validateLastName()
     * @see #validateEmail()
     * @see #validateDateOfBirth()
     * @see #validateZipCode()
     * @author Selin Saracoglu
     * @version JDK 21
     */
    private void validateAddUser() {
        boolean firstNameValid = !f_NameVldLbl.isVisible() && fNameTF != null && !fNameTF.getText().trim().isEmpty();
        boolean lastNameValid = !l_NameVldLbl.isVisible() && lNameTF != null && !lNameTF.getText().trim().isEmpty();
        boolean zipValid = !zipVldLbl.isVisible() && zipTF != null && !zipTF.getText().trim().isEmpty();
        boolean emailValid = !emailVldLbl.isVisible() && emailTF != null && !emailTF.getText().trim().isEmpty();
        boolean dobValid = !dobVldLbl.isVisible() && dobTF != null && !dobTF.getText().trim().isEmpty();

        addBtn.setDisable(!(firstNameValid && lastNameValid && zipValid && emailValid && dobValid));
    }

    private void validateEmail() {
        //Regex pattern to match any farmingdale.edu email address that starts
        //with a case-insensitive letter and has at least one character following
        final String regexEmailPattern = "(([a-zA-z])(\\w)+)@farmingdale[.]edu";
        final Pattern pattern = Pattern.compile(regexEmailPattern, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(emailTF.getText());

        if (emailTF.getText() == null || emailTF.getText().trim().isEmpty() || !matcher.matches()) {
            emailVldLbl.setVisible(true);
        } else {
            emailVldLbl.setVisible(false);
        }
    }

    private void validateDateOfBirth() {
        //Regex pattern to capture any living human date
        // MM as 0+[1-9] or 1+[0,1, or 2] , "-" or "/" , DD as [01-31], "-" or "/", YYYY as 19## or 20##
        final String regexUSDatePattern =
                "((0[1-9])|(1[0-2]))[-/]((0[1-9])|([12][0-9])|(3[01]))[-/]((19|20)\\d{2})";
        final Pattern pattern = Pattern.compile(regexUSDatePattern, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(dobTF.getText());
        if (dobTF.getText() == null || dobTF.getText().trim().isEmpty() || !matcher.matches()) {
            dobVldLbl.setVisible(true);
        } else {
            dobVldLbl.setVisible(false);
        }
    }

    private void validateZipCode() {
        //Zip Code Regex Validation
        //Get zip string from TF, create regex pattern + mattern
        //Regex pattern for any 5 digits
        final String regexZipPattern = "(\\d{5})";
        final Pattern pattern = Pattern.compile(regexZipPattern, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(zipTF.getText());

        // Example validation: Show the label if the first name is empty
        //Regex for if statement below
        if (zipTF.getText() == null || zipTF.getText().trim().isEmpty() ||!matcher.matches()) {
            zipVldLbl.setVisible(true);
        } else {
            zipVldLbl.setVisible(false);
        }
    }

    //Helper methods to accommodate both first name and last name using the same pattern
    private void validateFirstName() {
        validateName(fNameTF.getText(), f_NameVldLbl);
    }

    private void validateLastName() {
        validateName(lNameTF.getText(), l_NameVldLbl);
    }

    private void validateName(String name, Label validationLabel) {
        //Pattern to capture occasional name's with hyphens or apostrophes as valid
        //Any letters any case [A-Z] including hyphens or apostrophes
        final String regexNamePattern = "([a-zA-Z'-]{2,25})";
        final Pattern pattern = Pattern.compile(regexNamePattern, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(name);
        // Example validation: Show the label if the first name is empty or null
        // or doesn't match pattern
        if (name == null || name.trim().isEmpty() || !matcher.matches()) {
            validationLabel.setVisible(true);
        } else {
            validationLabel.setVisible(false);
        }
    }
}

