package sortari;

public class QuickSorter extends AbstractSorter{
    @Override
    public void sort(int[] vector){
        quickSort(vector, 0, vector.length - 1);
    }
    private void quickSort(int[] vector, int start, int end) {
        if (start < end) {
            int pi = partition(vector, start, end);
            quickSort(vector, start, pi - 1);
            quickSort(vector, pi + 1, end);
        }
    }
    private int partition(int[] vector, int start, int end) {
        int pivot = vector[end];
        int i = (start - 1);
        for (int j = start; j < end; j++) {
            if (vector[j] < pivot) {
                i++;
                int temp = vector[i];
                vector[i] = vector[j];
                vector[j] = temp;
            }
        }
        int temp = vector[i + 1];
        vector[i + 1] = vector[end];
        vector[end] = temp;
        return i + 1;
    }
}