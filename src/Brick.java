public class Brick {
    private int id;
    private Position positionA;
    private Position positionB;

    public Brick(int id, Position positionA, Position positionB) {
        int coefficient = Math.abs(positionA.getRow() - positionB.getRow()) +
                            Math.abs(positionA.getCol() - positionB.getCol());
        if (coefficient == 1) {
            this.id = id;
            this.positionA = positionA;
            this.positionB = positionB;
        }
    }

    public Brick(int id, Position position) {
            this.id = id;
            this.positionA = position;
            this.positionB = null;

    }

    public int getId() {
        return id;
    }

    public Position getPositionA() {
        return positionA;
    }

    public Position getPositionB() {
        return positionB;
    }

    public void setPositionB(Position position) {
        int coefficient = Math.abs(this.positionA.getRow() - position.getRow()) +
                Math.abs(this.positionA.getCol() - position.getCol());
        if (coefficient == 1) {
            this.positionB = position;
        }
    }
}
