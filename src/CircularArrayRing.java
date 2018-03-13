import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularArrayRing<E> extends AbstractCollection<E> implements Ring<E> {

    private static final int DEFAULT_RING_SIZE = 10;
    private int ringSize, head, elementCount;
    private Object[] ringArray;

    public CircularArrayRing() {
        this.ringArray = new Object[DEFAULT_RING_SIZE];
        head = elementCount = 0;
        head = -1;
    }

    public CircularArrayRing(int ringSize) {
        this.ringArray = new Object[ringSize];
        head = elementCount = 0;
    }

    @Override
    public boolean add(E e) {
        head++;
        if (head == ringArray.length) {
            head = 0;
        }
        ringArray[head] = e;
        if (elementCount < ringArray.length)
            elementCount++;
        return true;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {

        System.out.println(Arrays.toString(ringArray));
        if (index >= ringArray.length || index < 0)
            throw new IndexOutOfBoundsException("Index out of bounds");

        //return (E) (head - index < 0 ? ringArray[head - index + ringArray.length] : ringArray[head-index]);

        int newIndex = head - index;
        if (newIndex < 0)
            newIndex += ringArray.length;
        return (E) ringArray[newIndex];
    }

    @Override
    public Iterator<E> iterator() {

        Iterator<E> myIt = new Iterator<E>() {

            private int currIndex = head;

            @Override
            public boolean hasNext() {
                if (currIndex < ringArray.length && ringArray[currIndex] != null) {
                    return false;
                }
                return true;
            }

            @Override
            public E next() {
                Object nextElement = ringArray[currIndex];
                if (currIndex < ringArray.length && ringArray[currIndex] != null)
                    throw new NoSuchElementException("No element");
                currIndex--;
                if (currIndex < 0)
                    currIndex = ringArray.length - 1;
                return (E) nextElement;
            }

            @Override
            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException("Remove is not supported");
            }
        };
        return myIt;
    }

    @Override
    public int size() {
        return elementCount;
    }
}
