package com.fenixinfotech.csveed.playpen;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvDate;
import org.csveed.annotations.CsvFile;

import java.util.Date;

@CsvFile(separator=',')
public class CSVPojo
{
    @CsvCell(columnIndex = 1)
    private String key;
    @CsvCell(columnIndex = 2)
    private String col1;
    @CsvCell(columnIndex = 3)
    private String col2;
    @CsvCell(columnIndex = 4)
    private String col3;
    @CsvCell(columnIndex = 5)
    @CsvDate(format = "yyyy-MM-dd")
    private Date col4;

    public CSVPojo() {
    }

    public CSVPojo(String key, String col1, String col2, String col3, Date col4) {
        this.key = key;
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSVPojo csvPojo = (CSVPojo) o;

        if (col1 != null ? !col1.equals(csvPojo.col1) : csvPojo.col1 != null) return false;
        if (col2 != null ? !col2.equals(csvPojo.col2) : csvPojo.col2 != null) return false;
        if (col3 != null ? !col3.equals(csvPojo.col3) : csvPojo.col3 != null) return false;
        if (col4 != null ? !col4.equals(csvPojo.col4) : csvPojo.col4 != null) return false;
        if (key  != null ? !key.equals(csvPojo.key)   : csvPojo.key  != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (col1 != null ? col1.hashCode() : 0);
        result = 31 * result + (col2 != null ? col2.hashCode() : 0);
        result = 31 * result + (col3 != null ? col3.hashCode() : 0);
        result = 31 * result + (col4 != null ? col4.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CSVPojo{" +
                "key='" + key + '\'' +
                ", col1='" + col1 + '\'' +
                ", col2='" + col2 + '\'' +
                ", col3='" + col3 + '\'' +
                ", col4='" + col4 + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public Date getCol4() {
        return col4;
    }

    public void setCol4(Date col4) {
        this.col4 = col4;
    }
}