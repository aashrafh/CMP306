
import mpi.*;
import java.io.*;

public class GatherExample {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		MPI.Init(args);
		int rank = MPI.COMM_WORLD.Rank();
		int size = 2 * MPI.COMM_WORLD.Size();
		int root = 0;
		int[] sendbuf;
		int[] recvbuf;
		char[] message = new char[30];

		if (rank == root) {
			// =============== Scatter information to all processes ================
			// This send buffer of the root process should have enough space to
			// hold the data sent to all processes
			sendbuf = new int[size];
			// This receive buffer of the root process should have enough space to
			// hold the data sent by all processes
			recvbuf = new int[size];

			for (int i = 0; i < size; i++) {
				sendbuf[i] = (i + 1) * 10;
			}

			// =============== Gather information from all processes ================

			// Since the root is 0, this function gathers all data from all processes
			// in the world including the root
			// Notice that the receive buffer is of size 2, as the sent data from all
			// processes is of size 2
			// Again:: 2 integer is received from each process, and 2 integer is sent to
			// itself
			MPI.COMM_WORLD.Gather(sendbuf, 0, 2, MPI.INT, recvbuf, 0, 2, MPI.INT, root);

			// This prints the received data from all processes including itself
			System.out.println("The received data from all processes including itself: ");
			for (int i = 0; i < size; i++) {
				System.out.println(recvbuf[i]);
			}

			message = "You can exit now".toCharArray();

			System.out.println(message);
			MPI.COMM_WORLD.Bcast(message, 0, message.length, MPI.CHAR, 0);

		} else {
			// Although it is not needed, it needs to match the total number of received
			// messages
			int[] dummy = new int[size];
			sendbuf = new int[2]; // notice only size of sent needed
			for (int i = 0; i < 2; i++) {
				sendbuf[i] = (i + 1) * 3;
			}
			// Send the new data to the root
			// send and recieve buffer should match in size
			MPI.COMM_WORLD.Gather(sendbuf, 0, 2, MPI.INT, dummy, 0, 2, MPI.INT, root);

			// make enough space to receive message.
			// what is the last parameter??
			MPI.COMM_WORLD.Bcast(message, 0, 30, MPI.CHAR, 0);
			String Msg = new String(message);
			System.out.println("Process " + rank + " exiting ...with message: " + Msg);
		}

		MPI.Finalize();
	}
}
