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
 * La típica calculadora básica para realizar cálculos artitméticos como la
 * suma, resta, multiplicación y división, en la que se permite el cálculo de
 * expresiones combinadas.
 *
 * @author Antonio Naranjo Castillo
 */
public class Calculadora extends Application {

    //----------------------------------------------
    //          Declaración de variables 
    //----------------------------------------------
    private final TextField pantalla = new TextField(); // Campo de texto para mostrar la expresión y el resultado
    private final StringBuilder expresion = new StringBuilder(); // StringBuilder para construir la expresión matemática

    //----------------------------------------------
    //          Declaración de variables auxiliares 
    //---------------------------------------------- 
    // No se consideran necesarias
    
    
    /**
     * El método start es el punto de entrada para una aplicación JavaFX.
     * Su función principal es inicializar y mostrar la interfaz 
     * gráfica de usuario (GUI) de la aplicación. Se crea un diseño (layout), 
     * se añaden controles (botones, etiquetas, campos, ...) y se crea la escena
     * con un estilo, y se muestra el escenario.
     *
     * @param escenario El escenario principal de la aplicación
     */
    @Override
    public void start(Stage escenario) {
        escenario.setTitle("Mi calculadora");
        escenario.setResizable(false);

        // Icono
        try {
            Image icono = new Image(getClass().getResourceAsStream("logoCalcu.png")); // Cargar icono de la calculadora
            escenario.getIcons().add(icono); // Establecer icono en la ventana
        } catch (Exception e) {
            System.out.println("Error cargando el icono: " + e.getMessage()); // Manejo de errores si no se puede cargar el icono
        }

        // Panel principal
        BorderPane root = new BorderPane(); // Crear un layout principal tipo BorderPane

        // Pantalla
        pantalla.setEditable(false); // Hacer que la pantalla no sea editable por el usuario
        pantalla.setAlignment(Pos.CENTER_RIGHT); // Alinear texto a la derecha en la pantalla
        pantalla.setStyle("-fx-font-size: 20px;"); // Establecer tamaño de fuente en la pantalla
        root.setTop(pantalla); // Colocar la pantalla en la parte superior del BorderPane
        BorderPane.setMargin(pantalla, new Insets(10)); // Añadir margen alrededor de la pantalla

        // Botones y su disposición en un GridPane
        GridPane grid = new GridPane(); // Crear un nuevo GridPane para los botones
        grid.setAlignment(Pos.CENTER); // Centrar el GridPane en el centro del BorderPane
        grid.setHgap(10); // Espacio horizontal entre columnas de botones
        grid.setVgap(10); // Espacio vertical entre filas de botones
        grid.setPadding(new Insets(10)); // Añadir padding alrededor del GridPane

        // Matriz de texto para los botones
        String[][] botones = {
            {"C", "<", "(", ")"},
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"0", ".", "=", "+"},
        };

        // Crear y añadir botones al GridPane
        for (int fila = 0; fila < botones.length; fila++) {
            for (int col = 0; col < botones[fila].length; col++) {
                String texto = botones[fila][col]; // Obtener texto para el botón actual
                Button btn = new Button(texto); // Crear nuevo botón con el texto actual
                btn.setPrefSize(50, 50); // Establecer tamaño preferido del botón
                btn.setOnAction(e -> procesoDeEntrada(texto)); // Asociar acción al botón según el texto
                grid.add(btn, col, fila); // Añadir botón al GridPane en la posición correspondiente
            }
        }

        root.setCenter(grid); // Colocar el GridPane en el centro del BorderPane

        // Crear escena y añadir estilo desde un archivo CSS
        Scene escena = new Scene(root, 300, 400); // Crear una nueva escena con el layout BorderPane
        try {
            escena.getStylesheets().add(getClass().getResource("calculadora.css").toExternalForm()); // Cargar estilo desde archivo CSS
        } catch (Exception e) {
            System.out.println("Error cargando el CSS: " + e.getMessage()); // Manejo de errores si no se puede cargar el CSS
        }

