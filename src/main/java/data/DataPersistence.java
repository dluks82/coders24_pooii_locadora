package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataPersistence {

    public static <T> void save(List<T> dataList, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeObject(dataList);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar os dados: " + e.getMessage());
        }
    }


    public static <T> List<T> load(String fileName) {
        File arquivo = new File(fileName);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao ler os dados: " + e.getMessage());
        }

    }


}
