package lab2.zad2;

import org.jgrapht.graph.DefaultEdge;

class CapacityEdge extends DefaultEdge {
    private final int capacityMax;
    private int capacity;

    public CapacityEdge(int capacityMax) {
        this.capacityMax = capacityMax;
        capacity = 0;
    }

    public CapacityEdge(int capacityMax, int capacity) {
        this.capacityMax = capacityMax;
        this.capacity = capacity;
    }

    public int getCapacityMax() {
        return capacityMax;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
