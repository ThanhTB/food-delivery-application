package com.dev.core.report.dto;

import lombok.Data;

@Data
public class MtdDTO {
    /** month list */
    public int[] month;

    /** year */
    public int year;

    /** day of last month */
    public int ld;

    /** day of next month */
    private int nd;
}
