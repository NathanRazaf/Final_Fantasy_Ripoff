package Utilities;

import FinalFantasy.MainFlows.GameStateManager;

import java.io.*;

public class SerializationUtil {
    public static void saveData(GameStateManager gameStateManager, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(gameStateManager);
        }
    }

    public static GameStateManager loadData(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameStateManager) ois.readObject();
        }
    }
}
