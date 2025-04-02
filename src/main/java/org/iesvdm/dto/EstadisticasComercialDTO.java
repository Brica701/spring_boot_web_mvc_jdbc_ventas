package org.iesvdm.dto;

public class EstadisticasComercialDTO {

    private float totalPedidos;
    private float mediaPedidos;

    public EstadisticasComercialDTO(float totalPedidos, float mediaPedidos) {
        this.totalPedidos = totalPedidos;
        this.mediaPedidos = mediaPedidos;
    }

    // Getters y setters
    public float getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(float totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public float getMediaPedidos() {
        return mediaPedidos;
    }

    public void setMediaPedidos(float mediaPedidos) {
        this.mediaPedidos = mediaPedidos;
    }
}
