package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tag implements Serializable{

    private static final long serialVersionUID = 2572659596329551353L;
    private List<String> people;
    private String location;

    public Tag() {
        people = new ArrayList<>();
        location = new String();
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }

    public void addPerson(String newPerson){
        people.add(newPerson);
    }

    public List<String> getPeople(){
        return people;
    }

    public boolean checkIfPeopleEmpty(){
        if(people.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkIfLocationEmpty(){
        if(location.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    public void deleteLocation(){
        this.location = "";
    }

    public String getPerson(String targetPerson){
        int index = -1;

        for(int i = 0; i < people.size();i++){
            if(people.get(i).equals(targetPerson)){
                index = i;
            }
        }

        if(index != -1){
            return people.get(index);
        }
        else{
            return "none";
        }
    }

}

