#!/bin/ksh

DEPLOYMENT_DIR=$HOME/workspace/sfa-qb/deployments
TARGET_DIR=$HOME/workspace/sfa-qb/target

rm  $DEPLOYMENT_DIR/*
cp $TARGET_DIR/quotebuilder.war $DEPLOYMENT_DIR
touch $DEPLOYMENT_DIR/quotebuilder.war.dodeploy
#rsync -avz $DEPLOYMENT_DIR/ 4cb21b56186048d493a62901f91919fa@quotebuilder-sfa.rhcloud.com:~/quotebuilder/repo/deployments
rsync -avz $DEPLOYMENT_DIR/ 20913375ace145188de70761d6ad2290@quotebuilder-sfa.openshift.devlab.phx1.redhat.com:~/quotebuilder/repo/deployments
