package utnfc.backend.parcial;

import java.nio.file.Path;

/** Ejecutable mínimo para inspeccionar el procesamiento del modelo inicial. */
public class Main {
    public static void main(String[] args) throws Exception {
        Path ruta = Path.of(args.length == 0 ? "datos/alquileres.csv" : args[0]);
        ResultadoParseo resultado = new ParserAlquileres().leer(ruta);
        Garage garage = new Garage(resultado.getAlquileres());
        System.out.printf("Archivo: %s%nFilas: %d | procesadas: %d | descartadas: %d | inválidas: %d%n",
                ruta, resultado.getLeidas(), resultado.getAlquileres().size(),
                resultado.getDescartadas(), resultado.getInvalidas());
        System.out.println("Alquiler con más de 5 dias"+ garage.filtrar(a-> a.getDias() < 5));
        System.out.printf("Total: %.2f%nTotales por sucursal: %s%n", garage.total(), garage.totalesPorSucursal());
        if (!resultado.getErrores().isEmpty()) {
            System.out.println("Errores: " + resultado.getErrores());
        }
    }
}
