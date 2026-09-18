package utnfc.backend.parcial;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AlquilerTest {

    @Test
    public void testCreacionAlquilerValidoYCalculoImporte() {
        Alquiler alquiler = new Alquiler("A001", "Ana Gomez", "SEDAN", "Centro", 3, 5000.0, 300);
        assertEquals("A001", alquiler.getId());
        assertEquals("Ana Gomez", alquiler.getCliente());
        assertEquals("SEDAN", alquiler.getCategoria());
        assertEquals("Centro", alquiler.getSucursal());
        assertEquals(3, alquiler.getDias());
        assertEquals(5000.0, alquiler.getTarifaDiaria());
        assertEquals(300, alquiler.getKmIncluidos());
        assertEquals("ABIERTO", alquiler.estado());
        assertFalse(alquiler.estaCerrado());
        assertEquals(15000.0, alquiler.importe(), 0.01);
    }

    @Test
    public void testInvarianteTextoObligatorio() {
        assertThrows(IllegalArgumentException.class, () ->
                new Alquiler("", "Ana", "SEDAN", "Centro", 3, 5000.0, 300));
        assertThrows(IllegalArgumentException.class, () ->
                new Alquiler("A001", "   ", "SEDAN", "Centro", 3, 5000.0, 300));
        assertThrows(IllegalArgumentException.class, () ->
                new Alquiler("A001", "Ana", null, "Centro", 3, 5000.0, 300));
    }

    @Test
    public void testInvariantesNumericos() {
        // dias <= 0
        assertThrows(IllegalArgumentException.class, () ->
                new Alquiler("A001", "Ana", "SEDAN", "Centro", 0, 5000.0, 300));
        // tarifaDiaria <= 0
        assertThrows(IllegalArgumentException.class, () ->
                new Alquiler("A001", "Ana", "SEDAN", "Centro", 3, 0.0, 300));
        // kmIncluidos < 0
        assertThrows(IllegalArgumentException.class, () ->
                new Alquiler("A001", "Ana", "SEDAN", "Centro", 3, 5000.0, -10));
    }

    @Test
    public void testCreacionDesdeCampos() {
        String[] campos = {"A001", "Ana Gomez", "SEDAN", "Centro", "3", "5000.0", "300", "ABIERTO"};
        Alquiler alq = Alquiler.desdeCampos(campos);
        assertEquals("A001", alq.getId());
        assertEquals(15000.0, alq.importe(), 0.01);

        // Estado inválido para modelo inicial
        String[] camposInvalidos = {"A002", "Beto", "SUV", "Norte", "2", "6000.0", "200", "OTRO"};
        assertThrows(IllegalArgumentException.class, () -> Alquiler.desdeCampos(camposInvalidos));
    }
}
