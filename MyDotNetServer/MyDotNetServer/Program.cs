using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;

namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                IPAddress ipAd = IPAddress.Parse("127.0.0.1");
                // use local m/c IP address, and 

                // use the same in the client


                /* Initializes the Listener */
                TcpListener myList = new TcpListener(ipAd, 11000);

                /* Start Listeneting at the specified port */
                myList.Start();

                Console.WriteLine("The server is running at port 11000...");
                Console.WriteLine("The local End point is  :" +
                                  myList.LocalEndpoint);
                Console.WriteLine("Waiting for a connection.....");
            m:
                Socket s = myList.AcceptSocket();
                Console.WriteLine("Connection accepted from " + s.RemoteEndPoint);

                byte[] b = new byte[100];
                int k = s.Receive(b);

                char cc = ' ';
                string test = null;
                Console.WriteLine("Recieved...");
                for (int i = 0; i < k - 1; i++)
                {
                    Console.Write(Convert.ToChar(b[i]));
                    cc = Convert.ToChar(b[i]);
                    test += cc.ToString();
                }

                switch (test)
                {
                    case "1":
                        break;


                }

                ASCIIEncoding asen = new ASCIIEncoding();
                s.Send(asen.GetBytes("The string was recieved by the server."));
                Console.WriteLine("\nSent Acknowledgement");


                /* clean up */
                goto m;
                s.Close();
                myList.Stop();
                Console.ReadLine();
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                Console.ReadKey();
            }
        }
    }
}
