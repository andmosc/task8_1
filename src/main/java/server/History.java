package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class History {
    private LinkedList<String> listHistory = new LinkedList<>();

    public void addStory(String s) {
        if (listHistory.size() >= 10)
            listHistory.removeFirst();
        listHistory.add(s);
    }

    public void printHistory(BufferedWriter writer) {
        if (listHistory.size() > 0) {
            try {
                writer.write("History messages" + "\n");
                for (String s : listHistory) {
                    writer.write("strory: " + s + "\n");
                }
                writer.write("----------------" + "\n");
                writer.flush();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
