import java.util.Random;

/**
 * Clase Racetrack (Tramo) encargada de modelar un tramo del circuito
 * donde correrán los coches. Un tramo representa una parte de un circuito
 * y puede tener distintas formas: curvo, recto, múltiples curvas, cambio de
 * rasante, etc. cada tipo de tramo requiere de un tiempo para ser cruzado,
 * es decir, no es lo mismo un tramo recto donde los vehículos pueden ir a
 * máxima velocidad y tardarán menos tiempo en recorrerlo que uno que
 * tenga múltiples curvas.
 *
     *ToDo  -> Clase Racetrack (0,5 puntos): Clase que constituye un tramo
     * dentro del circuito. En el caso básico no existen tipos distintos
     * de tramos, sino todos los tramos son iguales y tendrán la misma
     * longitud. Por tanto, solo influye la capacidad del conductor del
     * vehículo
     *
     *ToDo  -> Clase Racetrack (0,5 puntos): En este caso, el problema
     * añadido es que no todos los tramos de las carreteras son iguales
     * pudiendo existir tramos rectos, curvos, cambios de rasante,
     * entre otros. Para simular cada uno de estos tramos se añadirá
     * una propiedad que indique la dificultad para superar el tramo.
 */
public class RaceTrack {

    String[] trackTypes = {"STRAIGHT_TRACK","GRADE_CHANGE_TRACK","CURVE_TRACK","MULTIPLE_CURVE_TRACK"};

    private int length;
    private int trackType;

    public RaceTrack(){
        this.length = new Random().nextInt(30)+20;
        this.trackType = new Random().nextInt(4);
    }

    public int getLength() {
        return length;
    }

    public int computeDistance(int vehicleAdaptability){
        return length + (length*(trackType%3))/vehicleAdaptability;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTrackType() {
        return trackTypes[trackType];
    }

    public void setTrackType(int trackType) {
        this.trackType = trackType;
    }

    @Override
    public String toString() {
        return "RaceTrack{" +
                "type="+getTrackType()+
                "length=" + length +
                '}';
    }
}
