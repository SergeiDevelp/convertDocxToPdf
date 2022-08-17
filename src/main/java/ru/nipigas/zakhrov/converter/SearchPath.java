package ru.nipigas.zakhrov.converter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class SearchPath  {


    public static void searchFilesDocx(File rootFile, List<File> fileList) {
        if (rootFile.isDirectory()) { //TODO находит все файлы от корня и в глубину, хочу ограничить только корнем либо только 2 уровнем вложенности
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        searchFilesDocx(file, fileList);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".docx")) {
                            fileList.add(file);
                        }
                    }

                }

            }
        }
    }
    public static void searchFilesPdf(File rootFile, List<File> fileList) {
        if (rootFile.isDirectory()) { //TODO находит все файлы от корня и в глубину, хочу ограничить только корнем либо только 2 уровнем вложенности
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        searchFilesPdf(file, fileList);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".pdf")) {
                            fileList.add(file);
                        }
                    }

                }

            }
        }
    }
    //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    //Properties properties = new Properties();
    public Map<String, String> findPathDirectory() throws IOException {
        //File fileConfig = new File(Objects.requireNonNull(classLoader.getResource("config.properties")).getFile());

        //Path directoryJar2 = Path.of(String.valueOf(fileConfig));
        //получаем путь, откуда запущена программа и получаем файл конфига
        //properties.load(new FileReader(String.valueOf(directoryJar2)));

        //вот этот вариант вроде сработал. Пробую упаковать

        //InputStream fileConfig = SearchPath.class.getClassLoader().getResourceAsStream("config.properties");//1 вариант для чтения из джар
        String sep = FileSystems.getDefault().getSeparator();
        File fileConfig = new File("." + sep + "config.properties"); //2 вариант для чтения из вне
        Properties properties = new Properties();
        properties.load(new FileReader(fileConfig)); //2 вариант для чтения из вне
        //properties.load(fileConfig);//1 вариант для чтения из джар
        Map<String, String> map = new HashMap<>();
        properties.forEach((k, v) -> {map.put((String) k, (String) v);});
        //map.forEach((k, v) -> System.out.println((k + ":" + v)));
        //File sourceDirectory = new File(properties.getProperty("directoryData"));

        return map;
    }


}
