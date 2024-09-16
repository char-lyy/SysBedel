package utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author char.ly
 */
public class GestorSalidaConsola {

    /**
     * Este método formatea un título añadiendo caracteres de formato a ambos
     * lados del título para que ocupe un espacio total de longitud máxima
     * especificada.
     *
     * @param titulo String que representa el título que se desea formatear.
     * @param longitudMaximaConsola Longitud total máxima deseada del título
     * formateado, incluyendo los caracteres de formato y el título en sí.
     * @param c1 Carácter utilizado para formatear el lado izquierdo del título.
     * @param c2 Carácter utilizado para formatear el lado derecho del título.
     * @return Una cadena que contiene el título formateado con caracteres `c1`
     * a la izquierda y caracteres `c2` a la derecha, con el título centrado
     * entre ellos.
     *
     * La longitud total del título formateado será igual a
     * `longitudMaximaConsola`, asumiendo que la longitud del título y los
     * caracteres de formato sumen esa longitud.
     *
     * La longitud de las cadenas de formato `cad1` y `cad2` se calcula en
     * función de la longitud total deseada y la longitud del título, asegurando
     * que el título esté centrado en el resultado.
     *
     * Ejemplo:
     *
     * Si `titulo` es "Mi Título", `longitudMaximaConsola` es 20, `c1` es '*' y
     * `c2` es '#':
     *
     * - La longitud total deseada es 20 caracteres. - La longitud del título es
     * 9 caracteres. - Se añaden caracteres '*' a la izquierda y '#' a la
     * derecha para alcanzar una longitud total de 20 caracteres.
     *
     * El resultado podría ser: "**** Mi Título ######"
     */
    public static String titulo(String titulo, int longitudMaximaConsola, char c1, char c2) {

        String cad1 = "";
        String cad2 = "";
        int medio = 0;

        int cantidadCaracteres = (int) (longitudMaximaConsola - titulo.length()) / 2 - 2;

        for (int i = 0; i <= cantidadCaracteres; i++) {
            cad1 = cad1 + c1;
        }

        for (int i = 0; i <= cantidadCaracteres; i++) {
            cad2 = cad2 + c2;
        }
        titulo = cad1 + " " + titulo + " " + cad2;

        return titulo;
    }

    /**
     * Genera un string con los nombres de los atributos no constantes (ni
     * static, ni final) de una clase, separados de manera que puedan ser usados
     * como títulos de columnas en la salida por consola.
     *
     * @param aClass La clase de la cual se obtienen los nombres de los
     * atributos.
     * @param LIMITE_CARACTERES_LINEA_CONSOLA La longitud máxima de caracteres
     * que pueden entrar en una línea de consola.
     * @return Un string con los nombres de los atributos formateados como
     * títulos de columnas.
     */
    public static void generarTitulosColumnas(Class<?> aClass, int LIMITE_CARACTERES_LINEA_CONSOLA) {
        // Obtener los atributos no constantes
        Field[] fields = obtenerAtributosNoConstantes(aClass);
        int cantidadAtributos = fields.length;

        // Calcular el espacio disponible para cada atributo en la línea de consola
        int espacioPorAtributo = (int) Math.floor(LIMITE_CARACTERES_LINEA_CONSOLA / (double) cantidadAtributos);

        // Construir el string con los nombres de los atributos formateados
        StringBuilder salida = new StringBuilder();

        for (Field field : fields) {
            String nombreAtributo = field.getName().toUpperCase();

            // Ajustar el nombre a la longitud adecuada
            if (nombreAtributo.length() > espacioPorAtributo) {
                nombreAtributo = nombreAtributo.substring(0, espacioPorAtributo); // Recortar si es necesario
            } else {
                // Rellenar con espacios en blanco hasta alcanzar el espacio por atributo
                while (nombreAtributo.length() < espacioPorAtributo) {
                    nombreAtributo += " ";
                }
            }

            salida.append(nombreAtributo);
        }

        System.out.println(salida.toString());
    }

    /**
     * Muestra en pantalla los valores de los atributos no constantes de una
     * instancia de clase, formateados para que cada atributo ocupe un espacio
     * determinado en la línea de la consola.
     *
     * @param aClass La clase de la cual se obtienen los atributos.
     * @param instancia La instancia de la cual se obtienen los valores de los
     * atributos.
     * @param LIMITE_CARACTERES_LINEA_CONSOLA La longitud máxima de caracteres
     * que pueden entrar en una línea de consola.
     */
    public static void mostrarTabulado(Class<?> aClass, Object instancia, int LIMITE_CARACTERES_LINEA_CONSOLA) {
        // Obtener los atributos no constantes
        Field[] fields = obtenerAtributosNoConstantes(aClass);
        int cantidadAtributos = fields.length;

        // Calcular el espacio disponible para cada atributo en la línea de consola
        int espacioPorAtributo = (int) Math.floor(LIMITE_CARACTERES_LINEA_CONSOLA / (double) cantidadAtributos);

        // Construir el string con los valores de los atributos formateados
        StringBuilder salida = new StringBuilder();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object valor = field.get(instancia);
                String valorAtributo = valor != null ? valor.toString() : "null";

                // Ajustar el valor a la longitud adecuada
                if (valorAtributo.length() > espacioPorAtributo) {
                    valorAtributo = valorAtributo.substring(0, espacioPorAtributo); // Recortar si es necesario
                } else {
                    // Rellenar con espacios en blanco hasta alcanzar el espacio por atributo
                    while (valorAtributo.length() < espacioPorAtributo) {
                        valorAtributo += " ";
                    }
                }

                salida.append(valorAtributo);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Mostrar en pantalla el string resultante
        System.out.println(salida.toString());
    }

    public static Field[] obtenerAtributosNoConstantes(Class<?> aClass) {
        List<Field> nonConstantFields = new ArrayList<>();

        while (aClass != null && aClass != Object.class) {
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                    nonConstantFields.add(field);
                }
            }
            aClass = aClass.getSuperclass();
        }

        return nonConstantFields.toArray(new Field[0]);
    }

}
