import java.util.Objects;
import java.util.Random;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

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
 *
 * Un vehiculo debe implementar la lógica de avanzar de tramo y adelantar dependiendo del tramo y la posicion de los demas coches, esta informacion la contiene
 * la instancia de la carrera, el tramo lo obtiene al ejecutar su lógica y consulta a la carrera la posición del vehiculo siguiente para comprobar si puede adelantarlo
 * esta consulta se debe hacer de forma sincronizada para que no se produzcan adelantamientos inconsistentes
 * (ej si 3 va a adelantar a 2 y 4 va a adelantar a 3 4 no pasa a ser 2 sino a ser 3) a no ser que adelante a dos,
 * comprobar entonces todos los que hay delante? o limitar adelantamiento a un solo coche, comprobar cada coche delante hasta que no haya mas o no esté mas avanzado que este.
 */
public class Vehicle implements Runnable,Comparable{

    private int speed;
    private int distancePerSecond;
    private int fuelLevel;
    private int currentTrack;
    private int currentTrackDistance;
    private float currentTrackRealDistance;
    private int lap;
    private int adaptability;
    private int fuelConsume;
    private Race race;
    private RaceJudge judge;
    private int id;
    private int position;
    private int boxes;
    private boolean started = false;
    private boolean finished = false;
    private boolean stopped = false;


    private static final int SECONDS_PER_ITERATION = 5;
    public Vehicle(Race race,int id){
        this.speed = new Random().nextInt(10)+5;
        this.fuelConsume = new Random().nextInt(6)+2;
        this.fuelLevel = new Random().nextInt(150)+50;
        this.distancePerSecond = 2;
        this.race = race;
        this.currentTrack = 1;
        this.currentTrackDistance = 0;
        this.currentTrackRealDistance = 0;
        this.lap = 1;
        this.id = id;
        this.adaptability = new Random().nextInt(4)+1;

    }

