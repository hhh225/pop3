package mainPack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Runner1 extends Thread{
    int port;
    private ServerSocket serverSocket;

    public Runner1(int port) throws IOException {
        this.port=port;
        serverSocket=new ServerSocket(port);
    }

    @Override
    public void run() {
        super.run();
        while (true)
        {
            try
            {
                System.out.println("waiting for a connect");
                Socket server=serverSocket.accept();   //有客户端连接
                System.out.println("get a connect");
                SocketHandle socketHandle=new SocketHandle(server);    //创建一个新线程来处理这个连接
                socketHandle.start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Thread t = new Runner1(8089);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
