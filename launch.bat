@echo off
@title Maple83
set CLASSPATH=.;dist\*
java -Xmx2048m -Dwzpath=wz\ net.server.Server
pause