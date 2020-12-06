/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendation_library.domain;

/**
 *
 * @author timot
 */
public class Tag {
    private int id;
    private String text;
    
    public Tag(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
    
}
