package ru.nipigas.zakhrov.converter;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import java.io.*;
import java.net.InetAddress;
import java.util.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        SearchPath searchPath = new SearchPath();
        //Map<String, String> map2;
        //map2 = searchPath.findPathDirectory().;
        //File sourceDirectory = new File(map2.get("directoryData"));  //searchPath.findPathDirectory();
        File sourceDirectory = new File(searchPath.findPathDirectory().get("directoryData"));

        String compName = InetAddress.getLocalHost().getHostName();//TODO можно получить имя компа, составить путь до ClassLoader, и создать путь автоматически записав в проперти?
        System.out.println(compName);
        ArrayList<File> fileListDocx = new ArrayList<>();
        ArrayList<File> fileListPdf = new ArrayList<>();
        //Загрузить документа Word
        try {
            SearchPath.searchFilesDocx(sourceDirectory, fileListDocx); //directoryJar.toUri()
             //directoryJar.toUri()
            for (File file :
                    fileListDocx) {
                System.out.println(file.getAbsolutePath());
            }

            Document document = new Document();
            for (int i = 0; i < fileListDocx.size(); i++) {
                document.loadFromFile(String.valueOf(fileListDocx.get(i)));
                //Сохранить как PDF
                document.saveToFile(sourceDirectory + "\\" + fileListDocx.get(i).getName() + ".pdf", FileFormat.PDF); //TODO отрезать от названия файла символы после точки
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("convert form word to pdf problems");
        }

        //Проставляем штамп
        try {
            SearchPath.searchFilesPdf(sourceDirectory, fileListPdf);
            for (File file :
                    fileListPdf) {
                System.out.println(file.getAbsolutePath());
            }
            ImageStamp.stamp(searchPath.findPathDirectory(), fileListPdf); //"C:\\Users\\user\\Desktop\\tests\\forConverter\\converter123.docx.pdf"
            ImageStamp.dynamicStamp(sourceDirectory,fileListPdf);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("pdf problems");
        }
        System.out.println("Hello World!");
    }

//    private static void searchFilesDocx(File rootFile, List<File> fileList) {
//        if (rootFile.isDirectory()) { //TODO находит все файлы от корня и в глубину, хочу ограничить только корнем либо только 2 уровнем вложенности
//            File[] directoryFiles = rootFile.listFiles();
//            if (directoryFiles != null) {
//                for (File file : directoryFiles) {
//                    if (file.isDirectory()) {
//                        searchFilesDocx(file, fileList);
//                    } else {
//                        if (file.getName().toLowerCase().endsWith(".docx")) {
//                            fileList.add(file);
//                        }
//                    }
//
//                }
//
//            }
//        }
//    }
//    private static void searchFilesPdf(File rootFile, List<File> fileList) {
//        if (rootFile.isDirectory()) { //TODO находит все файлы от корня и в глубину, хочу ограничить только корнем либо только 2 уровнем вложенности
//            File[] directoryFiles = rootFile.listFiles();
//            if (directoryFiles != null) {
//                for (File file : directoryFiles) {
//                    if (file.isDirectory()) {
//                        searchFilesPdf(file, fileList);
//                    } else {
//                        if (file.getName().toLowerCase().endsWith(".pdf")) {
//                            fileList.add(file);
//                        }
//                    }
//
//                }
//
//            }
//        }
//    }
}
