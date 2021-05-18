import mpi.*;
import java.util.*;
import java.io.*;


public class ScatterExample {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = 2*MPI.COMM_WORLD.Size();
        int root=0;
        int [] sendbuf; 
        int [] recvbuf; 
        char [] message = new char [30];

        if(rank==root){
        	// =============== Scatter information to all processes ================
        	// This send buffer of the root process should have enough space to 
        	// hold the data sent to all processes
        	sendbuf = new int[size];
        	// This receive buffer of the root process should have enough space to 
        	// hold the data sent by all processes
        	recvbuf = new int[size];
                
                int dummy [] = new int [1];

        	for(int i=0;i<size;i++){
	            sendbuf[i] = (i+1)*10;
			}

			// Scatter in the root processes uses the sendbuff to send data to all processes, and 
			// recvbuffer to receive the data sent to it
			// Here two integer is sent to each process, and two integer is received from itself, however if we don't want to use the receive buffer we can set it to dummy.
                        //====All recieve data are dummies
                        //==== is this blocking or unblocking?
        	MPI.COMM_WORLD.Scatter(sendbuf,0, 2, MPI.INT, dummy, 0, 1, MPI.INT, root);
      
                for(int i=0;i<size;i++){
	            System.out.println("I SENT" +sendbuf[i]);
			}
        } else {
        
        int []  dummy = new int [8]; //Although it is not needed, it needs to match the total number of sent messages
        int []	Scatter_recvbuf = new int[2];
                 //the sendpart have to match the original send, so does the rec (should match the send )
                MPI.COMM_WORLD.Scatter(dummy,0, 2, MPI.INT, Scatter_recvbuf, 0, 2, MPI.INT, root);
        	size = 2;
                for(int i=0;i<size;i++){
	            System.out.println("I rank " + rank + " am receiving from "+Scatter_recvbuf[i]);
                   //Scatter_recvbuf[i]=Scatter_recvbuf[i]*10;
			}
    		System.out.println ("Process " + rank + " exiting ..." );
        }
    	
        MPI.Finalize();
     }
}
