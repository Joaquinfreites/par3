package utnfc.backend.parcial;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserAlquileres {
    public ResultadoParseo leer(Path ruta) throws IOException {
        List<Alquiler> alquileres = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        int leidas = 0;
        int descartadas = 0;
        try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            if (!"id,cliente,categoria,sucursal,dias,tarifaDiaria,kmIncluidos,estado".equals(lector.readLine())) {
                throw new IllegalArgumentException("encabezado incorrecto");
            }
            String linea;
            while ((linea = lector.readLine()) != null) {
                leidas++;
                try {
                    String[] campos = Arrays.stream(linea.split(",", -1))
                            .map(String::strip)
                            .toArray(String[]::new);
                    // Cada fila debe tener exactamente la cantidad de columnas indicadas por el
                    // encabezado que se está leyendo (8 o 9 según corresponda).
                    if (campos.length != 8 && campos.length != 9) {
                        // Si una fila no coincide con el ancho esperado del archivo, se considera inválida.
                        throw new IllegalArgumentException("cantidad de columnas incorrecta");
                    }
                    if (campos[7].equals("CANCELADO")) {
                        descartadas++;
                        continue;
                    }
                    if (!campos[7].equals("ABIERTO")) {
                        throw new IllegalArgumentException("estado desconocido");
                    }
                    if (campos[8] == null || campos[8].isBlank()) {
                        throw new IllegalArgumentException("vacío kmRecorrido");
                    }
                    alquileres.add(Alquiler.desdeCampos(campos));
                } catch (IllegalArgumentException error) {
                    errores.add("Linea " + (leidas + 1) + ": " + error.getMessage());
                }
            }
        }
        return new ResultadoParseo(alquileres, leidas, descartadas, errores);
    }
}
