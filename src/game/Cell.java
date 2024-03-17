package game;

class Cell {

    private boolean isMine, isMarked, isOpen;


    Cell() {
        this.isMarked = this.isOpen = false;
        this.isMine = false;
    }

    public boolean isMine() {
        return isMine;
    }
    public void setAsMine(boolean mine) {
        isMine = mine;
    }
    public boolean isMarked() {
        return isMarked;
    }
    public void setAsMarked(boolean marked) {
        isMarked = marked;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public void setAsOpen(boolean open) {
        isOpen = open;
    }

}
