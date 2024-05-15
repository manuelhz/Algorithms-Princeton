public class Search {

    public static int binarySearch(int[] a, int key) {
        int lo = 0, hi = a.length-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }
    public static int bitonicLinear(int[] a) {
        for(int i = 1; i < a.length - 1;i++) {
            if(a[i - 1] < a[i] && a[i] > a[i + 1]) {
                return i;
            }
        }
        return -1;
    }
    public static int bitonicBinary(int[] a) {
        int min = 0;
        int max = a.length;
        int mid;
        while(min<max) {
            mid = min + (max-min)/2;
            if(a[mid - 1] < a[mid] && a[mid] > a[mid + 1]) {
                return mid;
            }
            else  if(a[mid] < a[mid+1]) {
                min = mid - 1;
            }
            else if(a[mid] > a[mid+1]) {
                max = mid + 1;
            }
        }
        return -1;
    }
    private static int rightSearch(int[] a, int key, int min, int max) {
        int mid;
        while(min <= max) {
            mid = min + (max - min)/2;
            System.out.println("min:"+min+"mid:"+mid+"max:"+max);
            if(key > a[mid]) {
                max = mid - 1;


            } else if (key < a[mid]) {
                min = mid + 1;

            } else return mid;
        }
        return -1;
    }
    private static int leftSearch(int[] a, int key, int bitonic) {
        int min = 0;
        int max = bitonic;
        int mid;
        while(min <= max) {
            mid = min + (max - min)/2;
            if(key > a[mid]) {
                min = mid + 1;
            } else if (key < a[mid]) {
                max = mid - 1;
            } else return mid;
        }
        return -1;
    }
    public static int bitonicSearch(int a[], int key) {
        int bitonic = Search.bitonicBinary(a);
        int min;
        int mid;
        int max;
        int search;
        if (key >= a[a.length -1] && a[bitonic] >= key) {
            min = bitonic;
            max = a.length - 1;
            search = rightSearch(a, key, min, max);
            if(search != -1) return search;

            else {
                return leftSearch(a, key, bitonic);
            }


        } else {


            leftSearch(a, key, bitonic);


        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = {6, 7, 8, 11, 9, 5, 2, 1};
        System.out.println(Search.bitonicBinary(a));
        System.out.println(Search.bitonicSearch(a, 10));
    }
}
