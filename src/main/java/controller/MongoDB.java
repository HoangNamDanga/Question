/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author ADMIN
 */
public class MongoDB {
    MongoClient mongo1 = new MongoClient("localhost", 27017);
    MongoDatabase db = mongo1.getDatabase("Question");
    public MongoCollection<Document> userCollection = db.getCollection("User"); // Replace with your collection name
    public MongoCollection<Document> subjectCollection = db.getCollection("Subject"); // Replace with your collection name
    public MongoCollection<Document> epcCollection = db.getCollection("EPC");
    public MongoCollection<Document> javaCollection = db.getCollection("JAVA");
}
