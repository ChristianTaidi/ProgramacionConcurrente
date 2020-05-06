import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * Clase Principal que contendrá toda la lógica para crear, ejecutar y parar
 * los hilos que se necesiten en la aplicación. Por último, el método principal
 * tendrá en cuanta la entrada por pantalla, si se introduce algún valor se
 * deberá parar la carrera y finalizar mostrando el resultado una vez se paren
 * todos los vehículos.
 *
     *ToDo -> Clase Principal (0,75 puntos): Representa el punto dónde
     * comienza la ejecución de la aplicación concurrente. Estas son
     * las funciones que debería de implementar:
     * § Funciones necesarias para la gestión de hilos de las
     * correspondientes clases necesarias para la simulación.
     * § Funciones necesarias para finalizar el programa de
     * manera correcta.
     *
     *ToDo -> Main (0,25 puntos): gestionará de forma correcta la entrada por
     * teclado y, en caso de recibir información, deberá suspender la
     * carrera, mostrar el panel y finalizar el programa de manera
     * adecuada.
 */
public class Main {

    public static ReadWriteLock updateAccess;
    private static BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args){
        updateAccess = new ReentrantReadWriteLock(true);
        Race race = new Race();
        List<Vehicle> vehicleList = new ArrayList<>();
        for(int i =0;i<5;i++){
            Vehicle newVehicle = new Vehicle(race,i);
           vehicleList.add(newVehicle);

        }
        RaceJudge judge = new RaceJudge(vehicleList.size(),Race.laps);
        race.setVehicles(vehicleList);
        System.out.println("Pulse enter para terminar de forma inmediata cuando quiera");

        Thread raceExec = new Thread(()->race.run(),"Race");
        raceExec.start();
        Thread judgeExec = new Thread(()->judge.run(),"Judge");
        judgeExec.start();
        Thread systemIn = new Thread(()->{
            try {
                while(!sc.ready()){
                    sleep(100);
                }
                sc.readLine();
                raceExec.interrupt();
                judgeExec.interrupt();
            }catch (Exception e){
                System.out.println("La carrera terminó correctamente");
            }
        });
        systemIn.start();

        try {
            raceExec.join();
            System.out.println(currentThread().getName()+" -> Fin de la carrera");
            systemIn.interrupt();

        } catch (InterruptedException e) {
            System.out.println(currentThread().getName()+" -> Carrera interrumpida");
        }


    }
}
