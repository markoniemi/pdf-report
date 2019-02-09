package org.pdf.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.pdf.report.model.Application;
import org.pdf.report.model.Element;
import org.pdf.report.model.Field;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfReport extends Document {
    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
    Font subTitleFont = FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, BaseColor.BLACK);
    Font defaultFont = FontFactory.getFont(FontFactory.TIMES, 11, Font.NORMAL, BaseColor.BLACK);
    private Application application;

    public PdfReport(Application application) {
        super();
        this.application = application;
    }

    public void create(String filename) throws FileNotFoundException, DocumentException {
        PdfWriter.getInstance(this, new FileOutputStream(filename));
        open();
        addTitle();
        add(Chunk.NEWLINE);
        addHeader();
        addInfoElementValues();
        close();
    }

    public void addTitle() throws DocumentException {
        add(new Paragraph(application.getCode(), titleFont));
    }

    public void addHeader() throws DocumentException {
        PdfPTable table = new PdfPTable(application.getElements().size());
        table.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        // table.setSpacingBefore(10);
        // table.setSpacingAfter(10);
        for (Element element : application.getElements()) {
            table.addCell(createCell(element.getCode(), true));
        }
        add(table);
    }

    private void addInfoElementValues() throws DocumentException {
        for (Element element : application.getElements()) {
            addInfoElementValue(element);
        }
    }

    private void addInfoElementValue(Element element) throws DocumentException {
        addParagraph(element.getCode(), subTitleFont);
        for (Field field : element.getFields()) {
            addFieldValue(field);
        }
    }

    private void addFieldValue(Field field) throws DocumentException {
        addParagraphWithTab(field.getCode(), field.getValue());
    }

    private void addParagraph(String text) throws DocumentException {
        addParagraph(text, defaultFont);
    }

    private void addParagraph(String text, Font font) throws DocumentException {
        add(new Paragraph(text, font));
    }

    private void addParagraphWithTab(String... values) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.setIndentationLeft(20f);
        paragraph.setTabSettings(new TabSettings(200f));
        for (String value : values) {
            paragraph.add(value);
            paragraph.add(Chunk.TABBING);
        }
        add(paragraph);
    }

    private PdfPCell createCell(String text) {
        return createCell(text, false);
    }

    private PdfPCell createCell(String text, boolean borders) {
        return createCell(text, PdfPCell.BOTTOM | PdfPCell.TOP | PdfPCell.LEFT | PdfPCell.RIGHT);
    }

    private PdfPCell createCell(String text, int borders) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBorder(borders);
        return cell;
    }
}
