package mainPack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandle extends Thread{
    Socket socket;
    InputStream is;
    OutputStream os;
    String info;

    public SocketHandle(Socket soc){
        socket=soc;
    }

    @Override
    public void run() {
        super.run();
        try{
            is=socket.getInputStream();
            os=socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
