package be.febscap.game;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ImposteurGame {
    private final List<String> members;
    private final int id;
    private final Channel channel;
    private List<Integer> point = new ArrayList<>();
    private List<Integer> timesImpostor = new ArrayList<>();
    private List<String> words = new ArrayList<>();
    private int impostorIndex = 0;

    public ImposteurGame(List<String> members, int id, Channel channel) {
        this.members = members;
        this.id = id;
        this.channel = channel;

        for (int i = 0; i < members.size(); i++) {
            this.point.add(0);
            this.timesImpostor.add(0);
        }
        startGame();
    }

    public List<String> getMembers() {
        return members;
    }

    public int getId() {
        return id;
    }
    public List<Integer> getPoint() {
        return point;
    }

    public List<String> getWords() {
        return words;
    }

    public void addPoint(int index, int pointToAdd) {
        this.point.set(index, pointToAdd+point.get(index));
    }
    public List<Integer> getTimesImpostor() {
        return timesImpostor;
    }

    public void incrementTimesImpostor(int index) {
        this.timesImpostor.set(index, this.timesImpostor.get(index)+1);
    }

    public void startGame() {
        Random r = new Random();
        int random = r.nextInt(0,19);
        String mots = "";
        try {
            File myObj = new File("src/main/resources/motsImposteur.txt");
            Scanner myReader = new Scanner(myObj);
            for (int i = 0; i < random; i++) {
                if (random-1 == i)
                    mots = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.print("");
        }
        List<String> mot = List.of(mots.split(":"));
        impostorIndex = r.nextInt(0,3);
        int impostorWord = r.nextInt(0, 1);
        int x;
        if (impostorWord == 0)
            x = 1;
        else
            x = 0;
        for (int i = 0; i < members.size(); i++) {
            if (i == impostorIndex)
                words.add(mot.get(impostorWord));
            else
                words.add(mot.get(x));
        }
    }
}
