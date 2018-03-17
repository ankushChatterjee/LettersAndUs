package com.ankush.lettersandus;

/**
 * Created by nEW u on 9/13/2017.
 */

public class Message {
    private String from;
    private String to;
    private String stampUrl;
    private String letterText;
    private String favourited;
    Message(){
    }
    Message(String f,String t,String l,String su,String i){
        from = f;
        to = t;
        stampUrl = su;
        favourited = i;
        letterText = l;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public String getStampUrl() {
        return stampUrl;
    }

    public void setStampUrl(String stampUrl) {
        this.stampUrl = stampUrl;
    }

    public String getFavourited() {
        return favourited;
    }

    public void setFavourited(String favourited) {
        this.favourited = favourited;
    }

    public String getTo() {

        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    public String getLetterText() {
        return letterText;
    }

    public void setLetterText(String letterText) {
        this.letterText = letterText;
    }



}
