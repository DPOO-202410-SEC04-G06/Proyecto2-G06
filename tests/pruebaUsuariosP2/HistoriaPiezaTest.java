package pruebaUsuariosP2;

import static org.junit.jupiter.api.Assertions.*;
import galeria.Galeria;
import piezas.*;
import usuarios.UsuarioCorriente;
import pagos.Transaccion;

import java.util.ArrayList;
import java.util.Date;



public class HistoriaPiezaTest {

    private Galeria galeria;
    private UsuarioCorriente comprador1;
    private UsuarioCorriente comprador2;
    private Pieza pieza;

void setUp() {
        galeria = new Galeria();
        comprador1 = new UsuarioCorriente("Comprador uno", "123456789", "compradorUno", "contraseña123");
        comprador2 = new UsuarioCorriente("Comprador dos", "987654321", "compradorDos", "contraseña321");
        
        ArrayList<String> autores = new ArrayList<>();
        autores.add("Jeronimo vasquez");
        
        
        pieza = new Cuadro("Pepito Perez", Pieza.CUADRO, Pieza.SIN_PRECIO, autores, "2024", "Bogota", "Colombia", comprador1,
                            "Óleo", "1m", "2m", "Sí");

        galeria.crearUsuarioCorriente(comprador1.getNombre(), comprador1.getTelefono(), comprador1.getUsername(), comprador1.getPassword());
        galeria.crearUsuarioCorriente(comprador2.getNombre(), comprador2.getTelefono(), comprador2.getUsername(), comprador2.getPassword());
        
        
        Date fechaVenta = new Date(); 
        Transaccion venta = new Transaccion(comprador1.getUsername(), comprador2.getUsername(), fechaVenta, Transaccion.NO_SUBASTADO, 50, pieza.getTitulo(), 1, 10);
        pieza.getHistorialPagosCodigo().put(10, venta);
        pieza.cambiarPropietarioPieza(comprador2); 
    }

    void testHistoriaPieza() {
        
        assertNotNull(pieza.getMapaUsuarios().get(comprador1.getUsername()));
        assertNotNull(pieza.getMapaUsuarios().get(comprador2.getUsername()));

        
        Transaccion transaccionRegistrada = pieza.buscarTransaccionCodigo(10);
        assertNotNull(transaccionRegistrada);
        assertEquals(50, transaccionRegistrada.getPrecio());
        assertEquals(comprador2.getUsername(), pieza.getPropietario().getUsername());
        assertEquals(comprador1.getUsername(), transaccionRegistrada.getNombreVendedor());
        assertEquals(comprador2.getUsername(), transaccionRegistrada.getNombreComprador());

        
        assertEquals(comprador2.getUsername(), pieza.getPropietario().getUsername());
    }

}


