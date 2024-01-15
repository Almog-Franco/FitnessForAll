package com.example.final_project;

public class Drill {
    String name, link, reps, sets;

    public Drill() {
    }

    public Drill(String name, String link, String reps, String sets) {
        this.name = name;
        this.link = link;
        this.reps = reps;
        this.sets = sets;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getReps() {
        return reps;
    }

    public String getSets() {
        return sets;
    }
}
