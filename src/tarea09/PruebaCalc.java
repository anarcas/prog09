/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea09;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author anaranjo
 */
public class PruebaCalc extends Application {

    @Override
    public void start(Stage primaryStage){
        
        // Uso del botón control
        Button btn=new Button();
        btn.setText("Say 'Hello World!");
        
        // Evento del botón
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        
        // Uso del contenedor StackPane
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        // Uso de la escena
        Scene scene = new Scene(root, 300, 250);
        // Barra de título
        primaryStage.setTitle("Hello World!");
        // Escena y escenario principal
        primaryStage.setScene(scene);
        // Mostrar el escenario
        primaryStage.show();
        
    }
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    
}
