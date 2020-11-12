package old.utils.shredding;

import java.io.File;

public abstract class DataShredder {
    public abstract void shred(File file);
    public abstract void shred(Class<?> clazz);
    //TODO add SQL things like table, database or some rows
}
