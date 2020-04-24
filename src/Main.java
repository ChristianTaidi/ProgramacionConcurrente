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

    public static void main(String[] args){
        Race race = new Race();
        for(int i =0;i<5;i++){
            race.addVehicle(new Vehicle(race,i));
        }
        race.run();

    }
}
