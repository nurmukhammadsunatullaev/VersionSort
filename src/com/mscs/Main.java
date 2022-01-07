package com.mscs;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class Main {

    private static final String INPUT = "version.txt";
    private static final String OUTPUT = "sorted_version.txt";

    public static void main(String[] args) {
        try {
            ReadVersions reader = ReadVersions.getInstance(INPUT);
            WriteVersions writer = WriteVersions.getInstance(OUTPUT);
            List<String> versions = reader.getVersions();
            Pattern parser = Pattern.compile("\\.");
            Comparator<String> comparator = Comparator.comparing(version -> parser.splitAsStream(version).mapToInt(Integer::parseInt).toArray(), Arrays::compare);
            versions.sort(comparator);
            writer.setVersions(versions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ReadVersions{
    private static  ReadVersions singleton;
    private final String FILENAME;
    private ReadVersions(String fileName){
        FILENAME= fileName;
    }

    public static ReadVersions getInstance(String fileName){
        if(singleton==null) {
            singleton = new ReadVersions(fileName);
        }
        return singleton;
    }

    public static ReadVersions getInstance() throws Exception {
        if(singleton!=null){
            return singleton;
        }

        throw new Exception("Object not found");
    }

    public List<String> getVersions() throws IOException {
        BufferedReader input=new BufferedReader(new FileReader(FILENAME));
        ArrayList<String> versions= new ArrayList<>();
        String line;
        while((line=input.readLine())!=null) {
            versions.add(line);
        }
        return versions;
    }
}

class WriteVersions{
    private static  WriteVersions singleton;
    private final String FILENAME;
    private WriteVersions(String fileName){
        FILENAME= fileName;
    }

    public static WriteVersions getInstance(String fileName){
        if(singleton==null) {
            singleton = new WriteVersions(fileName);
        }
        return singleton;
    }

    public static WriteVersions getInstance() throws Exception {
        if(singleton!=null){
            return singleton;
        }

        throw new Exception("Object not found");
    }

    void setVersions(List<String> versions) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(FILENAME));
        for(String version : versions) {
            output.write(version + "\n");
        }
        output.flush();
        output.close();
    }

}
