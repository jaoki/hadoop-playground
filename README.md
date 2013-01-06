PREREQUISITES
---------------------
- JDK 1.7
- maven 3


Hadoop Setup
--------------------------

wget http://www.linuxtourist.com/apache/hadoop/common/hadoop-1.0.4/hadoop-1.0.4-bin.tar.gz

gzip -d hadoop-1.0.4-bin.tar.gz

tar -xvf hadoop-1.0.4-bin.tar.gz


ssh localhost should work

	ssh-agent -t rsa

	cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys


Set JAVA_HOME in conf/hadoop-env.sh

Modify conf/core-site.xml

      <!-- OOZIE -->
      <property>
        <name>hadoop.proxyuser.jaoki.hosts</name>
        <value>localhost</value>
      </property>
       <property>
        <name>hadoop.proxyuser.jaoki.groups</name>
        <value>users</value>
      </property>

      <property>
        <name>fs.default.name</name>
        <value>hdfs://localhost:8020</value>
      </property>

vi conf/mapred-site.xml

      <property>
        <name>mapred.job.tracker</name>
        <value>localhost:8021</value>
      </property>

./bin/hadoop namenode -format

./bin/start-all.sh

Namenode
http://localhost:50070 

MapReduce
http://localhost:50030


ExtJS Setup
--------------------------
wget http://extjs.com/deploy/ext-2.2.zip


Oozie Setup
--------------------------

wget http://mirror.olnevhost.net/pub/apache/oozie/3.3.0/oozie-3.3.0.tar.gz

./bin/mkdistro.sh

tar -xvf distro/target/oozie-3.3.0-distro.tar.gz

modify conf/oozie-site.xml

      122     <property>
      123         <name>oozie.service.JPAService.create.db.schema</name>
      124         <value>true</value>
      125         <description>
      126             Creates Oozie DB.
      127
      128             If set to true, it creates the DB schema if it does not exist. If the DB schema exists is a NOP.
      129             If set to false, it does not create the DB schema. If the DB schema does not exist it fails start up.
      130         </description>
      131     </property>
      
      208     <property>
      209         <name>oozie.service.HadoopAccessorService.jobTracker.whitelist</name>
      210         <value>localhost:8021</value>
      211         <description>
      212             Whitelisted job tracker for Oozie service.
      213         </description>
      214     </property>
      215
      216     <property>
      217         <name>oozie.service.HadoopAccessorService.nameNode.whitelist</name>
      218         <value>localhost:8020</value>
      219         <description>
      220             Whitelisted job tracker for Oozie service.
      221         </description>
      222     </property>

      224     <property>
      225         <name>oozie.service.HadoopAccessorService.hadoop.configurations</name>
      226         <value>*=/home/jaoki/hadoop-oozie/hadoop/conf,localhost:8020=/home/jaoki/hadoop-oozie/hadoop/conf,localhost:8021=/home/jaoki/hadoop-oozie/hadoop/conf</value>
      227         <description>
      228             Comma separated AUTHORITY=HADOOP_CONF_DIR, where AUTHORITY is the HOST:PORT of
      229             the Hadoop service (JobTracker, HDFS). The wildcard '*' configuration is
      230             used when there is no exact match for an authority. The HADOOP_CONF_DIR contains
      231             the relevant Hadoop *-site.xml files. If the path is relative is looked within
      232             the Oozie configuration directory; though the path can be absolute (i.e. to point
      233             to Hadoop client conf/ directories in the local filesystem.
      234         </description>
      235     </property>


bin/oozie-setup.sh -hadoop 0.20.200 /grid/usr/hadoop-1.0.3 -extjs ~/tmp/ext-2.2.zip

export OOZIE_URL=http://localhost:11000/oozie

./bin/oozie-start.sh

./bin/oozie admin -status



Run oozie examples
--------------------------

If hdfs runs safe mode

    ./bin/hadoop dfsadmin -safemode leave

tar -xvf oozie-sharelib-3.3.0.tar.gz


tar -xvf oozie-examples.tar

./bin/hadoop dfs -put oozie-examples /user/jaoki/examples

oozie job -run -config ~/apps/oozie-examples/apps/pig/job.properties


hbase
--------------------------
wget http://apache.petsads.us/hbase/stable/hbase-0.94.3.tar.gz

Modify conf/hbase-site.xml

    <property>
      <name>hbase.rootdir</name>
      <value>hdfs://localhost:8020/hbase</value>
    </property>
    <property>
      <name>hbase.cluster.distributed</name>
      <value>true</value>
    </property>

pig
--------------------------

wget http://mirror.cc.columbia.edu/pub/software/apache/pig/pig-0.10.0/pig-0.10.0.tar.gz

