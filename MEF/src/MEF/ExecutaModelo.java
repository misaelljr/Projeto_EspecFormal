package MEF;

public class ExecutaModelo {
	
	private Modelo model;
	private Vertice estado;
	private String utiEntrada;
	private String utiSaida;
	
	public ExecutaModelo(Modelo m){
		this.model = m;
		this.estado = m.getInicial();
	}
	
	public Vertice getEstado(){
		return estado;
	}
	
	public void setEstado (Vertice stage){
		this.estado = stage;
	}
	
	public String getUtiEntrada(){
		return utiEntrada;
	}
	
	public String getUtiSaida(){
		return utiSaida;
	}
	
	public Modelo getModelo(){
		return model;
	}
	
	public String entradaSeq(String in){
		for(int i = 0; i<estado.getOut().size(); i++){
			Aresta k = estado.getOut().get(i);
			if(k.getIn().equals(in)){
				estado = k.getDestino();
				utiEntrada = k.getOut();
				utiSaida = k.getOut();
				return k.getOut();
			}
		}
		return null;
	}
	
	public String entradaSeq(char in){
		for(int i = 0; i<estado.getOut().size(); i++){
			Aresta k = estado.getOut().get(i);
			if(k.getIn().charAt(0) == in){
				estado = k.getDestino();
				utiEntrada = k.getIn();
				utiSaida = k.getOut();
				return k.getOut();
			}
		}
		return null;
	}
	
	public Aresta retornoEntradaSeq(String in){
		for(int i = 0; i<estado.getOut().size(); i++){
			Aresta k = estado.getOut().get(i);
			if(k.getIn().equals(in)){
				estado = k.getDestino();
				utiEntrada = k.getIn();
				utiSaida = k.getOut();
				return k;
			}
		}
		return null;
	}
	
	public Aresta retornoEntradaSeq(char in){
		for(int i = 0; i<estado.getOut().size(); i++){
			Aresta k = estado.getOut().get(i);
			if(k.getIn().charAt(0) == in){
				estado = k.getDestino();
				utiEntrada = k.getIn();
				utiSaida = k.getOut();
				return k;
			}
		}
		return null;
	}
	
	public void reset(){
		estado = model.inicial;
	}
}
