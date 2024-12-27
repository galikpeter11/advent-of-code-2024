package day09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));

        // Read the input, which is a single long disk map line
        String input = f.readLine();

        // Generate the initial disk map
        List<Integer> diskMap = generateDiskMap(input);

        // Compact the disk map
        defragmentDiskMap(diskMap);

        // Calculate the checksum of the compacted disk map
        long checksum = calculateChecksum(diskMap);
        System.out.println("Checksum: " + checksum);
    }

    // Parses the input string into a list representing the disk layout
    private static List<Integer> generateDiskMap(String input) {
        List<Integer> diskMap = new ArrayList<>();
        int currentFileId = 0;

        for (int i = 0; i < input.length(); i++) {
            int blockSize = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) { // For file blocks
                for (int j = 0; j < blockSize; j++) {
                    diskMap.add(currentFileId); // Add file block with ID
                }
                currentFileId++; // Increment file ID for the next file
            } else { // For free space blocks
                for (int j = 0; j < blockSize; j++) {
                    diskMap.add(-1); // Add free space
                }
            }
        }

        return diskMap;
    }

    // Compacts the disk map by moving file blocks one step to the left per iteration
    private static void defragmentDiskMap(List<Integer> diskMap) {
        int left = 0;
        int right = diskMap.size() - 1;

        while (left < right) {
            // Find the first empty block from the left
            while (left < right && diskMap.get(left) != -1) {
                left++;
            }

            // Find the first file block from the right
            while (left < right && diskMap.get(right) == -1) {
                right--;
            }

            // Swap the file block with the free space block
            if (left < right) {
                diskMap.set(left, diskMap.get(right));
                diskMap.set(right, -1);
            }
        }
    }

    // Calculates the checksum of the compacted disk
    private static long calculateChecksum(List<Integer> diskMap) {
        long checksum = 0;

        for (int position = 0; position < diskMap.size(); position++) {
            Integer block = diskMap.get(position);
            if (!block.equals(-1)) { // Skip free spaces
                checksum += (long) position * block; // Multiply position by file ID
            }
        }

        return checksum;
    }
}