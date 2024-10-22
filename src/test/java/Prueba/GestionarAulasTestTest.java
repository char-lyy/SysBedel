package Prueba;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestionarAulasTestTest {
    
    public GestionarAulasTestTest() {
    }

    /**
     * Test of NumeroAulaCorrecto method, of class GestionarAulasTest.
     */
    @Test
    public void testNumeroAulaCorrecto() {
        System.out.println("NumeroAulaCorrecto");
        String numero = "25";
        GestionarAulasTest instance = new GestionarAulasTest();
        boolean expResult = true;
        boolean result = instance.NumeroAulaCorrecto(numero);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
     
    }
    
    //se ingresa un numero de aula incorrecto.
    @Test
    public void testNumeroAulaCorrecto2() {
        System.out.println("NumeroAulaIncorrecto1");
        String numero = "59";
        GestionarAulasTest instance = new GestionarAulasTest();
        boolean expResult = false;
        boolean result = instance.NumeroAulaCorrecto(numero);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
     
    }
    
    //se ingresa cualquier cosa que no sea numero.
    @Test
    public void testNumeroAulaCorrecto3() {
        System.out.println("NumeroAulaIncorrecto2");
        String numero = "jsahbdsa";
        GestionarAulasTest instance = new GestionarAulasTest();
        boolean expResult = false;
        boolean result = instance.NumeroAulaCorrecto(numero);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
     
    }

    /**
     * Test of AforoCorrecto method, of class GestionarAulasTest.
     */
    @Test
    public void testAforoCorrecto() {
        System.out.println("AforoCorrecto");
        String aforo = "40";
        GestionarAulasTest instance = new GestionarAulasTest();
        boolean expResult = true;
        boolean result = instance.AforoCorrecto(aforo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    //Se ingresa un aforo incorrecto.
    @Test
    public void testAforoCorrecto2() {
        System.out.println("AforoIncorrecto1");
        String aforo = "100";
        GestionarAulasTest instance = new GestionarAulasTest();
        boolean expResult = false;
        boolean result = instance.AforoCorrecto(aforo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    //Se ingresa cualquier cosa menos numeros.
    @Test
    public void testAforoCorrecto3() {
        System.out.println("AforoIncorrecto2");
        String aforo = "uibsauydbsa";
        GestionarAulasTest instance = new GestionarAulasTest();
        boolean expResult = false;
        boolean result = instance.AforoCorrecto(aforo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of ObservacionesCorrectas method, of class GestionarAulasTest.
     */
    @Test
    public void testObservacionesCorrectas() {
        System.out.println("ObservacionesCorrectas");
        String descripcion = "El aula posee 2 bancos defectuosos";
        GestionarAulasTest instance = new GestionarAulasTest();
        boolean expResult = true;
        boolean result = instance.ObservacionesCorrectas(descripcion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    //Se ingresa una observacion vacia.
    @Test
    public void testObservacionesCorrectas2() {
        System.out.println("ObservacionesCorrectas2");
        String descripcion = " ";
        GestionarAulasTest instance = new GestionarAulasTest();
        boolean expResult = true;
        boolean result = instance.ObservacionesCorrectas(descripcion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    //Se ingresa una observacion incorrecta.
    @Test
    public void testObservacionesCorrectas3() {
        System.out.println("ObservacionesIncorrectas1");
        String descripcion = "2";
        GestionarAulasTest instance = new GestionarAulasTest();
        boolean expResult = false;
        boolean result = instance.ObservacionesCorrectas(descripcion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    //Se ingresa una observacion incorrecta 2.
    @Test
    public void testObservacionesCorrectas4() {
        System.out.println("ObservacionesIncorrectas2");
        String descripcion = "2a";
        GestionarAulasTest instance = new GestionarAulasTest();
        boolean expResult = false;
        boolean result = instance.ObservacionesCorrectas(descripcion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    
    
}
