package org.slavawins.winslib.storage;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import org.json.simple;

public class BaseColdRepository<T extends ModelCold> {


    private File dataFoolder;

    private List<T> cashedHot = new ArrayList<>();

    private long CASHED_RAM_TIME = 60 * 5;
    private boolean IS_DEBUG = false;


    private T FindInRam(String name) {

        long current_time_seconds = Instant.now().getEpochSecond();
        Iterator<T> iterator = cashedHot.iterator();

        while (iterator.hasNext()) {
            T clan = iterator.next();

            if (clan.GetId().equals(name)) {
                clan.updatedAt = current_time_seconds;
                return clan;
            }

            if (current_time_seconds - clan.updatedAt > CASHED_RAM_TIME) {
                if (IS_DEBUG)
                    System.out.println("CLEAR FROM RAM " + clan.GetId() + " no used: " + (current_time_seconds - clan.updatedAt));
                iterator.remove();
            }
        }

        return null;
    }


    public void remove(String name) {


        T item = FindInRam(name);
        if (item != null) {
            cashedHot.remove(item);
        }

        File file = new File(dataFoolder, name + ".json");
        if (!file.exists()) return;

        if (!file.delete()) {
            System.out.println("BaseColdRepository Error deleted " + name);
        }
    }

    public T FindByName(String name) {

        T res = FindInRam(name);
        if (res != null) {
            if (IS_DEBUG) System.out.println("DATA FROM RAM " + res.GetId());
            return res;
        }

        File file = new File(dataFoolder, name + ".json");

        if (!file.exists()) return null;

        try {

            Gson gson = new Gson();
            Reader reader = new FileReader(file);

            T m = gson.fromJson(reader, GetClassModel());
            m.updatedAt = Instant.now().getEpochSecond();
            if (IS_DEBUG) System.out.println("ADD IN RAM " + m.GetId());
            cashedHot.add(m);
            reader.close();
            return m;

        } catch (IOException e) {

            System.out.println("----- [] Ошибка загрузки возможно файл повережден");
            e.printStackTrace();
        }

        return null;
    }


    public Type GetClassModel() {
        return null;
    }


    public String GetDirName() {
        return "data-list";
    }


    public boolean Save(T data) {


        String uid = data.GetId();
        if (uid.isEmpty()) {
            System.out.println("ERROR SAVED IN " + GetDirName());
            return false;
        }

        data.updatedAt = Instant.now().getEpochSecond();
        if (data.createdAt == 0) {
            data.createdAt = Instant.now().getEpochSecond();
        }

        if (IS_DEBUG) System.out.println("TRY TO SAVE " + GetDirName());
        File file = new File(dataFoolder, data.GetId() + ".json");

        Gson gson = new Gson();


        try {
            if (!file.exists()) file.createNewFile();

            Writer writer = new FileWriter(file, false);
            gson.toJson(data, writer);
            writer.flush();
            writer.close();


            if (IS_DEBUG) System.out.println("SAVE IN:" + file.getPath());

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public void Init(File dataFoolder) {

        if (!dataFoolder.exists()) dataFoolder.mkdirs();


        File file = new File(dataFoolder, GetDirName());
        if (!file.exists()) file.mkdirs();


        this.dataFoolder = file;


    }

    public List<T> GetAll() {

        this.cashedHot.clear();


        File[] files = dataFoolder.listFiles();


        if (files == null) return cashedHot;


        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                FindByName(file.getName().replace(".json", ""));
            }
        }

        return cashedHot;
    }
}
