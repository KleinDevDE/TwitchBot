package old.actions;

import com.google.gson.JsonObject;

public abstract class Action {
    public abstract void run(JsonObject jsonObject);
}
