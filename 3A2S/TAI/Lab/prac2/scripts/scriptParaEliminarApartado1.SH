#!/bin/bash

iptables -t nat -D POSTROUTING 1

iptables -t nat -A POSTROUTING -s 192.168.2.1/24 -o eth0 -j MASQUERADE
iptables -t nat -A POSTROUTING -s 192.168.2.1/24 -o eth1 -j MASQUERADE

ip route change default scope global nexthop via 192.168.0.1 dev eth0 weight 2 nexthop via 192.168.1.1 dev eth1 weight 1

