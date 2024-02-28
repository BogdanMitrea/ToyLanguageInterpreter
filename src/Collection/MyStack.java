package Collection;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>
{
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }
    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public int getsize() {
        return stack.size();
    }

    @Override
    public Stack<T> getstack() {
        return stack;
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString(){
        String res="";
        for (int i = stack.size() - 1; i >= 0; i--) {
            res+=stack.get(i).toString();
            if (i > 0)
                res+="\n ";
        }
        return res;
    }
}
