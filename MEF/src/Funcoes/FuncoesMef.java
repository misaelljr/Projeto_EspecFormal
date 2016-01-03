package Funcoes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import MEF.Aresta;
import MEF.ArvoreTeste;
import MEF.Modelo;
import MEF.ExecutaModelo;
import MEF.Sequencias;
import MEF.Vertice;

public class FuncoesMef {
	static FuncoesMef instance = new FuncoesMef();

	private FuncoesMef (){

	}

	public static FuncoesMef getInstance(){
		return instance;
	}

	//Carregar arquivo de modelo
	public Modelo CarregaMef(File arquivo) throws FileNotFoundException{
		Modelo model = new Modelo();

		Scanner input = null;

		input = new Scanner(arquivo); 

		String source = "";
		String target = "";
		String in = "";
		String out = "";

		while(input.hasNextLine()){
			String coords[] = input.nextLine().split("\\--|\\/|\\->");
			source = coords[0].trim();
			in = coords[1].trim();
			out = coords[2].trim();
			target = coords[3].trim();

			Vertice vsource = model.getVerticeState(source);

			if (vsource == null)
				vsource = new Vertice(source);
			Vertice vtarget = model.getVerticeState(target);

			if (vtarget == null)
				vtarget = new Vertice(target);


			Aresta tran = new Aresta(vsource,in,out,vtarget);

			model.addIn(in);
			model.addOut(out);
			model.addArestaTrans(tran);

			if(model.getInicial()==null)
				model.setInicial(vsource);
			if(model.getAtual()==null)
				model.setAtual(vsource);
		}

		input.close();



		return model;

	}

	//Carregar arquivo de sequência
	public Sequencias CarregaSeq(File arquivo) throws FileNotFoundException{
		Sequencias teste = new Sequencias();
		Scanner input2 = null;
		input2 = new Scanner(arquivo);


		while(input2.hasNext()){
			String line = input2.nextLine();
			teste.getSeq().add(line);
		}

		input2.close();

		return teste;

	}

	//Gerar arvore teste
	public ArvoreTeste geraArvore(Modelo modelo, Sequencias seq){
		ArvoreTeste arvore = new ArvoreTeste();
		ExecutaModelo modeloExec = new ExecutaModelo(modelo);
		int cont=0;
		Vertice estado = new Vertice(cont);

		arvore.addVerticeState(estado);
		arvore.setAtual(estado);
		arvore.setInicial(estado);

		ExecutaModelo arvoreExec = new ExecutaModelo(arvore);

		for (String tc : seq.getSeq()) {
			tc.charAt(0);
			int i=0;

			for (; i < tc.length() && isValid(arvoreExec, tc.charAt(i)); i++) {
				char in = tc.charAt(i);
				modeloExec.entradaSeq(in);
				arvoreExec.entradaSeq(in);
			}

			for (; i < tc.length(); i++) {
				String in = ""; 
				in +=tc.charAt(i);
				Vertice atual = arvoreExec.getEstado();

				Aresta trans = new Aresta(atual, in, modeloExec.entradaSeq(in), new Vertice(++cont));
				arvore.addArestaTrans(trans);
				arvoreExec.entradaSeq(in);
			}

			arvoreExec.reset();
			modeloExec.reset();
		}

		return arvore;
	}

	private boolean isValid(ExecutaModelo arvore, char in) {
		String sk = "";
		sk+=in;
		return isValid(arvore, sk);

	}

	private boolean isValid(ExecutaModelo arvore, String in) {
		for (Aresta a : arvore.getEstado().getOut()) {
			if (a.getIn().equals(in)){
				return true;
			}

		}
		return false;
	}

	//Montando grafo
	public UndirectedGraph<Vertice, DefaultEdge> geraGrafoDist(ArvoreTeste arvore){

		// Declaração e objeto da Classe Graph        
		UndirectedGraph<Vertice, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);


		//Adicionando vértices no Grafo g
		for (Vertice v : arvore.getVerticeStates()) {
			g.addVertex(v);
		}

		System.out.println();

		for (int i = 0; i < arvore.getVerticeStates().size()-1; i++) {
			for (int j = i+1; j < arvore.getVerticeStates().size(); j++) {
				Vertice vi = arvore.getVerticeStates().get(i);
				Vertice vj = arvore.getVerticeStates().get(j);

				if (isDistinguivel(vi, vj)){
					//Adicionando arestas referentes aos distinguiveis
					g.addEdge(vi, vj);
				}

			}

		}

