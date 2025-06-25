import java.util.Random;

class RandomNumber extends Thread {
    public void run() {
        Random rand = new Random();
        while (true) {
            int num = rand.nextInt(100);
            System.out.println("Generated: " + num);

            if (num % 2 == 0) {
                new Square(num).start();
            } else {
                new Cube(num).start();
            }

            try {
                Thread.sleep(1000);  // wait 1 second
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

class Square extends Thread {
    int num;
    Square(int n) {
        num = n;
    }

    public void run() {
        System.out.println("Square of " + num + " is: " + (num * num));
    }
}

class Cube extends Thread {
    int num;
    Cube(int n) {
        num = n;
    }

    public void run() {
        System.out.println("Cube of " + num + " is: " + (num * num * num));
    }
}

public class MultiThreadApp {
    public static void main(String[] args) {
        RandomNumber r = new RandomNumber();
        r.start();
    }
}
