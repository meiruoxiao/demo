package com.mrx.www.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 测试类.
 *
 * @author Mei Ruoxiao
 * @since 2019/05/22
 */
@AllArgsConstructor
@NoArgsConstructor
public class PersonPoJo {
    private String name;
    private Integer age;
    private static Integer ss;

    public static PersonPoJo of() {
        return new PersonPoJo();
    }

    public PersonPoJo(String name) {
        this.name = name;
    }

    public PersonPoJo(Integer age) {
        this.age = age;
    }

    public static PersonPoJo of(String name, Integer age) {
        PersonPoJo poJo = new PersonPoJo();
        poJo.setAge(age);
        poJo.setName(name);
        return poJo;
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PersonPoJo)) {
            return false;
        }
        final PersonPoJo other = (PersonPoJo) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object name = this.getName();
        final Object otherName = other.getName();
        if (!Objects.equals(name, otherName)) {
            return false;
        }
        final Object age = this.getAge();
        final Object otherAge = other.getAge();
        if (!Objects.equals(age, otherAge)) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PersonPoJo;
    }


    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object name = this.getName();
        result = result * PRIME + (name == null ? 43 : name.hashCode());
        final Object age = this.getAge();
        result = result * PRIME + (age == null ? 43 : age.hashCode());
        return result;
//        final Integer age = this.getAge();
//        return age % 9;
    }
}
