import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa el tablero de la simulación.
 */
public class Tablero {

    private int tablero[][];
    List<Hormiga> hormigas = new ArrayList<>();

    /**
     * Constructor de la clase Tablero.
     *
     * @param tamaño Tamaño del tablero (cuadrado).
     */
    public Tablero(int tamaño) {
        this.tablero = new int[tamaño][tamaño];
        generaComida(this.tablero);
    }

    /**
     * Método privado para generar comida en posiciones aleatorias del tablero.
     *
     * @param tablero El tablero donde se genera la comida.
     */
    private void generaComida(int[][] tablero) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(tablero.length - 1);
            int y = random.nextInt(tablero.length - 1);
            int tamaño = random.nextInt(15);
            for (int j = y; j < Math.min(tablero.length, y + tamaño); j++)
                for (int k = x; k < Math.min(tablero.length, x + tamaño); k++)
                    tablero[j][k] = 2;
        }
    }

    /**
     * Método para imprimir el tablero en la consola.
     *
     * @param tablero El tablero a imprimir.
     */
    public void imprime(int[][] tablero) {
        for (int j = 0; j < tablero.length; j++) {
            for (int i = 0; i < tablero[j].length; i++) {
                System.out.print(tablero[j][i] + "  ");
            }
            System.out.println(" ");
        }
    }

    /**
     * Método para generar una cantidad específica de hormigas en el tablero.
     *
     * @param cantidad Cantidad de hormigas a generar.
     * @return Lista de hormigas generadas.
     */
    public List<Hormiga> generarHormigas(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            Hormiga hormiga = new Hormiga(i, false, tablero.length / 2, tablero.length / 2, tablero);
            hormigas.add(hormiga);
        }
        return hormigas;
    }

    /**
     * Método para obtener el tablero.
     *
     * @return El tablero de la simulación.
     */
    public int[][] getTablero() {
        return tablero;
    }
}
