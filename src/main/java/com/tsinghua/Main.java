package com.tsinghua;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by Wushudeng on 2018/11/15.
 */
public class Main {
    private static class Student implements Comparable{
        String name;
        int grade;

        public Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }

        @Override
        public int compareTo(Object o) {
            Student student = (Student) o;
            if (grade == student.grade) {
                return 0;
            } else if (grade < student.grade) {
                return -1;
            }
            return 1;
        }
    }

    public static void main(String[] args) {
        int student_num, sort_order=0;
        ArrayList<Student> list = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            try {
                student_num = Integer.valueOf(scanner.nextLine());
                sort_order = Integer.valueOf(scanner.nextLine());

                for (int i=0; i<student_num; i++) {
                    String line = scanner.nextLine();
                    String[] items = line.split(" ");
                    list.add(new Student(items[0], Integer.valueOf(items[1])));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            Comparator<Student> comparator = (sort_order == 1) ? new Comparator<Student>() {
                @Override
                // decrease order
                public int compare(Student o1, Student o2) {
                    return o1.compareTo(o2);
                }
            } : new Comparator<Student>() {
                // increase order
                @Override
                public int compare(Student o1, Student o2) {
                    return o2.compareTo(o1);
                }
            };

            list.sort(comparator);

            for (Student s:list) {
                System.out.println(s.name+" "+s.grade);
            }
        }
    }
}
