import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la interfaz gráfica de la simulación del hormiguero.
 */
public class Hormiguero extends JFrame {
    private static final int CELL_SIZE = 20; // Tamaño de cada celda
    private static int[][] tablero = null; // El tablero de la simulación
    private final List<Hormiga> hormigas; // Lista de hormigas en la simulación

    /**
     * Constructor de la clase Hormiguero.
     *
     * @param tablero El tablero de la simulación.
     * @param hormigas Lista de hormigas en la simulación.
     */
    public Hormiguero(int[][] tablero, List<Hormiga> hormigas) {
        this.tablero = tablero;
        this.hormigas = hormigas;
        inicializaUI();
    }

    /**
     * Inicializa la interfaz gráfica.
     */
    private void inicializaUI() {
        setTitle("Simulación de Rastro de Hormigas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(tablero[0].length * CELL_SIZE, tablero.length * CELL_SIZE);
        setVisible(true);
    }

    /**
     * Método para pintar el tablero en la interfaz gráfica.
     *
     * @param g El contexto gráfico.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Dibujar la cuadrícula
        for (int y = 0; y < tablero.length; y++) {
            for (int x = 0; x < tablero[y].length; x++) {
                Color color = Color.WHITE;
                if (tablero[y][x] == 1)
                    color = Color.RED;
                if (tablero[y][x] == 2)
                    color = Color.BLUE;
                if (tablero[y][x] == 3) {
                    color = Color.YELLOW;
                }
                g.setColor(color);
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    /**
     * Actualiza la interfaz gráfica.
     */
    public void updateUI() {
        repaint();
    }

    /**
     * Método principal para ejecutar la simulación.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     * @throws InterruptedException Si ocurre una interrupción durante la ejecución del hilo.
     */
    public static void main(String[] args) throws InterruptedException {
        Tablero tablero1 = new Tablero(50);
        tablero = tablero1.getTablero();
        Hormiguero gui = new Hormiguero(tablero, new ArrayList<>()); // Crear GUI
        gui.setVisible(true);
        int numAntsPerBatch = 30;
        while (true) {
            List<Hormiga> hormigas1 = tablero1.generarHormigas(numAntsPerBatch);
            Thread[] hilos = new Thread[numAntsPerBatch + 1];
            int i = 0;
            for (Hormiga hormiga : hormigas1) {
                gui.hormigas.add(hormiga);
                hilos[i] = new Thread(hormiga);
                hilos[i].start();
                i++;
            }
            for (Thread hormiga : hilos) {
                if (hormiga != null)
                    hormiga.join();
            }

            Thread.sleep(100);
            gui.updateUI();
            tablero1.imprime(tablero);
            System.out.println("");
            hormigas1.clear();
        }
    }
}
