package com.bi.util;

import java.util.List;

public class Page<e> {
    private boolean first=true;
    private boolean last=false;
    private String number = null;
    private String size = "5";
    private String totalPages = null;
    private List<e> content = null;

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public List<e> getContent() {
        return content;
    }

    public void setContent(List<e> content) {
        this.content = content;
    }
}
