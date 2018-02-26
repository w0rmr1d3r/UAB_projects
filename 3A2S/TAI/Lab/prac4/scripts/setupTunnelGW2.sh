#!/bin/bash

ip t a mitunel mode sit local 10.0.0.1 remote 10.0.3.2

ip link set mitunel up

ip -6 r a 2001::/64 dev mitunel
