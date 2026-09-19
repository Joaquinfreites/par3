package utnfc.backend.parcial;

public class Alquiler {
    protected  String id;
    protected  String cliente;
    protected  String categoria;
    protected  String sucursal;
    protected  int dias;
    protected  double tarifaDiaria;
    protected  int kmIncluidos;
    protected String estado;

    public Alquiler() {

    }

    public Alquiler(String id, String cliente, String categoria, String sucursal,
                    int dias, double tarifaDiaria, int kmIncluidos, String estado) {
        this.id = textoObligatorio(id, "id");
        this.cliente = textoObligatorio(cliente, "cliente");
        this.categoria = textoObligatorio(categoria, "categoria");
        this.sucursal = textoObligatorio(sucursal, "sucursal");
        if (dias <= 0 || tarifaDiaria <= 0 || kmIncluidos < 0) {
            throw new IllegalArgumentException("dias, tarifa y kilometros deben ser validos");
        }
        this.dias = dias;
        this.tarifaDiaria = tarifaDiaria;
        this.kmIncluidos = kmIncluidos;
        this.estado = estado;
    }

    public static Alquiler desdeCampos(String[] campos) {
        if (campos.length != 8) {
            throw new IllegalArgumentException("cantidad de columnas incorrecta");
        }
        if (!campos[7].equals("ABIERTO")) {
            throw new IllegalArgumentException("el modelo inicial solo admite ABIERTO");
        }
        return new Alquiler(campos[0], campos[1], campos[2], campos[3],
                Integer.parseInt(campos[4]), Double.parseDouble(campos[5]),
                Integer.parseInt(campos[6]),campos[7]);
    }

    public double importe() {
        return dias * tarifaDiaria;
    }

    public boolean estaCerrado() {
        return false;
    }

    public String estado() {
        return "ABIERTO";
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getSucursal() {
        return sucursal;
    }

    public int getDias() {
        return dias;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public int getKmIncluidos() {
        return kmIncluidos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    protected static String textoObligatorio(String valor, String nombre) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(nombre + " no puede estar vacio");
        }
        return valor.strip();
    }
    @Override
    public String toString() {
        return "Alquiler{" +
                "id='" + id + '\'' +
                ", cliente='" + cliente + '\'' +
                ", categoria='" + categoria + '\'' +
                ", dias=" + dias +
                '}';
    }
}
