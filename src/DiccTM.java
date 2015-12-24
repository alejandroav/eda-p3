// DNI 45928098 ALARCON VILLENA, ALEJANDRO
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class DiccTM implements Diccionario {
	private int nlenguas;
	private ArrayList<Character> lenguas;
	private TreeMap<String, Vector<String>> dicc;
	
	public DiccTM() {
		nlenguas = -1;
		lenguas = new ArrayList<Character>();
		dicc = new TreeMap<String, Vector<String>>(String.CASE_INSENSITIVE_ORDER);
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
	// utilizando los metodos de treemap, insertamos de forma ordenada
	// la insercion tiene un coste logaritmico O(logn), ya que treemap se impelementa con un arbol rojo-negro
	public boolean inserta(Palabra2 p) {
		if (p!=null) {
			// comprobar que las lenguas coinciden
			char[] leng = p.getLenguas();
			
			if (leng.length!=lenguas.size())
				return false;

			for (int i = 0; i < leng.length; i++)
				if (leng[i]!=lenguas.get(i))
					return false;			
			
			// comprobar si ya existe. si existe, anadir cada traduccion distinta a la ya existente
			Vector<String> trads = dicc.get(p.getOrigen());
			boolean exito = false;			
			
			if (trads!=null)
				for (int i = 0; i < nlenguas; i++)
					if (p.getTraduccion(lenguas.get(i))!="" && !p.getTraduccion(lenguas.get(i)).equalsIgnoreCase(trads.get(i))) {
						trads.set(i, p.getTraduccion(lenguas.get(i)));
						exito = true;
					}
			
			// si se han modificado las traducciones, reinsertamos, sustituyendo el valor de la clave
			if (exito) {
				dicc.put(p.getOrigen(), trads);
				return true;
			}
			
			// si no existia previamente, se inserta
			dicc.put(p.getOrigen(),p.getTraducciones());
			return true;
		}

		return false;
	}

	@Override
	// treemap implementa el metodo remove para eliminar nodos
	// de nuevo, tiene un coste logaritmico O(logn), ya que treemap se implementa con un arbol rojo-negro
	public boolean borra(String s) {
		// para eliminar una palabra del diccionario, utilizamos el metodo remove de treemap
		if (s!=null)
			if (dicc.remove(s)!=null)
				return true;			
		return false;
	}

	@Override
	public int busca(String s) {
		// utilizamos get para comprobar que está insertada
		if (s!=null)
			if (dicc.get(s)!=null)
				return 1;
		return -1;
	}

	@Override
	public String traduce(String s, char l) {
		// devolver la traduccion de s a l
		if (s!=null) {
			Vector<String> trads = dicc.get(s);
			if (trads!=null && trads.get(lenguas.indexOf(l))!="")
				return trads.get(lenguas.indexOf(l));
		}
		return null;
	}

	@Override
	// recorremos el entrySet almacenado en el diccionario.
	// por cada par clave valor, generamos una palabra2, y luego invocamos escribeInfo
	// tiene un coste lineal O(n), pues recorremos todos los elementos
	// si en vez del entrySet obtuvieramos el keySet, habria que recorrer de nuevo el arbol entero
	// por cada clave para obtener su valor, y seria coste O(n log n)
	public void visualiza() {
		for(Map.Entry<String,Vector<String>> entry : dicc.entrySet()) {
			String key = entry.getKey();
			Vector<String> value = entry.getValue();
			char[] l = new char[nlenguas];
			
			for (int i = 0; i < nlenguas; i++) {
				l[i] = lenguas.get(i);
			}
			
			Palabra2 aux = new Palabra2(key,l);
			
			for (int i = 0; i < nlenguas; i++)
				aux.setTrad(value.get(i), lenguas.get(i));
			
			aux.escribeInfo();
		}
	}

	@Override
	public void visualiza(int j) {
		for(Map.Entry<String,Vector<String>> entry : dicc.entrySet()) {
			// como el metodo anterior, pero si j llega a 0 salimos
			if (j<=0)
				return;
			
			String key = entry.getKey();
			Vector<String> value = entry.getValue();
			char[] l = new char[nlenguas];
			
			for (int i = 0; i < nlenguas; i++) {
				l[i] = lenguas.get(i);
			}
			
			Palabra2 aux = new Palabra2(key,l);
			
			for (int i = 0; i < nlenguas; i++)
				aux.setTrad(value.get(i), lenguas.get(i));
			
			aux.escribeInfo();
			
			j--;
		}
	}

	@Override
	public void visualiza(int j, char l) {
		for(Map.Entry<String,Vector<String>> entry : dicc.entrySet()) {
			// como el metodo anterior, pero si j llega a 0 salimos y solo mostramos l
			if (j<=0)
				return;
			
			String key = entry.getKey();
			Vector<String> value = entry.getValue();
			String v = "";
			
			if (lenguas.indexOf(l) >= 0)
				v = value.get(lenguas.indexOf(l));
				
			System.out.println(key + ":" + v);
			
			j--;
		}
	}
}