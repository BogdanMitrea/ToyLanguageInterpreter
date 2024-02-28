package Collection;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T>{

    private ArrayList<T> mylist;

    public MyList()
    {
        mylist=new ArrayList<T>();
    }
    @Override
    public int size() {
        return mylist.size();
    }

    @Override
    public ArrayList<T> getArrayList()
    {
        return this.mylist;
    }
    @Override
    public boolean isEmpty() {
        return mylist.isEmpty();
    }

    @Override
    public boolean add(T e) {
        return mylist.add(e);
    }

    @Override
    public void clear() {
        mylist.clear();
    }

    @Override
    public T get(int index) {
        return mylist.get(index);
    }

    @Override
    public String toString()
    {
        String res="";
        for(int i=0;i<this.mylist.size();i++)
        {
            res+=mylist.get(i).toString();
            if (i < this.mylist.size()-1)
                res+="\n ";
        }
        return res;
    }
}
