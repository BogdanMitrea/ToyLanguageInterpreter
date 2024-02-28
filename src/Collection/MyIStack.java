package Collection;

import java.util.Stack;

public interface MyIStack<T> {
    T pop();
    int getsize();

    Stack<T> getstack();
    void push(T v);
    boolean isEmpty();
    String toString();
}


