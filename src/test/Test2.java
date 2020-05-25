package test;

public class Test2 implements Runnable{

    @Override
    public void run() {
        int i=0;
        for (i=0;i<120;i++){
            System.out.println(i);
        }
    }

    public static void main(String[] argv)
    {
        Runnable t1=new Test2();
        Thread t=new Thread(t1,"t1");
        t.start();
        int i=0;
        while(i<100){System.out.println("hello");i++;}
    }
}
