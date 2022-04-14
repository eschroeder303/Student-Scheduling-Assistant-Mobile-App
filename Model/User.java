package android.C868.Capstone.All.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table", indices = {@Index(value = {"user_name", "password"}, unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private final int user_id;
    @ColumnInfo(name = "user_name")
    private String userName;
    @ColumnInfo(name = "password")
    private String password;

    public User(int user_id, String userName, String password) {
        this.user_id = user_id;
        this.userName = userName;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
