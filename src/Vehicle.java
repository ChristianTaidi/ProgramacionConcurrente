/**
 * Clase Vehicle (Vehículo) encargada de modelar el coche que correrá en
 * el circuito. Cada vehículo deberá ser modelado teniendo en cuenta
 * características técnicas como: velocidad, gasto de combustible, motor, etc.
 * Cada vez que un vehículo avanza al siguiente tramo se actualizaran
 * aspectos relacionados con velocidad, combustible, etc. En el caso en el
 * que no quede combustible el Vehículo tendrá que ir a boxes durante 2
 * simulaciones. Además, cada vehículo tendrá que decidir si avanzan o no
 * al siguiente tramo, cuánto avanza dentro del tramo y, en caso de
 * adelantamiento, si debieran avanzar uno más que su contrincante
 *
     *ToDo  -> Clase Vehicle (0,25 puntos): Representa una entidad que se
     * tiene que ejecutar de manera concurrente. En el caso básico
     * todos los vehículos poseen las mismas características técnicas:
     * (velocidad, espacio recorrido cada segundo, depósito de
     * combustible, motor, etc.).
     *
     *ToDo  -> Clase Vehicle (0,7 puntos): en este caso, el problema añadido
     * es que no todos los vehículos tienen las mismas características
     * técnicas, sino que pueden tener distinta velocidad punta, motor
     * más rápido, etc. Por tanto, durante la carrera se pueden llevar a
     * cabo adelantamientos entre vehículos considerando estas
     * características técnicas y las características del tramo. En este
     * caso el vehículo tendrá que decidir si adelantan a uno o más
     * contrincantes.
     *ToDo  -> Gestión del combustible (0,3 puntos): Cada vez que se supere
     * un tramo se tendrá que recalcular el gasto de combustible y en
     * el caso en el que el vehículo se encuentra en la reserva tendrá
     * que pasar por boxes y estar 2 simulaciones para rellenar el
     * depósito. El acceso a boxes se podrá realizar desde cualquier
     * sitio y momento.
 */
public class Vehicle {
}
