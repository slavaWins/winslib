package org.slavawins.winslib.storage.ram;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RamRepository<T> {

    private final long CASHED_RAM_TIME = 10;

    public List<RamRepositoryContract<T>> list = new ArrayList<>();


    public void add(String id, T data) {

        if(find(id)!=null){

            return;
        }
        RamRepositoryContract<T> item = new RamRepositoryContract<T>();
        item.id = id;
        item.data = data;
        list.add(item);

        clean();
    }

    private void clean() {
        long current_time_seconds = Instant.now().getEpochSecond();
        Iterator<RamRepositoryContract<T>> iterator = list.iterator();

        while (iterator.hasNext()) {
            RamRepositoryContract<T> clan = iterator.next();

            if (current_time_seconds - clan.updatedAt > CASHED_RAM_TIME) {
                System.out.println("REMOVE RAM ITEM");
                iterator.remove();
            }

        }
    }

    public T find(String id) {
        for (RamRepositoryContract<T> item : list) {
            if (item.id.equalsIgnoreCase(id)) {
                item.update();
                clean();
                return item.data;
            }
        }
        return null;
    }
}