		return g;

	}

	//Verificar distinguiveis
	private boolean isDistinguivel(Vertice vi, Vertice vj) {

		for (int i = 0; i < vi.getOut().size(); i++) {
			Aresta transicaoVi = vi.getOut().get(i);
			Aresta transicaoVj = null;
			for (int j = 0; j < vj.getOut().size(); j++) {
				if (vj.getOut().get(j).getIn().equals(transicaoVi.getIn())){
					transicaoVj = vj.getOut().get(j);
				}
			}
			if((transicaoVj!=null) && (transicaoVi.getOut() !=null)){
				if(transicaoVi.getOut().equals(transicaoVj.getOut())){
					return isDistinguivel(transicaoVi.getDestino(), transicaoVj.getDestino());
				}else{
					return true;
				}
			}
		}
		return false;
	}

	//Lema 2
	public void lema2 (Vertice k, Map<Integer, Set<Vertice>> labels,Set<Vertice> vert, UndirectedGraph<Vertice, DefaultEdge> j){

		List<Integer> conexoes = new ArrayList<Integer>();

		conexoes.addAll(labels.keySet());

		for (Integer chave : labels.keySet()) {
			for(Vertice v : labels.get(chave)){
				if (j.containsEdge(k, v)){
					conexoes.remove(chave);
					break;
				}
			}

			if(conexoes.size() == 1){
				labels.get(conexoes.get(0)).add(k);
				vert.add(k);
			}

		}		
	}

	//Lema 3
	public void lema3 (Vertice k, Map<Integer, Set<Vertice>> labels,Set<Vertice> vert, UndirectedGraph<Vertice, DefaultEdge> j){

		List<Aresta> seq = getSequenc(k, vert); 

		if(seq.size()>0){
			Vertice n = seq.get(0).getOrigem();
			Integer l;

			List<Vertice> label = new ArrayList<Vertice>();

			for (Integer i : labels.keySet()) {
				if(labels.get(i).contains(n)){
					l = i;
					label.addAll(labels.get(l));
					break;
				}		
			}

			List<String> inputSeq = extrairEntradas(seq);

			for ( Vertice vertice : label) {
				Vertice v = aplicarSequencia(inputSeq, vertice);
				if (v!=null){
					for (Integer rot : labels.keySet()) {
						if(labels.get(rot).contains(v)){
							labels.get(rot).add(k);
							vert.add(k);
							return;
						}

					}
				}

			}

		}
	}

	private Vertice aplicarSequencia(List<String> inputSeq, Vertice vertice) {
		Vertice v = vertice;
		for (String entrada : inputSeq) {
			for (int i = 0; i < vertice.getOut().size();i++) {
				vertice.getOut().get(i);
				if(v.getOut().get(i).getIn().equals(entrada)){
					v = v.getOut().get(i).getDestino();
					break;
				}else{
					return null;
				}
			}

		}
		return v;
	}

	private List<String> extrairEntradas(List<Aresta> seq) {
		List<String> input = new ArrayList<>();

		for (Aresta aresta : seq) {
			input.add(aresta.getIn());
		}

		return input;
	}

	//Buscar sequência e inverter ordem
	public List<Aresta> getSequenc(Vertice vert, Set<Vertice> sequec){
		List<Aresta> ares = new ArrayList<Aresta>();
		List<Aresta> temp = new ArrayList<Aresta>();

		Vertice s = vert;

		while(!sequec.contains(s) && s.getIn().size()>0){
			ares.add(s.getIn().get(0));
			s = s.getIn().get(0).getOrigem();
		}

		int i = ares.size()-1;

		while(i>=0){
			temp.add(ares.get(i));
			i--;
		}

		ares=temp;

		return ares;
	}

	public List<Aresta> getSequencTeorem1(Vertice vert){
		List<Aresta> ares = new ArrayList<Aresta>();
		List<Aresta> temp = new ArrayList<Aresta>();

		Vertice s = vert;

		while(s.getIn().size()>0){
			ares.add(s.getIn().get(0));
			s = s.getIn().get(0).getOrigem();
		}

		int i = ares.size()-1;

		while(i>=0){
			temp.add(ares.get(i));
			i--;
		}

		ares=temp;

		return ares;
	}

	public boolean validaTeorema1(Modelo arvore, Set<Vertice> estados, Modelo model){

		ExecutaModelo modeloExec = new ExecutaModelo(model);

		//MefUtils utils = MefUtils.getInstance();
		Aresta aresta = null;
		Set<Aresta> arestaTransi = new HashSet<Aresta>();
		Set<Vertice> vertice = new HashSet<Vertice>();	


		for (Vertice seq : estados){
			List<Aresta> seqIn = getSequencTeorem1(seq);

			for (Aresta a : seqIn) {

				aresta = modeloExec.retornoEntradaSeq(a.getIn());

				if (aresta!=null){
					vertice.add(aresta.getOrigem());
					vertice.add(aresta.getDestino());
					arestaTransi.add(aresta);
				}
			}
		}

		if(model.getArestasTrans().containsAll(arestaTransi) && model.getVerticeStates().containsAll(vertice)){
			return true;
		}else{
			return false;
		}

	}
}
