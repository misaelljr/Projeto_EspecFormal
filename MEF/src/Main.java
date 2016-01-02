import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultEdge;

import Funcoes.FuncoesMef;
import MEF.ArvoreTeste;
import MEF.Modelo;
import MEF.Sequencias;
import MEF.Vertice;


public class Main {

	public static void main(String[] args) {
		
		FuncoesMef mefutils = FuncoesMef.getInstance();

		try {
			//executar pela linha de comando
			String modelo 	= args	[0];
			String sequenciaTeste	= args	[1];

			Modelo model = mefutils.CarregaMef(new File(modelo));
			//System.out.println(model);
			Sequencias seq = mefutils.CarregaSeq(new File(sequenciaTeste));
			//System.out.println(seq);
			ArvoreTeste arvore = mefutils.geraArvore(model, seq);
			//System.out.println(arvore);
			UndirectedGraph<Vertice, DefaultEdge> grafo = mefutils.geraGrafoDist(arvore);
			//System.out.println(grafo);
			BronKerboschCliqueFinder<Vertice, DefaultEdge>  cliqueFdr = new BronKerboschCliqueFinder(grafo);
			Collection<Set<Vertice>> cliques = cliqueFdr.getBiggestMaximalCliques();

			Set<Vertice> test = new HashSet<Vertice>();
			Set<Vertice> k = new HashSet<Vertice>();
			k.addAll(arvore.getVerticeStates());
			Map<Integer,Set<Vertice>> labels = new HashMap<Integer,Set<Vertice>>();

			for (Set<Vertice> set : cliques) {
				
				//caso não gere clique, retorna not n-complete
				if(set.size() != model.getVerticeStates().size()) continue;

				//teste selecionando clique para teste clique= 1,6,9,0
				/*if(g!=5){
					g++;
					continue;
				}*/
				//System.out.println(set);

				test.clear();
				test.addAll(set);

				labels.clear();
				int label_int  = 0;

				//adicionar labels a cada clique
				for (Vertice vertices : test) {
					labels.put(label_int, new HashSet<Vertice>());
					labels.get(label_int).add(vertices);
					label_int++;
				}

				//Lemas
				for (int i = 0; i < arvore.getVerticeStates().size()-model.getVerticeStates().size(); i++) {
					for (Vertice vert : k) { 

						if(test.contains(k)){
							continue;
						}

						mefutils.lema2(vert, labels, test, grafo); //incluir sequência satisfeita pelo lema 2

						mefutils.lema3(vert, labels, test, grafo); //incluir sequência satisfeita pelo lema 3

						if(!test.contains(k)){
							if(mefutils.validaTeorema1(arvore, test, model)){
								System.out.println("the set is n-complete");
								System.exit(0);
							}
						}
					}
				}		
			}
			
			System.out.println("the set is not n-complete");
			System.exit(1);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}