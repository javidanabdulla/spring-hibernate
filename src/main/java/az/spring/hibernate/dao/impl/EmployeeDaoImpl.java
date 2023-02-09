package az.spring.hibernate.dao.impl;

import az.spring.hibernate.dao.EmployeeDao;
import az.spring.hibernate.model.Employee;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDaoImpl extends AbstractSessionFactory implements EmployeeDao {

    @Transactional
    @Override
    public void save (Employee employee) {
        getSession().save(employee);
    }

    @Transactional
    @Override
    public void update(Employee employee) {
        getSession().update(employee);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Employee employee = findById(id).orElseThrow(RuntimeException::new);
        getSession().delete(findById(id));
    }

    @Transactional
    @Override
    public Optional<Employee> findById (Integer id) {
        return Optional.ofNullable(getSession().get(Employee.class,id));
    }

    @Transactional
    @Override
    public List<Employee> findAll() {
        return getSession().createQuery("select e from Employee  e").list();
    }
}
