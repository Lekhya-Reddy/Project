@echo on
echo "arg 1 " %1
echo "arg 2 " %2
set PATH="C:\Work\Tools\WinScp\";%PATH%
winscp /script=dials-deploy-script  plaidapp:Pl@id2014@lxdenvd%1:/opt/plaid/DIALS/diagnostics /parameter %2