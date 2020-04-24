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

    private static final int STRAIGHT_TRACK = 0;
    private static final int CURVE_TRACK = 0;
    private static final int MULTIPLE_CURVE_TRACK = 0;
    private static final int GRADE_CHANGE_TRACK = 0;

    private int length;
    private int trackType;
}
