package MEF;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
	
	//estrutura com referência ao estado da MEF
	List<Aresta> in;
	List<Aresta> out;
	int label;
	String nome;

	public Vertice(int id) {
		super();
		in = new ArrayList<Aresta>();
		out = new ArrayList<Aresta>();
		this.label = id;
		nome = Integer.toString(id);
	}
	
	public Vertice(String nome) {
		super();
		in = new ArrayList<Aresta>();
		out = new ArrayList<Aresta>();
		label = nome.hashCode();
		this.nome = nome;
	}
	
	public String getNome(){

		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Aresta> getIn(){

		return in;
	}
	
	public void setIn(List<Aresta> in){
		this.in = in;
	}

	public List<Aresta> getOut(){
		return out;
	}
	
	public void setOut(List<Aresta> out){
		this.out = out;
	}
	
	public int getLabel(){

		return label;
	}
	
	public void setLabel(int label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + label;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertice other = (Vertice) obj;
		if (label != other.label)
			return false;
		return true;
	}
	
	

}
