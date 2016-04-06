package database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mahdi on 3/5/2016.
 */
public class Food implements Parcelable {

    private int id;
    private String title;
    private String description;
    private String picture;
    private String bodyPicture;
    private int like;
    private int read;
    private String type;

    public Food(){

    }

    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getPicture(){
        return picture;
    }
    public String getBodyPicture(){
        return bodyPicture;
    }
    public int getLike(){
        return like;
    }
    public int getRead(){
        return read;
    }
    public String getType(){
        return type;
    }


    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public void setPicture(String picture){
        this.picture=picture;
    }

    public void setBodyPicture(String bodyPicture){
        this.bodyPicture=bodyPicture;
    }

    public void setLike(int like){
        this.like=like;
    }

    public void setRead(int read){
        this.read=read;
    }

    public void setType(String type){
        this.type=type;
    }



    protected Food(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        picture = in.readString();
        bodyPicture = in.readString();
        like = in.readInt();
        read = in.readInt();
        type = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel arg0) {
            return new Food(arg0);
        }

        @Override
        public Food[] newArray(int arg0) {
            return new Food[arg0];
        }
    };






    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(picture);
        dest.writeString(bodyPicture);
        dest.writeInt(like);
        dest.writeInt(read);
        dest.writeString(type);
    }
}
