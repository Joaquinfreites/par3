package utnfc.backend.parcial;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Garage {
    private final List<Alquiler> alquileres;
    public Garage(List<Alquiler> alquileres) {
        this.alquileres = List.copyOf(alquileres);
    }

    public double total() {
        return alquileres.stream().mapToDouble(Alquiler::importe).sum();
    }

    public List<Alquiler> filtrar(Predicate<Alquiler> criterio) {
        return alquileres.stream().filter(criterio).toList();
    }

    public Map<String, Double> totalesPorSucursal() {
        return alquileres.stream().collect(Collectors.groupingBy(
                Alquiler::getSucursal,
                Collectors.summingDouble(Alquiler::importe)));
    }
    public List<Alquiler> todos() {
        return alquileres;
    }

    public Map<String, Long> porEstado() {
        return alquileres.stream().collect(Collectors.groupingBy(Alquiler::getEstado, TreeMap::new , Collectors.counting()));
    }

}
