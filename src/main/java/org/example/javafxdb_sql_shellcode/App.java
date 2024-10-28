package org.example.javafxdb_sql_shellcode;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.javafxdb_sql_shellcode.db.ConnDbOps;

/**
 * JavaFX App
 */
public class App extends Application {

    private Stage primaryStage;
    private static Scene scene;
    private static ConnDbOps cdbop;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        showScene1();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));

        return fxmlLoader.load();
    }

    //Imported methods
    public void changeScene() {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("db_interface_gui.fxml"));

            Scene currentScene = primaryStage.getScene();
            Parent currentRoot = currentScene.getRoot();
            currentScene.getStylesheets().add("style.css");
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), currentRoot);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {


                Scene newScene = new Scene(newRoot,850, 560);
                primaryStage.setScene(newScene);

            });

            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showScene1() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("splash_screen.fxml"));
            Scene scene = new Scene(root, 850, 560);
            scene.getStylesheets().add("style.css");
            primaryStage.setScene(scene);
            primaryStage.show();
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//End imported methods to integrate

    public static void main(String[] args) {
        cdbop = new ConnDbOps();
        Scanner scan = new Scanner(System.in);

        char input;
        do {
            System.out.println(" ");
            System.out.println("============== Menu ==============");
            System.out.println("| To start GUI,           press 'g' |");
            System.out.println("| To connect to DB,       press 'c' |");
            System.out.println("| To display all users,   press 'a' |");
            System.out.println("| To insert to the DB,    press 'i' |");
            System.out.println("| To query by name,       press 'q' |");
            System.out.println("| To exit,                press 'e' |");
            System.out.println("===================================");
            System.out.print("Enter your choice: ");
            input = scan.next().charAt(0);

            switch (input) {
                case 'g':
                     launch(args); //GUI
                    break;

                case 'c':
                    cdbop.connectToDatabase(); //Your existing method
                    break;
                case 'a':
                    cdbop.listAllUsers(); //all users in DB
                    break;

                case 'i':
                    System.out.print("Enter First Name: ");
                    String firstName = scan.next();
                    System.out.print("Enter Last Name: ");
                    String lastName = scan.next();
                    System.out.print("Enter Major: ");
                    String major = scan.next();
                    System.out.print("Enter Dept: ");
                    String department = scan.next();
                    cdbop.insertUser(firstName, lastName, major, department); //Your insertUser method
                    break;
                case 'q':
                    System.out.print("Enter the first name to query: ");
                    String queryFirstName = scan.next();

                    System.out.print("Enter the last name to query: ");
                    String queryLastName = scan.next();
                    cdbop.queryUserByName(queryFirstName, queryLastName); //Your queryUserByName method
                    break;
                case 'e':
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println(" ");
        } while (input != 'e');

        scan.close();
    }
}
