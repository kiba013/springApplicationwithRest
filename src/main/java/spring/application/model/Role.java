package spring.application.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;


@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
//<div class="col-sm-4">
//<label for="email1" class="form-label">Email</label>
//<input required type="text" th:field="*{email}" class="form-control" id="email1">
//</div>
//<div class="col-sm-4">
//<label for="password1" class="form-label">Password</label>
//<input required th:field="*{password}" type="password" class="form-control" id="password1">
//</div>
//<div class="col-12">
//<input type="checkbox" th:each="role : ${roles}"
//        th:field="*{roleSet}"
//        th:value="${role.getId()}"
//        th:text="${role.getName()}">
//</div>