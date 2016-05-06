package com.k13.secondeventbus;

/**
 * Created by Patel Milan on 4/5/16.
 */

/*This Calss Is POJO*/
public class WordReadyEvent
{
    private String word;

    public WordReadyEvent(String word)
    {
        this.word=word;
    }

    public String getWord() {
        return(word);
    }
}
