#!/bin/bash


#Permitir hacer forward y net en el gateway
echo 1 > /proc/sys/net/ipv4/ip_forward
iptables -t nat -A POSTROUTING -s 192.168.2.0/24 -o eth1 -j MASQUERADE

#Hacemos drop del usuari2
iptables -A FORWARD -m tos --tos 4 -j DROP


#Logs
echo ':msg, contains, "USER SENDS A MSG " -/var/log/iptables.log
&~' > /etc/rsyslog.d/iptables.conf

#Reiniciamos rsyslog
/etc/init.d/rsyslog restart

#Comprobacion
tail -f /var/log/iptables.log

