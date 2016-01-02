package MEF;

import java.util.ArrayList;
import java.util.List;

public class Modelo {

	//estrutura com referência ao modelo, correspondendo a estados com transições.
	Vertice inicial;
	Vertice atual;
	List<Vertice> verticeState;
	List<Aresta> arestaTrans;
	List<String> in;
	List<String> out;

	public Modelo(){
		this.verticeState = new ArrayList<Vertice>();
		this.arestaTrans = new ArrayList<Aresta>();
		this.in = new ArrayList<String>();
		this.out = new ArrayList<String>();
		this.inicial = null;
		this.atual = null;
	}

	public Modelo(String n){
		this.verticeState = new ArrayList<Vertice>();
		this.arestaTrans = new ArrayList<Aresta>();
		this.in = new ArrayList<String>();
		this.out = new ArrayList<String>();
		this.inicial = null;
		this.atual = null;
	}

	public boolean addIn(String ent){
		if(!this.in.contains(ent)){
			in.add(ent);
			return true;
		}else{
			return false;
		}
	}

	public boolean addOut(String ent){
		if(!this.out.contains(ent)){
			out.add(ent);
			return true;
		}else{
			return false;
		}
	}

	public void addVerticeState(Vertice ent){
		if(!this.verticeState.contains(ent))
			verticeState.add(ent);
		if(inicial == null)
			this.inicial = ent;
	}

	public void reset(){
		setAtual(inicial);
	}

	public void addArestaTrans(Aresta ent){
		if(!this.arestaTrans.contains(ent)){
			addVerticeState(ent.origem);
			addVerticeState(ent.destino);
			addIn(ent.getIn());
			addOut(ent.getOut());
			getArestasTrans().add(ent);
		}
	}

	public List<String> getIn(){
		return this.in;
	}

	public List<String> getOut(){
		return this.out;
	}

	public Vertice getInicial() {
		return inicial;
	}

	public void setInicial(Vertice inicial) {
		this.inicial = inicial;
	}

	public Vertice getAtual() {
		return atual;
	}

	public void setAtual(Vertice atual) {
		this.atual = atual;
	}

	public Vertice getVerticeState(Vertice vertice){
		for (Vertice estados : verticeState){
			if(estados.equals(vertice)){
				return estados;
			}
		}
		return null;
	}
	
	public Vertice getVerticeState(String id){
		for (Vertice estados : verticeState){
			if(estados.getNome().equals(id) ){
				return estados;
			}
		}
		return null;
	}

	public List<Vertice> getVerticeStates(){
		return this.verticeState;
	}

	public List<Aresta> getArestasTrans(){
		return this.arestaTrans;
	}

	public Aresta getArestaTrans(Aresta aresta){
		for (Aresta trans : arestaTrans){
			if(trans.equals(aresta)){
				return trans;
			}
		}
		return null;
	}

	public String executar(String input){
		
		for (Aresta a : inicial.getOut()){
			if(a.getIn().equals(input)){
				setAtual(a.getDestino());
				return a.getOut();
			}
		}

		return null;
	}

	public String executar(char input){

		for (Aresta a : atual.getOut()){
			if(a.getIn().charAt(0) == input){
				setAtual(a.getDestino());
				return a.getOut();
			}
		}

		return null;
	}

	@Override
	public String toString() {
		String out = "";
		for (Aresta a : getArestasTrans())
			out += a.toString() + "\n";

		return out;
	}
}
