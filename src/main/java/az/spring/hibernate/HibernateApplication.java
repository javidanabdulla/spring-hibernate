package az.spring.hibernate;

import az.spring.hibernate.config.SpringHibernateConfig;
import az.spring.hibernate.dao.EmployeeDao;
import az.spring.hibernate.dao.PersonDao;
import az.spring.hibernate.model.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HibernateApplication {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringHibernateConfig.class);
        EmployeeDao employeeDao = context.getBean(EmployeeDao.class);
        PersonDao personDao = context.getBean(PersonDao.class);

        Person person = new Person();
        person.setName("Ali");
        person.setSurname("Aliyev");
        person.setAge(20);
        person.setSalary(1000);

        personDao.save(person);

    }
}
