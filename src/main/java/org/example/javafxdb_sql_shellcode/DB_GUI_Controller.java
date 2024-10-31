package org.example.javafxdb_sql_shellcode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.example.javafxdb_sql_shellcode.db.Person;
import org.example.javafxdb_sql_shellcode.App;


public class DB_GUI_Controller implements Initializable {

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private Button addBtn;

    @FXML
    private MenuItem darkModeMenuItem;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private MenuItem editMenuItem;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem lightModeMenuItem;

    @FXML
    private AnchorPane anchorPane;

    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person(1, "Jacob", "Smith", "CPIS", "CS"),
                    new Person(2, "Jacob2", "Smith1", "CPIS1", "CS")

            );


    @FXML
    TextField first_name, last_name, department, major;
    @FXML
    private TableView<Person> tv;
    @FXML
    private TableColumn<Person, Integer> tv_id;
    @FXML
    private TableColumn<Person, String> tv_fn, tv_ln, tv_dept, tv_major;

    @FXML
    ImageView img_view;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tv_fn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tv_ln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tv_dept.setCellValueFactory(new PropertyValueFactory<>("dept"));
        tv_major.setCellValueFactory(new PropertyValueFactory<>("major"));


        tv.setItems(data);
    }

    @FXML
    void openDocs(ActionEvent event) {

    }

    @FXML
    void openFileMenu(ActionEvent event) {

    }

    @FXML
    void switchDarkMode(ActionEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("dark-style.css").toExternalForm());
    }

    @FXML
    void switchLightMode(ActionEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }

    @FXML
    protected void addNewRecord() {


        data.add(new Person(
                data.size()+1,
                first_name.getText(),
                last_name.getText(),
                department.getText(),
                major.getText()
        ));
    }

    @FXML
    protected void clearForm() {
        first_name.clear();
        last_name.setText("");
        department.setText("");
        major.setText("");
    }

    @FXML
    protected void closeApplication() {
        System.exit(0);
    }


    @FXML
    protected void editRecord() {
        //Edit method displays ID, try using this to query database?
        Person p= tv.getSelectionModel().getSelectedItem();
        int c=data.indexOf(p);
        Person p2= new Person();
        p2.setId(c+1);
        p2.setFirstName(first_name.getText());
        p2.setLastName(last_name.getText());
        p2.setDept(department.getText());
        p2.setMajor(major.getText());
        data.remove(c);
        data.add(c,p2);
        tv.getSelectionModel().select(c);
    }

    @FXML
    protected void deleteRecord() {
        Person p= tv.getSelectionModel().getSelectedItem();
        data.remove(p);
    }

    @FXML
    protected void showImage() {
        File file= (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
        if(file!=null){
            img_view.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    protected void selectedItemTV(MouseEvent mouseEvent) {
        Person p= tv.getSelectionModel().getSelectedItem();
        first_name.setText(p.getFirstName());
        last_name.setText(p.getLastName());
        department.setText(p.getDept());
        major.setText(p.getMajor());
    }
}