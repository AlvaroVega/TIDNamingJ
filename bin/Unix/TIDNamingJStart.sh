#!/bin/ksh

JVM="$JDK_HOME/bin/java $JAVA_RUN_OPTS"

stderr() { echo $* 1>&2; }

# Comprueba que "es.tid.TIDorbj.iiop.orb_port" esta en los argumentos
if [ -z "$(echo $* | egrep "es.tid.TIDorbj.iiop.orb_port")" ]; then
	stderr "Debe indicar el argumento es.tid.TIDorbj.iiop.orb_port\n"
	exit 1
fi

# Comprueba que "es.tid.corba.TIDNaming.spaces_file" esta en los argumentos
if [ -z "$(echo $* | egrep "es.tid.corba.TIDNaming.spaces_file")" ]; then
	stderr "Debe indicar el argumento es.tid.corba.TIDNaming.spaces_file\n"
	exit 1
fi

$JVM es.tid.corba.TIDNaming.Server $*
