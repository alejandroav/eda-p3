/**
* @author Alicia Garrido Alenda
*
* Se crea un objeto de tipo DiccABB, se invoca su metodo leeDiccionario
* con un diccionario sin palabras repetidas.
* Se invoca su metodo busca con palabras que unas veces si que estan y otras no.
*/
public class p03 {
  public static void main(String[] args){
    Diccionario diccio=new DiccABB();
    if(args.length>=1){
      diccio.leeDiccionario(args[0]);
      System.out.println("encuentra america? -> "+diccio.busca("america"));
      System.out.println("encuentra uSeFuL? -> "+diccio.busca("uSeFuL"));
      System.out.println("encuentra LAZY? -> "+diccio.busca("LAZY"));
      System.out.println("encuentra doctor? -> "+diccio.busca("doctor"));
      
    }
    else
     System.out.println("Forma uso: java p03 p03.dic");
    
  }
}
