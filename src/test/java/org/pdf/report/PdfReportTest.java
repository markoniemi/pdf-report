package org.pdf.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.pdf.report.model.Application;
import org.pdf.report.model.Field;
import org.pdf.report.model.Element;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PdfReportTest {
    @Test
    public void test() throws IOException, DocumentException {
        Application application = createApplication();
        PdfReport pdfReport = new PdfReport(application);
        pdfReport.create("target/report.pdf");
        PdfReader pdfReader = new PdfReader("target/report.pdf");
        String text = PdfTextExtractor.getTextFromPage(pdfReader, 1);
        Assert.assertEquals(33, text.split("\n").length);
        Assert.assertTrue(text.contains(application.getCode()));
    }

    public Application createApplication() {
        Application application = new Application();
        application.setCode("Title text");
        application.setElements(createElements());
        return application;
    }

    private List<Element> createElements() {
        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Element element = new Element();
            element.setCode("code" + i);
            element.setFields(createFields());
            elements.add(element);
        }
        return elements;
    }

    private List<Field> createFields() {
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Field field = new Field();
            field.setCode("code" + i);
            field.setValue("value" + i);
            fields.add(field);
        }
        return fields;
    }
}
