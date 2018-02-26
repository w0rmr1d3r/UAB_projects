!#/usr/sbin/setkey -f
 
flush;
spdflush;
 
# SA ESP
add 10.0.0.1 10.0.3.2 esp 15701 -E 3des-cbc "123456789012123456789012";
add 10.0.3.2 10.0.0.1  esp 15702 -E 3des-cbc "123456789012123456789012";
 
# SA AH
add 10.0.0.1 10.0.3.2 ah 15703 -A hmac-md5 "1234567890123456";
add 10.0.3.2 10.0.0.1 ah 15704 -A hmac-md5 "1234567890123456";
 
# SP
spdadd 10.0.3.2/24 10.0.0.1/24 any -P out ipsec 
esp/transport//require
ah/transport//require;
spdadd 10.0.0.1/24 10.0.3.2/24 any -P in ipsec 
esp/transport//require
ah/transport//require;

