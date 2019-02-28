public class Photo {

    private String orientation;
    private String[] tags;

    public Photo(String currentOrientation, String[] tags){
        this.orientation = currentOrientation;
        this.tags = tags;
    }

    public String[] getTags(){
        return tags;
    }

    public String getOrientation(){
        return orientation;
    }
}
