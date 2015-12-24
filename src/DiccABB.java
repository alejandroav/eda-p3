// DNI 45928098 ALARCON VILLENA, ALEJANDRO
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class DiccABB implements Diccionario {
	private int nlenguas;
	private ArrayList<Character> lenguas;
	private NodoABB dicc;

	private class NodoABB {
		private Palabra2 pal;
		private NodoABB hiz;
		private NodoABB hde;

		public NodoABB(Palabra2 p) {
			setPalabra2(p);
			hiz = hde = null;
		}

		public void cambiaHijoIz(NodoABB n) {
			hiz = n;
		}

		public void cambiaHijoDe(NodoABB n) {
			hde = n;
		}

		public void setPalabra2(Palabra2 p) {
			pal = p;
		}

		public NodoABB getHijoIz() {
			return hiz;
		}

		public NodoABB getHijoDe() {
			return hde;
		}

		public Palabra2 getPalabra2() {
			return pal;
		}
	}

	public DiccABB() {
		nlenguas = -1;
		lenguas = new ArrayList<Character>();
		dicc = null;
	}

	@Override
	public void leeDiccionario(String f) {
		if (f!=null) {
			FileReader fichero = null;
			BufferedReader lectura = null;
			try {
				// flujos
				fichero = new FileReader(f);
				lectura = new BufferedReader(fichero);
				String linea = lectura.readLine();
				nlenguas = Integer.parseInt(linea);
	
				// obtengo las partes
				linea = lectura.readLine();
				String partes[] = null;
				partes = linea.split("[ ]");
				
				for (int i = 0; i < nlenguas; i++) {
					lenguas.add(partes[i].charAt(0));
				}
				
				Palabra2 nueva = null;
				
				linea = lectura.readLine();
	
				// recorro las lineas, creando las palabras y sus traducciones e insertando
				while (linea != null) {
					partes = linea.split("[ ]*\\*[ ]*");
					
					char[] l = new char[nlenguas];
					for (int i = 0; i < nlenguas; i++) {
						l[i] = lenguas.get(i);
					}
					
					nueva = new Palabra2(partes[0],l);
					
					for (int i = 1; i < partes.length; i++) {
						nueva.setTrad(partes[i],l[i-1]);
					}
					
					inserta(nueva);
					linea = lectura.readLine();
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					if (fichero != null)
						fichero.close();
					if (lectura != null)
						lectura.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}		
		}
	}

	@Override
	// la insercion tiene un coste logaritmico O(log(n))
	public boolean inserta(Palabra2 p) {
		if (p!=null) {
			// comprobar que las lenguas coinciden
			char[] leng = p.getLenguas();
			
			if (leng.length!=lenguas.size())
				return false;

			for (int i = 0; i < leng.length; i++)
				if (leng[i]!=lenguas.get(i))
					return false;		
			
			// si no existia previamente, se inserta
			// dicc vacio?
			if (dicc==null) {
				dicc = new NodoABB(p);
				return true;
			}
			
			// recorremos en busca de su lugar
			NodoABB iterador = dicc;
			while (iterador!=null) {
				if (p.getOrigen().compareToIgnoreCase(iterador.getPalabra2().getOrigen())<0) { // es menor
					if (iterador.getHijoIz()!=null)
						iterador = iterador.getHijoIz();
					else {
						iterador.cambiaHijoIz(new NodoABB(p));
						return true;
					}
				}
				if (p.getOrigen().compareToIgnoreCase(iterador.getPalabra2().getOrigen())>0) { // es mayor
					if (iterador.getHijoDe()!=null)
						iterador = iterador.getHijoDe();
					else {
						iterador.cambiaHijoDe(new NodoABB(p));
						return true;
					}
				}
				if (p.getOrigen().compareToIgnoreCase(iterador.getPalabra2().getOrigen())==0) { // es repetida, las mezclamos
					if (mezclarPalabras(p,iterador))
						return true;
					else
						return false;
				}
			}
		} return false;
	}
	
	@Override
	public int busca(String s) {
		int n = 0;
		if (s!=null) {
			NodoABB iterador = dicc;
			while (iterador!=null) {				
				n++;
				if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen())==0)
					return n;
				else if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen())<0)
					iterador = iterador.getHijoIz();
				else if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen())>0)
					iterador = iterador.getHijoDe();
			}
		}
		return n*(-1);
	}

	@Override
	public String traduce(String s, char l) {
		if (s!=null) {
			NodoABB iterador = dicc;
			while (iterador!=null) {		
				
				if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen())==0)
					return iterador.getPalabra2().getTraduccion(l);
				
				if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen()) < 0)
					iterador = iterador.getHijoIz();
				if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen()) > 0)
					iterador = iterador.getHijoDe();
			}
		}
		return null;
	}
	
	// implemento este metodo para poder realizar el recorrido completo
	public void verRecursivo(NodoABB root){
		if(root!=null) {			
			verRecursivo(root.getHijoIz());
			root.getPalabra2().escribeInfo();
			verRecursivo(root.getHijoDe());
		}
	}
	
	// implemento este metodo para poder realizar el recorrido completo
	public int verRecursivo(NodoABB root, int lim, char l) {
		if(root!=null) {		
			lim = verRecursivo(root.getHijoIz(),lim,l);

			if (lim > 0)
				if (l=='0')
					root.getPalabra2().escribeInfo();
				else
					System.out.println(root.getPalabra2().getOrigen() + ":" + root.getPalabra2().getTraduccion(l));	
			
			lim--;

			lim = verRecursivo(root.getHijoDe(),lim,l);
		}
		return lim;
	}

	@Override
	// realizamos un recorrido recursivo inorden y mostramos el contenido
	// tiene un coste lineal, pues recorremos todos los nodos
	public void visualiza() {
		verRecursivo(dicc);
	}

	@Override
	public void visualiza(int j) {
		verRecursivo(dicc,j,'0');
	}

	@Override
	public void visualiza(int j, char l) {
		verRecursivo(dicc,j,l);
	}

	@Override
	// recorro el arbol en busca de la palabra
	// si es una hoja, se elimina
	// si solo tiene un hijo, se susittuye por el correspondiente
	// si tiene dos hijos, se realiza la rotacion tomando el mayor por la izquierda
	// tiene un coste logaritmico, pues solo se recorre una de las dos opciones
	// el cambio de nodos es constante
	public boolean borra(String s) {
		NodoABB origen = dicc;
		NodoABB actual = dicc;
		boolean hIzquierdo = false;
		
		// buscamos el nodo a borrar
		while(!actual.getPalabra2().getOrigen().equalsIgnoreCase(s)) {
			origen = actual;
			if(actual.getPalabra2().getOrigen().compareToIgnoreCase(s) > 0) {
				hIzquierdo = true;
				actual = actual.getHijoIz();
			}
			
			else {
				hIzquierdo = false;
				actual = actual.getHijoDe();
			}
			
			if(actual ==null) {
				return false;
			}
		}
		
		// nodo encontrado
		// es una hoja
		if(actual.getHijoIz()==null && actual.getHijoDe()==null) {
			if(actual==origen) {
				origen = null;
			}
			if(hIzquierdo ==true) {
				origen.cambiaHijoIz(null);
			} 
			
			else {
				origen.cambiaHijoDe(null);
			}
		}
		// tiene un hijo derecho
		else if(actual.getHijoDe()==null) {
			if(actual==origen){
				origen = actual.getHijoIz();
			}
			
			else if(hIzquierdo){
				origen.cambiaHijoIz(actual.getHijoIz());
			}
			
			else{
				origen.cambiaHijoDe(actual.getHijoIz());
			}
		}
		
		// tiene un hijo izquierdo
		else if(actual.getHijoIz()==null){
			if(actual==origen){
				origen = actual.getHijoDe();
			}
			
			else if(hIzquierdo){
				origen.cambiaHijoIz(actual.getHijoDe());
			}
			
			else{
				origen.cambiaHijoDe(actual.getHijoDe());
			}
		}
		
		// tiene dos hijos, cogemos el
		// elemento mayor en arbol izquierdo
		else if(actual.getHijoIz()!=null && actual.getHijoDe()!=null){
			
		}		
		return true;
	}

	public void preordenABB() {
		preordenRecursivo(dicc);
	}
	
	// metodo para recorrer el preorden recursivamente
	public void preordenRecursivo(NodoABB root) {
		root.getPalabra2().escribeInfo();
		preordenRecursivo(root.getHijoIz());
		preordenRecursivo(root.getHijoDe());
	}

	public void nivelesABB() {
		NodoABB temp;
	    Queue<NodoABB> queue = new LinkedList<>();
	    queue.add(dicc);
	    while (!queue.isEmpty())
	    {
	        temp = queue.poll();
	        temp.getPalabra2().escribeInfo();
	        if (temp.getHijoIz() != null)
	        {
	            queue.add(temp.getHijoIz());
	        }
	        if (temp.getHijoDe() != null)
	        {
	            queue.add(temp.getHijoDe());
	        }
	    }
	}

	// recorro el arbol en busca de s
	// si vooy hacia la derecha, el anterior es el nodo actual
	// si voy hacia la izquierda, es el que ya figurara como anterior
	// si una vez encontrado s, tiene hijo izquierdo, ese es su anterior
	// coste logaritmico, pues de n nodos se recorre log2(n)
	public String anterior(String s) {
		if(s!=null) {
			NodoABB iterador = dicc;
			NodoABB aux = dicc;
			while (iterador!=null) {
				
				if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen())==0) {
					if (iterador.getHijoIz()!=null)
						aux = iterador.getHijoIz();
					return aux.getPalabra2().getOrigen();
				}
				if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen())<0) {
					iterador = iterador.getHijoIz();
				}
				if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen())>0) {
					aux = iterador;
					iterador = iterador.getHijoDe();
				}
			}
		}
		return null;
	}

	public void camino(String s) {
		if (s!=null) {
			NodoABB iterador = dicc;
			String v = ""; boolean f = false;
			while (iterador!=null) {				
				v+= iterador.getPalabra2().getOrigen() + " - ";
					
				if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen())<0)
					iterador = iterador.getHijoIz();
				if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen())>0)
					iterador = iterador.getHijoDe();
				
				if (s.compareToIgnoreCase(iterador.getPalabra2().getOrigen())==0) {
					iterador = null;
					f = true;
				}
			}
			
			if (!f)
				v = "NO HAY CAMINO";
			
			System.out.println(v);
		}
	}

	public boolean equilibrado() {
		return false;
	}
	
	// implemento este metodo para realizar mas facilmente el solape de traducciones al insertar la misma palabra
	public boolean mezclarPalabras(Palabra2 p, NodoABB nodo) {
		boolean exito = false;
		if (p!=null && nodo!=null) {
			for (int i = 0; i < nlenguas; i++) {
				String trad = p.getTraduccion(lenguas.get(i));
				if (trad!=null && !trad.equalsIgnoreCase("") &&
						!trad.equalsIgnoreCase(nodo.getPalabra2().getTraduccion(lenguas.get(i)))) {
					nodo.getPalabra2().setTrad(p.getTraduccion(lenguas.get(i)), lenguas.get(i));
					exito = true;
				}
			}
		}		
		return exito;
	}
	
	// creo este metodo parar recuperar las lenguas del diccionario en simlenguas
	public ArrayList<Character> getLenguas() {
		return lenguas;
	}
	
	// creo este metodo para recuperar las palabras contenidas en simlenguas
	public ArrayList<Palabra2> getPalabras(NodoABB root,ArrayList<Palabra2> v) {
		if (root==null)
			return null;
		
		getPalabras(root.getHijoIz(),v);
		v.add(root.getPalabra2());
		getPalabras(root.getHijoDe(),v);
		
		return v;
	}
	
	// lo implemento para recuperar la raiz
	public NodoABB getRoot() {
		return dicc;
	}
}