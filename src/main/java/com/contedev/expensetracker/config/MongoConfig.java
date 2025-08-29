package com.contedev.expensetracker.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;


public class MongoConfig {
    private static final String URI = "mongodb://localhost:27017";
    private static final MongoClient client;
    public static final MongoDatabase db;

    static {
        PojoCodecProvider pojoClient = PojoCodecProvider.builder().automatic(true).build();

        MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoClient)
        )).build();

        client = MongoClients.create(settings);
        db = client.getDatabase("expenseTracker");

    }
}
