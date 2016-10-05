package DeleterRepeatFiles;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    private static File file ;
    private static String path; 
    private static Scanner in = new Scanner(System.in);
    private static HashSet<String> set = new HashSet<>();

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println("Description: This program delete repeats files in your dir");
        inputDir();
        lookingfor(file);
        System.out.println("!!! Finish !!!");
    }


    private static void inputDir(){
        while(true){
            System.out.println("Looking for in dir:");
            path = in.nextLine();
            file = new File(path);
            if(file.isDirectory()){
                System.out.println("-> Well, '"+ path +"' is dir");
                break;
            }else{
                System.out.println("!!! Please correct !!!");
            }
        }
    }

    private static void lookingfor(File file) throws IOException, NoSuchAlgorithmException {
        File[] list = file.listFiles();
        for (File l : list) {
            if(l.isDirectory()) lookingfor(l); // recursion searching
            else {
                    MessageDigest md5Digest = MessageDigest.getInstance("MD5");
                    String checksum = getFileChecksum(md5Digest, l);
                    if(set.contains(checksum)){ // if this file finded yet
                        System.out.println("delete file: "+l);
                        l.delete();

                    }else{
                        // add hash to set
                        System.out.println("original: "+l);
                        set.add(checksum);
                    }
            }
        }
    }


    /* StackOverFlow help*/
    private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }


}
