import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class SelectTheme{
    List<String> s = new ArrayList<>();
    public SelectTheme(List<String> s) {
        this.s = s;
        Collections.shuffle(s);
    }

    public String getTheme(int themeNum){
        return s.get(themeNum);
    }

    public void resetTheme(){
        Collections.shuffle(s);
    }

    public void newThemeSet(List<String> s){
        this.s = s;
        Collections.shuffle(s);
    }
}