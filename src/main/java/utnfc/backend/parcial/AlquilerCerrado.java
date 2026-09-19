package utnfc.backend.parcial;

public class AlquilerCerrado extends Alquiler {
    private final int kmRecorridos;

    public AlquilerCerrado(String id, String cliente, String categoria, String sucursal, int dias, double tarifaDiaria, int kmIncluidos, String estado, int kmRecorridos) {
        super(id, cliente, categoria, sucursal, dias, tarifaDiaria, kmIncluidos, estado);
        if (kmRecorridos < 0) {
            throw new IllegalArgumentException("Los kilometros recorridos deben ser validos");
        }
        this.kmRecorridos = kmRecorridos;
    }
    @Override
    public double importe(){
        double importeBase = dias * tarifaDiaria;
        double adicional = 0.0;
        if (kmRecorridos > kmIncluidos){
             adicional = (kmRecorridos - kmIncluidos) * 150;
        }
        if( kmRecorridos > kmIncluidos * 5){
            throw new IllegalArgumentException("Fila rechazada");
        }
        double importeTotal =  importeBase + adicional;
        return  importeTotal;
    }
    @Override
    public String toString() {
        return super.toString() + " | AlquilerCerrado{" +
                "kmRecorridos=" + kmRecorridos +
                '}';
    }
}
