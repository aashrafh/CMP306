import java.io.Serializable;

public class SerializableClass implements Serializable {
    int x;
    int y;
    String M;

    SerializableClass() {
        x = 2;
        y = 3;
        M = "this is a String";
    }

    void print() {
        System.out.printf("The data I have is as the following :\n x=%d , y=%d , M=%s\n", x, y, M);
    }

}
