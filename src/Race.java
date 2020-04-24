import java.util.*;
import java.util.concurrent.*;

import static java.lang.Thread.interrupted;

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
 */
public class Race implements Runnable{

    List<RaceTrack> tracks;
    Score score;
    Collection<Vehicle> vehicles;
    ScheduledExecutorService exec;
    public Race(){
        exec = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

        vehicles = Collections.synchronizedSortedSet(new ConcurrentSkipListSet<>());
        this.tracks = new ArrayList<>();
        for(int i=0;i<5;i++){
            this.tracks.add(new RaceTrack());
        }
        this.score = new Score();
    }


    public RaceTrack currentTrack(int trackPos){
        if(trackPos>=0 && trackPos< tracks.size()) {
            return tracks.get(trackPos);
        }else{
            return null;
        }
    }


    public void updateScore(){
        score.updateRanking();
    }


    @Override
    public void run() {

            for (Vehicle vehicle : vehicles) {
                exec.scheduleAtFixedRate(vehicle, 0, 5, TimeUnit.SECONDS);

            }

    }

    public void addVehicle(Vehicle vehicle){
        this.vehicles.add(vehicle);
    }
}
