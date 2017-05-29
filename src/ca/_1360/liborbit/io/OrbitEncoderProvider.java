package ca._1360.liborbit.io;

public interface OrbitEncoderProvider {
    int getPosition();
    double getRate();
    void reset();
}
