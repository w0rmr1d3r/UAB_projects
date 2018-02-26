#!/bin/bash
if [ "$#" != 2 ]; then
echo "Necesitas 2 parametros becario !!!!"
exit
fi

if [ "$1" == "activar" ];
then		
	iptables -t nat -A POSTROUTING -s "$2" -o eth0 -j MASQUERADE
	echo "$2 Puede conectarse a Internet :D"	
elif [ "$1" == "desactivar" ]; then
	iptables -t nat -D POSTROUTING -s "$2" -o eth0 -j MASQUERADE
	echo "$2 Ya no puede conectarse a Internet D:"
else
	echo "comando invalido"
fi
