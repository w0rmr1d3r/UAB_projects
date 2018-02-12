package generics;

/**
 * Class TwoTuple
 * @author ramonguimera, davidcuadrado
 *
 * @param <A> first element of type A
 * @param <B> second element of type B
 */
public class TwoTuple<A,B> {
    
    public final A first;
    public final B second;

    /**
     * Constructor
     * @param first element (type A)
     * @param second element (type B)
     */
    public TwoTuple(A first, B second) {
        this.first  = first;
        this.second = second;
    }
    
    /**
     * Prints the elements
     */
    @Override
    public String toString() {
        return first.toString() + " " + second.toString();
    }
    
}
