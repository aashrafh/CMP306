import java.util.Random;
import mpi.MPI;

public class ReduceTest {
    public static void main(String[] args) {

        float rand_nums[] = new float[4];

        MPI.Init(args);
        int world_rank = MPI.COMM_WORLD.Rank();
        int world_size = MPI.COMM_WORLD.Size();

        // generate random elements for each process

        Random randomFloatsGenerator = new Random(System.nanoTime());
        StringBuilder arrayString = new StringBuilder();
        arrayString.append("I am process with rank ").append(world_rank).append(", my array values are {");
        for (int i = 0; i < rand_nums.length; i++) {
            rand_nums[i] = randomFloatsGenerator.nextFloat() * 10;
            arrayString.append(" ").append(rand_nums[i]).append(" ");
        }
        arrayString.append(" }");
        System.out.println(arrayString.toString());

        // Sum the numbers locally
        float local_sum = 0;
        int i;
        for (i = 0; i < 4; i++) {
            local_sum += rand_nums[i];
        }

        // Print the random numbers on each process
        System.out.printf("Local sum for process %d - %f, avg = %f\n", world_rank, local_sum, local_sum / 4);

        // prepare sending/receiving buffer
        float sendbuff[] = new float[2];
        float result[] = new float[2];
        sendbuff[0] = local_sum;
        sendbuff[1] = local_sum * 2;

        // Reduce all of the local sums into the global sum
        MPI.COMM_WORLD.Reduce(sendbuff, 0, result, 0, 2, MPI.FLOAT, MPI.SUM, 0);

        // Print the result
        if (world_rank == 0) {
            System.out.printf("Total sum = %f, avg = %f\n", result[0], result[0] / (world_size * 4));
            System.out.printf("Total Double sum = %f, avg = %f\n", result[1], result[1] / (world_size * 4));
        }

    }

}