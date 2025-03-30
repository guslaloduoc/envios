package com.envios.envios;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EnviosController {

	private List<Envios> envios = new ArrayList<>();

	// llenamis lista con ejemplos de envios
	public EnviosController() {
		envios.add(new Envios(1, "En ruta", "Las Condes"));
		envios.add(new Envios(2, "En preparación", "Ñuñoa"));
		envios.add(new Envios(3, "Entregado", "No aplica"));
		envios.add(new Envios(4, "Recibido", "Providencia"));
	}

	// accedemos a la lista de todos los pedidos
	@GetMapping("/envios")
	public List<Envios> getEnvios() {
		return envios;
	}

	// accedemos al enpoint detalle de pedido con codigo pedido
	@GetMapping("/envios/{codigoPedido}")
	public Envios getViajesById(@PathVariable int codigoPedido) {
		for (Envios envios : envios) {
			if (envios.getNumeroSeguimiento() == codigoPedido) {
				return envios;
			}
		}
		return null;
	}

}
