// DNI 45928098 ALARCON VILLENA, ALEJANDRO
public interface Diccionario {
	public void leeDiccionario(String f);
	public boolean inserta(Palabra2 p);
	public boolean borra(String s);
	public int busca(String s);
	public String traduce(String s, char l);
	public void visualiza();
	public void visualiza(int j);
	public void visualiza(int j, char l);
}
