package org.pdf.report.model;

import java.util.List;

import lombok.Data;

@Data
public class Element {
    private String code;
    private List<Field> fields;
}
