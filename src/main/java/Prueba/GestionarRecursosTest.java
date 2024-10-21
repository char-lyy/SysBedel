package Prueba;


public class GestionarRecursosTest {
    
public boolean DescripcionCorrecta(String a) {
    // Validar si la descripción no es nula ni vacía
    if (a == null || a.trim().isEmpty()) {
        return false;
    }

    // Validar longitud mínima y máxima (por ejemplo, entre 3 y 50 caracteres)
    int length = a.trim().length();
    if (length < 3 || length > 50) {
        return false;
    }

    // Validar que la descripción solo contenga letras, números, espacios y ciertos caracteres especiales
    // Por ejemplo: punto, coma, guion o paréntesis
    String pattern = "^[A-Za-z]+";
    if (!a.matches(pattern)) {
        return false;
    }

    // Si cumple con todas las condiciones, la descripción es válida
    return true;
}

public boolean CantidadCorrecta(String cantidad) {
    // Verificar si la cantidad es un número entero positivo
    try {
        int valor = Integer.parseInt(cantidad);
        return valor > 0;
    } catch (NumberFormatException e) {
        return false; // Si no es un número entero válido
    }
}

public boolean ResponsableCorrecto(String responsable) {
    // Verificar si el nombre del responsable solo contiene letras y espacios
    return responsable.matches("[a-zA-Z ]+");
}


}