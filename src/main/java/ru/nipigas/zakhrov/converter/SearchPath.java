package ru.nipigas.zakhrov.converter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

public class SearchPath  {

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    //Properties properties = new Properties();
    public File findPathDirectory() throws IOException {
        //File fileConfig = new File(Objects.requireNonNull(classLoader.getResource("config.properties")).getFile());

        //Path directoryJar2 = Path.of(String.valueOf(fileConfig));
        //получаем путь, откуда запущена программа и получаем файл конфига
        //properties.load(new FileReader(String.valueOf(directoryJar2)));

        //вот этот вариант вроде сработал. Пробую упаковать

        //InputStream fileConfig = SearchPath.class.getClassLoader().getResourceAsStream("config.properties");//1 вариант для чтения из джар
        File fileConfig = new File("./config.properties"); //2 вариант для чтения из вне
        Properties properties = new Properties();
        properties.load(new FileReader(fileConfig)); //2 вариант для чтения из вне
        //properties.load(fileConfig);//1 вариант для чтения из джар
        File sourceDirectory = new File(properties.getProperty("directoryData"));
        System.out.println("путь который вытащил из проперти" + sourceDirectory);
        return sourceDirectory;
    }
}
