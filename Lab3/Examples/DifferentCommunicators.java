
import mpi.*;

/**
 *
 */
public class DifferentCommunicators {
    public static void main(String[] args) {
    // Get the rank and size in the original communicator
        MPI.Init(args);
         int world_rank = MPI.COMM_WORLD.Rank();
        int world_size = MPI.COMM_WORLD.Size();


        int color = world_rank / 2; // Determine color based on row

        // Split the communicator based on the color and use the
        // original rank for ordering
        Intracomm row_comm = MPI.COMM_WORLD.Split(color, world_rank);

        int row_rank = row_comm.Rank();
        int row_size = row_comm.Size();

        System.out.printf("WORLD RANK/SIZE: %d/%d \t ROW RANK/SIZE: %d/%d\n",world_rank, world_size, row_rank, row_size);

        MPI.Finalize();
    }


}
