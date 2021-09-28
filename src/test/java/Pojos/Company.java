package Pojos;
/*
POJO stands for Plain Old Java Object.
A Pojo class must be public, and its variables must be private to improve the security of the project.
A Pojo can have a constructor with arguments, and it should have getter and setters to access data by other java programs.
Pojo classes are used for creating JSON and XML payloads(data) for API.
 */
public class Company {
    private String name;
    private int companyId;
    private String companyName;
    private String title;

    public Company(String name, int companyId, String companyName, String title) {
        this.name = name;
        this.companyId = companyId;
        this.companyName = companyName;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name=" + name +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setCompanyId(int companyId){
        this.companyId = companyId;
    }
    public int getCompanyId(){
        return this.companyId;
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    public String getCompanyName(){
        return this.companyName;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
}