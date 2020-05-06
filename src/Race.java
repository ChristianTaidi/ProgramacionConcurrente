import javax.crypto.SecretKey;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

/**
 * Clase Race (Carrera) encargada de modelar el circuito donde correrán los
 * coches. Una carrera se encuentra compuesta por una serie de tramos que
 * pueden tener distintas formas, como se ha indicado anteriormente. El
 * objetivo de esta clase será la de gestionar la lógica del movimiento de los
 * vehículos, informar al panel de control y a los competidores con datos
 * "actuales" de los vehículos como posición actual, metros hacia la meta,
 * distancia entre vehículos, etc.
 *
 *  ->ToDo Clase Race (2 puntos): Esta clase implementará la lógica de la
     * carrera. Estas son las funciones que tendrá que implementar:
     * § Proporcionar acceso concurrente de los vehículos.
     * § Gestionar lógica del movimiento de los vehículos, por lo
     * tanto, implementar métodos que permitan conocer el
     * tramo actual y los siguientes.
     * § La gestión del movimiento debe de ser de forma
     * concurrente y se implementará mediante el uso de
     * EJECUTORES.
     * § Informar a la clase Score del desarrollo de la carrera.
     * § Informar contrincantes de la posición de los otros
     * vehículos.
 *
 * Una carrera está compuesta por tramos, será la encargada de actualizar la posición de los vehiculos en la puntuación?
 * Race será la encargada de gestionar y actualizar los adelantamientos
 * (doble referencia? vehiculo referencia a carrera y carrera referencia una lista de vehiculos)
 *
 */
public class Race implements Runnable{

    List<RaceTrack> tracks;
    static Score score;
    static List<Vehicle> vehicles;
    static List<Vehicle> finalPositions;

    ScheduledExecutorService exec;
    static int laps;
    public Race(){
        exec = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        vehicles = Collections.synchronizedList(new CopyOnWriteArrayList<>());
        finalPositions = new CopyOnWriteArrayList<>();
        this.tracks = new ArrayList<>();
        for(int i=0;i<5;i++){
            this.tracks.add(new RaceTrack());
        }
        System.out.println(tracks.stream()
                .map(track -> track.toString())
                .collect(Collectors.joining("|\n","Tracks\n","\n-------------------------------------")));
        this.score = new Score();
        this.laps= 5;
    }


    public RaceTrack currentTrack(int trackPos){
        if(trackPos>=0 && trackPos< tracks.size()) {
            return tracks.get(trackPos);
        }else{
            return null;
        }
    }


    public void complete(Vehicle completedVehicle){
        finalPositions.add(completedVehicle);
        completedVehicle.setPosition(finalPositions.indexOf(completedVehicle)+1);
    }

    public static void updateScore(){
        score.updateRanking(vehicles);
    }

    public static List<Vehicle> getPositions(Vehicle currentVehicle){
        Main.updateAccess.writeLock().lock();
        vehicles.sort(Vehicle::compareTo);
        currentVehicle.setPosition(vehicles.indexOf(currentVehicle));
        List sorted = List.copyOf(vehicles);
        Main.updateAccess.writeLock().unlock();
        return sorted;
    }

    @Override
    public void run() {

        exec.scheduleAtFixedRate(()->updateScore(),0,3, TimeUnit.SECONDS);

        System.out.println(vehicles.size());
        for (Vehicle vehicle : vehicles) {
            Future future = exec.scheduleAtFixedRate(vehicle, 0, 5, TimeUnit.SECONDS);
            System.out.println("Next vehicle");
            RaceJudge.vehiclesReady.countDown();
            //vehicle.setFuture(future);

        }


        try {
            RaceJudge.finish.acquire();
            exec.awaitTermination(5,TimeUnit.SECONDS);
            score.finalResult(finalPositions);
        } catch (InterruptedException e) {
            System.out.println("Race -> Finalizando de forma inesperada");

            exec.shutdownNow();
            try {
                exec.awaitTermination(5,TimeUnit.SECONDS);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            vehicles.sort(Vehicle::compareTo);
            score.finalResult(vehicles);

        }
        exec.shutdown();
        System.out.println("Fin De la carrera");
    }

    public void addVehicle(Vehicle vehicle){
        System.out.println(vehicles.size());
        System.out.println(vehicles);
        this.vehicles.add(vehicle);
    }

    public void setVehicles(List<Vehicle> vehicles){
        this.vehicles = vehicles;
    }
}
