package org.pdf.report.model;

import java.util.List;

import lombok.Data;

@Data
public class Application {
    private String code;
    private List<Element> elements;
}
