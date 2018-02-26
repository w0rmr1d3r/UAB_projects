#!/bin/bash

if [ "$#" != 2 ]; then
	echo "Necesitas pasar 2 parametros weight eth0 y weight eth1"
	exit
fi

ip route change default scope global nexthop via 192.168.0.1 dev eth0 weight "$1" nexthop via 192.168.1.1 dev eth1 weight "$2"

echo "W1 cambiado por $1 y W2 cambiado por $2"


