export HBASE_HOME="/opt/homebrew/Cellar/hbase/2.5.5/libexec"
export HBASE_CONF_DIR=$HBASE_HOME/conf
export PATH=$HBASE_HOME/bin:$PATH
$HBASE_HOME/bin/start-hbase.sh