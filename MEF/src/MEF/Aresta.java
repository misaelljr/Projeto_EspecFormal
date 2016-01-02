package MEF;

public class Aresta {
	
	//estrutura com referência a transição entre estados
	Vertice origem;
	public String in;
	public String out;
	Vertice destino;
	
	public Aresta() {
		super();
	}
	
	public Aresta(Vertice o, String entrada, String saida, Vertice d) {
		this();
		this.origem = o;
		this.in = entrada;
		this.out = saida;
		this.destino = d;
		o.getOut().add(this);
		d.getIn().add(this);
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public Vertice getOrigem() {
		return origem;
	}

	public void setOrigem(Vertice origem) {
		
		this.origem = origem;
	}

	public Vertice getDestino() {
		return destino;
	}

	public void setDestino(Vertice destino) {
		
		this.destino = destino;
	}
	
	@Override
	public String toString() {
		String s = " ";
		s+= "origem: " + this.getOrigem().toString() + "in: " + this.getIn() + "out: " + this.getOut() + 
				"destino: " + this.getDestino().toString();
		return s;
	}

}