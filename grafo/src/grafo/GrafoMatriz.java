package grafo;

import java.util.Stack;

/**
 * Grafo representado por matriz de adjacencia.
 * @author pedro
 *
 */
public class GrafoMatriz {
	
	private int vertices;  //Vertices do grafo, representados por um inteiro. Que e a quantidade.
	private int[][] arestas; // matriz que representa as arestas
	
	/**
	 * Construtor. Recebe um inteira que ira reprentar a quantidade de vertices do grafo e cria a matriz de adjacencia vazia baseada
	 * na quantidade de vertices.
	 * 
	 * @param vertices
	 */
	GrafoMatriz(int vertices){
		this.vertices = vertices;
		this.arestas = new int [vertices] [vertices];
	}
	
	/**
	 * Adiciona aresta a matriz de adjacencia. Colocando um 1 nos pontos (x,y) e (y,x).
	 * @param verticeX
	 * @param verticeY
	 */
	public void adicionarAresta(int verticeX, int verticeY) {
			arestas[verticeX][verticeY] = 1;
			arestas[verticeY][verticeX] = 1;
	}
	
	/**
	 * 
	 * Remove aresta a matriz de adjacencia. Colocando um 0 nos pontos (x,y) e (y,x).
	 * @param verticeX
	 * @param verticeY
	 */
	public void removerAresta(int verticeX, int verticeY) {
			arestas[verticeX][verticeY] = 0;
			arestas[verticeY][verticeX] = 0;
	}
	
	
	/**
	 * Verifica a diagonal superior da matriz de adjacencia, checando se tem arestas ou nao. So e necessario checar a diagonal
	 * superior pois o grafo nao e direcionado. Logo, se existe uma aresta de x pra y, existe de y pra x. 
	 * 
	 * @return true se for nulo ou false se nao for nulo. 
	 */
	public boolean isNulo() {
		 
		 for(int i = 0; i < arestas.length; i++ ) 
			 for(int x = i+1; x < arestas.length; x++) 
				 if(arestas[i][x] == 1) return false;
			 
			 
		 return true;
	}
	
	/**
	 * Verifica se todos os vertices tem o mesmo numero de arestas. Checando apenas a diagonal superior, pois se existe uma 
	 * aresta de x pra y, existe de y pra x. 
	 * 
	 * @return Retorna -1 se o grafo não for regular ou retorna o grau do grafo, caso este seja regular.
	 */
	public int isRegular() {
		
		int[] vertices = new int[arestas.length]; //Representas a quantidade de arestas que cada vertice tem.
		
		//Verifica a diagonal superior. Sempre olhando na linha x e olhando a coluna a partir de x + 1.
		 for(int x = 0; x < arestas.length; x++ ) 
			 for(int y = x+1; y < arestas.length; y++) 
				 if(arestas[x][y] == 1) {
					 vertices[x]++; // aresta de x pra y
					 vertices[y]++; // aresta de y pra x
				 }
					 
		
		 int quantBase = vertices[0];//Quantidade de arestas para comparacao.
		 
		 for (int i = 0; i < vertices.length; i++)
			  if(vertices[i] != quantBase) return -1; //Se existe um vertice com quantidade de arestas diferente da quantidade base
		 												 //, então existe um vertice com a quantidade de arestas diferente das demais.
		 												 //Logo, o grafo nao e regular.
		 
		 return quantBase;
		 
	}
	/**
	 * Verifica se o grafo e completo. Para um grafo ser completo, e preciso que exista exatamente uma aresta entre cada par 
	 * de vertices distintos. Logo, o grafo precisa ser regular de grau n - 1, onde n e igual ao numero de vertices.
	 * 
	 * @return true se o grafo for completo / false se o grafo nao for completo.
	 */
	public boolean isCompleto() {
		int n = isRegular(); 
		
		if(n == this.vertices-1) {
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
	 */
	public boolean isConexo() {
		Stack<Integer> caminho = new Stack<Integer>();
		caminho.push(0); //Vamos começar o caminho do vertice 0.
	
		return isConexo(0,caminho);
	}
	
	/**
	 * Funcao auxiliar de isConexo().
	 * 
	 * @param vert
	 * @param caminho
	 * @return true se o grafo for conexo ou false se o grafo nao for conexo.
	 */
	private boolean isConexo(int vert, Stack<Integer> caminho) {
		//Se eu percorrer todos os vertices do grafo, entao o grafo e conexo.	
		if(caminho.size() == this.vertices) return true;
		
		//Aqui e feita a busca por arestas a partir do vertice atual do caminho.
		for(int i = 0; i < arestas.length; i++ ) {
			//Se existir uma aresta entre o vertice atual e algum outro vertice e esse vertice ainda nao tiver sido percorrido,
			//entao a funcao e chamada para esse 
			if(this.arestas[vert][i] == 1 & caminho.contains(i) == false) {
				caminho.push(i);
				
				/*Se ja tiver sido percorridos todos os pontos, entao o looping tem que parar e retorna true. Mas se for
				 * retornado false em algum momento da pilha de execucao da recursao, entao nao necessariamente o grafo nao 
				 * e conexo. Pois pode ter chegado apenas em um ponto que irei denominar de "beco sem saida". Onde ha uma
				 * bifurcacao em algum vertice de execucao anterior. */
				if(isConexo(vert,caminho) == true) {
					return true;
				}
				
			}
		}
		
		
		return false;
		
	}
	
	public String toString() {
		String tupla = "Grafo = ( { " + vertices + " } , { ";
		Stack<String> arestasTupla = new Stack<String>(); //Para evitar que hajam repeticoes de arestas na tupla
		
		for(int x = 0; x < arestas.length; x++) {
			for(int y = 0; y < arestas.length; y++) {
				
				String arestayx = Integer.toString(y); arestayx.concat(Integer.toString(x));
				//Aqui eu checo se existe uma aresta de x para y e vejo tambem se eu ja nao chequei essa aresta de y para x.
				//Assim evito que tenham arestas repetidas no grafo nao direcionado.
				if(arestas[x][y] == 1 & arestasTupla.contains(arestayx) == false) {
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
		
		for(int x = 0; x < arestas.length; x++)
			for(int y = 0; y < arestas.length; y++)
				System.out.println("[" + x + "]" + "[" + y + "]" + " = " + arestas[x][y]);
	
	}
}
