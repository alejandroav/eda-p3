/**
* @author Alicia Garrido Alenda
*
* Se crea un objeto de tipo DiccTM, se invoca su metodo leeDiccionario
* con un diccionario sin palabras repetidas. Se invoca su
* metodo borra unas veces con cadenas que estan en el diccionario y se 
* borran y otras con cadenas que no estan en el diccionario.
* Se invoca su metodo visualiza indicandole distintas cantidades de palabras
* a mostrar y su traduccion a distintos idiomas.
*/
public class p05 {
  public static void main(String[] args){
    Diccionario diccio=new DiccTM();
    if(args.length>=1){
      diccio.leeDiccionario(args[0]);
      System.out.println("borra america? -> "+diccio.borra("america"));
      diccio.visualiza(14,'E');
      System.out.println("borra uSeFuL? -> "+diccio.borra("uSeFuL"));
      diccio.visualiza(13,'F');
      System.out.println("borra LAZY? -> "+diccio.borra("LAZY"));
      diccio.visualiza(13,'P');
      System.out.println("borra doctor? -> "+diccio.borra("doctor"));
      diccio.visualiza(13,'I');
    }
    else
     System.out.println("Forma uso: java p05 p05.dic");
    
  }
}
