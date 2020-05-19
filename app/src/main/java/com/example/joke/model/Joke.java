package com.example.joke.model;

public class Joke {

    private int id;
    private String category;
    private String type;
    private String joke;
    private String setup;
    private String delivery;
    private boolean nsfw;
    private boolean religious;
    private boolean political;
    private boolean racist;
    private boolean sexist;
    private boolean error;

    public Joke(int id, String category, String type, String joke, String setup, String delivery, boolean nsfw, boolean religious, boolean political, boolean racist, boolean sexist, boolean error) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.joke = joke;
        this.setup = setup;
        this.delivery = delivery;
        this.nsfw = nsfw;
        this.religious = religious;
        this.political = political;
        this.racist = racist;
        this.sexist = sexist;
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getJoke() {
        return joke;
    }

    public String getSetup() {
        return setup;
    }

    public String getDelivery() {
        return delivery;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public boolean isReligious() {
        return religious;
    }

    public boolean isPolitical() {
        return political;
    }

    public boolean isRacist() {
        return racist;
    }

    public boolean isSexist() {
        return sexist;
    }

    public boolean isError() {
        return error;
    }
}
