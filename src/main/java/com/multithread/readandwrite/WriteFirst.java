package com.multithread.readandwrite;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Wushudeng on 2018/10/16.
 */
public class WriteFirst {
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
        Semaphore write_waiting, read_count_mutex, use;
        IntegerObject readcount;

        public Reader(Semaphore write_waiting, Semaphore read_count_mutex, Semaphore use, IntegerObject readcount) {
            this.write_waiting = write_waiting;
            this.read_count_mutex = read_count_mutex;
            this.use = use;
            this.readcount = readcount;
        }

        @Override
        public void run() {
            super.run();
            try {
                write_waiting.acquire();
                write_waiting.release();

                read_count_mutex.acquire();
                if (readcount.get_i() == 0)
                    use.acquire();
                readcount.inc();
                System.out.println(readcount.get_i());
                read_count_mutex.release();

                // mock read
                sleep(1);
                System.out.println(getName());

                read_count_mutex.acquire();
                readcount.dec();
                if (readcount.get_i() == 0)
                    use.release();
                read_count_mutex.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Writer extends Thread {
        Semaphore write_count_mutex, use, write_waiting;
        IntegerObject write_count;

        public Writer(Semaphore write_count_mutex, Semaphore use, Semaphore write_waiting, IntegerObject write_count) {
            this.write_count_mutex = write_count_mutex;
            this.use = use;
            this.write_waiting = write_waiting;
            this.write_count = write_count;
        }

        @Override
        public void run() {
            super.run();

            try {

                write_count_mutex.acquire();
                if (write_count.get_i() == 0)
                    write_waiting.acquire();
                write_count.inc();
                write_count_mutex.release();

                use.acquire();
                //mock write
                sleep(1);
                System.out.println(getName());
                use.release();

                write_count_mutex.acquire();
                write_count.dec();
                if (write_count.get_i() == 0)
                    write_waiting.release();
                write_count_mutex.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public static void main(String[] args) {

        Semaphore use = new Semaphore(1), write_count_mutex = new Semaphore(1), read_count_mutex = new Semaphore(1),
                write_waiting = new Semaphore(1);

        IntegerObject read_count = new IntegerObject(0), write_count=new IntegerObject(0);
        Reader[] readers = new Reader[1000];
        Writer[] writers = new Writer[1000];

        for (int i=0; i<1000; i++) {
            readers[i] = new Reader(write_waiting, read_count_mutex, use, read_count);
            readers[i].setName("reader "+i);
            writers[i] = new Writer(write_count_mutex, use, write_waiting, write_count);
            writers[i].setName("writer "+i);
        }

        for (int i=0; i<1000; i++) {
            readers[i].start();
            writers[i].start();
        }

    }
}
