import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

/**
 * Clase RaceJudge (Juez de Carrera) encargada de modelar al juez de la
 * carrera. El objetivo principal de esta clase es controlar el desarrollo
 * correcto de la carrera desde que se inicia, cuando el juez da el pistoletazo
 * de salida, hasta cuando se termina la carrera y se posicionan todos los
 * vehículos en boxes.
 *
     *ToDo  -> Clase RaceJudge (0,5 puntos): Juez encargado de las siguientes
     * funciones:
     * § Iniciar carrera: Dar el pistoletazo de salida asegurando
     * que todos los coches se encuentran en la parrilla de salida.
     * § Finalizar carrera: Asegurar que todos los vehículos
     * terminen la carrera, den una vuelta extra y terminen en
     * boxes.
 *
 * Al ser cada coche un thread se debe esperar al countdown latch de cada vehiculo para dar la salida
 * y se debe esperar al countdown latch del ultimo vehiculo de la ultima vuelta para indicar la meta
 */
public class RaceJudge implements Runnable{

    static CountDownLatch vehiclesReady;
    static CountDownLatch lapsCompleted;
    static CountDownLatch start;
    static Semaphore finish;
    int nVehicles;
    public RaceJudge(int nVehicles,int laps){
        this.vehiclesReady = new CountDownLatch(nVehicles);
        this.lapsCompleted = new CountDownLatch(laps);
        this.nVehicles = nVehicles;
        start= new CountDownLatch(3);
        finish = new Semaphore(0);
    }


    @Override
    public void run() {
        try {
            System.out.println("Esperando a vehiculos");
            vehiclesReady.await();
            System.out.println("Iniciando salida");
            for (int i=0;i<3;i++){
                System.out.println("Juez:" +(3-i));
                sleep(1000);
                start.countDown();

            }
            //Used to block the cars at the end

            lapsCompleted.await();
            System.out.println("Juez: final de la carrera, coches en boxes");
            finish.release();

        } catch (InterruptedException e) {
            System.out.println("Finalizando de forma inseperada");
        }
    }
}
