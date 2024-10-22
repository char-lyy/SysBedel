package Prueba;

public class GestionarAulasTest {
    
    public boolean NumeroAulaCorrecto(String numero){
        //se verifica que se ingrese un numero entero.
        try {
        int valor = Integer.parseInt(numero);
        //El numero de aula debe estar entre la 20 y 31.
        if (valor >= 20 && valor <= 31){
            return valor > 0;
        }else{
            return false;
        }
    } catch (NumberFormatException e) {
        return false; // Si no es un número entero válido
    }          
                
    }
    
    public boolean AforoCorrecto(String aforo){
        //se verifica que se ingrese un numero entero.
        try {
        int valor = Integer.parseInt(aforo);
        //el aforo minimo es 20 y el maximo es 50
        if (valor >= 20 && valor <= 50){
            return valor > 0;
        }else{
            return false;
        }
    } catch (NumberFormatException e) {
        return false; // Si no es un número entero válido
    }          
        
    }
    
    public boolean ObservacionesCorrectas(String descripcion){
    // Validar si la descripción es nula ni vacía
    if (descripcion == null || descripcion.trim().isEmpty()) {
        return true;
    }

    // Validar longitud mínima y máxima (por ejemplo, entre 3 y 50 caracteres)
    int length = descripcion.trim().length();
    if (length < 3 || length > 50) {
        return false;
    }

    // Validar que la descripción solo contenga letras, números, espacios y ciertos caracteres especiales
    // Por ejemplo: punto, coma, guion o paréntesis
    String pattern = "^[a-zA-Z0-9.,\\-()\\s]+$";
    if (!descripcion.matches(pattern)) {
        return false;
    }

    // Si cumple con todas las condiciones, la descripción es válida
    return true;
}
    
    }
    
