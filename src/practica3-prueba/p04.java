/**
* @author Alicia Garrido Alenda
*
* Se crea un objeto de tipo DiccABB, se invoca su metodo leeDiccionario
* con un diccionario sin palabras. Se invoca su metodo inserta con palabras que
* unas veces son nuevas, otras son repetidas, otras modifican una ya existente
* y otras no modifica el diccionario. Se invoca su metodo visualiza.
*/
import java.util.*;
public class p04 {
  /**
  * Crea una lista de objetos de tipo Palabra2, sin leerlos de fichero.
  * @return La lista creada, con un objeto en cada una de sus posiciones.
  */
  public static ArrayList<Palabra2> creaLista(){
    ArrayList<Palabra2> lista=new ArrayList<Palabra2>();
    Palabra2 tmp=null;
    char[] idiomas={'F','E','P'};
    tmp=new Palabra2("lay",idiomas);
    tmp.setTrad("dejar",'E');
    lista.add(tmp);
    tmp=new Palabra2("blouse",idiomas);
    tmp.setTrad("blusa",'P');
    tmp.setTrad("blusa",'E');
    tmp.setTrad("chemisier",'F');
    lista.add(tmp);
    tmp=new Palabra2("about",idiomas);
    tmp.setTrad("sobre",'P');
    tmp.setTrad("sobre",'E');
    tmp.setTrad("sur",'F');
    lista.add(tmp);
    tmp=new Palabra2("able",idiomas);
    tmp.setTrad("capaz",'P');
    tmp.setTrad("capaz",'E');
    tmp.setTrad("capable",'F');
    lista.add(tmp);
    tmp=new Palabra2("accept",idiomas);
    tmp.setTrad("aceitar",'P');
    tmp.setTrad("aceptar",'E');
    tmp.setTrad("accepter",'F');
    lista.add(tmp);
    tmp=new Palabra2("please",idiomas);
    tmp.setTrad("por favor",'P');
    lista.add(tmp);
    tmp=new Palabra2("normal",idiomas);
    tmp.setTrad("normal",'F');
    lista.add(tmp);
    tmp=new Palabra2("toy",idiomas);
    tmp.setTrad("jouet",'F');
    lista.add(tmp);
    tmp=new Palabra2("wrong",idiomas);
    tmp.setTrad("errado",'P');
    tmp.setTrad("equivocado",'E');
    tmp.setTrad("faux",'F');
    lista.add(tmp);
    char[] idiomas2={'E','P','F'};
    tmp=new Palabra2("wrong",idiomas2);
    tmp.setTrad("desacertado",'E');
    lista.add(tmp);
    tmp=new Palabra2("you",idiomas);
    tmp.setTrad("tu",'P');
    tmp.setTrad("tu",'E');
    tmp.setTrad("tu",'F');
    lista.add(tmp);
    tmp=new Palabra2("yet",idiomas);
    tmp.setTrad("ainda",'P');
    tmp.setTrad("ya",'E');
    tmp.setTrad("déjà",'F');
    lista.add(tmp);
    tmp=new Palabra2("your",idiomas);
    tmp.setTrad("vosso",'P');
    tmp.setTrad("tu",'E');
    tmp.setTrad("ton",'F');
    lista.add(tmp);
    tmp=new Palabra2("himself",idiomas);
    tmp.setTrad("ele mesmo",'P');
    tmp.setTrad("el mismo",'E');
    tmp.setTrad("lui-même",'F');
    lista.add(tmp);
    tmp=new Palabra2("December",idiomas);
    tmp.setTrad("dezembro",'P');
    tmp.setTrad("diciembre",'E');
    tmp.setTrad("décembre",'F');
    lista.add(tmp);

    tmp=new Palabra2("PLEASE",idiomas);
    tmp.setTrad("por favor",'E');
    lista.add(tmp);
    tmp=new Palabra2("toy",idiomas);
    tmp.setTrad("juguete",'E');
    lista.add(tmp);
    tmp=new Palabra2("yet",idiomas);
    tmp.setTrad("ainda",'P');
    tmp.setTrad("ya",'E');
    tmp.setTrad("déjà",'F');
    lista.add(tmp);
    return lista;
  }


  public static void main(String[] args){
    Diccionario diccio=new DiccABB();
    if(args.length>=1){
      diccio.leeDiccionario(args[0]);
      diccio.visualiza();
      ArrayList<Palabra2> lista=creaLista();
      if(lista!=null){
       int tam=lista.size();
       for(int i=0;i<tam;i++){
         Palabra2 elem=lista.get(i);
         System.out.println("inserta "+elem.getOrigen()+"? -> "+diccio.inserta(elem));
       }
      }
      diccio.visualiza();
    }
    else
     System.out.println("Forma uso: java p04 p04.dic");
    
  }
}