    @Override
    public void run() {

        if(!stopped) {
            //If race is not started yet or finished so the thread blocks
            if (!started) {
                try {
                    System.out.println(currentThread().getName() + " ->Vehiculo:" + this.id + "Esperando salida");
                    RaceJudge.start.await();
                    started = true;
                    System.out.println(currentThread().getName() + " ->Vehiculo:" + this.id + "Salida");
                } catch (InterruptedException e) {
                    System.out.println("Vehicle:"+this.id+"Finalizando de forma inesperada");
                }
            }
            int trackLength = 0;
            try {
                RaceTrack currentTrackObj = race.currentTrack(currentTrack - 1);

                trackLength = currentTrackObj.computeDistance(this.adaptability);

                //Actualizar el tramo
                //System.out.println(currentThread().getName() + "vehicleId:" + this.id + "-> distancia antes de avanzar = " + currentTrackDistance);
                currentTrackDistance += speed * SECONDS_PER_ITERATION;//Incluir tipo de tramo
                //System.out.println(currentThread().getName() + " vehicleId:" + this.id + "-> tramo: " + currentTrack + " distancia: " + currentTrackDistance);


                //La distancia del tramo actual se ha recorrido?
                //avanza tantos tramos como sea necesario dependiendo de la distancia que haya recorrido
                Main.updateAccess.readLock().lockInterruptibly();

                while (currentTrackDistance >= trackLength) {

                        sleep(10);

                    //Incrementa la distancia restante con respecto al tramo anterior
                    //Si la distancia del siguiente tramo es menor a la distancia recorrida esta iteración, debe incrementar tramos hasta que
                    nextTrack(trackLength);
                    trackLength = race.currentTrack(currentTrack - 1).computeDistance(this.adaptability);
                    //System.out.println(currentThread().getName()+" vehicleId:"+this.id+" Distancia por recorrer="+currentTrackDistance);
                    //System.out.println(currentThread().getName()+" vehicleId:"+this.id+" Distancia del tramo "+currentTrack+" ="+trackLength);

                }
            } catch (InterruptedException e) {
                System.out.println("Vehicle:"+this.id+" parando de forma inesperada");
                while (currentTrackDistance >= trackLength) {

                    //Incrementa la distancia restante con respecto al tramo anterior
                    //Si la distancia del siguiente tramo es menor a la distancia recorrida esta iteración, debe incrementar tramos hasta que
                    nextTrack(trackLength);
                    trackLength = race.currentTrack(currentTrack - 1).computeDistance(this.adaptability);
                    //System.out.println(currentThread().getName()+" vehicleId:"+this.id+" Distancia por recorrer="+currentTrackDistance);
                    //System.out.println(currentThread().getName()+" vehicleId:"+this.id+" Distancia del tramo "+currentTrack+" ="+trackLength);

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            Main.updateAccess.readLock().unlock();

        }

        if(boxes>0){
            System.out.println("vehicle: "+this.id+" -> En boxes "+(3-boxes));
            boxes--;
            stopped = boxes!=0;
            if(!stopped){
                fuelLevel = new Random().nextInt(50)+100;
                System.out.println("vehicle: "+this.id+" -> saliendo de boxes, fuel= "+fuelLevel);

            }
        }

    }

    private void nextTrack(int currentLength){

        currentTrack++;
        //Ha completado el ultimo tramo?
        if (currentTrack-1 == race.tracks.size()) {
            if(this.lap<Race.laps) {
                lap++;
                System.out.println(currentThread().getName() + " vehicleId:" + this.id + ": sigueinte vuelta lap:" + lap);
                currentTrack = 1;
            }else if(this.lap==Race.laps&&!finished){
                this.finished = true;
                System.out.println(currentThread().getName()+"Vehicle:"+id+"Vuelta final a boxes");
                race.complete(this);
                currentTrack = 1;
            }else{
                currentTrack = 5;
                currentTrackDistance = currentLength;
                currentTrackRealDistance = race.currentTrack(currentTrack-1).getLength();
                System.out.println("Vehicle:"+this.id+" Track ->"+race.currentTrack(currentTrack-1).toString()+" Distance ->"+race.currentTrack(currentTrack-1).getLength());
                System.out.println(currentThread().getName()+"Vehicle:"+id+"Fin de la carrera");
                RaceJudge.lapsCompleted.countDown();
                stopped = true;
                //ToDo notificar al juez al terminar la vuelta a boxes para terminar la carrera y hacer el shutdown
            }
        }

        //System.out.println(currentThread().getName()+" vehicleId:"+this.id+": accedido al siguiente tramo: "+ currentTrack +"/"+race.tracks.size());
        //Distancia en el tramo actual es distancia recorrida menos la distancia del tramo anterior por avanzar k "metros"
        currentTrackDistance = currentTrackDistance-currentLength;
        currentTrackRealDistance = (float)(race.currentTrack(currentTrack-1).getLength()*currentTrackDistance)/race.currentTrack(currentTrack-1).computeDistance(this.adaptability);

        if(!calculateFuel(currentLength)&&!finished){
            System.out.println("vehicle: "+this.id+" -> Entrando a boxes");
            currentTrackDistance=0;
            currentTrackRealDistance=0;
            stopped=true;
            boxes=2;
        }
    }

    private boolean calculateFuel(int trackLength){
        fuelLevel = fuelLevel-(trackLength/fuelConsume);
        return  fuelLevel> 0;
    }


    @Override
    public int compareTo(Object o) {
        Vehicle vehicle = (Vehicle) o;
        if(this.lap<vehicle.lap){
            return 1;
        }else if(this.currentTrack<vehicle.currentTrack){
            if(this.lap>vehicle.lap){
                return -1;
            }else {
                return 1;
            }
        }else if(this.currentTrackRealDistance<vehicle.currentTrackRealDistance){
            if(this.lap==vehicle.lap&&this.currentTrack==vehicle.currentTrack){
                return 1;
            }else{
                return -1;
            }
        }else{
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(int currentTrack) {
        this.currentTrack = currentTrack;
    }

    public float getCurrentTrackRealDistance() {
        return currentTrackRealDistance;
    }

    public void setCurrentTrackRealDistance(float currentTrackRealDistance) {
        this.currentTrackRealDistance = currentTrackRealDistance;
    }

    public int getCurrentTrackDistance() {
        return currentTrackDistance;
    }

    public void setCurrentTrackDistance(int currentTrackDistance) {
        this.currentTrackDistance = currentTrackDistance;
    }

    public int getLap() {
        return lap;
    }

    public void setLap(int lap) {
        this.lap = lap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
