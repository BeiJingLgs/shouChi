package cfcc.com.shouChi.bean;


/**
 * Created by acer on 2018/1/12.
 */

public class Person  {
    private  String   name;
    private  String   age;
    private  String   gender;
    private  String   hobby;
    private  String   book;

    public Person(String name, String age, String gender, String hobby, String book) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hobby = hobby;
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", hobby='" + hobby + '\'' +
                ", book='" + book + '\'' +
                '}';
    }
}
