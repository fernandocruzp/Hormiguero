import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[][] tablero=new int[50][50];
        generaComida(tablero);
        imprime(tablero);
        //Scanner scanner=new Scanner(System.in);
        int i=100;
        while (i<500){
            Thread[] hilos = new Thread[30];
            for(int j=0;j<30;j++){
                Hormiga homiga = new Hormiga(i+j,true, tablero[0].length / 2, tablero.length / 2, tablero);
                hilos[j]=new Thread(homiga);
                hilos[j].start();
            }
            for(int j=0;j<10;j++){
                hilos[j].join();
            }
            i++;
        }
        System.out.println("");
        imprime(tablero);
    }

    private static void generaComida(int[][] tablero){
        Random random= new Random();
        for(int i=0;i<10;i++) {
            int x = random.nextInt(tablero.length-1);
            int y = random.nextInt(tablero.length-1);
            int tamaño = random.nextInt(15);
            System.out.println(tamaño);
            System.out.println(x+ ""+ y);
            for (int j =y;j<Math.min(tablero.length,y+tamaño);j++)
                for (int k=x;k< Math.min(tablero.length,x+tamaño);k++)
                    tablero[j][k]=2;
        }
    }
    private static void imprime(int [][]tablero){
        for(int j=0;j<tablero.length;j++){
            for(int i=0;i<tablero[j].length;i++){
                System.out.print(tablero[j][i] + "  ");
            }
            System.out.println(" ");
        }
    }


}