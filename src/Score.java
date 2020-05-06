import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * Clase Score (Marcador) encargada de modelar el marcador donde se
 * recoge toda la información de la carrera como posiciones, tiempos,
 * distancias a meta y distancias entre los vehículos.
 *
     *ToDo  -> Clase Score (1 puntos): Marcador de la carrera que
     * implementará las siguientes funciones:
     * § Mostrar sólo información sobre el ranking de la carrera.
     * Esta clase será accedida periódicamente por la clase Race
     * para mostrar datos sobre el desarrollo de la carrera.
     * § Se valorará positivamente el uso de herramientas
     * concurrentes para el procesamiento de los datos.
     *
     *ToDo  -> Clase Score (0,75 puntos): Se mostrará de manera "continua"
     * distintas variables de información del desarrollo de la carrera:
     * posición, distancia a meta, distancia entre vehículos, etc.
     * posiciones de los vehículos, distancia a la meta, distancia entre
     * los vehículos, etc. Se valorará positivamente el uso de
     * herramientas concurrentes para el procesamiento de los datos.
 *
 * Score debe ser invocado por un thread cada cierto tiempo o cada vez que se produzca un adelantamiento dependiendo del nivel al que se quiera llegar
 */
public class Score {


    public Score(){

    }

    public void updateRanking(List<Vehicle> ranking){

        try {
            sleep(10);

        Main.updateAccess.writeLock().lock();
        System.out.println(ranking.parallelStream()
                .sorted(Vehicle::compareTo)
                .map(v -> "|vehicle"+v.getId()+"|lap="+v.getLap()+"|track="+v.getCurrentTrack()+"|distance="+v.getCurrentTrackRealDistance())
                .collect(Collectors.joining("|\n","-------------------------------------\n","\n-------------------------------------")));
        Main.updateAccess.writeLock().unlock();
        } catch (InterruptedException e) {
            Main.updateAccess.writeLock().unlock();
            System.out.println("Parando actualización de puntuación");
        }
    }

    public void finalResult(List<Vehicle> result){

        System.out.println(result.parallelStream()
                .map(v -> v.getPosition()+" --> vehicle"+v.getId()+"|lap="+v.getLap()+"|track="+v.getCurrentTrack()+"|distance="+v.getCurrentTrackDistance())
                .collect(Collectors.joining("|\n","-------------------------------------\n","\n-------------------------------------")));

    }

//    public void swapPositions(Vehicle v1, Vehicle v2){
//        int posV1,posV2;
//        posV1 = ranking.indexOf(v1);
//        posV2 = ranking.indexOf(v2);
//        Vehicle aux = ranking.get(posV1);
//        ranking.set(posV1,ranking.get(posV2));
//        ranking.set(posV2,ranking.get(posV1));
//    }
}
