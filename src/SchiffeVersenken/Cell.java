package SchiffeVersenken;

public class Cell {
    private boolean belegt;
    private boolean beschossen;
    private int x;
    private int y;
    private String content;

    public Cell(int x, int y, String content) {
        belegt = false;
        beschossen = false;
        this.x = x;
        this.y = y;
        this.content = content;
    }

    public Cell(int x, int y) {
        belegt = false;
        beschossen = false;
        this.x = x;
        this.y = y;
        content = " ";

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isBelegt() {
        return belegt;
    }

    public void setBelegt(boolean belegt) {
        this.belegt = belegt;
    }

    public boolean isBeschossen() {
        return beschossen;
    }

    public void setBeschossen(boolean beschossen) {
        this.beschossen = beschossen;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
