import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ConnectionListener implements Runnable {

    private MulticastSocket socket;
    private DatagramPacket packet;
    private byte[] BUFFER;

    public ConnectionListener() {
        try {
            BUFFER = new byte[64];
            socket = new MulticastSocket(9854);
            InetAddress ip = InetAddress.getByName("224.0.0.1");
            packet = new DatagramPacket(BUFFER, BUFFER.length);
            socket.joinGroup(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("LISTENING...");
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(packet.getAddress());

        byte[] data = packet.getData();
        System.out.println(toInt(data, 0));
        System.out.println(toInt(data, 4));
        System.out.println(byteToString(data, 8, data.length));

    }

    public int toInt(byte[] array, int startIndex) {
        byte[] code = {array[startIndex], array[startIndex + 1], array[startIndex + 2], array[startIndex + 3]};
        return new BigInteger(code).intValue();
    }

    public String byteToString(byte[] array, int startIndex, int endIndex) {
        byte[] byteArray = new byte[endIndex - startIndex];
        int j = 0;
        for (int i = startIndex; i < endIndex; i++) {
            byteArray[j] = (array[i]);
            j++;
        }


        return new String(byteArray).trim();
    }

}
