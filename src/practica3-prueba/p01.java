/**
* @author Alicia Garrido Alenda
*
* Se crea un objeto de tipo DiccTM, se invoca su metodo leeDiccionario
* con un diccionario sin palabras repetidas.
* Se invoca su metodo visualiza.
*/
public class p01 {
  public static void main(String[] args){
    Diccionario diccio=new DiccTM();
    if(args.length>=1){
      diccio.leeDiccionario(args[0]);
      diccio.visualiza();
    }
    else
     System.out.println("Forma uso: java p01 p01.dic");
    
  }
}
