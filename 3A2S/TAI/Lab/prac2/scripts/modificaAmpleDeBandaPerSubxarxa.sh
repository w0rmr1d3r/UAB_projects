#!/bin/bash

if [ "$#" != 2 ]; then
echo "Necesitas 2 parametros becario !!!!"
exit
fi

tc qdisc add dev eth0 parent 1:"$1" handle 10: tbf rate "$2"kbit latency 50ms burst 1540

echo "Cambiado con exito"


