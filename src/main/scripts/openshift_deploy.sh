#!/bin/ksh

DEPLOYMENT_DIR=$HOME/workspace/sfa-qb/deployments
TARGET_DIR=$HOME/workspace/sfa-qb/target

rm  $DEPLOYMENT_DIR/*
cp $TARGET_DIR/quotebuilder.war $DEPLOYMENT_DIR
touch $DEPLOYMENT_DIR/quotebuilder.war.dodeploy
rsync -avz $DEPLOYMENT_DIR/ 4cb21b56186048d493a62901f91919fa@quotebuilder-jdh.rhcloud.com:~/quotebuilder/repo/deployments
rhc-ctl-app -l jherson@ix.netcom.com -a quotebuilder -c restart
