#!/bin/bash
# Creacion de bandas
tc qdisc add dev eth0 root handle 1: prio bands 8
tc qdisc add dev eth1 root handle 1: prio bands 8

#Creacion de algoritmo de colas por banda
tc qdisc add dev eth0 parent 1:1 handle 10: tbf rate 10kbit latency 50ms burst 1540
tc qdisc add dev eth0 parent 1:2 handle 20: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth0 parent 1:3 handle 30: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth0 parent 1:4 handle 40: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth0 parent 1:5 handle 50: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth0 parent 1:6 handle 60: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth0 parent 1:7 handle 70: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth0 parent 1:8 handle 80: tbf rate 1kbit latency 50ms burst 1540

tc qdisc add dev eth1 parent 1:1 handle 10: tbf rate 10kbit latency 50ms burst 1540
tc qdisc add dev eth1 parent 1:2 handle 20: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth1 parent 1:3 handle 30: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth1 parent 1:4 handle 40: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth1 parent 1:5 handle 50: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth1 parent 1:6 handle 60: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth1 parent 1:7 handle 70: tbf rate 1kbit latency 50ms burst 1540
tc qdisc add dev eth1 parent 1:8 handle 80: tbf rate 1kbit latency 50ms burst 1540


# marcaje de paquetes según origen

ip route add 192.168.0.2 dev eth1 realm 1
ip route add 192.168.1.2 dev eth0 realm 1
ip route add 192.168.2.33 dev eth2 realm 2
ip route add 192.168.2.65 dev eth2 realm 3
ip route add 192.168.2.97 dev eth2 realm 4
ip route add 192.168.2.129 dev eth2 realm 5
ip route add 192.168.2.161 dev eth2 realm 6
ip route add 192.168.2.193 dev eth2 realm 7
ip route add 192.168.2.225 dev eth2 realm 8

# desviación de paquetes según realm

tc filter add dev eth0 parent 1:0 protocol ip prio 100 route from 1 flowid 1:1
tc filter add dev eth0 parent 1:0 protocol ip prio 100 route from 2 flowid 1:2
tc filter add dev eth0 parent 1:0 protocol ip prio 100 route from 3 flowid 1:3
tc filter add dev eth0 parent 1:0 protocol ip prio 100 route from 4 flowid 1:4
tc filter add dev eth0 parent 1:0 protocol ip prio 100 route from 5 flowid 1:5
tc filter add dev eth0 parent 1:0 protocol ip prio 100 route from 6 flowid 1:6
tc filter add dev eth0 parent 1:0 protocol ip prio 100 route from 7 flowid 1:7
tc filter add dev eth0 parent 1:0 protocol ip prio 100 route from 8 flowid 1:8

tc filter add dev eth1 parent 1:0 protocol ip prio 100 route from 1 flowid 1:1
tc filter add dev eth1 parent 1:0 protocol ip prio 100 route from 2 flowid 1:2
tc filter add dev eth1 parent 1:0 protocol ip prio 100 route from 3 flowid 1:3
tc filter add dev eth1 parent 1:0 protocol ip prio 100 route from 4 flowid 1:4
tc filter add dev eth1 parent 1:0 protocol ip prio 100 route from 5 flowid 1:5
tc filter add dev eth1 parent 1:0 protocol ip prio 100 route from 6 flowid 1:6
tc filter add dev eth1 parent 1:0 protocol ip prio 100 route from 7 flowid 1:7
tc filter add dev eth1 parent 1:0 protocol ip prio 100 route from 8 flowid 1:8
