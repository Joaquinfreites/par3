package utnfc.backend.parcial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class ParserAlquileresTest {

    @TempDir
    Path tempDir;

    private ResultadoParseo parsear(String cabecera, String contenido) throws IOException {
        Path csvFile = tempDir.resolve("alquileres-test.csv");
        Files.writeString(csvFile, cabecera + "\n" + contenido);
        return new ParserAlquileres().leer(csvFile);
    }

    @Test
    public void testProcesadasDescartadasEInvalidas() throws IOException {
        String cabecera = "id,cliente,categoria,sucursal,dias,tarifaDiaria,kmIncluidos,estado";
        String contenido =
                "A001,Ana Gomez,SEDAN,Centro,3,5000.0,300,ABIERTO\n" +
                "A002,Beto Perez,SUV,Norte,2,6000.0,200,CANCELADO\n" +
                "A003,Caro Diaz,COMPACTO,Sur,0,4000.0,100,ABIERTO\n"; // dias = 0 es inválido

        ResultadoParseo resultado = parsear(cabecera, contenido);

        assertEquals(3, resultado.getLeidas());
        assertEquals(1, resultado.getProcesadas());
        assertEquals(1, resultado.getDescartadas());
        assertEquals(1, resultado.getInvalidas());
        assertEquals(1, resultado.getAlquileres().size());
        assertEquals("A001", resultado.getAlquileres().get(0).getId());
    }

    @Test
    public void testValidaAnchoAntesDeDescartarCancelado() throws IOException {
        String cabecera = "id,cliente,categoria,sucursal,dias,tarifaDiaria,kmIncluidos,estado";
        // Fila CANCELADO con columna extra (9 columnas en vez de 8)
        String contenido = "A002,Beto Perez,SUV,Norte,2,6000.0,200,CANCELADO,columna_extra\n";

        ResultadoParseo resultado = parsear(cabecera, contenido);

        assertEquals(1, resultado.getLeidas());
        assertEquals(0, resultado.getDescartadas());
        assertEquals(1, resultado.getInvalidas());
    }

    @Test
    public void testEncabezadoInvalido() throws IOException {
        String cabeceraInvalida = "id;cliente;categoria;sucursal;dias;tarifa;km;estado";
        String contenido = "A001,Ana,SEDAN,Centro,3,5000,300,ABIERTO\n";

        assertThrows(IllegalArgumentException.class, () -> parsear(cabeceraInvalida, contenido));
    }

    @Test
    public void testFilaConColumnasInsuficientes() throws IOException {
        String cabecera = "id,cliente,categoria,sucursal,dias,tarifaDiaria,kmIncluidos,estado";
        String contenido = "A001,Ana,SEDAN,Centro,3\n";

        ResultadoParseo resultado = parsear(cabecera, contenido);

        assertEquals(1, resultado.getLeidas());
        assertEquals(0, resultado.getProcesadas());
        assertEquals(1, resultado.getInvalidas());
    }
}
