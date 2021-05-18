
/**
 *
 * @author Dina Tantawy
 */
import mpi.*;

public class PointtoPoint {

	public static void main(String[] args) {
		MPI.Init(args);
		int myrank = MPI.COMM_WORLD.Rank();
		System.out.println("My Rank: " + myrank);
		if (myrank == 0) {
			char[] message = "Hello, there".toCharArray();
			System.out.println("Will send:" + new String(message) + ":");

			try {
				Thread.sleep(3000); // 1000 milliseconds is one second.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			MPI.COMM_WORLD.Send(message, 0, message.length, MPI.CHAR, 2, 99);
		} else {
			char[] message = new char[20];
			System.out.println("Will receive:" + new String(message));
			Request req = MPI.COMM_WORLD.Irecv(message, 0, 20, MPI.CHAR, 0, 99);
			System.out.println("Received:" + new String(message));
			req.Wait();
			System.out.println("My Rank: " + myrank + " and I received after waiting:" + new String(message));
		}
		MPI.Finalize();
	}
}
