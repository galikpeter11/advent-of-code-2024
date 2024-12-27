package day09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Part2 {

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
        // Step 1: Precompute free space regions
        List<int[]> freeSpans = computeFreeSpans(diskMap);

        // Step 2: Get file IDs in descending order
        List<Integer> fileIds = new ArrayList<>();
        for (int block : diskMap) {
            if (block != -1 && !fileIds.contains(block)) {
                fileIds.add(block);
            }
        }
        fileIds.sort((a, b) -> b - a);

        // Step 3: Process files in descending ID order
        for (int fileId : fileIds) {
            // Locate the file's blocks and determine its size
            int start = -1, end = -1;
            for (int i = 0; i < diskMap.size(); i++) {
                if (diskMap.get(i) == fileId) {
                    if (start == -1) start = i;
                    end = i;
                }
            }
            int fileSize = end - start + 1;

            // Try to move the file to the leftmost suitable free span
            for (int i = 0; i < freeSpans.size(); i++) {
                int[] span = freeSpans.get(i);
                int spanStart = span[0];
                int spanSize = span[1];

                if (spanSize >= fileSize && spanStart + spanSize <= start) {
                    // Move the file into this free span
                    for (int j = 0; j < fileSize; j++) {
                        diskMap.set(spanStart + j, fileId); // Place file blocks
                        diskMap.set(start + j, -1); // Free up old space
                    }

                    // Update the free spans
                    freeSpans.remove(i);
                    if (spanSize > fileSize) {
                        freeSpans.add(i, new int[]{spanStart + fileSize, spanSize - fileSize});
                    }
                    freeSpans.add(new int[]{start, fileSize}); // Free up the file's old location
                    freeSpans.sort(Comparator.comparingInt(a -> a[0])); // Keep spans sorted
                    break; // File moved successfully
                }
            }
            // If no valid free span is found, leave the file in place
        }
    }

    // Helper method to precompute free space spans
    private static List<int[]> computeFreeSpans(List<Integer> diskMap) {
        List<int[]> freeSpans = new ArrayList<>();
        int start = -1, size = 0;

        for (int i = 0; i < diskMap.size(); i++) {
            if (diskMap.get(i) == -1) {
                if (start == -1) start = i;
                size++;
            } else {
                if (start != -1) {
                    freeSpans.add(new int[]{start, size});
                    start = -1;
                    size = 0;
                }
            }
        }

        // Add the last free span if we're still tracking one
        if (start != -1) {
            freeSpans.add(new int[]{start, size});
        }

        return freeSpans;
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