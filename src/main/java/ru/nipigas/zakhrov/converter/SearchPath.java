package ru.nipigas.zakhrov.converter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

public class SearchPath  {

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();


    Properties properties = new Properties();


    public File findPathDirectory() throws IOException {
        File fileConfig = new File(Objects.requireNonNull(classLoader.getResource("config.properties")).getFile());
        Path directoryJar2 = Path.of(String.valueOf(fileConfig));
        //получаем путь, откуда запущена программа и получаем файл конфига
        properties.load(new FileReader(String.valueOf(directoryJar2)));
        File sourceDirectory = new File(properties.getProperty("directoryData"));
        return sourceDirectory;
    }
}
