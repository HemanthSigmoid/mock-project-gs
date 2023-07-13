package org.task1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.task1.constant.NUM_FILES;
import static org.task1.constant.path;

public class HBaseDataLoader {

    private static final String HBASE_ZOOKEEPER_QUORUM = "127.0.0.1";
    private static final String HBASE_ZOOKEEPER_CLIENTPORT = "2182";
    private static final String tableName = "people";
    private static final String columnFamily = "info";

    public static void main(String[] args) {

        try{
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", HBASE_ZOOKEEPER_QUORUM);
            conf.set("hbase.zookeeper.property.clientPort", HBASE_ZOOKEEPER_CLIENTPORT);
            Connection connection = ConnectionFactory.createConnection(conf);
            createTable(connection);
            loadDataIntoTable(connection);
            System.out.println("Data has been loaded into table: "+tableName);
            connection.close();
        }
        catch (Exception e){
            System.out.println("Exception has arisen: "+e);
        }

    }

    private static void createTable(Connection connection) throws Exception{
        try (Admin admin = connection.getAdmin()) {
            TableName testTableName = TableName.valueOf(tableName);
            if(!admin.tableExists(testTableName)){
                TableDescriptor tableDescriptor =  TableDescriptorBuilder.newBuilder(testTableName).
                        addColumnFamily(ColumnFamilyDescriptorBuilder.
                                newBuilder(Bytes.toBytes("info")).build()).
                        build();
                admin.createTable(tableDescriptor);
                admin.close();
            }
        }

    }

    private static void loadDataIntoTable(Connection connection) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));

        for(int i=1;i<=NUM_FILES;i++){
            String fileName = path + "hadoop-files/people_" + i + ".csv";
            File file = new File(fileName);
            if (file.exists()) {

                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] columns = line.split(",");
                        Put put = new Put(Bytes.toBytes(columns[0]));
                        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("age"), Bytes.toBytes(columns[1]));
                        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("company"), Bytes.toBytes(columns[2]));
                        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("building_code"), Bytes.toBytes(columns[3]));
                        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("phone_number"), Bytes.toBytes(columns[4]));
                        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("address"), Bytes.toBytes(columns[5]));

                        table.put(put);
                    }
                }
            }
            else System.out.println("File does not exist: " + fileName);
        }
        table.close();
    }
}
