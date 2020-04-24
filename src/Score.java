import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
 */
public class Score {

    private List<Vehicle> ranking;

    public Score(){
        this.ranking = Collections.synchronizedList(new ArrayList<>());
    }

    public void updateRanking(){
        for (Vehicle vehicle : ranking) {
            System.out.println(vehicle.toString());
        }
    }

    public void swapPositions(Vehicle v1, Vehicle v2){
        int posV1,posV2;
        posV1 = ranking.indexOf(v1);
        posV2 = ranking.indexOf(v2);
        Vehicle aux = ranking.get(posV1);
        ranking.set(posV1,ranking.get(posV2));
        ranking.set(posV2,ranking.get(posV1));
    }
}
