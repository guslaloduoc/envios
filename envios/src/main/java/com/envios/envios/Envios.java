package com.envios.envios;

public class Envios {

	private int numeroSeguimiento;
	private String estado;
	private String ubicacionActual;

	public Envios(
			int numeroSeguimiento,
			String estado,
			String ubicacionActual) {
		this.numeroSeguimiento = numeroSeguimiento;
		this.estado = estado;
		this.ubicacionActual = ubicacionActual;
	}

	// metodos para acceder a los valores
	public int getNumeroSeguimiento() {
		return numeroSeguimiento;
	}

	public String getUbicacionActual() {
		return ubicacionActual;
	}

	public String getEstado() {
		return estado;
	}

}