package MEF;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArvoreTeste extends Modelo {
	Map<Integer, Set<Vertice>> rotulos;
	
	public ArvoreTeste() {
		rotulos = new HashMap<Integer, Set<Vertice>>();
	}

	public Map<Integer, Set<Vertice>> getRotulos() {
		return rotulos;
	}
	
	public void setRotulos(Map<Integer, Set<Vertice>> rotulos) {
		this.rotulos = rotulos;
	}
}
