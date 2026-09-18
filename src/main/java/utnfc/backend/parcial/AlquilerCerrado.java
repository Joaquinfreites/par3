package utnfc.backend.parcial;

public class AlquilerCerrado extends Alquiler {
    private final int kmRecorridos;

    public AlquilerCerrado(String id, String cliente, String categoria, String sucursal, int dias, double tarifaDiaria, int kmIncluidos, int kmRecorridos) {
        this.id = textoObligatorio(id, "id");
        this.cliente = textoObligatorio(cliente, "cliente");
        this.categoria = textoObligatorio(categoria, "categoria");
        this.sucursal = textoObligatorio(sucursal, "sucursal");
        if (dias <= 0 || tarifaDiaria <= 0 || kmIncluidos < 0 || kmRecorridos < 0) {
            throw new IllegalArgumentException("dias, tarifa y kilometros deben ser validos");
        }
        this.dias = dias;
        this.tarifaDiaria = tarifaDiaria;
        this.kmIncluidos = kmIncluidos;
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
}
