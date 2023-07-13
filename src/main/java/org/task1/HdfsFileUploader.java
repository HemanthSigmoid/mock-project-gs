package org.task1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import static org.task1.constant.*;

public class HdfsFileUploader {
    public static void main(String[] args) {
        try{
            uploadCsvFilesToHdfs();
            System.out.println("Files uploaded successfully to HDFS");
        }
        catch(Exception e){
            System.out.println("Could not upload files: "+e);
        }
    }
    private static void uploadCsvFilesToHdfs() throws Exception{
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        for(int i = 1; i<= NUM_FILES; i++){
            String srcPath = path + "people_" + i + ".csv";
            String destPath = path + "hadoop-files/people_" + (i+1) + ".csv";
           fs.copyFromLocalFile(new Path(srcPath),new Path(destPath));
        }
        fs.close();
    }
}
