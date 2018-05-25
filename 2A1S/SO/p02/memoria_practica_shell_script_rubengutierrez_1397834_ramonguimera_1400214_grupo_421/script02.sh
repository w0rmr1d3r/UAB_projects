# Funcion de menu, solo la usaremos ahi donde necesitemos
# mostrar el menu
function menu
{
cat << eof
1) Encontrar en el disco ficheros que contengan un patron
2) Tamaño de un directorio y su contenido
3) Exit
------------------------------------
Introduce (1/2/3):
eof
}

# Bucel del script, el cual se realiza hasta que se salga (opcion 3)
while true
do
	menu;
	read opcion
	case $opcion in
	1)
		# Apartado 1, encontrar en el disco ficheros con un patron
		# A parte, se muestra la cantidad de coincidencias (Extra 2)
		echo "Introduce el directorio raiz de la busqueda:"
		read raiz
		echo "Introduce el partron a buscar:"
		read patron
		busqueda=$(grep -r -l $patron $raiz)
		cantidad=$(grep -r -l $patron $raiz | wc -l)
		echo "Resultado:"
		echo $busqueda
		echo "Cantidad de coincidencias:"
		echo $cantidad
	;;
	2)
		# Apartado 2, calcular el tamaño de un directorio
		# Se muestra en diversas unidades (Extra 1)
		echo "Introduce un directorio para saber su tamaño:"
		read directorio
		tamano=$(du -h $directorio | tail -n 1)
		total=$(du $directorio | tail -n 1 | cut -f1)
		total_bytes=$((total*1024))
		total_megabytes=$((total/1024))
		total_gigabytes=$((total_megabytes/1024))
		echo "Tamaño total:"
		echo $tamano
		echo "Otras medidas:"
		echo "Bytes: " $total_bytes
		echo "Kilobytes: " $total
		echo "Megabytes: " $total_megabytes
		echo "Gigabytes: " $total_gigabytes
	;;
	3)
		exit
		# Con esta opcion salimos del bucle
	;;
	*)
		# Opcion por si la entrada no es correcta
		echo "Opcion no disponible"
		echo "Vuelve a intentarlo"
	;;
	esac
done
