package utnfc.backend.parcial;

import java.util.List;

public final class ResultadoParseo {
    private final List<Alquiler> alquileres;
    private final int leidas;
    private final int descartadas;
    private final List<String> errores;

    public ResultadoParseo(
            List<Alquiler> alquileres,
            int leidas,
            int descartadas,
            List<String> errores) {
        this.alquileres = List.copyOf(alquileres);
        this.leidas = leidas;
        this.descartadas = descartadas;
        this.errores = List.copyOf(errores);
    }

    public List<Alquiler> getAlquileres() {
        return alquileres;
    }

    public int getProcesadas() {
        return alquileres.size();
    }

    public int getLeidas() {
        return leidas;
    }

    public int getDescartadas() {
        return descartadas;
    }

    public int getInvalidas() {
        return errores.size();
    }

    public List<String> getErrores() {
        return errores;
    }
}
