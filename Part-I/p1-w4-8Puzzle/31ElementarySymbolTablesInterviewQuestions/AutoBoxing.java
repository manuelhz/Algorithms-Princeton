public class AutoBoxing {
    //https://stackoverflow.com/questions/12559634/java-autoboxing-rules
    //https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html
    public static void main(String args[]) {
        System.out.println("Find values such that a == b true but x.equals(y) is false");
        double a2 = 0.0;
        double b2 = -0.0;
        Double x2 = 0.0;
        Double y2 = -0.0;
        System.out.print("a=" + a2 + " b=" + b2 + " ");
        System.out.println(a2==b2);
        System.out.print("x=" + x2 + " y=" + y2 + " ");
        System.out.println(x2.equals(y2));
        System.out.println("Find values such that a == b is false but x.equals(y) is true.");
        double a1 = 0.0/0.0;
        double b1 = 0.0/0.0;
        Double x1 = 0.0/0.0;
        Double y1 = 0.0/0.0;
        System.out.print("a=" + a1 + " b=" + b1 + " ");
        System.out.println(a1==b1);
        System.out.print("x=" + x1 + " y=" + y1 + " ");
        System.out.println(x1.equals(y1));
    }
}

