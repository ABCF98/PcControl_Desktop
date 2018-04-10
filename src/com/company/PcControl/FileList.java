package com.company.PcControl;

import com.company.PcControl.file.FileHead;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;

public class FileList {

    /************************* TO GET LIST OF FILES
     *
     * @param path
     * @return
     *
     * First get the list of file names
     * Check the file type
     * Set size to no. of items (if it is a directory) or actual size
     * Set the absolute path
     * Return the list of file headers
     */
    public static ArrayList<FileHead> getFileHeadArrayList(String path) {
        ArrayList<FileHead> filesList = new ArrayList<FileHead>() ;
        File parDir = new File(path) ;
        File[] files = parDir.listFiles() ;

        if(files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName() ;
                String size, filePath, type ;

                if(files[i].isDirectory()) {
                    type = "dir/ord" ;
                    File[] tempList = files[i].listFiles() ;
                    if(tempList != null) {
                        size = tempList.length + " items" ;
                    } else {
                        size = "0 items" ;
                    }
                } else {
                    try {
                        type = identifyFileType(fileName) ;
                    } catch (IOException e) {
                        e.printStackTrace();
                        type = "text/plain" ;
                    }
                    size = String.valueOf(files[i].length());
                }
                filePath = files[i].getAbsolutePath() ;
                filesList.add(new FileHead(fileName, size, filePath, type)) ;
            }
            print(filesList) ;
            return filesList ;
        }

        return null ;
    }

    public static void print(ArrayList<FileHead> fileHeads) {
        for(int i = 0; i < fileHeads.size(); i++) {
            System.out.println("Filename: " + fileHeads.get(i).getFileName() + " Size: " + fileHeads.get(i).getSize() + " Path: " + fileHeads.get(i).getPath() + " Type: " + fileHeads.get(i).getType());
        }
    }

    // Identifies the file type
    private static String identifyFileType(String fileName) throws IOException {
        final String fileUrl = "file://" + fileName ;
        FileNameMap fileNameMap = URLConnection.getFileNameMap() ;

        return fileNameMap.getContentTypeFor(fileUrl) ;
    }

}
