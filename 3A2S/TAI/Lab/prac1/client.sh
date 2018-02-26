#!/bin/bash

#Añadimos una nueva entrada en la tabla de enrutamiento
ip r a default via 192.168.2.1 dev eth0

#Añadimos 2 entradas en la iptables para el marcage de paquetes tanto para el usuario1 como el usuario2
iptables -t mangle -I OUTPUT 1 -p tcp -s 192.168.2.33 -m owner --uid-owner 999 -j TOS --set-tos 2
iptables -t mangle -I OUTPUT 1 -p tcp -s 192.168.2.33 -m owner --uid-owner 998 -j TOS --set-tos 4

#Enviamos logs al gateway a la salida de paquetes
iptables -A OUTPUT -j LOG --log-uid --log-prefix "USER SENDS A MSG "
