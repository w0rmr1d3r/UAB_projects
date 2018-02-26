#!/bin/bash

ip route change 192.168.0.2 dev eth1 realm 1
ip route change 192.168.1.2 dev eth0 realm 1
ip route change 192.168.2.33 dev eth2 realm 1
ip route change 192.168.2.65 dev eth2 realm 1
ip route change 192.168.2.97 dev eth2 realm 1
ip route change 192.168.2.129 dev eth2 realm 1
ip route change 192.168.2.161 dev eth2 realm 1
ip route change 192.168.2.193 dev eth2 realm 1
ip route change 192.168.2.225 dev eth2 realm 1