        escenario.setScene(escena); // Establecer la escena en el escenario
        escenario.show(); // Mostrar el escenario y la aplicación
    }

    /**
     * El método procesoDeEntrada maneja la entrada de datos en una calculadora.
     * Toma una cadena de texto (entrada) y realiza diferentes acciones según el
     * contenido de esa cadena, agregando al campo de texto, estableciendo el
     * estado, controlando la adición de puntos decimales para evitar múltiples
     * decimales en un número o evaluando la expresión mátemática para mostrar
     * el resultado haciendo uso de la librería Exp4J.
     *
     * @param entrada Texto recogido de los diferentes textoBotones de la
     * calculadora.
     */
    public void procesoDeEntrada(String entrada) {
        switch (entrada) {
            case "C":
                expresion.setLength(0); // Limpiar StringBuilder de la expresión
                pantalla.setText(""); // Limpiar pantalla de la calculadora
                break;
            case "<":
                if (expresion.length() > 0) {
                    expresion.deleteCharAt(expresion.length() - 1); // Eliminar último carácter de la expresión
                    pantalla.setText(expresion.toString()); // Actualizar pantalla con la nueva expresión
                }
                break;
            case "=":
                try {
                String exprStr = expresion.toString(); // Obtener la expresión como cadena
                if (exprStr.matches("[0-9+\\-*/().]*")) { // Verificar si la expresión es válida
                    Expression exp = new ExpressionBuilder(exprStr).build(); // Construir objeto Expression con la expresión
                    double resultado = exp.evaluate(); // Evaluar la expresión y obtener el resultado
                    pantalla.setText(String.valueOf(resultado)); // Mostrar resultado en la pantalla
                    expresion.setLength(0); // Limpiar StringBuilder de la expresión
                    expresion.append(resultado); // Actualizar la expresión con el resultado
                } else {
                    pantalla.setText("Expresión inválida"); // Mostrar mensaje de expresión inválida
                }
            } catch (Exception e) {
                pantalla.setText("Error"); // Manejo de errores en la evaluación de la expresión
            }
            break;
            case ".":
                if (expresion.length() == 0 || !expresion.toString().matches(".*\\d$")) {
                    pantalla.setText("Error decimal"); // Mostrar mensaje de error si se intenta agregar un decimal incorrectamente
                    break;
                }
                expresion.append("."); // Agregar punto decimal a la expresión
                pantalla.setText(expresion.toString()); // Actualizar pantalla con la nueva expresión
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (expresion.length() > 0 && expresion.toString().matches(".*[0-9)]$")) {
                    expresion.append(entrada); // Agregar operador a la expresión si es válido
                    pantalla.setText(expresion.toString()); // Actualizar pantalla con la nueva expresión
                }
                break;
            case "(":
                if (expresion.length() > 0 && expresion.toString().matches(".*[0-9)]$")) {
                    expresion.append("*("); // Agregar multiplicación implícita antes de paréntesis abierto si es necesario
                } else {
                    expresion.append("("); // Agregar paréntesis abierto a la expresión
                }
                pantalla.setText(expresion.toString()); // Actualizar pantalla con la nueva expresión
                break;
            case ")":
                long abre = expresion.chars().filter(ch -> ch == '(').count(); // Contar paréntesis abiertos en la expresión
                long cierra = expresion.chars().filter(ch -> ch == ')').count(); // Contar paréntesis cerrados en la expresión
                if (abre > cierra && expresion.toString().matches(".*[0-9)]$")) {
                    expresion.append(")"); // Agregar paréntesis cerrado a la expresión si es válido
                    pantalla.setText(expresion.toString()); // Actualizar
                }
                break;
            default: // Caso por defecto: si se pulsa un botón que representa un número u otro carácter válido
                expresion.append(entrada); // Agregar a la expresión
                pantalla.setText(expresion.toString()); // Actualizar
                break;
        }
    }

    /**
     * Programa principal, lanza la aplicación.
     *
     * @param args Argumentos o parámetros (no hay en este caso)
     */
    public static void main(String[] args) {
        launch(args); // Inicia la aplicación JavaFX, llama al método start()
    }
}
