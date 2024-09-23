package menus;

import utilidades.GestorEntradaConsola;

public class MenuActividades {

    public static void menu() {
        while (true) {
            System.out.println("1. Agregar actividad");
            System.out.println("2. Listar actividades");
            System.out.println("3. Actualizar actividad");
            System.out.println("4. Eliminar actividad");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = GestorEntradaConsola.leerEntero();

            switch (opcion) {
                case 1 -> {
                }
                case 2 -> {
                }
                case 3 -> {
                }
                case 4 -> {
                }
                case 0 ->
                    System.exit(0);
                default ->
                    System.out.println("Opción no válida");
            }
        }
    }
    
}
