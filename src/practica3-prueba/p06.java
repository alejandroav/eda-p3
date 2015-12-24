/**
* @author Alicia Garrido Alenda
*
* Se crea un objeto de tipo DiccABB, se invoca su metodo leeDiccionario
* con un diccionario sin palabras repetidas. 
* Se invoca su metodo visualiza indicandole distintas cantidades de palabras
* a mostrar.
*/
public class p06 {
  public static void main(String[] args){
    Diccionario diccio=new DiccABB();
    if(args.length>=1){
      diccio.leeDiccionario(args[0]);
      System.out.println("---- Muestra 4 ----");
      diccio.visualiza(4);
      System.out.println("---- Muestra 8 ----");
      diccio.visualiza(8);
      System.out.println("---- Muestra 16 ----");
      diccio.visualiza(16);
    }
    else
     System.out.println("Forma uso: java p06 p06.dic");
    
  }
}
