package org.slavawins.winslib.storage;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import org.json.simple;

public class BaseHotRepository<T extends IModelHotRepository> {

    private File file;
    private List<T> myList = new ArrayList<>();



    public T FindByName(String name) {
        for (T clan : myList) {


            if (clan.GetId().equals(name)) {
                return clan;
            }
        }
        return null;
    }


    public boolean Save() {


        Gson gson = new Gson();

        try {
            file.getParentFile().mkdir();

            file.createNewFile();
            Writer writer = new FileWriter(file, false);
            gson.toJson(myList, writer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public List<T> Get() {
        return myList;
    }
    public List<T> GetAll() {
        return myList;
    }

    public Class<T[]> GetClass() {
        return null;
    }

    public void Load() throws IOException {

        Gson gson = new Gson();

        if (file.exists()) {
            Reader reader = new FileReader(file);
            T[] n = gson.fromJson(reader, GetClass());
            myList = new ArrayList<>(Arrays.asList(n));
            reader.close();
        }
    }


    public String GetFileName() {
        return "item-storage";
    }

    public void Init(File dataFoolder) {

        if (!dataFoolder.exists()) dataFoolder.mkdirs();


        file = new File(dataFoolder, GetFileName() + ".json");


        try {
            Load();
        } catch (IOException e) {

            System.out.println("----- [RENT TABLE] Ошибка загрузки rents-data.json возможно файл повережден");
            e.printStackTrace();
        }


    }
}
