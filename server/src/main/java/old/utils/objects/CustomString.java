package old.utils.objects;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomString implements Iterable<Character> {
    private String string;

    public CustomString(String string){
        this.string = string;
    }

    @Override
    public Iterator<Character> iterator() {
        List<Character> characters = new ArrayList<>();
        for(char c : string.toCharArray()){
            characters.add(c);
        }
        return characters.iterator();
    }

    @Override
    public String toString(){
        return string;
    }
}
