package com.multithread.readandwrite;

import java.util.concurrent.Semaphore;

/**
 * Created by Wushudeng on 2018/10/16.
 */
public class ReadFirst {
    private static class IntegerObject {
        int i;

        public IntegerObject(int i) {
            this.i = i;
        }

        public int get_i() {return i;}
        public void inc() {i++;}
        public void dec() {i--;}
    }

    private static class Reader extends Thread {
        Semaphore use, read_count_mutex;
        IntegerObject read_count;

        public Reader(Semaphore use, Semaphore read_count_mutex, IntegerObject read_count) {
            this.use = use;
            this.read_count_mutex = read_count_mutex;
            this.read_count = read_count;
        }

        @Override
        public void run() {
            super.run();

            try {
                read_count_mutex.acquire();
                if (read_count.get_i() == 0)
                    use.acquire();
                read_count.inc();
                read_count_mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            read();

            try {
                read_count_mutex.acquire();
                read_count.dec();
                if (read_count.get_i() == 0)
                    use.release();
                read_count_mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        public void read() {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName());
        }
    }


    private static class Writer extends Thread {
        Semaphore use;

        public Writer(Semaphore use) {
            this.use = use;
        }

        @Override
        public void run() {
            super.run();
            try {
                use.acquire();
                write();
                use.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void write () {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName());
        }
    }

    public static void main(String[] args) {

        Semaphore use = new Semaphore(1), write_count_mutex = new Semaphore(1), read_count_mutex = new Semaphore(1),
                write_waiting = new Semaphore(1);

        IntegerObject read_count = new IntegerObject(0), write_count=new IntegerObject(0);
        Reader[] readers = new Reader[1000];
        Writer[] writers = new Writer[1000];

        for (int i=0; i<1000; i++) {
            readers[i] = new Reader(use, read_count_mutex, read_count);
            readers[i].setName("reader "+i);
            writers[i] = new Writer(use);
            writers[i].setName("writer "+i);
        }

        for (int i=0; i<1000; i++) {
            writers[i].start();
            readers[i].start();
        }

    }
}
