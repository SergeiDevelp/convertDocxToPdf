package ru.nipigas.zakhrov.converter;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.annotations.PdfRubberStampAnnotation;
import com.spire.pdf.annotations.appearance.PdfAppearance;
import com.spire.pdf.graphics.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ImageStamp {

    public static void stamp(Map configProperty, List<File> fileList) {
        // Создать объект pdfdocument, загрузите  документ PDF
        PdfDocument doc = new PdfDocument();

        int x = 0;
        int number = 0;
        while (fileList.size() > x) {
            doc.loadFromFile(String.valueOf(fileList.get(x)));

            //for (int i = 0; i < fileList.size(); i++) {

            //doc.loadFromFile("C:\\Users\\user\\IdeaProjects\\convertDocxToPdf\\target\\converter.docx.pdf");//"test.pdf";

            // Получить документ Page
            for (int j = 0; j < doc.getPages().getCount(); j++) {

                PdfPageBase page = doc.getPages().get(j);

                // Загрузка картинки уплотнения
                PdfImage image = (PdfImage) configProperty.get("pathForStampImage"); //PdfImage.fromFile("C:\\Users\\user\\Desktop\\tests\\free767.png");
                // Получить ширину и высоту изображения уплотнения
                int width = (int) (image.getWidth() * 0.15f);
                int height = (int) (image.getHeight() * 0.15f);

                // Создать объект pdftemplate
                PdfTemplate template = new PdfTemplate(width, height);
                // Нарисуйте картинки к шаблону
                template.getGraphics().drawImage(image, 0, 0, width, height);

                // создать объект pdfrubebrstampannotation, укажите размер и местоположение
                Rectangle2D rect = new Rectangle2D.Float((float) (page.getActualSize().getWidth() - width - 10), (float) (page.getActualSize().getHeight() - height - 60), width, height);
                PdfRubberStampAnnotation stamp = new PdfRubberStampAnnotation(rect);

                // Создать объект PDFAppearance
                PdfAppearance pdfAppearance = new PdfAppearance(stamp);
                // Примените шаблон в общее состояние PDFAppearance
                pdfAppearance.setNormal(template);
                // применять PDFAppearance в стиле штампа
                stamp.setAppearance(pdfAppearance);

                // добавить штамп до PDF
                page.getAnnotationsWidget().add(stamp);
            }
            // Сохранить документ
            //doc.saveToFile("C:\\Users\\user\\Desktop\\tests\\ImageStamp.pdf", FileFormat.PDF);

            doc.saveToFile(configProperty.get("directoryData") + "\\" + fileList.get(x).getName(), FileFormat.PDF);
            x++;
        }
    }

    public static void dynamicStamp(File sourceDirectory, List<File> fileList) {
        // Создать объект pdfdocument
        PdfDocument document = new PdfDocument();
        int x = 0;
        int number = 0;
        while (fileList.size() > x) {

            document.loadFromFile(String.valueOf(fileList.get(x)));
            // Загрузить PDF документ
            //document.loadFromFile("test.pdf");

            for (int j = 0; j < document.getPages().getCount(); j++) {
                // получить страницу 3
                //PdfPageBase page = document.getPages().get(2);
                PdfPageBase page = document.getPages().get(j);

                // Создать объект pdftamplate
                PdfTemplate template = new PdfTemplate(185, 50);

                // Создать два шрифта
                PdfTrueTypeFont font1 = new PdfTrueTypeFont(new Font("Arial Unicode MS", Font.PLAIN, 14), true);
                PdfTrueTypeFont font2 = new PdfTrueTypeFont(new Font("Arial Unicode MS", Font.PLAIN, 10), true);

                // создать роспись кистью
                PdfSolidBrush solidBrush = new PdfSolidBrush(new PdfRGBColor(Color.blue));
                Rectangle2D rect1 = new Rectangle2D.Float();
                rect1.setFrame(new Point2D.Float(0, 0), template.getSize());

                // Создать прямоугольник с закругленными углами
                int CornerRadius = 20;
                PdfPath path = new PdfPath();
                path.addArc(template.getBounds().getX(), template.getBounds().getY(), CornerRadius, CornerRadius, 180, 90);
                path.addArc(template.getBounds().getX() + template.getWidth() - CornerRadius, template.getBounds().getY(), CornerRadius, CornerRadius, 270, 90);
                path.addArc(template.getBounds().getX() + template.getWidth() - CornerRadius, template.getBounds().getY() + template.getHeight() - CornerRadius, CornerRadius, CornerRadius, 0, 90);
                path.addArc(template.getBounds().getX(), template.getBounds().getY() + template.getHeight() - CornerRadius, CornerRadius, CornerRadius, 90, 90);
                path.addLine(template.getBounds().getX(), template.getBounds().getY() + template.getHeight() - CornerRadius, template.getBounds().getX(), template.getBounds().getY() + CornerRadius / 2);

                // нарисуйте путь к шаблону и заполните
                template.getGraphics().drawPath(PdfPens.getBlue(), path);

                // Нарисуйте текст и динамическую дату на шаблоне
                String s1 = "АО \"НИПИГАЗ\"";
                String s2 = "Центр управления миром";
                String s3 = dateToString(new java.util.Date(), "dd-MMM-yy");

                template.getGraphics().drawString(s1, font1, solidBrush, new Point2D.Float(5, 5));
                template.getGraphics().drawString(s2, font2, solidBrush, new Point2D.Float(5, 20));
                template.getGraphics().drawString(s3, font2, solidBrush, new Point2D.Float(5, 33));

                // создать объект pdfrubberstampannotation и укажите его местоположение и размер
                Rectangle2D rect2 = new Rectangle2D.Float();
                rect2.setFrame(new Point2D.Float((float) (page.getActualSize().getWidth() - 250), (float) (page.getActualSize().getHeight() - 90)), template.getSize());
                PdfRubberStampAnnotation stamp = new PdfRubberStampAnnotation(rect2);

                // Создать объект PDFAppearance, шаблон приложения является нормальным состоянием
                PdfAppearance appearance = new PdfAppearance(stamp);
                appearance.setNormal(template);

                // стиль приложения на штамп
                stamp.setAppearance(appearance);

                // добавить марку к коллекции аннотации
                page.getAnnotationsWidget().add(stamp);
            }
            // Сохранить документ
            document.saveToFile("DynamicStamp.pdf");
            document.close();
            x++;
        }
    }

    // преобразую дату в строку
    public static String dateToString(Date poDate, String pcFormat) {
        SimpleDateFormat loFormat = new SimpleDateFormat(pcFormat);
        return loFormat.format(poDate);
    }

}

