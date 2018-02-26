#!/bin/bash

# Activar Emmascarament para todos
iptables -t nat -A POSTROUTING -s 192.168.2.33 -o eth0 -j MASQUERADE
iptables -t nat -A POSTROUTING -s 192.168.2.65 -o eth0 -j MASQUERADE
iptables -t nat -A POSTROUTING -s 192.168.2.97 -o eth0 -j MASQUERADE
iptables -t nat -A POSTROUTING -s 192.168.2.129 -o eth0 -j MASQUERADE
iptables -t nat -A POSTROUTING -s 192.168.2.161 -o eth0 -j MASQUERADE
iptables -t nat -A POSTROUTING -s 192.168.2.193 -o eth0 -j MASQUERADE
iptables -t nat -A POSTROUTING -s 192.168.2.255 -o eth0 -j MASQUERADE
#Poner default al router

ip r a default via 192.168.0.1 dev eth0
