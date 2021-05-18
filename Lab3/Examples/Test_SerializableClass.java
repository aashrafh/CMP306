
import mpi.*;
import java.io.*;

public class Test_SerializableClass {

  public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

    MPI.Init(args);
    int rank = MPI.COMM_WORLD.Rank();
    int root = 0;
    int[] recvbuf = new int[1];
    if (rank == root) {

      SerializableClass test = new SerializableClass();
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutput out = null;
      try {
        out = new ObjectOutputStream(bos);
        out.writeObject(test);
        out.flush();
        byte[] yourBytes = bos.toByteArray();
        recvbuf[0] = yourBytes.length;
        MPI.COMM_WORLD.Bcast(recvbuf, 0, 1, MPI.INT, 0);
        MPI.COMM_WORLD.Bcast(yourBytes, 0, yourBytes.length, MPI.BYTE, 0);
      } finally {
        try {
          bos.close();
        } catch (IOException ex) {
          // ignore close exception
        }
      }

    } else {

      MPI.COMM_WORLD.Bcast(recvbuf, 0, 1, MPI.INT, 0);
      byte[] yourBytes = new byte[recvbuf[0]];

      MPI.COMM_WORLD.Bcast(yourBytes, 0, yourBytes.length, MPI.BYTE, 0);
      ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
      ObjectInput in = null;
      try {
        in = new ObjectInputStream(bis);
        SerializableClass o = (SerializableClass) in.readObject();
        o.print();
      } finally {
        try {
          if (in != null) {
            in.close();
          }
        } catch (IOException ex) {
          // ignore close exception
        }
      }
      System.out.println("Process " + rank + " exiting ...");
    }

    MPI.Finalize();
  }
}
