## Objetivo

#### El objetivo de la práctica es implementar un programa concurrente en java que simule carreras de coches de Fórmula 1. En general una carrera de coches se encuentra formada por las siguientes entidades:

- Vehículo. Entidad que representa cada uno de los coches que
componen la parrilla de salida y que correrán en la carrera. Como en
todas las carreras, cada coche tendrá características técnicas diferentes
como, por ejemplo: mejor trazado de curvas, más aceleración en recta,
etc. Así mismo se pueden producir adelantamientos o posibles
problemas en un mismo tramo.
- Circuito. Entidad que representa en lugar donde corren los vehículos.
Un circuito se compone de una serie de tramos que pueden ser lentos
o rápidos en función de si dificultan o facilitan la conducción a los
pilotos, es decir, tramos con muchas curvas complican la conducción
a los pilotos y, por tanto, ralentizan su velocidad. Por el contrario,
tramos en línea recta serían fáciles de cubrir por los pilotos y por lo
tanto los vehículos irían más rápidos. En función de cómo se
organicen estos tramos darán lugar a distintos tipos de circuitos y, por
lo tanto, distintos tipos de carreras.
- Panel informativo o ranking de la carrera que registra, calcula y
muestra información en "directo" de los vehículos de la carrera como:
la posición actual, diferencia entre ellos, diferencia a meta,
velocidades, etc. De esta forma, cada X tiempo la clasificación deberá
ser actualizada con los nuevos cambios que se producen en la carrera.
- El juez de carrera será el encargado de dar el pistoletazo de salida,
controlar la estancia de los vehículos en boxes y revisar que todo el
mundo ha acabado la carrera correctamente. Finalizar la carrera
correctamente significa que todos los coches tendrán que pasar por
meta, la clasificación se actualizará en el marcador, los coches darán
otra vuelta y por último terminarán en boxes.

### Funcionamiento

#### La lógica de la simulación será la siguiente: se crea un circuito a partir de diversos tramos. Después, se crean N vehículos cada uno con sus especificaciones técnicas. Entonces, el juez de carrera comprueba que todo está bien en la parrilla de salida, y si todo está bien da el pistoletazo de salida. A partir de ahí, los vehículos serán invocados cada 5 segundos, tiempo suficiente para la simulación anterior y que todos ellos compitan por intentar ser los primeros. Los vehículos tendrán que decidir si avanzan o no al siguiente tramo o, en caso de adelantamiento, si debieran avanzar uno más que su contrincante. El circuito informará al panel de control y a los competidores con datos "actuales" de los vehículos como posición actual, metros hacia la meta, distancia entre vehículos, etc. Como es lógico, el avance de cada tramo hará que los vehículos consuman combustible, teniendo que pasar a boxes a repostar cuando se encuentren en la reserva. La carrera terminará cuando todos los vehículos pasen la línea de meta, den una vuelta al circuito extra y acaben en boxes. El juez de la carrera se encargará de comprobar que se cumplen cada una de las situaciones anteriores. Una vez analizado el funcionamiento del programa, se pide que los alumnos implementen la siguiente jerarquía de clases para solucionar la práctica:
- Clase Racetrack (Tramo) encargada de modelar un tramo del circuito
donde correrán los coches. Un tramo representa una parte de un circuito
y puede tener distintas formas: curvo, recto, múltiples curvas, cambio de
rasante, etc. cada tipo de tramo requiere de un tiempo para ser cruzado,
es decir, no es lo mismo un tramo recto donde los vehículos pueden ir a
máxima velocidad y tardarán menos tiempo en recorrerlo que uno que
tenga múltiples curvas.
- Clase Race (Carrera) encargada de modelar el circuito donde correrán los
coches. Una carrera se encuentra compuesta por una serie de tramos que
pueden tener distintas formas, como se ha indicado anteriormente. El
objetivo de esta clase será la de gestionar la lógica del movimiento de los
vehículos, informar al panel de control y a los competidores con datos
"actuales" de los vehículos como posición actual, metros hacia la meta,
distancia entre vehículos, etc.
- Clase Vehicle (Vehículo) encargada de modelar el coche que correrá en
el circuito. Cada vehículo deberá ser modelado teniendo en cuenta
características técnicas como: velocidad, gasto de combustible, motor, etc.
Cada vez que un vehículo avanza al siguiente tramo se actualizaran
aspectos relacionados con velocidad, combustible, etc. En el caso en el
que no quede combustible el Vehículo tendrá que ir a boxes durante 2
simulaciones. Además, cada vehículo tendrá que decidir si avanzan o no
al siguiente tramo, cuánto avanza dentro del tramo y, en caso de
adelantamiento, si debieran avanzar uno más que su contrincante
- Clase Score (Marcador) encargada de modelar el marcador donde se
recoge toda la información de la carrera como posiciones, tiempos,
distancias a meta y distancias entre los vehículos.
- Clase RaceJudge (Juez de Carrera) encargada de modelar al juez de la
carrera. El objetivo principal de esta clase es controlar el desarrollo
correcto de la carrera desde que se inicia, cuando el juez da el pistoletazo
de salida, hasta cuando se termina la carrera y se posicionan todos los
vehículos en boxes.
- Clase Principal que contendrá toda la lógica para crear, ejecutar y parar
los hilos que se necesiten en la aplicación. Por último, el método principal
tendrá en cuanta la entrada por pantalla, si se introduce algún valor se
deberá parar la carrera y finalizar mostrando el resultado una vez se paren
todos los vehículos.