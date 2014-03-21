package com.sfr.demo.model;

import java.util.Date;

public class Form {
    
    private String name;
    
    private String age;
    
    private Date date;
    
    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(final String age) {
        this.age = age;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(final Date date) {
        this.date = date;
    }
    
    public Form() {
    }
    
    public Form(final String name, final String age, final Date date) {
        this.name = name;
        this.age = age;
        this.date = date;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.age == null) ? 0 : this.age.hashCode());
        result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Form other = (Form) obj;
        if (this.age == null) {
            if (other.age != null)
                return false;
        } else if (!this.age.equals(other.age))
            return false;
        if (this.date == null) {
            if (other.date != null)
                return false;
        } else if (!this.date.equals(other.date))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        return true;
    }
    
    
}
