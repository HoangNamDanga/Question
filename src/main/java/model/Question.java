/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */
public class Question {
    private int qid;// id tuwj tawng cuar mongoDB
    private String title;
    private List<String> options;
    private int answer;
    private int status = 0;
    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Question(int qid, String title, List<String> options, int answer) {
        this.qid = qid;
        this.title = title;
        this.options = options;
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
    
    public String getAnswerA(){ // //
        return getOptions().get(0);
    }
    public String getAnswerB(){
        return getOptions().get(1);
    }
    public String getAnswerC(){
        return getOptions().get(2);
    }
    public String getAnswerD(){
        return getOptions().get(3);
    }
}
