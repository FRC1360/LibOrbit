package ca._1360.liborbit.test;

import ca._1360.liborbit.auto.OrbitAutonomousCommand;
import ca._1360.liborbit.auto.OrbitAutonomousController;

import java.util.Arrays;

public class AutoControllerTest {
    public static void main(String[] args) {
        OrbitAutonomousController<Subsystem> controller = new OrbitAutonomousController<>(Arrays.asList(consumer -> {
            consumer.accept(new OrbitAutonomousCommand<Subsystem>(Subsystem.SS1, 500) {
                @Override
                protected void initializeCore() {
                    System.out.println("Start command 1A");
                }

                @Override
                protected void deinitializeCore() {
                    System.out.println("End command 1A");
                }
            });
            consumer.accept(new OrbitAutonomousCommand<Subsystem>(Subsystem.SS2, 1000) {
                @Override
                protected void initializeCore() {
                    System.out.println("Start command 2A");
                }

                @Override
                protected void deinitializeCore() {
                    System.out.println("End command 2A");
                }
            });
        }, consumer -> consumer.accept(new OrbitAutonomousCommand<Subsystem>(Subsystem.SS1, 500) {
            @Override
            protected void initializeCore() {
                System.out.println("Start command 1B");
            }

            @Override
            protected void deinitializeCore() {
                System.out.println("End command 1B");
            }
        })), Subsystem.values());
        controller.getModes().forEach(controller.getSelection()::add);
        controller.start();
    }

    private enum Subsystem {
        SS1, SS2;
    }
}
