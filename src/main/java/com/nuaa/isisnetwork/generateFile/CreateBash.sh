#========2.创建LXD容器=========
lxc copy LxcMould R01
lxc copy LxcMould R02
lxc copy LxcMould R03
lxc copy LxcMould R11
lxc copy LxcMould R12
lxc copy LxcMould R13
lxc copy LxcMould R1
lxc copy LxcMould R21
lxc copy LxcMould R22
lxc copy LxcMould R23
lxc copy LxcMould R2
lxc copy LxcMould R31
lxc copy LxcMould R32
lxc copy LxcMould R33
lxc copy LxcMould R3
#========3.创建并连接网桥=========
lxc network create R01R11 ipv6.address=none ipv4.address=none;
lxc network attach R01R11 R01 eth1;
lxc network create R01R13 ipv6.address=none ipv4.address=none;
lxc network attach R01R13 R01 eth2;
lxc network create R01R1 ipv6.address=none ipv4.address=none;
lxc network attach R01R1 R01 eth3;
lxc network create R01R2 ipv6.address=none ipv4.address=none;
lxc network attach R01R2 R01 eth4;
lxc network create R02R2 ipv6.address=none ipv4.address=none;
lxc network attach R02R2 R02 eth1;
lxc network create R02R3 ipv6.address=none ipv4.address=none;
lxc network attach R02R3 R02 eth2;
lxc network create R02R22 ipv6.address=none ipv4.address=none;
lxc network attach R02R22 R02 eth3;
lxc network create R02R23 ipv6.address=none ipv4.address=none;
lxc network attach R02R23 R02 eth4;
lxc network create R03R3 ipv6.address=none ipv4.address=none;
lxc network attach R03R3 R03 eth1;
lxc network create R03R1 ipv6.address=none ipv4.address=none;
lxc network attach R03R1 R03 eth2;
lxc network create R03R33 ipv6.address=none ipv4.address=none;
lxc network attach R03R33 R03 eth3;
lxc network create R03R32 ipv6.address=none ipv4.address=none;
lxc network attach R03R32 R03 eth4;
lxc network create R11R12 ipv6.address=none ipv4.address=none;
lxc network attach R11R12 R11 eth1;
lxc network create R11R13 ipv6.address=none ipv4.address=none;
lxc network attach R11R13 R11 eth2;
lxc network attach R01R11 R11 eth3;
lxc network attach R11R12 R12 eth1;
lxc network create R12R13 ipv6.address=none ipv4.address=none;
lxc network attach R12R13 R12 eth2;
lxc network attach R11R13 R13 eth1;
lxc network attach R12R13 R13 eth2;
lxc network attach R01R13 R13 eth3;
lxc network attach R01R1 R1 eth1;
lxc network attach R03R1 R1 eth2;
lxc network create R1R3 ipv6.address=none ipv4.address=none;
lxc network attach R1R3 R1 eth3;
lxc network create R1R2 ipv6.address=none ipv4.address=none;
lxc network attach R1R2 R1 eth4;
lxc network create R21R23 ipv6.address=none ipv4.address=none;
lxc network attach R21R23 R21 eth1;
lxc network create R21R22 ipv6.address=none ipv4.address=none;
lxc network attach R21R22 R21 eth2;
lxc network attach R02R22 R22 eth1;
lxc network create R22R23 ipv6.address=none ipv4.address=none;
lxc network attach R22R23 R22 eth2;
lxc network attach R21R22 R22 eth3;
lxc network attach R02R23 R23 eth1;
lxc network attach R22R23 R23 eth2;
lxc network attach R21R23 R23 eth3;
lxc network attach R01R2 R2 eth1;
lxc network attach R1R2 R2 eth2;
lxc network create R2R3 ipv6.address=none ipv4.address=none;
lxc network attach R2R3 R2 eth3;
lxc network attach R02R2 R2 eth4;
lxc network create R31R33 ipv6.address=none ipv4.address=none;
lxc network attach R31R33 R31 eth1;
lxc network create R31R32 ipv6.address=none ipv4.address=none;
lxc network attach R31R32 R31 eth2;
lxc network attach R31R32 R32 eth1;
lxc network create R32R33 ipv6.address=none ipv4.address=none;
lxc network attach R32R33 R32 eth2;
lxc network attach R03R32 R32 eth3;
lxc network attach R03R33 R33 eth1;
lxc network attach R31R33 R33 eth2;
lxc network attach R32R33 R33 eth3;
lxc network attach R1R3 R3 eth1;
lxc network attach R2R3 R3 eth2;
lxc network attach R03R3 R3 eth3;
lxc network attach R02R3 R3 eth4;
#========4.创建接口配置文件=========
mkdir -p /home/yzx/AutoNetwork/R01
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.2/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.18/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.21/30]
    eth4:
      dhcp4: false
      addresses: [192.168.15.53/30]" > /home/yzx/AutoNetwork/R01/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R02
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.70/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.66/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.73/30]
    eth4:
      dhcp4: false
      addresses: [192.168.15.77/30]" > /home/yzx/AutoNetwork/R02/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R03
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.30/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.94/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.33/30]
    eth4:
      dhcp4: false
      addresses: [192.168.15.49/30]" > /home/yzx/AutoNetwork/R03/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R11
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.5/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.9/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.1/30]" > /home/yzx/AutoNetwork/R11/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R12
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.6/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.13/30]" > /home/yzx/AutoNetwork/R12/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R13
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.10/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.14/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.17/30]" > /home/yzx/AutoNetwork/R13/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R1
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.22/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.93/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.25/30]
    eth4:
      dhcp4: false
      addresses: [192.168.15.57/30]" > /home/yzx/AutoNetwork/R1/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R21
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.86/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.90/30]" > /home/yzx/AutoNetwork/R21/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R22
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.74/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.82/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.89/30]" > /home/yzx/AutoNetwork/R22/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R23
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.78/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.81/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.85/30]" > /home/yzx/AutoNetwork/R23/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R2
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.54/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.58/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.62/30]
    eth4:
      dhcp4: false
      addresses: [192.168.15.69/30]" > /home/yzx/AutoNetwork/R2/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R31
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.38/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.41/30]" > /home/yzx/AutoNetwork/R31/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R32
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.42/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.46/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.50/30]" > /home/yzx/AutoNetwork/R32/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R33
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.34/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.37/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.45/30]" > /home/yzx/AutoNetwork/R33/10-lxc.yaml
mkdir -p /home/yzx/AutoNetwork/R3
echo "network:
  version: 2
  ethernets:
    eth0:
      dhcp4: true
      dhcp-identifier: mac
    eth1:
      dhcp4: false
      addresses: [192.168.15.26/30]
    eth2:
      dhcp4: false
      addresses: [192.168.15.61/30]
    eth3:
      dhcp4: false
      addresses: [192.168.15.29/30]
    eth4:
      dhcp4: false
      addresses: [192.168.15.65/30]" > /home/yzx/AutoNetwork/R3/10-lxc.yaml
