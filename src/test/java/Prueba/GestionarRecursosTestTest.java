package Prueba;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestionarRecursosTestTest {
    
    public GestionarRecursosTestTest() {
    }

    @Test
    public void testDescripcionCorrecta() {
        System.out.println("DescripcionCorrecta");
        String a = "Proyector";
        GestionarRecursosTest instance = new GestionarRecursosTest();
        boolean expResult = true;
        boolean result = instance.DescripcionCorrecta(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    //Se ingresa una descripcion incorrecta.
    @Test
    public void testDescripcionCorrecta2() {
        System.out.println("DescripcionIncorrecta");
        String a = "144";
        GestionarRecursosTest instance = new GestionarRecursosTest();
        boolean expResult = false;
        boolean result = instance.DescripcionCorrecta(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }


    @Test
    public void testCantidadCorrecta() {
        System.out.println("CantidadCorrecta");
        String cantidad = "5";
        GestionarRecursosTest instance = new GestionarRecursosTest();
        boolean expResult = true;
        boolean result = instance.CantidadCorrecta(cantidad);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    //Se ingresa una cantidad incorrecta.
    @Test
    public void testCantidadCorrecta2() {
        System.out.println("CantidadIncorrecta");
        String cantidad = "dasdsa";
        GestionarRecursosTest instance = new GestionarRecursosTest();
        boolean expResult = false;
        boolean result = instance.CantidadCorrecta(cantidad);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of ResponsableCorrecto method, of class GestionarRecursosTest.
     */
    @Test
    public void testResponsableCorrecto() {
        System.out.println("ResponsableCorrecto");
        String responsable = "Juan";
        GestionarRecursosTest instance = new GestionarRecursosTest();
        boolean expResult = true;
        boolean result = instance.ResponsableCorrecto(responsable);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    //Se ingresa un responsable incorrecto.
    @Test
    public void testResponsableCorrecto2() {
        System.out.println("ResponsableIncorrecto");
        String responsable = "5";
        GestionarRecursosTest instance = new GestionarRecursosTest();
        boolean expResult = false;
        boolean result = instance.ResponsableCorrecto(responsable);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
