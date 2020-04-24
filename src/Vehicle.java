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
     * contrincantes. Los adelantamientos involucran una gestión
     * especial, ya que se pueden dar diversos escenarios como por
     * ejemplo que el vehículo al que tratas de adelantar te puede
     * cerrar y quedarías detrás del vehículo al que intentabas
     * adelantar; que ya hay un vehículo que te está adelantando; que
     * no hay espacio suficiente delante del vehículo a adelantar, entre
     * otrassituaciones. La lógica del procedimiento adelantar es una
     * definición libre en función de vuestra implementación.
     *ToDo  -> Gestión del combustible (0,3 puntos): Cada vez que se supere
     * un tramo se tendrá que recalcular el gasto de combustible y en
     * el caso en el que el vehículo se encuentra en la reserva tendrá
     * que pasar por boxes y estar 2 simulaciones para rellenar el
     * depósito. El acceso a boxes se podrá realizar desde cualquier
     * sitio y momento.
 */
public class Vehicle implements Runnable,Comparable{

    private int speed;
    private int distancePerSecond;
    private int fuelLevel;
    private int currentTrack;
    private int currentTrackDistance;
    private int lap;
    private Race race;
    private int id;

    private static final int SECONDS_PER_ITERATION = 5;
    public Vehicle(Race race,int id){
        this.speed = 5;
        this.distancePerSecond = 2;
        this.race = race;
        this.currentTrack = 0;
        this.currentTrackDistance = 0;
        this.lap = 0;
        this.id = id;
    }

    @Override
    public void run() {

        //Comprobar si la distancia del tramo actual se ha recorrido
        int trackLength = race.currentTrack(currentTrack).getLength();
        if(currentTrackDistance<trackLength){
            currentTrackDistance+=speed*SECONDS_PER_ITERATION;
        }
        //Actualizar el tramo
        if(currentTrackDistance>=trackLength){
            System.out.println(Thread.currentThread().getName()+" vehicleId:"+this.id+": accedido al siguiente tramo: "+currentTrack++);
            //Incrementa la distancia restante con respecto al tramo anterior
            currentTrackDistance = currentTrackDistance-trackLength;
        }
    }


    @Override
    public int compareTo(Object o) {
        Vehicle vehicle = (Vehicle) o;
        if(this.lap<vehicle.lap){
            return -1;
        }else if(this.currentTrack<vehicle.currentTrack){
            if(this.lap>vehicle.lap){
                return 1;
            }else {
                return -1;
            }
        }else if(this.currentTrackDistance<vehicle.currentTrackDistance){
            if(this.lap==vehicle.lap&&this.currentTrack==vehicle.currentTrack){
                return -1;
            }else{
                return 1;
            }
        }else{
            return 0;
        }
    }
}
