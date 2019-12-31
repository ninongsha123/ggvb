package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/6/11/011.
 */

public class Sudoku {
    private String text;
    private int image;

    public Sudoku() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Sudoku(String text, int image) {

        this.text = text;
        this.image = image;
    }
}