#========5.创建网络配置文件=========
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R01
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 11
exit
!
interface eth2
 ip router isis 11
exit
!
interface eth3
 ip router isis 11
exit
!
interface eth4
 ip router isis 11
exit
!
router isis 11
 is-type level-2-only
 net 10.7307.1921.6801.5021.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R01/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R02
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 11
exit
!
interface eth2
 ip router isis 11
exit
!
interface eth3
 ip router isis 11
exit
!
interface eth4
 ip router isis 11
exit
!
router isis 11
 is-type level-2-only
 net 10.7105.1921.6801.5077.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R02/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R03
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 10
exit
!
interface eth2
 ip router isis 10
exit
!
interface eth3
 ip router isis 10
exit
!
interface eth4
 ip router isis 10
exit
!
router isis 10
 is-type level-2-only
 net 10.7106.1921.6801.5094.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R03/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R11
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 11
exit
!
interface eth2
 ip router isis 11
exit
!
interface eth3
 ip router isis 11
exit
!
router isis 11
 is-type level-2-only
 net 10.7307.1921.6801.2002.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R11/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R12
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 11
exit
!
interface eth2
 ip router isis 11
exit
!
router isis 11
 is-type level-2-only
 net 10.7307.1921.6801.5006.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R12/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R13
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 11
exit
!
interface eth2
 ip router isis 11
exit
!
interface eth3
 ip router isis 11
exit
!
router isis 11
 is-type level-2-only
 net 10.7307.1921.6801.5017.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R13/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R1
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 11
exit
!
interface eth2
 ip router isis 11
exit
!
interface eth3
 ip router isis 11
exit
!
interface eth4
 ip router isis 11
exit
!
router isis 11
 is-type level-2-only
 net 10.7307.1921.6801.5022.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R1/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R21
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 11
exit
!
interface eth2
 ip router isis 11
exit
!
router isis 11
 is-type level-2-only
 net 10.7105.1921.6801.3002.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R21/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R22
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 11
exit
!
interface eth2
 ip router isis 11
exit
!
interface eth3
 ip router isis 11
exit
!
router isis 11
 is-type level-2-only
 net 10.7105.1921.6801.5089.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R22/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R23
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 11
exit
!
interface eth2
 ip router isis 11
exit
!
interface eth3
 ip router isis 11
exit
!
router isis 11
 is-type level-2-only
 net 10.7105.1921.6801.5081.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R23/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R2
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 11
exit
!
interface eth2
 ip router isis 11
exit
!
interface eth3
 ip router isis 11
exit
!
interface eth4
 ip router isis 11
exit
!
router isis 11
 is-type level-2-only
 net 10.7105.1921.6801.5069.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R2/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R31
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 10
exit
!
interface eth2
 ip router isis 10
