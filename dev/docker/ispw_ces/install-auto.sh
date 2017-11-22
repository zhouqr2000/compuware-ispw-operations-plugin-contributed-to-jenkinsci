#!/usr/bin/expect -f

set force_conservative 0  ;# set to 1 to force conservative mode even if
			  ;# script wasn't run conservatively originally
if {$force_conservative} {
	set send_slow {1 .1}
	proc send {ignore arg} {
		sleep .1
		exp_send -s -- $arg
	}
}

set timeout -1
spawn ./install.bin
match_max 100000

expect "PRESS <ENTER> TO CONTINUE: "
send "\r"

expect "   Reading): "
send "Y\r"

expect "   : "
send "Y\r"

expect "PRESS <ENTER> TO CONTINUE: "
send "\r"

expect "   /opt/Compuware/CES): "
send "\r"

expect "   only: "
send "1\r"

expect "PRESS <ENTER> TO CONTINUE: "
send "\r"

expect "Data folder: (Default: /opt/Compuware/CES/data): "
send "\r"

expect "Instance name: (Default: ): "
send "ces\r"

expect "User ID: (Default: root): "
send "\r"

expect "Do you wish to modify the default values? (Y/N) (Default: N): "
send "Y\r"

expect "Strobe communication port: (Default: 24354): "
send "\r"

expect "Web server port: (Default: 48226): "
send "48080\r"

expect "Compuware Enterprise Services shutdown port: (Default: 8465): "
send "\r"

expect "Internal messaging port: (Default: 17667): "
send "\r"

expect "Derby port: (Default: 1545): "
send "\r"

expect "Abend-AID communication port: (Default: 48301): "
send "\r"

expect "Profile folder: (Default: /opt/Compuware/CES/data/istrobe/profiles): "
send "\r"

expect "Quarantine folder: (Default: /opt/Compuware/CES/data/istrobe/quarantine): "
send "\r"

expect "Agent communication port (for mainframe communication): (Default: 48128): "
send "\r"

expect "PRESS <ENTER> TO CONTINUE: "
send "\r"

expect "PRESS <ENTER> TO EXIT THE INSTALLER: "
send "\r"

expect eof
