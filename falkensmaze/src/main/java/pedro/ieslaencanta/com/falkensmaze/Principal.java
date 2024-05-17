/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pedro.ieslaencanta.com.falkensmaze;

import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.Pane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pedro.ieslaencanta.com.falkensmaze.components.Block;
import pedro.ieslaencanta.com.falkensmaze.components.BlocksPanel;
import pedro.ieslaencanta.com.falkensmaze.components.DialogSize;
import pedro.ieslaencanta.com.falkensmaze.components.DialogTime;
import pedro.ieslaencanta.com.falkensmaze.components.MazeCanvas;
import pedro.ieslaencanta.com.falkensmaze.model.Maze;

public class Principal extends Application {

    // Escena actual de la aplicación
    Scene scene;

    // Dimensiones de la ventana de la aplicación
    private int width = 480;
    private int height = 480;

    // FileChooser para seleccionar archivos
    final FileChooser fileChooser;

    // Lienzo para dibujar el laberinto
    private MazeCanvas maze;

    /**
     * Constructor predeterminado que inicializa el FileChooser.
     */
    public Principal() {
        super();
        fileChooser = new FileChooser();
    }

    /**
     * Sobrescribe el método start de Application para configurar la GUI y mostrar la aplicación.
     * @param stage Stage de la aplicación.
     * @throws Exception Si ocurre algún error durante la ejecución.
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane border = new BorderPane();
        border.setCenter(this.createMaze()); // Centra el lienzo del laberinto
        border.setLeft(this.createBlockMenu()); // Añade el panel de bloques a la izquierda
        border.setTop(this.createMenu()); // Añade el menú superior
        this.scene = new Scene(border, this.width + 120, this.height + 50); // Configura la escena

        stage.setTitle("Falken's Maze Editor"); // Establece el título de la ventana
        stage.setResizable(false); // Evita que el usuario cambie el tamaño de la ventana
        stage.setScene(scene); // Asigna la escena al stage
        // Manejo del evento de cerrar la aplicación
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit(); // Cierra todas las ventanas de la aplicación
                System.exit(0); // Termina el proceso de la aplicación
            }
        });

        stage.show(); // Muestra la ventana
        this.maze.draw(); // Dibuja el laberinto
    }

    /**
     * Crea y devuelve un panel de bloques (BlocksPanel) con opciones para agregar bloques al laberinto.
     * @return Pane con el panel de bloques.
     */
    private Pane createBlockMenu() {
        BlocksPanel b = new BlocksPanel();
        Block tb;
        String[] nombres = Block.getNamesBlocks(); // Obtiene los nombres de los tipos de bloques
        for (int i = 0; i < nombres.length; i++) {
            tb = new Block();
            tb.setTipo(nombres[i]); // Establece el tipo de bloque
            b.addBlock(tb); // Añade el bloque al panel
            tb.addBlocklistener(this.maze); // Asigna el listener del bloque al lienzo del laberinto
        }
        b.init(); // Inicializa el panel de bloques
        return b;
    }

    /**
     * Crea y devuelve un lienzo de laberinto (MazeCanvas) con dimensiones especificadas.
     * @return MazeCanvas inicializado.
     */
    private MazeCanvas createMaze() {
        this.maze = new MazeCanvas();
        this.maze.setBoard_size(new Size(this.width, this.height)); // Establece el tamaño del tablero
        this.maze.setRows(10); // Establece el número de filas
        this.maze.setCols(10); // Establece el número de columnas
        // this.maze.setCell_size(new Size(this.width / 10, this.height / 10)); // Tamaño de las celdas (comentado)
        this.maze.init(); // Inicializa el lienzo del laberinto

        return this.maze;
    }

    /**
     * Crea y devuelve un menú de barras (MenuBar) con opciones para nuevo, guardar, abrir, configuración de sonido y tiempo, y salir.
     * @return MenuBar con las opciones del menú.
     */
    private MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("Archivo");
        MenuItem newMenuItem = new MenuItem("Nuevo");
        newMenuItem.setOnAction(eh -> {
            DialogSize ds = new DialogSize();
            ds.init();
            Optional<Size> result = ds.showAndWait();
            if (result.isPresent()) { // Verifica si el resultado no es nulo
                this.maze.reset(result.get()); // Reinicia el laberinto con el nuevo tamaño
            }
        });
        MenuItem saveMenuItem = new MenuItem("Guardar");
        saveMenuItem.setOnAction(actionEvent -> {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(scene.getWindow());
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml*"),
                new FileChooser.ExtensionFilter("JSon", "*.json"),
                new FileChooser.ExtensionFilter("Bin", "*.bin")
            );
            if (file!= null) {
                try {
                    Maze.save(this.maze.getMaze(), file); // Guarda el laberinto en un archivo
                } catch (JAXBException | IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        MenuItem loadMenuItem = new MenuItem("Abrir");
        loadMenuItem.setOnAction(actionEvent -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml*"),
                new FileChooser.ExtensionFilter("JSon", "*.json"),
                new FileChooser.ExtensionFilter("Bin", "*.bin")
            );
            File file = fileChooser.showOpenDialog(scene.getWindow());
            if (file!= null) {
                try {
                    Maze m = Maze.load(file); // Carga un laberinto desde un archivo
                    this.maze.reset(new Size(m.getBlocks().length,m.getBlocks()[0].length));
                    this.maze.setMaze(m);
                    this.maze.draw();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Menu optionsMenu = new Menu("Options");
        MenuItem soundMenu = new MenuItem("Sound");
        optionsMenu.getItems().add(soundMenu);
        soundMenu.setOnAction(actionEvent -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("mp3", "*.mp3*")
            );
            File file = fileChooser.showOpenDialog(scene.getWindow());
            if (file!= null) {
                this.maze.getMaze().setSound(file.getAbsolutePath()); // Asigna un archivo de sonido al laberinto
            }
        });
        MenuItem timeMenu = new MenuItem("Time");
        timeMenu.setOnAction(eh -> {
            DialogTime dt = new DialogTime();
            dt.init();
            Optional<Double> result = dt.showAndWait();
            if (result.isPresent()) {
                this.maze.getMaze().setTime(result.get()); // Establece el tiempo asignado al laberinto
            }
        });
        optionsMenu.getItems().add(timeMenu);
        MenuItem exitMenuItem = new MenuItem("Salir");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit()); // Cierra la aplicación

        fileMenu.getItems().addAll(newMenuItem, saveMenuItem, loadMenuItem,
                new SeparatorMenuItem(), exitMenuItem);

        menuBar.getMenus().addAll(fileMenu, optionsMenu);
        return menuBar;
    }
}
