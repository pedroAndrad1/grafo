package grafo;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Grafo representado por lista de adjacencia.
 * @author pedro
 *
 */
public class GrafoLista {
	
	private ArrayList<Integer>[] arestas;  //Vertices do grafo e suas arestas.
	
	/**
	 * Construtor que recebe a quantidade de vertices do grafo como parametro.
	 * @param quantVertices
	 */
	GrafoLista(int quantVertices){
		arestas = new ArrayList[quantVertices];
		
		for(int i = 0; i < quantVertices; i++) {
			arestas[i] = new ArrayList<Integer>();
		}
			
	}
	/**
	 * Adiciona uma aresta ao grafo.
	 * @param x
	 * @param y
	 */
	public void adicionarAresta(int x, int y) {
		if(arestas[x].contains(y) == true) {
			System.out.println("A aresta ja existe");
			return;
		}
		//Adiciona nas duas listas pois o grafo e nao direcionado.
		arestas[x].add(y);
		arestas[y].add(x);
		
	}
	/**
	 * Remove uma aresta do grafo
	 * 
	 * @param x
	 * @param y
	 */
	public void removeAresta(int x, int y) {
		if(arestas[x].contains(y) == false) {
			System.out.println("A aresta nao existe");
			return;
		}
		//Remove nas duas listas pois o grafo e nao direcionado.
		arestas[x].remove(y);
		arestas[y].remove(x);
	}
	/**
	 * Verifica se o grafo e nulo, ou seja, se tem arestas.
	 * @return true se o grafo nao tiver arestas.
	 * 
	 */
	public boolean isNulo() {
		for(ArrayList<Integer> listaAdj: arestas )
			if(listaAdj.isEmpty() == false) //Se um das listas nao for vazia, entao o grafo tem arestas.
				return false;
		
		return true;
	}
	/**
	 * Verifica se o grafo e regular. Se for, retorna seu grau.
	 * 
	 * @return -1 caso nao seja regular e diferente de -1 caso seja.
	 */
	public int isRegular() {
		int quantBase = arestas[0].size();
		
		for(ArrayList<Integer> listaAdj: arestas)
			if(listaAdj.size() != quantBase) //Se alguma das listas tiver quantidade diferente, entao o grafo nao e regular.
				return -1;
		
		
		return quantBase;
	}
	/**
	 * Verifica se o grafo e completo. Para um grafo ser completo, e preciso que exista exatamente uma aresta entre cada par 
	 * de vertices distintos. Logo, o grafo precisa ser regular de grau n - 1, onde n e igual ao numero de vertices.
	 * 
	 * @return true se o grafo for completo / false se o grafo nao for completo.
	 * 
	 */
	public boolean isCompleto() {
		int n = isRegular();
		
		if(n == arestas.length - 1) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Checa se o grafo e conexo. Para o grafo ser conexo e preciso que, a partir de um vertice x do vertice, seja possivel tracar
	 * uma caminho por todos os demais vertices do grafo.
	 * 
	 * @return true se o grafo for conexo ou false se o grafo nao for conexo.
	 * 
	 */
	public boolean isConexo() {
		Stack<Integer> caminho = new Stack<Integer>();
		caminho.push(0); //Vamos começar o caminho do vertice 0.
	
		return isConexo(0,caminho);
	}
	
	private boolean isConexo(int vert, Stack<Integer> caminho) {
		//Se eu percorrer todos os vertices do grafo, entao o grafo e conexo.	
			if(caminho.size() == this.arestas.length) return true;
			
			//Aqui e feita a busca por arestas a partir do vertice atual do caminho.
			for(int i = 0; i < arestas.length; i++) {
					
				//Se existir uma aresta entre o vertice atual e algum outro vertice e esse vertice ainda nao tiver sido percorrido,
				//entao a funcao e chamada para esse 
				if(this.arestas[vert].contains(i) & caminho.contains(i) == false) {
					caminho.push(i);
					
					/*Se ja tiver sido percorridos todos os pontos, entao o looping tem que parar e retorna true. Mas se for
					 * retornado false em algum momento da pilha de execucao da recursao, entao nao necessariamente o grafo nao 
					 * e conexo. Pois pode ter chegado apenas em um ponto que irei denominar de "beco sem saida". Onde ha uma
					 * bifurcacao em algum vertice de execucao anterior. */
					if(isConexo(vert,caminho) == true) return true;
				
				}
			}
			
		return false;	
	}
	
	public String toString() {
		String tupla = "Grafo = ( { " + this.arestas.length + " } , { ";
		Stack<String> arestasTupla = new Stack<String>(); //Para evitar que hajam repeticoes de arestas na tupla
		
		for(int x = 0; x < arestas.length; x++) {
			for(int y = 0; y < arestas.length; y++) {
				
				String arestayx = Integer.toString(y); arestayx.concat(Integer.toString(x));
				//Aqui eu checo se existe uma aresta de x para y e vejo tambem se eu ja nao chequei essa aresta de y para x.
				//Assim evito que tenham arestas repetidas no grafo nao direcionado.
				if(arestas[x].contains(y) & arestasTupla.contains(arestayx) == false) {
					tupla.concat(" (" + x + "," + y +")");
					String arestaxy = Integer.toString(x); arestaxy.concat(Integer.toString(y));
					arestasTupla.push(arestaxy);
				}
					
			}
		}
		
		
		tupla.concat(" } )");
		return tupla;
	}
	/**
	 * Imprime a estrutura do grafo no console.
	 */
	public void estrutura() {
		System.out.println("ESTRUTURA");
		System.out.println("");
		
		for(int i = 0; i < arestas.length; i++) {
			System.out.println("vertice " + i +" : " );
			
			for(int a = 0; a < arestas[i].size(); a++)
				System.out.print(arestas[i].get(a) + " - ");
		}
	}
}
