import java.util.ArrayList;
import java.util.List;

// DNI 45928098 ALARCON VILLENA, ALEJANDRO
public class SimLenguas {
	public static void main(String args[]) {
		if (args.length == 2) {
			// creamos y llenamos el diccionario
			DiccABB dicc = new DiccABB();
			dicc.leeDiccionario(args[0]);
			
			// obtener contenido
			ArrayList<Character> l = dicc.getLenguas();
			ArrayList<Palabra2> pa = new ArrayList<Palabra2>();
			pa = dicc.getPalabras(dicc.getRoot(), pa);
			
			// variables utiles
			List<Integer> distancia = new ArrayList<Integer>();
			int minima = 1000, aux = 0, total = 0;
			char p1 = 0, p2 = 0;
			
			switch (Integer.parseInt(args[1])) {
				case 1:
					// mostrar las dos lenguas mas parecidas y la distancia total entre esas dos [F P 23]
					for (Palabra2 p : pa) {						
						// recorro cada combinacion de traducciones
						for (int i = 0; i < l.size(); i++) {
							String s1 = p.getTraduccion(l.get(i));
							for (int j = 0; j < l.size(); j++) {
								String s2 = p.getTraduccion(l.get(j));
								if (!s1.equals(s2)) {
									aux = calculaDistancia(s1,s2);
									
									// si la nueva distancia es menor que la anterior, la guardo y guardo las lenguas
									if (aux < minima) {
										minima = aux;
										p1 = l.get(i); p2 = l.get(j);
									}
								}
							}
						}
					}
					System.out.println(p1 + " " + p2 + " " + minima);
				break;
					
				case 2:
					// mostrar la escribeinfo de la palabra cuya distancia a sus traducciones es la menor y la suma de esas distancias [another:otro:... 39]
					Palabra2 pal = null;
					for (Palabra2 p : pa) {
						aux = 0;
						// recorro cada combinacion de traducciones
						for (int i = 0; i < l.size(); i++) {
							String s1 = p.getTraduccion(l.get(i));
							for (int j = 0; j < l.size(); j++) {
								String s2 = p.getTraduccion(l.get(j));
								if (!s1.equals(s2)) {
									aux += calculaDistancia(s1,s2);
									
									// si la nueva distancia es menor que la anterior, la guardo y guardo las lenguas
									if (aux < minima) {
										minima = aux;
										pal = p;
									}
								}
							}
						}
					}
					pal.escribeInfo();
					System.out.println(minima);
					break;
					
				default: break;
			}
		}
	}
	
	// metodo que utiliza programacion dinamica para calcular la distancia entre dos palabras
	public static int calculaDistancia(String s1, String s2) {
		s1 = s1.toLowerCase(); s2 = s2.toLowerCase();
		// creamos la matriz de comparacion
		int[][] m = new int[s1.length()+1][s2.length()+1];

		for (int i = 0; i <= s1.length(); i++) {
			m[i][0] = i;	
		}
		for (int i = 0; i <= s2.length(); i++) {
			m[0][i] = i;
		}
		
		for(int i=1;i<=s1.length();i++) {
            for(int j=1;j<=s2.length();j++) { 
                m[i][j]= Math.min(m[i-1][j]+1,m[i][j-1]+1);
                m[i][j] = Math.min(m[i][j],m[i-1][j-1]+
                		((s1.charAt(i-1)==s2.charAt(j-1)?0:1)));
            }
        }
		
		return m[s1.length()][s2.length()];
	}
}
