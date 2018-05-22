package lab4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

class Z2Sender
{
    static final int datagramSize=50;
    static final int sleepTime=500;
    InetAddress localHost;
    int destinationPort;
    DatagramSocket socket;
    SenderThread sender;
    ReceiverThread receiver;
    private List<Byte> dataSentButNotReceived;
    private List<Integer> packetSentButNotReceived;
    private List<Long> time;
    public Z2Sender(int myPort, int destPort)
            throws Exception
    {
        localHost=InetAddress.getByName("127.0.0.1");
        destinationPort=destPort;
        socket=new DatagramSocket(myPort);
        sender=new SenderThread();
        receiver=new ReceiverThread();
        dataSentButNotReceived = new LinkedList<>();
        packetSentButNotReceived = new LinkedList<>();
        time = new LinkedList<>();
        socket.setSoTimeout(10000); //Po 10 sekundach bez otrzymania pakietu socket.receive() przestanie blokowac resztę programu
    }

    class SenderThread extends Thread
    {
        public void run(){
            int iteration, packetId;
            try
            {
                for(iteration=0; (packetId=System.in.read()) >= 0 ; iteration++)
                {
                    Z2Packet toSend =new Z2Packet(4+1);
                    toSend.setIntAt(iteration,0);
                    toSend.data[4]= (byte) packetId;
                    dataSentButNotReceived.add(toSend.data[4]);
                    packetSentButNotReceived.add(iteration);
                    time.add(System.currentTimeMillis());
                    DatagramPacket packet =
                            new DatagramPacket(toSend.data, toSend.data.length,
                                    localHost, destinationPort);
                    socket.send(packet);
                    sleep(sleepTime);
                }
                while(!packetSentButNotReceived.isEmpty())
                {
                    for(int j=0;j<time.size();j++)
                    {
                        if((int)(System.currentTimeMillis()-time.get(j))>sleepTime)
                        {
                            Z2Packet p2=new Z2Packet(4+1);
                            p2.setIntAt(packetSentButNotReceived.get(j),0);
                            p2.data[4]= dataSentButNotReceived.get(j);
                            time.set(j,System.currentTimeMillis());
                            DatagramPacket packet2 =
                                    new DatagramPacket(p2.data, p2.data.length,
                                            localHost, destinationPort);
                            socket.send(packet2);
                            sleep(sleepTime);
                        }
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println("Z2Sender.SenderThread.run: "+e);
            }
        }
    }



    class ReceiverThread extends Thread
    {
        public void run()
        {
            try
            {
                while(true)
                {
                    byte[] data=new byte[datagramSize];
                    DatagramPacket packet=
                            new DatagramPacket(data, datagramSize);
                    socket.receive(packet);
                    Z2Packet p=new Z2Packet(packet.getData());
                    System.out.println("S:"+p.getIntAt(0)
                            +": "+(char) p.data[4]);
                    int num = packetSentButNotReceived.indexOf(p.getIntAt(0));
                    dataSentButNotReceived.remove(p.data[4]);
                    packetSentButNotReceived.remove(p.getIntAt(0));
                    time.remove(num);
                }
            }
            catch(Exception e)
            {
                System.out.println("Z2Sender.ReceiverThread.run: "+e);
            }
        }
    }


    public static void main(String[] args)
            throws Exception
    {
        Z2Sender sender=new Z2Sender( Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        sender.sender.start();
        sender.receiver.start();
    }



}