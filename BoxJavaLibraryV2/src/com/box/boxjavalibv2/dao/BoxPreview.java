package com.box.boxjavalibv2.dao;

/**
 * Preview of a file.
 */
public class BoxPreview extends BoxBigPayloadObject {

    public final static String MIN_WIDTH = "min_width";
    public final static String MIN_HEIGHT = "min_height";
    public final static String MAX_WIDTH = "max_width";
    public final static String MAX_HEIGHT = "max_height";
    public final static String PAGE = "page";

    private int firstPage = 1;
    private int lastPage = 1;

    /**
     * @param firstPage
     *            first page number
     */
    public void setFirstPage(Integer firstPage) {
        this.firstPage = firstPage;
    }

    /**
     * Get the first page number.
     * 
     * @return the first page number.
     */
    public Integer getFirstPage() {
        return this.firstPage;
    }

    /**
     * Set the last page number.
     * 
     * @param lastPage
     *            last page number
     */
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    /**
     * Get the last page number.
     * 
     * @return the last page number
     */
    public Integer getLastPage() {
        return this.lastPage;
    }

    /**
     * Get number of pages.
     * 
     * @return number of pages
     */
    public Integer getNumPages() {
        return getLastPage() - getFirstPage() + 1;
    }
}
