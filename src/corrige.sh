#! /bin/bash

total=0
diferencias=""

function CorrigeResto(){
    nombre=$1
    error=0
    if test -e $nombre.tmp; then
     if test $nombre == "p03"; then
       d1=$(grep -i "america" $nombre.tmp |cut -d ">" -f 2|cut -d " " -f 2) 
       d2=$(grep -i "america" $nombre.txt |cut -d ">" -f 2|cut -d " " -f 2)
       let d3=$d2+1
       let d4=$d2-1
       d5=$(grep -i "useful" $nombre.tmp |cut -d ">" -f 2|cut -d " " -f 2)
       d6=$(grep -i "useful" $nombre.txt |cut -d ">" -f 2|cut -d " " -f 2)
       let d7=$d6+1
       let d8=$d6-1
       d9=$(grep -i "lazy" $nombre.tmp |cut -d ">" -f 2|cut -d " " -f 2)
       d10=$(grep -i "lazy" $nombre.txt |cut -d ">" -f 2|cut -d " " -f 2)
       let d11=$d10+1
       let d12=$d10-1
       d13=$(grep -i "doctor" $nombre.tmp |cut -d ">" -f 2|cut -d " " -f 2)
       d14=$(grep -i "doctor" $nombre.txt |cut -d ">" -f 2|cut -d " " -f 2)
       let d15=$d14+1
       let d16=$d14-1
       if test $d1 -eq $d2 || test $d3 -eq $d2 || test $d4 -eq $d2; then
        if test $d5 -eq $d6 || test $d7 -eq $d6 || test $d8 -eq $d6; then
          if test $d9 -eq $d10 || test $d11 -eq $d10 || test $d12 -eq $d10; then
            if test $d13 -eq $d14 || test $d15 -eq $d14 || test $d16 -eq $d14; then
              nota=1
            else
              nota=0
              error=-1
            fi
          else
            nota=0
            error=-1
          fi
        else
          nota=0
          error=-1
        fi
       else
        nota=0
        error=-1
       fi
     else
       diff -w $nombre.tmp $nombre.txt > d1.tmp
       nlin=$(cat d1.tmp|wc -l)
       if test $nlin -eq 0; then
         nota=1
       else
         nota=0
         diferencias="diferencias en fichero salida txt; ejecuta diff -w $nombre.txt $nombre.tmp"
       fi
     fi
    fi
    if test $error -eq -1; then
        diferencias="diferencias en fichero salida txt; ejecuta diff -w $nombre.txt $nombre.tmp [$d2 ($d1), $d6 ($d5), $d10 ($d9), $d14 ($d13)]"
    fi
    rm -rf d?.tmp  
    return $nota
}


compilador=javac

interprete=java


fuentes=$(ls *.java 2>/dev/null)

directorio=practica3-prueba
javas=$(ls *.java|wc -l)
numfuentes=0
nota=0
rm -rf *.tmp  $directorio/*.tmp $directorio/*.terr *.class $directorio/*.class $directorio/*.tmp.err

for fichero in $fuentes; do
 if test $fichero == Diccionario.java || test $fichero == Palabra2.java || \
 test $fichero == DiccTM.java || test $fichero == DiccABB.java || \
 test $fichero == SimLenguas.java; then 
  if test -f $fichero; then
  let numfuentes=numfuentes+1
  fi
 fi
done

if test $numfuentes -eq 5 -a $javas -eq 5; then
  continuar=true
else
  continuar=false
  echo "Error, ficheros fuente requeridos: Diccionario.java, Palabra2.java, DiccTM.java, DiccABB.java y SimLenguas.java; 0"
fi
grep "\.sort" *.java >ordenacion
ordena=$(cat ordenacion | wc -l)
rm -rf ordenacion
if test $ordena -ne 0; then
 echo "Se restara 0.5 de la nota de la practica por utilizar el metodo sort para ordenar un diccionario"
fi
grep "ArrayList<Palabra2>\|LinkedList<Palabra2>\|Vector<Palabra2>\|TreeMap|TreeSet" DiccABB.java >trampa.txt
tuampa=$(cat trampa.txt|wc -l)
rm -rf trampa.txt
if test $tuampa -ne 0; then
  continuar=false
  echo "Error: no se puede usar el api de java para implementar el árbol de DiccABB.java"
fi
if $continuar; then
 $compilador *.java 2> errores.compilacion 
 numlin=$(cat errores.compilacion | wc -l)
 if test $numlin -ne 0; then
  echo "Error de compilacion; 0"
  exit 1
 else
  rm -rf errores.compilacion
 fi
 # rm -rf errores.compilacion
 mv *.class $directorio
 cd  $directorio
 ficherosprueba=$(ls *.java)
 total=0
 for prueba in $ficherosprueba; do
  nombre=$(basename $prueba .java)
  $compilador $prueba 2> $nombre.terr 
  numlin=$(cat $nombre.terr | wc -l)
  if test $numlin -eq 0; then
    $interprete $nombre $nombre.dic  >$nombre.tmp 2>$nombre.tmp.err
    numlin=$(cat $nombre.tmp.err|wc -l)
    if test $numlin -eq 0; then
      CorrigeResto $nombre
      nota=$?
      if test $nota -eq 1; then
        if test $nombre == "p02" || test $nombre == "p03"; then
          echo "Prueba $nombre: ok"
          total=$(echo "$total+0.25"|bc)
        else 
          echo "Prueba $nombre: Ok"
          total=$(echo "$total+0.5"|bc)
        fi
      else
        echo "Prueba $nombre: $diferencias" 
      fi
    else
      echo "Prueba $nombre: Hay errores de ejecucion"
      cat $nombre.tmp.err
    fi
  else
   echo "Prueba $nombre: hay errores de compilacion" 
   cat $nombre.terr
  fi
 rm -rf d1.tmp $nombre.terr 
 done
 if test $ordena -ne 0; then
  total=$(echo "$total-0.5"|bc)
 fi
 echo "Nota: $total"
rm -rf ordenacion
 
fi

