package org.slavawins.winslib.storage.ram;

import java.time.Instant;

public class RamRepositoryContract<T> {

    public String id;
    public long updatedAt;
    public T data;


    public void update() {
        long current_time_seconds = Instant.now().getEpochSecond();
        updatedAt = current_time_seconds;
    }

    public RamRepositoryContract() {
        update();
    }
}
