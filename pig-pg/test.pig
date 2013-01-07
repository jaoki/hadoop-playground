-- hadoop dfs -put passwd /user/jaoki
-- run create 'test1', 'cf' in hbase shell
register /home/jaoki/apps/hbase-0.94.3/hbase-0.94.3.jar
register /home/jaoki/apps/hbase-0.94.3/lib/zookeeper-3.4.3.jar
register /home/jaoki/apps/hbase-0.94.3/lib/protobuf-java-2.4.0a.jar

A = load 'passwd' using PigStorage(':') AS (
f1:chararray,
f2:chararray,
f3:chararray,
f4:chararray,
f5:chararray,
f6:chararray,
f7:chararray
		);

B = foreach A generate $0 as id;

-- dump A;
-- dump B;
store A into 'hbase://test1' using org.apache.pig.backend.hadoop.hbase.HBaseStorage('cf:f1 cf:f2 cf:f3 cf:f4 cf:f5 cf:f6 cf:f7');
-- store B into 'hbase://test1' using org.apache.pig.backend.hadoop.hbase.HBaseStorage('id');
-- store B into 'hbase://test1' using org.apache.pig.backend.hadoop.hbase.HBaseStorage('cf:id');

