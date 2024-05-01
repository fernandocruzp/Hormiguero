import java.util.*;

/**
 * Clase que representa una hormiga en la simulación.
 */
public class Hormiga implements Runnable {

    private boolean estado, encontro;
    private int puntoX, puntoY, id;

    Stack<int[]> camino = new Stack<>();
    HashMap<Coordenada, Coordenada> mapa = new HashMap<>();

    int[][] tablero;

    /**
     * Clase interna que representa una coordenada en el tablero.
     */
    private class Coordenada {
        private int puntoX, puntoY;

        /**
         * Constructor de Coordenada.
         *
         * @param puntoX Coordenada X.
         * @param puntoY Coordenada Y.
         */
        public Coordenada(int puntoX, int puntoY) {
            this.puntoX = puntoX;
            this.puntoY = puntoY;
        }

        /**
         * Constructor de Coordenada.
         *
         * @param pos Posición como arreglo de dos elementos [x, y].
         */
        public Coordenada(int[] pos) {
            this.puntoX = pos[0];
            this.puntoY = pos[1];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coordenada)) return false;
            Coordenada that = (Coordenada) o;
            return getPuntoX() == that.getPuntoX() && getPuntoY() == that.getPuntoY();
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new int[]{puntoX, puntoY});
        }

        public int getPuntoX() {
            return puntoX;
        }

        public void setPuntoX(int puntoX) {
            this.puntoX = puntoX;
        }

        public int getPuntoY() {
            return puntoY;
        }

        @Override
        public String toString() {
            return "Coordenada{" +
                    "puntoX=" + puntoX +
                    ", puntoY=" + puntoY +
                    '}';
        }

        public void setPuntoY(int puntoY) {
            this.puntoY = puntoY;
        }
    }

    /**
     * Constructor de la clase Hormiga.
     *
     * @param id       Identificador de la hormiga.
     * @param estado   Estado de la hormiga.
     * @param puntoX   Coordenada X inicial.
     * @param puntoY   Coordenada Y inicial.
     * @param tablero  Tablero de la simulación.
     */
    public Hormiga(int id, boolean estado, int puntoX, int puntoY, int[][] tablero) {
        this.id = id;
        this.estado = estado;
        this.puntoX = puntoX;
        this.puntoY = puntoY;
        this.encontro = false;
        int[] pos = {this.puntoX, this.puntoY};
        camino.add(pos);
        Coordenada cord = new Coordenada(pos);
        mapa.put(cord, cord);
        this.tablero = tablero;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getPuntoX() {
        return puntoX;
    }

    public void setPuntoX(int puntoX) {
        this.puntoX = puntoX;
    }

    public int getPuntoY() {
        return puntoY;
    }

    public void setPuntoY(int puntoY) {
        this.puntoY = puntoY;
    }

    public boolean getEncontro() {
        return encontro;
    }

    public void setEncontro(boolean encontro) {
        this.encontro = encontro;
    }

    public Stack<int[]> getCamino() {
        return camino;
    }

    /**
     * Método para avanzar la hormiga en el tablero.
     */
    public void avanza() {
        int[] movimiento = buscaFeromonas();
        if (movimiento == null)
            caminar();
        else {
            puntoX = movimiento[0];
            puntoY = movimiento[1];
            int[] pos = new int[]{puntoX, puntoY};
            camino.add(pos);
            Coordenada cord = new Coordenada(pos);
            mapa.put(cord, cord);
        }
    }

    /**
     * Método para hacer que la hormiga se mueva aleatoriamente en el tablero.
     */
    public void caminar() {
        Random random = new Random();
        int x, y = 0;
        int[] pos = null;
        do {
            int movimiento = random.nextInt(8);
            int[] traduccion = traduccionMovimiento(movimiento);
            x = traduccion[0];
            y = traduccion[1];
            pos = new int[]{x, y};
        } while (!movimientoValido(tablero, x, y) || mapa.containsKey(pos));
        puntoX = x;
        puntoY = y;
        camino.add(new int[]{puntoX, puntoY});
        Coordenada cord = new Coordenada(pos);
        mapa.put(cord, cord);
    }

    /**
     * Método para buscar feromonas en las celdas adyacentes.
     *
     * @return La posición de la feromona si se encuentra, o null si no se encuentra.
     */
    private int[] buscaFeromonas() {
        for (int i = 0; i < 8; i++) {
            int[] posicion = traduccionMovimiento(i);
            Coordenada coordenada = new Coordenada(posicion);
            if (movimientoValido(tablero, posicion[0], posicion[1]))
                if (tablero[posicion[1]][posicion[0]] == 1 && !mapa.containsKey(coordenada)) {
                    return posicion;
                }
        }
        return null;
    }

    /**
     * Método para traducir el movimiento en un arreglo de dos elementos [x, y].
     *
     * @param movimiento El movimiento a traducir.
     * @return Arreglo de dos elementos [x, y] representando el movimiento.
     */
    private int[] traduccionMovimiento(int movimiento) {
        switch (movimiento) {
            case 0:
                return new int[]{puntoX - 1, puntoY + 1};
            case 1:
                return new int[]{puntoX + 1, puntoY - 1};
            case 2:
                return new int[]{puntoX + 1, puntoY};
            case 3:
                return new int[]{puntoX + 1, puntoY + 1};
            case 4:
                return new int[]{puntoX, puntoY + 1};
            case 5:
                return new int[]{puntoX, puntoY - 1};
            case 6:
                return new int[]{puntoX - 1, puntoY};
            case 7:
                return new int[]{puntoX - 1, puntoY - 1};
        }
        return new int[]{puntoX, puntoY};
    }

    /**
     * Método para verificar si un movimiento es válido en el tablero.
     *
     * @param tablero El tablero de la simulación.
     * @param movX    Movimiento en el eje X.
     * @param movY    Movimiento en el eje Y.
     * @return true si el movimiento es válido, false de lo contrario.
     */
    private boolean movimientoValido(int[][] tablero, int movX, int movY) {
        if (puntoX + movX < 0 || puntoY + movY < 0)
            return false;
        if (puntoX + movX > tablero[0].length || puntoY + movY > tablero.length)
            return false;
        return true;
    }

    /**
     * Método para ejecutar la lógica de la hormiga.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        do {
            avanza();
            if (tablero[puntoY][puntoX] == 2) {
                encontro = true;
                estado = false;
                ;
                break;
            }

        } while (puntoX >= 3 && puntoY < tablero.length - 3 && puntoY >= 3 && puntoX < tablero[0].length - 3 && camino.size() < 30000);
        for (int i = 0; i < camino.size(); i++) {
            int[] pos = camino.pop();
            if (encontro)
                tablero[pos[1]][pos[0]] = 1;
            else
                tablero[pos[1]][pos[0]] = 0;

            puntoX = pos[0];
            puntoY = pos[1];
        }
    }
}
