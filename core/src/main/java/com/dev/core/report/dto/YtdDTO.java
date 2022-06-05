package com.dev.core.report.dto;

import lombok.Data;

@Data
public class YtdDTO {
    /** month */
    private int month;

    /** year */
    private int year;

    /** day of last month */
    public int ld;

    /** day of next month */
    private int nd;
}