exit
!
interface eth3
 ip router isis 10
exit
!
router isis 10
 is-type level-2-only
 net 10.7106.1921.6801.5041.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R31/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R32
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 10
exit
!
interface eth2
 ip router isis 10
exit
!
interface eth3
 ip router isis 10
exit
!
router isis 10
 is-type level-2-only
 net 10.7106.1921.6801.1050.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R32/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R33
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 10
exit
!
interface eth2
 ip router isis 10
exit
!
interface eth3
 ip router isis 10
exit
!
router isis 10
 is-type level-2-only
 net 10.7106.1921.6801.5045.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R33/frr.conf
echo "frr version 8.5-dev-MyOwnFRRVersion
frr defaults traditional
hostname R3
log syslog informational
service integrated-vtysh-config
!
interface eth0
 mpls enable
exit
!
interface eth1
 ip router isis 10
exit
!
interface eth2
 ip router isis 10
exit
!
interface eth3
 ip router isis 10
exit
!
interface eth4
 ip router isis 10
exit
!
router isis 10
 is-type level-2-only
 net 10.7106.1921.6801.5065.00
exit
!
segment-routing
 traffic-eng
 exit
exit
!" > /home/yzx/AutoNetwork/R3/frr.conf
#========6.启动所有容器=========
lxc start R01
lxc start R02
lxc start R03
lxc start R11
lxc start R12
lxc start R13
lxc start R1
lxc start R21
lxc start R22
lxc start R23
lxc start R2
lxc start R31
lxc start R32
lxc start R33
lxc start R3
#========7.替换配置文件=========
lxc file push /home/yzx/AutoNetwork/R01/10-lxc.yaml R01/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R01/frr.conf R01/etc/frr/
lxc file push /home/yzx/AutoNetwork/R02/10-lxc.yaml R02/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R02/frr.conf R02/etc/frr/
lxc file push /home/yzx/AutoNetwork/R03/10-lxc.yaml R03/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R03/frr.conf R03/etc/frr/
lxc file push /home/yzx/AutoNetwork/R11/10-lxc.yaml R11/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R11/frr.conf R11/etc/frr/
lxc file push /home/yzx/AutoNetwork/R12/10-lxc.yaml R12/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R12/frr.conf R12/etc/frr/
lxc file push /home/yzx/AutoNetwork/R13/10-lxc.yaml R13/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R13/frr.conf R13/etc/frr/
lxc file push /home/yzx/AutoNetwork/R1/10-lxc.yaml R1/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R1/frr.conf R1/etc/frr/
lxc file push /home/yzx/AutoNetwork/R21/10-lxc.yaml R21/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R21/frr.conf R21/etc/frr/
lxc file push /home/yzx/AutoNetwork/R22/10-lxc.yaml R22/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R22/frr.conf R22/etc/frr/
lxc file push /home/yzx/AutoNetwork/R23/10-lxc.yaml R23/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R23/frr.conf R23/etc/frr/
lxc file push /home/yzx/AutoNetwork/R2/10-lxc.yaml R2/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R2/frr.conf R2/etc/frr/
lxc file push /home/yzx/AutoNetwork/R31/10-lxc.yaml R31/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R31/frr.conf R31/etc/frr/
lxc file push /home/yzx/AutoNetwork/R32/10-lxc.yaml R32/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R32/frr.conf R32/etc/frr/
lxc file push /home/yzx/AutoNetwork/R33/10-lxc.yaml R33/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R33/frr.conf R33/etc/frr/
lxc file push /home/yzx/AutoNetwork/R3/10-lxc.yaml R3/etc/netplan/
lxc file push /home/yzx/AutoNetwork/R3/frr.conf R3/etc/frr/
#========8.重启网络=========
lxc exec R01 netplan apply
lxc exec R02 netplan apply
lxc exec R03 netplan apply
lxc exec R11 netplan apply
lxc exec R12 netplan apply
lxc exec R13 netplan apply
lxc exec R1 netplan apply
lxc exec R21 netplan apply
lxc exec R22 netplan apply
lxc exec R23 netplan apply
lxc exec R2 netplan apply
lxc exec R31 netplan apply
lxc exec R32 netplan apply
lxc exec R33 netplan apply
lxc exec R3 netplan apply
#========9.重启Frr=========
lxc exec R01 systemctl restart frr
lxc exec R02 systemctl restart frr
lxc exec R03 systemctl restart frr
lxc exec R11 systemctl restart frr
lxc exec R12 systemctl restart frr
lxc exec R13 systemctl restart frr
lxc exec R1 systemctl restart frr
lxc exec R21 systemctl restart frr
lxc exec R22 systemctl restart frr
lxc exec R23 systemctl restart frr
lxc exec R2 systemctl restart frr
lxc exec R31 systemctl restart frr
lxc exec R32 systemctl restart frr
lxc exec R33 systemctl restart frr
lxc exec R3 systemctl restart frr
