package principal;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import paqueteDAO.ClaseDAO;
import utilidades.*;
import paqueteDAO.DocenteDAO;
import paqueteDTO.*;
import paqueteDTO.DocenteDTO;

public class Main {

    private static ConnectionManager connectionManager;

    public static void cargarClase() {
        connectionManager = new ConnectionManager();

        ClaseDTO claseDTO = new ClaseDTO();
        System.out.println("Codigo act: ");
        claseDTO.setCodigoActividad(GestorEntradaConsola.leerString());
        claseDTO.setAsignatura("Prueba");
        claseDTO.setLegajoDocente(1234);
        claseDTO.setNumeroAula(33);
        claseDTO.setCodigoHorario("123");
        ClaseDAO claseDAO = new ClaseDAO(connectionManager.getConnection());
        try {
            claseDAO.agregarClase(claseDTO);
            // Validar las entradas
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            connectionManager.close();
        }
    }

    public static void cargarEvento() {
    }

    /**
     * Carga una actividad hasta que el usuario desee no continuar
     */
    public static void cargarActividad() {
        do {

            System.out.println("1. CLASE");
            System.out.println("2. EVENTO");
            int op = GestorEntradaConsola.leerEntero();
            switch (op) {
                case 1:
                    cargarClase();
                    break;
                case 2:
                    cargarEvento();
                default:
                    break;
            }
        } while (GestorEntradaConsola.confirmar());
    }

    public static void listarActividades() {
    }

    public static void menuActividades() {
        int op;
        String opciones = "Gestion de actividades" + '\n' + "1. ALTAS" + '\n' + "2. LISTADO" + '\n' + "3. BAJAS" + '\n'
                + "4. MODIFICACIONES" + '\n' + "0. Atras";
        do {
            System.out.println(opciones);
            System.out.print("--> ");
            op = GestorEntradaConsola.leerEntero();
            switch (op) {
                case 1 -> {
                    cargarActividad();
                }
                case 2 -> {
                    listarActividades();

                }
                case 3 -> {
                }
                case 4 -> {
                }
            }
        } while (op != 0);
    }

    public static void menuAulas() {
    }

    public static void menuInformes() {
    }

    public static void menuRecursos() {
    }

    public static void cargarDocentes() {
        do {
        } while (GestorEntradaConsola.confirmar());
    }

    public static void listarDocentes() {
        GestorEntradaConsola.pausar();
    }

    public static void menuDocentes() {
        int op;
        String opciones = "MENU DOCENTES" + '\n' + "1. ALTAS" + '\n' + "2. LISTADO" + '\n' + "3. BAJAS" + '\n'
                + "4. MODIFICACIONES" + '\n' + "0. Atras";
        do {
            System.out.println(opciones);
            System.out.print("--> ");
            op = GestorEntradaConsola.leerEntero();
            switch (op) {
                case 1 -> {
                    cargarDocentes();
                }
                case 2 -> {
                    listarDocentes();

                }
                case 3 -> {
                }
                case 4 -> {
                }
            }
        } while (op != 0);
    }

    /**
     * Menu principal (consola)
     */
    public static void menu() {
        int op;
        String opciones = "Principal" + '\n' + "1. ACTIVIDADES" + '\n' + "2. AULAS" + '\n' + "3. INFORMES" + '\n'
                + "4. RECURSOS" + '\n' + "5. DOCENTES" + '\n' + "0. Salir";
        do {
            System.out.println(opciones);
            System.out.print("--> ");
            op = GestorEntradaConsola.leerEntero();
            switch (op) {
                case 1 -> {
                    menuActividades();
                }
                case 2 -> {
                    menuAulas();
                }
                case 3 -> {
                    menuInformes();
                }
                case 4 -> {
                    menuRecursos();
                }
            }
        } while (op != 0);

    }

    public static void main(String[] args) {
        menu();
//        System.out.println("Hola mundo!");
//        Conexion conexion = new Conexion();
//        
//        DocenteDTO docenteDTO = new DocenteDTO(777, "Carlos", "Alvarez", "711carlosalva771@gmail.com", Boolean.TRUE, "Dr en juegitos", 777);
//        DocenteDAO docenteDAO = new DocenteDAO(conexion.getConnection());
//        docenteDAO.agregarDocente(docenteDTO);
//        
//        System.out.println("Docente ingresado: ");
//        DocenteDTO docenteObtenido = docenteDAO.obtenerDocentePorLegajo(777);
//        System.out.println(docenteObtenido.toString());
//        conexion.close();

    }
}
