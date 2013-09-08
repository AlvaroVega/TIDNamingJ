#!/bin/ksh

JVM="$JDK_HOME/bin/java $JAVA_RUN_OPTS"

$JVM es.tid.corba.TIDNamingAdminClient.AddSQLSpace $*
