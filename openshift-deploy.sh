#!/bin/ksh
rsync -avz deployments/ ac10c62cfb3845b2b1ae839af6c6952d@quotebuilder-jdh.rhcloud.com:~/quotebuilder/repo/deployments
rhc-ctl-app -l jherson@ix.netcom.com -a quotebuilder -c restart

