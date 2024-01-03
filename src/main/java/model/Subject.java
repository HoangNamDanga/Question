/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.UUID;

/**
 *
 * @author ADMIN
 */
public class Subject {
    private UUID _id;// id tuwj tawng cuar mongoDB
    private String Name;
    private String Code;

    public Subject(UUID _id, String Name, String Code) {
        this._id = _id;
        this.Name = Name;
        this.Code = Code;
    }

    public UUID getId() {
        return _id;
    }

    public void setId(UUID _id) {
        this._id = _id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    
    
}
