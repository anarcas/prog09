package tarea09;

//Librerías para poder utilizar JavaFX
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//Librerías específicas para evaluar expresiones exp4j
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**  
 * La típica calculadora básica para realizar cálculos artitméticos como la suma, 
 * resta, multiplicación y división, en la que se permite el cálculo de 
 * expresiones combinadas.
 * 
 * @author profesorado
 */
public class Calculadora extends Application{
    //----------------------------------------------
    //          Declaración de variables 
    //----------------------------------------------
    private final TextField pantalla = new TextField();
    private final StringBuilder expresion = new StringBuilder();
    
    
    //----------------------------------------------
    //          Declaración de variables auxiliares 
    //----------------------------------------------  


    
    /*El método start es el punto de entrada para una aplicación JavaFX.
    Su función principal es inicializar y mostrar la interfaz 
    gráfica de usuario (GUI) de la aplicación. Se crea un diseño (layout), 
    se añaden controles (botones, etiquetas, campos, ...) y se crea la escena
    con un estilo, y se muestra el escenario.*/
    
    @Override
    public void start(Stage escenario) {
         escenario.setTitle("Mi calculadora");
        escenario.setResizable(false);

        // Icono
        try {
            Image icono = new Image(getClass().getResourceAsStream("logoCalcu.png"));
            escenario.getIcons().add(icono);
        } catch (Exception e) {
            System.out.println("Error cargando el icono: " + e.getMessage());
        }

        // Panel principal
        BorderPane root = new BorderPane();

        // Pantalla
        pantalla.setEditable(false);
        pantalla.setAlignment(Pos.CENTER_RIGHT);
        pantalla.setStyle("-fx-font-size: 20px;");
        root.setTop(pantalla);
        BorderPane.setMargin(pantalla, new Insets(10));

        // Botones y su disposición
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        String[][] botones = {
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"0", ".", "=", "+"},
            {"(", ")", "C", "<"}
        };

        for (int fila = 0; fila < botones.length; fila++) {
            for (int col = 0; col < botones[fila].length; col++) {
                String texto = botones[fila][col];
                Button btn = new Button(texto);
                btn.setPrefSize(50, 50);
                btn.setOnAction(e -> procesoDeEntrada(texto));
                grid.add(btn, col, fila);
            }
        }

        root.setCenter(grid);

        // Escena y estilos
        Scene escena = new Scene(root, 300, 400);
        try {
            escena.getStylesheets().add(getClass().getResource("calculadora.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("Error cargando el CSS: " + e.getMessage());
        }

        escenario.setScene(escena);
        escenario.show();        
    }

    /**
    * El método procesoDeEntrada maneja la entrada de datos en una calculadora.
    * Toma una cadena de texto (entrada) y realiza diferentes acciones 
    * según el contenido de esa cadena, agregando al campo de texto, estableciendo 
    * el estado, controlando la adición de puntos decimales para evitar múltiples 
    * decimales en un número o evaluando la expresión mátemática para mostrar el 
    * resultado haciendo uso de la librería Exp4J.
    * @param entrada Texto recogido de los diferentes textoBotones de  la calculadora.
    */
    public void procesoDeEntrada(String entrada) {
        switch (entrada) {
            case "C":
                expresion.setLength(0);
                pantalla.setText("");
                break;
            case "<":
                if (expresion.length() > 0) {
                    expresion.deleteCharAt(expresion.length() - 1);
                    pantalla.setText(expresion.toString());
                }
                break;
            case "=":
                try {
                String exprStr = expresion.toString();
                if (exprStr.matches("[0-9+\\-*/().]*")) {
                    Expression exp = new ExpressionBuilder(exprStr).build();
                    double resultado = exp.evaluate();
                    pantalla.setText(String.valueOf(resultado));
                    expresion.setLength(0);
                    expresion.append(resultado);
                } else {
                    pantalla.setText("Expresión inválida");
                }
            } catch (Exception e) {
                pantalla.setText("Error");
            }
            break;
            case ".":
                if (expresion.length() == 0 || !expresion.toString().matches(".*\\d$")) {
                    pantalla.setText("Error decimal");
                    break;
                }
                expresion.append(".");
                pantalla.setText(expresion.toString());
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (expresion.length() > 0 && expresion.toString().matches(".*[0-9)]$")) {
                    expresion.append(entrada);
                    pantalla.setText(expresion.toString());
                }
                break;
            case "(":
                if (expresion.length() > 0 && expresion.toString().matches(".*[0-9)]$")) {
                    expresion.append("*(");
                } else {
                    expresion.append("(");
                }
                pantalla.setText(expresion.toString());
                break;
            case ")":
                long abre = expresion.chars().filter(ch -> ch == '(').count();
                long cierra = expresion.chars().filter(ch -> ch == ')').count();
                if (abre > cierra && expresion.toString().matches(".*[0-9)]$")) {
                    expresion.append(")");
                    pantalla.setText(expresion.toString());
                }
                break;
            default:
                expresion.append(entrada);
                pantalla.setText(expresion.toString());
                break;
        }
    }

    /**
     * Programa principal, lanza la aplicación.
     * @param args Argumentos o parámetros (no hay en este caso)
     */
    public static void main(String[] args) {
        launch(args);
    }
}