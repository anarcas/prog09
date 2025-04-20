/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea09;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author anaranjo
 */
public class HolaEinstein extends Application {

    @Override
    public void start(Stage primaryStage){
    
    // Definición variable cadena
    String menssage="Hola qué tal!! Niños tenéis que portaros bien!! y Gloría está castigada!!";
    
    // Referencia de la cadena a Text
    Text textRef = new Text(menssage);
    textRef.setLayoutY(25);
    textRef.setTextOrigin(VPos.TOP);
    textRef.setTextAlignment(TextAlignment.JUSTIFY);
    textRef.setWrappingWidth(400);
    textRef.setFill(Color.rgb(187,195,107));
    textRef.setFont(Font.font("SanSerif", FontWeight.BOLD, 24));
    
    // Movimiento al texto
    TranslateTransition transTransition = new TranslateTransition(new Duration(9000), textRef);
    transTransition.setToY(-190);
    transTransition.setInterpolator(Interpolator.LINEAR);
    transTransition.setCycleCount(Timeline.INDEFINITE);
    
    // Uso de la ImageView
    Image image = new Image("https://github.com/serlavio/HelloWorldJavaFx/raw/master/cafeEinstein3.png");
    ImageView imageView = new ImageView(image);
    
    // Uso del contenedor Group
    Group textGroup =new Group (textRef);
    textGroup.setLayoutX(50);
    textGroup.setLayoutY(115);
    textGroup.setClip(new Rectangle(480,60));
    
    // Combinación de ImageView y Group
    Group root = new Group (imageView, textGroup);
    Scene scene = new  Scene(root, 516,516);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Hola Einstein");
    primaryStage.show();
    
    // Empieza la animación de texto
    transTransition.play();
    
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    
}
