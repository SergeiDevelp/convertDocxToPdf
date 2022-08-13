package ru.nipigas.zakhrov.converter;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.annotations.PdfRubberStampAnnotation;
import com.spire.pdf.annotations.appearance.PdfAppearance;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.graphics.PdfTemplate;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.List;

public class ImageStamp {

    public static void stamp(File sourceDirectory, List<File> fileList) {
        // Создать объект pdfdocument, загрузите  документ PDF
        PdfDocument doc = new PdfDocument();

        int x = 0;
        int number =0;
        while (fileList.size() > x) {
            doc.loadFromFile(String.valueOf(fileList.get(x)));

            //for (int i = 0; i < fileList.size(); i++) {

            //doc.loadFromFile("C:\\Users\\user\\IdeaProjects\\convertDocxToPdf\\target\\converter.docx.pdf");//"test.pdf";

            // Получить документ Page
            for (int j = 0; j < doc.getPages().getCount(); j++) {

                PdfPageBase page = doc.getPages().get(j);

                // Загрузка картинки уплотнения
                PdfImage image = PdfImage.fromFile("C:\\Users\\user\\IdeaProjects\\convertDocxToPdf\\target\\free767.png");
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

            doc.saveToFile(sourceDirectory + "\\" + fileList.get(x).getName(), FileFormat.PDF);
            x++;
        }
    }
}
