lxc copy MouldLxd R01
lxc copy MouldLxd R02
lxc copy MouldLxd R03
lxc copy MouldLxd R11
lxc copy MouldLxd R12
lxc copy MouldLxd R13
lxc copy MouldLxd R1
lxc copy MouldLxd R21
lxc copy MouldLxd R22
lxc copy MouldLxd R23
lxc copy MouldLxd R2
lxc copy MouldLxd R31
lxc copy MouldLxd R32
lxc copy MouldLxd R33
lxc copy MouldLxd R3
lxc network create R01R11 ipv6.address=none ipv4.address=none;
lxc network attach R01R11 R01 eth1;
lxc network attach R01R11 R11 eth4;
lxc network create R01R13 ipv6.address=none ipv4.address=none;
lxc network attach R01R13 R01 eth2;
lxc network attach R01R13 R13 eth3;
lxc network create R01R1 ipv6.address=none ipv4.address=none;
lxc network attach R01R1 R01 eth3;
lxc network attach R01R1 R1 eth1;
lxc network create R01R2 ipv6.address=none ipv4.address=none;
lxc network attach R01R2 R01 eth4;
lxc network attach R01R2 R2 eth1;
lxc network create R02R2 ipv6.address=none ipv4.address=none;
lxc network attach R02R2 R02 eth1;
lxc network attach R02R2 R2 eth4;
lxc network create R02R3 ipv6.address=none ipv4.address=none;
lxc network attach R02R3 R02 eth2;
lxc network attach R02R3 R3 eth4;
lxc network create R02R22 ipv6.address=none ipv4.address=none;
lxc network attach R02R22 R02 eth3;
lxc network attach R02R22 R22 eth1;
lxc network create R02R23 ipv6.address=none ipv4.address=none;
lxc network attach R02R23 R02 eth4;
lxc network attach R02R23 R23 eth1;
lxc network create R03R3 ipv6.address=none ipv4.address=none;
lxc network attach R03R3 R03 eth1;
lxc network attach R03R3 R3 eth3;
lxc network create R03R1 ipv6.address=none ipv4.address=none;
lxc network attach R03R1 R03 eth2;
lxc network attach R03R1 R1 eth2;
lxc network create R03R33 ipv6.address=none ipv4.address=none;
lxc network attach R03R33 R03 eth3;
lxc network attach R03R33 R33 eth1;
lxc network create R03R32 ipv6.address=none ipv4.address=none;
lxc network attach R03R32 R03 eth3;
lxc network attach R03R32 R32 eth2;
lxc network create R03R32 ipv6.address=none ipv4.address=none;
lxc network attach R03R32 R03 eth4;
lxc network attach R03R32 R32 eth3;
lxc network create R11R12 ipv6.address=none ipv4.address=none;
lxc network attach R11R12 R11 eth2;
lxc network attach R11R12 R12 eth1;
lxc network create R11R13 ipv6.address=none ipv4.address=none;
lxc network attach R11R13 R11 eth3;
lxc network attach R11R13 R13 eth1;
lxc network create R12R13 ipv6.address=none ipv4.address=none;
lxc network attach R12R13 R12 eth2;
lxc network attach R12R13 R13 eth2;
lxc network create R1R3 ipv6.address=none ipv4.address=none;
lxc network attach R1R3 R1 eth3;
lxc network attach R1R3 R3 eth1;
lxc network create R1R2 ipv6.address=none ipv4.address=none;
lxc network attach R1R2 R1 eth4;
lxc network attach R1R2 R2 eth2;
lxc network create R21R23 ipv6.address=none ipv4.address=none;
lxc network attach R21R23 R21 eth2;
lxc network attach R21R23 R23 eth3;
lxc network create R21R22 ipv6.address=none ipv4.address=none;
lxc network attach R21R22 R21 eth3;
lxc network attach R21R22 R22 eth3;
lxc network create R22R23 ipv6.address=none ipv4.address=none;
lxc network attach R22R23 R22 eth2;
lxc network attach R22R23 R23 eth2;
lxc network create R2R3 ipv6.address=none ipv4.address=none;
lxc network attach R2R3 R2 eth3;
lxc network attach R2R3 R3 eth2;
lxc network create R31R33 ipv6.address=none ipv4.address=none;
lxc network attach R31R33 R31 eth2;
lxc network attach R31R33 R33 eth2;
lxc network create R31R32 ipv6.address=none ipv4.address=none;
lxc network attach R31R32 R31 eth3;
lxc network attach R31R32 R32 eth1;
lxc network create R33R03 ipv6.address=none ipv4.address=none;
lxc network attach R33R03 R33 eth3;
lxc network attach R33R03 R03 eth3;